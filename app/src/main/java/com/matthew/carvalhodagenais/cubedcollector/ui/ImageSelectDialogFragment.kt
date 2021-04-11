package com.matthew.carvalhodagenais.cubedcollector.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.matthew.carvalhodagenais.cubedcollector.R
import com.matthew.carvalhodagenais.cubedcollector.databinding.FragmentImageSelectDialogBinding

class ImageSelectDialogFragment: AppCompatDialogFragment() {

    private lateinit var listener: ImageSelectDialogInterface

    companion object {
        const val REQUEST_CAMERA = 1
        const val REQUEST_GALLERY = 2
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val binding = DataBindingUtil.inflate<FragmentImageSelectDialogBinding>(
            inflater, R.layout.fragment_image_select_dialog, null, false
        )

        binding.cameraInput.setOnClickListener(cameraOptionOnClick)
        binding.galleryInput.setOnClickListener(galleryOptionOnClick)

        val builder = MaterialAlertDialogBuilder(activity)

        builder.setView(binding.root)
            .setTitle(getString(R.string.game_add_edit_change_image_title))

        return builder.create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                val extras: Bundle? = data?.extras
                val bitmap: Bitmap = extras?.get("data") as Bitmap
                listener.changeImageBitmap(bitmap)
            } else {
                Toast.makeText(context, getString(R.string.camera_cancelled), Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == REQUEST_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                val image: Uri? = data?.data
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, image)
                listener.changeImageBitmap(bitmap)
            } else {
                Toast.makeText(context, getString(R.string.gallery_cancelled), Toast.LENGTH_LONG).show()
            }
        }
        dialog!!.dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ImageSelectDialogInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$context must implement ProfilePickerDialogListener")
        }
    }

    private val cameraOptionOnClick = View.OnClickListener {
        if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA)
                }
            }
        } else {
            Toast.makeText(context, getString(R.string.no_camera), Toast.LENGTH_LONG).show()
        }
    }

    private val galleryOptionOnClick = View.OnClickListener {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes: Array<String> = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    interface ImageSelectDialogInterface {
        fun changeImageBitmap(bitmap: Bitmap)
    }

}