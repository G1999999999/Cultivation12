package com.example.cultivation11

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class paintchart : Activity() {
    private lateinit var fanhui: Button
    private lateinit var sure: Button
    private var testData = listOf(
        Entry(1f, 2f),
        Entry(2f, 5f),
        Entry(3f, 2f),
        Entry(4f, 7f),
        Entry(5f, 5f),
        Entry(6f, 4f),
        Entry(7f, 3f),
        Entry(8f, 1f),
        Entry(9f, 2f),
        Entry(10f, 3f),
        Entry(11f, 6f),
        Entry(12f, 2f),
        Entry(13f, 5f),
        Entry(14f, 2f),
        Entry(15f, 7f),
        Entry(16f, 5f),
        Entry(17f, 4f),
        Entry(18f, 3f),
        Entry(19f, 1f),
        Entry(20f, 2f),
        Entry(21f, 3f),
        Entry(22f, 6f),
        Entry(23f, 2f),
        Entry(24f, 5f),
        Entry(25f, 2f),
        Entry(26f, 7f),
        Entry(27f, 5f),
        Entry(28f, 4f),
        Entry(29f, 3f),
        Entry(30f, 1f),
        Entry(31f, 2f),
        Entry(32f, 3f),
        Entry(33f, 6f),
    )
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paint_chart_win)
        var lineChart = findViewById<LineChart>(R.id.lineChart)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        var dataSet = LineDataSet(testData, "数据可视化")
        dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
        dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
        var lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
        initSpinner(0)
        fanhui=findViewById(R.id.btn1)
        fanhui.setOnClickListener {
            Intent(this, secondwindows::class.java).also {
                startActivity(it)
                n=0
                finish()
            }
        }
        sure=findViewById(R.id.sure)
        sure.setOnClickListener{
            when(n){
                1->{
                    var a=1F
                    var shuju= arrayListOf(Entry(0F,0F))
                    for (i in wendu_group){
                        shuju.add(Entry(a, i))
                        a++
                    }
                    lineChart = findViewById<LineChart>(R.id.lineChart)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    dataSet = LineDataSet(shuju, "单位：℃")
                    dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
                    dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
                    lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate()
                    initSpinner(n)

                }
                2->{
                    var a=1F
                    var shuju= arrayListOf(Entry(0F,0F))
                    for (i in shidu_group){
                        shuju.add(Entry(a, i))
                        a++
                    }
                    lineChart = findViewById(R.id.lineChart)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    dataSet = LineDataSet(shuju, "单位：%")
                    dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
                    dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
                    lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate()
                    initSpinner(n)
                }
                3->{
                    var a=1F
                    var shuju= arrayListOf(Entry(0F,0F))
                    for (i in CO2_group){
                        shuju.add(Entry(a, i))
                        a++
                    }
                    lineChart = findViewById(R.id.lineChart)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    dataSet = LineDataSet(shuju, "单位：×10⁶ppm")
                    dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
                    dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
                    lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate()
                    initSpinner(n)
                }
                4->{
                    var a=1F
                    var shuju= arrayListOf(Entry(0F,0F))
                    for (i in NH3_group){
                        shuju.add(Entry(a, i))
                        a++
                    }
                    lineChart = findViewById(R.id.lineChart)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    dataSet = LineDataSet(shuju, "单位：ppm")
                    dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
                    dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
                    lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate()
                    initSpinner(n)
                }
                5->{
                    var a=1F
                    var shuju= arrayListOf(Entry(0F,0F))
                    for (i in siliao_group){
                        shuju.add(Entry(a, i))
                        a++
                    }
                    lineChart = findViewById(R.id.lineChart)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    dataSet = LineDataSet(shuju, "单位：g")
                    dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
                    dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
                    lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate()
                    initSpinner(n)
                }
                6->{
                    var a=1F
                    var shuju= arrayListOf(Entry(0F,0F))
                    for (i in yinyongshui_group){
                        shuju.add(Entry(a, i))
                        a++
                    }
                    lineChart = findViewById(R.id.lineChart)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    dataSet = LineDataSet(shuju, "单位：mL")
                    dataSet.color = ContextCompat.getColor(this, R.color.purple_200)
                    dataSet.valueTextColor = ContextCompat.getColor(this, R.color.purple_500)
                    lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate()
                    initSpinner(n)
                }
            }
        }
    }
    private fun initSpinner(n:Int) {
        //声明一个下拉列表的数组适配器
        val starAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.item_select, starArray)
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_drapdown)
        //从布局文件中获取名叫sp_dialog的下拉框
        val sp = findViewById<Spinner>(R.id.spinner)
        //设置下拉框的标题，不设置就没有难看的标题了
        sp.prompt = "请选择数据类型"
        //设置下拉框的数组适配器
        sp.adapter = starAdapter
        //设置下拉框默认的显示第一项
        sp.setSelection(n)
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp.onItemSelectedListener = MySelectedListener()
    }

     class MySelectedListener : OnItemSelectedListener {
        @Override
        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
            if(starArray[i]=="温度"){
                var a=1F
                n=1
            }
            if(starArray[i]=="湿度"){ n=2 }
            if(starArray[i]=="二氧化碳"){ n=3 }
            if(starArray[i]=="氨气"){ n=4 }
            if(starArray[i]=="饲料"){ n=5 }
            if(starArray[i]=="饮用水"){ n=6 }
        }
        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }
}