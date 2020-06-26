package managedata;

import java.io.*;
import java.net.URL;

/**
 * Data Structure to save one Edge between Nodes in a Graph
 *    edge = (de)Kante   d.h. Verbindung
 * Created by Samuel on 16.06.2020
 */

public class dataimport {

    public String getData() {
        StringBuilder s = new StringBuilder();
        String file ="src/main/resources/data.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String zeile = null;
            while ((zeile = reader.readLine()) != null) {
                s.append(zeile).append("=");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}