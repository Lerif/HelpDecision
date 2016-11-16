package controller.backbeans;

import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import model.domain.entidades.LogDashboard;
import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;
import model.domain.util.CalendarioUtil;

@ManagedBean(eager = true)
@RequestScoped
public class HomeBean {

	protected static final String NOME_DO_PROJETO = "HelpDecision";
	private ServicoFachada servicoFachada;
	private List<SelectItem> comboServidores;
	private String servidorSelecionado;
	private List<LogDashboard> gerarLogDashboardInicial;
	private long rangeInicio;
	private long rangeFim;
	private Date dataInicio;
	private Date dataFim;

	public HomeBean() {
		servicoFachada = new ServicoFachada();
		gerarLogDashboardInicial = servicoFachada.gerarLogDashboardInicial();
	}

	public void filtrar() throws SQLException {
		
		int servidorId;
		
		System.out.println("data Inicio: " + dataInicio);
		System.out.println("data Fim: " + dataFim);
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		
		try{
			servidorId = Integer.parseInt(servidorSelecionado);
		}catch(NumberFormatException e){
			requestContext.execute("alertServidorNaoSelecionado()");
			return;
		}
		
		if((dataInicio == null) || (dataFim == null)){
			requestContext.execute("alertDataFormatoInvalido()");
		}
		
		requestContext.execute("alertLetras");
		//gerarLogDashboardInicial = servicoFachada.solicitarFiltroDashBoard(servidorId/*, Timestamp.valueOf(dataInicio), dataFim*/, rangeInicio, rangeFim);
		gerarLogDashboardInicial = servicoFachada.solicitarFiltroDashBoard(servidorId, CalendarioUtil.dateParaSqlTimestamp(dataInicio), CalendarioUtil.dateParaSqlTimestamp(dataFim), rangeInicio, rangeFim);
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setGerarLogDashboardInicial(List<LogDashboard> gerarLogDashboardInicial) {
		this.gerarLogDashboardInicial = gerarLogDashboardInicial;
	}
	
	
}
