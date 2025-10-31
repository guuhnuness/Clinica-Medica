package MainScreen;

import Clinica.java.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicosDAO {

    public List<Medicos> listarMedicos() {
        List<Medicos> lista = new ArrayList<>();
        String sql = "SELECT m.id_medico, m.nome, m.crm, m.id_especialidade, m.data_nascimento, m.telefone " +
                     "FROM medicos m";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Medicos m = new Medicos(
                    rs.getInt("id_medico"),
                    rs.getString("nome"),
                    rs.getString("crm"),
                    rs.getInt("id_especialidade"),
                    rs.getDate("data_nascimento"),
                    rs.getString("telefone")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public Medicos buscarPorId(int id) {
    String sql = "SELECT * FROM medicos WHERE id_medico = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Medicos(
                    rs.getInt("id_medico"),
                    rs.getString("nome"),
                    rs.getString("crm"),
                    rs.getInt("id_especialidade"),
                    rs.getDate("data_nascimento"),
                    rs.getString("telefone")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    public void inserirMedico(Medicos m) {
        String sql = "INSERT INTO medicos (nome, crm, id_especialidade, data_nascimento, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getNome());
            ps.setString(2, m.getCrm());
            ps.setInt(3, m.getIdEspecialidade());
            ps.setDate(4, m.getData());
            ps.setString(5, m.getTelefone());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                m.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarMedico(int id) {
        String sql = "DELETE FROM medicos WHERE id_medico = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
