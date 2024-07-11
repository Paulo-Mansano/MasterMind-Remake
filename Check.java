import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Check {

    // Método para verificar a tentativa do jogador
    public static boolean checkAttempt(String[] correctSequence, String[] playerSequence, JPanel attemptsPanel, JPanel currentAttemptPanel, ArrayList<String> currentAttempt, Color corescolhida) {
        boolean isCorrect = true;

        // Verificar a tentativa do jogador com a sequência correta
        if (correctSequence.length != playerSequence.length) {
            isCorrect = false;
        } else {
            for (int i = 0; i < correctSequence.length; i++) {
                if (!correctSequence[i].equals(playerSequence[i])) {
                    isCorrect = false;
                    break;
                }
            }
        }

        // Mostrar a tentativa do jogador no painel de tentativas
        JPanel attemptPanel = new JPanel();
        attemptPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (String color : playerSequence) {
            ImageIcon icon = new ImageIcon(color);
            JLabel label = new JLabel(icon);
            attemptPanel.add(label);
            attemptPanel.setBackground(corescolhida);
        }

        // Adicionar o painel de feedback ao painel de tentativas
        JPanel feedbackPanel = Feedback.getFeedbackPanel(correctSequence, playerSequence);
        attemptPanel.add(feedbackPanel);

        // Adicionar o painel da tentativa ao painel de tentativas
        attemptsPanel.add(attemptPanel);
        attemptsPanel.revalidate();
        attemptsPanel.repaint();

        // Limpar a tentativa atual do jogador
        currentAttemptPanel.removeAll();
        currentAttempt.clear();
        currentAttemptPanel.revalidate();
        currentAttemptPanel.repaint();

        return isCorrect;
    }
}
