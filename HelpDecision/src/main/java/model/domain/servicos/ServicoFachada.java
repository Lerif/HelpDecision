package model.domain.servicos;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;

public class ServicoFachada {

	private ArquivoLogServico servicoArquivoLog = new ArquivoLogServico();
	private ChamadaMetodoServico servicoChamadaMetodo = new ChamadaMetodoServico();
	private ServidorServico servicoServidor = new ServidorServico();
	private ChamadaMetodoArquivoLogServidorServico servicoAgregador = new ChamadaMetodoArquivoLogServidorServico();
	private ServicoDescompactador servicoDescompactador = ServicoDescompactador.novo();


	public ServicoFachada() {

	}

	public Boolean inserirNovoArquivo(List<ChamadaMetodo> listaChamadaMetodo, ArquivoLog arquivoLog, Servidor servidor)
			throws SQLException {
		// Faz insert na tb_chamada_metodo
		// Faz insert na tb_arquivo
		// Recuperar o id_servidor a partir do seu nome
		// Agrega listChamadaMetodo, ArquivoLog e Servidor
		return servicoAgregador.inserirAgregador(servicoAgregador.agregar(
				servicoChamadaMetodo.inserirDadosNaTbChamadaMetodo(listaChamadaMetodo),
				servicoArquivoLog.inserirDadosNaTbArquivo(arquivoLog), servicoServidor.recuperarIdServidor(servidor)));
	}

	// M�TODOS REFERENTE AO SERVICO CHAMADA METODO
	
	public List<ChamadaMetodo> buscarPorDuracao(long inicio, long fim){
		return servicoChamadaMetodo.buscarPorDuracao(inicio, fim);
	}
	
	public List<ChamadaMetodo> buscarPorData(Date inicio, Date fim){
		return servicoChamadaMetodo.buscarPorData(inicio, fim);
	}
	
	public List<ChamadaMetodo> buscarPorServidor(String nomeDoServidor){
		return servicoChamadaMetodo.buscarPorServido(nomeDoServidor);
	}

	// M�TODOS REFERENTE AO SERVICO ARQUIVO LOG
	public ArquivoLog solicitarCriacaoArquivoLog(int idArquivo, String nomeArquivo, java.sql.Date dataUpload,
			String descricao) {
		return servicoArquivoLog.criarArquivoLog(idArquivo, nomeArquivo, dataUpload, descricao);
	}

	public List<ArquivoLog> solicitarTodosArquivoLogDB() throws SQLException {
		return servicoArquivoLog.solicitarListaDeArquivoLogCadastradoDB();
	}

	// M�TODOS REFERENTE AO SERVICO SERVIDOR
	public Boolean cadastrarServidor(int idServidor, String nomeServidor) throws SQLException {
		return servicoServidor.cadastrarServidorDB(solicitarNovoServidor(idServidor, nomeServidor));
	}

	public Servidor solicitarNovoServidor(int idServidor, String nomeServidor) {
		return servicoServidor.criarServidor(idServidor, nomeServidor);
	}

	public List<Servidor> solicitarTodosServidoresDB() throws SQLException {
		return servicoServidor.solicitarListaDeServidoresCadastradosDB();
	} 
	
	// METODOS REFERENTE AO SERVICO DESCOMPACTADOR
	public void extrairTarGz (File arquivoTarGz, File localDestino){
		
		try {
			servicoDescompactador.extrairTarGz(arquivoTarGz, localDestino);
		} catch (IOException | ArchiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Getters e setters
	public void setServicoArquivoLog(ArquivoLogServico servicoArquivoLog) {
		this.servicoArquivoLog = servicoArquivoLog;
	}

	public void setServicoChamadaMetodo(ChamadaMetodoServico servicoChamadaMetodo) {
		this.servicoChamadaMetodo = servicoChamadaMetodo;
	}

	public void setServicoServidor(ServidorServico servicoServidor) {
		this.servicoServidor = servicoServidor;
	}

	public void setServicoAgregador(ChamadaMetodoArquivoLogServidorServico servicoAgregador) {
		this.servicoAgregador = servicoAgregador;
	}

}
