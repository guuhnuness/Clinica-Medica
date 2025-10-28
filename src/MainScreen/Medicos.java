package MainScreen;

import java.sql.Date;
import javafx.beans.property.*;

public class Medicos {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty crm;
    private final IntegerProperty id_especialidade;
    private final ObjectProperty<Date> data;
    private final StringProperty telefone;

    // Construtor completo
    public Medicos(int id, String nome, String crm, int id_especialidade, Date data, String telefone) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.crm = new SimpleStringProperty(crm);
        this.id_especialidade = new SimpleIntegerProperty(id_especialidade);
        this.data = new SimpleObjectProperty<>(data);
        this.telefone = new SimpleStringProperty(telefone);
    }

    // Construtor sem ID (para inserções)
    public Medicos(String nome, String crm, int id_especialidade, Date data, String telefone) {
        this(0, nome, crm, id_especialidade, data, telefone);
    }

    // Getters e setters
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getNome() { return nome.get(); }
    public StringProperty nomeProperty() { return nome; }
    public void setNome(String nome) { this.nome.set(nome); }

    public String getCrm() { return crm.get(); }
    public StringProperty crmProperty() { return crm; }
    public void setCrm(String crm) { this.crm.set(crm); }

    public int getIdEspecialidade() { return id_especialidade.get(); }
    public IntegerProperty idEspecialidadeProperty() { return id_especialidade; }
    public void setIdEspecialidade(int idEspecialidade) { this.id_especialidade.set(idEspecialidade); }

    public Date getData() { return data.get(); }
    public ObjectProperty<Date> dataProperty() { return data; }
    public void setData(Date data) { this.data.set(data); }

    public String getTelefone() { return telefone.get(); }
    public StringProperty telefoneProperty() { return telefone; }
    public void setTelefone(String telefone) { this.telefone.set(telefone); }

    @Override
    public String toString() {
        return getNome(); // usado para exibir nome no ComboBox
    }
}
