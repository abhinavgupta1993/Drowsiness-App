package com.drowsiness.ai.facedetection.camera

import android.graphics.Rect
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import com.drowsiness.ai.facedetection.graphic.GraphicOverlay
import com.drowsiness.ai.facedetection.graphic.RectangleOverlay

class CameraAnalyzer(
    private val overlay: GraphicOverlay<*>
) : BaseCameraAnalyzer<List<Face>>(){

    override val graphicOverlay: GraphicOverlay<*>
        get() = overlay

    private val cameraOptions = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
        .setMinFaceSize(0.1f)
        .enableTracking()
        .build()

    private val detector = FaceDetection.getClient(cameraOptions)

    override fun detectInImage(image: InputImage): Task<List<Face>> {
        return detector.process(image)
            .addOnSuccessListener { faces ->
                // Handle detected faces
                for (face in faces) {
                    val upperLipBottom = face.getLandmark(FaceLandmark.MOUTH_LEFT)
                    val lowerLipTop = face.getLandmark(FaceLandmark.MOUTH_RIGHT)

// Calculate vertical distance between upper and lower lip
                    val lipDistance = lowerLipTop?.position?.y!! - upperLipBottom?.position?.y!!

// Determine if the mouth is open based on the calculated distance
                    val isMouthOpen = lipDistance > 0.75F

                    if (isMouthOpen){
                        println("OPEN_YOUR_MOUTH HAHAHAHAHA")
                    }

                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                // Handle failure
            }

//         detector.process(image)
    }


    override fun stop() {
       try {
           detector.close()
       } catch (e : Exception) {
           Log.e(TAG , "stop : $e")
       }
    }

    override fun onSuccess(results: List<Face>, graphicOverlay: GraphicOverlay<*>, rect: Rect) {
        graphicOverlay.clear()
        results.forEach {
            val faceGraphic = RectangleOverlay(graphicOverlay, it, rect)
            graphicOverlay.add(faceGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "onFailure : $e")
    }

    companion object {
        private const val TAG = "CameraAnalyzer"
    }
}