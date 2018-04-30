package com.beatus.goodbyeq.email.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beatus.goodbyeq.email.model.MailVO;
import com.beatus.goodbyeq.utils.Constants;
import com.beatus.goodbyeq.utils.Utils;

public class EmailRepository {
	
private static final Logger LOGGER = LoggerFactory.getLogger(EmailRepository.class);
	
	private Connection conn;
	
	private String defaultHost;
	private String defaultDriver;
	private String defaultUsername;
	private String defaultPassword;
	private String defaultPort;

	private static final Logger logger = LoggerFactory.getLogger(EmailRepository.class);
	
	public EmailRepository() throws ClassNotFoundException, SQLException, IOException{
		Properties prop = new Utils().getEmailProperties();
		defaultHost = prop.getProperty("db.host");
		defaultDriver = prop.getProperty("db.driver");
		defaultUsername = prop.getProperty("db.username");
		defaultPassword = prop.getProperty("db.password");
		defaultPort = prop.getProperty("db.port");
		logger.info("getRemoteConnection()::defaultHost:- " + defaultHost);
		
		Class.forName(defaultDriver);
		String jdbcUrl = "jdbc:mysql://" + defaultHost + ":" + defaultPort + "?user=" + defaultUsername + "&password=" + defaultPassword;
		conn = DriverManager.getConnection(jdbcUrl);
		logger.info("Remote connection successful.");
	}
	
	public String addEmail(MailVO mailVO) throws ClassNotFoundException, SQLException{
		LOGGER.info("In addStore " + mailVO.getMailId());
			String sql = "INSERT INTO EMAIL (store_id,company_id, store_name, address_line1, address_line2, city, state,zipcode, email,phone_number) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			/*statement.setString(1, mailVO.getStoreID());
			statement.setString(2, mailVO.getCompanyID());
			statement.setString(2, mailVO.getStoreName());
			statement.setString(3, mailVO.getAddressLine1());
			statement.setString(4, mailVO.getAddressLine2());
			statement.setString(5, mailVO.getCity());
			statement.setString(6, mailVO.getState());
			statement.setString(7, mailVO.getZipCode());
			statement.setString(8, mailVO.getEmail());
			statement.setString(9, mailVO.getPhoneNumber());*/
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				LOGGER.info("A new store information inserted successfully!");
				return Constants.SUCCESS;
			}
			return Constants.FAILURE;
	}

	public MailVO getMailVOByID(String mailId) throws SQLException  {
		MailVO mailVO = null;
		String sql = "SELECT * FROM MAIL WHERE store_id = ?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, mailId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			mailVO = new MailVO();
			mailVO.setMailId(result.getString("mail_id"));
			
		}
		return mailVO;
		
	}

	public String updateMailVO(MailVO mailVO) throws SQLException {
		LOGGER.info("In updateStore " + mailVO.getMailId());
		String sql = "UPDATE EMAIL SET company_id,store_name=?, address_line1=?, address_line2=?, city=?, state=?,zipcode=?, email=?,phone_number=?) WHERE store_id = ?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, mailVO.getMailId());
		
		int rowsUpdated = statement.executeUpdate();
		if (rowsUpdated > 0) {
			LOGGER.info("An existing store updated successfully!");
			return Constants.SUCCESS;
		}
		return Constants.FAILURE;
	}
	
	/*public MailVO getStoreByID(String mailId) throws SQLException {
		LOGGER.info("In getStoreByID " + mailId);
		MailVO mailVO = null;
		String sql = "SELECT * FROM EMAIL WHERE store_id = ?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, mailId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			mailVO = new MailVO();
			mailVO.setStoreID(result.getString("store_id"));
			mailVO.setCompanyID(result.getString("company_id"));
			mailVO.setStoreName(result.getString("store_name"));
			mailVO.setAddressLine1(result.getString("address_line1"));
			mailVO.setAddressLine2(result.getString("address_line2"));
			mailVO.setCity(result.getString("city"));
			mailVO.setState(result.getString("state"));
			mailVO.setZipCode(result.getString("zipcode"));
			mailVO.setEmail(result.getString("email"));
			mailVO.setPhoneNumber(result.getString("phone_number"));
		}
		return mailVO;
	}
	
	public String updateStore(MailVO mailVO) throws SQLException {
		LOGGER.info("In updateStore " + mailVO.getStoreID());
		String sql = "UPDATE EMAIL SET company_id,store_name=?, address_line1=?, address_line2=?, city=?, state=?,zipcode=?, email=?,phone_number=?) WHERE store_id = ?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, mailVO.getCompanyID());
		statement.setString(2, mailVO.getStoreName());
		statement.setString(2, mailVO.getAddressLine1());
		statement.setString(3, mailVO.getAddressLine2());
		statement.setString(4, mailVO.getCity());
		statement.setString(5, mailVO.getState());
		statement.setString(6, mailVO.getZipCode());
		statement.setString(7, mailVO.getEmail());
		statement.setString(8, mailVO.getPhoneNumber());
		statement.setString(9, mailVO.getStoreID());
		int rowsUpdated = statement.executeUpdate();
		if (rowsUpdated > 0) {
			LOGGER.info("An existing store updated successfully!");
			return Constants.SUCCESS;
		}
		return Constants.FAILURE;
	}
	
	public boolean deleteStore(String mailId) throws SQLException {
		LOGGER.info("In deleteStore " + mailId);
		String sql = "DELETE FROM EMAIL WHERE store_id = ?";

		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, mailId);

		boolean result = statement.execute();
		return result;
	}*/

}
