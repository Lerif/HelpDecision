package model.domain.repositorios;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoDB {

	public Connection conectarDB() {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection("jdbc:postgresql://localhost/helpdecisiondb", "postgres", "postgres");
		} catch (Exception error) {
			return null;
		}
	}
}
