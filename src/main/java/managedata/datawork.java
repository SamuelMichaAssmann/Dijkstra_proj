package managedata;

import dijkstra.DijkstraGraph;
import graphtheory.Edge;
import graphtheory.Node;

import java.io.*;
import java.net.URL;

/**
 * Data Structure to save one Edge between Nodes in a Graph
 *    edge = (de)Kante   d.h. Verbindung
 * Created by Samuel on 16.06.2020
 */

public class datawork {
    dataimport s = new dataimport();
    Node<String> newNode;

    public DijkstraGraph<String> adding(DijkstraGraph<String> dijkstra) {
        String[] data = s.getData().split("=");

        for (String city : data) {
            String[] subcity = city.split("#");
            newNode = new Node<String>((String) subcity[0]);
            for (int i = 1; i < subcity.length; i++) {
                String value = subcity[i];
                String[] dist = value.split("-");
                Double cost = Double.parseDouble(dist[1]);
                newNode.addPeers(new Edge<>((String)dist[0],  cost));
            }
            dijkstra.addNode(newNode);
        }


        newNode = new Node<String>("Frankfurt");
        newNode.addPeers(new Edge<>("Mannheim",  85d));
        newNode.addPeers(new Edge<>("Würzburg",  217d));
        newNode.addPeers(new Edge<>("Kassel",    173d));
        dijkstra.addNode(newNode);
        return dijkstra;
    }
}
