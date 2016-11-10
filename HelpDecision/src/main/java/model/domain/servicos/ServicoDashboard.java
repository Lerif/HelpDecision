package model.domain.servicos;

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
}
