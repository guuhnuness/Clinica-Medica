package MainScreen;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

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

    // ======= MÉDICOS =======
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

    // ======= CONSULTAS =======
    @FXML private TableView<Consulta> tabelaConsultas;
    @FXML private TableColumn<Consulta, Integer> coluna_id_consulta;
    @FXML private TableColumn<Consulta, String> coluna_paciente_consulta;
    @FXML private TableColumn<Consulta, String> coluna_medico_consulta;
    @FXML private TableColumn<Consulta, Date> coluna_data_consulta;
    @FXML private TableColumn<Consulta, Time> coluna_hora_inicio;
    @FXML private TableColumn<Consulta, Time> coluna_hora_fim;
    @FXML private TableColumn<Consulta, String> coluna_status_consulta;

    @FXML private ComboBox<Pacientes> campo_paciente_consulta;
    @FXML private ComboBox<Medicos> campo_medico_consulta;
    @FXML private TextField campo_hora_inicio_consulta;
    @FXML private TextField campo_hora_fim_consulta;
    @FXML private DatePicker campo_data_consulta;
    @FXML private ComboBox<String> campo_status_consulta;

    private final ObservableList<Consulta> listaConsultas = FXCollections.observableArrayList();
    private final ConsultaDAO consultaDAO = new ConsultaDAO();

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
    @FXML private Button btn_especialidades;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ======== PACIENTES ========
        if (tabelaPacientes != null && coluna_id_paciente != null) {
            coluna_id_paciente.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
            coluna_nome_paciente.setCellValueFactory(cell -> cell.getValue().nomeProperty());
            coluna_cpf_paciente.setCellValueFactory(cell -> cell.getValue().cpfProperty());
            coluna_data_paciente.setCellValueFactory(cell -> cell.getValue().data_nascimentoProperty());
            coluna_telefone_paciente.setCellValueFactory(cell -> cell.getValue().telefoneProperty());
            coluna_email_paciente.setCellValueFactory(cell -> cell.getValue().emailProperty());
            coluna_logradouro_paciente.setCellValueFactory(cell -> cell.getValue().logradouroProperty());
            tabelaPacientes.setItems(listaPacientes);
            listaPacientes.addAll(pacienteDAO.listarPacientes());
        }

        // ======== MÉDICOS ========
        if (tabelaMedicos != null && coluna_id_medico != null) {
            coluna_id_medico.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
            coluna_nome_medico.setCellValueFactory(cell -> cell.getValue().nomeProperty());
            coluna_crm_medico.setCellValueFactory(cell -> cell.getValue().crmProperty());
            coluna_especialidade_medico.setCellValueFactory(cell -> {
                int idEsp = cell.getValue().getIdEspecialidade();
                Especialidade esp = especialidadeDAO.buscarPorId(idEsp);
                return new SimpleStringProperty(esp != null ? esp.getNome() : "");
            });
            coluna_data_medico.setCellValueFactory(cell -> cell.getValue().dataProperty());
            coluna_telefone_medico.setCellValueFactory(cell -> cell.getValue().telefoneProperty());
            tabelaMedicos.setItems(listaMedicos);
            listaMedicos.addAll(medicoDAO.listarMedicos());

            if (campo_especialidade_medico != null) {
                campo_especialidade_medico.setItems(FXCollections.observableArrayList(especialidadeDAO.listarEspecialidades()));
            }
        }

        // ======== ESPECIALIDADES ========
        if (tabelaEspecialidades != null && coluna_id_especialidade != null) {
            coluna_id_especialidade.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
            coluna_nome_especialidade.setCellValueFactory(cell -> cell.getValue().nomeProperty());
            coluna_descricao_especialidade.setCellValueFactory(cell -> cell.getValue().descricaoProperty());
            tabelaEspecialidades.setItems(listaEspecialidades);
            listaEspecialidades.addAll(especialidadeDAO.listarEspecialidades());
        }

        // ======== CONSULTAS ========
        if (tabelaConsultas != null && coluna_id_consulta != null) {
            coluna_id_consulta.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getIdConsulta()).asObject());
            coluna_paciente_consulta.setCellValueFactory(cell -> {
                Pacientes p = pacienteDAO.buscarPorId(cell.getValue().getIdPaciente());
                return new SimpleStringProperty(p != null ? p.getNome() : "Desconhecido");
            });
            coluna_medico_consulta.setCellValueFactory(cell -> {
                Medicos m = medicoDAO.buscarPorId(cell.getValue().getIdMedico());
                return new SimpleStringProperty(m != null ? m.getNome() : "Desconhecido");
            });
            coluna_data_consulta.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getDataConsulta()));
            coluna_hora_inicio.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getHoraInicio()));
            coluna_hora_fim.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getHoraFim()));
            coluna_status_consulta.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus()));

            tabelaConsultas.setItems(listaConsultas);
            listaConsultas.addAll(consultaDAO.listarConsultas());

            if (campo_paciente_consulta != null) campo_paciente_consulta.setItems(listaPacientes);
            if (campo_medico_consulta != null) campo_medico_consulta.setItems(listaMedicos);
            if (campo_status_consulta != null) campo_status_consulta.setItems(FXCollections.observableArrayList("Agendada", "Concluída", "Cancelada"));
        }
    }
            @FXML private void adicionarConsulta(MouseEvent event) {
                Pacientes paciente = campo_paciente_consulta.getValue();
                Medicos medico = campo_medico_consulta.getValue();
                Date data = campo_data_consulta.getValue() != null ? Date.valueOf(campo_data_consulta.getValue()) : null;
                String horaInicioStr = campo_hora_inicio_consulta.getText().trim(); String horaFimStr = campo_hora_fim_consulta.getText().trim();
                String status = campo_status_consulta.getValue();
                if (paciente == null || medico == null || data == null || horaInicioStr.isEmpty() || horaFimStr.isEmpty() || status == null) {
                    mostrarAlerta("Erro", "Todos os campos são obrigatórios.", Alert.AlertType.ERROR);
                    return; }
                try {
                    if (!horaInicioStr.matches("\\d{2}:\\d{2}") || !horaFimStr.matches("\\d{2}:\\d{2}")) {
                        mostrarAlerta("Erro", "Formato de hora inválido. Use HH:MM.", Alert.AlertType.ERROR);
                        return;
                    } 
                    Time horaInicio = Time.valueOf(horaInicioStr + ":00"); Time horaFim = Time.valueOf(horaFimStr + ":00");
                    if (horaFim.before(horaInicio) || horaFim.equals(horaInicio)) { 
                        mostrarAlerta("Erro", "Hora de término deve ser maior que hora de início.", Alert.AlertType.ERROR); 
                        return;
                    }
                    Consulta nova = new Consulta(paciente.getId(), medico.getId(), data, horaInicio, horaFim, status);
                    consultaDAO.inserirConsulta(nova); listaConsultas.add(nova); 
                    limparCamposConsulta();
                    mostrarAlerta("Sucesso", "Consulta adicionada com sucesso!", Alert.AlertType.INFORMATION);
                } catch (IllegalArgumentException e) { mostrarAlerta("Erro", "Formato de hora inválido. Use HH:MM.", Alert.AlertType.ERROR); } }

    @FXML
    private void removerConsulta(MouseEvent event) {
        Consulta sel = tabelaConsultas.getSelectionModel().getSelectedItem();
        if (sel != null) {
            consultaDAO.deletarConsulta(sel.getIdConsulta());
            listaConsultas.remove(sel);
            mostrarAlerta("Sucesso", "Consulta removida com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Erro", "Selecione uma consulta para remover.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limparCamposConsulta() {
        campo_paciente_consulta.getSelectionModel().clearSelection();
        campo_medico_consulta.getSelectionModel().clearSelection();
        campo_data_consulta.setValue(null);
        campo_hora_inicio_consulta.clear();
        campo_hora_fim_consulta.clear();
        campo_status_consulta.getSelectionModel().clearSelection();
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
            mostrarAlerta("Erro", "Todos os campos são obrigatórios.", Alert.AlertType.ERROR);
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
            mostrarAlerta("Erro", "Selecione um paciente para remover.", Alert.AlertType.ERROR);
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
            mostrarAlerta("Erro", "Todos os campos são obrigatórios.", Alert.AlertType.ERROR);
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
            mostrarAlerta("Erro", "Selecione um médico para remover.", Alert.AlertType.ERROR);
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
            mostrarAlerta("Erro", "O campo nome é obrigatório.", Alert.AlertType.ERROR);
            return;
        }

        Especialidade nova = new Especialidade(nome, descricao);
        especialidadeDAO.adicionarEspecialidade(nova);
        listaEspecialidades.setAll(especialidadeDAO.listarEspecialidades());
        limparCamposEspecialidade();
        mostrarAlerta("Sucesso", "Especialidade adicionada com sucesso!", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void removerEspecialidade(MouseEvent event) {
        Especialidade sel = tabelaEspecialidades.getSelectionModel().getSelectedItem();
        if (sel != null) {
            especialidadeDAO.removerEspecialidade(sel.getId());
            listaEspecialidades.remove(sel);
            mostrarAlerta("Sucesso", "Especialidade removida com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Erro", "Selecione uma especialidade para remover.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limparCamposEspecialidade() {
        campo_nome_especialidade.clear();
        campo_descricao_especialidade.clear();
    }

    // ================= PÁGINAS =================
    @FXML private void BtnPacientes(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(true);
        medicos_page.setVisible(false);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(false);
        top_bar.setVisible(true);
        resetarBotoes();
        btn_pacientes.setStyle("-fx-background-color: #141313;");
    }

    @FXML private void BtnHome(MouseEvent event) {
        home_page.setVisible(true);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(false);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(false);
        top_bar.setVisible(true);
        resetarBotoes();
        btn_home.setStyle("-fx-background-color: #141313;");
    }

    @FXML private void BtnMedicos(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(true);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_medicos.setStyle("-fx-background-color: #141313;");
    }

    @FXML private void BtnConsultas(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(false);
        consultas_page.setVisible(true);
        especialidades_page.setVisible(false);
        top_bar.setVisible(false);
        resetarBotoes();
        btn_consultas.setStyle("-fx-background-color: #141313;");
    }

    @FXML private void BtnEspecialidades(MouseEvent event) {
        home_page.setVisible(false);
        pacientes_page.setVisible(false);
        medicos_page.setVisible(false);
        consultas_page.setVisible(false);
        especialidades_page.setVisible(true);
        top_bar.setVisible(false);
        btn_especialidades.setStyle("-fx-background-color: #141313;");
        resetarBotoes();
    }
    

    private void resetarBotoes() {
        btn_home.setStyle("-fx-background-color: #353A56;");
        btn_pacientes.setStyle("-fx-background-color: #353A56;");
        btn_medicos.setStyle("-fx-background-color: #353A56;");
        btn_consultas.setStyle("-fx-background-color: #353A56;");
        btn_sair.setStyle("-fx-background-color: #353A56;");
        btn_especialidades.setStyle("-fx-background-color: #353A56;");
    }
    
    
@FXML private void sairAplicacao(MouseEvent event) { System.exit(0); }
    // ================= ALERTA =================
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
