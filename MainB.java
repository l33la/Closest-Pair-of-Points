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
        System.out.println(closestPoints(sortedX, sortedY, 0, N - 1));
    }

    // Printing should be here to also print Point indices
    public static double closestPoints(ArrayList<Point> X, ArrayList<Point> Y, int lower, int upper) {
        int size = upper - lower + 1;
        if(size == 2) 
            return getDistance(X.get(0), X.get(1));

        else if(size == 3) {
            double dist1 = getDistance(X.get(0), X.get(1));
            double dist2 = getDistance(X.get(0), X.get(2));
            double dist3 = getDistance(X.get(1), X.get(2));
            double min = getMinimum(dist1, dist2);
            min = getMinimum(min, dist3);
            return min;
        }
        
        int mid = lower + (upper - lower) / 2;

        double leftDist = closestPoints(X, Y, lower, mid);
        double rightDist = closestPoints(X, Y, mid + 1, upper);
        double minDist = getMinimum(leftDist, rightDist);

        ArrayList<Point> S = getStrip(X, Y, minDist, mid);

        for (int i = 0; i<S.size(); i++)
            for(int j = i + 1; j < S.size() && (S.get(j).getY() - S.get(i).getY() < minDist); j++)
                minDist = getMinimum(minDist, getDistance(S.get(i), S.get(j)));

        return minDist;
    }

    public static ArrayList<Point> getStrip(ArrayList<Point> X, ArrayList<Point> Y, double min, int mid) {
        ArrayList<Point> S = new ArrayList<>();
        double midX = X.get(mid).getX();

        for(int i = 0; i < Y.size(); i++) {
            Point curr = Y.get(i);

            if(curr.getX() > midX - min && curr.getX() < midX + min)
                S.add(curr);
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