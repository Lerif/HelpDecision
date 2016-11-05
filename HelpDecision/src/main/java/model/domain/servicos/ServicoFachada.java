package model.domain.servicos;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.Servidor;

public class ServicoFachada {

	private ArquivoLogServico servicoArquivoLog;
	@SuppressWarnings("unused")
	private ChamadaMetodoServico servicoChamadaMetodo;
	@SuppressWarnings("unused")
	private ServidorServico servicoServidor;

	public ServicoFachada() {
		setServicoArquivoLog(ArquivoLogServico.novo());
		setServicoChamadaMetodo(ChamadaMetodoServico.novo());
		setServicoServidor(ServidorServico.novo());
	}

	// MÉTODOS REFERENTE AO SERVICO CHAMADA METODO

	// MÉTODOS REFERENTE AO SERVICO ARQUIVO LOG
	public ArquivoLog solicitarCriacaoArquivoLog(String nomeArquivo, Date dataUpload, String descricao,
			int idServidor) {
		return servicoArquivoLog.criarArquivoLog(nomeArquivo, dataUpload, descricao, idServidor);
	}

	public List<ArquivoLog> solicitarTodosArquivoLogDB() throws SQLException {
		return servicoArquivoLog.solicitarListaDeArquivoLogCadastradoDB();
	}

	// MÉTODOS REFERENTE AO SERVICO SERVIDOR
	public Boolean cadastrarServidor(int idServidor, String nomeServidor) {
		return servicoServidor.cadastrarServidorDB(solicitarNovoServidor(idServidor, nomeServidor));
	}
	
	public Servidor solicitarNovoServidor(int idServidor, String nomeServidor){
		return servicoServidor.criarServidor(idServidor, nomeServidor);
	}

	public List<Servidor> solicitarTodosServidoresDB() throws SQLException {
		return servicoServidor.solicitarListaDeServidoresCadastradosDB();
	}
	// Getters e setters
	public void setServicoArquivoLog(ArquivoLogServico servicoArquivoLog) {
		this.servicoArquivoLog = servicoArquivoLog;
	}

	public void setServicoChamadaMetodo(ChamadaMetodoServico servicoChamadaMetodo) {
		this.servicoChamadaMetodo = servicoChamadaMetodo;
	}

	public void setServicoServidor(ServidorServico servicoServidor) {
		this.servicoServidor = servicoServidor;
	}

}
