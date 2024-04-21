package com.angoga.android_nuclearhack.service

import android.content.Context
import androidx.core.content.edit
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher


object CryptoService {
    fun decode(challenge: String, context: Context): String {
        val key = context.getSharedPreferences("KEYS", Context.MODE_PRIVATE).let {
            loadPrivateKey(it.getString("KEY_PRIVATE", null)!!)
        }
        return decodeString(key, challenge)
    }


    fun generateKeyPairAndSave(context: Context): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val pair = keyPairGenerator.generateKeyPair()

        val pref = context.getSharedPreferences("KEYS", Context.MODE_PRIVATE)
        pref.edit {
            putString("KEY_PRIVATE", privateKeyToString(pair.private))
            putString("KEY_PUBLIC", publicKeyToString(pair.public))
            commit()
        }
        return pair
    }

    private fun decodeString(key: PrivateKey, data: String): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init (Cipher.DECRYPT_MODE, key)
        val decodedData = Base64.getDecoder().decode(data)
        val decryptedData = cipher.doFinal(decodedData)
        return String(decryptedData, Charsets.UTF_8)
    }
    fun privateKeyToString(privateKey: PrivateKey): String {
        val keyBytes = privateKey.encoded
        return Base64.getEncoder().encodeToString(keyBytes)
    }

    fun publicKeyToString(publicKey: PublicKey): String {
        val keyBytes = publicKey.encoded
        return Base64.getEncoder().encodeToString(keyBytes)
    }

    private fun loadPrivateKey(storedKey: String): PrivateKey {
        val data = Base64.getDecoder().decode(storedKey)
        val spec = PKCS8EncodedKeySpec(data)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePrivate(spec)
    }
}