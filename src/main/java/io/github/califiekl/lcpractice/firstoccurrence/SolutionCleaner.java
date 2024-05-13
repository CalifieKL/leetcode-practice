package io.github.califiekl.lcpractice.firstoccurrence;

import io.github.califiekl.lcpractice.util.HelperMethod;

public class SolutionCleaner implements Solution{
    @Override
    public int strStr(String haystack, String needle) {
        HelperMethod.nullCheck(haystack, FirstOccurrenceException::new, "hay stack is null");
        HelperMethod.nullCheck(needle, FirstOccurrenceException::new, "needle is null");
        for(int i=0, j=needle.length(); i+needle.length()<=haystack.length(); ++i,++j){
            if(haystack.substring(i,j).equals(needle)) return i;
        }
        return -1;
    }
}
