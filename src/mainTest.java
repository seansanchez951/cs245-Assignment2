import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.Reader;
import org.apache.commons.csv.CSVParser;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.StringReader;
import java.lang.String;
import java.util.*;
import java.util.ArrayList;

public class mainTest {

    // create breadth first search function to find connecting actors
    private static ArrayList<String> bfSearch(HMGraph graph, String a1, String a2) throws Exception {
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        HashMap<String, String> previous = new HashMap<String, String>();

        ArrayList<String> path = new ArrayList<String>();
        arrayQueue<String> queue = new arrayQueue<String>();

        String current = a1;
        queue.enqueue(current);
        visited.put(current, true);

        while (!queue.isEmpty()) {
            current = queue.dequeue();
            if (current.equalsIgnoreCase(a2)) {
                break;
            }
            else {
                for (String actor : graph.getNeighbors(current)) {
                    if (visited.get(actor) == null) {
                        queue.enqueue(actor);
                        visited.put(actor, true);
                        previous.put(actor, current);
                    }
                }
            }
        }
        if (!current.equalsIgnoreCase(a2)) {
            System.out.println("No path exists between actors.");
            return null;
        }
        String m = a2;

        while(m != null) {
            path.add(m);
            m = previous.get(m);
        }
        return path;
    }


    public static void main (String[] args) throws Exception {


        HMGraph graph = null;
        try {
            // create reader for file input
            Reader reader = new FileReader(args[0]);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            JSONParser jParser = new JSONParser();

            // create graph to store data
            graph = new HMGraph();

            boolean flag = true;


            // start looping through the CSV file
            for (CSVRecord csvRecord : csvParser) {
                if (!flag) {

                    String cast = csvRecord.get(2);
                    Object object = jParser.parse(new StringReader(cast));
                    JSONArray castArray = (JSONArray) object;

                    for (int i = 0; i < castArray.size(); i++) {

                        // parse and set up Actor 1, add actor 1 as a vertex in graph
                        Object item1 = castArray.get(i);
                        JSONObject jsonItem1 = (JSONObject) item1;
                        String a1 = (String) jsonItem1.get("name");
                        graph.addVertex(a1);


                        for (int j = 0; j < castArray.size(); j++) {

                            // parse and set up Actor 2, add actor 2 as a vertex in graph
                            Object item2 = castArray.get(j);
                            JSONObject jsonItem2 = (JSONObject) item2;
                            String a2 = (String) jsonItem2.get("name");
                            graph.addEdge(a1, a2);
                        }
                    }

                }
                flag = false;
            }
        } catch (Exception e) {
            System.out.println("File is invalid, Or there is an error in parsing");
        }

        // user input implementation
        System.out.println("------------------starting----------------");
        Scanner s1 = new Scanner(System.in);

        System.out.println("Please enter Actor 1 name (enter q to exit): ");
        String actor1 = s1.nextLine();

        if (actor1.equalsIgnoreCase("q")) {
            System.out.println("Thanks for playing six degrees of Kevin Bacon!");
            return;
        }

        System.out.println("Please enter Actor 2 name (enter q to exit): ");
        String actor2 = s1.nextLine();

        if (actor1.equalsIgnoreCase("q")) {
            System.out.println("Thanks for playing six degrees of Kevin Bacon!");
            return;
        }

        // set flags for actors being found to false
        boolean a1Found = false;
        boolean a2Found = false;


        ArrayList<String> vertices = graph.getVertices();

        for (String vertex: vertices) {
            if (actor1.equalsIgnoreCase(vertex)) {
                a1Found = true;
                actor1 = vertex;
            }
            if (actor2.equalsIgnoreCase(vertex)) {
                a2Found = true;
                actor2 = vertex;
            }
        }

        if (!a1Found || !a2Found) {
            System.out.println("Actor 1 or Actor 2 does not exist in database");
            return;
        }

        // search for path using breadth first search
        ArrayList<String> path = bfSearch(graph, actor1, actor2);
        System.out.println();
        System.out.println("Path between " + actor1 + " and " + actor2 + " is ");


        System.out.print(path.get(path.size() - 1) + " ");

        for (int i = path.size() - 2; i>= 0; i--) {
            System.out.print("--> ");
            System.out.print(path.get(i) + " ");
        }

        System.out.println();
        System.out.println("Thanks for playing six degrees of Kevin Bacon!");

    }

    }


