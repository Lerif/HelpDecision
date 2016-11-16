package model.domain.fabricas;

import model.domain.entidades.LogDashboard;

public class FabricaDashboard {

	private FabricaDashboard() {
	}

	public static FabricaDashboard nova() {
		return new FabricaDashboard();
	}

	public static LogDashboard novoDashboard(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal, long tempoTotal,
			float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal, String nomeServidor, int idServidor) {
		return LogDashboard.novo(nomeMetodo, quantidadeChamadas, porcentagemTotal, tempoTotal, tempoMedio, tempoMenor,
				tempoMaior, quantidadeChamadasTotal, nomeServidor, idServidor);
	}
}
