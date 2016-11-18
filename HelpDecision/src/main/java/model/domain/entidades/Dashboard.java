package model.domain.entidades;

public class Dashboard {

	private String nomeMetodo;
	private int quantidadeDessaChamada;
	private float porcentagemTotal;
	private long tempoTotal;
	private float tempoMedio;
	private long tempoMenor;
	private long tempoMaior;
	private int quantidadeChamadasTotal;
	private String nomeServidor;
	private int idServidor;

	private Dashboard(int quantidadeChamadas, float porcentagemTotal, long tempoTotal, float tempoMedio,
			long tempoMenor, long tempoMaior, int quantidadeChamadasTotal) {
		this.quantidadeDessaChamada = quantidadeChamadas;
		this.porcentagemTotal = porcentagemTotal;
		this.tempoTotal = tempoTotal;
		this.tempoMedio = tempoMedio;
		this.tempoMenor = tempoMenor;
		this.tempoMaior = tempoMaior;
		this.quantidadeChamadasTotal = quantidadeChamadasTotal;
	}

	private Dashboard(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal, long tempoTotal,
			float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal, String nomeServidor) {
		this.nomeMetodo = nomeMetodo;
		this.quantidadeDessaChamada = quantidadeChamadas;
		this.porcentagemTotal = porcentagemTotal;
		this.tempoTotal = tempoTotal;
		this.tempoMedio = tempoMedio;
		this.tempoMenor = tempoMenor;
		this.tempoMaior = tempoMaior;
		this.quantidadeChamadasTotal = quantidadeChamadasTotal;
		this.nomeServidor = nomeServidor;
	}

	private Dashboard(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal, long tempoTotal,
			float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal, String nomeServidor,
			int idServidor) {
		this.nomeMetodo = nomeMetodo;
		this.quantidadeDessaChamada = quantidadeChamadas;
		this.porcentagemTotal = porcentagemTotal;
		this.tempoTotal = tempoTotal;
		this.tempoMedio = tempoMedio;
		this.tempoMenor = tempoMenor;
		this.tempoMaior = tempoMaior;
		this.quantidadeChamadasTotal = quantidadeChamadasTotal;
		this.nomeServidor = nomeServidor;
		this.idServidor = idServidor;
	}

	public static Dashboard novo(int quantidadeChamadas, float porcentagemTotal, long tempoTotal, float tempoMedio,
			long tempoMenor, long tempoMaior, int quantidadeChamadasTotal) {
		return new Dashboard(quantidadeChamadas, porcentagemTotal, tempoTotal, tempoMedio, tempoMenor, tempoMaior,
				quantidadeChamadasTotal);
	}

	public static Dashboard novo(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal, long tempoTotal,
			float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal, String nomeServidor) {
		return new Dashboard(nomeMetodo, quantidadeChamadas, porcentagemTotal, tempoTotal, tempoMedio, tempoMenor,
				tempoMaior, quantidadeChamadasTotal, nomeServidor);
	}

	public static Dashboard novo(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal, long tempoTotal,
			float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal, String nomeServidor,
			int idServidor) {
		return new Dashboard(nomeMetodo, quantidadeChamadas, porcentagemTotal, tempoTotal, tempoMedio, tempoMenor,
				tempoMaior, quantidadeChamadasTotal, nomeServidor, idServidor);
	}

	public float getPorcentagemTotal() {
		return porcentagemTotal;
	}

	public void setPorcentagemTotal(float porcentagemTotal) {
		this.porcentagemTotal = porcentagemTotal;
	}

	public long getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(long tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

	public float getTempoMedio() {
		return tempoMedio;
	}

	public void setTempoMedio(float tempoMedio) {
		this.tempoMedio = tempoMedio;
	}

	public long getTempoMenor() {
		return tempoMenor;
	}

	public void setTempoMenor(long tempoMenor) {
		this.tempoMenor = tempoMenor;
	}

	public long getTempoMaior() {
		return tempoMaior;
	}

	public void setTempoMaior(long tempoMaior) {
		this.tempoMaior = tempoMaior;
	}

	public int getQuantidadeDessaChamada() {
		return quantidadeDessaChamada;
	}

	public void setQuantidadeDessaChamada(int quantidadeDessaChamada) {
		this.quantidadeDessaChamada = quantidadeDessaChamada;
	}

	public int getQuantidadeChamadasTotal() {
		return quantidadeChamadasTotal;
	}

	public void setQuantidadeChamadasTotal(int quantidadeChamadasTotal) {
		this.quantidadeChamadasTotal = quantidadeChamadasTotal;
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}

	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public int getIdServidor() {
		return idServidor;
	}

	public void setIdServidor(int idServidor) {
		this.idServidor = idServidor;
	}
}
