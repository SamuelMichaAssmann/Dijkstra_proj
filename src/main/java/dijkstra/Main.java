package dijkstra;

import graphtheory.Edge;
import graphtheory.Node;
import managedata.dataimport;
import managedata.datawork;

import java.util.ArrayList;


class Main {
    public static <T> void main(String[] args) {
        //--- initialize
        DijkstraGraph<String> dijkstra = new DijkstraGraph<String>();

        //--- initialize
        Node<String> newNode;

        newNode = new Node<String>("Mannheim");
        newNode.addPeers(new Edge<>("Frankfurt", 85d));
        newNode.addPeers(new Edge<>("Karlsruhe", 80d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("Würzburg");
        newNode.addPeers(new Edge<>("Frankfurt", 217d));
        newNode.addPeers(new Edge<>("Erfurt",    186d));
        newNode.addPeers(new Edge<>("Nürnberg",  103d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("Stuttgart");
        newNode.addPeers(new Edge<>("Nürnberg",  183d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("Kassel");
        newNode.addPeers(new Edge<>("Frankfurt", 173d));
        newNode.addPeers(new Edge<>("München",   502d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("Karlsruhe");
        newNode.addPeers(new Edge<>("Mannheim",  80d));
        newNode.addPeers(new Edge<>("Augsburg",  250d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("Erfurt");
        newNode.addPeers(new Edge<>("Würzburg",  186d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("Nürnberg");
        newNode.addPeers(new Edge<>("Würzburg",  103d));
        newNode.addPeers(new Edge<>("Stuttgart", 183d));
        newNode.addPeers(new Edge<>("München",   167d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("Augsburg");
        newNode.addPeers(new Edge<>("Karlsruhe", 250d));
        newNode.addPeers(new Edge<>("München",   84d));
        dijkstra.addNode(newNode);

        newNode = new Node<String>("München");
        newNode.addPeers(new Edge<>("Augsburg",  84d));
        newNode.addPeers(new Edge<>("Nürnberg",  167d));
        newNode.addPeers(new Edge<>("Kassel",    502d));
        dijkstra.addNode(newNode);

        datawork d = new datawork();
        dijkstra = d.adding(dijkstra);

        //print all points
        System.out.println("-------------------------------------");
        System.out.println(dijkstra.toString());
        System.out.println("-------------------------------------");

        // make the magic happen:
        dijkstra.compute("Oberzent", "Stuttgart").print();
    }

}