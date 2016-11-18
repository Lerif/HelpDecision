package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaServidor;

public class RepositorioServidor {

	Connection conexao;

	public RepositorioServidor() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public Boolean insert(Servidor servidor) throws SQLException {
		if (verificarServidorExiste(servidor) == false) {
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
		} else {
			return false;
		}
	}

	public Boolean verificarServidorExiste(Servidor servidor) throws SQLException {
		String sql = "SELECT * FROM tb_servidor WHERE nome_servidor = '" + servidor.getNomeServidor() + "'";
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			if (retornoSelect.next()) {
				return true;
			} else {
				return false;
			}
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
				servidores.add(FabricaServidor.novo().novoServidor(retornoSelect.getInt("id_servidor"),
						retornoSelect.getString("nome_servidor")));
			}
		} catch (Exception e) {
		}
		return servidores;
	}

	public Servidor findByName(Servidor servidor) throws SQLException {
		String sql = "SELECT * FROM tb_servidor WHERE nome_servidor = '" + servidor.getNomeServidor() + "'";
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				servidor.setIdServidor(retornoSelect.getInt("id_servidor"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return servidor;
	}

	public Servidor findById(Integer id) throws SQLException {
		String sql = "SELECT id_servidor, nome_servidor FROM tb_servidor WHERE id_servidor = '" + id + "'";
		Servidor servidor = null;
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				servidor = FabricaServidor.novo().novoServidor(retornoSelect.getInt("id_servidor"),
						retornoSelect.getString("nome_servidor"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return servidor;
	}
}
