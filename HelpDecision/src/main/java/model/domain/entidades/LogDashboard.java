package model.domain.entidades;

public class LogDashboard {
	
	private String nomeMetodo;
	private int quantidadeDessaChamada;
	private float porcentagemTotal;
	private long tempoTotal;
	private float tempoMedio;
	private long tempoMenor;
	private long tempoMaior;
	private int quantidadeChamadasTotal;
	
	private LogDashboard(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal,	
			long tempoTotal, float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal){
		this.nomeMetodo = nomeMetodo;
		this.quantidadeDessaChamada = quantidadeChamadas;
		this.porcentagemTotal = porcentagemTotal;
		this.tempoTotal = tempoTotal;
		this.tempoMedio = tempoMedio;
		this.tempoMenor = tempoMenor;
		this.tempoMaior = tempoMaior;
		this.quantidadeChamadasTotal = quantidadeChamadasTotal;
	}
	
	public static LogDashboard novo(String nomeMetodo, int quantidadeChamadas, float porcentagemTotal,	
			long tempoTotal, float tempoMedio, long tempoMenor, long tempoMaior, int quantidadeChamadasTotal){
		return new LogDashboard(nomeMetodo,  quantidadeChamadas,  porcentagemTotal,	
				 tempoTotal,  tempoMedio,  tempoMenor,  tempoMaior, quantidadeChamadasTotal);
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
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
