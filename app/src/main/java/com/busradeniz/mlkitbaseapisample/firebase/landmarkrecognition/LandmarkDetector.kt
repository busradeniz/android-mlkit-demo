package com.busradeniz.mlkitbaseapisample.firebase.landmarkrecognition

import android.graphics.Bitmap
import android.util.Log
import com.busradeniz.mlkitbaseapisample.firebase.DetectionListener
import com.busradeniz.mlkitbaseapisample.firebase.textrecognition.TextRecognizer
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class LandmarkDetector {

    companion object {
        private const val TAG = "LandmarkDetector"
        private const val MAX_RESULT = 15
    }

    private val landmarkDetector = FirebaseVision.getInstance()
            .getVisionCloudLandmarkDetector(FirebaseVisionCloudDetectorOptions.Builder()
                    .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                    .setMaxResults(MAX_RESULT)
                    .build())

    fun detect(bitmap: Bitmap, listener: DetectionListener) {
        landmarkDetector.detectInImage(FirebaseVisionImage.fromBitmap(bitmap))
                .addOnSuccessListener {
                    var result = ""
                    for (item in it) {
                        result += "Landmark : ${item.landmark} \n"
                        result += "Bounding Box : ${item.boundingBox} \n"
                        result += "Confidence : ${item.confidence} \n"
                        result += "------------------------------\n"
                    }
                    listener.onDetectCompleted(result)
                    landmarkDetector.close()
                }
                .addOnFailureListener {
                    landmarkDetector.close()
                    Log.e(TAG, it.localizedMessage)
                }
    }


}