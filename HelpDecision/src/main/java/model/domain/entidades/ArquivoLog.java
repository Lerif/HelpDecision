package model.domain.entidades;

import java.util.Date;

public class ArquivoLog {

	private String nomeArquivo;
	private Date dataUpload;

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

}
