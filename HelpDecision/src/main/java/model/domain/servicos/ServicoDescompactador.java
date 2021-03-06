package model.domain.servicos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

public class ServicoDescompactador {

	private ServicoDescompactador() {
	}

	public static ServicoDescompactador novo() {
		return new ServicoDescompactador();
	}

	public List<File> extrairTarGz(File arquivoTarGz, File localDestino) throws IOException, ArchiveException {

		List<File> arquivosExtraidos;

		File arquivoTar = unGzip(arquivoTarGz, localDestino);
		arquivosExtraidos = unTar(arquivoTar, localDestino);

		if (!arquivoTar.delete()) {
			System.err.println("Could not delete file: " + arquivoTar);
		}
		if (!arquivoTarGz.delete()) {
			System.err.println("Could not delete file: " + arquivoTarGz);
		}
		return arquivosExtraidos;
	}

	public List<File> unTar(File arquivoTar, File localDestino) throws IOException, ArchiveException {

		List<File> untaredFiles = new LinkedList<File>();
		InputStream inputStream = new FileInputStream(arquivoTar);
		TarArchiveInputStream tarArchiveInputStream = (TarArchiveInputStream) new ArchiveStreamFactory()
				.createArchiveInputStream("tar", inputStream);
		TarArchiveEntry entradaTar;
		byte[] buffer = new byte[1024];
		int bytesLido;

		while ((entradaTar = (TarArchiveEntry) tarArchiveInputStream.getNextEntry()) != null) {
			File outputFile = new File(localDestino, entradaTar.getName());

			if (entradaTar.isDirectory()) {
				if (!outputFile.exists() && !outputFile.mkdirs()) {
					System.err.println("Could not create file: " + outputFile.getAbsolutePath());
				}
			} else {
				OutputStream outputFileStream = new FileOutputStream(outputFile);

				while ((bytesLido = tarArchiveInputStream.read(buffer)) > 0) {
					outputFileStream.write(buffer, 0, bytesLido);
				}
				outputFileStream.close();
			}
			untaredFiles.add(outputFile);
		}
		tarArchiveInputStream.close();

		return untaredFiles;
	}

	public File unGzip(final File arquivoGz, final File localDestino) throws IOException {

		File outputFile = new File(localDestino, arquivoGz.getName().substring(0, arquivoGz.getName().length() - 3));
		GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(arquivoGz));
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		byte[] buffer = new byte[1];
		int bytesLido;

		while ((bytesLido = gzipInputStream.read(buffer)) > 0) {
			fileOutputStream.write(buffer, 0, bytesLido);
		}

		gzipInputStream.close();
		fileOutputStream.close();

		return outputFile;
	}
}
