package com.busradeniz.mlkitbaseapisample

interface LabelDetectListener {

    fun onDetectCompleted(list: List<Label>)
}