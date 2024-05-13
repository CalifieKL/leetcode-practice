package io.github.califiekl.lcpractice.firstoccurrence;

public class SolutionCleaner implements Solution{
    @Override
    public int strStr(String haystack, String needle) {
        for(int i=0, j=needle.length(); i+needle.length()<=haystack.length(); ++i,++j){
            if(haystack.substring(i,j).equals(needle)) return i;
        }
        return -1;
    }
}
