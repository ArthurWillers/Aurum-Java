/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aurum.java.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ArthurWillers
 */
public class Connect_db {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "aurum";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useTimezone=true&serverTimezone=UTC";

    /**
     * Conecta com o Banco de Dados
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao ligar-se à base de dados: " + e.getMessage());
            throw new RuntimeException("Falha na ligação com a base de dados. A aplicação será encerrada.");
        }
    }
}
