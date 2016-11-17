package model.domain.servicos;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import model.domain.entidades.Dashboard;
import model.domain.repositorios.RepositorioDashboard;

public class ServicoDashboard {
	
	private RepositorioDashboard repositorioLogDashboard = new RepositorioDashboard();
	
	private ServicoDashboard(){
	}
	
	public static ServicoDashboard novo(){
		return new ServicoDashboard();
	}
	
	public List<Dashboard> gerarLogDashboardInicial(){		
		return repositorioLogDashboard.buscarDashboardDoBanco();
	}
	
	public List<Dashboard> filtrarDashboard(int servidor, Timestamp dataInicio, Timestamp dataFim, long duracaoInicial, long duracaoFinal) throws SQLException{
		return repositorioLogDashboard.filtrarPorTudo(servidor, dataInicio, dataFim, duracaoInicial, duracaoFinal);
	}
}
