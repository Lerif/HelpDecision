package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

public class RepositorioServidor {

	Connection conexao;
	private ServicoFachada servicoFachada = new ServicoFachada();

	public RepositorioServidor() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public Boolean insert(Servidor servidor) {
		String sql = "INSERT INTO tb_servidor " + "(nome_servidor) " + "VALUES (?)";
		try {
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setString(1, servidor.getNomeServidor());
			pst.execute();
			pst.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Servidor> findAll() throws SQLException {
		List<Servidor> servidores = new ArrayList<Servidor>();
		String sql = "SELECT * FROM tb_servidor";
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				Servidor servidor = servicoFachada.solicitarNovoServidor(retornoSelect.getInt("id_servidor"),
						retornoSelect.getString("nome_servidor"));
				servidores.add(servidor);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return servidores;
	}
}
