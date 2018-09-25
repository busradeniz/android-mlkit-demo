package com.busradeniz.mlkitbaseapisample.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.busradeniz.mlkitbaseapisample.Label
import com.busradeniz.mlkitbaseapisample.LabelDetectListener
import com.busradeniz.mlkitbaseapisample.LabelDetector
import com.busradeniz.mlkitbaseapisample.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LabelDetectListener {

    companion object {
        private const val SELECT_PHOTO_REQUEST_CODE = 1000
    }

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img_selected.setOnClickListener { openGallery() }
        btn_image_labelling.setOnClickListener { bitmap?.let { LabelDetector.detect(it, this) } }
        btn_image_labelling_cloud.setOnClickListener { bitmap?.let { LabelDetector.detectInCloud(it, this) } }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
                img_selected.setImageBitmap(bitmap)
            }
        }
    }

    override fun onDetectCompleted(list: List<Label>) {
        var result = ""
        for (item in list) {
            result += "${item.label} - ${item.confidence} \n"
        }

        txt_result.text = result
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO_REQUEST_CODE)
    }
}
