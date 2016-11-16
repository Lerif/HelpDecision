package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.domain.entidades.LogDashboard;
import model.domain.fabricas.FabricaDashboard;

public class RepositorioLogDashboard {

	private List<LogDashboard> repositorioLogDashboard = new ArrayList<LogDashboard>();
	private int totalChamadas;
	static final String TABELA_CHAMADA_METODO = "tb_chamada_metodo";

	Connection conexao;

	public RepositorioLogDashboard() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<LogDashboard> buscarDashboardDoBanco() {

		final String sql = "select nome_metodo, count(*) as quantidade_chamada, sum(duracao) as tempo_total, "
				+ "avg(duracao) as tempo_medio, max(duracao) as tempo_maior, min(duracao) as tempo_menor, ser.id_servidor, "
				+ "ser.nome_servidor from tb_chamada_metodo met join tb_chamada_metodo_arquivo_servidor mas on "
				+ "met.id_chamada_metodo = mas.id_chamada_metodo "
				+ "join tb_servidor ser on ser.id_servidor = mas.id_servidor "
				+ "join tb_arquivo ar on mas.id_arquivo = ar.id_arquivo " + "where (ar.arquivo_excluido != true) "
				+ "group by 1, 7 order by tempo_maior desc";

		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				repositorioLogDashboard.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("quantidade_chamada"), 0, retornoSelect.getLong("tempo_total"),
						retornoSelect.getFloat("tempo_medio"), retornoSelect.getLong("tempo_menor"),
						retornoSelect.getLong("tempo_maior"), 1, retornoSelect.getString("nome_servidor"), retornoSelect.getInt("id_servidor")));
				totalChamadas += retornoSelect.getInt("quantidade_chamada");
			}

			for (LogDashboard ld : repositorioLogDashboard) {
				ld.setQuantidadeChamadasTotal(totalChamadas);
				ld.setPorcentagemTotal(((ld.getQuantidadeDessaChamada() * 100.0f) / totalChamadas));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return repositorioLogDashboard;
	}

	public List<LogDashboard> filtrarPorTudo(int servidor, Timestamp dataInicio, Timestamp dataFim, long duracaoInicio,
			long duracaoFim) throws SQLException {
		List<LogDashboard> resultado = new ArrayList<LogDashboard>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT nome_metodo, count(*) AS quantidade_chamada, ");
		sql.append("SUM(duracao) AS tempo_total, ");
		sql.append("AVG(duracao) AS tempo_medio, ");
		sql.append("MAX(duracao) AS tempo_maior, ");
		sql.append("MIN(duracao) AS tempo_menor, ");
		sql.append("ser.id_servidor, ");
		sql.append("ser.nome_servidor ");
		sql.append("FROM tb_chamada_metodo met ");
		sql.append("JOIN tb_chamada_metodo_arquivo_servidor mas ON met.id_chamada_metodo = mas.id_chamada_metodo ");
		sql.append("JOIN tb_servidor ser ON ser.id_servidor = mas.id_servidor ");
		sql.append("JOIN tb_arquivo ar ON mas.id_arquivo = ar.id_arquivo ");
		sql.append("WHERE (ar.arquivo_excluido != TRUE) ");
		sql.append("AND ser.id_servidor = ? ");
		sql.append("AND (met.duracao >= ? ");
		sql.append("AND met.duracao <= ?) ");
		sql.append("AND (met.data_inicio, met.data_fim) OVERLAPS ( ?,?) ");
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
						retornoSelect.getLong("tempo_maior"), 1, retornoSelect.getString("nome_servidor"),
						retornoSelect.getInt("id_servidor")));
				totalChamadas += retornoSelect.getInt("quantidade_chamada");
			}

			for (LogDashboard ld : resultado) {
				ld.setQuantidadeChamadasTotal(totalChamadas);
				ld.setPorcentagemTotal(((ld.getQuantidadeDessaChamada() * 100.0f) / totalChamadas));
			}
		} catch (Exception e) {
			// TODO: handle exception
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
