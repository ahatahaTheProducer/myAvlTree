
    // we are creating a private myNode class, so that we can use it in our avlGolden
    // mean tree
public class myNode<T> {
    public myNode<myPersonalData> leftChild;
    public myNode<myPersonalData> rightChild;
    public int height;
    public myPersonalData data;
    public boolean noChild = true;
    public static int nodeCount = 0;

    myNode(myPersonalData data) {
        this(data, null, null, 1);
    }

    myNode(myPersonalData data, myNode<myPersonalData> leftChild, myNode<myPersonalData> rightChild, int height) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.height = height;
        nodeCount++;
    }
    
}

