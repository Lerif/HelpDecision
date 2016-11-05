package model.domain.repositorios;

import java.sql.Connection;

public class RepositorioChamadaMetodoArquivoLogServidor {

	Connection conexao; 
	
	public RepositorioChamadaMetodoArquivoLogServidor() {
		this.conexao = new ConexaoDB().conectarDB();
	}

}
