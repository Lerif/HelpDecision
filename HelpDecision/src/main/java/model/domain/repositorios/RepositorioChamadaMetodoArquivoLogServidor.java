package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public void removeAgregadorThreeByIdArquivoLog(int i) {

		RepositorioChamadaMetodo repositorioChamadaMetodo = new RepositorioChamadaMetodo();

		for (String idChamadaMetodo : selectChamadasMetodosByIdArquivoLogFromAgregador(i)) {
			repositorioChamadaMetodo.removeByID(idChamadaMetodo);
		}

		deleteAgregadorByIdArquivo(i);

		RepositorioArquivoLog repositorioArquivoLog = new RepositorioArquivoLog();
		repositorioArquivoLog.removeByID(i);

	}

	private void deleteChamadasMetodosByIdArquivoLogFromAgregador(int i) {

		Connection dbConnection = new ConexaoDB().conectarDB();
		PreparedStatement preparedStatement = null;

		String sql = "DELETE FROM tb_chamada_metodo a WHERE a.id_chamada_metodo in (SELECT id_chamada_metodo FROM tb_chamada_metodo_arquivo WHERE id_arquivo = ?)";

		try {
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, i);
			preparedStatement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					dbConnection.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (dbConnection != null)
					dbConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	private List<String> selectChamadasMetodosByIdArquivoLogFromAgregador(int i) {

		Connection dbConnection = new ConexaoDB().conectarDB();
		PreparedStatement preparedStatement = null;
		List<String> idsChamadasMetodos = new ArrayList<String>();

		String sql = "SELECT id_chamada_metodo FROM tb_chamada_metodo_arquivo WHERE id_arquivo = ?";

		try {
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, i);
			ResultSet retornoSelect = preparedStatement.executeQuery(sql);

			while (retornoSelect.next()) {
				idsChamadasMetodos.add(retornoSelect.getString("id_chamada_metodo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					dbConnection.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (dbConnection != null)
					dbConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return idsChamadasMetodos;

	}

	private void deleteAgregadorByIdArquivo(int i) {

		Connection dbConnection = new ConexaoDB().conectarDB();
		PreparedStatement preparedStatement = null;

		String sql = "DELETE tb_chamada_metodo_arquivo a WHERE a.id_arquivo = ?";

		try {
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, i);
			preparedStatement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					dbConnection.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (dbConnection != null)
					dbConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

}
