import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreEntry {
    private String playerName;
    private static int attemptsCount;

    // Lista estática para armazenar as entradas de pontuação
    private static List<ScoreEntry> scoreList = new ArrayList<>();

    public ScoreEntry(String playerName, int attemptsCount) {
        this.playerName = playerName;
        this.attemptsCount = attemptsCount;
    }

    // Método estático para registrar a pontuação
    public static void scoreGame(String playerName, int attemptsCount) {
        ScoreEntry score = new ScoreEntry(playerName, attemptsCount);
        scoreList.add(score);
        // Ordena a lista de pontuações
        Collections.sort(scoreList, (s1, s2) -> 10 - attemptsCount);
        // Mantém apenas os top 5 scores
        if (scoreList.size() > 5) {
            scoreList = scoreList.subList(0, 5);
        }
        // Escreve os dados no arquivo de texto
        writeScoresToFile();
    }

    // Método para escrever os scores no arquivo de texto
    private static void writeScoresToFile() {
        String fileName = "scores.txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // true para append
        for (ScoreEntry score : scoreList) {
            writer.write(score.getPlayerName() + "," + (10 - attemptsCount));
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
    }
}

    // Método para obter a lista de top 5 melhores tentativas
    public static List<ScoreEntry> getTopScores() {
        return scoreList;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getAttempts() {
        return attemptsCount;
    }

    @Override
    public String toString() {
        return "Jogador: " + playerName + ", Tentativas: " + attemptsCount;
    }
}
