package model.domain.agregadores;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;

@Entity
@Table(name = "tb_chamada_metodo_arquivo", schema = "public")
public class ChamadaMetodoArquivoLogServidor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "id_chamada_metodo")
	private List<ChamadaMetodo> chamadasMetodos;
	@Column(name = "id_arquivo")
	private ArquivoLog arquivoLog;
	private Servidor servidor;
	
	public ChamadaMetodoArquivoLogServidor() {
	}

	private ChamadaMetodoArquivoLogServidor(List<ChamadaMetodo> chamadasMetodos, ArquivoLog arquivoLog, Servidor servidor) {
		this.chamadasMetodos = chamadasMetodos;
		this.arquivoLog = arquivoLog;
		this.servidor = servidor;
	}

	public static ChamadaMetodoArquivoLogServidor nova(List<ChamadaMetodo> chamadasMetodos, ArquivoLog arquivoLog, Servidor servidor) {
		return new ChamadaMetodoArquivoLogServidor(chamadasMetodos, arquivoLog, servidor);
	}

	public List<ChamadaMetodo> getChamadasMetodos() {
		return chamadasMetodos;
	}

	public void setChamadaMetodo(List<ChamadaMetodo> chamadaMetodo) {
		this.chamadasMetodos = chamadaMetodo;
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

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

}
