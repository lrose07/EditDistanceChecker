import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Lauren Rose
 * @version v0.1
 *
 * ITEC 360 - Data Structures and Analysis of Algorithms
 * Department of Information Technology
 * Radford University
 *
 * This program uses a dynamic programming algorithm
 * to highlight words in a passage of text that are
 * within a weighted edit distance of a search word.
 */
class DisChecker {

    private String searchWord;
    private int maxEditDistance;
    private int[] weights;

    private ArrayList<String> textLines = new ArrayList<>();

    /**
     * Constructs a new DisChecker object
     */
    DisChecker() {
        getInput();
    }

    // gets input for processing
    private void getInput() {
        Scanner fileScan = new Scanner(System.in);

        searchWord = fileScan.nextLine();
        String editDistance = fileScan.nextLine();
        String sWeights = fileScan.nextLine();

        while (fileScan.hasNextLine()) {
            textLines.add(fileScan.nextLine());
        }

        parseEditDistance(editDistance);
        parseWeights(sWeights);

        for (String line : textLines) {
            line = processLine(line);
            System.out.println(line);
        }

        System.out.println(searchWord);
        System.out.println(editDistance);
        System.out.println(sWeights);
    }

    // parses the input max distance from a string to an int
    private void parseEditDistance(String s) {
        maxEditDistance = Integer.parseInt(s);
    }

    // parses the input weights from strings to an int array
    private void parseWeights(String s) {
        String[] weightsAsStrings = s.split("\\s+");
        weights = new int[4];
        for (int i = 0; i < 4; i++) {
            weights[i] = Integer.parseInt(weightsAsStrings[i]);
        }
    }

    // processes a single line of input
    private String processLine(String line) {
        StringBuilder sbMain = new StringBuilder();
        StringBuilder sbInternal = new StringBuilder();
        boolean wordBeingFormed = false;
        for (int i = 0; i < line.length(); i++) {
            String currentLetter = line.substring(i, i+1);
            if (currentLetter.matches("[^a-zA-Z]")) {
                if (wordBeingFormed) {
                    String wordToCheck = sbInternal.toString();
                    wordToCheck = processNormalWord(wordToCheck);
                    sbMain.append(wordToCheck);
                    sbInternal.delete(0,sbInternal.length());
                    wordBeingFormed = false;
                }
                sbMain.append(currentLetter);
            } else {
                sbInternal.append(currentLetter);
                wordBeingFormed = true;
            }
        }

        if (wordBeingFormed) {
            String wordToCheck = sbInternal.toString();
            wordToCheck = processNormalWord(wordToCheck);
            sbMain.append(wordToCheck);
        }

        return sbMain.toString();
    }

    // sends a single word for processing
    private String processNormalWord(String word) {
        if (isWithinDistance(word)) {
            return "(" + word + ")";
        }
        return word;
    }

    // returns if a word is within the edit distance
    private boolean isWithinDistance(String word) {
        if (word.length() == 0) {
            return false;
        } else {
            return editDistDP(searchWord, word) <= maxEditDistance;
        }
    }

    // runs algorithm on given word
    private int editDistDP(String word1, String word2) {
        String str1 = word1.toLowerCase();
        String str2 = word2.toLowerCase();

        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    if (str1.charAt(i) == str2.charAt(j)) {
                        dp[i][j] = weights[0];
                    } else {
                        dp[i][j] = weights[3];
                    }
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + weights[1];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + weights[2];
                } else if (str1.charAt(i) == str2.charAt(j)) {
                    dp[i][j] = Math.min(dp[i][j - 1] + weights[1],  // Insert
                            Math.min(dp[i - 1][j] + weights[2],  // Remove
                                    dp[i - 1][j - 1] + weights[0])); // Copy
                } else {
                    dp[i][j] = Math.min(dp[i][j - 1] + weights[1],  // Insert
                            Math.min(dp[i - 1][j] + weights[2],  // Remove
                                    dp[i - 1][j - 1] + weights[3])); // Replace
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}

