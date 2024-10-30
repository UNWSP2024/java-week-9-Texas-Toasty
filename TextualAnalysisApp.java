package Week9;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class TextualAnalysisApp {
    public static void main(String[] args) throws FileNotFoundException {
        
    	Scanner in = new Scanner(new File("C:/Users/matta/test.txt"));

        int letterCount = 0, wordCount = 0, sentenceCount = 0;
        
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            System.out.println(line);

            // Count letters, words, and sentences in each line
            for (char ch : line.toCharArray()) {
                if (Character.isLetter(ch)) letterCount++; // Check for letter
                if (ch == '.' || ch == '!' || ch == '?') sentenceCount++; // Check for end
            }

            wordCount += line.split("\\s+").length; // Count words using whitespaces
        }

        in.close();

        // Calculate averages and display results
        System.out.println("Number of letters: " + letterCount);
        System.out.println("Number of words: " + wordCount);
        System.out.println("Number of sentences: " + sentenceCount);
        System.out.printf("Average letters per word: %.1f%n", wordCount > 0 ? (double) letterCount / wordCount : 0);
        System.out.printf("Average words per sentence: %.1f%n", sentenceCount > 0 ? (double) wordCount / sentenceCount : 0);
        System.out.println("Program ended...");
    }
}

