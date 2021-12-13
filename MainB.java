import java.io.*;
import java.util.*;
import java.awt.Point;

public class MainB {

    // Stores the pair of closest pairs found
    public static Point close1 = new Point();
    public static Point close2 = new Point();
    
    // Distance of the closest pairs
    public static double minimumDistance = Double.MAX_VALUE;

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

            // Call to initialize points and call to the solution method
            problem_2(input, N);
        }
        catch(FileNotFoundException e) {
            System.err.println("File: " + filename + " not found.");
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

    public static void problem_2(ArrayList<String> input, int N) {
        ArrayList<Point> points = toPoints(input, N);

        // Points sorted by X values
        ArrayList<Point> sortedX = new ArrayList<>(points);
        sortedX.sort(Comparator.comparing(Point::getX));

        // Points sorted by Y values
        ArrayList<Point> sortedY = new ArrayList<>(points);
        sortedY.sort(Comparator.comparing(Point::getY));

        // Initial call to closestPoints method
        double min = closestPoints(sortedX, sortedY, 0, N - 1);

        // Solution output
        System.out.print("" + points.indexOf(close1) + " " + points.indexOf(close2) + " " + min);
    }

    public static double closestPoints(ArrayList<Point> X, ArrayList<Point> Y, int lower, int upper) {
        int size = upper - lower + 1;

        // Brute for solution for when size is "manageable" (2 or 3 points)
        if(size <= 3) {
            return bruteForce(X);
        }
        
        int mid = lower + (upper - lower) / 2;

        // Gets the minimum distance from both partitions
        double leftDist = closestPoints(X, Y, lower, mid);
        double rightDist = closestPoints(X, Y, mid + 1, upper);
        double minDist = Math.min(leftDist, rightDist);

        // Gets the strip of the plane bounded by the attained minimum distance (minDist)
        ArrayList<Point> S = getStrip(X, Y, minDist, mid);

        // Compares points in strip
        for (int i = 0; i < S.size(); i++)
            for(int j = i + 1; j < S.size() && (S.get(j).getY() - S.get(i).getY() < minDist); j++){
                double distCompare = getDistance(S.get(i), S.get(j));
                minDist = Math.min(minDist, distCompare);
                if(distCompare < minimumDistance) {
                    close1 = S.get(i);
                    close2 = S.get(j);
                    minimumDistance = distCompare;
                }
            }

        return minDist;
    }

    // Brute force comparisons of point distances (for when size <= 3)
    public static double bruteForce(ArrayList<Point> points) {
        double min = 0;
        for(int i = 0; i < points.size(); i++)
            for(int j = i + 1; j < points.size(); j++) {
                min = getDistance(points.get(i), points.get(j));
                if(min < minimumDistance) {
                    close1 = points.get(i);
                    close2 = points.get(j);
                    minimumDistance = min;
                }
            }

        return min;
    }

    // Returns the array of points that are within the strip bounded by the found minimum distance
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

    // Gets the Eucledian distance of two points
    public static double getDistance(Point a, Point b) {
        double distance = Math.sqrt((b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY()));
        return Math.round(distance * 1000000.0) / 1000000.0;
    }

    // Initializes ArrayList of points
    public static ArrayList<Point> toPoints(ArrayList<String> input, int N) {
        ArrayList<Point> points = new ArrayList<>();

        for(int i = 0; i < N*2; i += 2)
            points.add(new Point(Integer.parseInt(input.get(i)), Integer.parseInt(input.get(i + 1))));

        return points;
    }

}