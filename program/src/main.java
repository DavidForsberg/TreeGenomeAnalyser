public class main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.numberOfComponents();
        graph.nodeDegreeDistribution();
        if (args.length > 0 && args[0].equals("-save_output")) graph.saveOutput();
    }
}