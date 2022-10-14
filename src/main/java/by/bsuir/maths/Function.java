package by.bsuir.maths;

import java.util.ArrayList;
import java.util.List;

public class Function {

    private final int n;

    private final double phase;

    public Function(int n, double phase) {
        this.n = n;
        this.phase = phase;
    }

    public List<Double> calculateFunction(int m) {
        List<Double> values = new ArrayList<>(m + 1);
        for (int i = 0; i <= m; i++) {
            double value = Math.sin(2 * Math.PI * i / n + phase);
            values.add(value);
        }
        return values;
    }
}
