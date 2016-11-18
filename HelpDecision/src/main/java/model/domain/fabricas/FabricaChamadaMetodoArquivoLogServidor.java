package model.domain.fabricas;

import java.util.List;
import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;

public class FabricaChamadaMetodoArquivoLogServidor {

	private FabricaChamadaMetodoArquivoLogServidor() {
	}

	public static FabricaChamadaMetodoArquivoLogServidor nova() {
		return new FabricaChamadaMetodoArquivoLogServidor();
	}

	/*public ChamadaMetodoArquivoLogServidor novaChamadaMetodoArquivoLogServidor(List<ChamadaMetodo> chamadasMetodos,
			ArquivoLog arquivoLog, Servidor servidor) {
		return ChamadaMetodoArquivoLogServidor.nova(chamadasMetodos, arquivoLog, servidor);
	}*/

}
