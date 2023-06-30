package com.example.cultivation11
import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.huaweicloud.sdk.core.auth.AbstractCredentials
import com.huaweicloud.sdk.core.auth.BasicCredentials
import com.huaweicloud.sdk.core.auth.ICredential
import com.huaweicloud.sdk.iotda.v5.IoTDAClient
import com.huaweicloud.sdk.iotda.v5.model.ShowRuleRequest
import com.huaweicloud.sdk.iotda.v5.region.IoTDARegion
import java.time.LocalDateTime
class secondwindows: AppCompatActivity(){
    private lateinit var exit: Button
    private lateinit var chart: Button
    private lateinit var shebei: ImageView
    private lateinit var localtimevalue: EditText
    private lateinit var wenduvalue: EditText
    private lateinit var shiduvalue: EditText
    private lateinit var CO2value: EditText
    private lateinit var NH3value: EditText
    private lateinit var siliaovalue: EditText
    private lateinit var yinyongshuivalue: EditText
    private lateinit var shebeivalue: EditText
    private lateinit var ca1:RadioButton
    private lateinit var ca2:RadioButton
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var chongxiswitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var siliaoswitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var yinyongshuiswitch: Switch
    var TAG="test"
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)
        shebei=findViewById(R.id.shebei)
        localtimevalue= findViewById(R.id.localtimevalue)
        wenduvalue= findViewById(R.id.wenduvalue)
        shiduvalue= findViewById(R.id.shiduvalue)
        CO2value= findViewById(R.id.CO2value)
        NH3value= findViewById(R.id.NH3value)
        siliaovalue= findViewById(R.id.siliaovalue)
        yinyongshuivalue= findViewById(R.id.yinyongshuivalue)
        chongxiswitch= findViewById(R.id.chongxiswitch)
        siliaoswitch= findViewById(R.id.siliaoswitch)
        yinyongshuiswitch= findViewById(R.id.yinyongshuiswitch)
        shebeivalue=findViewById(R.id.shebeivalue)
        ca1=findViewById(R.id.ca1)
        ca2=findViewById(R.id.ca2)

        //允许小数据量的网络通信
        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        ShowRuleSolution()
        if (state_=="active"){
            ca1.isChecked=true
            ca2.isChecked=false
        }
        else if(state_=="inactive") {
            ca1.isChecked=false
            ca2.isChecked=true
        }
        //方便线程更新TextView的数据，为每个数据设置Handler
        val localtimeshow=localtimeshow()
        val shebeionlineshow=shebeionlineshow()
        val shebeioutlineshow=shebeioutlineshow()
        val wendushow=wendushow()
        val shidushow=shidushow()
        val CO2show=CO2show()
        val NH3show=NH3show()
        val siliaoshow=siliaoshow()
        val yinyongshuishow=yinyongshuishow()
        val t: Thread = object : Thread() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                val hwiot = huaweiIOT("123","environment")
                val hwiot1 = huaweiIOT("123","environment")
                //设备控制状态
                while (true) {
                    //获取时间
                    val current=LocalDateTime.now()
                    val localtime_message = Message()
                    localtime_message.obj = current.toString().substring(0,10)+" "+current.toString().substring(11,19)
                    localtimeshow.sendMessage(localtime_message)
                    println('\n')
                    try {
                        line = hwiot.getAtt("", "status")
                        var str = ""
                        str = hwiot.getAtt("", "status")
                        if (str == "OFFLINE") {
                            var message = Message()
                            message.obj = "设备离线"
                            shebeivalue.setTextColor(R.color.black)
                            shebeioutlineshow.sendMessage(message)
                        } else if (str == "ONLINE") {
                            var message = Message()
                            message.obj = "设备在线"
                            shebeivalue.setTextColor(R.color.rgb_green_color)
                            shebeionlineshow.sendMessage(message)
                        }
                        //温度
                        wendu = hwiot1.getAtt("Temp", "shadow",1)
                        val wendu_message = Message()
                        wendu_message.obj = "$wendu℃"
                        wendushow.sendMessage(wendu_message)
                        wendu_group.add(wendu.toFloat())
                        println("获取成功，温度：$wendu")
                        //湿度
                        shidu = hwiot1.getAtt("humidity", "shadow",1)
                        val shidu_message = Message()
                        shidu_message.obj = "$shidu%"
                        shidushow.sendMessage(shidu_message)
                        shidu_group.add(shidu.toFloat())
                        //CO2浓度
                        CO2 = hwiot.getAtt("CO2", "shadow",2)
                        val CO2_message = Message()
                        CO2_message.obj = CO2 +"×10⁶ppm"
                        CO2show.sendMessage(CO2_message)
                        CO2_group.add(CO2.toFloat())
                        //NH3浓度
                        NH3 = hwiot.getAtt("ammonia", "shadow",2)
                        val NH3_message = Message()
                        NH3_message.obj = NH3+"ppm"
                        NH3show.sendMessage(NH3_message)
                        NH3_group.add(NH3.toFloat())
                        //饲料剩余量
                        siliao = hwiot.getAtt("Food", "shadow",0)
                        val siliao_message = Message()
                        siliao_message.obj = siliao +"g"
                        siliaoshow.sendMessage(siliao_message)
                        siliao_group.add(siliao.toFloat())
                        //饮用水剩余量
                        yinyongshui = hwiot.getAtt("Water", "shadow",0)
                        val yinyongshui_message = Message()
                        yinyongshui_message.obj = yinyongshui +"mL"
                        yinyongshuishow.sendMessage(yinyongshui_message)
                        yinyongshui_group.add(yinyongshui.toFloat())

                    } catch (e: Exception) {
                        e.printStackTrace()
                        println("获取失败：$e")
                    }
                    sleep(1000)
                }
            }
        }
        t.start()
        //控制模式
        ca1.setOnClickListener {
            if(line=="ONLINE") {
                ca1.isChecked = true
                ca2.isChecked = false
                val control: Thread = object : Thread() {
                    override fun run() {
                        ChangeRuleStatusSolution("123","active")
                        ChangeRuleStatusSolution("123","active")
                        ChangeRuleStatusSolution("123","active")
                        ChangeRuleStatusSolution("123","active")
                        ChangeRuleStatusSolution("123","active")
                        ChangeRuleStatusSolution("123","active")
                        state_="active"
                    }
                }
                control.start()
            }else{
                ca1.isChecked = false
                ca2.isChecked = false
                Toast.makeText(this@secondwindows, "设备不在线，命令下发失败", Toast.LENGTH_SHORT).show()
            }
        }
        ca2.setOnClickListener {
            if(line=="ONLINE") {
                ca1.isChecked = false
                ca2.isChecked = true
                val control: Thread = object : Thread() {
                    override fun run() {
                        ChangeRuleStatusSolution("123","inactive")
                        ChangeRuleStatusSolution("123","inactive")
                        ChangeRuleStatusSolution("123","inactive")
                        ChangeRuleStatusSolution("123","inactive")
                        ChangeRuleStatusSolution("123","inactive")
                        ChangeRuleStatusSolution("123","inactive")
                        state_="inactive"
                    }
                }
                control.start()
            }else{
                ca1.isChecked = false
                ca2.isChecked = false
                Toast.makeText(this@secondwindows, "设备不在线，命令下发失败", Toast.LENGTH_SHORT).show()
            }
        }
        //控制冲洗
        chongxiswitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if(line=="ONLINE") {
                if(state_=="inactive") {
                    if (b) {
                        val chongxi: Thread = object : Thread() {
                            override fun run() {
                                try {
                                    val hwiot =
                                        huaweiIOT(
                                            "123",
                                            "clean_temperature",
                                            "Waterpump_2"
                                        )
                                    println(hwiot.commands)
                                    println("命令下发")
                                    hwiot.setCom("Wat_2_control", "1")
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                    println("下发失败：$e")
                                }
                            }
                        }
                        chongxi.start()
                        Toast.makeText(this@secondwindows, "命令下发中", Toast.LENGTH_SHORT).show()
                    } else {
                        val chongxi: Thread = object : Thread() {
                            override fun run() {
                                try {
                                    val hwiot =
                                        huaweiIOT(
                                            "123",
                                            "clean_temperature",
                                            "Waterpump_2"
                                        )
                                    println("命令下发")
                                    hwiot.setCom("Wat_2_control", "0")
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                    println("下发失败：$e")
                                }
                            }
                        }
                        chongxi.start()
                        Toast.makeText(this@secondwindows, "命令下发中", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    chongxiswitch.isChecked=false
                    Toast.makeText(this@secondwindows, "当前为自动模式", Toast.LENGTH_SHORT).show()
                }
            }else{
                chongxiswitch.isChecked=false
                Toast.makeText(this@secondwindows, "设备不在线，命令下发失败", Toast.LENGTH_SHORT).show()
            }
        })
        //控制饲料投放
        siliaoswitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if(line=="ONLINE") {
                if(state_=="inactive") {
                    if (b) {
                        val siliao: Thread = object : Thread() {
                            override fun run() {
                                try {
                                    val hwiot =
                                        huaweiIOT(
                                            "123",
                                            "food_water",
                                            "Motor"
                                        )
                                    //hwiot.commands="lightControl";
                                    println(hwiot.commands)
                                    println("命令下发")
                                    hwiot.setCom("Mot_1_control", "1")
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                    println("下发失败：$e")
                                }
                            }
                        }
                        siliao.start()
                        Toast.makeText(this@secondwindows, "命令下发中", Toast.LENGTH_SHORT).show()
                    } else {
                        val siliao: Thread = object : Thread() {
                            override fun run() {
                                try {
                                    val hwiot =
                                        huaweiIOT(
                                            "123",
                                            "food_water",
                                            "Motor"
                                        )
                                    println("命令下发")
                                    hwiot.setCom("Mot_1_control", "0")
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                    println("下发失败：$e")
                                }
                            }
                        }
                        siliao.start()
                        Toast.makeText(this@secondwindows, "命令下发中", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    siliaoswitch.isChecked=false
                    Toast.makeText(this@secondwindows, "当前为自动模式", Toast.LENGTH_SHORT).show()
                }
            }else{
                siliaoswitch.isChecked=false
                Toast.makeText(this@secondwindows, "设备不在线，命令下发失败", Toast.LENGTH_SHORT).show()
            }
        })

        //控制饮用水投放
        yinyongshuiswitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if(line=="ONLINE") {
                if(state_=="inactive") {
                if (b) {
                    val yinyongshui: Thread = object : Thread() {
                        override fun run() {
                            try {
                                val hwiot =
                                    huaweiIOT(
                                        "123",
                                        "food_water",
                                        "Waterpump_1"
                                    )
                                //hwiot.commands="lightControl";
                                println(hwiot.commands)
                                println("命令下发")
                                hwiot.setCom("Wat_1_control", "1")
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                                println("下发失败：$e")
                            }
                        }
                    }
                    yinyongshui.start()
                    Toast.makeText(this@secondwindows, "命令下发中", Toast.LENGTH_SHORT).show()
                } else {
                    val yinyongshui: Thread = object : Thread() {
                        override fun run() {
                            try {
                                val hwiot =
                                    huaweiIOT(
                                        "123",
                                        "food_water",
                                        "Waterpump_1")
                                println("命令下发")
                                hwiot.setCom("Wat_1_control", "0")
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                                println("下发失败：$e")
                            }
                        }
                    }
                    yinyongshui.start()
                    Toast.makeText(this@secondwindows, "命令下发中", Toast.LENGTH_SHORT).show()
                }
                }else{
                    yinyongshuiswitch.isChecked=false
                    Toast.makeText(this@secondwindows, "当前为自动模式", Toast.LENGTH_SHORT).show()
                }
            }else{
                yinyongshuiswitch.isChecked=false
                Toast.makeText(this@secondwindows, "设备不在线，命令下发失败", Toast.LENGTH_SHORT).show()
            }
        })




        exit = findViewById(R.id.exit)
        exit.setOnClickListener{
            showNormalDialog("确认退出？", 2)
        }
        chart = findViewById(R.id.dataanalysis)
        chart.setOnClickListener {
            if(line=="ONLINE"){
                val intent = Intent(this, paintchart::class.java)
                startActivity(intent)
                //finish()
            }else{
                Toast.makeText(this@secondwindows, "设备不在线，命令下发失败", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun showNormalDialog(strtip: String, num: Int) {
        //创建dialog构造器
        val normalDialog = androidx.appcompat.app.AlertDialog.Builder(this)
        //设置title
        normalDialog.setTitle("温馨提示：")
        //设置icon
        normalDialog.setIcon(R.drawable.ic_baseline_privacy_tip)
        //设置内容
        normalDialog.setMessage(strtip)
        //设置按钮
        normalDialog.setPositiveButton(
            getString(R.string.sure)
        ) { dialog, which ->
            if (num > 1) {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        if (num == 2) normalDialog.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, which ->  }
        //创建并显示
        normalDialog.create().show()
    }
    inner class localtimeshow: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            localtimevalue.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class shebeionlineshow: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            shebei.setImageDrawable(resources.getDrawable(R.drawable.online))
            shebeivalue.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class shebeioutlineshow: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            shebei.setImageDrawable(resources.getDrawable(R.drawable.outline))
            shebeivalue.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class wendushow: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            wenduvalue.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class shidushow: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            shiduvalue.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class CO2show: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            CO2value.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class NH3show: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            NH3value.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class siliaoshow: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            siliaovalue.setText(msg.obj.toString())
        }
    }
    @SuppressLint("HandlerLeak")
    inner class yinyongshuishow: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            yinyongshuivalue.setText(msg.obj.toString())
        }
    }
}