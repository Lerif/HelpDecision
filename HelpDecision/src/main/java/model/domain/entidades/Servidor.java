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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idServidor;
		result = prime * result + ((nomeServidor == null) ? 0 : nomeServidor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servidor other = (Servidor) obj;
		if (idServidor != other.idServidor)
			return false;
		if (nomeServidor == null) {
			if (other.nomeServidor != null)
				return false;
		} else if (!nomeServidor.equals(other.nomeServidor))
			return false;
		return true;
	}

}
