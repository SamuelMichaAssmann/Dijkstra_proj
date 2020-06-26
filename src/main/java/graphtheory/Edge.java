package graphtheory;

/**
 * Data Structure to save one Edge between Nodes in a Graph
 *    edge = (de)Kante   d.h. Verbindung
 * Created by Samuel on 20.05.20
 */
public class Edge<T> {
    private final T destinationPoint;
    private final Double cost;


    public Edge(T destinationPoint, Double cost) {
        this.destinationPoint = destinationPoint;
        this.cost = cost;
    }

    public T getDestinationPoint() {
        return destinationPoint;
    }

    public Double getCost() {
        return cost;
    }

    public String toString() {
        return (String) destinationPoint + "-" + cost;
    }
}