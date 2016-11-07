package model.domain.servicos;

import java.sql.SQLException;
import java.util.List;

import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaServidor;
import model.domain.repositorios.RepositorioServidor;

public class ServidorServico {

	private RepositorioServidor servidorRepositorio = new RepositorioServidor();

	public ServidorServico() {
		
	}



	public Servidor criarServidor(int idServidor, String nomeServidor) {
		return FabricaServidor.novo().novoServidor(idServidor, nomeServidor);
	}

	public Boolean cadastrarServidorDB(Servidor servidor) {
		return servidorRepositorio.insert(servidor);
	}

	public List<Servidor> solicitarListaDeServidoresCadastradosDB() throws SQLException {
		return servidorRepositorio.findAll();
	}

	public Servidor recuperarIdServidor(Servidor servidor) throws SQLException {
		return servidorRepositorio.findByName(servidor);
	}

}
