package MainScreen;

import Clinica.java.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {

    // Listar todas as especialidades
    public static List<Especialidade> listarEspecialidades() {
        List<Especialidade> lista = new ArrayList<>();
        String sql = "SELECT * FROM especialidades";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Especialidade e = new Especialidade(
                    rs.getInt("id_especialidade"),
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Buscar especialidade por ID
    public Especialidade buscarPorId(int id) {
        String sql = "SELECT * FROM especialidades WHERE id_especialidade = ?";
        Especialidade e = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    e = new Especialidade(
                        rs.getInt("id_especialidade"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                    );
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return e;
    }

    // Adicionar especialidade
    public static boolean adicionarEspecialidade(Especialidade especialidade) {
        String sql = "INSERT INTO especialidades (nome, descricao) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, especialidade.getNome());
            stmt.setString(2, especialidade.getDescricao());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remover especialidade
    public static boolean removerEspecialidade(int id) {
        String sql = "DELETE FROM especialidades WHERE id_especialidade = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
