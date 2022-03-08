package cripto;

import cripto.FileHandling;

public class PlainText extends FileHandling {
    private String plainText;

    PlainText() {
        plainText = "";
    }

    public String filterText(String text) {
        String filteredText = "";
        filteredText = text.replaceAll("[0-9!@#$%^&*().,;:'\"/ ]", "");
        return filteredText;
    }

    public void readText(String fileName) {
        String text = readFile(fileName);
        this.plainText = this.filterText(text);
    }

    public String getPlainText() {
        return plainText;
    }
}
