package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.domain.agregadores.ChamadaMetodoArquivoLogServidor;
import model.domain.entidades.ChamadaMetodo;

public class RepositorioChamadaMetodoArquivoLogServidor {

	Connection conexao;

	public RepositorioChamadaMetodoArquivoLogServidor() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public Boolean insert(ChamadaMetodoArquivoLogServidor agregador) {
		try {
			for (ChamadaMetodo chamadaMetodo : agregador.getChamadaMetodo()) {
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
		String sql = "SELECT id_chamada_metodo FROM tb_chamada_metodo_arquivo WHERE id_arquivo = " + i;
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

		String sql = "DELETE FROM tb_chamada_metodo_arquivo a WHERE a.id_arquivo = " + i;

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

}
