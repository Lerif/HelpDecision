package controller.backbeans;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import model.domain.entidades.LogDashboard;
import model.domain.entidades.LogDashboardDetalhado;
import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

@ManagedBean(eager = true)
@RequestScoped
public class HomeBean {

	protected static final String NOME_DO_PROJETO = "HelpDecision";
	private ServicoFachada servicoFachada;
	private List<SelectItem> comboServidores;
	private String servidorSelecionado;
	private List<LogDashboard> gerarLogDashboardInicial;
	private List<LogDashboardDetalhado> gerarLogDashboardDetalhado;
	private long rangeInicio;
	private long rangeFim;


	public HomeBean() {
		servicoFachada = new ServicoFachada();

		gerarLogDashboardInicial = servicoFachada.gerarLogDashboardInicial();
	}

	public void filtrar() throws SQLException {
		
		gerarLogDashboardInicial = servicoFachada.solicitarFiltroDashBoard(Integer.parseInt(servidorSelecionado)/*, Timestamp.valueOf(dataInicio), dataFim*/, rangeInicio, rangeFim);
		
	
		
		
		
		
//		Matcher matcher;
//		Pattern naoNumero = Pat	tern.compile(Regex.NAONUMERO.valor());
//		if (naoNumero.matcher(String.valueOf(getRangeInicio())).find()
//				&& naoNumero.matcher(String.valueOf(getRangeFim())).find()) {
//
//		} else {
//			System.out.print("DataInicio: " + this.dataInicio);
//			System.out.println("	DataFim: " + this.dataFim);
//			System.out.print("rangeInicio: " + this.rangeInicio);
//			System.out.print("	rangeFim: " + this.rangeFim);
//		}
//		System.out.println(String.valueOf(getRangeInicio()));
//		System.out.println(String.valueOf(getRangeFim()));
	}

	public void dashBoardModal(ActionEvent event){
		System.out.println("Teste");
	}
	
	public List<SelectItem> getComboServidores() throws SQLException {
		this.comboServidores = new ArrayList<SelectItem>();
		List<Servidor> servidores = null;
		try {
			servidores = servicoFachada.solicitarTodosServidoresDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Servidor servidor : servidores) {
			SelectItem item = new SelectItem(servidor.getIdServidor(), servidor.getNomeServidor());
			this.comboServidores.add(item);

		}
		return comboServidores;
	}

	public String getServidorSelecionado() {
		return servidorSelecionado;
	}

	public void setServidorSelecionado(String servidorSelecionado) {
		this.servidorSelecionado = servidorSelecionado;
	}

//	public List<ChamadaMetodo> getIntervaloDatasDuracoes(String nomeServidor, long duracaoInicio, long duracaoFim,
//			Date dataInicio, Date dataFim) throws ParseException {
//
//		return servicoFachada.filtrarPorTudo(nomeServidor, duracaoInicio, duracaoFim, dataInicio, dataFim);
//	}

	public List<LogDashboard> getGerarLogDashboardInicial() {
		return gerarLogDashboardInicial;
	}
	
	public List<LogDashboardDetalhado> getGerarLogDashboardDetalhadoModal() {
		return gerarLogDashboardDetalhado;
	}

	public long getRangeInicio() {
		return rangeInicio;
	}

	public void setRangeInicio(long rangeInicio) {
		this.rangeInicio = rangeInicio;
	}

	public long getRangeFim() {
		return rangeFim;
	}

	public void setRangeFim(long rangeFim) {
		this.rangeFim = rangeFim;
	}

//	public String getDataInicio() {
//		return dataInicio;
//	}
//
//	public void setDataInicio(String dataInicio) {
//		this.dataInicio = dataInicio;
//	}
//
//	public String getDataFim() {
//		return dataFim;
//	}
//
//	public void setDataFim(String dataFim) {
//		this.dataFim = dataFim;
//	}

	public void setGerarLogDashboardInicial(List<LogDashboard> gerarLogDashboardInicial) {
		this.gerarLogDashboardInicial = gerarLogDashboardInicial;
	}
	
	
}
