package desAlgorithm;


import java.math.BigInteger;
import java.util.BitSet;

class DesAlgorithm{

    private static BigInteger key = new BigInteger(new String("pluto").getBytes());
    private static String[][] sBoxOne = new String[2][8];
    private static String[][] sBoxTwo = new String[2][8];

    /**
     *
     * @param wordAsBigInt
     * @return
     */
    public static BigInteger encrypt(BigInteger wordAsBigInt){
       return wordAsBigInt.xor(key);
    }

    /**
     * this is the expansion function of the DES algorithm, expands the
     * last 6 right bits in 8 bits and it returns these like a binary string
     * @param lastRightBits
     * @return
     */
    public static String expansionFunction(String lastRightBits){

        char[] lastRightBitsToChar = new char[8];

        lastRightBitsToChar[0] = lastRightBits.charAt(0);
        lastRightBitsToChar[1] = lastRightBits.charAt(1);
        lastRightBitsToChar[2] = lastRightBits.charAt(3);
        lastRightBitsToChar[3] = lastRightBits.charAt(2);
        lastRightBitsToChar[4] = lastRightBits.charAt(3);
        lastRightBitsToChar[5] = lastRightBits.charAt(2);
        lastRightBitsToChar[6] = lastRightBits.charAt(4);
        lastRightBitsToChar[7] = lastRightBits.charAt(5);

        return new String(lastRightBitsToChar);
    }

    /**
     * It returns the value from sbox
     * It takes in input an integer (4 bits) and return the corrisponding value
     * @param value
     * @return
     */
    public static String sBoxOneResult(int value){
        DesAlgorithm.setsBoxOne();
        return sBoxOne[value >>> 3][value & 0x7];
    }

    public static String sBoxTwoResult(int value){
        DesAlgorithm.setsBoxTwo();
        return sBoxTwo[value >>> 3][value & 0x7];
    }

    public static void setKey(String keyWord){
        key = new BigInteger(keyWord.getBytes());
    }

    public static BigInteger getKey(){
        return key;
    }

    private static void setsBoxOne(){
        sBoxOne[0][0] = "101";
        sBoxOne[0][1] = "010";
        sBoxOne[0][2] = "001";
        sBoxOne[0][3] = "110";
        sBoxOne[0][4] = "011";
        sBoxOne[0][5] = "100";
        sBoxOne[0][6] = "111";
        sBoxOne[0][7] = "000";

        sBoxOne[1][0] = "001";
        sBoxOne[1][1] = "100";
        sBoxOne[1][2] = "110";
        sBoxOne[1][3] = "010";
        sBoxOne[1][4] = "000";
        sBoxOne[1][5] = "111";
        sBoxOne[1][6] = "101";
        sBoxOne[1][7] = "011";
    }

    private static void setsBoxTwo(){
        sBoxTwo[0][0] = "100";
        sBoxTwo[0][1] = "000";
        sBoxTwo[0][2] = "110";
        sBoxTwo[0][3] = "101";
        sBoxTwo[0][4] = "111";
        sBoxTwo[0][5] = "001";
        sBoxTwo[0][6] = "011";
        sBoxTwo[0][7] = "010";

        sBoxTwo[1][0] = "101";
        sBoxTwo[1][1] = "011";
        sBoxTwo[1][2] = "000";
        sBoxTwo[1][3] = "111";
        sBoxTwo[1][4] = "110";
        sBoxTwo[1][5] = "010";
        sBoxTwo[1][6] = "001";
        sBoxTwo[1][7] = "100";
    }

}