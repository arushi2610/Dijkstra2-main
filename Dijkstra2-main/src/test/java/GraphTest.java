import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


class GraphTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void dijkstra() {
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
        Map<Integer, Integer> costs = myGraph.dijkstra(0);
        Assertions.assertEquals( 0, costs.get(0));
        Assertions.assertEquals( 5, costs.get(5));
        Assertions.assertEquals( 3, costs.get(2));
        Assertions.assertEquals( 3, costs.get(1));
        Assertions.assertEquals( 2, costs.get(3));
    }

    @Test
    void printPath1() {
        Graph g = new Graph(3);
        Map<Integer, Integer> predecessors = new HashMap<Integer, Integer>() {{
            put(2, 1);
            put(1, 0);
            put(0, -1);
        }};
        g.printPath(predecessors, 2, 0);
        Assertions.assertEquals("0->1->2", outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void printPath2() {
        Graph g = new Graph(3);
        Map<Integer, Integer> predecessors = new HashMap<Integer, Integer>() {{
            put(0, -1);
            put(1, 0);
            put(2, 3);
            put(3, 0);
            put(4, 2);
            put(5, 4);
        }};
        g.printPath(predecessors, 2, 0);
        Assertions.assertEquals("0->3->2", outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void printPath3() {
        Graph g = new Graph(3);
        Map<Integer, Integer> predecessors = new HashMap<Integer, Integer>() {{
            put(0, -1);
            put(1, 0);
            put(2, 3);
            put(3, 0);
            put(4, 2);
            put(5, 4);
        }};
        g.printPath(predecessors, 5, 0);
        Assertions.assertEquals("0->3->2->4->5", outputStreamCaptor.toString()
                .trim());
    }
}