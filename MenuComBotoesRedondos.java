import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuComBotoesRedondos {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuComBotoesRedondos::createAndShowGUI);
    }

    // Criação da tela principal
    private static void createAndShowGUI() {
        // Criação do frame principal
        JFrame frame = new JFrame("Exemplo de BorderLayout com Borda");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Criação do painel com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Criando um JPanel para colocar no centro
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Adicionando um botão ao centro do painel
        JButton button = new JButton("Clique Aqui");
        centerPanel.add(button);

        // Criando uma borda para o painel central
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        centerPanel.setBorder(border);

        // Criando um JPanel para a borda direita
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adicionando botões ao painel direito
        String[] buttonLabels = {"Modo Teste", "Check"};
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 50, 10, 50);
        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            rightPanel.add(btn, gbc);
            gbc.gridy++;
        }

        String[] imagePaths = {
                "botaoAzul.png",
                "botaoVermelho.png",
                "botaoRoxo.png",
                "botaoVerde.png",
                "botaoRosa.png",
                "botaoAmarelo.png"
        };

        for (int i = 0; i < imagePaths.length; i++) {
            // Cria o JButton
            JButton colorButton = new JButton();

            // Define o ícone do botão
            ImageIcon icon = new ImageIcon(imagePaths[i]);
            colorButton.setIcon(icon);

            // Define o tamanho do botão baseado no tamanho da imagem
            colorButton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

            // Torna o botão transparente
            colorButton.setOpaque(false);
            colorButton.setContentAreaFilled(false);
            colorButton.setBorderPainted(false);
            colorButton.setFocusPainted(false);

            // Adiciona um ActionListener ao botão
            colorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Ação a ser executada quando o botão for clicado
                    System.out.println("Botão " + colorButton.getIcon().toString() + " clicado!");
                }
            });

            // Adiciona o botão ao painel
            gbc.gridx = i % 2;
            rightPanel.add(colorButton, gbc);
            if (i % 2 == 1) {
                gbc.gridy++;
            }
        }

        // Criando um JPanel para a borda sul
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.LIGHT_GRAY);
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.setPreferredSize(new Dimension(frame.getWidth(), 110));

        // Adiciona o painel ao frame
        frame.add(rightPanel);

        // Criando uma borda para o painel direito
        Border rightBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        rightPanel.setBorder(rightBorder);

        // Adicionando o painel central ao painel principal
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Adicionando o painel direito ao painel principal
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Adicionando o painel sul ao painel principal
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Adicionando o painel principal ao frame
        frame.getContentPane().add(mainPanel);

        // Exibindo o frame
        frame.setVisible(true);
    }

    // Método para desativar os botões em um JPanel
    static void disableButtons(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setEnabled(false);
            }
        }
    }
}
