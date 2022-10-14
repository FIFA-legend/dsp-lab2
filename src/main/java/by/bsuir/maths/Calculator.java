package by.bsuir.maths;

import java.util.List;

public class Calculator {

    private final int m;

    private final List<Double> values;

    public Calculator(int m, List<Double> values) {
        this.m = m;
        this.values = values;
    }

    public double rmsValueWay1() {
        double sum = 0;
        for (double value : values) {
            sum += Math.pow(value, 2);
        }
        return Math.sqrt(sum / (m + 1));
    }

    public double rmsValueWay2() {
        double singleSum = 0;
        double squareSum = 0;
        for (double value : values) {
            singleSum += value;
            squareSum += Math.pow(value, 2);
        }

        double leftValue = squareSum / (m + 1);
        double rightValue = Math.pow(singleSum / (m + 1), 2);
        return Math.sqrt(leftValue - rightValue);
    }

    public double amplitudeValue() {
        double a = 0;
        double b = 0;
        for (int i = 0; i <= m; i++) {
            a += values.get(i) * Math.cos(2 * Math.PI * i / m);
            b += values.get(i) * Math.sin(2 * Math.PI * i / m);
        }
        a = a * 2 / m;
        b = b * 2 / m;
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}
