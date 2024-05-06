package io.github.califiekl.lcpractice.hindex;

import io.github.califiekl.lcpractice.util.HelperMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SolutionOriginal implements Solution{

    @Override
    public int hIndex(int[] citations) {
        HelperMethod.nullCheck(citations,HIndexException::new, "cannot calculate h-index with null citations");
        List<Integer> boxedCitations = new ArrayList<>();
        for(int citation: citations)
            boxedCitations.add(citation);
        Collections.sort(boxedCitations, new Comparator<Integer>(){
            @Override
            public int compare(Integer int1, Integer int2){
                return int2-int1;
            }
        });
        int hIndex = 0;
        for(int i=0;i<citations.length;++i){
            if(boxedCitations.get(i)<i+1) break;
            hIndex++;
        }
        return hIndex;
    }
}
