package com.busradeniz.mlkitbaseapisample.firebase

import android.graphics.Bitmap

interface DetectionListener {

    fun onDetectCompleted(text: String, image : Bitmap? = null)
}