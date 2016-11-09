package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.domain.agregadores.ChamadaMetodoArquivoLogServidor;
import model.domain.entidades.ChamadaMetodo;
import model.domain.fabricas.FabricaChamadaMetodo;

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
	
	public List<ChamadaMetodo> filtrarPorTudo(String nomeServidor, long duracaoInicio, long duracaoFim, Date dataInicio,
			Date dataFim) throws SQLException {
		List<ChamadaMetodo> resultado = new ArrayList<ChamadaMetodo>();
		
		String sqlCodigo = null;

		if(!nomeServidor.equals("")){
			sqlCodigo = "SELECT * FROM tb_chamada_metodo_arquivo" 
					+ "inner join tb_servidor on tb_chamada_metodo_arquivo.id_servidor = tb_servidor.id_servidor"
					+ "inner join tb_chamada_metodo on tb_chamada_metodo_arquivo.id_chamada_metodo = tb_chamada_metodo.id_chamada_metodo"
					+ "where tb_servidor.nome_servidor = ' " + nomeServidor + " ' "
					+ "and (tb_chamada_metodo.duracao >= " + duracaoInicio
					+ "and tb_chamada_metodo.duracao <= " + duracaoFim + ") "
					+ "and (tb_chamada_metodo.data_inicio >= ' " + dataInicio + "' "
					+ "and '" +dataFim + "' <= tb_chamada_metodo.data_fim)";
			
		}
		else{
			sqlCodigo = "SELECT * FROM tb_chamada_metodo_arquivo" 
					+ "inner join tb_servidor on tb_chamada_metodo_arquivo.id_servidor = tb_servidor.id_servidor"
					+ "inner join tb_chamada_metodo on tb_chamada_metodo_arquivo.id_chamada_metodo = tb_chamada_metodo.id_chamada_metodo"
					+ "where"
					+ "and (tb_chamada_metodo.duracao >= " + duracaoInicio
					+ "and tb_chamada_metodo.duracao <= " + duracaoFim + ") "
					+ "and (tb_chamada_metodo.data_inicio >= ' " + dataInicio + "' "
					+ "and '" +dataFim + "' <= tb_chamada_metodo.data_fim)";
		}
		
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sqlCodigo);
			while (retornoSelect.next()) {
				resultado
						.add(FabricaChamadaMetodo.nova().NovaChamadaMetodo(retornoSelect.getInt("id_chamada_metodo"),
								retornoSelect.getString("nome_metodo"), retornoSelect.getDate("data_inicio"),
								retornoSelect.getDate("data_fim"), retornoSelect.getString("id_metodo"),
								retornoSelect.getString("tipo_metodo"), retornoSelect.getLong("duracao")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultado;
	}
}
