package cripto;


//plainText -> litere mici
public class Vigenere {

    static private String keyStr;
    static private String plainTextStr;
    static private String encryptedStr;

    public static void main(String[] args) {
        Key key = new Key();
        PlainText plainText = new PlainText();
        Cryptanalysis cipher = new Cryptanalysis();

        //read text + key
        key.readKey("key.txt");
        plainText.readText("text.txt");

        keyStr = key.getKey();
        plainTextStr = plainText.getPlainText();

        //encrypt text
        cipher.setEncryptedText(cipher.encryptText(plainTextStr, keyStr));
        encryptedStr= cipher.getEncryptedText();
        cipher.printEncryptedText();

        cipher.cryptanalysis();

        //decrypt text
//        cipher.decryptText(encryptedStr, keyStr);
//        cipher.printDecryptedText();

    }
}
