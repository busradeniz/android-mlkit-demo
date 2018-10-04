package com.busradeniz.mlkitbaseapisample.firebase.facerecognition

import android.graphics.*
import android.util.Log
import com.busradeniz.mlkitbaseapisample.firebase.DetectionListener
import com.google.android.gms.vision.face.FaceDetector
import com.google.android.gms.vision.face.Landmark.*
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

class FaceDetector {

    companion object {
        private const val TAG = "FaceDetector"
        private const val MIN_FACE_SIZE = 0.10f
    }

    private var detector: FirebaseVisionFaceDetector

    init {
        val options = FirebaseVisionFaceDetectorOptions.Builder()
                .setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE)
                .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setMinFaceSize(MIN_FACE_SIZE)
                .setTrackingEnabled(true)
                .build()

        detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options)
    }

    fun detectInImage(bitmap: Bitmap, listener: DetectionListener) {
        var image = bitmap
        detector.detectInImage(FirebaseVisionImage.fromBitmap(bitmap))
                .addOnSuccessListener {
                    var result = ""
                    for (item in it) {
                        result += "Bounding Box : ${item.boundingBox} \n"
                        result += "LeftEyeOpenProbability : ${item.leftEyeOpenProbability} \n"
                        result += "RightEyeOpenProbability : ${item.rightEyeOpenProbability} \n"
                        result += "SmilingProbability : ${item.smilingProbability} \n"
                        result += "Left eye : ${item.getLandmark(LEFT_EYE)?.position?.x} -  ${item.getLandmark(LEFT_EYE)?.position?.y}\n"
                        result += "Right eye : ${item.getLandmark(RIGHT_EYE)?.position?.x} - ${item.getLandmark(RIGHT_EYE)?.position?.y} \n"
                        result += "Left Cheek : ${item.getLandmark(LEFT_CHEEK)?.position?.x} - ${item.getLandmark(LEFT_CHEEK)?.position?.y}\n"
                        result += "Right Cheek : ${item.getLandmark(RIGHT_CHEEK)?.position?.x} - ${item.getLandmark(RIGHT_CHEEK)?.position?.y} \n"
                        result += "------------------------------\n"

                        image = drawOnBitmap(image, item.boundingBox)
                    }


                    listener.onDetectCompleted(result, image)
                    detector.close()
                }
                .addOnFailureListener {
                    Log.e(TAG, it.localizedMessage)
                    detector.close()
                }
    }

    private fun drawOnBitmap(bitmap: Bitmap, rect: Rect): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        val strokePaint = Paint()

        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = Color.RED
        strokePaint.strokeWidth = 5.0F

        canvas.drawRect(rect, strokePaint)
        return mutableBitmap
    }
}