import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;


public class GoldenTree<E> {

    

    public myNode<myPersonalData> boss;
    String outFileName;
    
    GoldenTree() {
        this.boss = null;
        
    }
    GoldenTree(String outName) {
        this.boss = null;
        this.outFileName = outName;
    }
    public void mywrite(String s){
        try{
            File outFile = new File(this.outFileName);
            FileWriter fw = new FileWriter(outFile, true);
            fw.write(s);
            fw.close();
        }
        catch(Exception e){
            System.out.println("an error ocurred while creating the file\n DO NOT FORGET TO DELETE THIS CODE LINE");
        }
    }
    

    public boolean isEmpty() {
        return this.boss == null;
    }

    public void makeEmpty() {
        this.boss = null;
    }

    // below we are writing some methods that will be useful when we make rotations
    public float findMin() {
        if (isEmpty()) {
            throw new RuntimeException("the avl tree is empty");
        }
        return findMin(this.boss).data.getGValue();
    }

    private myNode<myPersonalData> findMin(myNode<myPersonalData> head) {
        while (head.leftChild != null) {
            head = head.leftChild;
        }
        return head;
    }

    // we used two blocks for findMin since we want to handle exceptions in the
    // public method

    public float findMax() {
        if (isEmpty()) {
            throw new RuntimeException("the avl tree is empty");
        }

        return findMax(this.boss).data.gValue;
    }

    private myNode<myPersonalData> findMax(myNode<myPersonalData> head) {
        while (head.rightChild != null) {
            head = head.rightChild;
        }
        return head;
    }
    // in this way we can find any right most or left most myNode in a subtree

    public int height() {
        return this.height(this.boss);
    }

    private int height(myNode<myPersonalData> head) {
        if (head == null) {
            return -1;
        }
        return Math.max(height(head.leftChild), height(head.rightChild)) + 1;
    }
    // for user we enable them to find the whole tree's height, but for the private
    // method we need to find the height of a subtree

    public boolean contains(myPersonalData data) {
        return this.contains(data, this.boss);
    }

    private boolean contains(myPersonalData data, myNode<myPersonalData> head) {
        if (head == null) {
            return false;
        }

        if (data.gValue < head.data.gValue) {
            return contains(data, head.leftChild);
        } else if (data.gValue > head.data.gValue) {
            return contains(data, head.rightChild);
        } else {
            return true;
        }
    }

    // after this we need to implement the most important part of the avl tree,
    // insertions and deletions and rotations
    // after that we can tackle with traversals
    // first we can think it as a normal bstree and make the rotations
    public void insert(myPersonalData data) {
        this.boss = this.insert(data, this.boss);
    }

    private myNode<myPersonalData> insert(myPersonalData data, myNode<myPersonalData> head) {
        rotationCount++;
        if (head == null) {
            return new myNode<myPersonalData>(data);
        }
        if (data.gValue < head.data.gValue) {
            mywrite(head.data.name + " welcomed " + data.name + "\n");
            
            
            head.leftChild = insert(data, head.leftChild);
            
        } else if (data.gValue > head.data.gValue) {
            mywrite(head.data.name + " welcomed " + data.name + "\n");
            head.rightChild = insert(data, head.rightChild);
            
        } else if (data.gValue == head.data.gValue) {
            throw new RuntimeException("the data is already in the tree");
            // return head;
        }
        updateHeight(head);
        return applyRotation(head);
        // if program returns here, it means that the data is already in the tree
    }

    // inserting ended
    public void remove(myPersonalData data) {
        this.boss = this.remove(data, this.boss);
    }
    private myNode<myPersonalData> remove1(myPersonalData data, myNode<myPersonalData> head){
        if (head == null) {
            return head;
        }
        if (data.gValue < head.data.gValue) {
            head.leftChild = remove1(data, head.leftChild);
        } else if (data.gValue > head.data.gValue) {
            head.rightChild = remove1(data, head.rightChild);
        }
        else if(head.leftChild == null && head.rightChild == null) {
 
            head = null;
        }
        
        updateHeight(head);
        return applyRotation(head);

    }
    private myNode<myPersonalData> remove(myPersonalData data, myNode<myPersonalData> head) {
        rotationCount++;
        if (head == null) {
            return head;
        }
        if (data.gValue < head.data.gValue) {
            head.leftChild = remove(data, head.leftChild);
        } else if (data.gValue > head.data.gValue) {
            head.rightChild = remove(data, head.rightChild);
        } else if (head.leftChild != null && head.rightChild != null) {
            String curLeft = head.data.name;
            head.data = findMin(head.rightChild).data;
            mywrite(curLeft + " left the family, replaced by " + head.data.name + "\n");
            myNode.nodeCount--;
            head.rightChild = remove1(head.data, head.rightChild);
            
        }
        else if(head.leftChild == null && head.rightChild == null) {
            mywrite(head.data.name + " left the family, replaced by nobody\n");
            myNode.nodeCount--;
            head = null;
        }
        else if (head.leftChild == null) {
            mywrite(head.data.name + " left the family, replaced by " + head.rightChild.data.name + "\n");
            myNode.nodeCount--;
            head = head.rightChild;
        } else if (head.rightChild == null) {
            mywrite(head.data.name + " left the family, replaced by " + head.leftChild.data.name + "\n");
            myNode.nodeCount--;
            
            head = head.leftChild;
        }
        
        updateHeight(head);
        return applyRotation(head);
    }

    // removing ended
    private int myHeight(myNode<myPersonalData> head) {
        if (head == null) {
            return 0;
        }
        return head.height;
    }

    // we have an additional myHeight method so that we can tackle with null leafs
    // easily
    private void updateHeight(myNode<myPersonalData> head) {
        if (head == null) {
            return;// if there is no null, do not update
        }
        head.height = Math.max(myHeight(head.leftChild), myHeight(head.rightChild)) + 1;
    }

    // now we need to implement the rotations
    //////////////// ROTATIONS/////////////////////
    private int myBalance(myNode<myPersonalData> head) {
        if (head == null) {
            return 0;
        }
        return myHeight(head.leftChild) - myHeight(head.rightChild);
    }
    public void giveMax(){
        //System.out.println(this.height());
    }
    private myNode<myPersonalData> applyRotation(myNode<myPersonalData> head) {
        // we will use the height difference to determine which rotation to use
        // and for that we will use an additional methhod called myBalance so that we
        // can tackle with null pointer exceptions
        int balance = myBalance(head);
        
        // LEFT HEAVY PART
        if (balance > 1) {
            // LEFT-RIGHT CASE
            if (myBalance(head.leftChild) < 0) {
                head.leftChild = rotateLeft(head.leftChild);
            }
            return rotateRight(head);
        }
        // RIGHT HEAVY PART
        else if (balance < -1) {
            // RIGHT-LEFT CASE
            if (myBalance(head.rightChild) > 0) {
                head.rightChild = rotateRight(head.rightChild);
            }
            return rotateLeft(head);
        }
        return head;

    }

    private myNode<myPersonalData> rotateLeft(myNode<myPersonalData> head) {
        myNode<myPersonalData> newHead = head.rightChild;
        head.rightChild = newHead.leftChild;
        newHead.leftChild = head;
        updateHeight(head);
        updateHeight(newHead);
        return newHead;
    }

    private myNode<myPersonalData> rotateRight(myNode<myPersonalData> head) {
        myNode<myPersonalData> newHead = head.leftChild;
        head.leftChild = newHead.rightChild;
        newHead.rightChild = head;
        updateHeight(head);
        updateHeight(newHead);
        return newHead;
    }

    //////////////// ROTATIONS/////////////////////
    // i want to implement traversals first we will implement inorder traversal
    //////////////// since we will only need that
    public ArrayList<myPersonalData> inorder() {
        ArrayList<myPersonalData> toReturn = new ArrayList<>();
        return this.inorder(this.boss, toReturn);
    }

    public ArrayList<myPersonalData> inorder(myNode<myPersonalData> head, ArrayList<myPersonalData> toReturn) {
        if (head == null) {
            return toReturn;
        }
        inorder(head.leftChild, toReturn);
        toReturn.add(head.data);
        //System.out.println(head.height);
        inorder(head.rightChild, toReturn);
        return toReturn;
    }
    int noChild = 0;
    public int howManyNoChild(myNode<myPersonalData> head) {
        //System.out.println("here");
        if (head == null) {
            return 0;
        }
        if (head.leftChild == null && head.rightChild == null) {
            return 1;
        }
        
        int leftmany = howManyNoChild(head.leftChild);
        int rightmany = howManyNoChild(head.rightChild);
        //theSum = howManyNoChild(head.leftChild, theSum) + howManyNoChild(head.rightChild, theSum);
        
        
        //System.out.println(head.height);
        
        
        
        
        return leftmany + rightmany;
    }
    //////////////////////ıntel target/////////////////////
    public myNode<myPersonalData> highestCommon (myNode<myPersonalData> head, myPersonalData first, myPersonalData second){
        if(head == null){
            return null;
        }
        if(head.data.gValue > first.gValue && head.data.gValue > second.gValue){
            return highestCommon(head.leftChild, first, second);
        }
        else if(head.data.gValue < first.gValue && head.data.gValue < second.gValue){
            return highestCommon(head.rightChild, first, second);
        }
        return head;
    }
    //////////////////////7
    /////////////////intel rank////////////////////////
    public static int rotationCount =0 ;
    public int findDepth(myNode<myPersonalData> head, myPersonalData myData, int curRank){
        if(head == null){
            return 0;
        }
        if(head.data.gValue == myData.gValue){
            return curRank;
        }
        if(head.data.gValue < myData.gValue){
            return findDepth(head.rightChild, myData, curRank + 1);
        }
        if(head.data.gValue > myData.gValue){
            return findDepth(head.leftChild, myData, curRank + 1);
        }
        return -1;
    }
    public static ArrayList<ArrayList<myPersonalData>> sameRank = new ArrayList<>();    
    public void rankTraverse(myNode<myPersonalData> head, int curDepth){
        if(head == null){
            return;
        }
        sameRank.get(curDepth).add(head.data);
        rankTraverse (head.leftChild, curDepth + 1);
        rankTraverse (head.rightChild, curDepth + 1);
        
        
    }
}
