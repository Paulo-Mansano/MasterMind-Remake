import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BorderLayoutExample {

    // Variáveis de instância
    private static Jogo jogo;
    private static JPanel attemptsPanel;
    private static JPanel currentAttemptPanel;
    private static JScrollPane scrollPane;
    public static Color selectedColor = Color.WHITE;

    // Array de caminhos das imagens
    private static String[] imagePaths = {
            "botaoAzul.png",
            "botaoVermelho.png",
            "botaoRoxo.png",
            "botaoVerde.png",
            "botaoRosa.png",
            "botaoAmarelo.png"
    };

    private static ArrayList<String> currentAttempt;
    private static ArrayList<String> lastAttempt;
    private static int attemptsCount;
    private static final int MAX_ATTEMPTS = 10;

    public static void main(String[] args) {
        createAndShowGUI();
    }

    // Criação da tela principal
    static void createAndShowGUI() {
        // Instanciando a classe Jogo para determinar o número de retângulos
        jogo = new Jogo(4); // Aqui você pode escolher 4 ou 6
        attemptsCount = 0; // Inicializando o contador de tentativas

        // Criação do frame principal
        JFrame frame = new JFrame("Mastermind Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Criação do painel com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Criando um JPanel para colocar no centro
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Criando uma borda para o painel central
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        centerPanel.setBorder(border);

        // Criando um JPanel inicial para o painel sul (será atualizado posteriormente)
        JPanel southPanel = new JPanel();
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Criando um JPanel para a borda direita
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout()); // Esse layout dá um controle maior em posicionamento
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Criando painel de tentativas
        attemptsPanel = new JPanel();
        attemptsPanel.setLayout(new BoxLayout(attemptsPanel, BoxLayout.Y_AXIS));

        // Adicionando o painel de tentativas a um JScrollPane
        scrollPane = new JScrollPane(attemptsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scrollPane); // Adicionando o JScrollPane ao painel central

        // Painel para exibir a tentativa atual do jogador
        currentAttemptPanel = new JPanel();
        currentAttemptPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(currentAttemptPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER); // Adicionando painel central ao BorderLayout
        mainPanel.add(rightPanel, BorderLayout.EAST); // Adicionando painel direito ao BorderLayout

        // Adicionando o painel principal ao frame
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        // Inicializando a tentativa atual do jogador
        currentAttempt = new ArrayList<>();
        lastAttempt = new ArrayList<>();

        // Adicionando botões ao painel direito
        String[] buttonLabels = { "Iniciar", "Modo Teste", "Check", "Reset", "Apagar", "Cor de Fundo", "Dica" , "Score"};
        gbc.gridx = 0;
        gbc.gridy = 0; // Primeiro botão na linha 0
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 50, 10, 50);
        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            rightPanel.add(btn, gbc); // Adiciona cada botão ao painel direito
            gbc.gridy++;

            if (label.equals("Reset")) {
                btn.addActionListener(e -> {
                    // Limpar o conteúdo do painel de tentativas e da tentativa atual
                    currentAttemptPanel.removeAll();
                    currentAttempt.clear();
                    lastAttempt.clear();
                    currentAttemptPanel.revalidate();
                    currentAttemptPanel.repaint();
                });
            } else if (label.equals("Iniciar")) {
                btn.addActionListener(e -> startGame());

            } else if (label.equals("Modo Teste")) {
                btn.addActionListener(e -> {
                    int numcores = Jogo.perguntarNumeroCores();
                    if (numcores == 4 || numcores == 6) {
                        JOptionPane.showMessageDialog(null, "Você escolheu jogar com " + numcores + " cores.");

                        // Escolher as cores aleatórias
                        jogo.EscolhaDasCores(imagePaths, numcores);

                        // Abrir a tela de modo teste
                        showTestModeWindow(jogo);
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, escolha 4 ou 6 cores.");
                    }
                });
            } else if (label.equals("Cor de Fundo")) {
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedColor = JColorChooser.showDialog(frame, "Escolha uma Cor de Fundo", Color.WHITE);
                        if (selectedColor != null) {
                            centerPanel.setBackground(selectedColor);
                            southPanel.setBackground(selectedColor);
                            mainPanel.setBackground(selectedColor);
                            frame.setBackground(selectedColor);
                            currentAttemptPanel.setBackground(selectedColor);
                            scrollPane.setBackground(selectedColor);
                            attemptsPanel.setBackground(selectedColor);
                        }
                        btn.setEnabled(false); // Desabilita o botão após o primeiro clique
                    }
                });
            
            }else  if (label.equals("Check")) {
                btn.addActionListener(e -> {
                    // Verifica a pontuação do jogador.
                    if (currentAttempt.size() == jogo.getCoresEs().length) {
                        attemptsCount++;
                        lastAttempt = new ArrayList<>(currentAttempt);
                        if (Check.checkAttempt(jogo.getCoresEs(), currentAttempt.toArray(new String[0]), attemptsPanel,
                                currentAttemptPanel, currentAttempt, selectedColor)) {
                            // se o jogador acertou a sequência
                            String playerName = JOptionPane.showInputDialog(frame, "Parabéns! Você acertou a sequência!\nDigite seu nome:");
                            if (playerName != null && !playerName.isEmpty()) {
                                ScoreEntry.scoreGame(playerName, attemptsCount); // gravar o score
                                JOptionPane.showMessageDialog(frame, "Pontuação registrada com sucesso para " + playerName + "!");
                                attemptsCount = 0; // Redefinir contagem de tentativas
                                // Chama o método para desativar os botões de cores
                                MenuComBotoesRedondos.disableButtons(rightPanel);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Nome de jogador inválido!");
                            }
                        } else {
                            // jogador chegou ao limite de tentativas.
                            if (attemptsCount >= MAX_ATTEMPTS) {
                                JOptionPane.showMessageDialog(frame,
                                        "Você não conseguiu descobrir a senha dentro das tentativas limite. Uma nova senha será sorteada.");
                                // Limpe os painéis de tentativa e redefina o estado do jogo
                                attemptsPanel.removeAll();
                                currentAttemptPanel.removeAll();
                                lastAttempt.clear();
                                currentAttempt.clear();
                                attemptsCount = 0; // Redefinir contagem de tentativas
                                jogo.EscolhaDasCores(imagePaths, jogo.getCoresEs().length); // Gera uma nova sequência.
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "A sequência deve ter " + jogo.getCoresEs().length + " cores.");
                    }
                    attemptsPanel.setBackground(selectedColor);
                });
            
            
            } else if (label.equals("Apagar")) {
                btn.addActionListener(e -> {
                    if (!currentAttempt.isEmpty()) {
                        currentAttempt.remove(currentAttempt.size() - 1);
                        currentAttemptPanel.remove(currentAttemptPanel.getComponentCount() - 1);
                        currentAttemptPanel.revalidate();
                        currentAttemptPanel.repaint();
                    }
                });
            } else if (label.equals("Dica")) {
                btn.addActionListener(e -> {
                    if (!lastAttempt.isEmpty()) {
                        StringBuilder dica = new StringBuilder("Cores corretas nas posições: ");
                        for (int i = 0; i < jogo.getCoresEs().length; i++) {
                            if (jogo.getCoresEs()[i].equals(lastAttempt.get(i))) {
                                dica.append(lastAttempt.get(i)).append(" ");
                            }
                        }
                        JOptionPane.showMessageDialog(frame,
                                dica.length() > 0 ? dica.toString() : "Nenhuma cor está na posição correta.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Faça uma tentativa primeiro.");
                    }
                });
            } else if (label.equals("Score")) {
                btn.addActionListener(e -> {
                    // Verifica se o jogador acertou a sequência
                    if (Check.checkAttempt(jogo.getCoresEs(), currentAttempt.toArray(new String[0]), attemptsPanel,
                            currentAttemptPanel, currentAttempt, selectedColor)) {
                        String playerName = JOptionPane.showInputDialog(frame, "Parabéns! Você acertou a sequência. Digite seu nome:");
                        if (playerName != null && !playerName.isEmpty()) {
                            ScoreEntry.scoreGame(playerName, attemptsCount); // Chamando o método para salvar a pontuação
                            JOptionPane.showMessageDialog(frame, "Pontuação registrada com sucesso para " + playerName + "!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Nome de jogador inválido!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Você ainda não acertou a sequência para registrar a pontuação.");
                    }
                });
            }
            
            }

        

        // Adicionando todas as cores ao painel direito em duas colunas
        gbc.gridy++;
        gbc.gridwidth = 1;
        for (int i = 0; i < imagePaths.length; i++) {
            final String imagePath = imagePaths[i]; // Captura o imagePath como final

            JButton colorButton = new JButton();
            ImageIcon icon = new ImageIcon(imagePath);
            colorButton.setIcon(icon);
            colorButton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            colorButton.setOpaque(false);
            colorButton.setContentAreaFilled(false);
            colorButton.setBorderPainted(false);
            colorButton.setFocusPainted(false);

            colorButton.addActionListener(e -> {
                currentAttempt.add(imagePath); // Usa a variável capturada imagePath
                JButton attemptButton = new JButton(icon);
                attemptButton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
                attemptButton.setOpaque(false);
                attemptButton.setContentAreaFilled(false);
                attemptButton.setBorderPainted(false);
                attemptButton.setFocusPainted(false);
                currentAttemptPanel.add(attemptButton);
                currentAttemptPanel.revalidate();
                currentAttemptPanel.repaint();
            });

            gbc.gridx = i % 2;
            rightPanel.add(colorButton, gbc);

            if (i % 2 == 1) {
                gbc.gridy++;
            }
        }

    }

    private static void startGame() {
        int numcores = Jogo.perguntarNumeroCores();
        if (numcores == 4 || numcores == 6) {
            JOptionPane.showMessageDialog(null, "Você escolheu jogar com " + numcores + " cores.");
            jogo.EscolhaDasCores(imagePaths, numcores);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, escolha 4 ou 6 cores.");
        }
    }

    private static void showTestModeWindow(Jogo jogo) {
        // Criação da janela de modo teste
        JFrame testFrame = new JFrame("Modo Teste");
        testFrame.setSize(800, 600);
        testFrame.setLocationRelativeTo(null); // Centraliza a janela na tela

        JPanel testPanel = new JPanel(new BorderLayout());

        // Painel direito para os botões
        JPanel testRightPanel = new JPanel();
        testRightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adicionando um painel para exibir a resposta
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Exibir a sequência correta usando ícones
        for (String cor : jogo.getCoresEs()) {
            ImageIcon icon = new ImageIcon(cor);
            JLabel label = new JLabel(icon);
            northPanel.add(label);
        }

        testPanel.add(northPanel, BorderLayout.NORTH);

        // Botões de controle
        String[] buttonLabels = { "Check", "Reset", "Apagar" };
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            testRightPanel.add(btn, gbc);
            gbc.gridy++;

            if (label.equals("Reset")) {
                btn.addActionListener(e -> {
                    // Limpar o conteúdo do painel de tentativas e da tentativa atual
                    currentAttemptPanel.removeAll();
                    currentAttempt.clear();
                    currentAttemptPanel.revalidate();
                    currentAttemptPanel.repaint();
                });
            } else if (label.equals("Check")) {
                btn.addActionListener(e -> {
                    // Lógica para verificar a tentativa do jogador
                    if (currentAttempt.size() == jogo.getCoresEs().length) {
                        attemptsCount++;
                        if (Check.checkAttempt(jogo.getCoresEs(), currentAttempt.toArray(new String[0]), attemptsPanel,
                                currentAttemptPanel, currentAttempt, selectedColor)) {
                            JOptionPane.showMessageDialog(testFrame, "Parabéns! Você acertou a sequência!");
                            attemptsCount = 0; // Resetando o contador de tentativas
                            testFrame.dispose();
                        } else {
                            if (attemptsCount >= MAX_ATTEMPTS) {
                                JOptionPane.showMessageDialog(testFrame,
                                        "Você não conseguiu descobrir a senha dentro das tentativas limite. Uma nova senha será sorteada.");
                                // Limpar o conteúdo do painel de tentativas e da tentativa atual
                                attemptsPanel.removeAll();
                                currentAttemptPanel.removeAll();
                                currentAttempt.clear();
                                attemptsCount = 0; // Resetando o contador de tentativas
                                jogo.EscolhaDasCores(imagePaths, jogo.getCoresEs().length); // Sorteia uma nova senha
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(testFrame,
                                "A sequência deve ter " + jogo.getCoresEs().length + " cores.");
                    }
                });
            } else if (label.equals("Apagar")) {
                btn.addActionListener(e -> {
                    if (!currentAttempt.isEmpty()) {
                        currentAttempt.remove(currentAttempt.size() - 1);
                        currentAttemptPanel.remove(currentAttemptPanel.getComponentCount() - 1);
                        currentAttemptPanel.revalidate();
                        currentAttemptPanel.repaint();
                    }
                });
            }
        }

        gbc.gridy++;
        gbc.gridwidth = 1;
        for (int i = 0; i < imagePaths.length; i++) {
            final String imagePath = imagePaths[i];

            JButton colorButton = new JButton();
            ImageIcon icon = new ImageIcon(imagePath);
            colorButton.setIcon(icon);
            colorButton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            colorButton.setOpaque(false);
            colorButton.setContentAreaFilled(false);
            colorButton.setBorderPainted(false);
            colorButton.setFocusPainted(false);

            colorButton.addActionListener(e -> {
                currentAttempt.add(imagePath);
                JButton attemptButton = new JButton(icon);
                attemptButton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
                attemptButton.setOpaque(false);
                attemptButton.setContentAreaFilled(false);
                attemptButton.setBorderPainted(false);
                attemptButton.setFocusPainted(false);
                currentAttemptPanel.add(attemptButton);
                currentAttemptPanel.revalidate();
                currentAttemptPanel.repaint();
            });

            gbc.gridx = i % 2;
            testRightPanel.add(colorButton, gbc);

            if (i % 2 == 1) {
                gbc.gridy++;
            }
        }

        // Painel para exibir a tentativa atual do jogador
        currentAttemptPanel = new JPanel();
        currentAttemptPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        testPanel.add(currentAttemptPanel, BorderLayout.SOUTH);

        // Painel para exibir todas as tentativas do jogador
        attemptsPanel = new JPanel();
        attemptsPanel.setLayout(new BoxLayout(attemptsPanel, BoxLayout.Y_AXIS));

        // Adicionando o painel de tentativas a um JScrollPane
        JScrollPane testScrollPane = new JScrollPane(attemptsPanel);
        testScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        testPanel.add(testScrollPane, BorderLayout.CENTER); // Adicionando o JScrollPane ao painel central

        testPanel.add(testRightPanel, BorderLayout.EAST);

        testFrame.getContentPane().add(testPanel);
        testFrame.setVisible(true);
    }
}
