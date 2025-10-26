package MainScreen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Clinica.java.PacientesDAO;

public class MainController implements Initializable {

    // ==== TABELA DE PACIENTES ====
    @FXML private TableView<Pacientes> tabelaPacientes;
    @FXML private TableColumn<Pacientes, Integer> coluna_id_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_nome_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_cpf_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_data_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_telefone_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_email_paciente;
   

    // Campos de paciente (UI)
    @FXML private TextField campo_nome_paciente;
    @FXML private TextField campo_cpf_paciente;
    @FXML private TextField campo_data_paciente;
    @FXML private TextField campo_telefone_paciente;
    @FXML private TextField campo_email_paciente;

    private final ObservableList<Pacientes> listaPacientes = FXCollections.observableArrayList();

    // ==== PÁGINAS ====
    @FXML private Pane home_page;
    @FXML private Pane pacientes_page;
    @FXML private Pane medicos_page;
    @FXML private Pane consultas_page;
    @FXML private Pane top_bar;
    @FXML private Button btn_pacientes;
    @FXML private Button btn_home;
    @FXML private Button btn_medicos;
    @FXML private Button btn_sair;
    @FXML private Button btn_consultas;

    // ==== TABELA DE MÉDICOS ====
   
   
    @FXML private TableView<Medicos> tabelaMedicos;
    @FXML private TableColumn<Medicos, String> coluna_nome_medico;
    @FXML private TableColumn<Medicos, String> coluna_cpf_medico;
    @FXML private TableColumn<Medicos, Integer> coluna_data_medico;
    @FXML private TableColumn<Medicos, Integer> coluna_telefone_medico;
    @FXML private TableColumn<Medicos, Integer> coluna_email_medico;
    
    // Campos de medicos (UI)
    @FXML private TextField campo_nome_medico;
    @FXML private TextField campo_cpf_medico;
    @FXML private TextField campo_idespecialidade_medico;
    @FXML private TextField campo_data_medico;
    @FXML private TextField campo_telefone_medico;
    @FXML private TextField campo_email_medico;
   
    
    

    private final ObservableList<Medicos> medicos = FXCollections.observableArrayList();

    // ==== DAOs ====
    private final PacientesDAO pacienteDAO = new PacientesDAO();
    private final MedicosDAO medicoDAO = new MedicosDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar colunas da tabela de pacientes
        coluna_id_paciente.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        coluna_nome_paciente.setCellValueFactory(cell -> cell.getValue().nomeProperty());
        coluna_cpf_paciente.setCellValueFactory(cell -> cell.getValue().cpfProperty());
        coluna_data_paciente.setCellValueFactory(cell -> cell.getValue().data_nascimentoProperty());
        coluna_telefone_paciente.setCellValueFactory(cell -> cell.getValue().telefoneProperty());
        coluna_email_paciente.setCellValueFactory(cell -> cell.getValue().emailProperty());

        // colunaCurso mostra o nome do curso com base em cursoId
        coluna_nome_medico.setCellValueFactory(data -> {
            int medicoId = data.getValue().get();
            Medicos c = medicos.stream().filter(x -> x.getId() == medicoId).findFirst().orElse(null);
            return new SimpleStringProperty(c != null ? c.getNome() : "N/A");
        });

        campoStatus.setItems(FXCollections.observableArrayList("Ativo", "Inativo"));
        tabelaAlunos.setItems(listaAlunos);

        // Configurar colunas de cursos
        coluna_nome_curso.setCellValueFactory(c -> c.getValue().nomeProperty());
        coluna_nivel_curso.setCellValueFactory(c -> c.getValue().nivelProperty());
        coluna_ano_curso.setCellValueFactory(c -> c.getValue().anoProperty().asObject());
        cursoTable.setItems(cursos);

        // Opções do nível de curso
        campo_nivel_curso.setItems(FXCollections.observableArrayList(
                "Graduação",
                "Pós-graduação",
                "Doutorado",
                "Bacharelado",
                "Tecnólogo"
        ));

        // Carregar dados do BD
        cursos.addAll(cursoDAO.listarCursos());
        if (campoCurso != null) {
            campoCurso.setItems(cursos); // popula combobox de curso
        }
        listaPacientes.addAll(alunoDAO.listarPacientes());
    }

    // ====================== PACIENTES ======================
    @FXML
    private void adicionarAluno(MouseEvent event) {
       
            String nome = campoNome.getText().trim();
            String cpfText = campoCpf.getText().trim();
            String dataText = campoData.getText().trim();
            String telefoneText = campoTelefone.getText().trim();
            String emailText = campoEmail.getText().trim();

            if (nome.isEmpty() || cpfText.isEmpty() || dataText.isEmpty()
                    || telefoneText.isEmpty() || emailText.isEmpty()) {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }

            

            Pacientes novo = new Pacientes(nome, cpfText, dataText, telefoneText, emailText);
            PacientesDAO.inserirPaciente(novo); // grava no BD
            listaPacientes.add(novo);       // atualiza tabela
            limparCampos(null);
        } 
    }

    @FXML
    private void removerPaciente(MouseEvent event) {
        Pacientes sel = tabelaPacientes.getSelectionModel().getSelectedItem();
        if (sel != null) {
            PacientesDAO.deletarPaciente(sel.getId());
            listaPacientes.remove(sel);
        } else {
            mostrarAlerta("Erro", "Selecione um aluno para remover.");
        }
    }

    @FXML
    private void limparCampos(ActionEvent event) {
        campoNome.clear();
        campoCpf.clear();
        campoData.clear();
        campoTelefone.clear();
        campoEmail.clear();
       
    }

    // ====================== MÉDICOS ======================
    @FXML
    public void adicionarMédico(MouseEvent event) {
        try {
            String nome = campo_nome_curso.getText().trim();
            String nivel = campo_nivel_curso.getValue();
            String anoText = campo_ano_curso.getText().trim();

            if (nome.isEmpty() || nivel == null || anoText.isEmpty()) {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
                return;
            }

            int ano = Integer.parseInt(anoText);
            Medicos curso = new Medicos(nome, nivel, ano);
            cursoDAO.inserirCurso(curso);
            cursos.add(curso);

            if (campoCurso != null) {
                campoCurso.setItems(cursos); // atualiza combobox
            }
            limparCursos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Ano inválido.");
        }
    }

    @FXML
    public void removerCurso(MouseEvent event) {
        Medicos sel = cursoTable.getSelectionModel().getSelectedItem();
        if (sel != null) {
            cursoDAO.deletarCurso(sel.getId());
            cursos.remove(sel);
            if (campoCurso != null) {
                campoCurso.setItems(cursos);
            }
        } else {
            mostrarAlerta("Erro", "Selecione um curso para remover.");
        }
    }

    @FXML
    public void limparCursos() {
        campo_nome_curso.clear();
        campo_ano_curso.clear();
        campo_nivel_curso.setValue(null);
    }

    // ====================== PÁGINAS ======================
    @FXML
    public void BtnPacientes(MouseEvent event) {
        home_page.setVisible(false);
        medicos_page.setVisible(false);
        consultas_page.setVisible(false);
        pacientes_page.setVisible(true);
        top_bar.setVisible(true);
        resetarBotoes();
       btn_pacientes.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    public void BtnHome(MouseEvent event) {
        home_page.setVisible(true);
        medicos_page.setVisible(false);
        pacientes_page.setVisible(false);
        consultas_page.setVisible(false);
        top_bar.setVisible(true);
        resetarBotoes();
        btn_home.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    public void BtnMedicos(MouseEvent event) {
        home_page.setVisible(false);
        medicos_page.setVisible(true);
        pacientes_page.setVisible(false);
        consultas_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_medicos.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    public void BtnConsultas(MouseEvent event) {
        consultas_page.setVisible(true);
        home_page.setVisible(false);
        medicos_page.setVisible(false);
       pacientes_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_consultas.setStyle("-fx-background-color: #141313;");
    }

    private void resetarBotoes() {
        btn_pacientes.setStyle("-fx-background-color: #353A56;");
        btn_home.setStyle("-fx-background-color: #353A56;");
        btn_medicos.setStyle("-fx-background-color: #353A56;");
        btn_consultas.setStyle("-fx-background-color: #353A56;");
        btn_sair.setStyle("-fx-background-color: #353A56;");
    }

    // ====================== ALERTA ======================
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void sairAplicacao(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de saída");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja sair da aplicação?");

        resetarBotoes();
        btn_sair.setStyle("-fx-background-color: #141313;");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
