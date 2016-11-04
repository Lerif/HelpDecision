package model.domain.entidades;

import java.util.Date;

public class ChamadaMetodo {

	private String nomeMetodo;
	private Date dataInicio;
	private Date dataFim;
	private String idElemento;
	private String tipoElemento;
	private long duracao;

	private ChamadaMetodo(String nomeMetodo, Date dataInicio, Date dataFim, String idElemento, String tipoElemento,
			long duracao) {
		setNomeMetodo(nomeMetodo);
		setDataInicio(dataInicio);
		setDataFim(dataFim);
		setIdElemento(idElemento);
		setTipoElemento(tipoElemento);
		setDuracao(duracao);
	}

	public static ChamadaMetodo nova(String nomeMetodo, Date dataInicio, Date dataFim, String idElemento,
			String tipoElemento, long duracao) {
		return new ChamadaMetodo(nomeMetodo, dataInicio, dataFim, idElemento, tipoElemento, duracao);
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(String idElemento) {
		this.idElemento = idElemento;
	}

	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public long getDuracao() {
		return duracao;
	}

	public void setDuracao(long duracao) {
		this.duracao = duracao;
	}

}
