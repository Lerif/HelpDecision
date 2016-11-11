package model.domain.repositorios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.domain.entidades.LogDashboard;
import model.domain.fabricas.FabricaDashboard;

public class RepositorioLogDashboard {

	private List<LogDashboard> repositorioLogDashboard = new ArrayList<LogDashboard>();
	private int totalChamadas;
	static final String TABELA_CHAMADA_METODO = "tb_chamada_metodo";

	Connection conexao;
	
	public RepositorioLogDashboard(){
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<LogDashboard> buscarDashboardDoBanco() {
		String sql = "select chamada_metodo.nome_metodo, count(*), "
				+ "max(chamada_metodo.duracao), min(chamada_metodo.duracao), avg(chamada_metodo.duracao) from "
				+ TABELA_CHAMADA_METODO + " chamada_metodo group by 1";
		
		sql = "select nome_metodo, count(*) as quantidade_chamada, sum(duracao) as tempo_total, "
				+ "avg(duracao) as tempo_medio, max(duracao) as tempo_maior, min(duracao) as tempo_menor "
				+ "from " + TABELA_CHAMADA_METODO  +" group by 1 "
				+ "order by tempo_maior desc";
		
		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				repositorioLogDashboard.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("quantidade_chamada"), 0, retornoSelect.getLong("tempo_total"),
						retornoSelect.getFloat("tempo_medio"), retornoSelect.getLong("tempo_menor"),
						retornoSelect.getLong("tempo_maior"), 1));
				totalChamadas += retornoSelect.getInt("quantidade_chamada");
			}

			for (LogDashboard ld : repositorioLogDashboard){
				ld.setQuantidadeChamadasTotal(totalChamadas);
				ld.setPorcentagemTotal(( (ld.getQuantidadeDessaChamada()*100.0f) / totalChamadas));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return repositorioLogDashboard;
	}

	public int getTotalChamadas() {
		return totalChamadas;
	}

	public void setTotalChamadas(int totalChamadas) {
		this.totalChamadas = totalChamadas;
	}
}
