import java.util.LinkedList;

// MyBigInteger.java
public class MyBigInteger {
    IntegerNode sign = new IntegerNode(0);

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

        //We assume there are atleast some digits in each number
        IntegerNode tracker1 = new IntegerNode(0);// For tracking num1
        tracker1 = num1.sign.higher_position;

        IntegerNode tracker2 = new IntegerNode(0); // For tracking num2
        tracker2 = num2.sign.higher_position;

        IntegerNode finalTracker = new IntegerNode(0); // For tracking afterAddition
        MyBigInteger afterAddition = new MyBigInteger();
        if(num1.sign.digits == num2.sign.digits){
            int carry = 0;

            if(num1.sign.digits == -1){
                afterAddition.sign.digits = -1;
            }
            else{
                afterAddition.sign.digits = 0;
            }

            finalTracker = afterAddition.sign; // To track new LinkedList

            // Adding the numbers up together
            while(tracker1 != null && tracker2 != null){
                int numberAfterAddition = tracker1.digits + tracker2.digits + carry;
                carry = numberAfterAddition / 10000;
                int numberToAdd = numberAfterAddition % 10000;
                IntegerNode newNode = new IntegerNode(numberToAdd);
                finalTracker.higher_position = newNode;
                finalTracker = finalTracker.higher_position;
                tracker1 = tracker1.higher_position;
                tracker2 = tracker2.higher_position;
            }

            // Case where num1 had more nodes than num2
            while(tracker1 != null){
                int numberToAdd = tracker1.digits + carry;
                IntegerNode newNode = new IntegerNode(numberToAdd);
                carry = 0;
                finalTracker.higher_position = newNode;
                finalTracker = finalTracker.higher_position;
                tracker1 = tracker1.higher_position;
            }

            //Case where num2 has more nodes than num1
            while(tracker2 != null){
                int numberToAdd = tracker2.digits + carry;
                IntegerNode newNode = new IntegerNode(numberToAdd);
                carry = 0;
                finalTracker.higher_position = newNode;
                finalTracker = finalTracker.higher_position;
                tracker2 = tracker2.higher_position;
            }

        }
        else{
            boolean borrowed = false;
            boolean firstIsNegative = false;

            if(tracker1.digits == '-'){
                firstIsNegative = true;
            }

            if(firstIsNegative){
                IntegerNode tempTracker = new IntegerNode(0);
                tempTracker = tracker1;
                tracker1 = tracker2;
                tracker2 = tempTracker;
            }

            while(tracker1.higher_position != null && tracker2.higher_position != null){
                if(tracker1.digits < tracker2.digits){
                    int finalAnswer = tracker2.digits - tracker1.digits;
                    IntegerNode newNode = new IntegerNode(finalAnswer);
                    finalTracker.higher_position = newNode;
                    tracker1 = tracker1.higher_position;
                    tracker2 = tracker2.higher_position;
                    finalTracker = finalTracker.higher_position;
                    borrowed = false;
                }
                else{
                    int finalAnswer = (tracker2.digits + 10000) - tracker1.digits;
                    IntegerNode newNode = new IntegerNode(finalAnswer);
                    tracker2.higher_position.digits --; // Since we carry
                    finalTracker.higher_position = newNode;
                    tracker1 = tracker1.higher_position;
                    tracker2 = tracker2.higher_position;
                    finalTracker = finalTracker.higher_position;
                    borrowed = true;
                }
            }

            while(tracker1 != null && tracker2 != null){
                if(borrowed){
                    tracker2.digits --;
                    borrowed = false;
                }
                if(tracker1.digits > tracker2.digits){
                   IntegerNode newNode = new IntegerNode(tracker1.digits - tracker2.digits);
                    finalTracker.higher_position = newNode;
                    afterAddition.sign.digits = -1;
                }
                else{
                    IntegerNode newNode = new IntegerNode(tracker2.digits - tracker1.digits);
                    finalTracker.higher_position = newNode;
                    afterAddition.sign.digits = 0;
                }

                finalTracker = finalTracker.higher_position;
                tracker1 = tracker1.higher_position;
                tracker2 = tracker2.higher_position;
            }

            while(tracker1 != null){
                afterAddition.sign.digits = -1;
                IntegerNode newNode = new IntegerNode(tracker1.digits);
                finalTracker.higher_position = newNode;
                finalTracker = finalTracker.higher_position;
                tracker1 = tracker1.higher_position;
            }

            while(tracker2 != null){
                afterAddition.sign.digits = 0;
                if(borrowed){
                    tracker2.digits --;
                    borrowed = false;
                }

                IntegerNode newNode = new IntegerNode(tracker2.digits);
                finalTracker.higher_position = newNode;

                finalTracker = finalTracker.higher_position;
                tracker2 = tracker2.higher_position;
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


