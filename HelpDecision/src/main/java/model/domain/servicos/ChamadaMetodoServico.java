package model.domain.servicos;

import java.util.List;

import model.domain.entidades.ChamadaMetodo;
import model.domain.repositorios.RepositorioChamadaMetodo;

public class ChamadaMetodoServico {

	private RepositorioChamadaMetodo chamadaMetodoRepositorio;
	
	private ChamadaMetodoServico() {
		chamadaMetodoRepositorio = new RepositorioChamadaMetodo();
	}

	public static ChamadaMetodoServico novo() {
		return new ChamadaMetodoServico();
	}
	
	public List<ChamadaMetodo> inserirDadosNaTbChamadaMetodo(List<ChamadaMetodo> listaChamadaMetodo){
		return chamadaMetodoRepositorio.insert(listaChamadaMetodo);
	}

}
