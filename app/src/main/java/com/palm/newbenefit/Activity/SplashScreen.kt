package com.palm.newbenefit.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.palm.newbenefit.ApiConfig.AnalyticsApplication
import com.kmd.newbenefit.R
import java.io.File

class SplashScreen : AppCompatActivity() {
    var prefs: SharedPreferences? = null
    var token: String? = null
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        AnalyticsApplication.getInstance()
//            .trackEvent("Splashcreen", "SplashScreen Loading", "SplashScreen Loading Sucessfully")

        val prefs =
            getSharedPreferences(packageName, Context.MODE_PRIVATE)
          token = prefs.getString("api_token", null)

        val myThread: Thread = object : Thread() {
            override fun run() {
                try {



                    sleep(2000)



                    //stop for 200 seconds

                   // Log.d("token",token);


                    if (token != null) {
                        val intent = Intent(
                            applicationContext,
                            DemoActivty::class.java
                        ) //jump to next activity
                        startActivity(intent)
                        finish()
                    }else {
                        val intent = Intent(
                            applicationContext,
                            DemoActivty::class.java
                        ) //jump to next activity
                        startActivity(intent)
                        finish()

                    }



                    /* } else {

                        Log.d("device is not rooted","true");
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);    //jump to next activity
                        startActivity(intent);

                        finish();
                    }*/
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        myThread.start()
    }

    override fun onResume() {
        super.onResume()
//        if (prefs!!.getBoolean("firstrun", true)) {
//            Log.d("Firsrun", "true")
//           // SendData()
//            prefs!!.edit().putBoolean("firstrun", false).commit()
//        } else {
//            Log.d("Firsrun", "false")
//        }
    }

    fun findBinary(binaryName: String): Boolean {
        var found = false
        if (!found) {
            val places = arrayOf(
                "/sbin/", "/system/bin/", "/system/xbin/",
                "/data/local/xbin/", "/data/local/bin/",
                "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"
            )
            for (where in places) {
                if (File(where + binaryName).exists()) {
                    found = true
                    break
                }
            }
        }
        return found
    }

    private fun isRooted(): Boolean {
        return findBinary("su")
    }


}




