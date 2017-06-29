/**
 * 
 */
package fi.csc.avaa.paituli.email;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fi.csc.avaa.tools.logging.AvaaLogger;
import fi.csc.avaa.vaadin.email.SendEmailThread;

/**
 * @author jmlehtin
 *
 */
public class SendPaituliEmail extends SendEmailThread<String> {
	
	private AvaaLogger log = new AvaaLogger(this.getClass().getName());

	private static final String FROM_EMAIL_ADDRESS = "PaITuli (CSC) <gis@csc.fi>";
	private static final String SUBJECT_FI = "PaITulin lataustiedosto on valmiina";
	private static final String SUBJECT_EN = "PaITuli data download is available";
	private static final String BODY_TEXT_1_FI = "Ladattava aineisto: !, !, !, !, !, !.<br><br>";
	private static final String BODY_TEXT_1_EN = "You have ordered: !, !, !, !, !, !.<br><br>";
	private static final String BODY_TEXT_2_FI = "Tiedostot:<br>!<br><br>";
	private static final String BODY_TEXT_2_EN = "Files:<br>!<br><br>";
	private static final String BODY_TEXT_3_FI = "Tilaamasi tiedostot ovat ladattavissa osoitteessa:<br><br><a href='!'>!</a>.<br><br>";
	private static final String BODY_TEXT_3_EN = "The files ordered can be downloaded from:<br><br><a href='!'>!</a>.<br><br>";
	private static final String BODY_TEXT_4_FI = "Kiitos lataustilauksestasi.<br><br>";
	private static final String BODY_TEXT_4_EN = "Thank you for downloading from PaITuli.<br><br>";
	private final String language;
	private final String org;
	private final String data;
	private final String scale;
	private final String year;
	private final String coordsys;
	private final String format;
	private final String filenames;
	private final String dataId;
	
	public SendPaituliEmail(String toEmailAddress, GenerateDownloadPackageAction action, String language, String org, String data, String scale, String year, String coordsys, String format, String filenames, String dataId) {
		this.toEmailAddress = toEmailAddress;
		this.actionSupplier = action::doAction;
		this.language = language;
		this.org = org;
		this.data = data;
		this.scale = scale;
		this.year = year;
		this.coordsys = coordsys;
		this.format = format;
		this.filenames = filenames;
		this.dataId = dataId;
	}

	@Override
	public void doRun() {
		String zipUrl = actionSupplier.get();
		if(zipUrl == null) {
			log.error("Something went wrong while zipping files. No email will be sent!");
			return;
		}
		String subject = "", bt1 = "2", bt2 = "", bt3 = "", bt4 = "", bt5 = "";
		if("fi_FI".equals(language)) {
			subject = SUBJECT_FI;
			bt1 = BODY_TEXT_1_FI;
			bt2 = BODY_TEXT_2_FI;
			bt3 = BODY_TEXT_3_FI;
			bt4 = BODY_TEXT_4_FI;
		} else if ("en_US".equals(language)) {
			subject = SUBJECT_EN;
			bt1 = BODY_TEXT_1_EN;
			bt2 = BODY_TEXT_2_EN;
			bt3 = BODY_TEXT_3_EN;
			bt4 = BODY_TEXT_4_EN;
		}
		if(zipUrl != null) {
			StringBuffer sb = new StringBuffer();
			
			if(org != null) {
				bt1 = bt1.replaceFirst("!", org);
			} else {
				bt1 = bt1.replaceFirst("!, ", "");
			}
			if(data != null) {
				bt1 = bt1.replaceFirst("!", data);
			} else {
				bt1 = bt1.replaceFirst("!, ", "");
			}
			if(scale != null) {
				bt1 = bt1.replaceFirst("!", scale);
			} else {
				bt1 = bt1.replaceFirst("!, ", "");
			}
			if(year != null) {
				bt1 = bt1.replaceFirst("!", year);
			} else {
				bt1 = bt1.replaceFirst("!, ", "");
			}
			if(coordsys != null) {
				bt1 = bt1.replaceFirst("!", coordsys);
			} else {
				bt1 = bt1.replaceFirst("!, ", "");
			}
			if(format != null) {
				bt1 = bt1.replaceFirst("!", format);
			} else {
				bt1 = bt1.replaceFirst("!, ", "");
			}
			
			sb.append(bt1);
			if(filenames != null) {
				String htmlFilenames = "";
				String[] fileNamesArray = filenames.split(";");
				List<String> orderedFilenames = Arrays.asList(fileNamesArray);
				Collections.sort(orderedFilenames);
				for(String filename : orderedFilenames) {
					htmlFilenames += "<br>" + filename;
				}
				sb.append(bt2.replaceFirst("!", htmlFilenames));
				StoreLog st = new StoreLog(toEmailAddress, orderedFilenames.size(), dataId);
				st.start();
			}
			sb.append(bt3.replaceAll("!", zipUrl));
			sb.append(bt4);
			sb.append(bt5);
			sendMail(FROM_EMAIL_ADDRESS, subject, sb.toString(), true);
		} 
	}

}
