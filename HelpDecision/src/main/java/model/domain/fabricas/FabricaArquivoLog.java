package model.domain.fabricas;

import java.util.Date;

import model.domain.entidades.ArquivoLog;

public class FabricaArquivoLog {

	private FabricaArquivoLog() {
	}

	public static FabricaArquivoLog nova() {
		return new FabricaArquivoLog();
	}

	public ArquivoLog novoArquivoLog(String nomeArquivo, Date dataUpload, String descricao, int idServidor) {
		return ArquivoLog.novo(nomeArquivo, dataUpload, descricao, idServidor);
	}
}
