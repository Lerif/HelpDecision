import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.Test;

import model.domain.servicos.ServicoDescompactador;

public class Testes {
	
	ServicoDescompactador descompactador = ServicoDescompactador.novo();
	String arquivoGz = "";
	String arquivoDestino = "";
	String arquivoTar = "";
	String arquivoTarGz = "";
	
	@Test
	public void test_ungzip() {
		
		File gz = new File(arquivoGz);
		File dest = new File(arquivoDestino);
		
		try {
			descompactador.unGzip(gz, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_untar(){
		
		File tar = new File(arquivoTar);
		File dest = new File(arquivoDestino);
		
		try {
			descompactador.unTar(tar, dest);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_extrair_tar_gz(){
		
		File tarGz = new File(arquivoTarGz);
		File dest = new File(arquivoDestino);
		
		try {
			descompactador.extrairTarGz(tarGz, dest);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
	}

}
