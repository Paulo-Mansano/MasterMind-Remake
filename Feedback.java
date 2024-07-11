import javax.swing.*;
import java.awt.*;

public class Feedback {

    // Tamanho dos pinos de feedback
    private static final int PIN_SIZE = 110;

    // Método para obter o painel de feedback
    public static JPanel getFeedbackPanel(String[] correctSequence, String[] playerSequence) {
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        int blackPins = 0;
        int whitePins = 0;

        boolean[] usedInSolution = new boolean[correctSequence.length];
        boolean[] usedInGuess = new boolean[correctSequence.length];

        // Contar os pinos pretos
        for (int i = 0; i < correctSequence.length; i++) {
            if (playerSequence[i].equals(correctSequence[i])) {
                blackPins++;
                usedInSolution[i] = true;
                usedInGuess[i] = true;
            }
        }

        // Contar os pinos brancos
        for (int i = 0; i < correctSequence.length; i++) {
            if (!usedInGuess[i]) {
                for (int j = 0; j < correctSequence.length; j++) {
                    if (!usedInSolution[j] && playerSequence[i].equals(correctSequence[j])) {
                        whitePins++;
                        usedInSolution[j] = true;
                        break;
                    }
                }
            }
        }

        // Escalar as imagens dos pinos com as proporções corretas
        final int desiredHeight = PIN_SIZE;

        // Obter as imagens dos pinos pretos e brancos
        ImageIcon blackIcon = new ImageIcon("pinoPreto.png");
        double blackScale = (double) desiredHeight / blackIcon.getIconHeight();
        Image blackImage = blackIcon.getImage().getScaledInstance((int) (blackIcon.getIconWidth() * blackScale),
                desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledBlackIcon = new ImageIcon(blackImage);

        ImageIcon whiteIcon = new ImageIcon("pinoBranco.png");
        double whiteScale = (double) desiredHeight / whiteIcon.getIconHeight();
        Image whiteImage = whiteIcon.getImage().getScaledInstance((int) (whiteIcon.getIconWidth() * whiteScale),
                desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledWhiteIcon = new ImageIcon(whiteImage);

        // Adicionar pinos pretos
        for (int i = 0; i < blackPins; i++) {
            JLabel blackLabel = new JLabel(scaledBlackIcon);
            feedbackPanel.add(blackLabel);
        }

        // Adicionar pinos brancos
        for (int i = 0; i < whitePins; i++) {
            JLabel whiteLabel = new JLabel(scaledWhiteIcon);
            feedbackPanel.add(whiteLabel);
        }

        return feedbackPanel;
    }
}
