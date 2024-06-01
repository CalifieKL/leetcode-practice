package io.github.califiekl.lcpractice.minwindowsubstr;

public class SolutionOptimal implements Solution{
    @Override
    public String minWindow(String s, String t) {
        if(t.length()>s.length()) return "";
        int wStart=0, wEnd=0, minLen=Integer.MAX_VALUE, startInd=0;
        int numberOfTargetCharUnmatched=t.length();

        int targetCharMap[]= new int[128];
        for(char c: t.toCharArray())
            targetCharMap[c]++;

        char sourceCharArr[] = s.toCharArray();
        while(wEnd<sourceCharArr.length){
            if(targetCharMap[sourceCharArr[wEnd++]]-->0){
                 numberOfTargetCharUnmatched--;
            }
            while(numberOfTargetCharUnmatched==0){
                if(wEnd-wStart<minLen){
                    minLen = wEnd-wStart;
                    startInd = wStart;
                }
                if(targetCharMap[sourceCharArr[wStart++]]++==0) {
                    numberOfTargetCharUnmatched++;
                }
            }
        }

        return minLen==Integer.MAX_VALUE? "": s.substring(startInd, startInd+minLen);
    }
}
