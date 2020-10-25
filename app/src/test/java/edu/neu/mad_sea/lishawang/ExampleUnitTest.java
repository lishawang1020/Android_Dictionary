package edu.neu.mad_sea.lishawang;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void insert_test() {
        DictionaryTrie dt = new DictionaryTrie();
        dt.insert("aab");
        dt.insert("aac");
        dt.insert("aad");
        dt.insert("aae");
        dt.insert("bbc");

        dt.predictUnderscore("aabbcde", "__c" );
    }
}