package model.domain.fabricas;

import model.domain.entidades.Servidor;

public class FabricaServidor {

	private FabricaServidor() {
		// TODO Auto-generated constructor stub
	}

	public static FabricaServidor novo() {
		return new FabricaServidor();
	}

	public Servidor novoServidor(int idServidor, String nomeServidor) {
		return Servidor.novo(idServidor, nomeServidor);
	}

}
