package graphtheory;


import java.util.List;



/**
 * Data Structure to store Details about a Path, specially suited for paths representing a route
 * Created by Samuel on 16.06.2020
 */

public class Path<T> {
    private final T               startPoint;
    private final T               endPoint;
    private final List<T>         layoverPoints;
    private final Double          totalCost;


    public Path(T startPoint, T endPoint, Double totalCost, List<T> layoverPoints) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.totalCost = totalCost;
        this.layoverPoints = layoverPoints;
    }


    public T getStartPoint() {
        return startPoint;
    }

    public T getEndPoint() {
        return endPoint;
    }

    public List<T> getLayoverPoints() {
        return layoverPoints;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void print() {
        System.out.println(this.startPoint.toString() + " -> "
                + this.endPoint.toString() + "\n->| " + this.totalCost
                + " km |<-\n\nRoute:");

        System.out.print("   " + this.startPoint.toString() + " => ");
        for (T point : this.layoverPoints) {
            System.out.print(point.toString() + " => ");
        }
        System.out.println(this.endPoint.toString());
    }

}
