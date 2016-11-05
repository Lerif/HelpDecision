package model.domain.repositorios;

import java.sql.Connection;

public class RepositorioChamadaMetodoArquivoLog {

	Connection conexao; 
	
	public RepositorioChamadaMetodoArquivoLog() {
		this.conexao = new ConexaoDB().conectarDB();
	}

}
