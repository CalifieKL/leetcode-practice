package io.github.califiekl.lcpractice.util;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class HelperMethod {

    public static int[] getIntArray(int...args){
        List<Integer> input = new ArrayList<>();
        for(int arg: args)
            input.add(arg);
        return input.stream().mapToInt(i->i).toArray();
    }
    public static int[] getIntArrayOfSize(int size, int defaultValue){
        int array[] = new int[size];
        Arrays.fill(array,0, size, defaultValue);
        return array;
    }
    public static <T extends RuntimeException> void nullCheck(Object input, Function<String, T> exceptionCreator, String errorMessage) {
        if(input==null)
            throw exceptionCreator.apply(errorMessage);
    }

    public static String getStringOfSize(int n){
        if(n<=0) return "";
        return CharBuffer.allocate(n).toString().replace('\0', ' ');
    }

}
