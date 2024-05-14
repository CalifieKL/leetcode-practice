package io.github.califiekl.lcpractice.textjustification;

import java.util.ArrayList;
import java.util.List;

public class SolutionOriginal implements Solution{
    @Override
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        if(words.length==1){
            String justified = words[0]+getWhiteSpaceOfSize(maxWidth-words[0].length());
            result.add(justified);
            return result;
        }

        int cummulativeRowLength;
        int cummulativeWordLength;
        for(int i=0;i<words.length;){
            cummulativeRowLength=0;
            cummulativeWordLength=0;
            for(int j=i;j<words.length;++j){
                if(words[j].length()+cummulativeRowLength<maxWidth-1){
                    cummulativeRowLength+=(words[j].length()+1);
                    cummulativeWordLength+=words[j].length();
                    if(j==words.length-1){
                        result.add(getLastRow(i, j, maxWidth-cummulativeWordLength, words));
                        i=j+1;
                        break;
                    }
                }else if(words[j].length()+cummulativeRowLength==maxWidth-1
                        ||words[j].length()+cummulativeRowLength==maxWidth){
                    if(j==words.length-1){
                        result.add(getLastRow(i, j, maxWidth-words[j].length()-cummulativeWordLength, words));
                    }else{
                        result.add(getRow(i, j, maxWidth-words[j].length()-cummulativeWordLength, words));
                    }
                    i=j+1;
                    break;
                }else{
                    result.add(getRow(i, j-1, maxWidth-cummulativeWordLength, words));
                    i=j;
                    break;
                }
            }
        }
        return result;
    }

    private String getLastRow(int start, int end, int totalPadding, String[] words){
        int numOfWords = end-start+1;
        if(numOfWords==1) return words[start]+getWhiteSpaceOfSize(totalPadding);
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(words[start]);
        for(int i=1;i<numOfWords;++i){
            rowBuilder.append(" ");
            totalPadding--;
            rowBuilder.append(words[start+i]);
        }
        rowBuilder.append(getWhiteSpaceOfSize(totalPadding));
        return rowBuilder.toString();
    }

    private String getRow(int start, int end, int totalPadding, String[] words){
        int numOfWords = end-start+1;
        if(numOfWords==1) return words[start]+getWhiteSpaceOfSize(totalPadding);
        StringBuilder rowBuilder = new StringBuilder();
        int numOfPaddings = numOfWords-1;
        int sizeOfPadding = totalPadding/numOfPaddings;
        int extraAllocation = totalPadding%numOfPaddings;
        rowBuilder.append(words[start]);
        for(int i=1;i<numOfWords;++i){
            if(i<=extraAllocation)
                rowBuilder.append(getWhiteSpaceOfSize(sizeOfPadding+1));
            else rowBuilder.append(getWhiteSpaceOfSize(sizeOfPadding));
            rowBuilder.append(words[start+i]);
        }
        return rowBuilder.toString();
    }

    private String getWhiteSpaceOfSize(int n){
        return new String(new char[n]).replace('\0', ' ');
    }
}
