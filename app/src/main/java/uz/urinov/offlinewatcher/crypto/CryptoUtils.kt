package uz.urinov.offlinewatcher.crypto

import android.media.MediaCodec.CryptoException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.InvalidKeyException
import java.security.Key
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


private val ALGORITHM = "AES"
private val TRANSFORMATION = "AES"

@Throws(CryptoException::class)
fun encrypt(key: String, inputFile: File, outputFile: File) {
    doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile)
}

@Throws(CryptoException::class)
fun decrypt(key: String, inputFile: File, outputFile: File) {
    doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile)
}

@Throws(CryptoException::class)
private fun doCrypto(
    cipherMode: Int, key: String, inputFile: File,
    outputFile: File
) {
    try {
        val secretKey: Key = SecretKeySpec(key.toByteArray(), ALGORITHM)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(cipherMode, secretKey)
        val inputStream = FileInputStream(inputFile)
        val inputBytes = ByteArray(inputFile.length().toInt())
        inputStream.read(inputBytes)
        val outputBytes = cipher.doFinal(inputBytes)
        val outputStream = FileOutputStream(outputFile)
        outputStream.write(outputBytes)
        inputStream.close()
        outputStream.close()
    } catch (ex: NoSuchPaddingException) {
        throw CryptoException(1, "Error encrypting/decrypting file")
    } catch (ex: NoSuchAlgorithmException) {
        throw CryptoException(1, "Error encrypting/decrypting file")
    } catch (ex: InvalidKeyException) {
        throw CryptoException(1, "Error encrypting/decrypting file")
    } catch (ex: BadPaddingException) {
        throw CryptoException(1, "Error encrypting/decrypting file")
    } catch (ex: IllegalBlockSizeException) {
        throw CryptoException(1, "Error encrypting/decrypting file")
    } catch (ex: IOException) {
        throw CryptoException(1, "Error encrypting/decrypting file")
    }
}