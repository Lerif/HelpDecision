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
		repositorioLogDashboard = new ArrayList<LogDashboard>();
		totalChamadas = 0;
		String sql = "select chamada_metodo.nome_metodo, count(*), "
				+ "max(chamada_metodo.duracao), min(chamada_metodo.duracao), avg(chamada_metodo.duracao) from "
				+ TABELA_CHAMADA_METODO + " chamada_metodo group by 1";
		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				//FabricaDashboard.nova();
				repositorioLogDashboard.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("count"), 0, 0, retornoSelect.getFloat("avg"),
						retornoSelect.getLong("min"), retornoSelect.getLong("max"), 1));
				totalChamadas += retornoSelect.getInt("count");
			}

			System.out.println("totalChamadas: " + totalChamadas);
			for (LogDashboard ld : repositorioLogDashboard){
				ld.setQuantidadeChamadasTotal(totalChamadas);
				ld.calcularPorcentagemTotal();
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
