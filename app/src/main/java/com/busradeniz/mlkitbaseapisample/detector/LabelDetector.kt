package com.busradeniz.mlkitbaseapisample

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage


object LabelDetector {

    fun detect(bitmap: Bitmap, labelDetectListener: LabelDetectListener) {

        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().visionLabelDetector

        detector.detectInImage(image)
                .addOnSuccessListener {
                    labelDetectListener.onDetectCompleted(it.map { it -> Label(it.label, it.confidence) })
                }
                .addOnFailureListener {
                    Log.e("busra", it.localizedMessage)
                }

    }


    fun detectInCloud(bitmap: Bitmap, labelDetectListener: LabelDetectListener) {
        val options = FirebaseVisionCloudDetectorOptions.Builder()
                .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                .setMaxResults(15)
                .build()

        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
                .getVisionCloudLabelDetector(options)

        detector.detectInImage(image)
                .addOnSuccessListener {
                    labelDetectListener.onDetectCompleted(it.map { it -> Label(it.label, it.confidence) })
                }
                .addOnFailureListener {
                    Log.e("busra", it.localizedMessage)
                }

    }
}