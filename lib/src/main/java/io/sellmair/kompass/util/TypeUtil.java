package io.sellmair.kompass.util;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
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

    public static float[] toFloatArray(List<Float> list) {
        if (list == null) return new float[0];
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }


    public static List<Float> toFloatList(float[] array) {
        List<Float> list = new ArrayList<>(array.length);
        for (float f : array) {
            list.add(f);
        }

        return list;
    }

    public static double[] toDoubleArray(List<Double> list) {
        if (list == null) return new double[0];
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static List<Double> toDoubleList(double[] array) {
        if (array == null) return new ArrayList<>();
        ArrayList<Double> list = new ArrayList<>();
        for (double d : array) {
            list.add(d);
        }
        return list;
    }

    public static char[] toCharArray(List<Character> list) {
        if (list == null) return new char[0];
        char[] array = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static List<Character> toCharacterList(char[] array) {
        if (array == null) return new ArrayList<Character>();
        List<Character> list = new ArrayList<>(array.length);
        for (char c : array) {
            list.add(c);
        }

        return list;
    }

    public static boolean[] toBooleanArray(List<Boolean> list) {
        if (list == null) return new boolean[0];
        boolean[] array = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static List<Boolean> toBooleanList(boolean[] array) {
        if (array == null) return new ArrayList<>();
        List<Boolean> list = new ArrayList<>(array.length);
        for (boolean b : array) {
            list.add(b);
        }

        return list;
    }

    public static long[] toLongArray(List<Long> list) {
        if (list == null) return new long[0];
        long[] array = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static List<Long> toLongList(long[] array) {
        if (array == null) return new ArrayList<>();
        List<Long> list = new ArrayList<>(array.length);
        for (long l : array) {
            list.add(l);
        }

        return list;
    }

    public static short[] toShortArray(List<Short> list) {
        if (list == null) return new short[0];
        short[] array = new short[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static List<Short> toShortList(short[] array) {
        if (array == null) return new ArrayList<>();
        List<Short> list = new ArrayList<>(array.length);
        for (int i = 0; i < list.size(); i++) {
            list.add(array[i]);
        }

        return list;
    }

    public static ArrayList<String> toArrayList(List<String> list) {
        if (list instanceof ArrayList) return (ArrayList<String>) list;
        ArrayList<String> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }

    public static ArrayList<String> toArrayList(String[] array) {
        ArrayList<String> arrayList = new ArrayList<>(array.length);
        Collections.addAll(arrayList, array);
        return arrayList;
    }

    public static ArrayList<Parcelable> toParcelableArrayList(List<? extends Parcelable> list) {
        ArrayList<Parcelable> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
