package model.domain.servicos;

public class ServicoFachada {

	@SuppressWarnings("unused")
	private ArquivoLogServico servicoArquivoLog;
	@SuppressWarnings("unused")
	private ChamadaMetodoServico servicoChamadaMetodo;

	public ServicoFachada() {
		setServicoArquivoLog(ArquivoLogServico.novo());
		setServicoChamadaMetodo(ChamadaMetodoServico.novo());
	}

	public void setServicoArquivoLog(ArquivoLogServico servicoArquivoLog) {
		this.servicoArquivoLog = servicoArquivoLog;
	}

	public void setServicoChamadaMetodo(ChamadaMetodoServico servicoChamadaMetodo) {
		this.servicoChamadaMetodo = servicoChamadaMetodo;
	}

}
