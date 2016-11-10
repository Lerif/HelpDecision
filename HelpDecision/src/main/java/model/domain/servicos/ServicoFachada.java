package model.domain.servicos;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.LogDashboard;
import model.domain.entidades.Servidor;

public class ServicoFachada {

	private ArquivoLogServico servicoArquivoLog = new ArquivoLogServico();
	private ChamadaMetodoServico servicoChamadaMetodo = new ChamadaMetodoServico();
	private ServidorServico servicoServidor = new ServidorServico();
	private ChamadaMetodoArquivoLogServidorServico servicoAgregador = new ChamadaMetodoArquivoLogServidorServico();
	private ServicoDescompactador servicoDescompactador = ServicoDescompactador.novo();
	private ServicoDashboard servicoDashboard = ServicoDashboard.novo();
	private Leitor leitor = new Leitor();


	public ServicoFachada() {

	}

	public Boolean inserirNovoArquivo(List<ChamadaMetodo> listaChamadaMetodo, ArquivoLog arquivoLog, Servidor servidor)
			throws SQLException {
		// Faz insert na tb_chamada_metodo
		// Faz insert na tb_arquivo
		// Recuperar o id_servidor a partir do seu nome
		// Agrega listChamadaMetodo, ArquivoLog e Servidor
		return servicoAgregador.inserirAgregador(servicoAgregador.agregar(
				servicoChamadaMetodo.inserirDadosNaTbChamadaMetodo(listaChamadaMetodo),
				servicoArquivoLog.inserirDadosNaTbArquivo(arquivoLog), servicoServidor.recuperarIdServidor(servidor)));
	}
	
	public Boolean inserirNovoArquivo(List<ChamadaMetodo> listaChamadaMetodo, ArquivoLog arquivoLog, int idSservidor)
			throws SQLException {
		// Faz insert na tb_chamada_metodo
		// Faz insert na tb_arquivo
		// Recuperar o id_servidor a partir do seu nome
		// Agrega listChamadaMetodo, ArquivoLog e Servidor
		return servicoAgregador.inserirAgregador(servicoAgregador.agregar(
				servicoChamadaMetodo.inserirDadosNaTbChamadaMetodo(listaChamadaMetodo),
				servicoArquivoLog.inserirDadosNaTbArquivo(arquivoLog), servicoServidor.buscarPorId(idSservidor)));
	}

	// M�TODOS REFERENTE AO SERVICO CHAMADA METODO
	
	public List<ChamadaMetodo> buscarPorDuracao(long inicio, long fim){
		return servicoChamadaMetodo.buscarPorDuracao(inicio, fim);
	}
	
	public List<ChamadaMetodo> buscarPorData(Date inicio, Date fim){
		return servicoChamadaMetodo.buscarPorData(inicio, fim);
	}
	
	public List<ChamadaMetodo> buscarPorServidor(String nomeDoServidor){
		return servicoChamadaMetodo.buscarPorServido(nomeDoServidor);
	}
	
	public List<ChamadaMetodo> buscarTodosMetodos() throws SQLException{
		return servicoChamadaMetodo.buscarTodosMetodos();
	}

	// M�TODOS REFERENTE AO SERVICO ARQUIVO LOG
	public ArquivoLog solicitarCriacaoArquivoLog(int idArquivo, String nomeArquivo, java.sql.Date dataUpload,
			String descricao) {
		return servicoArquivoLog.criarArquivoLog(idArquivo, nomeArquivo, dataUpload, descricao);
	}

	public List<ArquivoLog> solicitarTodosArquivoLogDB() throws SQLException {
		return servicoArquivoLog.solicitarListaDeArquivoLogCadastradoDB();
	}

	// M�TODOS REFERENTE AO SERVICO SERVIDOR
	public Boolean cadastrarServidor(int idServidor, String nomeServidor) throws SQLException {
		return servicoServidor.cadastrarServidorDB(solicitarNovoServidor(idServidor, nomeServidor));
	}

	public Servidor solicitarNovoServidor(int idServidor, String nomeServidor) {
		return servicoServidor.criarServidor(idServidor, nomeServidor);
	}

	public List<Servidor> solicitarTodosServidoresDB() throws SQLException {
		return servicoServidor.solicitarListaDeServidoresCadastradosDB();
	}
	
	// METODOS REFERENTE AO AGREGADOR
	public List<ChamadaMetodo> filtrarPorTudo(String nomeServidor, long duracaoInicio, long duracaoFim, Date dataInicio,
			Date dataFim){
		return servicoAgregador.filtrarPorTudo(nomeServidor, duracaoInicio, duracaoFim, dataInicio, dataFim);
	}
	
	// METODOS REFERENTE AO SERVICO DESCOMPACTADOR
	public List<File> extrairTarGz (File arquivoTarGz, File localDestino){
		
		try {
			return servicoDescompactador.extrairTarGz(arquivoTarGz, localDestino);
		} catch (IOException | ArchiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<File>();
	}
	
	// METODO REFERENTE AO LEITOR
	public List<ChamadaMetodo> lerArquivoLog(String arquivo){
		try {
			return leitor.ler(arquivo);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<ChamadaMetodo>();
	}
	
	// METODO REFERENTE AO SERVICO DASHBOARD
	public List<LogDashboard> gerarLogDashboardInicial (){
		return servicoDashboard.gerarLogDashboardInicial();
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

	public void setServicoAgregador(ChamadaMetodoArquivoLogServidorServico servicoAgregador) {
		this.servicoAgregador = servicoAgregador;
	}

}
