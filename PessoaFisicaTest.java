package backend.usuario;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import backend.Endereco;
import backend.FuncoesArquivos;
import backend.Medicamento;
import backend.Agenda;

class PessoaFisicaTest {

    @Test
    void getNomeArquivoUsos_deveMontarNomeComCpf() {
        PessoaFisica pessoa = novaPessoaBase();

        String nomeArquivo = pessoa.getNomeArquivoUsos();

        assertTrue(nomeArquivo.endsWith("Uso12345678900.txt"));
        assertTrue(nomeArquivo.contains("arquivosUsosUsuarios"));
    }

    @Test
    void getUsoListaUsoMedicamentos_deveRetornarNullQuandoListaNaoExiste() {
        PessoaFisica pessoa = novaPessoaBase();

        Uso uso = pessoa.getUsoListaUsoMedicamentos("Paracetamol");

        assertNull(uso);
    }

    @Test
    void getUsoListaUsoMedicamentos_deveRetornarUsoQuandoNomeExistir() {
        PessoaFisica pessoa = novaPessoaBase();
        Uso usoParacetamol = novoUso("Paracetamol");
        Uso usoDipirona = novoUso("Dipirona");
        pessoa.setListaUsoMedicamentos(new ArrayList<>(Arrays.asList(usoParacetamol, usoDipirona)), false);

        Uso uso = pessoa.getUsoListaUsoMedicamentos("Dipirona");

        assertNotNull(uso);
        assertEquals("Dipirona", uso.getRemedio().getNome());
    }

    @Test
    void toString_deveConterNullParaCamposSemListaUso() {
        PessoaFisica pessoa = novaPessoaBase();

        String pessoaString = pessoa.toString();

        assertTrue(pessoaString.contains(",null,"));
        assertTrue(pessoaString.contains("12345678900"));
        assertTrue(pessoaString.contains("Rua Teste/10/Apto 1"));
    }

    @Test
    void toString_deveUsarSeparadorPortatilNoNomeDoArquivo() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Paracetamol"))), false);

        String pessoaString = pessoa.toString();

        assertFalse(pessoaString.contains("backend\\usuario\\"));
    }

    @Test
    void toString_deveConterNomeArquivoUsosQuandoListaExistir() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Paracetamol"))), false);

        String pessoaString = pessoa.toString();

        assertTrue(pessoaString.contains("Uso12345678900.txt"));
        assertTrue(pessoaString.contains("arquivosUsosUsuarios"));
    }

    @Test
    void setParticularidade_deveAtualizarEndereco() {
        PessoaFisica pessoa = novaPessoaBase();
        Endereco novoEndereco = new Endereco("Rua Nova", "200", "Casa");

        pessoa.setParticularidade(novoEndereco);

        assertEquals("Rua Nova", pessoa.getEndereco().getNomeDaRua());
        assertEquals("200", pessoa.getEndereco().getNumero());
    }

    @Test
    void setCpf_deveAtualizarValorCorretamente() {
        PessoaFisica pessoa = novaPessoaBase();

        pessoa.setCpf("99999999999");

        assertEquals("99999999999", pessoa.getCpf());
    }

    @Test
    void setEndereco_deveAtualizarEnderecoCorretamente() {
        PessoaFisica pessoa = novaPessoaBase();
        Endereco novoEndereco = new Endereco("Rua Nova", "99", "Casa 2");

        pessoa.setEndereco(novoEndereco);

        assertEquals("Rua Nova", pessoa.getEndereco().getNomeDaRua());
        assertEquals("99", pessoa.getEndereco().getNumero());
        assertEquals("Casa 2", pessoa.getEndereco().getComplemento());
    }

    @Test
    void setListaUsoMedicamentos_semPersistencia_deveAtualizarLista() {
        PessoaFisica pessoa = novaPessoaBase();
        List<Uso> usos = new ArrayList<>(List.of(novoUso("Dipirona")));

        pessoa.setListaUsoMedicamentos(usos, false);

        assertNotNull(pessoa.getListaUsoMedicamentos());
        assertEquals(1, pessoa.getListaUsoMedicamentos().size());
        assertEquals("Dipirona", pessoa.getListaUsoMedicamentos().get(0).getRemedio().getNome());
    }

    @Test
    void resgatarListaUsoMedicamentosArquivo_deveRetornarListaVaziaQuandoArquivoNaoExiste() {
        List<Uso> listaUsos = PessoaFisica.resgatarListaUsoMedicamentosArquivo("arquivo-que-nao-existe-123.txt");

        assertTrue(listaUsos.isEmpty());
    }

    @Test
    void adicionarUsoNaListaUsoMedicamentos_deveCriarListaQuandoNula() {
        PessoaFisica pessoa = novaPessoaBase();
        Uso novoUso = novoUso("Vitamina D");

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.adicionarUsoNaListaUsoMedicamentos(novoUso);

            assertNotNull(pessoa.getListaUsoMedicamentos());
            assertEquals(1, pessoa.getListaUsoMedicamentos().size());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), eq(false)));
        }
    }

    @Test
    void removerUsoNaListaUsoMedicamentos_deveRemoverMedicamentoCorreto() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(
                new ArrayList<>(Arrays.asList(novoUso("Dipirona"), novoUso("Paracetamol"))),
                false);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.removerUsoNaListaUsoMedicamentos("Dipirona");

            assertEquals(1, pessoa.getListaUsoMedicamentos().size());
            assertEquals("Paracetamol", pessoa.getListaUsoMedicamentos().get(0).getRemedio().getNome());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), eq(false)));
        }
    }

    @Test
    void removerUsoNaListaUsoMedicamentos_naoDeveFalharQuandoListaNula() {
        PessoaFisica pessoa = novaPessoaBase();

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.removerUsoNaListaUsoMedicamentos("Qualquer");

            assertNull(pessoa.getListaUsoMedicamentos());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), anyBoolean()), never());
        }
    }

    @Test
    void atualizarQntRemediosListaUsoMedicamentos_deveAtualizarQuantidade() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Dipirona"))), false);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.atualizarQntRemediosListaUsoMedicamentos("Dipirona", 5);

            assertEquals(5, pessoa.getUsoListaUsoMedicamentos("Dipirona").getQtdDisponivel());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), eq(false)));
        }
    }

    @Test
    void atualizarQntRemediosListaUsoMedicamentos_comListaNula_deveLancarExcecao() {
        PessoaFisica pessoa = novaPessoaBase();

        assertThrows(IllegalStateException.class,
                () -> pessoa.atualizarQntRemediosListaUsoMedicamentos("Dipirona", 10));
    }



    @Test
    void salvarArquivoUsos_listaNula_naoDeveLancarExcecao() {
        PessoaFisica pessoa = novaPessoaBase();
        assertDoesNotThrow(() -> pessoa.salvarArquivoUsos());
    }

    @Test
    void toString_enderecoNulo_naoDeveLancarExcecao() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setEndereco(null);
        assertDoesNotThrow(() -> pessoa.toString());
    }

    @Test
    void removerUsoNaLista_mesmoNomeRemoveUsoCorreto() {
        PessoaFisica pessoa = novaPessoaBase();
        Uso usoManha = novoUso("Dipirona");
        usoManha.setDose(2);
        Uso usoNoite = novoUso("Dipirona");
        usoNoite.setDose(1);
        pessoa.setListaUsoMedicamentos(new ArrayList<>(Arrays.asList(usoManha, usoNoite)), false);

        pessoa.removerUsoNaListaUsoMedicamentos("Dipirona");

        assertEquals(1, pessoa.getListaUsoMedicamentos().size());
        assertEquals(1, pessoa.getListaUsoMedicamentos().get(0).getDose());
    }

    @Test
    void getNomeArquivoUsos_deveUsarSeparadorDoSistema() {
        assertFalse(novaPessoaBase().getNomeArquivoUsos().contains("\\"));
    }

    @Test
    void addFarmaciaAosContatos_deveAdicionarFarmaciaEGravar() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setContatosFarmacias(null);
        
        backend.farmacia.PessoaJuridica farmacia = new backend.farmacia.PessoaJuridica(
            "Farmacia Popular", "123456", "farmacia@test.com", "12.345.678/0001-90", "senha123", null
        );

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), anyString())).thenReturn(true);
            
            pessoa.addFarmaciaAosContatos(farmacia);
            
            assertNotNull(pessoa.getContatosFarmacias());
            assertEquals(1, pessoa.getContatosFarmacias().getContatos().size());
            assertEquals("Farmacia Popular", pessoa.getContatosFarmacias().getContatos().get(0).getNome());
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()));
        }
    }

    @Test
    void removerContatoFarmacia_deveRemoverFarmaciaEGravar() {
        PessoaFisica pessoa = novaPessoaBase();
        Agenda agenda = new Agenda();
        backend.farmacia.PessoaJuridica farmacia = new backend.farmacia.PessoaJuridica(
            "Farmacia Popular", "123456", "farmacia@test.com", "12.345.678/0001-90", "senha123", null
        );
        agenda.adicionarContato(farmacia);
        pessoa.setContatosFarmacias(agenda);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), anyString())).thenReturn(true);
            
            pessoa.removerContatoFarmacia("Farmacia Popular");
            
            assertTrue(pessoa.getContatosFarmacias().getContatos().isEmpty());
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()));
        }
    }

    @Test
    void removerContatoFarmacia_comAgendaNula_naoDeveFazerNada() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setContatosFarmacias(null);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.removerContatoFarmacia("Farmacia Popular");
            assertNull(pessoa.getContatosFarmacias());
            mockArquivos.verifyNoInteractions();
        }
    }

    @Test
    void adicionarContatoMedico_deveAdicionarMedicoEGravar() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setContatosMedicos(null);
        
        Medico medico = new Medico(
            "Dr. House", "987654", "house@test.com", "senha123", "Diagnostico"
        );

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), anyString())).thenReturn(true);
            
            pessoa.adicionarContatoMedico(medico);
            
            assertNotNull(pessoa.getContatosMedicos());
            assertEquals(1, pessoa.getContatosMedicos().getContatos().size());
            assertEquals("Dr. House", medico.getNome());
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()));
        }
    }

    @Test
    void removerContatoMedico_deveRemoverMedicoEGravar() {
        PessoaFisica pessoa = novaPessoaBase();
        Agenda agenda = new Agenda();
        Medico medico = new Medico(
            "Dr. House", "987654", "house@test.com", "senha123", "Diagnostico"
        );
        agenda.adicionarContato(medico);
        pessoa.setContatosMedicos(agenda);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), anyString())).thenReturn(true);
            
            pessoa.removerContatoMedico("Dr. House");
            
            assertTrue(pessoa.getContatosMedicos().getContatos().isEmpty());
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()));
        }
    }

    @Test
    void removerContatoMedico_comAgendaNula_naoDeveFazerNada() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setContatosMedicos(null);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.removerContatoMedico("Dr. House");
            assertNull(pessoa.getContatosMedicos());
            mockArquivos.verifyNoInteractions();
        }
    }

    @Test
    void resgatarListaUsoMedicamentosArquivo_deveRetornarListaDeUsos() {
        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.obterListaLinhas("arquivosUsos.txt"))
                        .thenReturn(Arrays.asList("Paracetamol,1,08:00/20:00,7,14,8,12"));

            List<Uso> result = PessoaFisica.resgatarListaUsoMedicamentosArquivo("arquivosUsos.txt");
            
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("Paracetamol", result.get(0).getRemedio().getNome());
        }
    }

    @Test
    void testMetodosBancoDeDados() {
        PessoaFisica pessoa = novaPessoaBase();
        assertTrue(pessoa.estabelecerConexaoBD("path"));
        assertDoesNotThrow(() -> pessoa.salvarObejtoBancoDeDados(new Object()));
        assertNull(pessoa.recuperarObjetoBancoDeDados("path"));
    }

    @Test
    void setContatosMedicos_comModificarArquivo_deveSalvar() {
        PessoaFisica pessoa = novaPessoaBase();
        Agenda agenda = new Agenda();
        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), anyString())).thenReturn(true);
            pessoa.setContatosMedicos(agenda, true);
            assertEquals(agenda, pessoa.getContatosMedicos());
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()));
        }
    }

    @Test
    void setContatosFarmacias_comModificarArquivo_deveSalvar() {
        PessoaFisica pessoa = novaPessoaBase();
        Agenda agenda = new Agenda();
        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), anyString())).thenReturn(true);
            pessoa.setContatosFarmacias(agenda, true);
            assertEquals(agenda, pessoa.getContatosFarmacias());
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()));
        }
    }

    @Test
    void getParticularidade_deveRetornarEndereco() {
        PessoaFisica pessoa = novaPessoaBase();
        assertEquals(pessoa.getEndereco(), pessoa.getParticularidade());
    }

    @Test
    void atualizarQntRemediosListaUsoMedicamentos_deveImprimirAchou() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Dipirona"))), false);
        
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent, true));
        
        try {
            pessoa.atualizarQntRemediosListaUsoMedicamentos("Dipirona", 5);
            assertTrue(outContent.toString().contains("ACHOU"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void salvarDadosArquivo_deveInserirQuandoUsuarioNaoExiste() {
        PessoaFisica pessoa = novaPessoaBase();

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent, true));

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), eq("Fulano")))
                    .thenReturn(false);

            pessoa.salvarDadosArquivo();

            mockArquivos.verify(() -> FuncoesArquivos.appendLinhaArquivo(anyString(), anyString()));
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()), never());
            assertTrue(outContent.toString().contains("usuario NAO existe"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void salvarDadosArquivo_deveAtualizarQuandoUsuarioJaExiste() {
        PessoaFisica pessoa = novaPessoaBase();

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent, true));

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), eq("Fulano")))
                    .thenReturn(true);

            pessoa.salvarDadosArquivo();

            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), eq("Fulano"), anyString()));
            mockArquivos.verify(() -> FuncoesArquivos.appendLinhaArquivo(anyString(), anyString()), never());
            assertTrue(outContent.toString().contains("usuario JA existe"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void toString_comTodosOsCamposPreenchidos_deveConterValores() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Paracetamol"))), false);
        
        Agenda medicos = new Agenda();
        medicos.adicionarContato(new Medico("Dr. Silva", "1234", "silva@test.com", "senha", "Cardio"));
        pessoa.setContatosMedicos(medicos);

        Agenda farmacias = new Agenda();
        farmacias.adicionarContato(new backend.farmacia.PessoaJuridica("Farmacia A", "5678", "a@test.com", "12.345.678/0001-90", "senha", null));
        pessoa.setContatosFarmacias(farmacias);

        String resultNormal = pessoa.toString();
        String resultEncriptado = pessoa.toString(true);

        assertTrue(resultNormal.contains("silva@test.com"));
        assertTrue(resultNormal.contains("a@test.com"));
        assertTrue(resultEncriptado.contains("silva@test.com"));
        assertTrue(resultEncriptado.contains("a@test.com"));
    }

    @Test
    void removerUsoNaListaUsoMedicamentos_elementoNaoExistente_naoDeveLancarExcecao() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Dipirona"))), false);

        assertDoesNotThrow(() -> pessoa.removerUsoNaListaUsoMedicamentos("Paracetamol"));
        assertEquals(1, pessoa.getListaUsoMedicamentos().size());
    }

    @Test
    void getNomeArquivoUsuarios_deveRetornarCaminhoCorreto() {
        assertEquals("backend\\usuario\\RegistroUsuarios.txt", PessoaFisica.getNomeArquivoUsuarios());
    }

    @Test
    void resgatarUsuarioArquivo_comDadosValidos() {
        String linhaCsv = "Fulano,21999999999,fulano@teste.com,senha123,12345678900,Rua Teste/10/null/null/null/null/null/null,null,null,null";
        criarArquivoUsuariosTemporario(linhaCsv);
        try {
            PessoaFisica resultado = PessoaFisica.resgatarUsuarioArquivo("fulano@teste.com", "senha123", false, false);
            assertNotNull(resultado);
            assertEquals("Fulano", resultado.getNome());
            assertEquals("12345678900", resultado.getCpf());
        } finally {
            deletarArquivoUsuariosTemporario();
        }
    }

    @Test
    void resgatarUsuarioArquivo_comIgnorarSenha() {
        String linhaCsv = "Fulano,21999999999,fulano@teste.com,senha123,12345678900,Rua Teste/10/null/null/null/null/null/null,null,null,null";
        criarArquivoUsuariosTemporario(linhaCsv);
        try {
            PessoaFisica resultado = PessoaFisica.resgatarUsuarioArquivo("fulano@teste.com", "senha_errada", true, false);
            assertNotNull(resultado);
            assertEquals("Fulano", resultado.getNome());
        } finally {
            deletarArquivoUsuariosTemporario();
        }
    }

    @Test
    void resgatarUsuarioArquivo_naoEncontrado() {
        String linhaCsv = "Fulano,21999999999,fulano@teste.com,senha123,12345678900,Rua Teste/10/null/null/null/null/null/null,null,null,null";
        criarArquivoUsuariosTemporario(linhaCsv);
        try {
            PessoaFisica resultado = PessoaFisica.resgatarUsuarioArquivo("outro@teste.com", "senha123", false, false);
            assertNull(resultado);
        } finally {
            deletarArquivoUsuariosTemporario();
        }
    }

    @Test
    void resgatarUsuarioArquivo_comAgendaEUsos() {
        String linhaCsv = "Fulano,21999999999,fulano@teste.com,senha123,12345678900,Rua Teste/10/null/null/null/null/null/null,arquivosUsos.txt,agendaMedicos,agendaFarmacias";
        criarArquivoUsuariosTemporario(linhaCsv);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class);
             MockedStatic<Agenda> mockAgenda = mockStatic(Agenda.class)) {
             
            mockArquivos.when(() -> FuncoesArquivos.obterListaLinhas("arquivosUsos.txt"))
                        .thenReturn(Arrays.asList("Paracetamol,1,08:00/20:00,7,14,8,12"));
                        
            mockAgenda.when(() -> Agenda.stringToAgenda(eq("agendaMedicos"), anyString(), eq("medico"), anyBoolean(), anyBoolean()))
                      .thenReturn(new Agenda());
            mockAgenda.when(() -> Agenda.stringToAgenda(eq("agendaFarmacias"), anyString(), eq("farmacia"), anyBoolean(), anyBoolean()))
                      .thenReturn(new Agenda());

            PessoaFisica resultado = PessoaFisica.resgatarUsuarioArquivo("fulano@teste.com", "senha123", false, false);
            
            assertNotNull(resultado);
            assertEquals("Fulano", resultado.getNome());
            assertNotNull(resultado.getListaUsoMedicamentos());
            assertEquals(1, resultado.getListaUsoMedicamentos().size());
            assertNotNull(resultado.getContatosMedicos());
            assertNotNull(resultado.getContatosFarmacias());
        } finally {
            deletarArquivoUsuariosTemporario();
        }
    }

    private void criarArquivoUsuariosTemporario(String conteudo) {
        try {
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("backend", "usuario"));
            java.nio.file.Files.writeString(java.nio.file.Paths.get("backend", "usuario", "RegistroUsuarios.txt"), conteudo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletarArquivoUsuariosTemporario() {
        try {
            java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get("backend", "usuario", "RegistroUsuarios.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PessoaFisica novaPessoaBase() {
        Endereco endereco = new Endereco("Rua Teste", "10", "Apto 1");
        return new PessoaFisica("Fulano", "21999999999", "fulano@teste.com", "12345678900", "senha123", endereco);
    }

    private Uso novoUso(String nomeRemedio) {
        return new Uso(
                new Medicamento(nomeRemedio),
                1,
                new ArrayList<>(Arrays.asList("08:00", "20:00")),
                7,
                14,
                8,
                12);
    }
}