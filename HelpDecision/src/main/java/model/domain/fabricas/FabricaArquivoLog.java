package model.domain.fabricas;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.Servidor;

public class FabricaArquivoLog {

	private FabricaArquivoLog() {
	}

	public static FabricaArquivoLog nova() {
		return new FabricaArquivoLog();
	}

	public ArquivoLog novoArquivoLog(int idArquivo, String nomeArquivo, java.sql.Date dataUpload, String descricao, Servidor servidor) {
		return ArquivoLog.novo(idArquivo, nomeArquivo, dataUpload, descricao, servidor);
	}
}
