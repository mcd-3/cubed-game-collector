package com.matthew.carvalhodagenais.gamecubecollector.helpers

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.Toast
import kotlinx.coroutines.coroutineScope
import java.io.*
import java.util.*

class ImageStorageHelper {
    companion object {

        private const val IMAGE_DIRECTORY = "coverArt"

        const val IMAGE_PATH = "data/data/com.matthew.carvalhodagenais.gamecubecollector/" +
                "app_${IMAGE_DIRECTORY}/"

        /**
         * Returns the absolute path of an image
         *
         * @return String
         */
        fun getImageWithPath(name: String): String {
            return IMAGE_PATH + name
        }

        /**
         * Generates a unique image name using UUID
         *
         * @return String
         */
        fun generateUniqueImageName(): String {
            return "${UUID.randomUUID()}_cover.png"
        }

        /**
         * Saves a bitmap image to the internal storage
         *
         * @param context
         * @param bitmap
         * @param name
         */
        suspend fun save(context: Context, bitmap: Bitmap, name: String) = coroutineScope {
            val contextWrapper = ContextWrapper(context.applicationContext)
            val directory: File = contextWrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
            val path = File(directory, name)
            var outputStream: FileOutputStream? = null

            try {
                outputStream = FileOutputStream(path)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            } catch (e: Exception) {
                Toast.makeText(context, "Could not save file", Toast.LENGTH_SHORT).show()
            } finally {
                try {
                    outputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * Reads, returns an image from internal storage, and sets it to an ImageView
         *
         * @param path String Path to where the image is stored
         * @param name String Name of the image
         * @param imgView ImageView to set the bitmap to
         */
        suspend fun getBitmapAsync(path: String, name: String, imgView: ImageView) = coroutineScope {
            try {
                val image = File(path, name)
                val bitmap = BitmapFactory.decodeStream(FileInputStream(image))
                imgView.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        /**
         * Reads and returns an image from internal storage
         *
         * @param path String Path to where the image is stored
         * @param name String Name of the image
         * @return Bitmap Image that was read
         */
        fun getBitmap(path: String, name: String): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val image = File(path, name)
                bitmap = BitmapFactory.decodeStream(FileInputStream(image))
            } catch (e: FileNotFoundException) {
                bitmap = null
                e.printStackTrace()
            }
            return bitmap
        }

        /**
         * Deletes an image from the device
         *
         * @param path String Path to where the image is stored
         * @param name String Name of the image
         */
        suspend fun deleteImage(path: String, name: String) = coroutineScope {
            try {
                val image = File(path, name)
                image.delete()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}