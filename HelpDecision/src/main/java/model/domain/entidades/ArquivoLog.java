package model.domain.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_arquivo", schema = "public")
public class ArquivoLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_arquivo")
	private int idArquivo;
	@Column(name = "nome_arquivo")
	private String nomeArquivo;
	@Column(name = "data_upload")
	private Date dataUpload;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "id_servidor")
	private int idServidor;

	private ArquivoLog(String nomeArquivo, Date dataUpload) {
		this.nomeArquivo = nomeArquivo;
		this.dataUpload = dataUpload;
	}

	public static ArquivoLog novo(String nomeArquivo, Date dataUpload) {
		return new ArquivoLog(nomeArquivo, dataUpload);
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Date getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}

	public int getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(int idArquivo) {
		this.idArquivo = idArquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIdServidor() {
		return idServidor;
	}

	public void setIdServidor(int idServidor) {
		this.idServidor = idServidor;
	}

}
