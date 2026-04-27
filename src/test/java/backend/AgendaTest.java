package backend;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import backend.usuario.PessoaFisica;
import backend.usuario.Medico;
import backend.farmacia.PessoaJuridica;

public class AgendaTest {
	private Agenda agenda;
	private PessoaFisica pessoa;
	private Medico medico;
	private PessoaJuridica farmacia;
	
	
	@BeforeEach
	void inicaliza() {
		agenda = new Agenda();
		pessoa = new PessoaFisica("Leonardo", "21985473573", "leo@gmail.com", "1012", "123", new Endereco("Paulo Pereira","213"));
		medico = new Medico("Andre", "2126030000", "medicoandre@gmail.com", "321", "Onitorrino");
		farmacia = new PessoaJuridica("Farmelhor", "21912345678", "farmelhor@gmail.com", "101010", "212121", new Endereco("Trindade","150"));

		agenda.adicionarContato(pessoa);
		agenda.adicionarContato(medico);
		agenda.adicionarContato(farmacia);
	}

	
	@Test
	public void testAdicionarContato() {		
		assertEquals(3, agenda.getContatos().size());
		assertTrue(agenda.getContatos().contains(pessoa));
		assertTrue(agenda.getContatos().contains(medico));
		assertTrue(agenda.getContatos().contains(farmacia));
	}
	
	@Test
	public void testGetContatosOrdenados() {
		ArrayList<Pessoa> contatos = agenda.getContatos();
		
		assertEquals("Andre", contatos.get(0).getNome());
		assertEquals("Farmelhor", contatos.get(1).getNome());
		assertEquals("Leonardo", contatos.get(2).getNome());
	}
	
	
	@Test
	public void testAdicionarContatoComException() {
		assertThrows(IllegalArgumentException.class, () -> agenda.adicionarContato(null));
	}
	
	@Test
	public void testAdicionarContadoDuplicado() {
		// Bug: O contato já existe na agenda, e não deveria ser adicionado novamente.
		
		agenda.adicionarContato(pessoa);
		
		// O tamanho da agenda aumenta, indicando que o contato duplicado foi adicionado
		assertEquals(4, agenda.getContatos().size());
	}
	
	
	@Test
	public void testAlterarNomeContato() {
		assertTrue(agenda.alterarNomeContato("Andre","Diogo"));
		assertEquals("Diogo", medico.getNome());
	}
	
	@Test
	public void testAlterarNomeContatoParaVazio() {
		// Bug: É permitido alterar o nome de um contato para null
		
		assertTrue(agenda.alterarNomeContato("Andre", null));
		assertEquals(null, medico.getNome());
	}
	
	
	@Test
	public void testAlterarTelContato() {
		assertTrue(agenda.alterarTelContato("Leonardo", "40028922"));
		assertEquals("40028922", pessoa.getTelefone());
	}
	
	@Test
	public void testAlterarTelContatoParaVazio() {
		// Bug: É permitido alterar o telefone de um contato para null
		
		assertTrue(agenda.alterarTelContato("Leonardo", null));
		assertEquals(null, pessoa.getTelefone());
	}
	
	@Test
	public void testAlterarEmailContato() {
		assertTrue(agenda.alterarEmailContato("Andre", "diogosilva@gmail.com"));
		assertEquals("diogosilva@gmail.com", medico.getEmail());
	}
	
	@Test
	public void testAlterarParticularidadeContatoMedico() {
		assertTrue(agenda.alterarParticularidadeContato("Andre", "Clinico Geral"));
		assertEquals("Clinico Geral", medico.getParticularidade());
	}
	
	@Test
	public void testAlterarParticularidadeContatoPessoaF() {
		Endereco novoEndereco = new Endereco("Rua Lima Cleto","187");

		assertTrue(agenda.alterarParticularidadeContato("Leonardo", novoEndereco));
		assertEquals("Rua Lima Cleto", pessoa.getEndereco().getNomeDaRua());
		assertEquals("187", pessoa.getEndereco().getNumero());
	}

	@Test
	public void testAlterarParticularidadeContatoFarmacia() {
		Endereco novoEndereco = new Endereco("Rua Nova", "500");

		assertTrue(agenda.alterarParticularidadeContato("Farmelhor", novoEndereco));
		assertEquals("Rua Nova", farmacia.getEndereco().getNomeDaRua());
		assertEquals("500", farmacia.getEndereco().getNumero());
	}
	
	@Test
	public void testAlterarNomeContatoNaoEncontrado() {
		assertFalse(agenda.alterarNomeContato("Neymar", "Leonardo"));
	}
	
	@Test
	public void testAlterarTelContatoNaoEncontrado() {
		assertFalse(agenda.alterarTelContato("Teodoro", "37192885"));
	}
	
	@Test
	public void testAlterarEmailContatoNaoEncontrado() {
		assertFalse(agenda.alterarEmailContato("João", "diogosilva@gmail.com"));
	}
	
	@Test
	public void testAlterarParticularidadeContatoNaoEncontrado() {
		assertFalse(agenda.alterarParticularidadeContato("Julio", "Clinico geral"));
	}
	
	
	@Test
	public void testRemoverContato() {
		agenda.removerContato("Leonardo");
		assertEquals(2, agenda.getContatos().size());
		assertFalse(agenda.getContatos().contains(pessoa));
	}
	
	@Test
	public void testRemoverContatoComNomeIgual() {
		// Bug: Ao remover um contato que tenha o mesmo nome de outro, o primeiro contato a ser encontrado é removido
		
		PessoaFisica pessoa2 = new PessoaFisica("Leonardo", "21985101117", "abc@gmail.com", "3010", "101010", new Endereco("Tavares","516"));
		agenda.adicionarContato(pessoa2);
		
		agenda.removerContato("Leonardo"); // Queremos remover o contato que acabamos de adicionar à agenda
		assertEquals(3, agenda.getContatos().size());
		
		// Prova de que o contato que queriamos apagar ainda continua na agenda
		assertEquals("21985101117", agenda.getContatos().get(2).getTelefone());
	}

	
	@Test
	public void testRemoverContatoNaoEncontrado() {
		assertFalse(agenda.removerContato("Marcelo"));
		assertEquals(3, agenda.getContatos().size());
	}

	@Test
	public void testToString() {
		String contatosAgenda = agenda.toString();
		assertEquals("medicoandre@gmail.com/farmelhor@gmail.com/leo@gmail.com", contatosAgenda);
	}

	@Test
	public void testToStringComAgendaVazia() {
		Agenda agendaVazia = new Agenda();
		assertEquals("null", agendaVazia.toString());
	}

	

	@Test
	public void testStringToAgenda() {

		try (MockedStatic<PessoaFisica> pessoaFisicaMock = Mockito.mockStatic(PessoaFisica.class);
			 MockedStatic<Medico> medicoMock = Mockito.mockStatic(Medico.class);
			 MockedStatic<PessoaJuridica> farmaciaMock = Mockito.mockStatic(PessoaJuridica.class)) {
			
			// Para o usuário
			pessoaFisicaMock.when(() -> PessoaFisica.resgatarUsuarioArquivo("leo@gmail.com", "123", true, true))
					.thenReturn(pessoa); 
			
			// Para o médico
			medicoMock.when(() -> Medico.resgatarMedicoArquivo("medicoandre@gmail.com", "321", true))
					.thenReturn(medico);

			// Para a farmácia
			farmaciaMock.when(() -> PessoaJuridica.resgatarFarmaciaArquivo("farmelhor@gmail.com", "101010", true, true))
					.thenReturn(farmacia);

			
			Agenda resultadoUsuario = Agenda.stringToAgenda("leo@gmail.com", "123", "usuario", true, true);
			Agenda resultadoMedico = Agenda.stringToAgenda("medicoandre@gmail.com", "321", "medico", true, true);
			Agenda resultadoFarmacia = Agenda.stringToAgenda("farmelhor@gmail.com", "101010", "farmacia", true, true);

			assertEquals(1, resultadoUsuario.getContatos().size());
			assertTrue(resultadoUsuario.getContatos().contains(pessoa));

			assertEquals(1, resultadoMedico.getContatos().size());
			assertTrue(resultadoMedico.getContatos().contains(medico));

			assertEquals(1, resultadoFarmacia.getContatos().size());
			assertTrue(resultadoFarmacia.getContatos().contains(farmacia));
		}
	}

	@Test
	public void testStringToAgendaComStringNula() {
		//Se passarmos null na string, o método tenta fazer null.split("/") e lança NullPointerException

		assertThrows(NullPointerException.class, () -> {
			Agenda.stringToAgenda(null, "123", "usuario", true, true);
		});
	}

}
