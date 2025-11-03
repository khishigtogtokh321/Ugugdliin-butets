package dataStructures;

public class ChangeArrayLength {
    public static Object[] changeLength1D(Object[] a, int newLength) {
        Object[] newArray = new Object[newLength];
        int length = Math.min(a.length, newLength);
        for (int i = 0; i < length; i++)
            newArray[i] = a[i];
        return newArray;
    }
}