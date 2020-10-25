package edu.neu.mad_sea.lishawang;

public class DictionaryNode {
    private char letter;
    public DictionaryNode left;
    public DictionaryNode right;
    public DictionaryNode middle;
    boolean end;

    DictionaryNode(char c) {
        letter = c;
        left = null;
        right = null;
        middle = null;
        end = false;
    }

    public void setLetter(char c) {
        letter = c;
    }
    public char getLetter() {
        return letter;
    }
}
