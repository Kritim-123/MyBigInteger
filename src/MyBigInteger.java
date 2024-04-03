import java.util.LinkedList;

// MyBigInteger.java
public class MyBigInteger {
    IntegerNode sign;


    public MyBigInteger(String n) {
        sign = new IntegerNode(0);
        int length = n.length();
        int startPositon = 0;
        IntegerNode tracker = sign;

        int offset = length % 4;
        int endPosition = offset;

        if(n.charAt(0) == '-'){
            sign.digits = -1; // Indicating that the number is negative
            length--;
            offset = length %4;
            startPositon = 1;
            endPosition = offset + 1; // For the case like "-1234 1576 3190"
        }

        if(endPosition == startPositon){
            endPosition = startPositon + 4; // For the case like "1234 1576 3190"
        }


        int numberOfBlock = (int) Math.ceil(length/4); // Will give us how many Blocks there will be


        while(numberOfBlock>0){

            IntegerNode newNode = new IntegerNode(Integer.parseInt(n.substring(startPositon, endPosition)));
            tracker.higher_position = newNode;
            tracker = newNode; // Iterating through the LinkedList

            startPositon = endPosition;
            endPosition+=4; //Because four element in a block

            numberOfBlock--;
        }
    }

    public class IntegerNode{
        int digits;
        IntegerNode higher_position;

        public IntegerNode(int digits){
            this.digits = digits;
            this.higher_position = null;
        }
    }

}


