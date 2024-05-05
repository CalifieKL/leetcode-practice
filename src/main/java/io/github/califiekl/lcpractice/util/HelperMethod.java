package io.github.califiekl.lcpractice.util;

import java.util.ArrayList;
import java.util.List;

public class HelperMethod {
    public static int[] getIntArray(Integer... args){
        List<Integer> input = new ArrayList<>();
        for(Integer arg: args)
            input.add(arg);
        return input.stream().mapToInt(i->i).toArray();
    }
}
