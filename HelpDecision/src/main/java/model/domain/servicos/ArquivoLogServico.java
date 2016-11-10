package model.domain.servicos;

import java.sql.SQLException;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.fabricas.FabricaArquivoLog;
import model.domain.repositorios.RepositorioArquivoLog;

public class ArquivoLogServico {

	private RepositorioArquivoLog repositorioArquivoLog = new RepositorioArquivoLog();

	public ArquivoLogServico() {

	}

	public static ArquivoLogServico novo() {
		return new ArquivoLogServico();
	}

	public ArquivoLog inserirDadosNaTbArquivo(ArquivoLog arquivoLog) {
		return repositorioArquivoLog.insert(arquivoLog);
	}

	public ArquivoLog criarArquivoLog(int idArquivo, String nomeArquivo, java.sql.Date dataUpload, String descricao) {
		return FabricaArquivoLog.nova().novoArquivoLog(idArquivo, nomeArquivo, dataUpload, descricao);
	}

	public List<ArquivoLog> solicitarListaDeArquivoLogCadastradoDB() throws SQLException {
		return repositorioArquivoLog.findAll();
	}
	
	public Boolean solicitarFlagArquivoExcluido(ArquivoLog arquivoLog){
		return repositorioArquivoLog.flegarArquivoExcluido(arquivoLog);
	}

}
