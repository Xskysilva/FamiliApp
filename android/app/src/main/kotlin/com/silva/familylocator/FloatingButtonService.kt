package com.silva.familylocator

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class FloatingButtonService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingButton: Button
    private lateinit var prefs: SharedPreferences
    private var longPressHandler: Handler? = null
    private var isEmergency = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        prefs = getSharedPreferences("family_locator", Context.MODE_PRIVATE)

        floatingButton = Button(this).apply {
            text = "🚨"
            textSize = 24f
            setBackgroundColor(0xFFDC2626.toInt())
            setTextColor(0xFFFFFFFF.toInt())
        }

        val params = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            width = 120
            height = 120
            gravity = Gravity.BOTTOM or Gravity.END
            x = 20
            y = 20
        }

        windowManager.addView(floatingButton, params)

        longPressHandler = Handler(Looper.getMainLooper())

        floatingButton.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Iniciar contagem de hold
                    longPressHandler?.postDelayed({
                        toggleEmergency()
                    }, 3000) // 3 segundos
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Cancelar hold se foi solto antes de 3s
                    longPressHandler?.removeCallbacksAndMessages(null)
                    true
                }
                else -> false
            }
        }
    }

    private fun toggleEmergency() {
        isEmergency = !isEmergency
        prefs.edit().putBoolean("is_emergency", isEmergency).apply()

        if (isEmergency) {
            floatingButton.setBackgroundColor(0xFFEF4444.toInt())
            Toast.makeText(this, "🚨 EMERGÊNCIA ATIVADA", Toast.LENGTH_SHORT).show()

            // Passar para 1 minuto
            scheduleEmergencyUpdates()

            // Auto-desativar após 5 minutos
            Handler(Looper.getMainLooper()).postDelayed({
                isEmergency = false
                prefs.edit().putBoolean("is_emergency", false).apply()
                floatingButton.setBackgroundColor(0xFFDC2626.toInt())
                scheduleNormalUpdates()
                Toast.makeText(this, "Emergência desativada", Toast.LENGTH_SHORT).show()
            }, 5 * 60 * 1000)
        } else {
            floatingButton.setBackgroundColor(0xFFDC2626.toInt())
            Toast.makeText(this, "Emergência cancelada", Toast.LENGTH_SHORT).show()
            scheduleNormalUpdates()
        }
    }

    private fun scheduleEmergencyUpdates() {
        val emergencyWork = PeriodicWorkRequestBuilder<LocationWorker>(
            1,
            TimeUnit.MINUTES,
            1,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "location_updates",
            ExistingPeriodicWorkPolicy.REPLACE,
            emergencyWork
        )
    }

    private fun scheduleNormalUpdates() {
        val normalWork = PeriodicWorkRequestBuilder<LocationWorker>(
            10,
            TimeUnit.MINUTES,
            5,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "location_updates",
            ExistingPeriodicWorkPolicy.REPLACE,
            normalWork
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(floatingButton)
    }
}
