package MainScreen;

import javafx.beans.property.*;
import java.sql.Date;
import java.sql.Time;

public class Consulta {
    private final IntegerProperty idConsulta;
    private final IntegerProperty idPaciente;
    private final IntegerProperty idMedico;
    private final ObjectProperty<Date> dataConsulta;
    private final ObjectProperty<Time> horaInicio;
    private final ObjectProperty<Time> horaFim;
    private final StringProperty status;

    // Construtor completo
    public Consulta(int idConsulta, int idPaciente, int idMedico, Date dataConsulta, Time horaInicio, Time horaFim, String status) {
        this.idConsulta = new SimpleIntegerProperty(idConsulta);
        this.idPaciente = new SimpleIntegerProperty(idPaciente);
        this.idMedico = new SimpleIntegerProperty(idMedico);
        this.dataConsulta = new SimpleObjectProperty<>(dataConsulta);
        this.horaInicio = new SimpleObjectProperty<>(horaInicio);
        this.horaFim = new SimpleObjectProperty<>(horaFim);
        this.status = new SimpleStringProperty(status);
    }

    // Construtor para inserções
    public Consulta(int idPaciente, int idMedico, Date dataConsulta, Time horaInicio, Time horaFim, String status) {
        this(0, idPaciente, idMedico, dataConsulta, horaInicio, horaFim, status);
    }

    // Getters e setters
    public int getIdConsulta() { return idConsulta.get(); }
    public IntegerProperty idConsultaProperty() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta.set(idConsulta); }

    public int getIdPaciente() { return idPaciente.get(); }
    public IntegerProperty idPacienteProperty() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente.set(idPaciente); }

    public int getIdMedico() { return idMedico.get(); }
    public IntegerProperty idMedicoProperty() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico.set(idMedico); }

    public Date getDataConsulta() { return dataConsulta.get(); }
    public ObjectProperty<Date> dataConsultaProperty() { return dataConsulta; }
    public void setDataConsulta(Date dataConsulta) { this.dataConsulta.set(dataConsulta); }

    public Time getHoraInicio() { return horaInicio.get(); }
    public ObjectProperty<Time> horaInicioProperty() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio.set(horaInicio); }

    public Time getHoraFim() { return horaFim.get(); }
    public ObjectProperty<Time> horaFimProperty() { return horaFim; }
    public void setHoraFim(Time horaFim) { this.horaFim.set(horaFim); }

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }
    public void setStatus(String status) { this.status.set(status); }

    @Override
    public String toString() {
        return "Consulta #" + getIdConsulta() + " - " + getDataConsulta() + " (" + getStatus() + ")";
    }
}
