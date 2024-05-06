package io.github.califiekl.lcpractice.hindex;

import io.github.califiekl.lcpractice.util.HelperMethod;

public class SolutionOptimal implements Solution{
    @Override
    public int hIndex(int[] citations) {
        HelperMethod.nullCheck(citations,HIndexException::new, "cannot calculate h-index with null citations");
        int totalPublicationCount = citations.length;
        int[] citationCountBucket = HelperMethod.getIntArrayOfSize(totalPublicationCount+1, 0);
        for(int i = 0; i < totalPublicationCount; ++i){
            if(citations[i]>totalPublicationCount) citationCountBucket[totalPublicationCount]++;
            else citationCountBucket[citations[i]]++;
        }
        int citedPublicationCount = 0;
        for(int j = totalPublicationCount; j >= 0; --j){
            citedPublicationCount+=citationCountBucket[j];
            if(citedPublicationCount>=j) return j;
        }
        return 0;
    }
}
