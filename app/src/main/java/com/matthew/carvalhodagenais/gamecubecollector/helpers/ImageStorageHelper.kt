package com.matthew.carvalhodagenais.gamecubecollector.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class ImageStorageHelper {
    companion object {

        private const val IMAGE_DIRECTORY = ""

        /**
         * Reads, returns an image from internal storage, and sets it to an ImageView
         *
         * @param path String Path to where the image is stored
         * @param name String Name of the image
         * @param imgView ImageView to set the bitmap to
         */
        suspend fun getBitmap(path: String, name: String, imgView: ImageView) = coroutineScope {
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

            }
        }
    }
}