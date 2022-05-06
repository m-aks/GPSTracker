package com.example.gpstracker.util;

public class SpeedConverter {
    public static String convert(float speed) {
        String result = Float.toString(speed);
        try {
            result = result.substring(0, result.indexOf('.') + 3);
        }
        catch (IndexOutOfBoundsException exception) {
            exception.printStackTrace();
        }
        return  result;
    }
}
