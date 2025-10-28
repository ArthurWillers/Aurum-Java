/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aurum.java;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ArthurWillers
 */
public class Aurum {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Tenta definir o Look and Feel FlatLaf Light
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            // Se falhar, usa o tema padrão do sistema como fallback
            System.err.println("Falha ao carregar o Look and Feel FlatLaf. Usando o tema padrão do sistema.");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                System.err.println("Não foi possível nem carregar o Look and Feel do sistema.");
            }
        }

        // Inicia a Dashboard
        java.awt.EventQueue.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }

}
