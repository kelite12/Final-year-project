package com.victe.vproject.util

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.annotation.SuppressLint
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * Created by 13526 on 2018/3/9.
 */
class Base64Util{
    companion object {

    /**
     *
     * @Title: bitmapToBase64
     * @Description: TODO(Bitmap 转换为字符串)
     * @param @param bitmap
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */

    @SuppressLint("NewApi")
    fun bitmapToBase64(bitmap: Bitmap?): String? {

        // 要返回的字符串
        var reslut: String? = null

        var baos: ByteArrayOutputStream? = null

        try {

            if (bitmap != null) {

                baos = ByteArrayOutputStream()
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(CompressFormat.JPEG, 30, baos)

                baos!!.flush()
                baos!!.close()
                // 转换为字节数组
                val byteArray = baos!!.toByteArray()

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT)
            } else {
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {

            try {
                if (baos != null) {
                    baos!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return reslut

    }

    /**
     *
     * @Title: base64ToBitmap
     * @Description: TODO(base64l转换为Bitmap)
     * @param @param base64String
     * @param @return    设定文件
     * @return Bitmap    返回类型
     * @throws
     */
    fun base64ToBitmap(base64String: String): Bitmap {

        val decode = Base64.decode(base64String, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(decode, 0, decode.size)
    }


    }
}