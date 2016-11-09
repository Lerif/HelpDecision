package model.domain.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_chamada_metodo", schema = "public")
public class ChamadaMetodo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_chamada_metodo")
	private int idChamadaMetodo;
	@Column(name = "nome_metodo")
	private String nomeMetodo;
	@Column(name = "data_inicio")
	private Date dataInicio;
	@Column(name = "data_fim")
	private Date dataFim;
	@Column(name = "duracao")
	private long duracao;
	@Column(name = "id_elementro")
	private String idElemento;
	@Column(name = "tipo_elementro")
	private String tipoElemento;

	public ChamadaMetodo() {

	}

	private ChamadaMetodo(int idChamadaMetodo, String nomeMetodo, Date dataInicio, Date dataFim, String idElemento,
			String tipoElemento, long duracao) {
		setIdChamadaMetodo(idChamadaMetodo);
		setNomeMetodo(nomeMetodo);
		setDataInicio(dataInicio);
		setDataFim(dataFim);
		setIdElemento(idElemento);
		setTipoElemento(tipoElemento);
		setDuracao(duracao);
	}

	public static ChamadaMetodo nova(int idChamadaMetodo, String nomeMetodo, Date dataInicio, Date dataFim,
			String idElemento, String tipoElemento, long duracao) {
		return new ChamadaMetodo(idChamadaMetodo, nomeMetodo, dataInicio, dataFim, idElemento, tipoElemento, duracao);
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

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio =  dataInicio;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim =  dataFim;
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

	public int getIdChamadaMetodo() {
		return idChamadaMetodo;
	}

	public void setIdChamadaMetodo(int idChamadaMetodo) {
		this.idChamadaMetodo = idChamadaMetodo;
	}

}
