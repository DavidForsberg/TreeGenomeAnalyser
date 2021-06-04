public class main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.initGraph(Graph.Storage.ADJ_LIST);
        graph.numberOfComponents();
    }
}