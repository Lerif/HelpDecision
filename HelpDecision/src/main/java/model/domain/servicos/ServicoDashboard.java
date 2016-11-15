package model.domain.servicos;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import model.domain.entidades.LogDashboard;
import model.domain.repositorios.RepositorioLogDashboard;

public class ServicoDashboard {
	
	private RepositorioLogDashboard repositorioLogDashboard = new RepositorioLogDashboard();
	
	private ServicoDashboard(){
	}
	
	public static ServicoDashboard novo(){
		return new ServicoDashboard();
	}
	
	public List<LogDashboard> gerarLogDashboardInicial(){		
		return repositorioLogDashboard.buscarDashboardDoBanco();
	}
	
	public List<LogDashboard> filtrarDashboard(int servidor, Timestamp dataInicio, Timestamp dataFim, long duracaoInicial, long duracaoFinal) throws SQLException{
		return repositorioLogDashboard.filtrarPorTudo(servidor, dataInicio, dataFim, duracaoInicial, duracaoFinal);
	}
}
