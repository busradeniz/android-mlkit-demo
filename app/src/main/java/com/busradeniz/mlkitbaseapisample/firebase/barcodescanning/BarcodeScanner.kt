package com.busradeniz.mlkitbaseapisample.firebase.barcodescanning

import android.graphics.Bitmap
import android.util.Log
import com.busradeniz.mlkitbaseapisample.firebase.DetectionListener
import com.busradeniz.mlkitbaseapisample.firebase.textrecognition.TextRecognizer
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class BarcodeScanner {
    private val barcodeScanner = FirebaseVision.getInstance()
            .getVisionBarcodeDetector(FirebaseVisionBarcodeDetectorOptions.Builder()
                    .setBarcodeFormats(
                            FirebaseVisionBarcode.FORMAT_QR_CODE,
                            FirebaseVisionBarcode.FORMAT_AZTEC)
                    .build())

    fun scan(bitmap: Bitmap, listener: DetectionListener) {
        barcodeScanner.detectInImage(FirebaseVisionImage.fromBitmap(bitmap))
                .addOnSuccessListener {
                    var result = ""
                    for (item in it) {
                        result += "Display value : ${item.displayValue} \n"
                        result += "Bounding Box : ${item.boundingBox} \n"
                        result += "Calendar Event: ${item.calendarEvent.toString()} \n"
                        result += "Contact Info : ${item.contactInfo.toString()} \n"
                        result += "Email : ${item.email} \n"
                        result += "Raw value : ${item.rawValue} \n"
                        result += "------------------------------\n"
                    }
                    listener.onDetectCompleted(result)
                    barcodeScanner.close()
                }
                .addOnFailureListener {
                     barcodeScanner.close()
                    Log.e(TextRecognizer.TAG, it.localizedMessage) }
    }
}