package model.domain.fabricas;

import java.util.Date;

import model.domain.entidades.ChamadaMetodo;

public class FabricaChamadaMetodo {

	private FabricaChamadaMetodo() {

	}

	public static FabricaChamadaMetodo nova() {
		return new FabricaChamadaMetodo();
	}

	public ChamadaMetodo NovaChamadaMetodo(int idChamadaMetodo, String nomeMetodo, Date dataInicio, Date dataFim, String idElemento,
			String tipoElemento, long duracao) {
		return ChamadaMetodo.nova(idChamadaMetodo, nomeMetodo, dataInicio, dataFim, idElemento, tipoElemento, duracao);
	}
}
