/**
 * 
 */
package fi.csc.avaa.paituli.email;

import javax.sql.DataSource;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import fi.csc.avaa.paituli.db.service.persistence.paitulilokiPersistence;
import fi.csc.avaa.paituli.db.service.persistence.paitulilokiUtil;

/**
 * write log line to database
 * Multithread version as AVAA-708
 * Tried to fix AVAA-794 noncaching service.xml. Also incremental removed from service.xml, because it was wrong
 *
 * @author pj
 *
 */
public class StoreLog  extends Thread {
    final static String INSERT = "INSERT into loki (organisaatio, paiva, saltedhash, tiedotojenlkm, " +
            "aineisto) VALUES (?, ?, ?, ?, ?)";
    String email;
    int lkm;
    String dataId;

    public StoreLog(String email, int lkm, String dataId) {
       this.email = email;
       this.lkm = lkm;
       this.dataId = dataId;
    }

    /**
     *  Database writing is done by seperate thread to ensure that user will get the email even this fails.
     *
     *  After use close connection to return it to pool
     */
	public void run() {

		try {
            PreparedStatement ps = getConnection().prepareStatement(INSERT);

			ps.setString(1,email.substring(email.indexOf("@")+1));
			ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
			//ps.setDate(2, new java.sql.Date(new Date().getTime()));
			ps.setString(3, hash(email));
			ps.setInt(4, lkm-1);
			ps.setString(5, dataId);
			ps.executeUpdate();
			ps.getConnection().close(); // return to pool

		} catch (SQLException e) {
            System.err.println("SQLException paituliloki");
            e.printStackTrace();
        }

    }

    /**
     * Salted SHA 256 hash
     *
     * @param s String to encrypt
     * @return String SHA-256 hashed
     */
	private String hash(String s) {
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


    /**
     * Database connection from pool
     *
     * @return Connection
     */
    public Connection getConnection() {
        Connection connection = null;

        paitulilokiPersistence paitulilokiPersistence = paitulilokiUtil.getPersistence();
        DataSource datasource = paitulilokiPersistence.getDataSource();
        if (datasource != null) {
            try {
                connection = datasource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
