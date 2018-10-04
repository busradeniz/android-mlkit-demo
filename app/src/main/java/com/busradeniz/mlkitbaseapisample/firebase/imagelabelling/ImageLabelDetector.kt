package com.busradeniz.mlkitbaseapisample

import android.graphics.Bitmap
import android.util.Log
import com.busradeniz.mlkitbaseapisample.firebase.DetectionListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabelDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector


class ImageLabelDetector {

    companion object {
        private const val TAG = "ImageLabelDetector"
        private const val MAX_RESULT = 20
    }

    private val detector: FirebaseVisionLabelDetector = FirebaseVision.getInstance().visionLabelDetector
    private val cloudDetector: FirebaseVisionCloudLabelDetector = FirebaseVision.getInstance()
            .getVisionCloudLabelDetector(
                    FirebaseVisionCloudDetectorOptions.Builder()
                            .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                            .setMaxResults(MAX_RESULT)
                            .build())


    fun detect(bitmap: Bitmap, listener: DetectionListener) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(image)
                .addOnSuccessListener {
                    var result = ""
                    for (item in it) {
                        result += "${item.label} -> ${item.confidence} \n"
                    }
                    listener.onDetectCompleted(result)
                    detector.close()
                }
                .addOnFailureListener {
                    detector.close()
                    Log.e(TAG, it.localizedMessage)
                }

    }

    fun detectInCloud(bitmap: Bitmap, listener: DetectionListener) {
        cloudDetector.detectInImage(FirebaseVisionImage.fromBitmap(bitmap))
                .addOnSuccessListener {
                    var result = ""
                    for (item in it) {
                        result += "${item.label} -> ${item.confidence} \n"
                    }
                    listener.onDetectCompleted(result)
                    cloudDetector.close()

                }
                .addOnFailureListener {
                    Log.e(TAG, it.localizedMessage)
                    cloudDetector.close()
                }
    }
}