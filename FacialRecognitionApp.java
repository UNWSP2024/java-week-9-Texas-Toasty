package Week9;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FacialRecognitionApp {
    static class Face {
        String name;
        double[] measurements;
        double[] ratios;

        Face(String name, double[] measurements) {
            this.name = name;
            this.measurements = measurements;
            this.ratios = calculateRatios(measurements);
        }

        //  Calculate the 15 ratios based on measurements A, B, C, D, E, F
        private double[] calculateRatios(double[] m) {
            return new double[]{
                m[0] / m[1], m[0] / m[2], m[0] / m[3], m[0] / m[4], m[0] / m[5],
                m[1] / m[2], m[1] / m[3], m[1] / m[4], m[1] / m[5],
                m[2] / m[3], m[2] / m[4], m[2] / m[5],
                m[3] / m[4], m[3] / m[5],
                m[4] / m[5]
            };
        }
    }

    //  Parse the file and load known faces
    private static List<Face> loadKnownFaces(String filename) throws IOException {
        List<Face> faces = new ArrayList<>();
        File file = new File(filename);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                
                String name = parts[0];
                double[] measurements = new double[6];
                measurements[0] = Double.parseDouble(parts[3].replace("mm", ""));
                measurements[1] = Double.parseDouble(parts[6].replace("mm", ""));
                measurements[2] = Double.parseDouble(parts[9].replace("mm", ""));
                measurements[3] = Double.parseDouble(parts[12].replace("mm", ""));
                measurements[4] = Double.parseDouble(parts[15].replace("mm", ""));
                measurements[5] = Double.parseDouble(parts[18].replace("mm", ""));
                
                faces.add(new Face(name, measurements));
            }
        }
        return faces;
    }

    //  Calculate sum of squares % difference
    private static double calculateSumOfSquaresDifference(double[] ratios1, double[] ratios2) {
        double sum = 0.0;
        for (int i = 0; i < ratios1.length; i++) {
            sum += Math.pow((ratios2[i] - ratios1[i]) / ratios1[i], 2);
        }
        return sum;
    }

    //  Get the mystery face measurements from user
    private static Face getMysteryFace() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter measurements for the mystery picture (in mm):");
            double[] measurements = new double[6];
            String[] labels = {"A", "B", "C", "D", "E", "F"};
            for (int i = 0; i < 6; i++) {
                System.out.print(labels[i] + ": ");
                measurements[i] = scanner.nextDouble();
            }
            return new Face("Mystery", measurements);
        }
    }

    //  Find best method
    private static String findBestMatch(Face mysteryFace, List<Face> knownFaces) {
        String bestMatch = null;
        double minDifference = Double.MAX_VALUE;

        for (Face knownFace : knownFaces) {
            double difference = calculateSumOfSquaresDifference(knownFace.ratios, mysteryFace.ratios);
            if (difference < minDifference) {
                minDifference = difference;
                bestMatch = knownFace.name;
            }
        }

        return bestMatch;
    }

    	public static void main(String[] args) {
    	    try {
    	        //  Load known faces file
    	        List<Face> knownFaces = loadKnownFaces("C:/Users/matta/known_faces.txt");

    	        // Get mystery face from user
    	        Face mysteryFace = getMysteryFace();

    	        //  Find best match
    	        String bestMatch = findBestMatch(mysteryFace, knownFaces);

    	        // Output the result
    	        System.out.println("The mystery picture is most likely: " + bestMatch);

    	    } catch (IOException e) {
    	        System.err.println("Error reading the known faces file: " + e.getMessage());
    	    }
    	}

}
