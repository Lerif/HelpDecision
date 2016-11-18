package model.domain.entidades;

import java.io.File;
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
	private Integer idArquivo;
	@Column(name = "nome_arquivo")
	private String nomeArquivo;
	@Column(name = "data_upload")
	private Date dataUpload;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "caminho_arquivo")
	private String caminhoDoArquivo;
	
	private Servidor servidor;
	
	public ArquivoLog() {

	}

	private ArquivoLog(Integer idArquivo, String nomeArquivo, Date dataUpload, String descricao, Servidor servidor, String caminhoDoArquivo) {
		this.idArquivo = idArquivo;
		this.nomeArquivo = nomeArquivo;
		this.dataUpload = dataUpload;
		this.descricao = descricao;
		this.servidor = servidor;
		this.caminhoDoArquivo = caminhoDoArquivo;
	}
	
	public static ArquivoLog novo(File file, Date dataUpload, String descricao, Servidor servidor) {
		return novo(null, file, dataUpload, descricao, servidor);
	}
	
	public static ArquivoLog novo(Integer idArquivo, File file, Date dataUpload, String descricao, Servidor servidor) {
		return new ArquivoLog(idArquivo, file.getName(), dataUpload, descricao, servidor, file.getAbsolutePath());
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

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}
	
}
