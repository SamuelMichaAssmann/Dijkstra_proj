package dijkstra;

import graphtheory.Edge;
import graphtheory.Node;
import graphtheory.Path;

import java.util.*;



/**
 * implements a Graph specially optimized to compute the best path by using the
 * Dijkstra-Algorithm
 * Samuel_20.05.20
 */
@SuppressWarnings("WeakerAccess")
public class DijkstraGraph<T> {
    private ArrayList<Node<T>> nodes = new ArrayList<>();


    public void addNode(Node<T> newNode) {
        this.nodes.add(newNode);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Node<T> t:nodes) {
            s.append(t.toString()).append("  ");
        }
        return s.toString();
    }

    /**
     * computes the cheapest path from sourcePoint to destinationPoint using Dijkstra and the
     *    provided set of possible nodes
     * @param sourcePoint Where the path should begin   (must be present in the provided set of nodes)
     * @param destinationPoint Where the path should end   (must be present in the provided set of nodes)
     * @return A path describing the cheapest route found
     */

    public Path<T> compute(T sourcePoint, T destinationPoint) {
        if ( this.nodes.stream().noneMatch( node -> node.getPoint().equals(sourcePoint) ) )   {
            throw new IllegalArgumentException("The desired source point is not defined in the " + "provided set of nodes!");
        }
        if ( this.nodes.stream().noneMatch( node -> node.getPoint().equals(destinationPoint) ) )   {
            throw new IllegalArgumentException("The desired destination point is not defined in " + "the provided set of nodes!");
        }

        Map<T, Double> costs = new HashMap<>();    // a TreeMap is not suited, because it is
        // unnecessary to sort the Map further
        // entry<nodeName, cost>
        Map<T, T> predecessors = new HashMap<>();
        List<T> unvisitedPoints = new LinkedList<>();

        Node<T> currentNode     = null;
        Double currentCost     = 0d;

        for ( Node<T> node : this.nodes )   {   // initialize all known nodes
            unvisitedPoints.add(node.getPoint());
            predecessors.put(node.getPoint(), null); // the best predecessor is yet
            //   unknown

            if ( node.getPoint().equals(sourcePoint) )   {
                costs.put(node.getPoint(), 0d);
                currentNode = node;
            }
            else {
                costs.put(node.getPoint(), Double.POSITIVE_INFINITY); // as defined by Dijkstra
            }
        }

        while( unvisitedPoints.contains(destinationPoint) )   {
            assert currentNode != null;
            updatePeersForLowerCost(costs, predecessors, currentNode, currentCost);
            unvisitedPoints.remove(currentNode.getPoint()); // a node is never visited multiple times

            if ( unvisitedPoints.contains(destinationPoint) )    {
                // keep on searching...

                final T newCurrentPoint = findUnvisitedPointWithLowestAssumedCost(costs, unvisitedPoints);
                // because the java-Lambda only accepts final vars

                currentNode = nodes.stream().filter( node -> node.getPoint().equals(newCurrentPoint) ).findFirst().get();
                currentCost = costs.get(newCurrentPoint);
            }
        }       // end of while ; destination was visited
        return new Path<>(sourcePoint, destinationPoint, currentCost, reconstructBestPath(sourcePoint, destinationPoint ,predecessors));
    }

    /**
     * checks if we have found a cheaper route to the peers of the current node
     * @param costs The Map which represents the current, assumed costs
     * @param predecessors The Map which represents the current, assumed best predecessors
     * @param currentNode The Node whose peers should be checked
     * @param currentCost The costs needed to get to currentNode
     */
    private void updatePeersForLowerCost(Map<T, Double> costs, Map<T, T> predecessors, Node<T> currentNode, Double currentCost){
        for ( Edge<T> peerEdge : currentNode.getPeers() )   {
            Double tentativeCost = currentCost + peerEdge.getCost();
            T tentativeDestinationPoint  = peerEdge.getDestinationPoint();

            if (costs.get(tentativeDestinationPoint) > tentativeCost) {
                costs.put(tentativeDestinationPoint, tentativeCost);
                predecessors.put(tentativeDestinationPoint, currentNode.getPoint());
            }
            // otherwise, the current (smaller) value and the current predecessor is kept
        }
    }

    private T findUnvisitedPointWithLowestAssumedCost(Map<T, Double> costs, List<T> unvisitedPoints) {
        T newCurrentPoint = null;
        Double newCurrentCost = Double.POSITIVE_INFINITY;

        for (T point : unvisitedPoints) {
            Double pointCost = costs.get(point);

            if (pointCost < newCurrentCost) {
                newCurrentPoint = point;
                newCurrentCost = pointCost;
            }
        }

        assert newCurrentPoint != null;
        return newCurrentPoint;
    }

    private List<T> reconstructBestPath(T sourcePoint, T destinationPoint, Map<T, T> predecessorMap){
        List<T> bestLayoverPoints = new LinkedList<>();

        T currentBackPoint = destinationPoint;
        while ( predecessorMap.get(currentBackPoint) != sourcePoint )   {
            bestLayoverPoints.add(0, predecessorMap.get(currentBackPoint));
            currentBackPoint = predecessorMap.get(currentBackPoint);
        }
        return bestLayoverPoints;
    }
}