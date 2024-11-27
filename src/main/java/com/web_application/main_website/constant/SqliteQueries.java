package com.web_application.main_website.constant;

public class SqliteQueries {
        public String createClientsTableQuery = """
            CREATE TABLE IF NOT EXISTS clients 
            (
	            id INTEGER PRIMARY KEY AUTOINCREMENT,
	            client_id TEXT NOT NULL,
	            name TEXT NOT NULL,
	            surname TEXT NOT NULL,
	            lastest_time_update TIMESTAMP NOT NULL,
	            status TEXT,
	            UNIQUE(client_id),
	            UNIQUE(name, surname),
                UNIQUE(client_id, name, surname)
            )
            """;

        public String createReceiptsTableQuery = """
            CREATE TABLE IF NOT EXISTS receipt
            (
	            id INTEGER PRIMARY KEY AUTOINCREMENT,
	            client_id TEXT NOT NULL,
	            product_name TEXT NOT NULL,
                price DECIMAL(10, 2),
	            reciept_time TIMESTAMP NOT NULL
            )
            """;

        public String selectToGetTotalPayment = """
            SELECT SUM(price) total_payment
            FROM receipt
            
            WHERE client_id = ?
            """;

        public String selectClientSpecifiedId = """
            SELECT *
            FROM clients
                
            WHERE client_id = ?
            """;

        public String selectTotalReceipt = """
            SELECT COUNT(*) total_receipts
            FROM receipt
                    
            WHERE client_id = ?
            """;

        public String upsertClientTableQuery = """
            INSERT INTO clients(client_id, name, surname, lastest_time_update, status)
            VALUES (?, ?, ?, datetime(CURRENT_TIMESTAMP, 'localtime'), ?)
            ON CONFLICT(client_id, name, surname) DO UPDATE
            SET lastest_time_update = datetime(CURRENT_TIMESTAMP, 'localtime')
            """;

        public String insertNewReceiptQuery = """
            INSERT INTO receipt(client_id ,product_name ,price ,reciept_time)
            VALUES (?, ?, ?, datetime(CURRENT_TIMESTAMP, 'localtime'))
            """;
}
