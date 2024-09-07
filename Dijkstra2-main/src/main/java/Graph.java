import java.util.*;
import java.util.Map.Entry;
/***
 * This Graph class creates *undirected* graphs.
 */
public class Graph {
    public static void main(String[] args) {
        /***
         * TODO: Nothing in particular here!
         * But you can edit the main method however you with
         * to help with testing.
         */
        Graph myGraph = new Graph(6);
        myGraph.addEdge(0, 1, 4);
        myGraph.addEdge(0, 2, 5);
        myGraph.addEdge(0, 3, 2);
        myGraph.addEdge(0, 4, 4);
        myGraph.addEdge(1, 3, 1);
        myGraph.addEdge(1, 5, 5);
        myGraph.addEdge(2, 3, 1);
        myGraph.addEdge(2, 4, 1);
        myGraph.addEdge(3, 5, 8);
        myGraph.addEdge(4, 5, 1);
        myGraph.dijkstra(0);
    }
    private Map<Integer, Map<Integer, Integer>> adjacencyList;
    private int size;
    private int weight;
    private int u;
    private int v;

    /***
     * Constructor.  This graph class contains nodes
     * labeled 0, 1, 2, . . . n-1
     * @param n number of nodes
     */
    public Graph(int n) {
        size = n;
        this.adjacencyList = new HashMap<>();
        for (int i=0; i<n; i++){
            this.adjacencyList.put(i, new HashMap<>());
        }
    }

    /***
     * Adds an edge from node1 to node2 of weight weight.
     * Remember edges are not directed, so this is also an
     * edge from node2 to node1
     * @param node1 node in edge
     * @param node2 other node in edge
     * @param weight weight of edge
     */
    public void addEdge(Integer node1, Integer node2, int weight) {
        adjacencyList.get(node1).put(node2, weight);
        adjacencyList.get(node2).put(node1, weight);
    }

    /***
     * Finds shortest path from node1 to every other node in graph
     * Uses Dijkstra's algorithm
     * This will also print the total cost of shortest path
     * It also calls printPath, which prints a shortest path from
     * node1 to each other node
     * @param node1 source node
     */
    public Map<Integer, Integer> dijkstra(Integer node1){
        Map<Integer, Integer> curCosts = new HashMap<>();
        Map<Integer, Integer> dijkstraPath = new HashMap<>();
        MinHeap minHeap = new MinHeap();

        for(int i = 0; i < size; i++){
            curCosts.put(i, Integer.MAX_VALUE);
            dijkstraPath.put(i,null);
        }

        curCosts.put(node1, 0);
        minHeap.insert(new Node(node1, 0));

        /***
         * TODO: Implement Dijkstra's algorithm
         * curCosts.get(i) should eventually contain the min
         * cost path from node1 to node i
         */

        while(!minHeap.isEmpty()) {
            Node currentNode = minHeap.removemin();
            int u = currentNode.node;

            for (Map.Entry<Integer, Integer> neighbor : adjacencyList.get(u).entrySet()) {
                int v = neighbor.getKey();
                int weight = neighbor.getValue();
            }

            if(curCosts.get(u) + weight < curCosts.get(v)){
                curCosts.put(v, curCosts.get(u) + weight);
                dijkstraPath.put(v,u);
                minHeap.changePriority(v,curCosts.get(v));
        }



        }

        for (Integer curNode : curCosts.keySet()){
            System.out.println("Min cost from " + node1 + " to " + curNode + " is " + curCosts.get(curNode));
            printPath(dijkstraPath, curNode, node1);
        }
        return curCosts;
    }

    /***
     * Prints shortest path from source node to curNode
     * @param dijkstraPath The "predecessor" map created in Dijkstra's algorithm
     * @param curNode node we traverse to from source
     * @param node1 source node
     */
    public void printPath(Map<Integer, Integer> dijkstraPath, Integer curNode, Integer node1) {
        /***
         * TODO: implement this method
         * It should print "0->1->2"
         * if node1 is the source, 2 is curNode,
         * and going from node 0 to 1 to 2 is the shortest path
         */

        if(curNode == null){
            System.out.println("No path found." + node1);
            return;
        }

        if(curNode.equals(node1)) {
            System.out.print(node1);
            return;
        }

        Integer previous_node = dijkstraPath.get(curNode);
        if (previous_node == null){
            System.out.println("No path found." + curNode);
            return;
        }

        printPath(dijkstraPath, dijkstraPath.get(curNode), node1);
        System.out.print("->" + curNode);

    }

    /***
     * Print method for help in debugging
     */
    public void print(){
        for (int node : adjacencyList.keySet()){
            System.out.println("Node " + node +" has neighbors ");
            for (Integer neighbor : adjacencyList.get(node).keySet()){
                System.out.print(neighbor + " has edge weight " + adjacencyList.get(node).get(neighbor) + " ");
            }
            System.out.println("\n");
        }
    }
}

