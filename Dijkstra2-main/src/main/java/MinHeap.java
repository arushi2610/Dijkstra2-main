/***
 * Created by Ellen Veomett
 * for CS 603
 */

import java.util.HashMap;
public class MinHeap {
    private Node[] heap;
    private int maxsize;
    private int size;
    private final HashMap<Integer, Integer> location;

    /***
     * Constructor.  Default size is 8 elements in the heap.
     */
    public MinHeap() {
        maxsize = 8; // max number of elements in the heap
        heap = new Node[maxsize+1]; // Heap[0] is not part of the heap
        location = new HashMap<>();
        size = 0 ;
        heap[0] = new Node(0, Integer.MIN_VALUE);
    }

    /***
     * For usage in buildHeap
     * @param arr int array of entries that will go into MinHeap
     */
    private MinHeap(Node[] arr){
        int heapSize = arr.length;
        Node[] newArr = new Node[2*heapSize+1];
        location = new HashMap<>();
        newArr[0] = new Node(0, Integer.MIN_VALUE);
        for (int i=0; i<arr.length; i++){
            newArr[i+1] = arr[i];
            location.put(arr[i].node, i+1);
        }
        maxsize = 2*heapSize;
        heap = newArr;
        size = heapSize;
    }
    /***
     * Private helper to find left child
     * @param pos index of entry in Heap
     * @return index of left child
     */
    private int leftchild(int pos) {
        return 2*pos;
    }

    /***
     * Private helper to find right child
     * @param pos index of entry in Heap
     * @return index of right child
     */
    private int rightchild(int pos) {
        return 2*pos + 1;
    }

    /***
     * Private helper to find parent
     * @param pos index of entry in Heap
     * @return index of parent
     */
    private int parent(int pos) {
        return  pos / 2;
    }

    /***
     * Private helper to determine if an entry in Heap is a leaf
     * @param pos index of entry in Heap
     * @return true if Heap[pos] is a leaf, false if not
     */
    private boolean isleaf(int pos) {
        return ((pos > size/2) && (pos <= size));
    }

    /***
     * Inserts elem into heap
     * @param elem int which is inserted into Heap
     */
    public void insert(Node elem) {
        if (size == maxsize){
            expand();
        }
        size++;
        heap[size] = elem;
        location.put(elem.node, size);
        int current = size;
        heapifyUp(current);
    }

    /***
     * Prints out elements in heap, as they appear
     * in the array
     */
    public void print() {
        int i;
        for (i=1; i<=size;i++)
            System.out.print("Node " + heap[i].node + " with distance " + heap[i].distance + "\n");
        System.out.println();
    }

    /***
     * Prints locations of elements in heap.
     * For de-bugging purposes.
     */
    private void printLocations(){
        for (int key: location.keySet()){
            System.out.println("Node " + key + " is in location " + location.get(key));
        }
    }

    /***
     *
     * @return true if heap is empty, false otherwise
     */
    public boolean isEmpty(){
        return size ==0;
    }

    /***
     * Private helper for insert
     * @param current index where new entry is inserted, from which we must heapifyUp
     */
    private void heapifyUp(int current){
        while (heap[current].isLessThan(heap[parent(current)])) {
            swap(current, parent(current));
            swapLocation(heap[current].node, heap[parent(current)].node);
            current = parent(current);
        }
    }
    /***
     * Private helper to swap entries during heapifyUp, removeMin, and heapifyDown
     * @param pos1 index of Heap entry to be swapped
     * @param pos2 other index of Heap entry to be swapped
     */
    private void swap(int pos1, int pos2) {
        Node tmp;
        tmp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = tmp;
    }

    /***
     * Private helper to swap locations during heapifyUp, removeMin, and heapifyDown
     * @param node1 node (should already be key in heap) whose position we want to swap
     * @param node2 other node (should already be key in heap) whose position we want to swap
     */
    private void swapLocation(int node1, int node2){
        int tmp;
        tmp = location.get(node1);
        location.put(node1, location.get(node2));
        location.put(node2, tmp);
    }
    /***
     * Removes min value from Heap
     * @return min value
     */
    public Node removemin() {
        swap(1,size);
        location.put(heap[1].node, 1);
        location.put(heap[size].node, null);
        size--;
        if (size != 0)
            heapifyDown(1);
        return heap[size+1];
    }

    /***
     * Private helper for removeMin
     * @param position index from which we heapifyDown
     */
    private void heapifyDown(int position) {
        int smallestchild;
        while (!isleaf(position)) {
            smallestchild = leftchild(position);
            if ((smallestchild < size) && (heap[smallestchild+1].isLessThan(heap[smallestchild])))
                smallestchild = smallestchild + 1;
            if (!heap[smallestchild].isLessThan(heap[position])) return;
            swap(position,smallestchild);
            swapLocation(heap[position].node, heap[smallestchild].node);
            position = smallestchild;
        }
    }

    /***
     * Changes priority of an element already in the heap
     * @param node is node whose distance we want to change
     * @param newPriority new priority value
     */
    public void changePriority(Integer node, int newPriority){
        int nodeLocation = location.get(node);
        heap[nodeLocation].distance = newPriority;
        if (heap[nodeLocation].isLessThan(heap[parent(nodeLocation)])){
            heapifyUp(nodeLocation);
        }
        else{
            heapifyDown(nodeLocation);
        }
    }
    /***
     * builds heap from a full int array
     * @param arr full int array which will go into heap
     * @return heap with entries from arr.  Size is already doubled.
     */
    public static MinHeap buildHeap(Node[] arr){
        MinHeap myHeap = new MinHeap(arr);
        for (int i= myHeap.size/2; i>0; i--){
            myHeap.heapifyDown(i);
        }
        return myHeap;
    }
    /***
     * Private helper for insert
     * Expands Heap array if needed
     */
    private void expand()
    {
        Node[] newData = new Node[2*size+1];
        maxsize = 2*size;
        for (int i=0; i <= size; i++){
            newData[i] = heap[i];
        }
        heap = newData;
    }
}
