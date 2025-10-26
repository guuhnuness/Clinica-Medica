package MainScreen;

import javafx.beans.property.*;

public class Medicos {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty crm;
    private final IntegerProperty id_especialidade;
    private final StringProperty data;
    private final StringProperty telefone;

    public Medicos(int id, String nome, String crm, int id_especialidade, String data, String telefone) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.crm = new SimpleStringProperty(crm);
        this.id_especialidade = new SimpleIntegerProperty(id_especialidade);
        this.data = new SimpleStringProperty(data);
        this.telefone = new SimpleStringProperty(telefone);
       
    }

    public Medicos(String nome, String crm, int id_especialidade, String data, String telefone) {
        this(0, nome, crm, id_especialidade, data, telefone );
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty(){ return id; }
    public void setId(int id){ this.id.set(id); }

    public String getNome(){ return nome.get(); }
    public StringProperty nomeProperty(){ return nome; }
    public void setNome(String nome){ this.nome.set(nome); }
    
     public String getCrm(){ return crm.get(); }
    public StringProperty crmProperty(){ return crm; }
    public void setCrm(String crm){ this.crm.set(crm); }


    public int getEspecialidade(){ return id_especialidade.get(); }
    public IntegerProperty anoProperty(){ return id_especialidade; }
    public void setAno(int ano){ this.id_especialidade.set(ano); }
    
     public String getData(){ return data.get(); }
    public StringProperty dataProperty(){ return data; }
    public void setData(String data){ this.data.set(data); }
    
    public String getTelefone(){ return telefone.get(); }
    public StringProperty telefoneProperty(){ return telefone; }
    public void setTelefone(String telefone){ this.telefone.set(telefone); }
    
    

    @Override
    public String toString() { return getNome(); } // para ComboBox mostrar nome
}
