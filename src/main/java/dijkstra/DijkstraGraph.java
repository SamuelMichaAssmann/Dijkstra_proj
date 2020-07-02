package dijkstra;

import graphtheory.Edge;
import graphtheory.Node;
import graphtheory.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.List;



/**
 * implements a Graph specially optimized to compute the best path by using the
 * Dijkstra-Algorithm
 * Samuel_20.05.20
 */
@SuppressWarnings("WeakerAccess")
public class DijkstraGraph<T> {
    private ArrayList<Node<T>> nodes = new ArrayList<>();
    private final static Logger log = LoggerFactory.getLogger(DijkstraGraph.class);

    public void addNode(Node<T> newNode) {
        this.nodes.add(newNode);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Node<T> t : nodes) {
            s.append(t.toString()).append("  ");
        }
        return s.toString();
    }

    /**
     * computes the cheapest path from sourcePoint to destinationPoint using Dijkstra and the
     * provided set of possible nodes
     *
     * @param sourcePoint      Where the path should begin   (must be present in the provided set of nodes)
     * @param destinationPoint Where the path should end   (must be present in the provided set of nodes)
     * @return A path describing the cheapest route found
     */

    public Path<T> compute(T sourcePoint, T destinationPoint) {
        log.info("Traveling from {} to {}", sourcePoint, destinationPoint);

        if (this.nodes.stream().noneMatch(node -> node.getPoint().equals(sourcePoint))) {
            throw new IllegalArgumentException("The desired source point is not defined in the " + "provided set of nodes!");
        }
        if (this.nodes.stream().noneMatch(node -> node.getPoint().equals(destinationPoint))) {
            throw new IllegalArgumentException("The desired destination point is not defined in " + "the provided set of nodes!");
        }

        Map<T, Double> costs = new HashMap<>();    // a TreeMap is not suited, because it is
        // unnecessary to sort the Map further
        // entry<nodeName, cost>
        Map<T, T> predecessors = new HashMap<>();
        List<T> unvisitedPoints = new LinkedList<>();

        Node<T> currentNode = null;
        Double currentCost = 0d;

        System.out.println("Checking " + this.nodes.size() + " Nodes");
        for (Node<T> node : this.nodes) {   // initialize all known nodes
            unvisitedPoints.add(node.getPoint());
            predecessors.put(node.getPoint(), null); // the best predecessor is yet
            //   unknown

            if (node.getPoint().equals(sourcePoint)) {
                System.out.println(node.getPoint() + " is same as " + sourcePoint + ". Setting 0 dist to point current node");
                costs.put(node.getPoint(), 0d);
                currentNode = node;
            } else {
                System.out.println("setting " + node.getPoint() + " infinite");
                costs.put(node.getPoint(), Double.POSITIVE_INFINITY); // as defined by Dijkstra
            }
        }
        assert nodes.size() == unvisitedPoints.size();
        assert nodes.size() == costs.size();


        while (unvisitedPoints.contains(destinationPoint)) {
            assert currentNode != null;
            updatePeersForLowerCost(costs, predecessors, currentNode, currentCost);
            System.out.println("remove current node " + currentNode);
            unvisitedPoints.remove(currentNode.getPoint()); // a node is never visited multiple times

            if (unvisitedPoints.contains(destinationPoint)) {
                // keep on searching...

                final T newCurrentPoint = findUnvisitedPointWithLowestAssumedCost(costs, unvisitedPoints);
                // because the java-Lambda only accepts final vars

                System.out.println("New current Point " + newCurrentPoint);
                currentNode = nodes.stream().filter(node -> node.getPoint().equals(newCurrentPoint)).findFirst().orElseThrow(RuntimeException::new);
                currentCost = costs.get(newCurrentPoint);
                System.out.println("reconstructed " + currentNode + " with " + currentCost);
            }else
                System.out.println("unvisited points doesn't contain unvisited points " + destinationPoint);
        }       // end of while ; destination was visited
        System.out.println("listen: unvisited " + unvisitedPoints.size() + " predecessors " + predecessors.size());
        List<T> reconstructBestPath = reconstructBestPath(sourcePoint, destinationPoint, predecessors);
        return new Path<>(sourcePoint, destinationPoint, currentCost, reconstructBestPath);
    }

    /**
     * checks if we have found a cheaper route to the peers of the current node
     *
     * @param costs        The Map which represents the current, assumed costs
     * @param predecessors The Map which represents the current, assumed best predecessors
     * @param currentNode  The Node whose peers should be checked
     * @param currentCost  The costs needed to get to currentNode
     */
    private void updatePeersForLowerCost(Map<T, Double> costs, Map<T, T> predecessors, Node<T> currentNode, Double currentCost) {
        System.out.println("Checking " + currentNode.getPeers().size() + " nodes");
        for (Edge<T> peerEdge : currentNode.getPeers()) {
            Double tentativeCost = currentCost + peerEdge.getCost();
            T tentativeDestinationPoint = peerEdge.getDestinationPoint();
            System.out.println("Cost dest " + tentativeCost + " of tntPoint " + tentativeDestinationPoint);

            if (costs.get(tentativeDestinationPoint) > tentativeCost) {
                System.out.println("Costs higher");
                costs.put(tentativeDestinationPoint, tentativeCost);
                predecessors.put(tentativeDestinationPoint, currentNode.getPoint());
            } else
                System.out.println("Costs equals or lower");
            // otherwise, the current (smaller) value and the current predecessor is kept
        }
    }

    private T findUnvisitedPointWithLowestAssumedCost(Map<T, Double> costs, List<T> unvisitedPoints) {
        T newCurrentPoint = null;
        Double newCurrentCost = Double.POSITIVE_INFINITY;

        System.out.println("Checking " + unvisitedPoints.size() + " nodes");
        for (T point : unvisitedPoints) {
            Double pointCost = costs.get(point);

            System.out.println("Cost dest " + pointCost + " of Point " + point);

            if (pointCost < newCurrentCost) {
                System.out.println( pointCost + " Pointcost lower " + newCurrentCost);
                newCurrentPoint = point;
                newCurrentCost = pointCost;
                System.out.println("setting " + point + "new cost = " + pointCost);
            }else
                System.out.println("New current cost bigger");
        }

        assert newCurrentPoint != null;
        return newCurrentPoint;
    }

    private List<T> reconstructBestPath(T sourcePoint, T destinationPoint, Map<T, T> predecessorMap) {
        System.out.println("Best path from " + sourcePoint + " to " + destinationPoint);
        assert predecessorMap.containsKey(destinationPoint);
        assert predecessorMap.containsKey(sourcePoint);
        List<T> bestLayoverPoints = new LinkedList<>();

        T currentBackPoint = destinationPoint;
        System.out.println("looking up current backpoint " + currentBackPoint + " against " + predecessorMap.size() + " str elements");

        T prepoint = predecessorMap.get(currentBackPoint);
        assert prepoint != null;
        do {
            assert prepoint != null;
            System.out.println("Current prepoint " + prepoint);
            if (prepoint.equals(sourcePoint)) {

                System.out.println("Fertig");
                break;
            }
            System.out.println("prepoint to 0 " + prepoint);
            bestLayoverPoints.add(0, prepoint);
            currentBackPoint = prepoint;
        }while ((prepoint = predecessorMap.get(currentBackPoint)) != sourcePoint);

        return bestLayoverPoints;
    }
}