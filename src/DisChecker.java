import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class DisChecker {

    private String searchWord;
    private int maxEditDistance;
    private int[] weights;

    DisChecker() {
        getInput();
    }

    private void getInput() {
        Scanner scan = new Scanner(System.in);

        System.out.print("File name: ");
        File file = new File(scan.next());

        try {
            Scanner fileScan = new Scanner(file);

            searchWord = fileScan.nextLine();
            String editDistance = fileScan.nextLine();
            String sWeights = fileScan.nextLine();

            StringBuilder sb = new StringBuilder();

            while (fileScan.hasNextLine()) {
                sb.append(fileScan.nextLine());
                sb.append("\n");
            }

            System.out.println(searchWord);
            System.out.println(editDistance);
            System.out.println(sWeights);
            System.out.println(sb);

            parseEditDistance(editDistance);
            parseWeights(sWeights);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    void parseEditDistance(String s) {

    }

    void parseWeights(String s) {

    }
}
