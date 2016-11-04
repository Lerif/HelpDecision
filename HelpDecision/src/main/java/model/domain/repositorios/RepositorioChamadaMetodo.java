package model.domain.repositorios;

import java.util.Date;
import java.util.List;

import model.domain.entidades.ChamadaMetodo;

public class RepositorioChamadaMetodo {

	public RepositorioChamadaMetodo() {
		// TODO Auto-generated constructor stub
	}

	public List<ChamadaMetodo> findByDateAndDuration(Date dataInicio, Date dateFim, long duracao) {
		// SELECT * FROM tb_chamada_metodo WHERE data_inicio = dataInicio and
		// data_fim = dataFim and duracao > duracao

		return null;
	}

}
