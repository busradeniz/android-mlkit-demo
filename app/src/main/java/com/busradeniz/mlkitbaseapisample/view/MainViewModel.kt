package com.busradeniz.mlkitbaseapisample.view

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.busradeniz.mlkitbaseapisample.ImageLabelDetector
import com.busradeniz.mlkitbaseapisample.firebase.textrecognition.TextRecognizer
import androidx.lifecycle.MutableLiveData
import com.busradeniz.mlkitbaseapisample.firebase.DetectionListener
import com.busradeniz.mlkitbaseapisample.firebase.barcodescanning.BarcodeScanner
import com.busradeniz.mlkitbaseapisample.firebase.facerecognition.FaceDetector
import com.busradeniz.mlkitbaseapisample.firebase.landmarkrecognition.LandmarkDetector


class MainViewModel : ViewModel(), DetectionListener {

    private val detectionResultLiveData = MutableLiveData<String>()
    private val bitmapLiveData = MutableLiveData<Bitmap>()

    private val imageLabelDetector = ImageLabelDetector()
    private val textRecognizer = TextRecognizer()
    private val barcodeScanner = BarcodeScanner()
    private val landmarkDetector = LandmarkDetector()
    private val faceDetector = FaceDetector()

    fun result(): LiveData<String> = detectionResultLiveData
    fun bitmap(): LiveData<Bitmap> = bitmapLiveData

    fun detectImageLabelOnDevice(bitmap: Bitmap) {
        imageLabelDetector.detect(bitmap, this)
    }

    fun detectImageLabelInCloud(bitmap: Bitmap) {
        imageLabelDetector.detectInCloud(bitmap, this)
    }

    fun detectBarcode(bitmap: Bitmap) {
        barcodeScanner.scan(bitmap, this)
    }

    fun detectLandmark(bitmap: Bitmap) {
        landmarkDetector.detect(bitmap, this)
    }

    fun detectFace(bitmap: Bitmap) {
        faceDetector.detectInImage(bitmap, this)
    }

    fun detectTextOnDevice(bitmap: Bitmap) {
        textRecognizer.detect(bitmap, this)
    }

    fun detectTextInCloud(bitmap: Bitmap) {
        textRecognizer.detectInCloud(bitmap, this)
    }

    fun detectDocumentInCloud(bitmap: Bitmap) {
        textRecognizer.detectInDocumentCloud(bitmap, this)
    }

    override fun onDetectCompleted(text: String, image: Bitmap?) {
        detectionResultLiveData.value = text
        image?.let {
            bitmapLiveData.value = image
        }
    }
}