package io.sellmair.kompass.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastiansellmair on 10.12.17.
 */

public class TypeUtil {
    public static int[] toIntArray(List<Integer> list) {
        if (list == null) return new int[0];
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static List<Integer> toIntegerList(int[] array) {
        List<Integer> list = new ArrayList<>(array.length);
        for (int i = 0; i < array.length; i++) {
            list.add(i, array[i]);
        }

        return list;
    }
}
