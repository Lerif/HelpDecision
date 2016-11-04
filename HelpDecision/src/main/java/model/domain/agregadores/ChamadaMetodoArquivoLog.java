package model.domain.agregadores;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;

public class ChamadaMetodoArquivoLog {

	private ChamadaMetodo chamadaMetodo;
	private ArquivoLog arquivoLog;
	String testGit;
	
	private ChamadaMetodoArquivoLog(ChamadaMetodo chamadaMetodo, ArquivoLog arquivoLog) {
		this.chamadaMetodo = chamadaMetodo;
		this.arquivoLog = arquivoLog;
	}

	public static ChamadaMetodoArquivoLog nova(ChamadaMetodo chamadaMetodo, ArquivoLog arquivoLog) {
		return new ChamadaMetodoArquivoLog(chamadaMetodo, arquivoLog);
	}

	public ChamadaMetodo getChamadaMetodo() {
		return chamadaMetodo;
	}

	public void setChamadaMetodo(ChamadaMetodo chamadaMetodo) {
		this.chamadaMetodo = chamadaMetodo;
	}

	public ArquivoLog getArquivoLog() {
		return arquivoLog;
	}

	public void setArquivoLog(ArquivoLog arquivoLog) {
		this.arquivoLog = arquivoLog;
	}
}
