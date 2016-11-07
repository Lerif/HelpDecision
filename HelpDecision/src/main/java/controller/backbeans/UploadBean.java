package controller.backbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

@ManagedBean
public class UploadBean {

	private ServicoFachada servicoFachada = new ServicoFachada();

	private List<Servidor> servidores = new ArrayList<Servidor>();

	public UploadBean() {
		carregarDropDownServidores();
	}
	
	public void carregarDropDownServidores() {
	
	}

	public List<Servidor> getServidores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}

}
