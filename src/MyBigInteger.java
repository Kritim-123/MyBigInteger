import java.util.LinkedList;

// MyBigInteger.java
public class MyBigInteger {
    IntegerNode sign;


    public MyBigInteger(String n) {
        sign.digits = 0;
        int length = n.length();

        if(n.charAt(0) == '-'){
            sign.digits = -1; // Indicating that the number is negative
            length--;
        }

        LinkedList<Integer> numbers = new LinkedList<>();
        numbers.add(sign.digits);

        int numberOfBlock = (int) Math.ceil(length/4); // Will give us how many Blocks there will be
        int offset = length % 4;

        int startPosition = ((numberOfBlock-1)*4) - offset;
        int endPosition = (numberOfBlock*4)-offset;

        while(numberOfBlock>=2){

        }
    }

    public class IntegerNode{
        int digits;
        IntegerNode higher_position;
    }

}


