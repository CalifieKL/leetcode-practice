package io.github.califiekl.lcpractice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HelperMethod {

    public static int[] getIntArray(int...args){
        List<Integer> input = new ArrayList<>();
        for(int arg: args)
            input.add(arg);
        return input.stream().mapToInt(i->i).toArray();
    }
    public static <T> T[] getArray(T...args){
        List<T> input = new ArrayList<>();
        for(T arg: args)
            input.add(arg);
        return (T[]) input.stream().map(i->i).toArray();
    }
    public static <T extends RuntimeException> void nullCheck(Object input, Function<String, T> exceptionCreator, String errorMessage) {
        if(input==null)
            throw exceptionCreator.apply(errorMessage);
    }

}
