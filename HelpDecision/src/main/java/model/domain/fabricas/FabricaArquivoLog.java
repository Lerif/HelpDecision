package model.domain.fabricas;

import java.util.Date;

import model.domain.entidades.ArquivoLog;

public class FabricaArquivoLog {

	private FabricaArquivoLog() {
	}

	public static FabricaArquivoLog nova() {
		return new FabricaArquivoLog();
	}

	public ArquivoLog novoArquivoLog(int idArquivo, String nomeArquivo, Date dataUpload, String descricao) {
		return ArquivoLog.novo(idArquivo, nomeArquivo, dataUpload, descricao);
	}
}
