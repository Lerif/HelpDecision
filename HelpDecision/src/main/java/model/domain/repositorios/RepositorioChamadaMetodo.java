package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.domain.entidades.ChamadaMetodo;

public class RepositorioChamadaMetodo {

	Connection conexao;

	public RepositorioChamadaMetodo() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<ChamadaMetodo> insert(List<ChamadaMetodo> listaChamadaMetodo) {
		List<ChamadaMetodo> chamadaMetodoComChave = new ArrayList<ChamadaMetodo>();
		try {
			for (ChamadaMetodo chamadaMetodo : listaChamadaMetodo) {
				String sql = "INSERT INTO tb_chamada_metodo "
						+ "(nome_metodo, data_inicio, data_fim, duracao, id_elemento, tipo_elemento) "
						+ "VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, chamadaMetodo.getNomeMetodo());
				pst.setDate(2, chamadaMetodo.getDataInicio());
				pst.setDate(3, chamadaMetodo.getDataFim());
				pst.setLong(4, chamadaMetodo.getDuracao());
				pst.setString(5, chamadaMetodo.getIdElemento());
				pst.setString(6, chamadaMetodo.getTipoElemento());
				pst.execute();
				final ResultSet resultSet = pst.getGeneratedKeys();
				if (resultSet.next()) {
					chamadaMetodo.setIdChamadaMetodo(resultSet.getInt("id_chamada_metodo"));
					chamadaMetodoComChave.add(chamadaMetodo);
				}
				pst.close();
			}

		} catch (Exception e) {

		}
		return chamadaMetodoComChave;
	}

	public List<ChamadaMetodo> findByDateAndDuration(Date dataInicio, Date dateFim, long duracao) {
		// SELECT * FROM tb_chamada_metodo WHERE data_inicio = dataInicio and
		// data_fim = dataFim and duracao > duracao

		return null;
	}

}
