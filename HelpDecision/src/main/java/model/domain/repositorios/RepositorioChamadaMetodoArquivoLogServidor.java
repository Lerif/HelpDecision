package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Dashboard;
import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaArquivoLog;
import model.domain.fabricas.FabricaChamadaMetodo;
import model.domain.fabricas.FabricaChamadaMetodoArquivoLogServidor;
import model.domain.fabricas.FabricaDashboard;
import model.domain.fabricas.FabricaServidor;

public class RepositorioChamadaMetodoArquivoLogServidor {

	Connection conexao;

	public RepositorioChamadaMetodoArquivoLogServidor() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	/*
	public Boolean insert(ChamadaMetodoArquivoLogServidor agregador) {
		try {
			for (ChamadaMetodo chamadaMetodo : agregador.getChamadasMetodos()) {
				String sql = "INSERT INTO tb_chamada_metodo_arquivo_servidor "
						+ "(id_chamada_metodo, id_arquivo, id_servidor) " + "VALUES (?, ?, ?)";
				PreparedStatement pst = conexao.prepareStatement(sql);
				pst.setInt(1, chamadaMetodo.getIdChamadaMetodo());
				pst.setInt(2, agregador.getArquivoLog().getIdArquivo());
				pst.setInt(3, agregador.getServidor().getIdServidor());
				pst.execute();
				pst.close();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	*/

	public void removeAgregadorThreeByIdArquivoLog(int i) throws SQLException {

		List<Integer> idsChamadaMetodo = selectChamadasMetodosByIdArquivoLogFromAgregador(i);

		if (deleteAgregadorByIdArquivo(i)) {

			for (Integer chamadaMetodo : idsChamadaMetodo) {
				RepositorioChamadaMetodo repositorioChamadaMetodo = new RepositorioChamadaMetodo();
				repositorioChamadaMetodo.removeByID(chamadaMetodo);
			}

			RepositorioArquivoLog repositorioArquivoLog = new RepositorioArquivoLog();
			repositorioArquivoLog.removeByID(i);

		}

	}

	private List<Integer> selectChamadasMetodosByIdArquivoLogFromAgregador(int i) throws SQLException {

		List<Integer> idsChamadasMetodos = new ArrayList<Integer>();
		String sql = "SELECT id_chamada_metodo FROM tb_chamada_metodo_arquivo_servidor WHERE id_arquivo = " + i;
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				idsChamadasMetodos.add(retornoSelect.getInt("id_chamada_metodo"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return idsChamadasMetodos;
	}

	private Boolean deleteAgregadorByIdArquivo(int i) throws SQLException {

		PreparedStatement preparedStatement = null;

		String sql = "DELETE FROM tb_chamada_metodo_arquivo_servidor a WHERE a.id_arquivo = " + i;

		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public List<ArquivoLog> findArquivoLogAndServidor() throws SQLException {
		
		List<ArquivoLog> arquivoLog = new ArrayList<ArquivoLog>();

		String sql = "SELECT DISTINCT FROM tb_arquivo INNER JOIN tb_servidor "
				+ "ON tb_arquivo.id_servidor = tb_servidor.id_servidor " + "WHERE tb_arquivo.arquivo_excluido = FALSE";

		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				arquivoLog.add(FabricaArquivoLog.nova().novoArquivoLog(retornoSelect.getInt("id_arquivo"),
						retornoSelect.getString("nome_arquivo"), retornoSelect.getDate("data_upload"),
						retornoSelect.getString("descricao"), FabricaServidor.novo().novoServidor(
								retornoSelect.getInt("id_servidor"), retornoSelect.getString("nome_servidor"))));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arquivoLog;
	}

	public List<ChamadaMetodo> findDetailsFromArquivoLogAndServidor(String nomeMetodo, int idServidor,
			Timestamp dataInicio, Timestamp dataFim, Long rangeInicio, Long rangeFim) throws SQLException {

		//ChamadaMetodoArquivoLogServidor resultado = null;
		List<ChamadaMetodo> chamadasMetodos = new ArrayList<ChamadaMetodo>();

		StringBuilder sql = new StringBuilder();
		/*sql.append("SELECT chamada_metodo.nome_metodo, ");
		sql.append("chamada_metodo.duracao, ");
		sql.append("chamada_metodo.data_inicio, ");
		sql.append("chamada_metodo.data_fim, ");
		sql.append("chamada_metodo.id_elemento, ");
		sql.append("chamada_metodo.tipo_elemento, ");
		sql.append("chamada_metodo.id_chamada_metodo, ");
		sql.append("chamada_metodo.tipo_elemento ");
		sql.append("FROM tb_chamada_metodo_arquivo_servidor agregador ");
		sql.append(
				"JOIN tb_chamada_metodo chamada_metodo ON agregador.id_chamada_metodo = chamada_metodo.id_chamada_metodo ");
		sql.append("JOIN tb_arquivo arquivo ON agregador.id_arquivo = arquivo.id_arquivo ");
		sql.append("JOIN tb_servidor servidor ON agregador.id_servidor = servidor.id_servidor ");
		sql.append("WHERE ");
		sql.append("chamada_metodo.nome_metodo = ? ");
		sql.append("AND servidor.id_servidor = ? ");
		sql.append("AND arquivo.arquivo_excluido = false ");*/

		sql.append("SELECT chamada_metodo.nome_metodo, ");
		sql.append("chamada_metodo.duracao, ");
		sql.append("chamada_metodo.data_inicio, ");
		sql.append("chamada_metodo.data_fim, ");
		sql.append("chamada_metodo.id_elemento, ");
		sql.append("chamada_metodo.tipo_elemento, ");
		sql.append("chamada_metodo.id_chamada_metodo, ");
		sql.append("chamada_metodo.tipo_elemento ");
		sql.append("FROM tb_chamada_metodo AS chamada_metodo ");
		sql.append("JOIN tb_arquivo arquivo ON chamada_metodo.id_arquivo = arquivo.id_arquivo");
		sql.append("JOIN tb_servidor servidor ON arquivo.id_servidor = servidor.id_servidor ");
		sql.append("WHERE chamada_metodo.nome_metodo = ?  ");
		sql.append("AND servidor.id_servidor = ? ");
		sql.append("AND arquivo.arquivo_excluido = false ");
		
		
		/*
		SELECT chamada_metodo.nome_metodo, chamada_metodo.duracao, chamada_metodo.data_inicio, chamada_metodo.data_fim, 
		chamada_metodo.id_elemento, chamada_metodo.tipo_elemento, chamada_metodo.id_chamada_metodo, chamada_metodo.tipo_elemento 
		FROM tb_chamada_metodo AS chamada_metodo
		JOIN tb_arquivo arquivo ON chamada_metodo.id_arquivo = arquivo.id_arquivo
		JOIN tb_servidor servidor ON arquivo.id_servidor = servidor.id_servidor
		WHERE chamada_metodo.nome_metodo = 'aguas abajo' 
		AND servidor.id_servidor = '1' 
		AND arquivo.arquivo_excluido = false 
		 */

		if (dataInicio != null && dataFim != null) {
			sql.append("AND (chamada_metodo.data_inicio, ");
			sql.append("chamada_metodo.data_fim) OVERLAPS (?,?) ");
		}
		if (rangeInicio != null && rangeFim != null) {
			sql.append("AND chamada_metodo.duracao BETWEEN ? and ? ");
		}
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
			// TODO: handle exception
		}
		return chamadasMetodos;
	}
}
