package io.github.califiekl.lcpractice.firstoccurrence;

public class SolutionOriginal implements Solution{
    @Override
    public int strStr(String haystack, String needle) {
        int needleLength = needle.length();
        int haystackLength = haystack.length();
        for(int i=0; i<haystackLength;++i){
            if(i+needleLength>haystackLength) return -1;
            for(int j=0;j<needleLength;++j){
                if(haystack.charAt(i+j)!=needle.charAt(j)) break;
                else if(j==needleLength-1) return i;
            }
        }
        return -1;
    }
}
