package com.silva.familylocator

import android.util.Base64
import com.google.gson.Gson
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val KEY_SIZE = 256
    private const val GCM_TAG_LENGTH = 128
    private const val MASTER_KEY_HEX = "1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef"

    fun encryptPayload(payload: LocationPayload): String {
        return try {
            val key = SecretKeySpec(
                MASTER_KEY_HEX.chunked(2).map { it.toInt(16).toByte() }.toByteArray(),
                0,
                32,
                "AES"
            )

            val cipher = Cipher.getInstance(ALGORITHM)
            val iv = ByteArray(12)
            java.security.SecureRandom().nextBytes(iv)

            cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))

            val plaintext = Gson().toJson(payload).toByteArray(Charsets.UTF_8)
            val encrypted = cipher.doFinal(plaintext)

            val result = mapOf(
                "iv" to Base64.encodeToString(iv, Base64.NO_WRAP),
                "encrypted" to Base64.encodeToString(encrypted, Base64.NO_WRAP)
            )

            Base64.encodeToString(
                Gson().toJson(result).toByteArray(),
                Base64.NO_WRAP
            )
        } catch (e: Exception) {
            android.util.Log.e("EncryptionUtil", "Erro ao encriptar", e)
            ""
        }
    }

    fun decryptPayload(encryptedData: String): LocationPayload? {
        return try {
            val decrypted = Base64.decode(encryptedData, Base64.NO_WRAP).decodeToString()
            val data = Gson().fromJson(decrypted, Map::class.java) as Map<*, *>

            val iv = Base64.decode(data["iv"] as String, Base64.NO_WRAP)
            val encrypted = Base64.decode(data["encrypted"] as String, Base64.NO_WRAP)

            val key = SecretKeySpec(
                MASTER_KEY_HEX.chunked(2).map { it.toInt(16).toByte() }.toByteArray(),
                0,
                32,
                "AES"
            )

            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))

            val plaintext = cipher.doFinal(encrypted).decodeToString()
            Gson().fromJson(plaintext, LocationPayload::class.java)
        } catch (e: Exception) {
            android.util.Log.e("EncryptionUtil", "Erro ao descriptografar", e)
            null
        }
    }
}
