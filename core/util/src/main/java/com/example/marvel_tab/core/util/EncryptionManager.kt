package com.example.marvel_tab.core.util

import java.math.BigInteger
import java.security.MessageDigest

object EncryptionManager {
    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32 ,'0')
    }
}