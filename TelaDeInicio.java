import javax.swing.*;
import java.awt.*;

public class TelaDeInicio {

    public static void main(String[] args) {
        // Iniciar a tela inicial
        SwingUtilities.invokeLater(TelaDeInicio::createAndShowGUI);
    }

    // Criação da tela inicial
    private static void createAndShowGUI() {
        // Criação do frame inicial
        JFrame startFrame = new JFrame("Tela de Início");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(450, 350);
        startFrame.setLocationRelativeTo(null); // Centraliza a janela na tela

        // Criação do painel inicial
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());

        // Adicionar texto ao painel inicial
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Mastermind!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        startPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Criação do painel secundário
        JPanel startPanel2 = new JPanel();
        startPanel2.setLayout(new BorderLayout());

        // Adicionar texto ao painel secundário
        JLabel welcomeLabel2 = new JLabel("Desenvolvedores: Gabrielle, Luiza, Paulo e Juan.", SwingConstants.CENTER);
        welcomeLabel2.setFont(new Font("Arial", Font.BOLD, 18));
        startPanel.add(welcomeLabel2, BorderLayout.PAGE_START);

        // Adicionar botão "Iniciar" ao painel inicial
        JButton startButton = new JButton("Iniciar");
        startButton.addActionListener(e -> {
            // Abrir a janela principal
            BorderLayoutExample.main(new String[0]);
            // Fechar a tela inicial
            startFrame.dispose();
        });
        startPanel.add(startButton, BorderLayout.SOUTH);

        // Adicionar o painel inicial ao frame
        startFrame.getContentPane().add(startPanel);
        startFrame.setVisible(true);
    }
}
