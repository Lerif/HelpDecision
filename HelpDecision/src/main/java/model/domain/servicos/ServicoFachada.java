package model.domain.servicos;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Dashboard;
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

	public boolean inserirChamadaMetodoList(List<ChamadaMetodo> listaChamadaMetodo) {
		return servicoChamadaMetodo.persistirChamadaMetodoList(listaChamadaMetodo);
	}

	public List<Dashboard> solicitarFiltroDashBoard(int servidor, Timestamp dataInicio, Timestamp dataFim,
			long duracaoInicial, long duracaoFinal) throws SQLException {
		return servicoDashboard.filtrarDashboard(servidor, dataInicio, dataFim, duracaoInicial, duracaoFinal);
	}

	public List<ChamadaMetodo> buscarPorDuracao(long inicio, long fim) {
		return servicoChamadaMetodo.buscarPorDuracao(inicio, fim);
	}

	public List<ChamadaMetodo> buscarPorData(Date inicio, Date fim) {
		return servicoChamadaMetodo.buscarPorData(inicio, fim);
	}

	public List<ChamadaMetodo> buscarPorServidor(String nomeDoServidor) {
		return servicoChamadaMetodo.buscarPorServido(nomeDoServidor);
	}

	public Boolean solicitarFlagDeArquivoDeletado(ArquivoLog arquivoLog) {
		return servicoArquivoLog.solicitarFlagArquivoExcluido(arquivoLog);
	}

	public List<ArquivoLog> solicitarTodosArquivoLogDB() throws SQLException {
		return servicoArquivoLog.solicitarListaDeArquivoLogCadastradoDB();
	}

	public Boolean cadastrarServidor(int idServidor, String nomeServidor) throws SQLException {
		return servicoServidor.cadastrarServidorDB(solicitarNovoServidor(idServidor, nomeServidor));
	}

	public Boolean imprimeSeServidorFoiCadastrado(Servidor servidor) {
		try {
			return servicoServidor.verificarServidorExiste(servidor);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
	
	public List<ArquivoLog> solicitarTodosArquivoLogEServidoresDB() throws SQLException {
		return servicoAgregador.solicitarListaDeArquivoLogEServidorCadastradoDB();
	}

	public List<File> extrairArquivosTarGz(File arquivoTarGz, File localDestino) {

		try {
			return servicoDescompactador.extrairTarGz(arquivoTarGz, localDestino);
		} catch (IOException | ArchiveException e) {
			e.printStackTrace();
		}
		return new ArrayList<File>();
	}

	public List<Dashboard> gerarLogDashboardInicial() {
		return servicoDashboard.gerarLogDashboardInicial();
	}

	public List<ChamadaMetodo> lerArquivoLog(ArquivoLog arquivo) {
		try {
			return leitor.ler(arquivo);			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return new ArrayList<ChamadaMetodo>();
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

	public List<ChamadaMetodo> buscarDashboardDetalhado(String nomeMetodo, int idServidor,
			Date dateInicio, Date dateFim, long rangeInicio, long rangeFim) {

		try {
			
			List<ChamadaMetodo> teste = servicoAgregador.solicitarDetalhesFromMetodoAndServidor(nomeMetodo, idServidor, dateInicio, dateFim,
					rangeInicio, rangeFim);
			
			return teste;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}

	public ArquivoLog inserirArquivoLog(ArquivoLog arquivoLog) {
		return this.servicoArquivoLog.inserirDadosNaTbArquivo(arquivoLog);		
	}

}
