package id.andiwijaya.story.core.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import id.andiwijaya.story.core.Constants.BYTE_MULTIPLIER
import id.andiwijaya.story.core.Constants.DOT_JPG
import id.andiwijaya.story.core.Constants.DateFormat.FILE_NAME
import id.andiwijaya.story.core.Constants.FIVE
import id.andiwijaya.story.core.Constants.IMAGE_JPEG
import id.andiwijaya.story.core.Constants.INITIAL_COMPRESS_QUALITY
import id.andiwijaya.story.core.Constants.MAX_STREAM_LENGTH
import id.andiwijaya.story.core.Constants.MULTIPART_BODY_NAME
import id.andiwijaya.story.core.Constants.ZERO
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object FileUtil {

    private val timeStamp: String = SimpleDateFormat(
        FILE_NAME,
        Locale.ENGLISH
    ).format(System.currentTimeMillis())

    fun String.convertToRequestBody(): RequestBody {
        return toRequestBody("text/plain".toMediaType())
    }

    fun File.toMultiBodyPart(fileName: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            MULTIPART_BODY_NAME,
            fileName,
            this.asRequestBody(IMAGE_JPEG.toMediaTypeOrNull())
        )
    }

    fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, DOT_JPG, storageDir)
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(BYTE_MULTIPLIER)
        var len: Int
        while (inputStream.read(buf).also { len = it } > ZERO) outputStream.write(buf, ZERO, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    fun File.reduceFileImage(): File {
        val bitmap = BitmapFactory.decodeFile(this.path)

        var compressQuality = INITIAL_COMPRESS_QUALITY
        var streamLength: Int

        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= FIVE
        } while (streamLength > MAX_STREAM_LENGTH)

        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(this))

        return this
    }

}