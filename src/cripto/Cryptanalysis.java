package cripto;

import javax.imageio.stream.IIOByteBuffer;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;

public class Cryptanalysis {

    public double[] LETTERFREQ = {8.0, 1.6, 3.0, 4.4, 12.0, 2.5, 1.7, 6.4, 8.0, 0.4, 0.8, 4.0, 3.0, 8.0, 8.0, 1.7, 0.5, 6.2, 8.0, 9.0, 3.4, 1.2, 2.0, 0.4, 2.0, 0.2};
    public String normalText;
    private final char[][] VIGNERE_CIPHER = new char[26][26];
    private String encryptedText;
    private String decryptedText;

    public void buildMatrix() {
        for (int j = 0; j < 26; ++j) {
            VIGNERE_CIPHER[0][j] = (char) (j + 97);
        }
        for (int i = 1; i < 26; ++i) {
            for (int j = 0; j < 25; ++j) {
                VIGNERE_CIPHER[i][j] = VIGNERE_CIPHER[i - 1][j + 1];
            }
            VIGNERE_CIPHER[i][25] = VIGNERE_CIPHER[i - 1][0];
        }
    }

    public void printMatrix() {
        for (int i = 0; i < 26; ++i) {
            for (int j = 0; j < 26; ++j) {
                System.out.printf("%c ", VIGNERE_CIPHER[i][j]);
            }
            System.out.println();
        }
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    public void printEncryptedText() {
        System.out.print("\n\n\n*****CRYPTOTEXT****\n" + encryptedText);

    }

    public void printDecryptedText() {
        System.out.print("\n*****PLAINTEXT****\n" + decryptedText);
    }

    public void setDecryptedText(String decryptedText) {
        this.decryptedText = decryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    public String encryptText(String text, String key) {
        StringBuilder tempString = new StringBuilder();
        int currentLetter = 0;
        text = text.toLowerCase();
        while (currentLetter < text.length()) {
            int currentKeyLetter = 0;
            while (currentKeyLetter < key.length() && currentLetter < text.length()) {
                char textLetter = text.charAt(currentLetter);
                char keyLetter = key.charAt(currentKeyLetter);
                int textLetterIndex = ((int) textLetter) - 97; // get the Vigenere Cipher index of the current letter in text
                int keyLetterIndex = ((int) keyLetter) - 97; // get the Vigenere Cipher index of the current letter in key

                char encLetter = (char) ((textLetterIndex + keyLetterIndex) % 26 + 97); // VIGNERE_CIPHER[textLetterIndex][keyLetterIndex];
                tempString.append(encLetter);
                ++currentKeyLetter;
                ++currentLetter;
            }
        }
        return String.valueOf(tempString);
    }

    public String decryptText(String text, String key) {
        buildMatrix();

        StringBuilder tempString = new StringBuilder();
        int currentLetter = 0;
        while (currentLetter < text.length()) {
            int currentKeyLetter = 0;
            while (currentKeyLetter < key.length() && currentLetter < text.length()) {
                char textLetter = text.charAt(currentLetter);
                char keyLetter = key.charAt(currentKeyLetter);
                int textLetterIndex = ((int) textLetter - 97);
                int keyLetterIndex = ((int) keyLetter - 97);
                for (int i = 0; i < 26; ++i) {
                    if (VIGNERE_CIPHER[keyLetterIndex][i] == textLetter)
                        tempString.append(VIGNERE_CIPHER[0][i]);
                }
                ++currentKeyLetter;
                ++currentLetter;
            }
        }
        return String.valueOf(tempString).toUpperCase();
    }

    //CRYPTANALYSIS

    public double getIC(String alpha) {
        int[] f = new int[26]; // f_i(alpha)
        for (int i = 0; i < alpha.length(); ++i) {
            f[((int) alpha.charAt(i)) - 97]++;
        }

        double num = 0;
        double den = 0;
        for (int i = 0; i < 26; ++i) {
            int value = f[i];
            num += value * (value - 1);
        }
        den = alpha.length();
        return num / (den * (den - 1));
    }

    public String[] k_Groups(String text, int k) {
        String[] groups = new String[k];
        int index = 0;
        for (int j = 1; j <= k; j++) {
            StringBuilder group_i = new StringBuilder();
            int i = 0;
            while ((k * i + j - 1) < text.length()) {
                group_i.append(text.charAt(k * i + j - 1));
                ++i;
            }
            groups[index++] = String.valueOf(group_i);
        }

        return groups;
    }

    public int getKeyLength(String text) {
        int m = 1; //key length
        double bestApprox = 0;
        int bestKey = 0;
        double min = 1000;
        double EN_IC = 0.065;
        while (m < 10) {
            double avg = 0;
            String[] groups = this.k_Groups(text, m);
            for (String group : groups) {
                avg += getIC(group);
            }
            avg = avg / groups.length;
            if (Math.abs(avg - EN_IC) < min) {
                min = Math.abs(avg - EN_IC);
                bestApprox = avg;
                bestKey = m;
            }
            m++;
        }
//        System.out.println("Best key: " + bestKey + " IC value: " + bestApprox);
        System.out.println();
        System.out.println();
        System.out.println();

        return bestKey;
    }

    public void getNormalText() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 26; ++i) {
            for (int j = 0; j < (int) (LETTERFREQ[i] * 10); ++j) {
                temp.append((char) (i + 97));
            }
        }
        normalText = String.valueOf(temp);
    }

    public double getMIC(String alpha, String beta) {
        double mic = 0;
        int[] fA = new int[26];
        int[] fB = new int[26];
        for (int i = 0; i < alpha.length(); ++i) {
            fA[((int) alpha.charAt(i)) - 97]++;
        }
        for (int i = 0; i < beta.length(); ++i) {
            fB[((int) beta.charAt(i)) - 97]++;
        }
        double den = alpha.length() * beta.length();
        double num = 0;
        for (int i = 0; i < 26; ++i) {
            num += fA[i] * fB[i];
        }
        mic = num / den;
        return mic;
    }

    public String findKey(String text, int keyLength) {
        double avg = 0;
        double EN_MIC = 0.065;
        double min = 100000;
        String[] groups = this.k_Groups(text, keyLength);
        StringBuilder keyLetter = new StringBuilder();

        for (int j = 0; j < keyLength; ++j) { // j --> the j'th group (y_{m,j})
            int s = 0;
            min = 100000;
            while (min > 0.01) {
                String Shift = this.encryptText(groups[j], String.valueOf((char) (s % 26 + 97))); /////////
                avg = getMIC(normalText, Shift);
                avg = Double.parseDouble(String.format("%.3f", avg));
                if (Math.abs(EN_MIC - avg) < min) {
                    min = Math.abs(avg - EN_MIC);
                }
                ++s;
            }
            keyLetter.append((char) ((26 - s + 1) % 26 + 97));

        }
        System.out.println("KEY FOUND! ------> " + keyLetter);
        System.out.println();
        System.out.println();
        return String.valueOf(keyLetter);
    }

    public void cryptanalysis() {
        String alpha = encryptedText;
        int m = this.getKeyLength(alpha);
        getNormalText();
        String foundKey = this.findKey(alpha, m);
        System.out.print("*****PLAINTEXT*****\n");
        System.out.println(this.decryptText(alpha, foundKey));
    }
}
