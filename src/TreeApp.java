
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;


/*
* Authors: Christian Marquardt
* Date: 1/26/2018
* Overview: Using NetBeans 8.2 reading in a input file into the console
* to create a binary tree where we insert, find, delete, calculate the 
* min and max and finally display the final product which is the tree
*/
//////////////////////////////////////////////////////////////
class Node {

    public int iData;           // data item (key)
    public double dData;        // data item
    public Node leftChild;      // this Node's left child
    public Node rightChild;     // this Node's right child

    public void displayNode() { // display ourself
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
    }
} // end Class Node
////////////////////////////////////////////////////////////////

class Tree {

    private Node root;                 // first Node of Tree

    public Tree() {                    // constructor
        root = null;                   // no nodes in tree yet
    }

    public Node find(int key) {      // find node with given key
        Node current = root;         // (assumes non-empty tree)
        while (current.iData != key) {          // while no match
            if (key < current.iData) {          // go left?
                current = current.leftChild;
            } else {                              // or go right?
                current = current.rightChild;
            }
            if (current == null) // if no child
            {                                   // didn't find it
                return null;
            }
        }
        return current;                         // found it
    }  //end find()
    
    public void insertRandom(int[] insert)
    {
        final long startTime = System.currentTimeMillis(); //Start Time
        for(int i = 0; i < insert.length; i++) //Goes through length of insertion in Driver
        {
            insert(insert[i], insert[i] + .09); //Adding each individual Node
        }
         final long endTime = System.currentTimeMillis(); //End Time for Binary Tree at Random
        System.out.println("Running Time for Binary Tree at Random: " + (endTime-startTime)); //Printing results
    }
    public void insertInOrder(int[] insert) //Binary Tree In Order calling how many Nodes we are inserting
    {
       final long startTime = System.currentTimeMillis(); //Start Time 
        for(int i = 0; i < insert.length; i++) // Goes through length of insertion in Driver
        {
            insert(insert[i], insert[i] + .09); //Adding each Indidvual Node
        }
        final long endTime = System.currentTimeMillis(); //EndTime
        System.out.println("Running Time for Binary Tree in Order: " + (endTime-startTime)); //Print Statement
    }
    
    public void insert(int id, double dd) {
        Node newNode = new Node();    // make new Node
        newNode.iData = id;           // insert data
        newNode.dData = dd;
        newNode.leftChild = null;
        newNode.rightChild = null;
        if (root == null) {            // no node in root
            root = newNode;
        } else {                        // root occupied
            Node current = root;      // start at root  
            Node parent;
            while (true) {            // exits internally			
                parent = current;
                if (id < current.iData) {              // go left?
                    current = current.leftChild;
                    if (current == null) {             // if the end of the line        
                        parent.leftChild = newNode;   // insert on left
                        return;
                    }
                } //end if go left
                else {                                // or go right?
                    current = current.rightChild;
                    if (current == null) // if the end of the line
                    {                                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    } // end insert()

    public boolean delete(int key) {             // delete node with given key
        Node current = root;		             // (assumes non-empty list)
        Node parent = root;
        boolean isLeftChild = true;

        while (current.iData != key) {           // search for Node
            parent = current;
            if (key < current.iData) {           // go left?
                isLeftChild = true;
                current = current.leftChild;
            } else {                               // or go right?
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) {                // end of the line,                             
                return false;                    // didn't find it
            }
        }
        //found the node to delete

        //if no children, simply delete it
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {              // if root,
                root = null;                    // tree is empty
            } else if (isLeftChild) {
                parent.leftChild = null;        // disconnect
            } // from parent
            else {
                parent.rightChild = null;
            }
        } //if no right child, replace with left subtree
        else if (current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        } //if no left child, replace with right subtree
        else if (current.leftChild == null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        } else { // two children, so replace with inorder successor
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }

            //connect successor to current's left child
            successor.leftChild = current.leftChild;
        } // end else two children
        // (successor cannot have a left child)
        return true;              // success
    }// end delete()

    //returns node with next-highest value after delNode
    //goes right child, then right child's left descendants
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;        // go to the right child
        while (current != null) {                 // until no more
            successorParent = successor;          // left children
            successor = current;
            current = current.leftChild;
        }

        if (successor != delNode.rightChild) {    // if successor not right child,
            //make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal: ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
            default:
                System.out.print("Invalid traversal type\n");
                break;
        }
        System.out.println();
    }

    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }

    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }

    public void displayTree() {
        Stack<Node> globalStack = new Stack<Node>();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                ".................................................................");
        while (isRowEmpty == false) {
            Stack<Node> localStack = new Stack<Node>();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }

            while (globalStack.isEmpty() == false) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null
                            || temp.rightChild != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }

                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(' ');
                }
            } // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            } // end while isRowEmpty is false
            System.out.println(
                    ".................................................................");
        }
    }// end displayTree()

    public Node findMax() {
        Node current = root; //Starts at the beginning
        //If there isnt a Node to be found
        if (current == null) {
            System.out.print("There are no nodes in the tree");
        }
        //Right child will alaways be the smallest so it will go all the way to the bottom of the tree
        while (current.rightChild != null) {
            current = current.rightChild;
        }
        return current; //returns the Node
    }

    public Node findMin() {
        Node current = root; //starts at the beginning
        //If there isnt a Node to be found
        if (current == null) {
            System.out.print("There are no nodes in the tree");
        }
        //Left chil will alaways be the smallest so it will go all the way to the bottom of the tree
        while (current.leftChild != null) {
            current = current.leftChild;
        }
        return current; //returns the Node
    }
} // end class Tree

////////////////////////////////////////////////////////////////
class TreeApp {

    public static void main(String[] args) throws IOException {
        int number = 65536; //How many Nodes we are putting In
        int[] treeRandomOrder = new int[number]; //Array for random Order
        int[] treeInOrder = new int[number]; //Array for In Order
        Random randomNumbers = new Random();//Random Number generator
        for(int i = 0; i < treeRandomOrder.length; i++){ //Intializes the random numbers into array
		   //random numbers from 1 to 10:
		   treeRandomOrder[i] = randomNumbers.nextInt(number);
                   
		}
         for(int i = 0; i < treeInOrder.length; i++){ //Intializes the numbers in Order into array
		   //random numbers from 1 to 10:
		   treeInOrder[i] = i; //adding numbers while incremementing
                   
		}
        Tree theTree = new Tree(); // new instance of Binary Tree in Random Order
        theTree.insertRandom(treeRandomOrder);//Insertion of Nodes into the tree
        Tree inOrderTree = new Tree(); // new instance of Binary Tree tree inOrder
        inOrderTree.insertInOrder(treeInOrder); //The insertion of Nodes into the tree
        
        TreeMap<Integer, Double> treeMap = new TreeMap<>(); //Our inOrder Tree
        final long startTime = System.currentTimeMillis(); //startTime
        for(int i = 0; i < treeInOrder.length; i++) //The insertion technique for TreeMap  InOrder
        {
            treeMap.put(treeInOrder[i], treeInOrder[i] + .09); //inserting through array
        }
        final long endTime = System.currentTimeMillis(); //EndTime
        System.out.println("Running Time for Tree Map in Order: " + (endTime-startTime)); //Print Statement for InOrder Tree
        
        TreeMap<Integer, Double> treeRandomMap = new TreeMap<>(); //Random TreeMap
        
        final long startRandomTime = System.currentTimeMillis(); //StartTime for Random TreeMap
        for(int i = 0; i < treeRandomOrder.length; i++) //The insertion technique for TreeMap Random
        {
            treeRandomMap.put(treeRandomOrder[i], treeRandomOrder[i] + .09); //Inserting through array
        }
         final long endRandomTime = System.currentTimeMillis(); //EndTime
        System.out.println("Running Time for Tree Map at Random: " + (endRandomTime-startRandomTime)); //Print Statement for Random Tree Map
        
                
    }// end main()

    private static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    private static int getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }

    private static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }
}// end TreeApp class
////////////////////////////////////////////////////////////////
