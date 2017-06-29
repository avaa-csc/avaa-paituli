
/**
 * 
 */
package fi.csc.avaa.paituli.email;

import fi.csc.avaa.tools.StringTools;
import fi.csc.avaa.tools.logging.AvaaLogger;
import fi.csc.avaa.vaadin.email.Action;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

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
	private final String OUTFILE_BASE_URL = "https://avaa.tdata.fi/tmp/";
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

    /**
     * Muutettu 12.10.2016. kopioimaan zip-tiedoston sisältö tiedoston itsensä asemasta sisäkkäisten ziptiedostojen
     * välttämiseksi. AVAA-741 Zip files to folders in Paituli .zip-file generation
     *
     *
     * @param file File to add zip
     * @param zout ZipOutputStream to add file
     * @throws IOException
     */
	void zipFile(File file, ZipOutputStream zout) throws IOException {

	    String name = file.getName();
	    if (name.endsWith("jp2")) {
	        zout.setLevel(Deflater.NO_COMPRESSION);
	    }
	    if (name.endsWith(OUTFILE_EXTENSION)) { // .zip
            zipContenCopy(file, zout);
        } else {
            FileInputStream fin = new FileInputStream(file);
            zout.putNextEntry(new ZipEntry(entryname(file)));
            int length;
            FileChannel ch = fin.getChannel();
            MappedByteBuffer mb = ch.map(MapMode.READ_ONLY,
                    0L, ch.size());
            byte[] barray = new byte[BUFF];
            while (mb.hasRemaining()) {
                length = Math.min(mb.remaining(), BUFF);
                mb.get(barray, 0, length);
                zout.write(barray, 0, length);
            }
            zout.closeEntry();
            zout.setLevel(Deflater.BEST_COMPRESSION);
            fin.close();
        }
        //System.out.println("Zip done:"+name);
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

    /**
     * stackoverflow.com/questions/11766741/what-is-the-idiomatic-way-to-copy-a-zipentry-into-a-new-zipfile
     *
     * @param file File file.zip which content to copy
     * @param zout  ZipOutputStream to add file
     */
    void zipContenCopy(File file,  ZipOutputStream zout) {
        try {
            String path = entryname(file);
            path = path.substring(0, path.length()-4) + SEPARATOR; //remove .zip
            zout.putNextEntry(new ZipEntry(path));
			Charset cs = Charset.forName("ISO-8859-1");
            ZipFile original = new ZipFile(file, cs);
            Enumeration entries = original.entries();
            byte[] buffer = new byte[BUFF];
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                // create a new empty ZipEntry
                ZipEntry newEntry = new ZipEntry(path+entry.getName());
	//      outputStream.putNextEntry(entry);
                zout.putNextEntry(newEntry);
                InputStream in = original.getInputStream(entry);
                while (0 < in.available()) {
                    int read = in.read(buffer);
                    zout.write(buffer, 0, read);
                }
                in.close();
                zout.closeEntry(); //newEntry
            }
            zout.closeEntry(); //path
        } catch (IOException e) {
            log.error("Zip copy IOException"+e);
            e.getStackTrace();
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

