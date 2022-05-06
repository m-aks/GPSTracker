package com.example.gpstracker.util;

public class DistanceConverter {
    public static String convert(long distance) {
        if (distance >= 1000L) {
            String result = Double.toString(distance / 1000.0);
            return result.substring(0, result.indexOf('.') + 3);
        }
        else {
            return Long.toString(distance);
        }
    }
}
