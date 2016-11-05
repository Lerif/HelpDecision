package model.domain.repositorios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.servicos.ServicoFachada;

public class RepositorioArquivoLog {

	Connection conexao;
	private ServicoFachada servicoFachada = new ServicoFachada();

	public RepositorioArquivoLog() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<ArquivoLog> findAll() throws SQLException {
		List<ArquivoLog> arquivosLog = new ArrayList<ArquivoLog>();
		String sql = "SELECT * FROM tb_arquivo";
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				ArquivoLog arquivoLog = servicoFachada.solicitarCriacaoArquivoLog(
						retornoSelect.getString("nome_arquivo"), retornoSelect.getDate("data_upload"),
						retornoSelect.getString("descricao"), retornoSelect.getInt("id_servidor"));
				arquivosLog.add(arquivoLog);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arquivosLog;
	}
}
