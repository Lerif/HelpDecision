package controller.backbeans;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.LogDashboard;
import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

@ManagedBean(eager = true)
@RequestScoped
public class HomeBean {

	protected static final String NOME_DO_PROJETO = "HelpDecision";
	private ServicoFachada servicoFachada;
	private List<SelectItem> comboServidores;
	private Servidor servidorSelecionado;
	private List<LogDashboard> gerarLogDashboardInicial;

	
	public HomeBean() {
		servicoFachada = new ServicoFachada();
		
		gerarLogDashboardInicial = servicoFachada.gerarLogDashboardInicial();
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

	public Servidor getServidorSelecionado() {
		return servidorSelecionado;
	}

	public void setServidorSelecionado(Servidor servidorSelecionado) {
		this.servidorSelecionado = servidorSelecionado;
	}
	
	public List<ChamadaMetodo> getIntervaloDatasDuracoes(String nomeServidor,
			long duracaoInicio, long duracaoFim, Date dataInicio, Date dataFim) throws ParseException {

		return servicoFachada.filtrarPorTudo(nomeServidor, duracaoInicio, duracaoFim, dataInicio, dataFim);
	}

	public List<ChamadaMetodo> getChamadasMetodos() {
		try {
			return servicoFachada.buscarTodosMetodos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<ChamadaMetodo>();
	}
	
	public List<LogDashboard> getGerarLogDashboardInicial() {
		return gerarLogDashboardInicial;
	}
		
}
