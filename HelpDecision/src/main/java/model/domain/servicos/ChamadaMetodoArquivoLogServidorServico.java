package model.domain.servicos;

import java.util.List;

import model.domain.agregadores.ChamadaMetodoArquivoLogServidor;
import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaChamadaMetodoArquivoLogServidor;

public class ChamadaMetodoArquivoLogServidorServico {

	private ChamadaMetodoArquivoLogServidorServico() {

	}

	public static ChamadaMetodoArquivoLogServidorServico novo() {
		return new ChamadaMetodoArquivoLogServidorServico();
	}

	public ChamadaMetodoArquivoLogServidor agregar(List<ChamadaMetodo> listaChamadaMetodo, ArquivoLog arquivoLog,
			Servidor servidor) {
		return FabricaChamadaMetodoArquivoLogServidor.nova().novaChamadaMetodoArquivoLogServidor(listaChamadaMetodo,
				arquivoLog, servidor);
	}
}
