package com.example.loginregister.util;

import android.content.Context;

import com.example.loginregister.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linchpinub4 on 22/1/16.
 */
public class MPUtil {

    private static int textSize = 12;

    public static ArrayList<BarDataSet> getDataSet(Context context, List<Float> dataList) {

        ArrayList<BarDataSet> dataSets = new ArrayList<>();

        ArrayList<BarEntry> valueSet = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            valueSet.add(new BarEntry(dataList.get(i), i));
        }
        int[] colors = {context.getResources().getColor(R.color.color4),
                context.getResources().getColor(R.color.light_blue_400),
                context.getResources().getColor(R.color.light_blue_400),
                context.getResources().getColor(R.color.color)};

        BarDataSet barDataSet = new MyBarDataSet(valueSet, "");
        barDataSet.setColors(colors);
        barDataSet.setValueTextSize(textSize);
        barDataSet.setBarSpacePercent(40);

        dataSets.add(barDataSet);

        return dataSets;
    }

    public static ArrayList<String> getXAxisValues(int dataSize) {

        ArrayList<String> xAxis = new ArrayList<>();

        for (int i = 0; i < dataSize; i++) {
            xAxis.add(String.valueOf(i + 1));
        }
        return xAxis;
    }


    public static void drawChart(final Context context, BarChart chart, BarData data) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineWidth(2);  // x ashix line size
        xAxis.setAxisLineColor(context.getResources().getColor(R.color.color6));
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(textSize);
        xAxis.setTextColor(context.getResources().getColor(R.color.color6));

        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        yAxis.setTextSize(textSize);
        yAxis.setTextColor(context.getResources().getColor(R.color.color6));
        yAxis.setAxisLineColor(context.getResources().getColor(R.color.color6));
        yAxis.setAxisLineWidth(2);  // y ashix line size

        data.setValueTextColor(context.getResources().getColor(R.color.color6)); // bar data level
        data.setValueTextSize(12); // bar data size

        // bar data size

        chart.setData(data);
        chart.setDescription("");
        chart.invalidate();
        chart.setHorizontalScrollBarEnabled(true);
        chart.setVisibleXRange(8);
        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDoubleTapToZoomEnabled(false);

    }

}
