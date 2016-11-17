package model.domain.entidades;

public class Dashboard {

	private int quantidadeDessaChamada;
	private float porcentagemTotal;
	private long tempoTotal;
	private float tempoMedio;
	private long tempoMenor;
	private long tempoMaior;
	private int quantidadeChamadasTotal;

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

	public static Dashboard novo(int quantidadeChamadas, float porcentagemTotal, long tempoTotal,
			float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal) {
		return new Dashboard(quantidadeChamadas, porcentagemTotal, tempoTotal, tempoMedio, tempoMenor, tempoMaior,
				quantidadeChamadasTotal);
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
}
