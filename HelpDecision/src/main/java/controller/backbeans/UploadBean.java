package controller.backbeans;

import java.io.File;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped
public class UploadBean {

	private Part arquivo;
	protected static final String NOME_DO_PROJETO = "HelpDecision";
	protected static final String CAMINHO_INTERNO = "src" + File.separator + "main" + File.separator +"webapp";
	protected static final String DIR_DO_TAR_GZ = "arquivo_log";
	protected static final String CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT = System.getProperty("user.dir")
			+ File.separator + NOME_DO_PROJETO + File.separator + CAMINHO_INTERNO + File.separator + DIR_DO_TAR_GZ;

	public UploadBean() {
		// Sem parametros, nao precisa ser private
	}

	public void upload() throws IOException {
		arquivo.write(CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT + File.separator + buscarNomeDoArquivo(arquivo));
	}

	public String buscarNomeDoArquivo(Part part) {

		String nomeArquivo;

		for (String cd : part.getHeader("content-disposition").split(";")) {
			nomeArquivo = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			return nomeArquivo.substring(nomeArquivo.lastIndexOf('/') + 1).substring(nomeArquivo.lastIndexOf('\\') + 1);
		}
		return null;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}
}
