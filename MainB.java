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

        // Initial call to closestPoints method
        closestPoints(sortedX, sortedY, 0, N - 1);
    }

    // Printing should be here to also print Point indices
    public static double closestPoints(ArrayList<Point> X, ArrayList<Point> Y, int lower, int upper) {
        if(X.size() == 2) {
            double min = getDistance(X.get(0), X.get(1));
            System.out.println("Base case 1 distance: " + min);
            return min;
        }

        else if(X.size() == 3) {
            double dist1 = getDistance(X.get(0), X.get(1));
            double dist2 = getDistance(X.get(0), X.get(2));
            double dist3 = getDistance(X.get(1), X.get(2));
            double min = getMinimum(dist1, dist2);
            min = getMinimum(min, dist3);
            System.out.println("Base case 2 distance: " + min);
            return min;
        }
        
        int mid = lower + (upper - lower) / 2; // Video says X[n/2]??
        System.out.println("\nLower: " + lower + "\nUpper: " + upper + "\nMiddle: " + mid);
        ArrayList<Point> leftPart = new ArrayList<>(X.subList(lower, mid));
        ArrayList<Point> rightPart = new ArrayList<>(X.subList(mid + 1, X.size() - 1));

        double leftDist = closestPoints(leftPart, Y, lower, mid);
        double rightDist = closestPoints(rightPart, Y, mid + 1, upper);
        double minDist = getMinimum(leftDist, rightDist);

        System.out.println("Mid point: " + X.get(mid).getX() + " " + X.get(mid).getY());

        ArrayList<Point> S = getStrip(X, Y, minDist, mid);
        displayPoints(S, S.size());

        for(int i = 0; i < S.size(); i++) // Doesn't work; out of bounds
            for(int j = 0; j < 7; j++)
                minDist = getMinimum(minDist, getDistance(S.get(i), S.get(i + j)));

        System.out.println("Min distance test: " + minDist);
        return minDist;

    }

    public static ArrayList<Point> getStrip(ArrayList<Point> X, ArrayList<Point> Y, double min, int mid) {
        ArrayList<Point> S = new ArrayList<>();
        double midX = X.get(mid).getX();

        for(int i = 0; i < Y.size(); i++) {
            Point curr = Y.get(i);

            if(curr.getX() > midX - min && curr.getX() < midX + min){
                S.add(curr);
                System.out.println("\n" + curr.getX() + " > " + midX + " - " + min + " && " + curr.getX() + " < " + midX + " + " + min);
            }
        }

        return S;
    }

    public static double getDistance(Point a, Point b) {
        double distance = Math.sqrt((b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY()));
        return Math.round(distance * 1000000.0) / 1000000.0;
    }

    public static double getMinimum(double a, double b) {
        if(a < b)
            return a;
        return b;
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