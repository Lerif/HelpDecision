package model.domain.repositorios;

import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.domain.entidades.ArquivoLog;

public class RepositorioArquivoLog {

	Connection conexao;
	
	public RepositorioArquivoLog() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<ArquivoLog> findAll() {
		// SELECT * FROM tb_arquivo
		return null;
	}
}
