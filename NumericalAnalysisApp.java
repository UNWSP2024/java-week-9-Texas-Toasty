package Week9;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class NumericalAnalysisApp {
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("C:/Users/matta/data.txt"));

        int count = 0;
        double sum = 0, max = Double.NEGATIVE_INFINITY, min = Double.POSITIVE_INFINITY;

        while (in.hasNextDouble()) {
            double number = in.nextDouble();
            count++;
            sum += number;
            if (number > max) max = number; // Update max if current number is larger
            if (number < min) min = number; // Update min if current number is smaller
        }

        in.close();

        // Calculate average and range
        double average = count > 0 ? sum / count : 0;
        double range = max - min;

        // Display results
        System.out.println("Quantity of numbers: " + count);
        System.out.printf("Average: %.2f%n", average);
        System.out.println("Largest number: " + max);
        System.out.println("Smallest number: " + min);
        System.out.printf("Range: %.2f%n", range);
    }
}
