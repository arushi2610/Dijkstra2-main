public class Node implements Comparable<Node>{
    Integer node;
    Integer distance;
    Node(Integer n, Integer d){
        node = n;
        distance = d;
    }
    public boolean isLessThan(Node otherNode){
        return this.distance < otherNode.distance;
    }
    @Override
    public int compareTo(Node n1)
    {
        return this.distance - n1.distance;
    }
}