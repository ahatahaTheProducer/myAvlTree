import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.lang.reflect.Array;
import java.util.HashMap;

public class Main {
    public static int countforRank = -1;
    public static HashMap<Integer, String> megaRankMap = new HashMap<>();
    public static void main(String[] args ){
        String inFileName;
        String outFileName;
        inFileName = args[0];
        outFileName = args[1];
        //print out the file names just for checking
        //System.out.println("Input file name: " + inFileName);
        //System.out.println("Output file name: " + outFileName);
        //now we have the file names, we can start reading the file
        //and writing to the output file

        //create a new file to read it
        File inFile = new File(inFileName);
        
        
        ArrayList<String> inLines = new ArrayList<>();
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                inLines.add(sc.nextLine());
            }
            sc.close();
        }catch(Exception e){
            System.out.println(inFileName +  " not found in the directory");
            return;
        }
        //now we have the lines in the file, we can start manipulating them
        //boss name does not change it is always the first line
        //so we can specify it
        String[] bossData = (inLines.get(0)).split(" ");
        myPersonalData boss = new myPersonalData(bossData[0], Float.parseFloat(bossData[1]));
        int i = 1;
        //System.out.printf("%.3f\n", boss.gValue);
        GoldenTree<myPersonalData> famTree = new GoldenTree<>(outFileName);
        famTree.insert(boss);
        boolean isTouched = false;
        while(i < inLines.size()){
            String[] curData = (inLines.get(i)).split(" ");
            if(curData[0].equals("MEMBER_IN")){
                myPersonalData curMember = new myPersonalData(curData[1], Float.parseFloat(curData[2]));
                famTree.insert(curMember);
                i++;
            }
            else if(curData[0].equals("MEMBER_OUT")){
                myPersonalData curMember = new myPersonalData(curData[1], Float.parseFloat(curData[2]));
                famTree.remove(curMember);
                i++;
            }
            else if(curData[0].equals("INTEL_DIVIDE")){
                
                famTree.mywrite("Division Analysis Result: " + maxDiffFam(famTree.boss) + "\n");
                i++;

            }
            else if(curData[0].equals("INTEL_TARGET")){
                myPersonalData curMember1 = new myPersonalData(curData[1], Float.parseFloat(curData[2]));
                myPersonalData curMember2 = new myPersonalData(curData[3], Float.parseFloat(curData[4]));
                myPersonalData result = famTree.highestCommon(famTree.boss, curMember1, curMember2).data;
                String strDouble = String.format("%.3f", result.gValue);
                strDouble = strDouble.replace(",", ".");
                famTree.mywrite("Target Analysis Result: " + result.name + " " + strDouble + "\n");
                i++;
            }
            else if(curData[0].equals("INTEL_RANK")){
                myPersonalData curMember = new myPersonalData(curData[1], Float.parseFloat(curData[2]));
                int toDepth = famTree.findDepth(famTree.boss, curMember, 1);
                
                if (GoldenTree.rotationCount == countforRank) {
                    boolean eq = true;
                    
                } 
                else {
                    //System.out.println("dırırıdıtaşlkjsdfşllaksdffşlkajsdfşlkj" + GoldenTree.rotationCount + " "+ countforRank);
                    ArrayList<ArrayList<myPersonalData>> tempMega = new ArrayList<>();
                    for (int j = 0; j <= famTree.boss.height; j++) {
                        ArrayList<myPersonalData> temp = new ArrayList<>();
                        tempMega.add(temp);
                    }
                    GoldenTree.sameRank = tempMega;
                    famTree.rankTraverse(famTree.boss, 1);
                    countforRank = GoldenTree.rotationCount;
                    HashMap<Integer, String> temp = new HashMap<>();
                    megaRankMap = temp;
                }
                String toPut = "";
                famTree.mywrite("Rank Analysis Result: ");
                if(megaRankMap.containsKey(toDepth)){
                    famTree.mywrite(megaRankMap.get(toDepth));
                    //System.out.println("buraya girdi");
                }
                else{
                    
                    for (myPersonalData temp: GoldenTree.sameRank.get(toDepth)) {
                        String strDouble = String.format("%.3f", temp.gValue);
                        strDouble = strDouble.replace(",", ".");
                        toPut += temp.name + " " + strDouble+ " ";
                    }
                    famTree.mywrite(toPut);
                    megaRankMap.put(toDepth, toPut);
                }
                
                famTree.mywrite("\n");
                i++;
                
            }
        }
        
        int theSize = inLines.size();
        ArrayList<myPersonalData> theList  = new ArrayList<>();
        ArrayList<myPersonalData> maaayList = new ArrayList<>();
        int howMany = famTree.howManyNoChild(famTree.boss);
         
        
        for(; i < theSize; i++){

        }
        
        
        
        
        
        
    }
    ////////////////RANK TARGET///////////////////
    
    public static ArrayList<ArrayList<myPersonalData>> rankMegaList = new ArrayList<>();
    
    ////////////////////////INTEL DIVIDE//////////////////////////
    public static int sumGrandChild(myNode<myPersonalData> head, HashMap<myNode<myPersonalData>, Integer> mp){
            int sum = 0;
            if(head.leftChild != null){
                sum += getMax(head.leftChild.leftChild, mp) + getMax(head.leftChild.rightChild, mp);

            }
            if(head.rightChild != null){
                sum += getMax(head.rightChild.leftChild, mp) + getMax(head.rightChild.rightChild, mp);
            }
            return sum;
    }
    public static int getMax(myNode<myPersonalData> head, HashMap<myNode<myPersonalData>, Integer> mp){
        if(head == null){
            return 0;
        }
        if(mp.containsKey(head)){
            return mp.get(head);
        }
        int withThis = 1 + sumGrandChild(head, mp);
        int withoutThis = getMax(head.leftChild, mp) + getMax(head.rightChild, mp);
        mp.put(head, Math.max(withThis, withoutThis));
        return mp.get(head);
    }
    public static int maxDiffFam(myNode<myPersonalData> head){
        if (head == null){
            return 0;
        }
        HashMap<myNode<myPersonalData>, Integer> mp = new HashMap<>();
        return getMax(head, mp);
    }
    /////////////////////////INTEL DIVIDE /////////////////////////////
    //i used the below one for divide but it is not working for some reason
    // int theHeight = famTree.boss.height;
                // int noChild = famTree.howManyNoChild(famTree.boss);
                
                
                // int theSum;
                // if (Math.pow(2, theHeight-1) == noChild){//full tree
                //     int firstTerm;
                //     int commonRatio = 4;
                //     int termNum;
                //     if (theHeight % 2 == 0){termNum = (int)(theHeight/2); firstTerm = 2;}
                //     else{termNum = (int)(theHeight/2) + 1; firstTerm = 1;}
                //     theSum = firstTerm * (int)((int)(Math.pow(commonRatio, termNum) - 1) / (commonRatio - 1));
                // }
                // else {
                //     if (theHeight - 3 <= 2) {
                //         if (theHeight - 3 == 1) {
                //             theSum = 1;
                //         } else {
                //             theSum = 2;
                //         }
                //     } else {
                //         int mymyHeight = theHeight - 3;
                //         int firstTerm;
                //         int commonRatio = 4;
                //         int termNum;
                //         if (mymyHeight % 2 == 0){termNum = (int)(mymyHeight/2); firstTerm = 2;}
                //         else{termNum = (int)(mymyHeight/2) + 1; firstTerm = 1;}
                //         theSum = firstTerm * (int)((int)(Math.pow(commonRatio, termNum) - 1) / (commonRatio - 1));
                //     }
                    
                // }
                // theSum = theSum + noChild;
}