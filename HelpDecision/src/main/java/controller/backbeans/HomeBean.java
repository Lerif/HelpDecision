package controller.backbeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Dashboard;
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
	private List<Dashboard> gerarLogDashboardInicial;
	private long rangeInicio;
	private long rangeFim;
	private Dashboard logDashBoard;
	private List<ChamadaMetodo> metodoDetails;
	private Date dateInicio;
	private Date dateFim;

	public HomeBean() {
		servicoFachada = new ServicoFachada();
		gerarLogDashboardInicial = servicoFachada.gerarLogDashboardInicial();
	}

	public void filtrar() throws SQLException {

		int servidorId;
		RequestContext requestContext = RequestContext.getCurrentInstance();

		if ((dateInicio == null) || (dateFim == null)) {
			requestContext.execute("alertDataFormatoInvalido()");
			return;
		}

		if (rangeInicio > rangeFim) {
			requestContext.execute("alertRageInvalido()");
			return;
		}

		if (dateInicio.after(dateFim)) {
			requestContext.execute("alertDataInvalido()");
			return;
		}

		try {
			servidorId = Integer.parseInt(servidorSelecionado);
		} catch (NumberFormatException e) {
			requestContext.execute("alertServidorNaoSelecionado()");
			return;
		}

		requestContext.execute("alertLetras");
		gerarLogDashboardInicial = servicoFachada.solicitarFiltroDashBoard(Integer.parseInt(servidorSelecionado),
				CalendarioUtil.dateParaSqlTimestamp(this.dateInicio), CalendarioUtil.dateParaSqlTimestamp(this.dateFim),
				rangeInicio, rangeFim);
		gerarLogDashboardInicial = servicoFachada.solicitarFiltroDashBoard(servidorId,
				CalendarioUtil.dateParaSqlTimestamp(this.dateInicio), CalendarioUtil.dateParaSqlTimestamp(this.dateFim),
				rangeInicio, rangeFim);
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

	public List<Dashboard> getGerarLogDashboardInicial() {
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

	public void setGerarLogDashboardInicial(List<Dashboard> gerarLogDashboardInicial) {
		this.gerarLogDashboardInicial = gerarLogDashboardInicial;
	}

	public Date getDateInicio() {
		return dateInicio;
	}

	public void setDateInicio(Date dateInicio) {
		this.dateInicio = dateInicio;
	}

	public Date getDateFim() {
		return dateFim;
	}

	public void setDateFim(Date dateFim) {
		this.dateFim = dateFim;
	}

	public void setLogDashBoard(Dashboard logDashBoard) {
		this.logDashBoard = logDashBoard;
	}
	
	public void setBuscarDashboardDetalhado(Dashboard logDashBoard){
		this.metodoDetails = servicoFachada.buscarDashboardDetalhado(logDashBoard.getNomeMetodo(),
				logDashBoard.getIdServidor(), this.dateInicio, this.dateFim, this.rangeInicio, this.rangeFim);
	}

	public List<ChamadaMetodo> getMetodoDetails() {
		return metodoDetails;

	}
}
