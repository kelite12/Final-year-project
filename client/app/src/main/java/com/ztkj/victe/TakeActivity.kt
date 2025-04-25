package com.ztkj.victe

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.jph.takephoto.app.TakePhotoActivity
import com.jph.takephoto.model.TResult
import java.io.File
import com.jph.takephoto.model.CropOptions


/**
 * 相机相册工具类
 */
class TakeActivity : TakePhotoActivity() {
    var pathAbsolute = "${Environment.getExternalStorageDirectory()}/DCIM";
    var takephoto:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_photo)
        val file = File(pathAbsolute, "${System.currentTimeMillis()}.jpg")
        val fileUri = FileProvider.getUriForFile(this, "com.ztkj.victe.fileprovider", file)
        val cropOptions = CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create()
        takephoto = intent.getIntExtra("takephoto",-1);
        when(takephoto){
            1->{
                takePhoto.onPickFromGallery();//相册
            }
            2->{

                takePhoto.onPickFromCapture(fileUri);//相机
            }
            3->{
                takePhoto.onPickFromDocuments();//文件
            }
            4 ->{
                //从相册中获取图片并裁剪
                takePhoto.onPickFromGalleryWithCrop(fileUri,cropOptions);

            }
            5 ->{
                takePhoto.onPickMultiple(9)
            }
        }

    }

    override fun takeFail(result: TResult?, msg: String?) {
        super.takeFail(result, msg)
        setResult(0x125)
        finish()
    }

    override fun takeSuccess(result: TResult?) {
        super.takeSuccess(result)
//        Log.e("TAG","------------>"+result!!.image.originalPath+"------>"+result!!.image.isCompressed)
        if (takephoto == 4){
            val file = File(pathAbsolute, result!!.image.originalPath.split("/")[2])
            result!!.image.originalPath = file.absolutePath
        }
        var intent = Intent();
        intent.putExtra("result",result!!.image)
        intent.putExtra("results",result.images)
        setResult(0x123,intent)
        finish()
    }

    override fun takeCancel() {
        super.takeCancel()
        setResult(0x124)
        finish()
    }
}
