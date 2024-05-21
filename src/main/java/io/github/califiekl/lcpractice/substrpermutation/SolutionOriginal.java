package io.github.califiekl.lcpractice.substrpermutation;

import java.util.*;

public class SolutionOriginal implements Solution{
    @Override
    public List<Integer> findSubstring(String s, String[] words) {
        Arrays.sort(words);

        int wStart=0, wEnd = getTotalCharCount(words)-1;
        List<Integer> result = new ArrayList<>();
        while(wEnd<s.length()){
            if(isSliceAConcat(s, wStart, wEnd, words))
                result.add(wStart);
            wStart++;
            wEnd++;
        }
        return result;
    }

    private int getTotalCharCount(String[] words){
        int count=0;
        for(String word: words)
            count+=word.length();
        return count;
    }

    private boolean isSliceAConcat(String s, int start, int end, String[] words){
        String slice = s.substring(start,end+1);
        String pieces[] = new String[words.length];
        int wordSize=words[0].length();
        for(int i=0;i+wordSize<=slice.length();i+=wordSize)
            pieces[i/wordSize]=slice.substring(i,i+wordSize);
        if(pieces.length!=words.length) return false;
        Arrays.sort(pieces);
        for(int i=0;i<words.length;++i){
            if(!words[i].equals(pieces[i])) return false;
        }
        return true;
    }
}
