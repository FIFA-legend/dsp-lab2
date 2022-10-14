package by.bsuir;

import by.bsuir.maths.Calculator;
import by.bsuir.maths.Function;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private RadioButton noPhaseRadio;

    @FXML
    private RadioButton phaseRadio;

    @FXML
    private ComboBox<Integer> nComboBox;

    @FXML
    private Button buildButton;

    @FXML
    private LineChart<String, Number> chart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    void initialize() {
        xAxis.setLabel("M");

        initializeN();

        buildButton.setOnAction(handler -> processButton());
    }

    private void processButton() {
        int n = nComboBox.getValue();
        int k = 3 * n / 4;
        double phase = noPhaseRadio.isSelected() ? 0 : Math.PI / 8;
        int inc = n / 32;

        Map<Integer, Double> rmsValues1 = new TreeMap<>();
        Map<Integer, Double> rmsValues2 = new TreeMap<>();
        Map<Integer, Double> amplitudeValues = new TreeMap<>();
        for (int i = k - 1 + inc; i < 2 * n; i += inc) {
            Function f = new Function(n, phase);
            List<Double> values = f.calculateFunction(i);

            Calculator calculator = new Calculator(i, values);
            rmsValues1.put(i, 0.707 - calculator.rmsValueWay1());
            rmsValues2.put(i, 0.707 - calculator.rmsValueWay2());
            amplitudeValues.put(i, 1 - calculator.amplitudeValue());
        }
        processChart(rmsValues1, rmsValues2, amplitudeValues);
    }

    private void processChart(Map<Integer, Double> rmsValues1, Map<Integer, Double> rmsValues2, Map<Integer, Double> amplitudeValues) {
        chart.getData().removeAll(chart.getData());

        XYChart.Series<String, Number> rmsSeries1 = new XYChart.Series<>();
        rmsSeries1.setName("Способ 1");
        rmsSeries1.getData().addAll(
                rmsValues1.entrySet().stream()
                        .map(entry -> new XYChart.Data<String, Number>(String.valueOf(entry.getKey()), entry.getValue()))
                        .collect(Collectors.toList())
        );

        XYChart.Series<String, Number> rmsSeries2 = new XYChart.Series<>();
        rmsSeries2.setName("Способ 2");
        rmsSeries2.getData().addAll(
                rmsValues2.entrySet().stream()
                        .map(entry -> new XYChart.Data<String, Number>(String.valueOf(entry.getKey()), entry.getValue()))
                        .collect(Collectors.toList())
        );

        XYChart.Series<String, Number> amplitudeSeries = new XYChart.Series<>();
        amplitudeSeries.setName("Амплитуда");
        amplitudeSeries.getData().addAll(
                amplitudeValues.entrySet().stream()
                        .map(entry -> new XYChart.Data<String, Number>(String.valueOf(entry.getKey()), entry.getValue()))
                        .collect(Collectors.toList())
        );

        chart.getData().addAll(List.of(rmsSeries1, rmsSeries2, amplitudeSeries));
        chart.getData().stream().parallel().forEach(series -> {
            series.getData().forEach(data -> {
                data.getNode().setScaleX(0.1);
                data.getNode().setScaleY(0.1);
            });
        });
    }

    private void initializeN() {
        nComboBox.getItems().addAll(64, 128, 256, 512, 1024, 2048, 4096);
        nComboBox.setValue(64);
    }

}
