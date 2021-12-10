import java.io.*;
import java.util.*;
import java.awt.Point;

public class MainB {
    public static void main(String[] args) {
        String filename = "test-cases-B.txt";
        try {
            Scanner sc = new Scanner(new File(filename));
            ArrayList<String> input = new ArrayList<>();
            int N = sc.nextInt();

            sc.nextLine();

            for(int i = 0; i < N*2; i++)
                input.add(String.valueOf(sc.nextInt()));

            sc.close();

            problem_2(input, N);
        }
        catch(FileNotFoundException e) {
            System.err.println("File: " + filename + " not found.");
        }
        // catch(Exception e) {
        //     System.err.println(e);
        // }
    }

    public static void problem_2(ArrayList<String> input, int N) {
        ArrayList<Point> points = toPoints(input, N);

        ArrayList<Point> sortedX = new ArrayList<>(points);
        sortedX.sort(Comparator.comparing(Point::getX));
        ArrayList<Point> sortedY = new ArrayList<>(points);
        sortedY.sort(Comparator.comparing(Point::getY));

        System.out.println("" + getDistance(points.get(0), points.get(3)));

        // Initial call to closestPoints method
        //closestPoints(sortedX, sortedY, N);
    }

    public static void closestPoints(ArrayList<Point> X, ArrayList<Point> Y, int N) {

    }

    public static double getDistance(Point a, Point b) {
        double distance = Math.sqrt((b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY()));
        return Math.round(distance * 1000000.0) / 1000000.0;
    }

    public static ArrayList<Point> toPoints(ArrayList<String> input, int N) {
        ArrayList<Point> points = new ArrayList<>();

        for(int i = 0; i < N*2; i += 2)
            points.add(new Point(Integer.parseInt(input.get(i)), Integer.parseInt(input.get(i + 1))));

        return points;
    }

    public static void displayPoints(ArrayList<Point> p, int N) {
        for(int i = 0; i < N; i++)
            System.out.println("Point: [" + p.get(i).getX() + ", " + p.get(i).getY() + "]");
    }
}