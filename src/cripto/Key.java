package cripto;

import cripto.FileHandling;

public class Key extends FileHandling {
    private String key;

    Key() {
        key = "";
    }

    //generate a random key and write the result in key.txt
    public void generateKey(int keyLength) {
        StringBuilder strKey = new StringBuilder();
        for (int i = 0; i < keyLength; ++i) {
            int num = 97 + (int) (Math.floor(Math.random() * 26));
            char c = (char) num;
            strKey.append(c);
        }
        key = String.valueOf(strKey);

        this.writeFile("key.txt", key);
    }

    public String getKey() {
        return key;
    }

    public void readKey(String fileName) {
        this.key = readFile(fileName);
    }

}
