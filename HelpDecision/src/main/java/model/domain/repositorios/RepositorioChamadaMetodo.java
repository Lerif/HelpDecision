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
import model.domain.fabricas.FabricaChamadaMetodo;
import model.domain.util.CalendarioUtil;

public class RepositorioChamadaMetodo {

	Connection conexao;

	static final String TABELA_CHAMADA_METODO = "tb_chamada_metodo";
	static final String TABELA_SERVIDOR = "tb_servidor";
	static final String TABELA_CHAMADA_METODO_ARQUIVO ="tb_chamada_metodo_arquivo";

	public RepositorioChamadaMetodo() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<ChamadaMetodo> insert(List<ChamadaMetodo> listaChamadaMetodo) {
		List<ChamadaMetodo> chamadaMetodoComChave = new ArrayList<ChamadaMetodo>();
		try {
			for (ChamadaMetodo chamadaMetodo : listaChamadaMetodo) {
				String sql = "INSERT INTO tb_chamada_metodo "
						+ "(nome_metodo, data_inicio, data_fim, duracao, id_elemento, tipo_elemento) "
						+ "VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, chamadaMetodo.getNomeMetodo());
				pst.setTimestamp(2, CalendarioUtil.dateParaSqlTimestamp(chamadaMetodo.getDataInicio()));
				pst.setTimestamp(3, CalendarioUtil.dateParaSqlTimestamp(chamadaMetodo.getDataFim()));
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

		} catch (Exception e) {
			System.err.println("[RepositorioChamadaMetodo] Erro: " + e);
		}
		return chamadaMetodoComChave;
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
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_metodo"),
								retornoSelect.getString("tipo_metodo"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return chamadasMetodo;
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
								retornoSelect.getString("nome_metodo"), retornoSelect.getDate("data_inicio"),
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_metodo"),
								retornoSelect.getString("tipo_metodo"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return chamadasMetodo;
	}

	public List<ChamadaMetodo> buscarPorData(Date inicio, Date fim) {

		List<ChamadaMetodo> chamadasMetodo = new ArrayList<ChamadaMetodo>();

		String sql = "SELECT * FROM " + TABELA_CHAMADA_METODO + "where data_inicio >= " + inicio + " and data_fim < " + fim;

		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				chamadasMetodo
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getDate("data_inicio"),
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_metodo"),
								retornoSelect.getString("tipo_metodo"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return chamadasMetodo;
	}

	public List<ChamadaMetodo> buscaPorServidor(String nomeDoServidor){
		
		List<ChamadaMetodo> chamadasMetodo = new ArrayList<ChamadaMetodo>();
		
		String sql = "select * from " + TABELA_CHAMADA_METODO +  " as me inner join " + TABELA_CHAMADA_METODO_ARQUIVO
		+" as ma inner join " + TABELA_SERVIDOR +" as se on me.id_chamada_metodo = ma.id_chamada_metodo "+ 
				"and ma.id_servidor = se.id_servidor where se.nome_servidor like " + nomeDoServidor;

		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				chamadasMetodo
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getDate("data_inicio"),
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_metodo"),
								retornoSelect.getString("tipo_metodo"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}	
		return chamadasMetodo;
	}
	
}
