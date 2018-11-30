package cn.hurrican.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/11/27
 * @Modified 15:53
 */

public class CommonUtils {

    public static <T> List<T> getRandomSublist(List<T> src, int length) {
        if (length <= 0) {
            return new ArrayList<>();
        }

        List<T> item = new ArrayList<>();
        List<T> srcCopy = new ArrayList<>();
        if (src == null) {
            src = new ArrayList<>();
        }
        int size = src.size();
        src.forEach(srcCopy::add);
        if (length >= size) {
            item.addAll(srcCopy);
            return item;
        }
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(size);
            item.add(srcCopy.get(index));
            srcCopy.remove(index);
            size = srcCopy.size();
        }
        return item;
    }

    public static List<Integer> generateCode(int codeLen, int matchCount) {
        List<Integer> origin = IntStream.range(0, codeLen).boxed().collect(Collectors.toList());
        if(codeLen - 1 <= matchCount){
            return origin;
        }
        int diff = codeLen - matchCount;
        List<Integer> locationIndex = getRandomSublist(origin, diff);
        Random random = new Random();
        List<Integer> tmp;
        Set<Integer> removed = new HashSet<>(diff);
        boolean swap = false;
        for (int i = 0, size = locationIndex.size(); i < size; i++) {
            Integer location = locationIndex.get(i);
            tmp = new ArrayList<>(locationIndex);
            if(!swap){
                tmp.remove(location);
                swap = true;
            }
            tmp.removeAll(removed);
            Integer newVal = tmp.get(random.nextInt(tmp.size()));
            removed.add(newVal);
            origin.set(location, newVal);
        }
        return origin;
    }
}
