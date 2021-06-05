import java.util.*;

public class Graph {
    private HashMap<Integer, ArrayList<Integer>> adj_list = null;
    private static HashMap<Integer, Integer> visited;

    Graph() {
        visited = new HashMap<Integer, Integer>();
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
    // Recursive
    private void findDFS(int vertex)
    {
        // Mark as visited
        visited.put(vertex,1);

        for(Integer child : adj_list.get(vertex))
        {
            if(visited.get(child) == 0){
                findDFS(child);
            }
        }
    }

    // Recursive
    public void numberOfComponents() {
        int ccCount = 0;

        for(Integer vertex : visited.keySet())
        {
            if(visited.get(vertex) == 0)
            {
//                findDFS(vertex);
                DFSUtil(vertex);
                ccCount++;
            }
        }

        // Print number of components
        System.out.println("Number of cc component: " + ccCount);
    }

    // Iterative
    // prints all not yet visited vertices reachable from s
    void DFSUtil(int currVertex)
    {
        // Create a stack for DFS
        Stack<Integer> stack = new Stack<>();

        // Push the current source node
        stack.push(currVertex);

        while(stack.empty() == false)
        {
            // Pop a vertex from stack and print it
            currVertex = stack.peek();
            stack.pop();

            // Stack may contain same vertex twice. So
            // we need to print the popped item only
            // if it is not visited.
            if(visited.get(currVertex) == 0)
            {
//                System.out.print(s + " ");
                visited.put(currVertex, 1);
            }

            // Get all adjacent vertices of the popped vertex s
            // If a adjacent has not been visited, then push it
            // to the stack.
            Iterator<Integer> itr = adj_list.get(currVertex).iterator();

            while (itr.hasNext())
            {
                int v = itr.next();
                if(visited.get(v) == 0)
                    stack.push(v);
            }

        }
    }

    /*
        Node degree distribution
     */
    public void nodeDegreeDistribution() {
        // Node degree (number of neighbors), Number of vertexes
        HashMap<Integer, Integer> distributionMap = new HashMap<Integer, Integer>();
        for (int vertex : adj_list.keySet()) {
            int degree = adj_list.get(vertex).size();
            if (!distributionMap.containsKey(degree)) distributionMap.put(degree, 1);
            else {
                int numberOfNodes = distributionMap.containsKey(degree) ? distributionMap.get(degree) : 0;
                distributionMap.put(degree, numberOfNodes + 1);
            }
        }

        // Print the results!
        System.out.println("-= Node Degree Distribution =-");
        for (int nodeDegree : distributionMap.keySet()) {
            System.out.println("Degree: " + nodeDegree + ", with: " + distributionMap.get(nodeDegree) + " number of nodes!");
        }
    }

    /*
        Utils - filereading, etc.
     */
    public Scanner loadData() {
        Scanner sc = new Scanner(System.in);
        return sc;
    }

    private void formatData() {

    }

    /*
        Utils - internal classes, enums, etc.
     */
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
