package com.busradeniz.mlkitbaseapisample.firebase.textrecognition

import android.graphics.Bitmap
import android.util.Log
import com.busradeniz.mlkitbaseapisample.firebase.DetectionListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import java.util.*


class TextRecognizer {

    companion object {
        const val TAG = "TextRecognizer"
    }

    private val detector: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer
    private val cloudDetector: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().cloudTextRecognizer
    private val documentDetector = FirebaseVision.getInstance()
            .getCloudDocumentTextRecognizer(
                    FirebaseVisionCloudDocumentRecognizerOptions.Builder()
                            .setLanguageHints(Arrays.asList("en"))
                            .build())

    fun detect(bitmap: Bitmap, listener: DetectionListener) {

        detector.processImage(FirebaseVisionImage.fromBitmap(bitmap))
                .addOnSuccessListener {
                    listener.onDetectCompleted(it.text)
                    detector.close()
                }
                .addOnFailureListener {
                    detector.close()
                    Log.e(TAG, it.localizedMessage)
                }
    }

    fun detectInCloud(bitmap: Bitmap, listener: DetectionListener) {

        cloudDetector.processImage(FirebaseVisionImage.fromBitmap(bitmap))
                .addOnSuccessListener {
                    listener.onDetectCompleted(it.text)
                    cloudDetector.close()
                }
                .addOnFailureListener {
                    cloudDetector.close()
                    Log.e(TAG, it.localizedMessage)
                }
    }

    fun detectInDocumentCloud(bitmap: Bitmap, listener: DetectionListener) {
        documentDetector.processImage(FirebaseVisionImage.fromBitmap(bitmap))
                .addOnSuccessListener {
                    listener.onDetectCompleted(it.text)
                    documentDetector.close()
                }
                .addOnFailureListener {
                    documentDetector.close()
                    Log.e(TAG, it.localizedMessage)
                }
    }
}