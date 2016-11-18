package model.domain.servicos;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import model.domain.entidades.ChamadaMetodo;
import model.domain.repositorios.RepositorioChamadaMetodo;

public class ChamadaMetodoServico {

	private RepositorioChamadaMetodo chamadaMetodoRepositorio = new RepositorioChamadaMetodo();

	public ChamadaMetodoServico() {

	}

	public static ChamadaMetodoServico novo() {
		return new ChamadaMetodoServico();
	}

	public List<ChamadaMetodo> buscarPorDuracao(long inicio, long fim) {
		return chamadaMetodoRepositorio.buscarPorDuracao(inicio, fim);
	}

	public List<ChamadaMetodo> buscarPorData(Date inicio, Date fim) {
		return chamadaMetodoRepositorio.buscarPorData(inicio, fim);
	}

	public List<ChamadaMetodo> buscarPorServido(String nomeDoServidor) {
		return chamadaMetodoRepositorio.buscaPorServidor(nomeDoServidor);
	}

	public List<ChamadaMetodo> buscarTodosMetodos() throws SQLException {
		return chamadaMetodoRepositorio.findAll();
	}

	public int persistirChamadaMetodoList(List<ChamadaMetodo> listaChamadaMetodo) throws SQLException {
		return chamadaMetodoRepositorio.insert(listaChamadaMetodo);
	}

	public List<ChamadaMetodo> obterDetalhes(String nomeMetodo, int idServidor, Timestamp dataInicio, Timestamp dataFim,
			Long rangeInicio, Long rangeFim) throws SQLException {
		return chamadaMetodoRepositorio.buscarDetalhes(nomeMetodo, idServidor, dataInicio, dataFim, rangeInicio,
				rangeFim);
	}
}
