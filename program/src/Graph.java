import java.util.*;

public class Graph {
    private Storage selectedStorage = null;
    private Storage adj_matrix = null;
    private HashMap<Integer, ArrayList<Integer>> adj_list = null;
    private static HashMap<Integer, Integer> visited;

    Graph() {
        visited = new HashMap<Integer, Integer>();
    }

    public void initGraph(Storage storageMethod) {
        if (storageMethod == Storage.ADJ_LIST) initAdjList();
        else System.out.println("Invalid storage method, try selecting another.");
    }


    private Boolean isOverlapSufficient(int aLength, int aStart, int aEnd, int bLength, int bStart, int bEnd) {
        // Check if contig A and B are not "fully" overlapping the other contig,
        // in this case the edge does not count!
        if (aStart == 0 && aEnd == aLength) {
            return false;
        } else if (bStart == 0 && bEnd == bLength) {
            return false;
        } else {
            return true;
        }
    }

    /*
        Storage methods
     */

    private void initAdjList() {
        HashMap<Integer, ArrayList<Integer>> adj_list = new HashMap<Integer, ArrayList<Integer>>();

        Scanner sc = loadData();

        int lineCounter = 0;

        while (sc.hasNext()) {
            String c = sc.nextLine();

            // Parse input row/line from scanner
            String[] splitRow = c.split("\\s+");
            int contigA = splitRow[0].hashCode();
            int contigB = splitRow[1].hashCode();
            int aStart = Integer.parseInt(splitRow[5]);
            int aEnd = Integer.parseInt(splitRow[6]);
            int aLength = Integer.parseInt(splitRow[7]);
            int bStart = Integer.parseInt(splitRow[9]);
            int bEnd = Integer.parseInt(splitRow[10]);
            int bLength = Integer.parseInt(splitRow[11]);

            if (isOverlapSufficient(aLength, aStart, aEnd, bLength, bStart, bEnd)) {
                if (!adj_list.containsKey(contigA)) {
                    adj_list.put(contigA, new ArrayList<Integer>());
                    adj_list.get(contigA).add(contigB);
                    this.visited.put(contigA, 0);
                } else if (!adj_list.get(contigA).contains(contigB)) {
                    adj_list.get(contigA).add(contigB);
                }
                if (!adj_list.containsKey(contigB)) {
                    adj_list.put(contigB, new ArrayList<Integer>());
                    adj_list.get(contigB).add(contigA);
                    this.visited.put(contigB, 0);
                } else if (!adj_list.get(contigB).contains(contigA)) {
                    adj_list.get(contigB).add(contigA);
                }
            }

            Integer[] timerMilestonesVals = new Integer[] { 10000000, 20000000, 30000000, 40000000, 50000000, 60000000 };
            List<Integer> timerMilestones = new ArrayList<>(Arrays.asList(timerMilestonesVals));

            if (timerMilestones.contains(lineCounter)) System.out.println(lineCounter);
            lineCounter++;
        }

        this.adj_list = adj_list;
        System.out.println("Complete! " + adj_list.entrySet().stream().count());
    }

//
//    /*
//        Traverse graph
//     */
    private void findDFS(int vertex)
    {
        // Mark as visited
        visited.put(vertex,1);

        // Print the vertex
//         System.out.println("vertex " + vertex +
//          " visited");
//         System.out.println(this.visited);
        for(Integer child : adj_list.get(vertex))
        {
            if(visited.get(child) == 0){
                findDFS(child);
            }
        }
    }

    public void numberOfComponents() {
        int ccCount = 0;

        for(Integer vertex : visited.keySet())
        {
            if(visited.get(vertex) == 0)
            {
                findDFS(vertex);

                ccCount++;
            }
        }

        // Print number of components
        System.out.println("Number of cc component: " + ccCount);
    }


    //    Set<Integer> depthFirstTraversal(Integer root) {
////        Set<Integer> visited = new LinkedHashSet<Integer>();
////        Stack<Integer> stack = new Stack<Integer>();
////        stack.push(root);
////        while (!stack.isEmpty()) {
////            Integer vertex = stack.pop();
////            if (!visited.contains(vertex)) {
////                visited.add(vertex);
////                if (selectedStorage == Storage.ADJ_LIST) {
////                    for (Integer v : getAdjVertices(vertex)) {
////                        stack.push(vertex);
////                    }
////                }
////            }
////        }
////
////        return visited;
//    }

    /*
        Utils - filereading, etc.
     */
    public Scanner loadData() {
        Scanner sc = new Scanner(System.in);
        return sc;
//        ArrayList<Integer> nodes = new ArrayList<Integer>();
//        ArrayList<String> contigBs = new ArrayList<String>();
//
//        while (sc.hasNext()) {
//            String c = sc.nextLine();
//            String[] splitRow = c.split("\\s+");
////            System.out.println("Contig A: " + splitRow[0] + ", Contig B: " + splitRow[1] + "... Contig A as Integer ID: " + splitRow[1].hashCode());
////            System.out.println(splitRow[1].hashCode() + ", " + splitRow[1]);
//            if (splitRow[0].hashCode() == -1192129190) System.out.println("Found multiple: " + splitRow[0] + "with: " + splitRow[1]);
//            nodes.add(splitRow[0].hashCode());
//            contigBs.add(splitRow[1]);
//        }
//
//        for (Integer node : nodes) {
//            int occurrences = Collections.frequency(nodes, node);
//            if (occurrences > 1) System.out.println(node);
//        }
//
//        for (String contigB : contigBs) {
//            System.out.println("Contig A found x times as Contig B: " + Collections.frequency(nodes, contigB.hashCode()));
//        }
    }

    private void formatData() {

    }

    /*
        Utils - internal classes, enums, etc.
     */
    enum Storage {
        ADJ_MATRIX,
        ADJ_LIST
    }

    private class Vertex {
        int node;
        ArrayList<Vertex> neighbors;
        boolean visited;

        Vertex(int node, ArrayList<Vertex> neighbors) {
            this.node = node;
            this.neighbors = neighbors;
            visited = false;
        }

        void visit() {
            visited = true;
        }

        void unvisit() {
            visited = false;
        }

        int getNode() {
            return node;
        }

        ArrayList<Vertex> getNeighbors() {
            return neighbors;
        }

        boolean isVisited() {
            return visited;
        }
    }
}
