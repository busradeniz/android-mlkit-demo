package com.busradeniz.mlkitbaseapisample.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.busradeniz.mlkitbaseapisample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SELECT_PHOTO_REQUEST_CODE = 1000
    }

    private var bitmap: Bitmap? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setClickListeners()
        subscribeToDetectionResult()
    }

    private fun subscribeToDetectionResult() {
        viewModel.result().observe(this, Observer<String> {
            Log.e("busra", it)
            txt_result.text = it
        })

        viewModel.bitmap().observe(this, Observer<Bitmap> {
            img_selected.setImageBitmap(it)
        })
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

    private fun setClickListeners() {
        img_selected.setOnClickListener {
            txt_result.text = ""
            openGallery()
        }
        btn_image_labelling.setOnClickListener { bitmap?.let { viewModel.detectImageLabelOnDevice(it) } }
        btn_image_labelling_cloud.setOnClickListener { bitmap?.let { viewModel.detectImageLabelInCloud(it) } }
        btn_barcode.setOnClickListener { bitmap?.let { viewModel.detectBarcode(it) } }
        btn_face.setOnClickListener { bitmap?.let { viewModel.detectFace(it) } }
        btn_landmark.setOnClickListener { bitmap?.let { viewModel.detectLandmark(it) } }
        btn_document.setOnClickListener { bitmap?.let { viewModel.detectDocumentInCloud(it) } }
        btn_text.setOnClickListener { bitmap?.let { viewModel.detectTextOnDevice(it) } }
        btn_text_cloud.setOnClickListener { bitmap?.let { viewModel.detectTextInCloud(it) } }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO_REQUEST_CODE)
    }
}
