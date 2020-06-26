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

    public DijkstraGraph<String> adding() {
        DijkstraGraph<String> dataDijkstra = new DijkstraGraph<>();
        dataimport s = new dataimport();
        Node<String> newNode;
        String[] data = s.getData().split("=");

        for (String city : data) {
            String[] subcity = city.split("#");
            newNode = new Node<>(subcity[0]);
            for (int i = 1; i < subcity.length; i++) {
                String value = subcity[i];
                String[] dist = value.split("-");
                Double cost = Double.parseDouble(dist[1]);
                newNode.addPeers(new Edge<>(dist[0],  cost));
            }
            dataDijkstra.addNode(newNode);
        }
        return dataDijkstra;
    }
}
