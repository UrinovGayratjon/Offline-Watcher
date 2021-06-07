package uz.urinov.offlinewatcher.downloader.internal.stream

import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec


@Throws(
    InvalidKeyException::class,
    NoSuchPaddingException::class,
    NoSuchAlgorithmException::class,
    BadPaddingException::class,
    IllegalBlockSizeException::class
)
fun encryptMessage(message: ByteArray, keyBytes: ByteArray): ByteArray {
    val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKey: SecretKey = SecretKeySpec(keyBytes, "AES")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    return cipher.doFinal(message)
}


@Throws(
    NoSuchPaddingException::class,
    NoSuchAlgorithmException::class,
    InvalidKeyException::class,
    BadPaddingException::class,
    IllegalBlockSizeException::class
)
fun decryptMessage(encryptedMessage: ByteArray, keyBytes: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKey: SecretKey = SecretKeySpec(keyBytes, "AES")
    cipher.init(Cipher.DECRYPT_MODE, secretKey)
    return cipher.doFinal(encryptedMessage)
}

