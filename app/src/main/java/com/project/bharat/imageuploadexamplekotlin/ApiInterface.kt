package com.project.bharat.imageuploadexamplekotlin

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @Multipart
    @POST("photo_upload.php")
    suspend fun updateUserProfile(
        @Part image: MultipartBody.Part,
        @Part("username") additionalParam: RequestBody
    ): Response<Root>
}