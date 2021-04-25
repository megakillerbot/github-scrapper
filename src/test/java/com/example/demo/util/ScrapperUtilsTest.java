package com.example.demo.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrapperUtilsTest {

    private static final String FILE_PATH = "/username/repo/blob/pizza.exe";
    private static final String DIR_PATH = "/username/repo/tree/pizza-box";
    private static final String CONTROL_PHRASE = "The pizza is in the pizza box.";
    private static final String KEYWORD = "pizza";

    @Test
    public void isFile(){
        Assert.assertTrue(ScrapperUtils.isFile(FILE_PATH));
    }

    @Test
    public void isDir(){
        Assert.assertFalse(ScrapperUtils.isFile(DIR_PATH));
    }

    @Test
    public void findFirst(){
        Assert.assertEquals(4, ScrapperUtils.findFirst(CONTROL_PHRASE, 0, KEYWORD));
        Assert.assertEquals(20, ScrapperUtils.findFirst(CONTROL_PHRASE, 10, KEYWORD));
    }

    @Test
    public void findMatches(){
        List<Integer> expected = Arrays.asList(4, 20);
        List<Integer> found = ScrapperUtils.findMatches(CONTROL_PHRASE, KEYWORD);
        Assert.assertEquals(expected, found);
    }

    @Test
    public void findMatchesEmpty(){
        String word = "pineapple";
        List<Integer> found = ScrapperUtils.findMatches(CONTROL_PHRASE, word);
        Assert.assertEquals(Collections.emptyList(), found);
    }

    @Test
    public void findMatchesEmptyKey(){
        List<Integer> found = ScrapperUtils.findMatches(CONTROL_PHRASE,"");
        Assert.assertEquals(Collections.emptyList(), found);
    }

    @Test
    public void getExtension(){
        String found = ScrapperUtils.getExtension(FILE_PATH);
        Assert.assertEquals("exe", found);
    }

}