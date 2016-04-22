/**
 * 
 */
package fi.csc.avaa.paituli.email;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
/*import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;*/
import com.liferay.portal.kernel.exception.SystemException;

import fi.csc.avaa.paituli.db.model.paituliloki;
import fi.csc.avaa.paituli.db.service.paitulilokiLocalServiceUtil;

/**
 * @author pj
 *
 */
public class StoreLog {

	static void writeDB(String email, int lkm, String dataId) {

		try { 
			int id = getNextAvailableEventIdInDatabase();
			paituliloki pl = paitulilokiLocalServiceUtil.createpaituliloki(id);
			pl.setOrganisaatio(email.substring(email.indexOf("@")+1));
			pl.setPaiva(new Date());
			pl.setSaltedhash(hash(email));
			pl.setTiedotojenlkm(lkm-1);
			pl.setAineisto(dataId);
			paitulilokiLocalServiceUtil.addpaituliloki(pl);
		} catch (SystemException e) {
			System.err.println("SystemException paituliloki");
		}

	}

	static private String hash(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = "Tämäsuolamuutetaanvielä ennentuotantoa"+s;
			md.update(text.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return String.format("%064x", new java.math.BigInteger(1, digest));
		} catch (java.security.NoSuchAlgorithmException e) {
			System.err.println("SHA-256 puuttuu");			
		} catch (java.io.UnsupportedEncodingException e) {
			System.err.println("UTF-8 muka puuttuu");	
		}
		return "";		
	}
	
	@SuppressWarnings("unchecked")
	static synchronized int getNextAvailableEventIdInDatabase() {
		DynamicQuery maxEventIdQuery = DynamicQueryFactoryUtil.forClass(paituliloki.class);
		maxEventIdQuery.setProjection(ProjectionFactoryUtil.max("event_id"));
		List<Integer> results = null;
		try {
			results = paitulilokiLocalServiceUtil.dynamicQuery(maxEventIdQuery);
			if(results != null && results.size() == 1) {
				if(results.get(0) == null) {
					throw new SystemException();
				}
				return ((int) results.get(0)) + 1;
			}
		} catch (SystemException e) {
			System.err.println("Unable to determine max paituli event_id. Using 1 as event_id");
		}
		return 1;
	}
}