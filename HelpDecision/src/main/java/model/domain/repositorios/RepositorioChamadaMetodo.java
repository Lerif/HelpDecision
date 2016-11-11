package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaChamadaMetodo;
import model.domain.util.CalendarioUtil;

public class RepositorioChamadaMetodo {

	Connection conexao;

	static final String TABELA_CHAMADA_METODO = "tb_chamada_metodo";
	static final String TABELA_SERVIDOR = "tb_servidor";
	static final String TABELA_CHAMADA_METODO_ARQUIVO = "tb_chamada_metodo_arquivo";

	public RepositorioChamadaMetodo() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<ChamadaMetodo> insert(List<ChamadaMetodo> listaChamadaMetodo) {
		List<ChamadaMetodo> chamadaMetodoComChave = new ArrayList<ChamadaMetodo>();
		try {
			for (ChamadaMetodo chamadaMetodo : listaChamadaMetodo) {
				if (!verificarChamadaMetodoExiste(chamadaMetodo)) {
					String sql = "INSERT INTO tb_chamada_metodo "
							+ "(nome_metodo, data_inicio, data_fim, duracao, id_elemento, tipo_elemento) "
							+ "VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pst.setString(1, chamadaMetodo.getNomeMetodo());
					pst.setTimestamp(2, chamadaMetodo.getDataInicio());
					pst.setTimestamp(3, chamadaMetodo.getDataFim());
					pst.setLong(4, chamadaMetodo.getDuracao());
					pst.setString(5, chamadaMetodo.getIdElemento());
					pst.setString(6, chamadaMetodo.getTipoElemento());
					pst.execute();
					final ResultSet resultSet = pst.getGeneratedKeys();
					if (resultSet.next()) {
						chamadaMetodo.setIdChamadaMetodo(resultSet.getInt("id_chamada_metodo"));
						chamadaMetodoComChave.add(chamadaMetodo);
					}
					pst.close();
				}
			}

		} catch (Exception e) {
			System.err.println("[RepositorioChamadaMetodo] Erro: " + e);
		}
		return chamadaMetodoComChave;
	}

	public Boolean verificarChamadaMetodoExiste(ChamadaMetodo chamadaMetodo) throws SQLException {
		String sql = "SELECT * FROM tb_chamada_metodo_arquivo_servidor "
				+ "INNER JOIN tb_chamada_metodo on tb_chamada_metodo_arquivo_servidor.id_chamada_metodo = tb_chamada_metodo.id_chamada_metodo "
				+ "INNER JOIN tb_arquivo on tb_chamada_metodo_arquivo_servidor.id_arquivo = tb_arquivo.id_arquivo "
				+ "WHERE tb_chamada_metodo.nome_metodo = '" + chamadaMetodo.getNomeMetodo()
				+ "' AND tb_chamada_metodo.data_inicio = '" + chamadaMetodo.getDataInicio()
				+ "' AND tb_chamada_metodo.data_fim = '" + chamadaMetodo.getDataFim()
				+ "' AND tb_chamada_metodo.id_elemento = '" + chamadaMetodo.getIdElemento()
				+ "' AND tb_chamada_metodo.tipo_elemento = '" + chamadaMetodo.getTipoElemento() 
				+ "' AND tb_arquivo.arquivo_excluido = false";
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			if (retornoSelect.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return null;
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
}
