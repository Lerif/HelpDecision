package model.domain.servicos;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.fabricas.FabricaArquivoLog;
import model.domain.repositorios.RepositorioArquivoLog;

public class ArquivoLogServico {

	private RepositorioArquivoLog repositorioArquivoLog;
	
	private ArquivoLogServico() {
		repositorioArquivoLog = new RepositorioArquivoLog();
	}
	
	public static ArquivoLogServico novo(){
		return new ArquivoLogServico();
	}
	
	public ArquivoLog criarArquivoLog(String nomeArquivo, Date dataUpload, String descricao, int idServidor){
		return FabricaArquivoLog.nova().novoArquivoLog(nomeArquivo, dataUpload, descricao, idServidor);
	}
	
	public List<ArquivoLog> solicitarListaDeArquivoLogCadastradoDB() throws SQLException{
		return repositorioArquivoLog.findAll();
	}
	
}
