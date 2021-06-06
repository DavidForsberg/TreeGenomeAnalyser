import java.io.FileWriter;
import java.util.*;

public class Graph {
    private HashMap<Integer, ArrayList<Integer>> adj_list;
    private static HashMap<Integer, Integer> visited;
    private HashMap<Integer, Integer> componentSizeDistribution;
    private HashMap<Integer, Integer> nodeDegreeDistribution;

    Graph() {
        adj_list = new HashMap<Integer, ArrayList<Integer>>();
        visited = new HashMap<Integer, Integer>();
        componentSizeDistribution = new HashMap<Integer, Integer>();
        nodeDegreeDistribution = new HashMap<Integer, Integer>();
        initAdjList();
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
        Initialize the graph with a adjacency list representation.
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

            // Add the new vertices and edges
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

            // Helpful process timer
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
    private void DFSRecursive(int vertex)
    {
        // Mark vertex as visited
        visited.put(vertex,1);

        for(Integer child : adj_list.get(vertex))
        {
            if(visited.get(child) == 0){
                DFSRecursive(child);
            }
        }
    }

    // Iterative
    int DFSIterative(int currVertex)
    {
        int componentSize = 0;
        Stack<Integer> stack = new Stack<>();

        // Push the current vertex onto the stack
        stack.push(currVertex);
        componentSize += 1;

        while(stack.empty() == false)
        {
            currVertex = stack.peek();
            stack.pop();

            if(visited.get(currVertex) == 0)
            {
                visited.put(currVertex, 1);
            }

            Iterator<Integer> itr = adj_list.get(currVertex).iterator();

            while (itr.hasNext())
            {
                int v = itr.next();
                if(visited.get(v) == 0) {
                    stack.push(v);
                    componentSize += 1;
                }
            }
        }

        return componentSize;
    }

    // Recursive
    public void numberOfComponents() {
        int ccCount = 0;

        // Component size, number of components
        HashMap<Integer, Integer> componentSizeDistribution = new HashMap<Integer, Integer>();
        int ccSize = 0;

        for(Integer vertex : visited.keySet())
        {
            if(visited.get(vertex) == 0)
            {
//                findDFS(vertex);
                ccSize = DFSIterative(vertex);
                ccCount++;
                // Add component size to distribution
                if (!componentSizeDistribution.containsKey(ccSize)) componentSizeDistribution.put(ccSize, 1);
                else {
                    int numberOfComponents = componentSizeDistribution.containsKey(ccSize) ? componentSizeDistribution.get(ccSize) : 0;
                    componentSizeDistribution.put(ccSize, numberOfComponents + 1);
                }
            }
        }

        // Print number of components
        System.out.println("Number of cc component: " + ccCount);
        // Print component size distribution
        this.componentSizeDistribution = componentSizeDistribution;
        System.out.println("-= Component Size Distribution =-");
        for (int componentSize : componentSizeDistribution.keySet()) {
            System.out.println("Size: " + componentSize + ", with: " + componentSizeDistribution.get(componentSize) + " number of components!");
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
        this.nodeDegreeDistribution = distributionMap;
        System.out.println("-= Node Degree Distribution =-");
        for (int nodeDegree : distributionMap.keySet()) {
            System.out.println("Degree: " + nodeDegree + ", with: " + distributionMap.get(nodeDegree) + " number of nodes!");
        }
    }

    /*
        Load the data, returns the scanner.
     */
    public Scanner loadData() {
        Scanner sc = new Scanner(System.in);
        return sc;
    }


    /*
        Save the distributions in a csv file located in the home data_visualization folder.
        This method will only run when specifying the argument -save_output when running the compiled JAR file.
     */
    public void saveOutput() {
        // Node degree distribution
        try {
            FileWriter csvWriter = new FileWriter("node_degree_distribution.csv");

            csvWriter.append("degree");
            csvWriter.append(",");
            csvWriter.append("amount");
            csvWriter.append("\n");

            for (int nodeDegree : this.nodeDegreeDistribution.keySet()) {
                csvWriter.append(Integer.toString(nodeDegree));
                csvWriter.append(",");
                csvWriter.append(Integer.toString(this.nodeDegreeDistribution.get(nodeDegree)));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            System.out.println("Error saving node degree distribution to CSV!");
        }
        System.out.println("Node degree distribution saved as CSV!");

        // Component size distribution
        try {
            FileWriter csvWriter = new FileWriter("component_size_distribution.csv");

            csvWriter.append("size");
            csvWriter.append(",");
            csvWriter.append("amount");
            csvWriter.append("\n");

            for (int componentSize : this.componentSizeDistribution.keySet()) {
                csvWriter.append(Integer.toString(componentSize));
                csvWriter.append(",");
                csvWriter.append(Integer.toString(this.componentSizeDistribution.get(componentSize)));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
            System.out.println("Component size distribution saved as CSV!");
        } catch (Exception e) {
            System.out.println("Error saving component size distribution to CSV!");
        }
    }
}
