package com.example.cultivation11
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huaweicloud.sdk.core.auth.AbstractCredentials
import com.huaweicloud.sdk.core.auth.BasicCredentials
import com.huaweicloud.sdk.core.auth.ICredential
import com.huaweicloud.sdk.core.exception.ConnectionException
import com.huaweicloud.sdk.core.exception.RequestTimeoutException
import com.huaweicloud.sdk.core.exception.ServiceResponseException
import com.huaweicloud.sdk.iotda.v5.IoTDAClient
import com.huaweicloud.sdk.iotda.v5.model.ChangeRuleStatusRequest
import com.huaweicloud.sdk.iotda.v5.model.RuleStatus
import com.huaweicloud.sdk.iotda.v5.region.IoTDARegion


class MainActivity:AppCompatActivity(){
    private lateinit var login: Button
    private lateinit var passwordvalue: EditText
    private lateinit var photo: ImageView
    var TAG="test"
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logininwindows)
        passwordvalue= findViewById(R.id.passwordvalue)
        photo= findViewById(R.id.imageView)
        photo.setImageDrawable(resources.getDrawable(R.drawable.pigpig22))
        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        login = findViewById(R.id.login)
        login.setOnClickListener {
            val password = passwordvalue.text.toString()
            if (password == IAMPASSWORD) {
                val intent = Intent(this, secondwindows::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this@MainActivity, "密码错误，请重新输入", Toast.LENGTH_SHORT).show()
                passwordvalue.setText("")
            }
        }
    }
}