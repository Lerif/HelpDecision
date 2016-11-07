package testes;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

public class TesteCarregarDropDownServidores {

	ServicoFachada servicoFachada;
	public TesteCarregarDropDownServidores() {
		servicoFachada = new ServicoFachada();
	}

	@Test
	public void carregarDropDown() throws SQLException{
		List<Servidor> servidores = servicoFachada.solicitarTodosServidoresDB();
		assertNotNull(servidores);
	}
}
