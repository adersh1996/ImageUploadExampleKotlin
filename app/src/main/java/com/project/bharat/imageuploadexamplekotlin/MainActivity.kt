package com.project.bharat.imageuploadexamplekotlin

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var buttonUpload: Button
    lateinit var imageView: ImageView
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)
        buttonUpload = findViewById(R.id.button2)

        button.setOnClickListener {
            pickMedia.launch("image/*")

        }
        buttonUpload.setOnClickListener {
            apiCall()
        }


    }


    fun apiCall() {
        CoroutineScope(Dispatchers.IO).launch {

            val filesDir = applicationContext.filesDir
            val file = File(filesDir, "image.jpg")

            val inputStream = contentResolver.openInputStream(imageUri)
            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)
            Log.d("pathhh", file.path.toString())

            val requestFile: RequestBody =
                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            val multipartBody: MultipartBody.Part =
                MultipartBody.Part.createFormData("avatar", file.name, requestFile)

            val additionalParam = RequestBody.create("text/plain".toMediaTypeOrNull(), "anil")


            val response = RetrofitClient.instance.updateUserProfile(multipartBody, additionalParam)
            if (response.isSuccessful) {
                Log.d("hi", response.toString())
            } else {
                Log.d("hi", response.toString() + "error")
            }


        }
    }


    val pickMedia = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        imageView.setImageURI(it)

    }


}