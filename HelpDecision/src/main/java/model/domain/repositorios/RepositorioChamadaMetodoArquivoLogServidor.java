package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.domain.agregadores.ChamadaMetodoArquivoLogServidor;
import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaArquivoLog;
import model.domain.fabricas.FabricaChamadaMetodoArquivoLogServidor;
import model.domain.fabricas.FabricaServidor;

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

	public List<ChamadaMetodoArquivoLogServidor> findArquivoLogAndServidor() throws SQLException {
		List<ChamadaMetodoArquivoLogServidor> arquivosLogAndSeridores = new ArrayList<ChamadaMetodoArquivoLogServidor>();
		Servidor servidor;
		ArquivoLog arquivoLog;
		List<ChamadaMetodo> chamadasMetodo = null;

		String sql = "SELECT DISTINCT "
				+ "tb_arquivo.id_arquivo, tb_servidor.id_servidor, tb_arquivo.nome_arquivo, tb_arquivo.data_upload, tb_arquivo.descricao, tb_servidor.nome_servidor "
				+ "FROM tb_chamada_metodo_arquivo_servidor "
				+ "inner join tb_arquivo on tb_chamada_metodo_arquivo_servidor.id_arquivo = tb_arquivo.id_arquivo "
				+ "inner join tb_servidor on tb_chamada_metodo_arquivo_servidor.id_servidor = tb_servidor.id_servidor "
				+ "WHERE tb_arquivo.arquivo_excluido = false";
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				servidor = FabricaServidor.novo().novoServidor(retornoSelect.getInt("id_servidor"),
						retornoSelect.getString("nome_servidor"));
				arquivoLog = FabricaArquivoLog.nova().novoArquivoLog(retornoSelect.getInt("id_arquivo"),
						retornoSelect.getString("nome_arquivo"), retornoSelect.getDate("data_upload"),
						retornoSelect.getString("descricao"));
				arquivosLogAndSeridores.add(FabricaChamadaMetodoArquivoLogServidor.nova()
						.novaChamadaMetodoArquivoLogServidor(chamadasMetodo, arquivoLog, servidor));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arquivosLogAndSeridores;
	}

	public List<ChamadaMetodoArquivoLogServidor> findDetailsFromArquivoLogAndServidor(String nomeMetodo,
			String nomeServidor, Timestamp dataInicio, Timestamp dataFim) {

		List<ChamadaMetodoArquivoLogServidor> agregadores;
		Servidor servidor;
		ArquivoLog arquivoLog;
		ChamadaMetodo chamadasMetodo;

		StringBuilder query = new StringBuilder();
		query.append("chamada_metodo.nome_metodo, chamada_metodo.duracao, chamada_metodo.data_inicio, ");
		query.append("chamada_metodo.data_fim, chamada_metodo.id_elemento, chamada_metodo.tipo_elemento, chamada_metodo.id_chamada_metodo, ");
		query.append("chamada_metodo.tipo_elemento, servidor.id_servidor, servidor.nome_servidor, arquivo.id_arquivo, arquivo.nome_arquivo, ");
		query.append("arquivo.descricao, arquivo.arquivo_excluido, arquivo.data_upload ");
		query.append("FROM tb_chamada_metodo_arquivo_servidor agregador ");
		query.append("JOIN tb_chamada_metodo chamada_metodo ON agregador.id_chamada_metodo = chamada_metodo.id_chamada_metodo" );
		query.append("JOIN tb_arquivo arquivo ON agregador.id_arquivo = arquivo.id_arquivo ");
		query.append("JOIN tb_servidor servidor ON agregador.id_servidor = servidor.id_servidor ");
		query.append("WHERE chamada_metodo.nome_metodo = :nomemetodo AND servidor.nome_servidor = :nomeservidor ");
		

		return null;
	}

}
