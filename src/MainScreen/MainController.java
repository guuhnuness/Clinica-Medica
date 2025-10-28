package MainScreen;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Clinica.java.*;
import javafx.beans.property.SimpleStringProperty;

public class MainController implements Initializable {

    // ======= PACIENTES =======
    @FXML private TableView<Pacientes> tabelaPacientes;
    @FXML private TableColumn<Pacientes, Integer> coluna_id_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_nome_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_cpf_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_data_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_telefone_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_email_paciente;
    @FXML private TableColumn<Pacientes, String> coluna_logradouro_paciente;

    @FXML private TextField campo_nome_paciente;
    @FXML private TextField campo_cpf_paciente;
    @FXML private TextField campo_data_paciente;
    @FXML private TextField campo_telefone_paciente;
    @FXML private TextField campo_email_paciente;
    @FXML private TextField campo_logradouro_paciente;

    private final ObservableList<Pacientes> listaPacientes = FXCollections.observableArrayList();
    private final PacientesDAO pacienteDAO = new PacientesDAO();

    // ======= MEDICOS =======
    @FXML private TableView<Medicos> tabelaMedicos;
    @FXML private TableColumn<Medicos, Integer> coluna_id_medico;
    @FXML private TableColumn<Medicos, String> coluna_nome_medico;
    @FXML private TableColumn<Medicos, String> coluna_crm_medico;
    @FXML private TableColumn<Medicos, String> coluna_especialidade_medico;
    @FXML private TableColumn<Medicos, Date> coluna_data_medico;
    @FXML private TableColumn<Medicos, String> coluna_telefone_medico;

    @FXML private TextField campo_nome_medico;
    @FXML private TextField campo_crm_medico;
    @FXML private ComboBox<Especialidade> campo_especialidade_medico;
    @FXML private DatePicker campo_data_medico;
    @FXML private TextField campo_telefone_medico;

    private final ObservableList<Medicos> listaMedicos = FXCollections.observableArrayList();
    private final MedicosDAO medicoDAO = new MedicosDAO();

    // ======= ESPECIALIDADES =======
    @FXML private TableView<Especialidade> tabelaEspecialidades;
    @FXML private TableColumn<Especialidade, Integer> coluna_id_especialidade;
    @FXML private TableColumn<Especialidade, String> coluna_nome_especialidade;
    @FXML private TableColumn<Especialidade, String> coluna_descricao_especialidade;

    @FXML private TextField campo_nome_especialidade;
    @FXML private TextArea campo_descricao_especialidade;

    private final ObservableList<Especialidade> listaEspecialidades = FXCollections.observableArrayList();
    private final EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

    // ======= PÁGINAS =======
    @FXML private Pane home_page;
    @FXML private Pane pacientes_page;
    @FXML private Pane medicos_page;
    @FXML private Pane consultas_page;
    @FXML private Pane especialidades_page;
    @FXML private Pane top_bar;

    @FXML private Button btn_pacientes;
    @FXML private Button btn_home;
    @FXML private Button btn_medicos;
    @FXML private Button btn_consultas;
    @FXML private Button btn_sair;

    // ================= INITIALIZE =================
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // === PACIENTES ===
        coluna_id_paciente.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        coluna_nome_paciente.setCellValueFactory(cell -> cell.getValue().nomeProperty());
        coluna_cpf_paciente.setCellValueFactory(cell -> cell.getValue().cpfProperty());
        coluna_data_paciente.setCellValueFactory(cell -> cell.getValue().data_nascimentoProperty());
        coluna_telefone_paciente.setCellValueFactory(cell -> cell.getValue().telefoneProperty());
        coluna_email_paciente.setCellValueFactory(cell -> cell.getValue().emailProperty());
        coluna_logradouro_paciente.setCellValueFactory(cell -> cell.getValue().logradouroProperty());
        tabelaPacientes.setItems(listaPacientes);
        listaPacientes.addAll(pacienteDAO.listarPacientes());

        // === MÉDICOS ===
        coluna_id_medico.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        coluna_nome_medico.setCellValueFactory(cell -> cell.getValue().nomeProperty());
        coluna_crm_medico.setCellValueFactory(cell -> cell.getValue().crmProperty());
        coluna_especialidade_medico.setCellValueFactory(cell -> {
            int idEsp = cell.getValue().getIdEspecialidade();
            Especialidade esp = especialidadeDAO.buscarPorId(idEsp); // Método que deve existir
            return new SimpleStringProperty(esp != null ? esp.getNome() : "");
        });
        coluna_data_medico.setCellValueFactory(cell -> cell.getValue().dataProperty());
        coluna_telefone_medico.setCellValueFactory(cell -> cell.getValue().telefoneProperty());
        tabelaMedicos.setItems(listaMedicos);
        listaMedicos.addAll(medicoDAO.listarMedicos());

        // Preencher ComboBox de especialidades
        campo_especialidade_medico.setItems(FXCollections.observableArrayList(especialidadeDAO.listarEspecialidades()));

        // === ESPECIALIDADES ===
        coluna_id_especialidade.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        coluna_nome_especialidade.setCellValueFactory(cell -> cell.getValue().nomeProperty());
        coluna_descricao_especialidade.setCellValueFactory(cell -> cell.getValue().descricaoProperty());
        tabelaEspecialidades.setItems(listaEspecialidades);
        listaEspecialidades.addAll(especialidadeDAO.listarEspecialidades());
    }

    // ================= PACIENTES =================
    @FXML
    private void adicionarPaciente(MouseEvent event) {
        String nome = campo_nome_paciente.getText().trim();
        String cpf = campo_cpf_paciente.getText().trim();
        String data = campo_data_paciente.getText().trim();
        String telefone = campo_telefone_paciente.getText().trim();
        String email = campo_email_paciente.getText().trim();
        String logradouro = campo_logradouro_paciente.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || data.isEmpty() || telefone.isEmpty() || email.isEmpty() || logradouro.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
            return;
        }

        Pacientes novo = new Pacientes(nome, cpf, data, telefone, email, logradouro);
        pacienteDAO.inserirPaciente(novo);
        listaPacientes.add(novo);
        limparCamposPaciente();
    }

    @FXML
    private void removerPaciente(MouseEvent event) {
        Pacientes sel = tabelaPacientes.getSelectionModel().getSelectedItem();
        if (sel != null) {
            pacienteDAO.deletarPaciente(sel.getId());
            listaPacientes.remove(sel);
        } else {
            mostrarAlerta("Erro", "Selecione um paciente para remover.");
        }
    }

    @FXML
    private void limparCamposPaciente() {
        campo_nome_paciente.clear();
        campo_cpf_paciente.clear();
        campo_data_paciente.clear();
        campo_telefone_paciente.clear();
        campo_email_paciente.clear();
        campo_logradouro_paciente.clear();
    }

    // ================= MÉDICOS =================
    @FXML
    private void adicionarMedico(MouseEvent event) {
        String nome = campo_nome_medico.getText().trim();
        String crm = campo_crm_medico.getText().trim();
        Especialidade especialidade = campo_especialidade_medico.getValue();
        Date data = campo_data_medico.getValue() != null ? Date.valueOf(campo_data_medico.getValue()) : null;
        String telefone = campo_telefone_medico.getText().trim();

        if (nome.isEmpty() || crm.isEmpty() || especialidade == null || data == null || telefone.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos são obrigatórios.");
            return;
        }

        Medicos medico = new Medicos(nome, crm, especialidade.getId(), data, telefone);
        medicoDAO.inserirMedico(medico);
        listaMedicos.add(medico);
        limparCamposMedico();
    }

    @FXML
    private void removerMedico(MouseEvent event) {
        Medicos sel = tabelaMedicos.getSelectionModel().getSelectedItem();
        if (sel != null) {
            medicoDAO.deletarMedico(sel.getId());
            listaMedicos.remove(sel);
        } else {
            mostrarAlerta("Erro", "Selecione um médico para remover.");
        }
    }

    @FXML
    private void limparCamposMedico() {
        campo_nome_medico.clear();
        campo_crm_medico.clear();
        campo_especialidade_medico.getSelectionModel().clearSelection();
        campo_data_medico.setValue(null);
        campo_telefone_medico.clear();
    }

    // ================= ESPECIALIDADES =================
    @FXML
    private void adicionarEspecialidade(MouseEvent event) {
        String nome = campo_nome_especialidade.getText().trim();
        String descricao = campo_descricao_especialidade.getText().trim();

        if (nome.isEmpty()) {
            mostrarAlerta("Erro", "O campo nome é obrigatório.");
            return;
        }

        Especialidade nova = new Especialidade(nome, descricao);
        especialidadeDAO.adicionarEspecialidade(nova);
        listaEspecialidades.clear();
        listaEspecialidades.addAll(especialidadeDAO.listarEspecialidades());
        limparCamposEspecialidade();
        mostrarAlerta("Sucesso", "Especialidade adicionada com sucesso!");
    }

    @FXML
    private void removerEspecialidade(MouseEvent event) {
        Especialidade sel = tabelaEspecialidades.getSelectionModel().getSelectedItem();
        if (sel != null) {
            especialidadeDAO.removerEspecialidade(sel.getId());
            listaEspecialidades.remove(sel);
            mostrarAlerta("Sucesso", "Especialidade removida com sucesso!");
        } else {
            mostrarAlerta("Erro", "Selecione uma especialidade para remover.");
        }
    }

    @FXML
    private void limparCamposEspecialidade() {
        campo_nome_especialidade.clear();
        campo_descricao_especialidade.clear();
    }

    // ================= PÁGINAS =================
    @FXML
    private void BtnPacientes(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(true);
        medicos_page.setVisible(false);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(false);
        top_bar.setVisible(true);
        resetarBotoes();
        btn_pacientes.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    private void BtnHome(MouseEvent event) {
        home_page.setVisible(true);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(false);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(false);
        top_bar.setVisible(true);
        resetarBotoes();
        btn_home.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    private void BtnMedicos(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(true);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_medicos.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    private void BtnConsultas(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(false);
        consultas_page.setVisible(true);
        especialidades_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_consultas.setStyle("-fx-background-color: #141313;");
    }

    @FXML
    private void BtnEspecialidades(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(false);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(true);
        top_bar.setVisible(false);
        resetarBotoes();
    }

    private void resetarBotoes() {
        btn_home.setStyle("-fx-background-color: #353A56;");
        btn_pacientes.setStyle("-fx-background-color: #353A56;");
        btn_medicos.setStyle("-fx-background-color: #353A56;");
        btn_consultas.setStyle("-fx-background-color: #353A56;");
        btn_sair.setStyle("-fx-background-color: #353A56;");
    }

    // ================= ALERTA =================
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void sairAplicacao(MouseEvent event) {
        System.exit(0);
    }
}
