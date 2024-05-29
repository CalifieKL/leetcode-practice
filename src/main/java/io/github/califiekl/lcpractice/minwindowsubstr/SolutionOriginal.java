package io.github.califiekl.lcpractice.minwindowsubstr;

import java.util.HashMap;
import java.util.Map;

public class SolutionOriginal implements Solution{
    @Override
    public String minWindow(String s, String t) {
        int slen = s.length();
        int tlen = t.length();
        if (tlen > slen) return "";

        Map<Character, Integer> tCharCount = getTargetCharCount(t);
        Map<Character, Integer> sCharCount = new HashMap<>();

        int wStart=0, wEnd=0;
        int[] minWin = new int[]{0,Integer.MAX_VALUE};
        StringBuilder sb=new StringBuilder();
        while (wEnd<slen){
            boolean wStartIncremented=false;
            while(shouldIncrementWStart(sCharCount, tCharCount)){
                if(minWin[1]-minWin[0]>wEnd-wStart){
                    minWin[0]=wStart;
                    minWin[1]=wEnd;
                }
                Character wStartChar = s.charAt(wStart);
                if(tCharCount.get(wStartChar)!=null)
                    sCharCount.put(wStartChar, sCharCount.get(wStartChar)-1);
                wStart++;
                wStartIncremented=true;
            }
            if(wStartIncremented){
                wStart--;
                Character wStartChar = s.charAt(wStart);
                if(tCharCount.get(wStartChar)!=null)
                    sCharCount.put(wStartChar, sCharCount.get(wStartChar)+1);
            }
            Character wEndChar = s.charAt(wEnd);
            if(tCharCount.get(wEndChar)!=null){
                if(sCharCount.get(wEndChar)==null){
                    sCharCount.put(wEndChar, 1);
                }else sCharCount.put(wEndChar, sCharCount.get(wEndChar)+1);
            }
            wEnd++;
        }
        while(shouldIncrementWStart(sCharCount, tCharCount)){
            if(minWin[1]-minWin[0]>wEnd-wStart){
                minWin[0]=wStart;
                minWin[1]=wEnd;
            }
            Character wStartChar = s.charAt(wStart);
            if(tCharCount.get(wStartChar)!=null)
                sCharCount.put(wStartChar, sCharCount.get(wStartChar)-1);
            wStart++;
        }
        if(minWin[1]==Integer.MAX_VALUE) return "";
        return s.substring(minWin[0], minWin[1]);
    }

    private Map<Character, Integer> getTargetCharCount(String t){
        Map<Character, Integer> charCount = new HashMap<>();
        for(int i=0; i<t.length();++i){
            Character ch=t.charAt(i);
            if(charCount.get(ch)==null)
                charCount.put(ch, 1);
            else charCount.put(ch, charCount.get(ch)+1);
        }
        return charCount;
    }

    private boolean shouldIncrementWStart(Map<Character, Integer> sCharCount, Map<Character, Integer> tCharCount){
        for(Character key: tCharCount.keySet()){
            Integer sCount = sCharCount.get(key);
            if(sCount==null) return false;
            Integer tCount = tCharCount.get(key);
            if(sCount<tCount) return false;
        }
        return true;
    }
}
