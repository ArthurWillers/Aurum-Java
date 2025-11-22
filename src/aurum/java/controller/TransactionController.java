/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aurum.java.controller;

import aurum.java.connection.Connect_db;
import aurum.java.model.Category;
import aurum.java.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ArthurWillers
 */
public class TransactionController {

    /**
     * Busca no banco de dados o total de RECEITAS e DESPESAS para um mês e ano
     * específicos.
     *
     * @param month O mês e ano (YearMonth) de referência.
     * @return um array de double onde: [0] = Total de Receitas (income) [1] =
     * Total de Despesas (expense)
     */
    public double[] getDashboardTotals(YearMonth month) {
        String sql = "SELECT type, SUM(amount) as total "
                + "FROM transactions "
                + "WHERE YEAR(date) = ? AND MONTH(date) = ? "
                + "GROUP BY type";

        double income = 0.0;
        double expense = 0.0;

        try (Connection conn = Connect_db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, month.getYear());
            stmt.setInt(2, month.getMonthValue());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("type").equals("income")) {
                        income = rs.getDouble("total");
                    } else if (rs.getString("type").equals("expense")) {
                        expense = rs.getDouble("total");
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível buscar os totais do dashboard.\nDetalhes: " + e.getMessage(),
                    "Erro ao Buscar Totais",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return new double[]{income, expense};
    }

    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (description, amount, type, date, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Connect_db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transaction.getDescription());
            stmt.setDouble(2, transaction.getAmount());
            stmt.setString(3, transaction.getType());
            stmt.setObject(4, transaction.getDate());
            
            if (transaction.getCategory() != null) {
                stmt.setInt(5, transaction.getCategory().getId());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível salvar a transação.\nDetalhes: " + e.getMessage(),
                    "Erro ao Salvar Transação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void update(Transaction transaction) {
        String sql = "UPDATE transactions SET description = ?, amount = ?, type = ?, date = ?, category_id = ? WHERE id = ?";

        try (Connection conn = Connect_db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transaction.getDescription());
            stmt.setDouble(2, transaction.getAmount());
            stmt.setString(3, transaction.getType());
            stmt.setObject(4, transaction.getDate());
            
            if (transaction.getCategory() != null) {
                stmt.setInt(5, transaction.getCategory().getId());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            stmt.setInt(6, transaction.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível atualizar a transação.\nDetalhes: " + e.getMessage(),
                    "Erro ao Atualizar Transação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = Connect_db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível apagar a transação.\nDetalhes: " + e.getMessage(),
                    "Erro ao Apagar Transação",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public List<Transaction> listByMonth(YearMonth month) {
        String sql = "SELECT t.*, c.name as category_name, c.type as category_type "
                + "FROM transactions t "
                + "LEFT JOIN categories c ON t.category_id = c.id "
                + "WHERE YEAR(t.date) = ? AND MONTH(t.date) = ? "
                + "ORDER BY t.date DESC, t.id DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = Connect_db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, month.getYear());
            stmt.setInt(2, month.getMonthValue());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setDescription(rs.getString("description"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setType(rs.getString("type"));
                    transaction.setDate(rs.getDate("date").toLocalDate());

                    if (rs.getObject("category_id") != null) {
                        Category category = new Category();
                        category.setId(rs.getInt("category_id"));
                        category.setName(rs.getString("category_name"));
                        category.setType(rs.getString("category_type"));
                        transaction.setCategory(category);
                    }

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível listar as transações do mês.\nDetalhes: " + e.getMessage(),
                    "Erro ao Listar Transações",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return transactions;
    }

    public Transaction getById(int id) {
        String sql = "SELECT t.*, c.name as category_name, c.type as category_type "
                + "FROM transactions t "
                + "LEFT JOIN categories c ON t.category_id = c.id "
                + "WHERE t.id = ?";
        Transaction transaction = null;

        try (Connection conn = Connect_db.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setDescription(rs.getString("description"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setType(rs.getString("type"));
                    transaction.setDate(rs.getDate("date").toLocalDate());

                    if (rs.getObject("category_id") != null) {
                        Category category = new Category();
                        category.setId(rs.getInt("category_id"));
                        category.setName(rs.getString("category_name"));
                        category.setType(rs.getString("category_type"));
                        transaction.setCategory(category);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível buscar a transação.\nDetalhes: " + e.getMessage(),
                    "Erro ao Buscar Transação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return transaction;
    }
}
