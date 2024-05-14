package io.github.califiekl.lcpractice.textjustification;

import org.junit.Before;
import org.junit.Test;

public abstract class TestSolution {
    protected Solution solution;
    protected abstract Solution getSolution();

    @Before
    public void init(){
        solution = getSolution();
    }

    @Test
    public void exceptionCase1(){
        String words[] = new String[]{"This", "is", "an", "example", "of", "text", "justification."};
        solution.fullJustify(words, 16);
    }
}
