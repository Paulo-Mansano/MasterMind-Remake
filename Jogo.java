import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Jogo {

    private String[] coresEs;

    // Construtor da classe Jogo
    public Jogo(int numCores) {
        coresEs = new String[numCores];
        EscolhaDasCores(new String[] { "botaoAzul.png", "botaoVermelho.png", "botaoRoxo.png", "botaoVerde.png",
                "botaoRosa.png", "botaoAmarelo.png" }, numCores);
    }

    // Método para escolher as cores
    public void EscolhaDasCores(String[] cores, int numCores) {
        ArrayList<String> coresList = new ArrayList<>();
        Collections.addAll(coresList, cores);
        Collections.shuffle(coresList);

        coresEs = new String[numCores];
        for (int i = 0; i < numCores; i++) {
            coresEs[i] = coresList.get(i);
        }
    }

    // Método para obter as cores escolhidas
    public String[] getCoresEs() {
        return coresEs;
    }

    // Método para perguntar o número de cores
    public static int perguntarNumeroCores() {
        String[] opcoes = { "4", "6" };
        String input = (String) JOptionPane.showInputDialog(null, "Escolha o número de cores para jogar:",
                "Número de Cores", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
        return input != null ? Integer.parseInt(input) : 4;
    }
}
