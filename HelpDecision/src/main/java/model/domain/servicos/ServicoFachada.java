package model.domain.servicos;

import java.sql.SQLException;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;

public class ServicoFachada {

	private ArquivoLogServico servicoArquivoLog = new ArquivoLogServico();
	private ChamadaMetodoServico servicoChamadaMetodo = new ChamadaMetodoServico();
	private ServidorServico servicoServidor = new ServidorServico();
	private ChamadaMetodoArquivoLogServidorServico servicoAgregador = new ChamadaMetodoArquivoLogServidorServico();

	public ServicoFachada() {

	}

	public Boolean inserirNovoArquivo(List<ChamadaMetodo> listaChamadaMetodo, ArquivoLog arquivoLog, Servidor servidor)
			throws SQLException {
		return servicoAgregador.inserirAgregador(servicoAgregador.agregar(
				servicoChamadaMetodo.inserirDadosNaTbChamadaMetodo(listaChamadaMetodo),
				servicoArquivoLog.inserirDadosNaTbArquivo(arquivoLog), servicoServidor.recuperarIdServidor(servidor)));
	}

	public ArquivoLog solicitarCriacaoArquivoLog(int idArquivo, String nomeArquivo, java.sql.Date dataUpload,
			String descricao) {
		return servicoArquivoLog.criarArquivoLog(idArquivo, nomeArquivo, dataUpload, descricao);
	}

	public List<ArquivoLog> solicitarTodosArquivoLogDB() throws SQLException {
		return servicoArquivoLog.solicitarListaDeArquivoLogCadastradoDB();
	}

	public Boolean cadastrarServidor(int idServidor, String nomeServidor) {
		return servicoServidor.cadastrarServidorDB(solicitarNovoServidor(idServidor, nomeServidor));
	}

	public void solicitarRemocaoEmCascataDoAgragadorPorArquivoLog(ArquivoLog arquivoLog) throws SQLException {
		this.servicoAgregador.removerAgregadorEmCascataByArquivoLogId(arquivoLog.getIdArquivo());
	}

	public Servidor solicitarNovoServidor(int idServidor, String nomeServidor) {
		return servicoServidor.criarServidor(idServidor, nomeServidor);
	}

	public List<Servidor> solicitarTodosServidoresDB() throws SQLException {
		return servicoServidor.solicitarListaDeServidoresCadastradosDB();
	}

	public void setServicoArquivoLog(ArquivoLogServico servicoArquivoLog) {
		this.servicoArquivoLog = servicoArquivoLog;
	}

	public void setServicoChamadaMetodo(ChamadaMetodoServico servicoChamadaMetodo) {
		this.servicoChamadaMetodo = servicoChamadaMetodo;
	}

	public void setServicoServidor(ServidorServico servicoServidor) {
		this.servicoServidor = servicoServidor;
	}

	public void setServicoAgregador(ChamadaMetodoArquivoLogServidorServico servicoAgregador) {
		this.servicoAgregador = servicoAgregador;
	}

}
