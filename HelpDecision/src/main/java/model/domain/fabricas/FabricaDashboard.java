package model.domain.fabricas;

import model.domain.entidades.Dashboard;

public class FabricaDashboard {

	private FabricaDashboard() {
	}

	public static FabricaDashboard nova() {
		return new FabricaDashboard();
	}

	public static Dashboard novoDashboard(int quantidadeChamadas, float porcentagemTotal, long tempoTotal,
			float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal) {
		return Dashboard.novo(quantidadeChamadas, porcentagemTotal, tempoTotal, tempoMedio, tempoMenor, tempoMaior,
				quantidadeChamadasTotal);
	}

	public static Dashboard novoDashboard(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal,
			long tempoTotal, float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal,
			String nomeServidor) {
		return Dashboard.novo(nomeMetodo, quantidadeChamadas, porcentagemTotal, tempoTotal, tempoMedio, tempoMenor,
				tempoMaior, quantidadeChamadasTotal, nomeServidor);
	}
}
