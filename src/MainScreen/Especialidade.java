package MainScreen;

import javafx.beans.property.*;

public class Especialidade {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty descricao;

    public Especialidade(int id, String nome, String descricao) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public Especialidade(String nome, String descricao) {
        this(0, nome, descricao);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getNome() { return nome.get(); }
    public StringProperty nomeProperty() { return nome; }
    public void setNome(String nome) { this.nome.set(nome); }

    public String getDescricao() { return descricao.get(); }
    public StringProperty descricaoProperty() { return descricao; }
    public void setDescricao(String descricao) { this.descricao.set(descricao); }

    @Override
    public String toString() {
        return nome.get();
    }
}
