package edu.neu.mad_sea.lishawang;


import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class DictionaryTrie {
    private DictionaryNode root;

    DictionaryTrie() {
        root = null;
    }

    public boolean insert(String word) {
        if (word.length() == 0) {
            return false;
        }

        int cnt = 0;
        DictionaryNode curNode;

        // create a new root node if the tree is empty
        if (root == null) {
            root = new DictionaryNode(word.charAt(0));
            curNode = root;
            if (cnt == word.length() - 1) {
                curNode.end = true;
            }
            cnt++;
        } else {
            curNode = root;
            boolean exitLoop = false;
            while (exitLoop == false) {

                // insert the letter node to the left of the current
                // node if the letter is smaller than the letter in the node
                if (word.charAt(cnt) < curNode.getLetter()) {
                    if (curNode.left == null) {
                        curNode.left = new DictionaryNode(word.charAt(cnt));
                        curNode = curNode.left;
                        if (cnt == word.length() - 1) {
                            curNode.end = true;
                        }
                        cnt++;
                        exitLoop = true;
                    } else {
                        curNode = curNode.left;
                    }
                }

                // insert the letter node to the right of the
                // current node if the letter is larger than the letter in the
                // node
                else if (word.charAt(cnt) > curNode.getLetter()) {
                    if (curNode.right == null) {
                        curNode.right = new DictionaryNode(word.charAt(cnt));
                        curNode = curNode.right;
                        if (cnt == word.length() - 1) {
                            curNode.end = true;
                        }
                        cnt++;
                        exitLoop = true;
                    } else {
                        curNode = curNode.right;
                    }
                }

                // insert the letter node to the middle of the
                // current node if the letter is neither larger or smaller than
                // the current node
                else {
                    if (curNode.middle == null) {
                        if (cnt == word.length() - 1) {
                            // return false if the word is already inserted
                            if (curNode.end == true) {
                                return false;
                            }
                        } else {
                            cnt++;
                            exitLoop = true;
                        }
                    } else {
                        if (cnt == word.length() - 1) {
                            if (curNode.end = true) {
                                return false;
                            } else {
                                curNode.end = true;
                                return true;
                            }
                        } else {
                            curNode = curNode.middle;
                            cnt++;
                        }
                    }
                }
            }
        }

        // insert the rest of the word to the middle of the initial letter
        for (int i = cnt; i < word.length(); i++) {
            curNode.middle = new DictionaryNode(word.charAt(i));
            curNode = curNode.middle;
            if (i == word.length() - 1) {
                curNode.end = true;
            }
        }
        // return true if the word is inserted successfully
        return true;
    }

    public ArrayList<String> predictCompletions(String prefix) {
        int cnt = 0;
        DictionaryNode curNode = root;
        boolean exitLoop = false;
        ArrayList<String> list = new ArrayList<String>();

        if (root == null || prefix.length() == 0) {
            return list;
        }

        while (exitLoop == false) {

            if (prefix.charAt(cnt) < curNode.getLetter()) {
                if (curNode.left == null) {
                    return list;
                } else {
                    curNode = curNode.left;
                }
            } else if (prefix.charAt(cnt) > curNode.getLetter()) {
                if (curNode.right == null) {
                    return list;
                } else {
                    curNode = curNode.right;
                }
            } else {
                if (cnt == prefix.length() - 1) {
                    exitLoop = true;
                } else {
                    if (curNode.middle == null) {
                        return list;
                    } else {
                        curNode = curNode.middle;
                        cnt++;
                    }
                }
            }
        }

        // add the prefix to the list if itself is a word
        if (curNode.end == true) {
            list.add(prefix);
        }

        buildString(curNode.middle, prefix, list);

        return list;
    }

    private void buildString(DictionaryNode bufferNode, String str,
                             ArrayList<String> list) {
        if (bufferNode != null) {
            // if reaches the end of a word, push it to the priority queue
            if (bufferNode.end == true) {
                list.add(str + bufferNode.getLetter());
            }

            // recursively build the word by going through the left, middle and right child of the node
            buildString(bufferNode.left, str, list);
            buildString(bufferNode.middle, str + bufferNode.getLetter(), list);
            buildString(bufferNode.right, str, list);
        }
    }

    public ArrayList<String> predictUnderscore(String characters, String pattern) {

        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < characters.length(); i++) {
            if (map.containsKey(characters.charAt(i))) {
                map.put(characters.charAt(i), map.get(characters.charAt(i)) + 1);
            } else {
                map.put(characters.charAt(i), 1);
            }
        }

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Pair<Integer, Character>> letters = new ArrayList<Pair<Integer, Character>>();
        if (root == null && pattern.length() == 0) {
            return list;
        }

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != '_') {
                Pair<Integer, Character> lttr = Pair.create(i, pattern.charAt(i));
                letters.add(lttr);
            }
        }

        backtrack(root, "", 0, pattern.length() - 1, list, letters, map);

        return list;
    }

    private void backtrack(DictionaryNode curNode, String curString, int curIndex, int maxLen,
                           ArrayList<String> list, ArrayList<Pair<Integer, Character>> letters,
                           HashMap<Character, Integer> map) {
        if (curNode == null) {
            return;
        }

        for (int i = 0; i < letters.size(); i++) {
            if (curIndex == letters.get(i).first) {
                if (curNode.getLetter() != letters.get(i).second) {
                    backtrack(curNode.left, curString, curIndex, maxLen, list, letters, map);
                    backtrack(curNode.right, curString, curIndex, maxLen, list, letters, map);
                    return;
                }
            }
        }

        if (curIndex == maxLen) {
            if (curNode.end == true) {
                if (checkCharacters(map, curString + curNode.getLetter())) {
                    list.add(curString + curNode.getLetter());
                }
            }
        }

        backtrack(curNode.left, curString, curIndex, maxLen, list, letters, map);
        String newString = curString + curNode.getLetter();
        backtrack(curNode.middle, newString, curIndex + 1, maxLen, list, letters, map);
        backtrack(curNode.right, curString, curIndex, maxLen, list, letters, map);
    }

    private boolean checkCharacters(HashMap<Character, Integer> map, String word) {
        HashMap<Character, Integer> myMap = new HashMap<Character, Integer>();
        myMap.putAll(map);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!myMap.containsKey(c)) {
                return false;
            } else {
                myMap.put(c, myMap.get(c) - 1);
                if (myMap.get(c) <= 0) {
                    myMap.remove(c);
                }
            }
        }
        return true;
    }


}
