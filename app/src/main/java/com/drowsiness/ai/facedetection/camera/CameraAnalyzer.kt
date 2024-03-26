package com.drowsiness.ai.facedetection.camera

import android.content.Context
import android.graphics.Rect
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.drowsiness.ai.facedetection.graphic.GraphicOverlay
import com.drowsiness.ai.facedetection.graphic.RectangleOverlay
import com.drowsiness.ai.helper.DetectionHelper
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark

class CameraAnalyzer(
    private val overlay: GraphicOverlay<*>, var context: Context
) : BaseCameraAnalyzer<List<Face>>() {

    var yawningCount: Int = 0
    var distractionCount: Int = 0
    var eyesCount: Int = 0
    var mediaPlayer: MediaPlayer? = null
    var detectionHelper : DetectionHelper? = null

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

//            .addOnSuccessListener { faces ->
//                // Handle detected faces
//                for (face in faces) {
//                    val upperLipBottom = face.getLandmark(FaceLandmark.MOUTH_LEFT)
//                    val lowerLipTop = face.getLandmark(FaceLandmark.MOUTH_RIGHT)
//
//// Calculate vertical distance between upper and lower lip
//                    val lipDistance = lowerLipTop?.position?.y!! - upperLipBottom?.position?.y!!
//
//// Determine if the mouth is open based on the calculated distance
//                    val isMouthOpen = lipDistance > 0.75F
//
//                    if (isMouthOpen){
//                        println("OPEN_YOUR_MOUTH HAHAHAHAHA")
//                    }
//
//                }
//            }
//            .addOnFailureListener { e ->
//                e.printStackTrace()
//                // Handle failure
//            }

            .addOnSuccessListener { faces ->
                mediaPlayer = MediaPlayer.create(context, getAlarmSoundUri())
                // Process the list of faces
                for (face in faces) {
                    // Extract attributes and perform necessary actions
                    val leftEyeOpenProbability = face.leftEyeOpenProbability
                    val rightEyeOpenProbability = face.rightEyeOpenProbability
                    val yawAngle = face.headEulerAngleY
                    // Check for yawning or other attributes
                    if (leftEyeOpenProbability!! < 0.5 && rightEyeOpenProbability!! < 0.5) {
                        Log.d(TAG, "Eyes Closed")

                        if (eyesCount++ > 10){
                            detectionHelper?.checkEyesCount(eyesCount)
                            eyesCount = 0
                        }

                    }

                    if (yawAngle > 10 || yawAngle < -10) {
                        Log.d(TAG, "head turned")

                        if (distractionCount++ > 10) {
                            detectionHelper?.checkDistractionCount(distractionCount)
                            distractionCount = 0
                        }
                    }


//                    val mouthOpenProbability = face.smilingProbability
//                    if (mouthOpenProbability!! > 0.7) {
//                        Log.d(TAG, "Mouth wide open")
//                    }


                    val leftMouth = face.getLandmark(FaceLandmark.MOUTH_LEFT)
                    val rightMouth = face.getLandmark(FaceLandmark.MOUTH_RIGHT)
                    val bottomMouth = face.getLandmark(FaceLandmark.MOUTH_BOTTOM)

                    // Example yawning detection logic
                    val mouthWidth = rightMouth?.position?.x!! - leftMouth?.position?.x!!
                    val mouthHeight =
                        bottomMouth?.position?.y!! - (leftMouth.position?.y!! + rightMouth.position?.y!!) / 2

                    val yawningThreshold = 18 // Adjust threshold as needed
                    if (mouthWidth > yawningThreshold && mouthHeight > yawningThreshold) {

                        println("YAWNING_IS_DETECTED ${yawningCount++}")
                    }

                    if (yawningCount > 3){
                        detectionHelper?.checkYawningCount(yawningCount++)
                        mediaPlayer?.start()
                        yawningCount = 0
                        Toast.makeText(context,"Yawning is detected",Toast.LENGTH_SHORT).show()
                    }

                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Face detection failed", e)
            }
//            .addOnCompleteListener {
//                imageProxy.close()
//            }


//         detector.process(image)
    }

    private fun getAlarmSoundUri(): Uri? {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
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