package com.example.gpstracker.util;

import androidx.annotation.NonNull;

public class TimeConverter {
    @NonNull
    public static String convert(long secondsCount) {
        long [] values = new long[4];

        values[0] = secondsCount / 86400;
        values[1] = (secondsCount % 86400) / 3600;
        values[2] = ((secondsCount % 86400) % 3600) / 60;
        values[3] = ((secondsCount % 86400) % 3600) % 60;

        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            if (!flag) {
                if (values[i] > 0) {
                    if (values[i] < 10) {
                        stringBuilder.append("0");
                    }
                    stringBuilder.append(values[i]);
                    if (i < 3) {
                        stringBuilder.append(":");
                    }
                    flag = true;
                }
                else {
                    if (i >= 2) {
                        if (values[i] < 10) {
                            stringBuilder.append("0");
                        }
                        stringBuilder.append(values[i]);
                        stringBuilder.append(":");
                    }
                }
            }
            else {
                if (values[i] < 10) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(values[i]);
                if (i < 3) {
                    stringBuilder.append(":");
                }
            }
        }
        return stringBuilder.toString();
    }
}