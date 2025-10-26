package MainScreen;

import javafx.beans.property.*;

public class Pacientes {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty cpf;
    private final StringProperty data_nascimento;
    private final StringProperty telefone;
    private final StringProperty email;
 

    // Carregado do BD (com id)
    public Pacientes(int id, String nome, String cpf, String data_nascimento, String telefone, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cpf = new SimpleStringProperty(cpf);
        this.data_nascimento = new SimpleStringProperty(data_nascimento);
        this.telefone = new SimpleStringProperty(data_nascimento);
        this.email = new SimpleStringProperty(email);
    }

    // Para inserir novo (sem id)
    public Pacientes(String nome, String cpf, String data_nascimento, String telefone, String email) {
        this(0, nome, cpf, data_nascimento, telefone, email);
    }

    public int getId(){ return id.get(); }
    public IntegerProperty idProperty(){ return id; }
    public void setId(int id){ this.id.set(id); }

    public String getNome(){ return nome.get(); }
    public StringProperty nomeProperty(){ return nome; }
    public void setNome(String nome){ this.nome.set(nome); }

    public String getCpf(){ return cpf.get(); }
    public StringProperty cpfProperty(){ return cpf; }
    public void setCpf(String cpf){ this.cpf.set(cpf); }

    public String getDataNascimento(){ return data_nascimento.get(); }
    public StringProperty data_nascimentoProperty(){ return data_nascimento; }
    public void setDataNascimento(String data_nascimento){ this.data_nascimento.set(data_nascimento); }

    public String getTelefone(){ return telefone.get(); }
    public StringProperty telefoneProperty(){ return telefone; }
    public void setTelefone(String telefone){ this.telefone.set(telefone); }
    
    public String getEmail(){ return email.get(); }
    public StringProperty emailProperty(){ return email; }
    public void setEmail(String email){ this.email.set(email); }
    
    
}
