package controller.backbeans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import org.primefaces.context.RequestContext;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaArquivoLog;
import model.domain.servicos.ServicoFachada;

@ManagedBean(eager = true)
@RequestScoped

public class UploadBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Part arquivo;
	protected static final String NOME_DO_PROJETO = "HelpDecision";
	protected static final String CAMINHO_INTERNO = "src" + File.separator + "main" + File.separator + "webapp";
	protected static final String DIR_DO_TAR_GZ = "arquivo_log";
	protected static final String CAMINHO_ABSOLUTO_DO_DIRETORIO_DO_ARQUIVO_TAR_GZ = System.getProperty("user.dir")
			+ File.separator + NOME_DO_PROJETO + File.separator + CAMINHO_INTERNO + File.separator + DIR_DO_TAR_GZ;
	private ServicoFachada servicoFachada;
	private Servidor servidorSelecionado;
	private List<SelectItem> comboServidores;
	private ArquivoLog arquivoLog;
	// private ChamadaMetodoArquivoLogServidor agregador;
	private String nomeServidor;
	private String itemSelecionado;
	private String descricaoArquivo;
	private Boolean servidorCadastrado;

	public UploadBean() {
		servicoFachada = new ServicoFachada();
	}

	public void upload() throws IOException, SQLException {

		final RequestContext requestContext = RequestContext.getCurrentInstance();
		final Date dataTimeUpload = new Date(System.currentTimeMillis());
		final Servidor servidorSelecionado = servicoFachada.buscarServidorByID(new Integer(itemSelecionado));

		File dirUpload = new File(CAMINHO_ABSOLUTO_DO_DIRETORIO_DO_ARQUIVO_TAR_GZ);
		File fileTarGz;

		if (!dirUpload.exists()) {
			dirUpload.mkdirs();
		}

		// Escreve arquivo que foi dado upload em diretorio arquivo_log
		arquivo.write(CAMINHO_ABSOLUTO_DO_DIRETORIO_DO_ARQUIVO_TAR_GZ + File.separator + buscarNomeDoArquivo(arquivo));

		// Pega nome do arquivo .tar.gz
		fileTarGz = new File(
				CAMINHO_ABSOLUTO_DO_DIRETORIO_DO_ARQUIVO_TAR_GZ + File.separator + buscarNomeDoArquivo(arquivo));

		for (File file : servicoFachada.extrairArquivosTarGz(fileTarGz, dirUpload)) {

			final ArquivoLog arquivoLog = servicoFachada
					.inserirArquivoLog(FabricaArquivoLog.nova().novoArquivoLog(file.getName(), dataTimeUpload,
							this.descricaoArquivo, servidorSelecionado, file.getAbsolutePath()));

			try {
				if (servicoFachada.inserirChamadaMetodoList(servicoFachada.lerArquivoLog(arquivoLog))) {
					requestContext.execute("alertUploadRealizadoComSucesso()");
				} else {
					requestContext.execute("alertUploadNaoRealizadoArquivoJaExiste()");
				}
			} catch (NumberFormatException e) {
				requestContext.execute("alertUploadNaoRealizadoErro()");
				System.out.println("[UploadBean] Erro: " + e);
			}
		}
	}

	public void cadastrarServidor() throws SQLException {
		RequestContext requestContext = RequestContext.getCurrentInstance();

		if (servicoFachada.cadastrarServidor(0, getNomeServidor())) {
			requestContext.execute("alertServidorCadastrado()");
		} else {
			requestContext.execute("alertNaoServidorCadastrado()");
		}
	} 


	public void deleteActionArquivoLogServidor(ArquivoLog arquivoLog)
			throws SQLException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if(servicoFachada.solicitarFlagDeArquivoDeletado(arquivoLog)){
			requestContext.execute("alertDeleteComSucesso()");
		}
		else{
			requestContext.execute("alertErroAoDeletar()");
		}
	}

	// public String
	// deleteActionArquivoLogServidor(ChamadaMetodoArquivoLogServidor
	// arquivoLogServidor)
	// throws SQLException {
	// RequestContext requestContext = RequestContext.getCurrentInstance();
	// if
	// (servicoFachada.solicitarFlagDeArquivoDeletado(arquivoLogServidor.getArquivoLog()))
	// {
	// requestContext.execute("alertDeleteComSucesso()");
	// } else {
	// requestContext.execute("alertErroAoDeletar()");
	// }
	// //
	// servicoFachada.solicitarRemocaoEmCascataDoAgragadorPorArquivoLog(arquivoLogServidor.getArquivoLog());
	// return null;
	// }

	public List<ArquivoLog> getListaArquivoLogComServidor() {

		try {
			return servicoFachada.solicitarListaDeArquivoLogCadastradoDB();
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

	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public String getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(String itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public String getComentarioArquivo() {
		return descricaoArquivo;
	}

	public void setComentarioArquivo(String comentarioArquivo) {
		this.descricaoArquivo = comentarioArquivo;
	}

	public Boolean getServidorCadastrado() {
		return servidorCadastrado;
	}

	public void setServidorCadastrado(Boolean servidorCadastrado) {
		this.servidorCadastrado = servidorCadastrado;
	}

	public ArquivoLog getArquivoLog() {
		return arquivoLog;
	}

}