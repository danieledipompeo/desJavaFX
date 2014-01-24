package desAlgorithm;

class DesAlgorithm{

    //It is a 9 bit lenght
    private static String key = "";

    private static String[][] sBoxOne = new String[2][8];
    private static String[][] sBoxTwo = new String[2][8];
    private static int maxRoundNumber = 4;
    private static StringBuilder sb;

    /**
     *
     * @param textToEncrypt
     * @return
     */
    //public static int encrypt(String textToEncrypt){
    public static String encrypt(String textToEncrypt){

        char[] textToEncryptAsArray = textToEncrypt.toCharArray();
        char[] leftPart;
        char[] rightPart;
        char[] test = new char[12];

        String keyRound;
        char[] xorResult;
        String textToEncryptAfterRound;
        int textToEncryptAsArrayLenght;

        textToEncryptAsArrayLenght = textToEncryptAsArray.length;
        char[] aux = new char[textToEncryptAsArrayLenght];

        ConsoleController.toConsole("@@@@@@@@@@@ START Encrypt @@@@@@@@@@@");

        for(int j=0; j < (textToEncryptAsArrayLenght/12); j++)
        {
            System.arraycopy(textToEncryptAsArray,(j*12), test, 0, 12);

            for(int countRound=0;countRound < maxRoundNumber;countRound++){

                //calculate the round key
                keyRound = DesAlgorithm.getKeyRound(countRound+1, key);

                ConsoleController.toConsole("\n\t ********** START ROUND "+(countRound+1)+" ****************");
                ConsoleController.toConsole("\t\t keyRound: "+keyRound);

                leftPart = takeBitFromArray(test, 0, 6);
                ConsoleController.toConsole("\t\t L("+countRound+"): "+ new String(leftPart));

                rightPart = takeBitFromArray(test, 6, 6);
                ConsoleController.toConsole("\t\t R("+countRound+"): "+ new String(rightPart));

                ConsoleController.toConsole("\t\t E(R"+countRound+"): "+ new String(expansionFunction(rightPart)));

                xorResult = xor( expansionFunction(rightPart) ,keyRound.toCharArray());

                ConsoleController.toConsole("\t\t E(R"+countRound+") xor K["+countRound+1+"] result: "+ new String(xorResult));

                ConsoleController.toConsole("\t\t SBox1 result: "+ sBoxOneResult(takeBitFromArray(xorResult,0,4)));
                ConsoleController.toConsole("\t\t SBox2 result: "+ sBoxTwoResult(takeBitFromArray(xorResult, 4, 4)));

                String feistelFunction = sBoxOneResult(takeBitFromArray(xorResult,0,4))+
                        sBoxTwoResult(takeBitFromArray(xorResult,4,4));
                ConsoleController.toConsole("\t\t Feistel Function result: "+ new String(feistelFunction));

                char[] roundResult = xor(leftPart,feistelFunction.toCharArray());
                ConsoleController.toConsole("\t\t (L("+countRound+") xor Feistel Function): "+ new String(roundResult));

                sb = new StringBuilder();
                sb.append(rightPart);
                sb.append(roundResult);
                textToEncryptAfterRound = new String(sb);
                test = textToEncryptAfterRound.toCharArray();

                ConsoleController.toConsole("\t\t textToEncrypt after round: "+ new String(textToEncryptAfterRound));
                ConsoleController.toConsole("\t ********** END ROUND "+(countRound+1)+" ****************");
            }
            System.arraycopy(test,0,aux,(j*12),12);
            ConsoleController.toConsole("risultato giro "+j+": "+new String(aux));
        }
        String back = new String(aux);
        ConsoleController.toConsole("stringa criptata: "+back);

        ConsoleController.toConsole("@@@@@@@@@@@ END Encrypt @@@@@@@@@@@");
        return back;
    }

    /**
     *
     * @param textToEncrypt
     * @return
     */
    public static String decrypt(String textToEncrypt){

        char[] leftPart;
        String keyRound;
        char[] rightPart;
        char[] xorResult;
        String textToEncryptAfterRound = "goofy";

        //generates the algorithm key
        DesAlgorithm.generateKey("010011001");
        /**
         * Is the shifting operation as first action to decrypt
         */
        char[] textToEncryptAsArray = textToEncrypt.toCharArray();

        StringBuilder sb;
        int textToEncryptAsArrayLenght = textToEncryptAsArray.length;

        char[] test = new char[12];
        char[] aux = new char[textToEncryptAsArrayLenght];

        ConsoleController.toConsole("\n\n\n");
        ConsoleController.toConsole("@@@@@@@@@@@ START Decrypt @@@@@@@@@@@");

        for(int j=0; j < (textToEncryptAsArrayLenght/12); j++)
        {
            System.arraycopy(textToEncryptAsArray,(j*12), test, 0, 12);

            test = getPermutation(test);

            for(int countRound=maxRoundNumber; countRound > 0;countRound--){

                //calculate the round key
                keyRound = DesAlgorithm.getKeyRound(countRound, key);

                ConsoleController.toConsole("\n\t ********** START ROUND "+(countRound)+" ****************");
                ConsoleController.toConsole("\t\t keyRound: "+keyRound);

                leftPart = takeBitFromArray(test, 0, 6);
                ConsoleController.toConsole("\t\t L("+countRound+"): "+ new String(leftPart));

                rightPart = takeBitFromArray(test, 6, 6);
                ConsoleController.toConsole("\t\t R("+countRound+"): "+ new String(rightPart));

                ConsoleController.toConsole("\t\t E(R"+countRound+"): "+ expansionFunction(rightPart));

                xorResult = xor( expansionFunction(rightPart) ,keyRound.toCharArray() );

                ConsoleController.toConsole("\t\t E(R"+countRound+") xor K["+countRound+1+"] result: "+ new String(xorResult));

                ConsoleController.toConsole("\t\t SBox1 result: "+ new String(sBoxOneResult(takeBitFromArray(xorResult,0,4))));
                ConsoleController.toConsole("\t\t SBox2 result: "+ new String(sBoxTwoResult(takeBitFromArray(xorResult, 4, 4))));

                String feistelFunction = sBoxOneResult(takeBitFromArray(xorResult,0,4))+
                        sBoxTwoResult(takeBitFromArray(xorResult,4,4));
                ConsoleController.toConsole("\t\t Feistel Function result: "+ feistelFunction);

                char[] roundResult = xor(leftPart,feistelFunction.toCharArray());
                ConsoleController.toConsole("\t\t (L("+countRound+") xor Feistel Function): "+ new String(roundResult));

                sb = new StringBuilder();
                sb.append(rightPart);
                sb.append(roundResult);
                textToEncryptAfterRound = new String(sb);
                ConsoleController.toConsole("\t\t textToEncrypt after round: "+ new String(textToEncryptAfterRound));
                ConsoleController.toConsole("\t ********** END ROUND "+(countRound+1)+" ****************");

                test = textToEncryptAfterRound.toCharArray();
            }
            test = getPermutation(test);

            System.arraycopy(test,0,aux,(j*12),12);
        }

        String back = new String(binary2Text(aux));

        ConsoleController.toConsole("\t Stringa descriptata: "+back);
        ConsoleController.toConsole("@@@@@@@@@@@ END Decrypt @@@@@@@@@@@");

        return back;
    }

    public static void generateKey(){
        //generates the algorithm key
        generateKey("010011001");
    }

    /**
     *
     * @param binaryArray
     * @return
     */
    private static StringBuilder binary2Text(char[] binaryArray){

        char[] binaryToText = new char[8];
        StringBuilder sb = new StringBuilder();
        String strTest;
        int auxSize = binaryArray.length;

        for ( int i = 0; i < auxSize;) {
            System.arraycopy(binaryArray,i,binaryToText,0,8);
            strTest = new String(binaryToText);
            sb.append( (char)Integer.parseInt( strTest, 2 ) );
            i+=8;
        }
        return sb;
    }

    private static char[] getPermutation(char[] arrayToCommute){
        /**
         * the last shifting operation
         */
        char[] leftPart, rightPart;

        sb = new StringBuilder();
        leftPart = takeBitFromArray(arrayToCommute, 0, 6);
        rightPart = takeBitFromArray(arrayToCommute, 6, 6);
        sb.append(rightPart);
        sb.append(leftPart);

        return new String(sb).toCharArray();
    }

    /**
     *
     * @param firstWordAsArray
     * @param secondWordAsArray
     * @return
     */
    private static char[] xor(char[] firstWordAsArray, char[]secondWordAsArray){

        char[] result = new char[firstWordAsArray.length];
        ConsoleController.toConsole("\t\t ######### START XOR OPERATION #########");
        for(int i=0;i<firstWordAsArray.length;i++){
            if(firstWordAsArray[i] == secondWordAsArray[i] ){
                result[i]='0';
            }
            else{
                result[i]='1';
            }
            ConsoleController.toConsole("\t\t\t xor method: "+new String(result));
        }
        ConsoleController.toConsole("\t\t ######### END XOR OPERATION #########");
        return result;
    }

    /**
     *
     * @param text
     * @param from
     * @return
     */
    private static char[] takeBitFromArray(char[] text, int from, int lenght){
        char[] backValue = new char[lenght];
        System.arraycopy(text,from, backValue, 0, lenght);
        return backValue;
    }

    /**
     *
     * @param round
     * @param text
     * @return
     */
    private static String getKeyRound(int round, String text){
        char[] stringToCharArray = text.toCharArray();
        char[] keyRound = new char[8];
        //takes the first i-simo bits of the key algorithm to generate the key round i-simo
        for(int i=round, j=0;i<stringToCharArray.length && j<8;i++,j++){
            keyRound[j] = stringToCharArray[i];
        }
        for(int i=(stringToCharArray.length-round), j=0;i<keyRound.length;i++,j++){
            keyRound[i] = stringToCharArray[j];
        }
        return new String(keyRound);
    }


    /**
     * this is the expansion function of the DES algorithm, expands the
     * last 6 right bits in 8 bits and it returns these like a binary string
     * @param lastRightBits
     * @return
     */
    public static char[] expansionFunction(char[] lastRightBits){

        char[] lastRightBitsToChar = new char[8];

        lastRightBitsToChar[0] = lastRightBits[0];
        lastRightBitsToChar[1] = lastRightBits[1];
        lastRightBitsToChar[2] = lastRightBits[3];
        lastRightBitsToChar[3] = lastRightBits[2];
        lastRightBitsToChar[4] = lastRightBits[3];
        lastRightBitsToChar[5] = lastRightBits[2];
        lastRightBitsToChar[6] = lastRightBits[4];
        lastRightBitsToChar[7] = lastRightBits[5];

        return lastRightBitsToChar;
    }

    /**
     * It returns the value from sbox
     * It takes in input an integer (4 bits) and return the corrisponding value
     * @param value
     * @return
     */
    private static String sBoxOneResult(char[] value){
        DesAlgorithm.setsBoxOne();
        char[] secondColumn = takeBitFromArray(value, 1, 3);
        return sBoxOne[Integer.parseInt(new String(takeBitFromArray(value,0,1)),2)][Integer.parseInt(new String(secondColumn),2)];
    }

    private static String sBoxTwoResult(char[] value){
        DesAlgorithm.setsBoxTwo();
        char[] secondColumn = takeBitFromArray(value, 1, 3);
        return sBoxTwo[Integer.parseInt(new String(takeBitFromArray(value,0,1)),2)][Integer.parseInt(new String(secondColumn),2)];
    }

    /**
     * This method produces a random key about 12 bits
     */
    public static void generateKey(String keyAsBinaryString){
        key = keyAsBinaryString;
    }

    public static void setKey(int keyWord){
        key = Integer.toBinaryString(keyWord);
    }

    public static String getKey(){
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