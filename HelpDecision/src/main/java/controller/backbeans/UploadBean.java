package controller.backbeans;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

@ManagedBean(eager = true)
@RequestScoped
public class UploadBean {

	private Part arquivo;
	protected static final String NOME_DO_PROJETO = "HelpDecision";
	protected static final String CAMINHO_INTERNO = "src" + File.separator + "main" + File.separator + "webapp";
	protected static final String DIR_DO_TAR_GZ = "arquivo_log";
	protected static final String CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT = System.getProperty("user.dir")
			+ File.separator + NOME_DO_PROJETO + File.separator + CAMINHO_INTERNO + File.separator + DIR_DO_TAR_GZ;
	private ServicoFachada servicoFachada;

	private Servidor servidorSelecionado;
	private List<SelectItem> comboServidores;

	public UploadBean() {
		servicoFachada = new ServicoFachada();
	}

	public void upload() throws IOException {

		File dirUpload = new File(CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT);

		if (!dirUpload.exists()) {
			dirUpload.mkdirs();
		}

		arquivo.write(CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT + File.separator + buscarNomeDoArquivo(arquivo));
	}

	public List<ArquivoLog> getListaArquivoLog() {

		try {
			return servicoFachada.solicitarTodosArquivoLogDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

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
	
	public List<SelectItem> getComboServidores() throws SQLException {
		this.comboServidores = new ArrayList<SelectItem>();
		List<Servidor> servidores = null;
		try {
			servidores = servicoFachada.solicitarTodosServidoresDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Servidor servidor : servidores) {
			SelectItem item = new SelectItem(servidor.getIdServidor(), servidor.getNomeServidor());
			this.comboServidores.add(item);
		}
		return comboServidores;
	}

	public Servidor getServidorSelecionado() {
		return servidorSelecionado;
	}

	public void setServidorSelecionado(Servidor servidorSelecionado) {
		this.servidorSelecionado = servidorSelecionado;
	}
	

}