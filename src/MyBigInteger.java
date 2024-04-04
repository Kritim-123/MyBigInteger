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
        // First node might have less than four digits and shouldn't be padded with zeros
        boolean firstNode = true;
        while (current != null) {
            if (firstNode) {
                result.insert(0, current.digits); // No padding or absolute value for the first group
                firstNode = false;
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


