/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodeus.analysis.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import ch.ethz.idsc.tensor.img.ColorDataIndexed;

public enum CompositionStack {
    ;

    public static final int WIDTH = 700; /* Width of the image */
    public static final int HEIGHT = 125; /* Height of the image */

    public static void of(File directory, String fileTitle, String diagramTitle, //
            double[] values, String[] labels, ColorDataIndexed colorDataIndexed) throws Exception {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < labels.length; ++i) {
            dataset.addValue(values[i], labels[i], "s1");
        }

        JFreeChart chart = ChartFactory.createStackedBarChart(diagramTitle, "", "", dataset, PlotOrientation.HORIZONTAL, false, false, false);

        chart.setBackgroundPaint(Color.white);
        chart.getCategoryPlot().getRangeAxis().setRange(0, 1.0);
        chart.getCategoryPlot().setRangeGridlinePaint(Color.lightGray);
        chart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);
        chart.getCategoryPlot().getDomainAxis().setLowerMargin(0.0);
        chart.getCategoryPlot().getDomainAxis().setUpperMargin(0.0);
        // chart.getCategoryPlot().getRangeAxis().setTickLabelFont(DiagramSettings.FONT_TICK);

        StackedBarRenderer renderer = new StackedBarRenderer();
        renderer.setDrawBarOutline(true);

        chart.getCategoryPlot().setRenderer(renderer);

        // Adapt colors & style
        for (int i = 0; i < labels.length; i++) {
            chart.getCategoryPlot().getRenderer().setSeriesPaint(i, colorDataIndexed.getColor(i));
            chart.getCategoryPlot().getRenderer().setSeriesOutlinePaint(i, colorDataIndexed.getColor(i).darker());
            chart.getCategoryPlot().getRenderer().setSeriesOutlineStroke(i, new BasicStroke(1.0f));
        }

        // chart.getTitle().setFont(DiagramSettings.FONT_TITLE);

        LegendTitle legend = new LegendTitle(chart.getCategoryPlot().getRenderer());
        // legend.setItemFont(DiagramSettings.FONT_TICK);
        legend.setPosition(RectangleEdge.TOP);
        chart.addLegend(legend);

        StaticHelper.savePlot(directory, fileTitle, chart, WIDTH, HEIGHT);
    }

}
