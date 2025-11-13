/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aurum.java.controller;

import aurum.java.connection.Connect_db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;

/**
 *
 * @author Ethan
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

        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
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
            System.err.println("Erro ao buscar totais do dashboard: " + e.getMessage());
        }

        return new double[]{income, expense};
    }
}
