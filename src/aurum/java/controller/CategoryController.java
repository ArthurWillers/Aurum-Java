/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aurum.java.controller;

import aurum.java.connection.Connect_db;
import aurum.java.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ArthurWillers
 */
public class CategoryController {

    /**
     * Salva uma nova categoria na base de dados (CREATE)
     *
     * @param category
     */
    public void save(Category category) {
        String sql = "INSERT INTO categories (name, type) VALUES (?, ?)";

        try (Connection conn = Connect_db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType());
            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível salvar a categoria.\nDetalhes: " + e.getMessage(),
                    "Erro ao Salvar Categoria",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Atualiza uma categoria existente (UPDATE)
     *
     * @param category
     */
    public void update(Category category) {
        String sql = "UPDATE categories SET name = ?, type = ? WHERE id = ?";

        try (Connection conn = Connect_db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType());
            stmt.setInt(3, category.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível atualizar a categoria.\nDetalhes: " + e.getMessage(),
                    "Erro ao Atualizar Categoria",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Apaga uma categoria pelo seu ID (DELETE)
     *
     * @param id
     * @return true se a categoria foi excluída, false caso contrário
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = Connect_db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível apagar a categoria.\nDetalhes: " + e.getMessage(),
                    "Erro ao Apagar Categoria",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    /**
     * Lista todas as categorias da base de dados (READ)
     *
     * @return
     */
    public List<Category> listAll() {
        String sql = "SELECT * FROM categories ORDER BY name ASC";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = Connect_db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setType(rs.getString("type"));
                categories.add(category);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível listar as categorias.\nDetalhes: " + e.getMessage(),
                    "Erro ao Listar Categorias",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return categories;
    }

    /**
     * Busca uma categoria específica pelo ID
     *
     * @param id
     * @return
     */
    public Category getById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        Category category = null;

        try (Connection conn = Connect_db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    category.setType(rs.getString("type"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível buscar a categoria.\nDetalhes: " + e.getMessage(),
                    "Erro ao Buscar Categoria",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return category;
    }

    /**
     * Lista categorias filtradas por tipo ("income" ou "expense").
     * @param type
     * @return
     */
    public List<Category> listByType(String type) {
        String sql = "SELECT * FROM categories WHERE type = ? ORDER BY name ASC";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = Connect_db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    category.setType(rs.getString("type"));
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível listar as categorias por tipo.\nDetalhes: " + e.getMessage(),
                    "Erro ao Listar Categorias",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return categories;
    }
}
