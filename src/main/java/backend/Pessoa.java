package backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//pessoa no sentido de pessoa fisica ou pessoa juridica
public abstract class Pessoa implements Comparable<Pessoa>, Serializable {
    private static final Logger LOGGER = Logger.getLogger(Pessoa.class.getName());
    private String nome; // nome da pessoa ou razao social
    private String telefone;
    private String email;
    private String senha;

    // construtores
    public Pessoa(String nome, String telefone, String email, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    // get Nome
    public String getNome() {
        return this.nome;
    }

    // set nome
    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    // get telefone
    public String getTelefone() {
        return this.telefone;
    }

    // set telefone
    public void setTelefone(String novoTelefone) {
        this.telefone = novoTelefone;
    }

    // get email
    public String getEmail() {
        return this.email;
    }

    // set email
    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public abstract Object getParticularidade(); // get endereco se pessoa fisica ou juridica OU get especialidade se
                                                 // medico

    public abstract <T> void setParticularidade(T novaParticularidade); // set endereco se pessoa fisica ou juridica OU
                                                                        // set especialidade se medico

    public int compareTo(Pessoa outraPessoa) {
        /*
         * if(this.getNome().compareTo(outraPessoa.getNome()) > 0) return 1;
         * if(this.getNome().compareTo(outraPessoa.getNome()) < 0) return -1; return 0;
         */
        return this.getNome().compareTo(outraPessoa.getNome());
    }

    public String PessoaToString() {
        ArrayList<String> listaValoresAtributos = new ArrayList<>();

        listaValoresAtributos.add(this.getNome());
        listaValoresAtributos.add(this.getTelefone());
        listaValoresAtributos.add(this.getEmail());
        listaValoresAtributos.add(this.getSenha());

        return String.join(",", listaValoresAtributos);
    }

    public String PessoaToString(Boolean botarSenhaEncriptada) {
        ArrayList<String> listaValoresAtributos = new ArrayList<>();

        listaValoresAtributos.add(this.getNome());
        listaValoresAtributos.add(this.getTelefone());
        listaValoresAtributos.add(this.getEmail());
        String senhaEncriptada = "";

        if (botarSenhaEncriptada){
            try {
                senhaEncriptada = Autenticacao.encriptarSenha(this.getEmail(), this.getSenha());
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                LOGGER.log(Level.SEVERE, "Falha ao encriptar a senha", e);
            }
            listaValoresAtributos.add(senhaEncriptada);
        }
        else{
            listaValoresAtributos.add(this.getSenha());
        }

        return String.join(",", listaValoresAtributos);
    }

    // função para gravar objeto no arquivo
    public void salvarObjetoArquivo(String nomeArquivo, Object objeto){
        try (FileOutputStream arquivo = new FileOutputStream(nomeArquivo);
             ObjectOutputStream objetoOut = new ObjectOutputStream(arquivo)){
            objetoOut.writeObject(objeto);
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE, "Falha ao salvar objeto no arquivo " + nomeArquivo, e);
        }
    }

    //função para resgatar objeto do arquivo
    public Object recuperarObjetoArquivo(String nomeArquivo){
        try (FileInputStream arquivo = new FileInputStream(nomeArquivo);
             ObjectInputStream objetoIn = new ObjectInputStream(arquivo)){
            return objetoIn.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            LOGGER.log(Level.SEVERE, "Falha ao recuperar objeto do arquivo " + nomeArquivo, e);
            return null;
        }
    }

}
