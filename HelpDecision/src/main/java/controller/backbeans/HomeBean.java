package controller.backbeans;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

@ManagedBean(eager = true)
@RequestScoped
public class HomeBean {

	protected static final String NOME_DO_PROJETO = "HelpDecision";
	private ServicoFachada servicoFachada;
	private List<SelectItem> comboServidores;
	private Servidor servidorSelecionado;
	private String intervaloDatas;
	private String duracao;
	
	public HomeBean() {
		servicoFachada = new ServicoFachada();
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
	

	
	//retorno?
	public List<ChamadaMetodo> getIntervaloDeDatasDuracoes() throws ParseException {
//		String [] datasInicioFim = getIntervaloDatas().split(" - ");
//		String [] duracoes = getDuracao().split(" - ");
//		
//		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//		Date dataInicio = df.parse(datasInicioFim[0]);
//		Date dataFim = df.parse(datasInicioFim[1]);
//		servicoFachada.buscarPorData(dataInicio, dataFim);
//		
//		Long duracaoInicio = Long.parseLong(duracoes[0]);
//		Long duracaoFim = Long.parseLong(duracoes[1]);
//		servicoFachada.buscarPorDuracao(duracaoInicio, duracaoFim);
		return null;
		
	}
	
	//TODO método retornará para função JSF ondenar tabela
	public void filtar() {
		
	}
	
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getDuracao() {
		return duracao;
	}
	
	public String getIntervaloDatas() {
		return intervaloDatas;
	}

	public void setIntervaloDatas(String intervaloDatas) {
		this.intervaloDatas = intervaloDatas;
	}
	
}
