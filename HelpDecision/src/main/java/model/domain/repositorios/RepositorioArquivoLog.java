package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.fabricas.FabricaArquivoLog;
import model.domain.fabricas.FabricaServidor;
import model.domain.util.CalendarioUtil;

public class RepositorioArquivoLog {

	Connection conexao;

	public RepositorioArquivoLog() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public ArquivoLog insert(ArquivoLog arquivoLog) {
		String sql = "INSERT INTO tb_arquivo " + "(nome_arquivo, data_upload, descricao, arquivo_excluido) "
				+ "VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, arquivoLog.getNomeArquivo());
			pst.setTimestamp(2, CalendarioUtil.dateParaSqlTimestamp(arquivoLog.getDataUpload()));
			pst.setString(3, arquivoLog.getDescricao());
			pst.setBoolean(4, false);
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

	public List<ArquivoLog> findAll() throws SQLException {
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
