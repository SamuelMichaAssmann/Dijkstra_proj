package graphtheory;


import java.util.ArrayList;
import java.util.Collections;



/**
 * Created by Samuel on 20.05.20
 */
public class Node<T> {
    private final T point;
    private ArrayList<Edge<T>> peers = new ArrayList<>();


    public Node(T point) {
        this.point = point;
    }
    public void addPeers(Edge<T> peer) {
        this.peers.add(peer);
    }

    public T getPoint() {
        return point;
    }

    public ArrayList< Edge<T> > getPeers() {
        return peers;
    }


    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append((String) point).append("->");
        for (Edge<T> t:peers) {
            s.append(t.toString()).append(",");
        }
        return s.toString();
    }
}
