package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.fabricas.FabricaArquivoLog;
import model.domain.fabricas.FabricaServidor;
import model.domain.util.CalendarioUtil;

public class RepositorioArquivoLog {

	Connection conexao;

	public RepositorioArquivoLog() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public Connection getConexao() {
		return new ConexaoDB().conectarDB();
	}

	public ArquivoLog insert(ArquivoLog arquivoLog) {

		final StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tb_arquivo ");
		sql.append("(nome_arquivo, data_upload, descricao, arquivo_excluido, id_servidor, caminho_arquivo) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?)");

		try {

			limparCacheIfAlreadyExists(arquivoLog);

			PreparedStatement pst = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, arquivoLog.getNomeArquivo());
			pst.setTimestamp(2, CalendarioUtil.dateParaSqlTimestamp(arquivoLog.getDataUpload()));
			pst.setString(3, arquivoLog.getDescricao());
			pst.setBoolean(4, false);
			pst.setInt(5, arquivoLog.getServidor().getIdServidor());
			pst.setString(6, arquivoLog.getCaminhoDoArquivo());
			pst.execute();
			final ResultSet resultSet = pst.getGeneratedKeys();
			if (resultSet.next()) {
				arquivoLog.setIdArquivo(resultSet.getInt("id_arquivo"));
			}
			pst.close();
			return arquivoLog;
		} catch (Exception e) {
			return null;
		}
	}

	private void limparCacheIfAlreadyExists(ArquivoLog arquivoLog) throws SQLException {

		final Integer idArquivo = getIdArquivoLogIfAlreadyExists(arquivoLog);

		if (idArquivo != null) {

			limparCache(idArquivo);

		}
	}

	private Integer getIdArquivoLogIfAlreadyExists(ArquivoLog arquivoLog) throws SQLException {

		Connection dbConnection = new ConexaoDB().conectarDB();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = dbConnection.prepareStatement(
					"SELECT arq.id_arquivo FROM tb_arquivo arq WHERE arq.nome_arquivo =  ? AND arq.id_servidor = ?");
			preparedStatement.setString(1, arquivoLog.getNomeArquivo());
			preparedStatement.setInt(2, arquivoLog.getServidor().getIdServidor());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("id_arquivo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			dbConnection.close();
		}

		return null;

	}

	private void limparCache(Integer idArquivo) throws SQLException {

		Connection dbConnection = new ConexaoDB().conectarDB();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = dbConnection
					.prepareStatement("DELETE FROM tb_chamada_metodo met WHERE met.id_arquivo = ?");
			preparedStatement.setInt(1, idArquivo);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			dbConnection.close();
		}

		limparCacheArquivo(idArquivo);

	}

	private void limparCacheArquivo(Integer arquivoLog) throws SQLException {

		Connection dbConnection = new ConexaoDB().conectarDB();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = dbConnection.prepareStatement("DELETE FROM tb_arquivo arq WHERE arq.id_arquivo = ?");
			preparedStatement.setInt(1, arquivoLog);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			dbConnection.close();
		}

	}

	public List<ArquivoLog> findAll() throws SQLException {
		List<ArquivoLog> arquivoLog = new ArrayList<ArquivoLog>();

		String sql = "SELECT * FROM tb_arquivo INNER JOIN tb_servidor "
				+ "ON tb_arquivo.id_servidor = tb_servidor.id_servidor " + "WHERE tb_arquivo.arquivo_excluido = FALSE";

		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				arquivoLog.add(FabricaArquivoLog.nova()
						.novoArquivoLog(retornoSelect.getInt("id_arquivo"), retornoSelect.getString("nome_arquivo"),
								retornoSelect.getDate("data_upload"), retornoSelect.getString("descricao"),
								FabricaServidor.novo().novoServidor(retornoSelect.getInt("id_servidor"),
										retornoSelect.getString("nome_servidor")),
								retornoSelect.getString("caminho_arquivo")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arquivoLog;
	}

	public void removeByID(int i) {

		Connection dbConnection = new ConexaoDB().conectarDB();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = dbConnection
					.prepareStatement("DELETE FROM tb_arquivo arquivo where arquivo.id_arquivo = ?");
			preparedStatement.setInt(1, i);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Boolean flegarArquivoExcluido(ArquivoLog arquivoLog) {
		String sql = "UPDATE tb_arquivo SET arquivo_excluido = true WHERE id_arquivo = " + arquivoLog.getIdArquivo();
		
		try {
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.execute();
			pst.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
