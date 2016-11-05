package model.domain.entidades;

public class Servidor {

	private int idServidor;
	private String nomeServidor;

	public Servidor() {
	}

	private Servidor(int idServidor, String nomeServidor) {
		setIdServidor(idServidor);
		setNomeServidor(nomeServidor);
	}

	public static Servidor novo(int idServidor, String nomeServidor) {
		return new Servidor(idServidor, nomeServidor);
	}

	public int getIdServidor() {
		return idServidor;
	}

	public void setIdServidor(int idServidor) {
		this.idServidor = idServidor;
	}

	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

}
