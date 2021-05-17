import java.util.ArrayList;
import java.util.HashMap;

public class HMGraph
{
    // hashmap data member
    private HashMap<String, ArrayList<String>> hashMap;

    // Constructor for hash map graph
    public HMGraph() {
        hashMap = new HashMap<String, ArrayList<String>>();
    }


    // function to add new vertex
    public void addVertex(String vertex) {
        ArrayList<String> list = new ArrayList<String>();
        hashMap.putIfAbsent(vertex,list);
    }

    // function to add edge to hashmap
    public void addEdge(String v1, String v2) {
        ArrayList<String> v1List = hashMap.get(v1);

        if (!v1List.contains(v2)) {
            v1List.add(v2);
        }
        hashMap.replace(v1,v1List);
    }

    // function to get adjacent vertices
    public ArrayList<String> getNeighbors(String vertex) {
        return hashMap.get(vertex);
    }

    // function to get the key values from neighboring vertices
    public ArrayList<String> getVertices() {
        ArrayList<String> keys = new ArrayList<String>(hashMap.keySet());
        return keys;
    }

}
