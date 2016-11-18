
package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.domain.entidades.Dashboard;
import model.domain.fabricas.FabricaDashboard;

public class RepositorioDashboard {
	private int totalChamadas;
	static final String TABELA_CHAMADA_METODO = "tb_chamada_metodo";

	Connection conexao;

	public RepositorioDashboard() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<Dashboard> buscarDashboardDoBanco() {
		List<Dashboard> repositorioLogDashboard = new ArrayList<Dashboard>();
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		// final String sql = "select nome_metodo, count(*) as
		// quantidade_chamada, sum(duracao) as tempo_total, "
		// + "avg(duracao) as tempo_medio, max(duracao) as tempo_maior,
		// min(duracao) as tempo_menor, ser.id_servidor, "
		// + "ser.nome_servidor from tb_chamada_metodo met join
		// tb_chamada_metodo_arquivo_servidor mas on "
		// + "met.id_chamada_metodo = mas.id_chamada_metodo "
		// + "join tb_servidor ser on ser.id_servidor = mas.id_servidor "
		// + "join tb_arquivo ar on mas.id_arquivo = ar.id_arquivo " + "where
		// (ar.arquivo_excluido != true) "
		// + "group by 1, 7 order by tempo_maior desc";

		final String sql = "select nome_metodo, count(*) as quantidade_chamada, sum(duracao) as tempo_total, "
				+ "avg(duracao) as tempo_medio, max(duracao) as tempo_maior, min(duracao) as tempo_menor, "
				+ "ser.nome_servidor from tb_chamada_metodo "
				+ "met join tb_arquivo ar on met.id_arquivo = ar.id_arquivo join "
				+ "tb_servidor ser on ser.id_servidor = ar.id_servidor "
				+ "where (ar.arquivo_excluido != true) group by 1, 7 order by tempo_maior desc";

		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				repositorioLogDashboard.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("quantidade_chamada"), (float) 0, retornoSelect.getLong("tempo_total"),
						retornoSelect.getFloat("tempo_medio"), retornoSelect.getLong("tempo_menor"),
						retornoSelect.getLong("tempo_maior"), 1, retornoSelect.getString("nome_servidor")));
				
				
				totalChamadas += retornoSelect.getInt("quantidade_chamada");

				if (hm.containsKey(retornoSelect.getString("nome_servidor")))
					hm.put(retornoSelect.getString("nome_servidor"), hm.get(retornoSelect.getString("nome_servidor"))
							+ retornoSelect.getInt("quantidade_chamada"));
				else
					hm.put(retornoSelect.getString("nome_servidor"), retornoSelect.getInt("quantidade_chamada"));
			}

			for (Dashboard ld : repositorioLogDashboard) {
				ld.setQuantidadeChamadasTotal(hm.get(ld.getNomeServidor()));
				System.out.println("valor antigo: " + totalChamadas);
				ld.setPorcentagemTotal(((ld.getQuantidadeDessaChamada() * 100.0f) / ld.getQuantidadeChamadasTotal()));
			}

		} catch (Exception e) {
		}
		return repositorioLogDashboard;
	}

	public List<Dashboard> filtrarPorTudo(int servidor, Timestamp dataInicio, Timestamp dataFim, long duracaoInicio,
			long duracaoFim) throws SQLException {
		List<Dashboard> resultado = new ArrayList<Dashboard>();
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT nome_metodo, count(*) AS quantidade_chamada, ");
		sql.append("SUM(duracao) AS tempo_total, ");
		sql.append("AVG(duracao) AS tempo_medio, ");
		sql.append("MAX(duracao) AS tempo_maior, ");
		sql.append("MIN(duracao) AS tempo_menor, ");
		sql.append("ser.id_servidor, ");
		sql.append("ser.nome_servidor ");
		sql.append("FROM tb_chamada_metodo AS met ");
		sql.append("JOIN tb_arquivo AS ar ON met.id_arquivo = ar.id_arquivo  ");
		sql.append("JOIN tb_servidor AS ser ON ser.id_servidor = ar.id_servidor WHERE (ar.arquivo_excluido != TRUE) ");
		sql.append("AND ser.id_servidor = ? ");
		sql.append("AND (met.duracao >= ? AND met.duracao <= ?)");
		sql.append("AND (met.data_inicio, met.data_fim) OVERLAPS (?,?)  ");
		sql.append("GROUP BY 1, 7 ");
		sql.append("ORDER BY tempo_maior DESC");

		PreparedStatement preparedStatement = conexao.prepareStatement(sql.toString());
		preparedStatement.setInt(1, servidor);
		preparedStatement.setLong(2, duracaoInicio);
		preparedStatement.setLong(3, duracaoFim);
		preparedStatement.setTimestamp(4, dataInicio);
		preparedStatement.setTimestamp(5, dataFim);

		try {
			ResultSet retornoSelect = preparedStatement.executeQuery();
			while (retornoSelect.next()) {
				resultado.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("quantidade_chamada"), 0, retornoSelect.getLong("tempo_total"),
						retornoSelect.getFloat("tempo_medio"), retornoSelect.getLong("tempo_menor"),
						retornoSelect.getLong("tempo_maior"), 1, retornoSelect.getString("nome_servidor")));
				totalChamadas += retornoSelect.getInt("quantidade_chamada");

				if (hm.containsKey(retornoSelect.getString("nome_servidor")))
					hm.put(retornoSelect.getString("nome_servidor"), hm.get(retornoSelect.getString("nome_servidor"))
							+ retornoSelect.getInt("quantidade_chamada"));
				else
					hm.put(retornoSelect.getString("nome_servidor"), retornoSelect.getInt("quantidade_chamada"));

			}

			for (Dashboard ld : resultado) {
				ld.setQuantidadeChamadasTotal(hm.get(ld.getNomeServidor()));
				ld.setPorcentagemTotal(((ld.getQuantidadeDessaChamada() * 100.0f) / ld.getQuantidadeChamadasTotal()));
			}

		} catch (Exception e) {
		}
		return resultado;
	}

	public int getTotalChamadas() {
		return totalChamadas;
	}

	public void setTotalChamadas(int totalChamadas) {
		this.totalChamadas = totalChamadas;
	}
}
