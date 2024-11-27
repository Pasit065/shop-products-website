package com.web_application.main_website;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.web_application.main_website.constant.SqliteQueries;
import com.web_application.main_website.model.ClientForm;
import com.web_application.main_website.model.Receipt;

public class DatabaseRepository {
    public String databaseName = "clients_information";
	
	public void notifySQLException(SQLException e) {
		System.out.println(e.getMessage());
	}

	public Connection connectDatabase() {
        Connection con = null;

        String dbUrl = "jdbc:sqlite:./database/" + this.databaseName + ".db";

		try {
        	con = DriverManager.getConnection(dbUrl);
		} catch(SQLException e) {
			this.notifySQLException(e);
		}

        return con;
    }

	public void createdTableIfNotExists(Statement curState, String createTableQuery) {
		try {
			curState.execute(createTableQuery);
		} catch (SQLException e) {
			this.notifySQLException(e);
		}

		System.out.println("Table has been created.....");
	}

	public ClientForm getClientDetailFromSpecificedClientId(SqliteQueries sqliteQueries, Connection con, String searchedClientId) {
		ResultSet rs = null;
		PreparedStatement preState = null;

		ClientForm returnedClient = new ClientForm();
		System.out.println("Client id: " + searchedClientId);
		
		try {
			preState = con.prepareStatement(sqliteQueries.selectClientSpecifiedId);
			preState.setString(1, searchedClientId);

			rs = preState.executeQuery();
			
			// Get data from select table.
			returnedClient.setClientId(rs.getString("client_id"));   
			returnedClient.setName(rs.getString("name"));   
			returnedClient.setSurname(rs.getString("surname"));     
			returnedClient.setTotalPayment(this.getTotalPayment(sqliteQueries.selectToGetTotalPayment, con, returnedClient.getClientId()));
			returnedClient.setLastestUpdated(rs.getString("lastest_time_update"));
			returnedClient.setStatusRefernceFromPayment(); 
			
			System.out.println(
				"cli id: " + returnedClient.getClientId()
			);

		} catch(SQLException e) {
			System.out.println("Get error");
			this.notifySQLException(e);
		}

		return returnedClient;
	}

	public Float getTotalPayment(String getTotalPaymentFromClientIdQuery, Connection con, String clientId) {
		ResultSet rs = null;
		PreparedStatement preState = null;
		Float totalPayment = null;
		
		try {
			preState = con.prepareStatement(getTotalPaymentFromClientIdQuery);

			preState.setString(1, clientId);
			rs = preState.executeQuery();

			// Get total payment.
			totalPayment = rs.getFloat("total_payment");

			if (totalPayment == null) {
				totalPayment = 0f;
			}

		} catch(SQLException e) {
			this.notifySQLException(e);
		}

		return totalPayment;
	}

	public Integer getTotalReceipts(String getTotalReceiptQuery, Connection con, String clientId) {
		ResultSet rs = null;
		PreparedStatement preState = null;
		Integer totalReceipts = null;
		
		try {
			preState = con.prepareStatement(getTotalReceiptQuery);

			preState.setString(1, clientId);
			rs = preState.executeQuery();

			// Get total payment.
			totalReceipts = rs.getInt("total_receipts");

			System.out.println(totalReceipts);

		} catch(SQLException e) {
			this.notifySQLException(e);
		}

		return totalReceipts;
	}


	public boolean upsertClientDetailToClientsTable (SqliteQueries sqliteQueries, Connection con, ClientForm clientForm) {
		PreparedStatement preState = null;

		try {
			preState = con.prepareStatement(sqliteQueries.upsertClientTableQuery);

			// Set client attributes to query.
			preState.setString(1, clientForm.getClientId());
			preState.setString(2, clientForm.getName());
			preState.setString(3, clientForm.getSurname());
			preState.setString(4, clientForm.getStatus());

			// Execute query.
			preState.executeUpdate();

		} catch(SQLException e) {
			this.notifySQLException(e);

			return false;
		}

		return true;
	}

	public boolean insertReceiptDetailToReceiptTable (SqliteQueries sqliteQueries, Connection con, Receipt receipt) {
		PreparedStatement preState = null;

		try {
			preState = con.prepareStatement(sqliteQueries.insertNewReceiptQuery);

			// Set client attributes to query.
			preState.setString(1, receipt.getClientId() );
			preState.setString(2, receipt.getProductName()  );
			preState.setFloat(3, receipt.getPrice());

			// Execute query.
			preState.executeUpdate();

		} catch(SQLException e) {
			this.notifySQLException(e);

			return false;
		}

		return true;
	}
}
