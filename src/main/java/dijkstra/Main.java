package dijkstra;

import graphtheory.Edge;
import graphtheory.Node;
import managedata.dataimport;
import managedata.datawork;

import java.util.ArrayList;


class Main {
    public static <T> void main(String[] args) {

        datawork d = new datawork();
        DijkstraGraph<String> Dijkstra = d.adding();
                //--- initialize
        //datawork s = new datawork();
        //DijkstraGraph<String> dijk = s.adding();
        System.out.println(Dijkstra.toString());
        // make the magic happen:
        Dijkstra.compute("Frankfurt", "Stuttgart").print();
    }

}