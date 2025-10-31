package MainScreen;

import Clinica.java.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    // === LISTAR TODAS AS CONSULTAS ===
    public List<Consulta> listarConsultas() {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consulta c = new Consulta(
                    rs.getInt("id_consulta"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getDate("data_consulta"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_fim"),
                    rs.getString("status")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // === INSERIR CONSULTA COM VERIFICAÇÃO ===
    public boolean inserirConsulta(Consulta c) {
        // Verifica se o médico já tem consulta nesse horário
        if (!verificarDisponibilidadeMedico(c.getIdMedico(), c.getDataConsulta(), c.getHoraInicio(), c.getHoraFim())) {
            return false; // indisponível
        }

        String sql = "INSERT INTO consultas (id_paciente, id_medico, data_consulta, hora_inicio, hora_fim, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, c.getIdPaciente());
            ps.setInt(2, c.getIdMedico());
            ps.setDate(3, c.getDataConsulta());
            ps.setTime(4, c.getHoraInicio());
            ps.setTime(5, c.getHoraFim());
            ps.setString(6, c.getStatus());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setIdConsulta(rs.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // === DELETAR CONSULTA ===
    public void deletarConsulta(int idConsulta) {
        String sql = "DELETE FROM consultas WHERE id_consulta = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === VERIFICAR DISPONIBILIDADE DO MÉDICO (corrigido para sobreposição) ===
    private boolean verificarDisponibilidadeMedico(int idMedico, Date data, Time horaInicio, Time horaFim) {
        String sql = "SELECT COUNT(*) FROM consultas " +
                     "WHERE id_medico = ? AND data_consulta = ? " +
                     "AND NOT (hora_fim <= ? OR hora_inicio >= ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            ps.setDate(2, data);
            ps.setTime(3, horaInicio); // hora_fim <= horaInicio da nova consulta
            ps.setTime(4, horaFim);    // hora_inicio >= horaFim da nova consulta

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // true se não há conflitos
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
