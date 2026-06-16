package backend.usuario;

import backend.FuncoesArquivos;
import backend.Pessoa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Medico extends Pessoa {
    private static final Logger LOGGER = Logger.getLogger(Medico.class.getName());
    public static final String nomeArquivoMedicos = "backend\\usuario\\RegistroMedicos.txt";
    private String especialidade;

    // construtor
    public Medico(String nome, String telefone, String email, String senha, String especialidade) {
        super(nome, telefone, email, senha);
        this.especialidade = especialidade;
    }

    // get e set
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    // get e set particularidade
    @Override
    public Object getParticularidade() {
        return getEspecialidade();
    }

    @Override
    public <T> void setParticularidade(T novaParticularidade) {
        setEspecialidade((String) novaParticularidade);
    }

    @Override
    public String toString(){
        return PessoaToString() + "," + this.getEspecialidade();
    }   

    public void salvarDadosArquivo(){
        List<String> listaMedico = new ArrayList<>();
        listaMedico.add(this.toString());
        FuncoesArquivos.salvarListaEmArquivo(nomeArquivoMedicos, listaMedico, true);
    }

    public static Medico resgatarMedicoArquivo(String emailFornecido, String senhaFornecida, boolean ignorarSenha){
        try (FileReader fr = new FileReader(nomeArquivoMedicos);
             BufferedReader br = new BufferedReader(fr)){

            String linha = br.readLine();

            while (linha != null){
                String[] dadosLinha = linha.split(",");
                String email = dadosLinha[2];
                String senha = dadosLinha[3];

                if (email.equals(emailFornecido) && (ignorarSenha || senhaFornecida.equals(senha))){
                    String telefone = dadosLinha[1];
                    String nome = dadosLinha[0];
                    String especialidade = dadosLinha[4];
                    return new Medico(nome, telefone, email, senha, especialidade);
                }
                linha = br.readLine();
            }
            LOGGER.info("medico n foi encontrado no arquivo");
            return null;
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, "n foi possivel resgatar o medico", e);
            return null;
        }
    }
}
