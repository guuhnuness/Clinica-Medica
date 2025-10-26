package Clinica.java;

import MainScreen.Pacientes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientesDAO {

    public  void inserirPaciente(Pacientes paciente) {
        String sql = "INSERT INTO pacientes (nome, cpf, data_nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getDataNascimento());
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEmail());

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Inserção falhou, nenhuma linha afetada.");
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) paciente.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pacientes> listarPacientes() {
        List<Pacientes> pacientes = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, data_nascimento, telefone, email FROM pacientes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pacientes.add(new Pacientes(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("data_nascimento"),
                        rs.getString("telefone"),
                        rs.getString("email")
                      
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public void deletarPaciente(int id) {
        String sql = "DELETE FROM pacientes WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
