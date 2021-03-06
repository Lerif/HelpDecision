package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.domain.entidades.ChamadaMetodo;
import model.domain.fabricas.FabricaChamadaMetodo;

public class RepositorioChamadaMetodo {

	Connection conexao;

	static final String TABELA_CHAMADA_METODO = "tb_chamada_metodo";
	static final String TABELA_SERVIDOR = "tb_servidor";
	static final String TABELA_CHAMADA_METODO_ARQUIVO = "tb_chamada_metodo_arquivo";

	public RepositorioChamadaMetodo() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public Connection getConexao() {
		return new ConexaoDB().conectarDB();
	}

	public int insert(List<ChamadaMetodo> listaChamadaMetodo) throws SQLException {

		final StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO tb_chamada_metodo ");
		sql.append("(nome_metodo, data_inicio, data_fim, duracao, id_elemento, tipo_elemento, id_arquivo) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?)");

		Connection connection = null;
		PreparedStatement pst = null;

		final int batchSize = 10000;
		int count = 0;
		int registrosPersistidos = 0;
		int[] result;

		try {

			connection = getConexao();
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sql.toString());

			for (ChamadaMetodo chamadaMetodo : listaChamadaMetodo) {

				pst.setString(1, chamadaMetodo.getNomeMetodo());
				pst.setTimestamp(2, chamadaMetodo.getDataInicio());
				pst.setTimestamp(3, chamadaMetodo.getDataFim());
				pst.setLong(4, chamadaMetodo.getDuracao());
				pst.setString(5, chamadaMetodo.getIdElemento());
				pst.setString(6, chamadaMetodo.getTipoElemento());
				pst.setInt(7, chamadaMetodo.getArquivo().getIdArquivo());

				pst.addBatch();
				if (++count % batchSize == 0) {
					result = pst.executeBatch();
					registrosPersistidos += result.length;
					connection.commit();
				}
			}

			result = pst.executeBatch(); // insere os registros restantes
			registrosPersistidos += result.length;
			connection.commit();

		} catch (SQLException se) {
			se.printStackTrace();
			connection.rollback();
		} catch (Exception e) {
			System.err.println("[RepositorioChamadaMetodo] Erro: " + e);

		} finally {
			try {
				if (pst != null)
					pst.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return registrosPersistidos;
	}

	public Boolean verificarChamadaMetodoExiste(ChamadaMetodo chamadaMetodo) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT 1 FROM tb_chamada_metodo met ");
		sql.append("JOIN tb_arquivo arq ON arq.id_arquivo=met.id_arquivo ");
		sql.append("JOIN tb_servidor ser ON ser.id_servidor=arq.id_servidor ");
		sql.append("WHERE met.nome_metodo = ? ");
		sql.append("AND met.data_inicio = ? ");
		sql.append("AND ser.id_servidor = ? ");
		sql.append("AND arq.arquivo_excluido = false");

		Connection connection = null;
		PreparedStatement pst = null;

		connection = getConexao();
		pst = connection.prepareStatement(sql.toString());

		pst.setString(1, chamadaMetodo.getNomeMetodo());
		pst.setTimestamp(2, chamadaMetodo.getDataInicio());
		pst.setInt(3, chamadaMetodo.getArquivo().getServidor().getIdServidor());

		ResultSet retornoSelect = pst.executeQuery();

		if (retornoSelect.next()) {
			pst.close();
			connection.close();
			return true;
		} else {
			pst.close();
			connection.close();
			return false;
		}
	}

	public List<ChamadaMetodo> buscarPorDuracao(long inicio, long fim) {

		List<ChamadaMetodo> chamadasMetodo = new ArrayList<ChamadaMetodo>();

		String sql = "SELECT * FROM " + TABELA_CHAMADA_METODO + "where duracao between " + inicio + "and" + fim;
		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				chamadasMetodo
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getTimestamp("data_inicio"),
								retornoSelect.getTimestamp("data_fim"), retornoSelect.getString("id_elemento"),
								retornoSelect.getString("tipo_elemento"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return chamadasMetodo;
	}

	public List<ChamadaMetodo> findAll() throws SQLException {

		List<ChamadaMetodo> chamadasMetodo = new ArrayList<ChamadaMetodo>();
		String sql = "SELECT * FROM tb_chamada_metodo ";
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				chamadasMetodo
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getDate("data_inicio"),
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_elemento"),
								retornoSelect.getString("tipo_elemento"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return chamadasMetodo;
	}

	public List<ChamadaMetodo> buscarPorData(Date inicio, Date fim) {

		List<ChamadaMetodo> chamadasMetodo = new ArrayList<ChamadaMetodo>();

		String sql = "SELECT * FROM " + TABELA_CHAMADA_METODO + "where data_inicio >= " + inicio + " and data_fim < "
				+ fim;

		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				chamadasMetodo
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getDate("data_inicio"),
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_elemento"),
								retornoSelect.getString("tipo_elemento"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return chamadasMetodo;
	}

	public List<ChamadaMetodo> buscaPorServidor(String nomeDoServidor) {

		List<ChamadaMetodo> chamadasMetodo = new ArrayList<ChamadaMetodo>();

		String sql = "select * from " + TABELA_CHAMADA_METODO + " as me inner join " + TABELA_CHAMADA_METODO_ARQUIVO
				+ " as ma inner join " + TABELA_SERVIDOR + " as se on me.id_chamada_metodo = ma.id_chamada_metodo "
				+ "and ma.id_servidor = se.id_servidor where se.nome_servidor like " + nomeDoServidor;

		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				chamadasMetodo
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getDate("data_inicio"),
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_elemento"),
								retornoSelect.getString("tipo_elemento"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return chamadasMetodo;
	}

	public void removeByID(Integer idChamadaMetodo) {

		PreparedStatement preparedStatement = null;

		String sql = "DELETE FROM tb_chamada_metodo a WHERE a.id_chamada_metodo = ?";

		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, idChamadaMetodo);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					conexao.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conexao != null)
					conexao.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	public List<ChamadaMetodo> buscarDetalhes(String nomeMetodo, int idServidor, Timestamp dataInicio,
			Timestamp dataFim, Long rangeInicio, Long rangeFim) throws SQLException {

		// ChamadaMetodoArquivoLogServidor resultado = null;
		List<ChamadaMetodo> chamadasMetodos = new ArrayList<ChamadaMetodo>();

		StringBuilder sql = new StringBuilder();
	
		sql.append("SELECT chamada_metodo.nome_metodo, ");
		sql.append("chamada_metodo.duracao, ");
		sql.append("chamada_metodo.data_inicio, ");
		sql.append("chamada_metodo.data_fim, ");
		sql.append("chamada_metodo.id_elemento, ");
		sql.append("chamada_metodo.tipo_elemento, ");
		sql.append("chamada_metodo.id_chamada_metodo, ");
		sql.append("chamada_metodo.tipo_elemento ");
		sql.append("FROM tb_chamada_metodo AS chamada_metodo ");
		sql.append("JOIN tb_arquivo arquivo ON chamada_metodo.id_arquivo = arquivo.id_arquivo ");
		sql.append("JOIN tb_servidor servidor ON arquivo.id_servidor = servidor.id_servidor ");
		sql.append("WHERE chamada_metodo.nome_metodo = ?  ");
		sql.append("AND servidor.id_servidor = ? ");
		sql.append("AND arquivo.arquivo_excluido = false ");
		sql.append("AND (chamada_metodo.data_inicio, ");
		sql.append("chamada_metodo.data_fim) OVERLAPS (?,?) ");
		sql.append("AND chamada_metodo.duracao BETWEEN ? AND ? ");
		sql.append("GROUP BY 5, 6, 7 ");
		sql.append("ORDER BY chamada_metodo.duracao DESC LIMIT 10 ");

		PreparedStatement preparedStatement = conexao.prepareStatement(sql.toString());
		preparedStatement.setString(1, nomeMetodo);
		preparedStatement.setInt(2, idServidor);

		if (dataInicio != null && dataFim != null) {

			preparedStatement.setTimestamp(3, dataInicio);
			preparedStatement.setTimestamp(4, dataFim);
		}

		if (rangeInicio != null && rangeFim != null) {
			preparedStatement.setLong(5, rangeInicio);
			preparedStatement.setLong(6, rangeFim);
		}
		try {
			ResultSet retornoSelect = preparedStatement.executeQuery();
			while (retornoSelect.next()) {

				chamadasMetodos
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getTimestamp("data_inicio"),
								retornoSelect.getTimestamp("data_fim"), retornoSelect.getString("id_elemento"),
								retornoSelect.getString("tipo_elemento"), retornoSelect.getLong("duracao")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return chamadasMetodos;
	}
}
