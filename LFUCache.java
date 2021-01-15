import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class LFUCache {

    class Node {
        int key;
        int val;
        int freq;

        Node(int key, int val, int freq) {
            this.freq = freq;
            this.key = key;
            this.val = val;
        }
    }

    PriorityQueue<Node> cacheMemory = new PriorityQueue<>(new sortingComparison());
    int cacheCapacity = 3;

    public static void main(String[] args) {
        LFUCache obj = new LFUCache();
        obj.put(1, 1001);
        obj.put(2, 1002);
        obj.put(3, 1003);
        obj.cacheMemory.forEach((e) -> {
            System.out.println("Key: " + e.key + " Value: " + e.val + " Frequeuncy: " + e.freq);
        });
        System.out.println("--------------------");
        obj.get(1);
        obj.cacheMemory.forEach((e) -> {
            System.out.println("Key: " + e.key + " Value: " + e.val + " Frequeuncy: " + e.freq);
        });
        System.out.println("--------------------");
        obj.get(1);
        obj.cacheMemory.forEach((e) -> {
            System.out.println("Key: " + e.key + " Value: " + e.val + " Frequeuncy: " + e.freq);
        });
        System.out.println("--------------------");
        obj.get(2);
        obj.cacheMemory.forEach((e) -> {
            System.out.println("Key: " + e.key + " Value: " + e.val + " Frequeuncy: " + e.freq);
        });
        System.out.println("--------------------");
        obj.put(4, 1004);
        obj.cacheMemory.forEach((e) -> {
            System.out.println("Key: " + e.key + " Value: " + e.val + " Frequeuncy: " + e.freq);
        });
        System.out.println("--------------------");
    }

    public void put(int key, int value) {
        if (cacheMemory.size() < cacheCapacity) {
            cacheMemory.add(new Node(key, value, 1));
        } else {
            PriorityQueue<Node> tempPriorityQueue = new PriorityQueue<>(new sortingComparison());
            while (cacheMemory.size() > 1) {
                tempPriorityQueue.offer((Node) cacheMemory.poll());
            }
            cacheMemory = tempPriorityQueue;
            cacheMemory.add(new Node(key, value, 1));
        }
    }

    class sortingComparison implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return Integer.compare(b.freq, a.freq);
        }
    }

    public int get(int key) {
        int returnValue = -1;
        Node tempNode = null;
        int flag = 0;
        for (Node node : cacheMemory) {
            if (node.key == key) {
                returnValue = node.val;
                flag = 1;
                tempNode = node;
            }
        }
        if (flag == 1) {
            cacheMemory.remove(tempNode);
            tempNode.freq += 1;
            cacheMemory.add(tempNode);
        }
        return returnValue;
    }
}