package model.domain.servicos;

import java.sql.SQLException;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.Servidor;
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

	public ArquivoLog criarArquivoLog(int idArquivo, String nomeArquivo, java.sql.Date dataUpload, String descricao, Servidor servidor) {
		return FabricaArquivoLog.nova().novoArquivoLog(idArquivo, nomeArquivo, dataUpload, descricao, servidor);
	}
	
	public Boolean solicitarFlagArquivoExcluido(ArquivoLog arquivoLog){
		return repositorioArquivoLog.flegarArquivoExcluido(arquivoLog);
	}

	public List<ArquivoLog> solicitarListaDeArquivoLogEServidorCadastradoDB() throws SQLException {
		return repositorioArquivoLog.findAll();
	}
}
