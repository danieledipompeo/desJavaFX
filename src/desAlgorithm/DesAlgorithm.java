package desAlgorithm;

class DesAlgorithm{

    //It is a 9 bit lenght
    private static String key = "";

    private static String[][] sBoxOne = new String[2][8];
    private static String[][] sBoxTwo = new String[2][8];
    private static int maxRoundNumber = 4;
    private static StringBuilder sb;
    private static char[] IV;

    /**
     *
     * @param textToEncrypt
     * @return
     */
    //public static int encrypt(String textToEncrypt){
    public static String encrypt(String textToEncrypt){

        char[] textToEncryptAsArray = textToEncrypt.toCharArray();
        char[] leftPart, rightPart, xorResult, roundResult;
        char[] textBlockRound = new char[12];

        int textToEncryptAsArrayLenght = textToEncryptAsArray.length;
        char[] result = new char[textToEncryptAsArrayLenght];
        String keyRound, textToEncryptAfterRound, feistelFunction;

        generateKey();

        ConsoleController.toConsole("@@@@@@@@@@@ START Encrypt @@@@@@@@@@@");

        for(int j=0; j < (textToEncryptAsArrayLenght/12); j++)
        {
            System.arraycopy(textToEncryptAsArray,(j*12), textBlockRound, 0, 12);

            for(int countRound=0; countRound < maxRoundNumber;countRound++){

                //calculates the round key
                keyRound = DesAlgorithm.getKeyRound(countRound+1, key);

                leftPart = takeBitFromArray(textBlockRound, 0, 6);
                rightPart = takeBitFromArray(textBlockRound, 6, 6);

                //calculates in first the Expantion function E(Ri-i) and after the XOR opertion between E(Ri-1) and Ki
                xorResult = xor( expansionFunction(rightPart) ,keyRound.toCharArray());

                feistelFunction = sBoxOneResult(takeBitFromArray(xorResult,0,4))+
                        sBoxTwoResult(takeBitFromArray(xorResult,4,4));
                roundResult = xor(leftPart,feistelFunction.toCharArray());

                //Creates a string from Right and Left Part
                sb = new StringBuilder();
                sb.append(rightPart);
                sb.append(roundResult);
                textToEncryptAfterRound = new String(sb);
                textBlockRound = textToEncryptAfterRound.toCharArray();

                // prints to Console Scene the results
                ConsoleController.toConsole("\n\t ********** START ROUND "+(countRound+1)+" ****************");
                ConsoleController.toConsole("\t\t keyRound: "+keyRound);
                ConsoleController.toConsole("\t\t L("+countRound+"): "+ new String(leftPart));
                ConsoleController.toConsole("\t\t R("+countRound+"): "+ new String(rightPart));
                ConsoleController.toConsole("\t\t E(R"+countRound+"): "+ new String(expansionFunction(rightPart)));
                ConsoleController.toConsole("\t\t E(R"+countRound+") xor K["+countRound+1+"] result: "+ new String(xorResult));
                ConsoleController.toConsole("\t\t SBox1 result: "+ sBoxOneResult(takeBitFromArray(xorResult,0,4)));
                ConsoleController.toConsole("\t\t SBox2 result: "+ sBoxTwoResult(takeBitFromArray(xorResult, 4, 4)));
                ConsoleController.toConsole("\t\t Feistel Function result: "+ new String(feistelFunction));
                ConsoleController.toConsole("\t\t (L("+countRound+") xor Feistel Function): "+ new String(roundResult));
                ConsoleController.toConsole("\t\t textToEncrypt after round: "+ new String(textToEncryptAfterRound));
                ConsoleController.toConsole("\t ********** END ROUND "+(countRound+1)+" ****************");
            }
            System.arraycopy(textBlockRound, 0, result, (j * 12), 12);
        }
        String resultAsString = new String(result);
        ConsoleController.toConsole("@@@@@@@@@@@ END Encrypt @@@@@@@@@@@");

        return resultAsString;
    }

    /**
     *
     * @param textToEncrypt
     * @return
     */
    public static String decrypt(String textToEncrypt){

        char[] leftPart, rightPart, xorResult;
        char[] textBlockRound = new char[12];
        char[] textToEncryptAsArray = textToEncrypt.toCharArray();
        String keyRound, textToEncryptAfterRound;
        int textToEncryptAsArrayLenght = textToEncryptAsArray.length;

        char[] result = new char[textToEncryptAsArrayLenght];

        StringBuilder sb;

        //generates the algorithm key
        generateKey();

        ConsoleController.toConsole("@@@@@@@@@@@ START Decrypt @@@@@@@@@@@");

        for(int j=0; j < (textToEncryptAsArrayLenght/12); j++)
        {
            System.arraycopy(textToEncryptAsArray,(j*12), textBlockRound, 0, 12);

            textBlockRound = getPermutation(textBlockRound);

            for(int countRound=maxRoundNumber; countRound > 0;countRound--){

                //calculates the round key
                keyRound = DesAlgorithm.getKeyRound(countRound, key);
                leftPart = takeBitFromArray(textBlockRound, 0, 6);
                rightPart = takeBitFromArray(textBlockRound, 6, 6);
                //calculates in first the Expantion function E(Ri-i) and after the XOR opertion between E(Ri-1) and Ki
                xorResult = xor( expansionFunction(rightPart) ,keyRound.toCharArray() );
                String feistelFunctionResult = sBoxOneResult(takeBitFromArray(xorResult,0,4))+
                        sBoxTwoResult(takeBitFromArray(xorResult,4,4));
                char[] roundResult = xor(leftPart, feistelFunctionResult.toCharArray());
                //Creates a string from Right and Left Part
                sb = new StringBuilder();
                sb.append(rightPart);
                sb.append(roundResult);
                textToEncryptAfterRound = new String(sb);

                //prints to Console Scene the results
                ConsoleController.toConsole("\n\t ********** START ROUND "+(countRound)+" ****************");
                ConsoleController.toConsole("\t\t keyRound: "+keyRound);
                ConsoleController.toConsole("\t\t L("+countRound+"): "+ new String(leftPart));
                ConsoleController.toConsole("\t\t R("+countRound+"): "+ new String(rightPart));
                ConsoleController.toConsole("\t\t E(R"+countRound+"): "+ expansionFunction(rightPart));
                ConsoleController.toConsole("\t\t E(R"+countRound+") xor K["+countRound+1+"] result: "+ new String(xorResult));
                ConsoleController.toConsole("\t\t SBox1 result: "+ new String(sBoxOneResult(takeBitFromArray(xorResult,0,4))));
                ConsoleController.toConsole("\t\t SBox2 result: "+ new String(sBoxTwoResult(takeBitFromArray(xorResult, 4, 4))));
                ConsoleController.toConsole("\t\t Feistel Function result: "+ feistelFunctionResult);
                ConsoleController.toConsole("\t\t (L("+countRound+") xor Feistel Function): "+ new String(roundResult));
                ConsoleController.toConsole("\t\t textToEncrypt after round: "+ new String(textToEncryptAfterRound));
                ConsoleController.toConsole("\t ********** END ROUND "+(countRound+1)+" ****************");

                textBlockRound = textToEncryptAfterRound.toCharArray();
            }
            textBlockRound = getPermutation(textBlockRound);

            System.arraycopy(textBlockRound,0,result,(j*12),12);
        }

        ConsoleController.toConsole("@@@@@@@@@@@ END Decrypt @@@@@@@@@@@");

        return new String(binary2Text(result));
    }

    public static String CTREncrypt(String textToEncrypt){

        char[] textToEncryptAsCharArray = textToEncrypt.toCharArray();
        char[] P = new char[8];
        char[] O = new char[8];
        char[] result = new char[textToEncryptAsCharArray.length];
        int textToEncryptLength = textToEncryptAsCharArray.length/8;
        char[] IV = encrypt(new String(generateInitialVector())).toCharArray();

        ConsoleController.toConsole("IV encypted: " + new String(IV));
        ConsoleController.toConsole("binary string of Text To Encrypt: " + new String(textToEncrypt));

        for(int i=0; i<textToEncryptLength; i++){

            textToEncrypt.getChars(8 * i, 8 + 8 * i, P, 0);
            new String(IV).getChars(0, 8, O, 0);
            plusOne(IV);
            System.arraycopy(xor(P,O),0,result,8*i,8);
        }

        return new String(result);
    }

    public static String CTRDecrypt(String textToDecrypt){

        char[] textToEncryptAsCharArray = textToDecrypt.toCharArray();
        int textToDecryptLength = textToEncryptAsCharArray.length/8;
        char[] P = new char[8];
        char[] O = new char[8];
        char[] result = new char[textToEncryptAsCharArray.length];
        char[] IV = encrypt(new String(generateInitialVector())).toCharArray();

        ConsoleController.toConsole("IV encrypted: " + new String(IV));
        ConsoleController.toConsole("binary string of Text To Decrypt: " + new String(textToDecrypt));

        for(int i=0; i < textToDecryptLength; i++){
            textToDecrypt.getChars(8*i,8+8*i,P,0);
            new String(IV).getChars(0,8,O,0);
            plusOne(IV);

            System.arraycopy(xor(P,O),0,result,8*i,8);
        }

        return new String(binary2Text(result));
    }


    public static char[] generateInitialVector(){
        IV = new String("010011000110011001001100011001100100110001100110").toCharArray();
        return IV;
    }

    public static void generateKey(){
        //generates the algorithm key
        generateKey("010011001");
    }

    private static void plusOne(char[] IV){
        int IVLength = IV.length;
        int riportoAddizione = 0;
        int i = 0;

        do{
            if(i == 0){
                if(IV[i]=='0'){
                    IV[i] = '1';
                    riportoAddizione=0;
                }
                else{
                    IV[i] = '0';
                    riportoAddizione = 1;
                }
            }
            else{
                if(IV[i]=='0'){
                    IV[i] = '1';
                    riportoAddizione=0;
                }
                else{
                    IV[i] = '0';
                    riportoAddizione = 1;
                }
            }
            i++;
        }while(riportoAddizione!=0 && i < IVLength);
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