package com.silva.familylocator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            scheduleLocationUpdates()
            Toast.makeText(this, "Permissões concedidas!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText: TextView = findViewById(R.id.statusText)
        val startBtn: Button = findViewById(R.id.startBtn)
        val emergencyTestBtn: Button = findViewById(R.id.emergencyTestBtn)

        // Iniciar serviço do botão flutuante
        startFloatingButtonService()

        // Permissões
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            )
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        startBtn.setOnClickListener {
            scheduleLocationUpdates()
            statusText.text = "✅ Monitoramento ativado (10 min)"
            Toast.makeText(this, "Localizações serão enviadas a cada 10 minutos", Toast.LENGTH_SHORT).show()
        }

        emergencyTestBtn.setOnClickListener {
            // Botão de teste para disparar emergência
            Toast.makeText(this, "Segure no botão flutuante por 3s para emergência", Toast.LENGTH_LONG).show()
        }
    }

    private fun startFloatingButtonService() {
        val intent = Intent(this, FloatingButtonService::class.java)
        startService(intent)
    }

    private fun scheduleLocationUpdates() {
        val locationWork = PeriodicWorkRequestBuilder<LocationWorker>(
            10,
            TimeUnit.MINUTES,
            5,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "location_updates",
            ExistingPeriodicWorkPolicy.KEEP,
            locationWork
        )
    }
}
