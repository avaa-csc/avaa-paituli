
/**
 * 
 */
package fi.csc.avaa.paituli.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.RandomStringUtils;

import fi.csc.avaa.tools.StringTools;
import fi.csc.avaa.tools.logging.AvaaLogger;
import fi.csc.avaa.vaadin.email.Action;

/**
 * @author jmlehtin
 *
 */
public class GenerateDownloadPackageAction implements Action<String> {
	
	private AvaaLogger log = new AvaaLogger(this.getClass().getName());

	private final String INPUT_BASE_PATH = "/geodata/";
	private final String OUTPUT_BASE_PATH = "/var/www/html/tmp/";
	private String outfilename;
	private final String OUTFILE_PREFIX = "paituli_";
	private final String OUTFILE_EXTENSION = ".zip";
	private final String OUTFILE_BASE_URL = "http://avaa.tdata.fi/tmp/";
	private final String  SEPARATOR = "/";
	private final int BUFF = 8096;
	
	private String filePaths;
	
	public GenerateDownloadPackageAction(String filePaths) {
		this.filePaths = filePaths;
	}
	
	void toZip(File file, ZipOutputStream zout) {
		try {
			if (file.isDirectory()) {
				zipSubDirectory(file.getName() + SEPARATOR, file, zout);
			} else {
				zipFile(file, zout);
			}
		}  catch (IOException e) {
			log.error("IO exception while zipping the files: " + file.getAbsolutePath());
	    }
	}

	void zipFile(File file, ZipOutputStream zout) throws IOException {
	    FileInputStream fin = new FileInputStream(file);
	    String name = file.getName();
	    if (name.endsWith("jp2") || name.endsWith("zip")) {    	
	        zout.setLevel(Deflater.NO_COMPRESSION);
	    }
	    zout.putNextEntry(new ZipEntry(entryname(file)));
	    int length;
	    FileChannel ch = fin.getChannel();
	    MappedByteBuffer mb = ch.map(MapMode.READ_ONLY,
	        0L, ch.size( ));
	    byte[] barray = new byte[BUFF];
	    while(mb.hasRemaining()) {
	    	length = Math.min(mb.remaining( ), BUFF);
	        mb.get(barray, 0, length);   
	        zout.write(barray, 0, length);
	    }
	    zout.closeEntry();
	    zout.setLevel(Deflater.BEST_COMPRESSION);
	    fin.close();
	}

	void zipSubDirectory(String basePath, File dir, ZipOutputStream zout) throws IOException {
	    File[] files = dir.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            String path = basePath + file.getName() + SEPARATOR;
	            //zout.putNextEntry(new ZipEntry(path));
	            zipSubDirectory(path, file, zout);
	            //zout.closeEntry();
	        } else {
	        	zipFile(file, zout);
	        }
	    }
	}
	    
	@Override
	public String doAction() {
		synchronized(this) {
			outfilename  = OUTFILE_PREFIX + RandomStringUtils.random(8, false, true) + OUTFILE_EXTENSION;
			if(!StringTools.isEmptyOrNull(filePaths)) {
				String[] filenamea =  filePaths.split(";");
				ArrayList<File> alf = new  ArrayList<File>();
				for (String filename : filenamea) {
					String absoluteFilePath = INPUT_BASE_PATH + filename;
					if (absoluteFilePath.contains("*")) { //RegEx
						int vika = absoluteFilePath.lastIndexOf(SEPARATOR);
						File parent = new File(absoluteFilePath.substring(0, vika)); 
						File[] files = parent.listFiles();
						String loppu = absoluteFilePath.substring(vika+1);	
						Pattern p = Pattern.compile(loppu);
						for (File file : files) {
							Matcher m = p.matcher(file.getName());
							if (m.matches()) {
								alf.add(file);
							}
						}
					} else  { // No RegEx
						File file = new File(absoluteFilePath);
						if (file.exists()) {
							alf.add(file);
						} else {
							log.warn("File cannot be found from path " + absoluteFilePath);
						}
					}
				}
				if (alf.isEmpty()) {
					log.error("There was no existing files listed in the filename list!");
					return null;
				} else {
					FileOutputStream fos = null;
					try {
						File outputFile = new File(OUTPUT_BASE_PATH + outfilename);
						if(!outputFile.exists()) {
							outputFile.createNewFile();
						} 
						fos = new FileOutputStream(outputFile);
						ZipOutputStream zout = new ZipOutputStream(fos);
						try {
							alf.forEach(file -> toZip(file, zout));
							zout.flush();
						} catch (IOException ex) {
							log.error(ex.getStackTrace());
							return null;
						} finally {
							if(zout != null) {
								zout.close();
							}
							if(fos != null) {
								fos.close();
							}
						}
						return OUTFILE_BASE_URL + outfilename;
					} catch (IOException e) {
						log.error(e.getStackTrace());
						return null;
					} finally {
						try {
							if(fos != null) {
								fos.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
	
	private String entryname (File file) {
		String absoluteFilePath  = file.getAbsolutePath();
		if (absoluteFilePath.startsWith(INPUT_BASE_PATH)) {
			return absoluteFilePath.substring(INPUT_BASE_PATH.length());
		} else {
			log.warn("Path not matched INPUT_BASE_PATH=/geodata/"+absoluteFilePath);
			//return absoluteFilePath.substring(INPUT_BASE_PATH.length()); //tämä on ainakin väärin, mutta  vaihtoehto??
			return file.getName(); //toivottavasti tänne ei koskaan jouduta!
		}
	}
}

