package model.domain.agregadores;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;

@Entity
@Table(name = "tb_chamada_metodo_arquivo", schema = "public")
public class ChamadaMetodoArquivoLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "id_chamada_metodo")
	private ChamadaMetodo chamadaMetodo;
	@Column(name = "id_arquivo")
	private ArquivoLog arquivoLog;

	public ChamadaMetodoArquivoLog() {
	}

	private ChamadaMetodoArquivoLog(ChamadaMetodo chamadaMetodo, ArquivoLog arquivoLog) {
		this.chamadaMetodo = chamadaMetodo;
		this.arquivoLog = arquivoLog;
	}

	public static ChamadaMetodoArquivoLog nova(ChamadaMetodo chamadaMetodo, ArquivoLog arquivoLog) {
		return new ChamadaMetodoArquivoLog(chamadaMetodo, arquivoLog);
	}

	public ChamadaMetodo getChamadaMetodo() {
		return chamadaMetodo;
	}

	public void setChamadaMetodo(ChamadaMetodo chamadaMetodo) {
		this.chamadaMetodo = chamadaMetodo;
	}

	public ArquivoLog getArquivoLog() {
		return arquivoLog;
	}

	public void setArquivoLog(ArquivoLog arquivoLog) {
		this.arquivoLog = arquivoLog;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
