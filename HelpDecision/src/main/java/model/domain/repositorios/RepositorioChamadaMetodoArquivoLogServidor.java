package model.domain.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
