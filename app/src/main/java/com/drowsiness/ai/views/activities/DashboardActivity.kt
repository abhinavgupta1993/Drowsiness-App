package com.drowsiness.ai.views.activities

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.drowsiness.ai.R
import com.drowsiness.ai.databinding.ActivityDashboardBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF


class DashboardActivity : AppCompatActivity(), OnChartValueSelectedListener {

    lateinit var dashboardBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        dashboardBinding.back.setOnClickListener{
            finish()
        }

        dashboardBinding.pieChart.setUsePercentValues(true)
        dashboardBinding.pieChart.description.isEnabled = false
        dashboardBinding.pieChart.setExtraOffsets(0f, 0f, 0f, 0f)

        dashboardBinding.pieChart.setDragDecelerationFrictionCoef(0.95f)

        // change when you do not need the hole
        dashboardBinding.pieChart.isDrawHoleEnabled = true
        dashboardBinding.pieChart.setHoleColor(Color.WHITE)

        dashboardBinding.pieChart.setTransparentCircleColor(Color.WHITE)
        dashboardBinding.pieChart.setTransparentCircleAlpha(110)

        dashboardBinding.pieChart.holeRadius = 58f
        dashboardBinding.pieChart.transparentCircleRadius = 61f

        dashboardBinding.pieChart.setDrawCenterText(false)
        dashboardBinding.pieChart.setRotationAngle(0f)
        // enable rotation of the dashboardBinding.pieChart by touch
        dashboardBinding.pieChart.isRotationEnabled = true
        dashboardBinding.pieChart.isHighlightPerTapEnabled = true

        dashboardBinding.pieChart.setOnChartValueSelectedListener(this)

        dashboardBinding.pieChart.animateY(2500, Easing.EaseInOutQuad)
        // dashboardBinding.pieChart.spin(2000, 0, 360);

        // dashboardBinding.pieChart.spin(2000, 0, 360);
//        val l: Legend = dashboardBinding.pieChart.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
//        l.orientation = Legend.LegendOrientation.VERTICAL
//        l.setDrawInside(false)
//        l.xEntrySpace = 7f
//        l.yEntrySpace = 0f
//        l.yOffset = 0f

        dashboardBinding.pieChart.legend.isEnabled = false

        dashboardBinding.pieChart.setEntryLabelColor(Color.WHITE)
//        dashboardBinding.pieChart.setEntryLabelTypeface(tfRegular)
        dashboardBinding.pieChart.setEntryLabelTextSize(12f)


//        dashboardBinding.btExport.setOnClickListener {
            val entries = ArrayList<PieEntry>()
            entries.add(PieEntry(35F))
            entries.add(PieEntry(40f))
            entries.add(PieEntry(25f))
            val dataSet = PieDataSet(entries, "")

            dataSet.setDrawIcons(false)

            dataSet.setSliceSpace(0f)
            dataSet.setIconsOffset(MPPointF(0f, 0f))
            dataSet.selectionShift = 5f

            val colors = ArrayList<Int>()

            colors.add(Color.parseColor("#FF3700B3"))
            colors.add(Color.parseColor("#ec9c9c"))
            colors.add(Color.parseColor("#99cc00"))

            dataSet.colors = colors


            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(11f)
            data.setValueTextColor(Color.WHITE)
            dashboardBinding.pieChart.setData(data)
            dashboardBinding.pieChart.highlightValues(null)
            dashboardBinding.pieChart.invalidate()
//        }

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
            "Value: " + e.getY() + ", index: " + h?.x
                    + ", DataSet index: " + h?.dataSetIndex
        );
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

}