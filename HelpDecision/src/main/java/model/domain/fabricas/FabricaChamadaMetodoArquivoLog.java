package model.domain.fabricas;

import model.domain.agregadores.ChamadaMetodoArquivoLog;
import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;

public class FabricaChamadaMetodoArquivoLog {

	private FabricaChamadaMetodoArquivoLog() {
	}

	public static FabricaChamadaMetodoArquivoLog nova() {
		return new FabricaChamadaMetodoArquivoLog();
	}

	public ChamadaMetodoArquivoLog novoArquivoLog(ChamadaMetodo chamadaMetodo, ArquivoLog arquivoLog) {
		return ChamadaMetodoArquivoLog.nova(chamadaMetodo, arquivoLog);
	}

}
