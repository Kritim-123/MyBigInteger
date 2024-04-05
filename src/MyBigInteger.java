import java.util.LinkedList;

// MyBigInteger.java
public class MyBigInteger {
    IntegerNode sign;

    public MyBigInteger(){

    }
    public MyBigInteger(String n) {
        sign = new IntegerNode(0);
        int length = n.length();
        int startPositon = 0;
        int offset = length % 4; // Either 0 or 1, 2,3
//        int endPosition = offset;

        if(n.charAt(0) == '-'){
            sign.digits = -1; // Indicating that the number is negative
            length--;
            offset = length %4; // Either 0, 1, 2, 3
            startPositon = 1;
//            endPosition = offset + 1; // For the case like "-123 1576 3190"  startPosition + (4- offset) = (4-1) =
        }
        int numberOfBlock = (offset == 0) ? length/4 : (int) (length/4) + 1;


        int endPosition = startPositon + (offset); // For the case like "1234 1576 3190"


        while(numberOfBlock>0){

            IntegerNode newNode = new IntegerNode(Integer.parseInt(n.substring(startPositon, endPosition)));
            n.substring(startPositon, endPosition);
            newNode.higher_position = sign.higher_position;
            sign.higher_position = newNode;// Iterating through the LinkedList

            startPositon = endPosition;
            endPosition+=4; //Because four element in a block

            numberOfBlock--;
        }
    }

    //
    public MyBigInteger(MyBigInteger num){
        IntegerNode trackerOriginal = new IntegerNode(num.sign.digits);

        this.sign = trackerOriginal;
        IntegerNode trackerDuplicate = this.sign;

        while(trackerOriginal.higher_position != null){ // Using two pointers on new Integer and old Integer
            trackerDuplicate.higher_position = trackerOriginal.higher_position;
            trackerOriginal = trackerOriginal.higher_position;
            trackerDuplicate = trackerDuplicate.higher_position;
        }

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        IntegerNode current = sign.higher_position;

        while (current != null) {
            if (current.higher_position == null) {
                result.insert(0, current.digits); // No padding or absolute value for the first group

            } else {
                // Using Math.abs() to avoid negative sign on individual groups and pad with zeros if necessary
                result.insert(0, String.format("%04d", Math.abs(current.digits)));
            }
            current = current.higher_position;

        }
        // If the overall number is negative, append the negative sign at the start of the StringBuilder
        if (sign.digits < 0) {
            result.insert(0, '-');
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
       if(obj instanceof MyBigInteger){
           MyBigInteger otherInteger = (MyBigInteger) obj;
           return otherInteger.toString().equals(this.toString()); // Using toString equals to check if their String
           // representation is equal or not
       }

       return false;
    }

    public static MyBigInteger add(MyBigInteger num1, MyBigInteger num2){

        MyBigInteger afterAddition = new MyBigInteger();
        IntegerNode tracker = new IntegerNode(0);
        if(num1.sign.digits == num2.sign.digits){
            if(num1.sign.digits == -1){
                afterAddition.sign.digits = -1;

            }
            else{
                afterAddition.sign.digits = 0;
            }

        }

        return afterAddition;
    }

    public static class IntegerNode{
        int digits;
        IntegerNode higher_position;

        public IntegerNode(int digits){
            this.digits = digits;
            this.higher_position = null;
        }
    }

}


