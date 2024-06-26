package io.github.califiekl.lcpractice.substrpermutation;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class TestSolution {
    protected Solution solution;
    protected abstract Solution getSolution();

    @Before
    public void init(){
        solution = getSolution();
    }

    @Test
    public void testCase1(){
        assertTwoSortedListsAreEqual(Arrays.asList(new Integer[]{0,9}),
                solution.findSubstring("barfoothefoobarman", new String[]{"foo","bar"}));
    }

    @Test
    public void testCase2(){
        assertTwoSortedListsAreEqual(Arrays.asList(new Integer[]{6,9,12}),
                solution.findSubstring("barfoofoobarthefoobarman", new String[]{"bar","foo","the"}));
    }

    @Test
    public void testCase3(){
        assertTwoSortedListsAreEqual(Arrays.asList(new Integer[]{}),
                solution.findSubstring("wordgoodgoodgoodbestword", new String[]{"word","good","best","word"}));
    }

    @Test
    public void exceptionCase1(){
        assertTwoSortedListsAreEqual(Arrays.asList(new Integer[]{0}),
                solution.findSubstring("ababababab", new String[]{"ababa","babab"}));
    }

    private void assertTwoSortedListsAreEqual(List<Integer> expected, List<Integer> result) {
        assertEquals(expected.size(), result.size());
        for(int i=0;i<expected.size();++i)
            assertEquals(expected.get(i), result.get(i));
    }
}
