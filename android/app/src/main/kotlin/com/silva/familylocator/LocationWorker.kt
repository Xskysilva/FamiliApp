package com.silva.familylocator

import android.content.Context
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import com.google.gson.Gson
import io.github.supabase.gotrue.gotrue
import io.github.supabase.supabase.Supabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val prefs: SharedPreferences = context.getSharedPreferences("family_locator", Context.MODE_PRIVATE)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            // Obter localização
            val location = Tasks.await(fusedLocationClient.lastLocation)

            if (location != null) {
                // Preparar payload
                val isEmergency = prefs.getBoolean("is_emergency", false)
                val userId = prefs.getString("user_id", null) ?: return@withContext Result.retry()
                val familyGroupId = prefs.getString("family_group_id", null) ?: return@withContext Result.retry()

                val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                val payload = LocationPayload(
                    user_id = userId,
                    family_group_id = familyGroupId,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracy = location.accuracy,
                    emergency = isEmergency,
                    created_at = timestamp
                )

                // Encriptar dados
                val encrypted = EncryptionUtil.encryptPayload(payload)

                // Enviar ao Supabase
                val supabase = Supabase.client
                supabase.from("locations")
                    .insert(mapOf(
                        "user_id" to payload.user_id,
                        "family_group_id" to payload.family_group_id,
                        "latitude" to payload.latitude,
                        "longitude" to payload.longitude,
                        "accuracy" to payload.accuracy,
                        "encrypted_data" to encrypted,
                        "emergency" to payload.emergency,
                        "created_at" to timestamp
                    ))

                // Se foi emergência, desativar após 5 min
                if (isEmergency) {
                    prefs.edit().putBoolean("is_emergency", false).apply()
                }

                Result.success()
            } else {
                // Sem localização, retry depois
                Result.retry()
            }
        } catch (e: Exception) {
            android.util.Log.e("LocationWorker", "Erro ao enviar localização", e)
            Result.retry()
        }
    }
}

data class LocationPayload(
    val user_id: String,
    val family_group_id: String,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val emergency: Boolean,
    val created_at: String
)
