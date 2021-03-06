package model.domain.entidades;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import model.domain.util.CalendarioUtil;

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
	private Timestamp dataInicio;
	@Column(name = "data_fim")
	private Timestamp dataFim;
	@Column(name = "duracao")
	private long duracao;
	@Column(name = "id_elemento")
	private String idElemento;
	@Column(name = "tipo_elemento")
	private String tipoElemento;
	private ArquivoLog arquivo;

	public ChamadaMetodo() {

	}

	private ChamadaMetodo(int idChamadaMetodo, String nomeMetodo, Date dataInicio, Date dataFim, String idElemento,
			String tipoElemento, long duracao, ArquivoLog arquivoLog) {
		setIdChamadaMetodo(idChamadaMetodo);
		setNomeMetodo(nomeMetodo);
		setDataInicio(CalendarioUtil.dateParaSqlTimestamp(dataInicio));
		setDataFim(CalendarioUtil.dateParaSqlTimestamp(dataFim));
		setIdElemento(idElemento);
		setTipoElemento(tipoElemento);
		setDuracao(duracao);
		setArquivo(arquivoLog);
	}

	private ChamadaMetodo(int idChamadaMetodo, String nomeMetodo, Date dataInicio, Date dataFim, String idElemento,
			String tipoElemento, long duracao) {
		setIdChamadaMetodo(idChamadaMetodo);
		setNomeMetodo(nomeMetodo);
		setDataInicio(CalendarioUtil.dateParaSqlTimestamp(dataInicio));
		setDataFim(CalendarioUtil.dateParaSqlTimestamp(dataFim));
		setIdElemento(idElemento);
		setTipoElemento(tipoElemento);
		setDuracao(duracao);
	}

	public static ChamadaMetodo nova(int idChamadaMetodo, String nomeMetodo, Date dataInicio, Date dataFim,
			String idElemento, String tipoElemento, long duracao, ArquivoLog arquivoLog) {
		return new ChamadaMetodo(idChamadaMetodo, nomeMetodo, dataInicio, dataFim, idElemento, tipoElemento, duracao,
				arquivoLog);
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

	public Timestamp getDataInicio() {
		return dataInicio;
	}

	public Timestamp getDataFim() {
		return dataFim;
	}

	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setDataFim(Timestamp dataFim) {
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

	public int getIdChamadaMetodo() {
		return idChamadaMetodo;
	}

	public void setIdChamadaMetodo(int idChamadaMetodo) {
		this.idChamadaMetodo = idChamadaMetodo;
	}

	public ArquivoLog getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoLog arquivo) {
		this.arquivo = arquivo;
	}
}
