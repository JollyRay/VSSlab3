package ru.ray.jolly.UI;

import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import ru.ray.jolly.Accountant.Functions;
import ru.ray.jolly.Accountant.Newton;

import java.awt.*;
import java.util.LinkedList;

public class GraphMaker {
    private GraphMaker(){}

    public static JFreeChart getChart(XYDataset dataset){
        JFreeChart chart = ChartFactory.createXYLineChart(
                "", "X", "Y", dataset,
                PlotOrientation.VERTICAL, true, false, false);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(200, 200, 200));
        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);

        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
        ValueAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible (false);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint  (0, Color.red);
        renderer.setSeriesStroke (0, new BasicStroke(1f));

        renderer.setSeriesPaint  (1, Color.blue);
        renderer.setSeriesShapesVisible(1, false);

        plot.setRenderer(renderer);

        return chart;
    }

    public static XYDataset getDataset(double min, double max, int id,  Pair<Double, Double> point){
        XYSeries series1 = getStartSeries(min, max, id);

        XYSeries series2 = new XYSeries("Point");
        series2.add(point.getKey(), point.getValue());

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series2);
        dataset.addSeries(series1);
        return dataset;
    }

    public static XYDataset getDataset(double min, double max, int id, LinkedList<Pair<Double, Double>> list) {
        XYSeries series1 = getStartSeries(min, max, id);
        XYSeries series2 = new XYSeries("Inter Function");
        long countPoint = getCountPoint(min, max);
        Newton.fillDiff(list);
        for (int i = 0; i <= countPoint; i++){
            double x = i*(max-min)/countPoint+min;
            double value = Newton.getPol(x, list);
            series2.add(x, value);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series2);
        dataset.addSeries(series1);
        return dataset;
    }

    private static XYSeries getStartSeries(double min, double max, int id){
        XYSeries series = new XYSeries("Base Function");
        long countPoint = getCountPoint(min, max);
        double value;
        for (int i =  0; i <= countPoint; i++ ) {
            double x = i*(max-min)/countPoint+min;
            value = Functions.valueFun(id, x);
            series.add(x, value);
        }
        return series;
    }

    private static long getCountPoint(double min, double max){
        long count = Math.round(max - min) * 5;
        if (count > 100)
            return count;
        return 100;
    }
}
