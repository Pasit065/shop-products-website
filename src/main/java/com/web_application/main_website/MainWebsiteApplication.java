package com.web_application.main_website;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.web_application.main_website.constant.SqliteQueries;

@SpringBootApplication
public class MainWebsiteApplication {

	public static void main(String[] args) {
		Connection con;
		DatabaseRepository databaseRepository;
		SqliteQueries sqliteQueries;
		String[] createTableQueryList;
		Statement currentState = null;

		databaseRepository = new DatabaseRepository();
		con = databaseRepository.connectDatabase();
		sqliteQueries = new SqliteQueries();

		try {
			currentState = con.createStatement();
		} catch (SQLException e) {
			databaseRepository.notifySQLException(e);
		}

		createTableQueryList = new String[]{sqliteQueries.createClientsTableQuery, sqliteQueries.createReceiptsTableQuery};
		
		for (String createTableQuery: createTableQueryList) {
			databaseRepository.createdTableIfNotExists(currentState, createTableQuery);
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch(SQLException e) {
			databaseRepository.notifySQLException(e);
		}
		SpringApplication.run(MainWebsiteApplication.class, args);
	}

}
