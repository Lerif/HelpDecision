package model.domain.fabricas;

import java.util.Date;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.Servidor;

public class FabricaArquivoLog {

	private FabricaArquivoLog() {
	}

	public static FabricaArquivoLog nova() {
		return new FabricaArquivoLog();
	}

	public ArquivoLog novoArquivoLog(String nomeArquivo, Date dataUpload, String descricao, Servidor servidor,
			String caminhoArquivo) {
		return ArquivoLog.novo(null, nomeArquivo, dataUpload, descricao, servidor, caminhoArquivo);
	}

	public ArquivoLog novoArquivoLog(Integer idArquivo, String nomeArquivo, Date dataUpload, String descricao,
			Servidor servidor, String caminhoArquivo) {
		return ArquivoLog.novo(nomeArquivo, dataUpload, descricao, servidor, caminhoArquivo);
	}
}
