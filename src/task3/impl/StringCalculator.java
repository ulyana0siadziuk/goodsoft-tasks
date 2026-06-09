package task3.impl;

import task3.operation.Calculator;

public class StringCalculator implements Calculator<String> {

    @Override
    public String add(String a, String b) {
        return a + b;
    }

    @Override
    public String subtract(String a, String b) {
        return a.replace(b, "");
    }

    @Override
    public String multiply(String a, String b) {
        StringBuilder result = new StringBuilder();
        int times = b.length();
        for (int i = 0; i < times; i++) {
            result.append(a);
        }
        return result.toString();
    }

    @Override
    public String divide(String a, String b) {
        int index = a.indexOf(b);
        if (index == -1) {
            return a;
        }
        return a.substring(0, index);
    }
}
