package cit.edu.garol.campus.core.network

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

object MultipartUtils {

    fun createPartFromUri(
        context: Context,
        uri: Uri?,
        partName: String = "image"
    ): MultipartBody.Part? {

        if (uri == null) {
            return null
        }

        val inputStream =
            context.contentResolver.openInputStream(uri)
                ?: return null

        val file = File(
            context.cacheDir,
            "upload_${System.currentTimeMillis()}.jpg"
        )

        FileOutputStream(file).use { output ->
            inputStream.copyTo(output)
        }

        val requestBody =
            file.asRequestBody(
                "image/*".toMediaTypeOrNull()
            )

        return MultipartBody.Part.createFormData(
            partName,
            file.name,
            requestBody
        )
    }

    fun createRequestBody(
        value: String
    ): RequestBody {

        return RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            value
        )

    }

}