package MainScreen;

import Clinica.java.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientesDAO {

    public List<Pacientes> listarPacientes() {
        List<Pacientes> lista = new ArrayList<>();
        String sql = "SELECT id_paciente, nome, cpf, data_nascimento, telefone, email, logradouro FROM pacientes";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pacientes p = new Pacientes(
                    rs.getInt("id_paciente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getDate("data_nascimento"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("logradouro")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Pacientes buscarPorId(int id) {
        String sql = "SELECT * FROM pacientes WHERE id_paciente = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pacientes(
                        rs.getInt("id_paciente"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("data_nascimento"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("logradouro")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void inserirPaciente(Pacientes p) {
        String sql = "INSERT INTO pacientes (nome, cpf, data_nascimento, telefone, email, logradouro) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
           ps.setDate(4, p.getDataNascimento());
            ps.setString(4, p.getTelefone());
            ps.setString(5, p.getEmail());
            ps.setString(6, p.getLogradouro());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarPaciente(int id) {
        String sql = "DELETE FROM pacientes WHERE id_paciente = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
