package dataStructures;

import java.io.Serializable;

public class StringMatching implements Serializable {

    private static final int ALPHABET_SIZE = 256; // Assumindo ASCII/Extended ASCII [cite: 163]

    // Implementação do slide 11: Tabela de más ocorrências
    private static int[] buildLast(String pattern) {
        int[] last = new int[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++)
            last[i] = -1;

        for (int i = 0; i < pattern.length(); i++)
            last[pattern.charAt(i)] = i;

        return last;
    }

    // Implementação do slide 18: Algoritmo Boyer-Moore
    // Retorna o índice da primeira ocorrência ou -1 se não encontrar
    public static int boyerMoore(String text, String pattern) {
        if (pattern.length() == 0) return 0;
        if (text.length() == 0) return -1;

        int[] last = buildLast(pattern);
        int m = pattern.length();
        int n = text.length();
        int shift = 0;

        while (shift <= (n - m)) {
            int j = m - 1;

            // Varrimento da direita para a esquerda [cite: 441]
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j))
                j--;

            if (j < 0) {
                return shift; // Encontrou ocorrência na posição 'shift' [cite: 444]
                // Se quiséssemos continuar: shift += (shift + m < n) ? m - last[text.charAt(shift + m)] : 1;
            } else {
                // Heurística do caráter errado [cite: 447-449]
                char bad = text.charAt(shift + j);
                // Math.max(1, j - last[bad]) garante que o shift é sempre positivo
                // Nota: Usamos bad & 0xFF ou garantimos que é ASCII para não dar erro de índice
                int lastOccur = (bad < ALPHABET_SIZE) ? last[bad] : -1;
                shift += Math.max(1, j - lastOccur);
            }
        }
        return -1;
    }
}