package controller.backbeans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped
public class UploadBean {

	private Part arquivo;
	
	public UploadBean() {
		
	}
	
	public void upload() throws IOException{
		arquivo.write("//home//skannon//" + buscarNomeDoArquivo(arquivo));
	}
	
	public String buscarNomeDoArquivo(Part part){
		
		String nomeArquivo;
		
		for (String cd : part.getHeader("content-disposition").split(";")){
			nomeArquivo = cd.substring(cd.indexOf('=') +1).trim().replace("\"", "");
			return nomeArquivo.substring(nomeArquivo.lastIndexOf('/') +1).substring(nomeArquivo.lastIndexOf('\\') +1);
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
