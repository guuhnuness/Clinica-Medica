package MainScreen;

import Clinica.java.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicosDAO {

    public void inserirMedico(Medicos medico) {
        String sql = "INSERT INTO cursos (id, nome, crm, id_especialidade, data_nascimento, telefone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, curso.getId());
            stmt.setString(2, curso.getNome());
            stmt.setString(3, curso.getCrm());
            stmt.setInt(4, curso.getEspecialidade());
            stmt.setString(5, curso.getData());
            stmt.setString(6, curso.getTelefone());

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Inserção falhou, nenhuma linha afetada.");
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) curso.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medicos> listarMedicos() {
        List<Medicos> list = new ArrayList<>();
        String sql = "SELECT id, nome, crm, id_especialidade, data_nascimento, telefone FROM medicos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Medicos(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("crm"),
                        rs.getInt("id_especialidade"),
                        rs.getString("data_nascimento"),
                        rs.getString("telefone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deletarMedico(int id) {
        String sql = "DELETE FROM medicos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
