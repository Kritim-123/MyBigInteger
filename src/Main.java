public class Main {
    public static void main(String[] args) {
        // Test cases
        MyBigInteger num1 = new MyBigInteger("1234567891110");
//        System.out.println(num1);

        MyBigInteger num2 = new MyBigInteger("-9382361766839928276166829");
        MyBigInteger sum1 = MyBigInteger.add(num1, num2);
        System.out.println("The sum is " + sum1 + ".");
//
//        MyBigInteger num3 = new MyBigInteger("839947462729219484028272");
//        MyBigInteger num4 = new MyBigInteger("-839947462729219484028000");
//        MyBigInteger sum2 = MyBigInteger.add(num3, num4);
//        System.out.println("The sum is " + sum2 + ".");
//
//        MyBigInteger num5 = new MyBigInteger("-25634837829208474747382992822");
//        MyBigInteger num6 = new MyBigInteger("-6382927634646483929283733883");
//        MyBigInteger sum3 = MyBigInteger.add(num5, num6);
//        System.out.println("The sum is " + sum3 + ".");
    }
}