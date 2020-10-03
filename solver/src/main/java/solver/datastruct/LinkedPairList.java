package solver.datastruct;

import solver.domain.InfoSet;

/** 
 * Linked list implementation of key-value pairs with Strings as keys and InfoSets as values.
 */
public class LinkedPairList {
    private Node head;
    private Node tail;
    private int length;
    
    /**
     * Constructor for the linked list.
     */
    public LinkedPairList() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }
    
    /**
     * Creates a new node and adds it to the list.
     * @param key String key.
     * @param value InfoSet to be stored with the key.
     */
    public void add(String key, InfoSet value) {
        Node node = new Node(key, value);
        
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        
        length++;
    }

    public int getLength() {
        return length;
    }

    public Node getTail() {
        return tail;
    }
    
    

    public Node getHead() {
        return head;
    }
    
    public class Node {
        private String key;
        private InfoSet value;
        private Node next;

        /**
         * Constructor for the nodes of the linked list.
         * @param key String key.
         * @param value InfoSet to be stored with the key.
         */
        public Node(String key, InfoSet value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public String getKey() {
            return key;
        }

        public InfoSet getValue() {
            return value;
        }

        public void setValue(InfoSet value) {
            this.value = value;
        }
        
        

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
        
        
    }
}
