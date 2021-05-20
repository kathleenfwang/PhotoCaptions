package edu.foothill.kwang.photostore

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    val sampleimage = "https://static.toiimg.com/photo/msid-68374658/68374658.jpg?2359844"
    lateinit var imageView: ImageView
    lateinit var caption: EditText
    lateinit var tags: TextView
    private var imageUri: Uri? = null
    private val SELECT_PICTURE = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val choose_btn = findViewById<Button>(R.id.btn_choose_img)
        val take_photo_btn = findViewById<Button>(R.id.btn_take_photo)
        caption = findViewById(R.id.et_caption)
        tags = findViewById(R.id.tv_tags)
        imageView = findViewById<ImageView>(R.id.iv_userImg)
        Picasso
                .get()
                .load(sampleimage)
                .into(imageView);
        choose_btn.setOnClickListener {
            imageChooser()
        }
        take_photo_btn.setOnClickListener {
            takePhoto()
        }
        getImageData(Image(mUrl = sampleimage))

    }
    private fun getImageData(userImage: Image) {
        val apiService = RestApiService()
        apiService.addImage(userImage) {
            if (it != null) {
                Log.d("main", it.description.toString())
                // cannot put .text for edit text editText.text = "some value" //Won't work,
                val captionText = it.description["captions"].toString()
                val end = captionText.indexOf(",") - 1
                val start = captionText.indexOf("=") + 1
                val captionTextSliced = captionText.slice(start..end)
                caption.setText(captionTextSliced)
                val tagText = getTags(it.tags).toString()
                val lenTagText =tagText.slice(1..tagText.length - 2)
                tags.text = "$lenTagText"
            }
            else {
                Log.d("get Image", "Error!")
            }
        }
    }

    private fun getTags(tags: List<Tag>): MutableList<String> {
        val tagArray = mutableListOf<String>()
        for (i in 0..tags.size-1)
        {
            if (tags[i].confidence > 0.90)
            tagArray.add("#${tags[i].name}")
        }
        return tagArray
    }

    private fun imageChooser() {
        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            imageUri = data?.data
            Log.d("Main Gallery Photo", imageUri.toString())
            imageView.setImageURI(imageUri)
            getImageData(Image(imageUri.toString()))
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            val imageByteArray = imageToBitmap(imageView)
            val imageAsString: String = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
            Log.d("Main Take Photo", imageAsString.toString())
            getImageData(Image(imageByteArray.toString()))
        }
    }
    private fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }
    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Log.d("Main", "No camera")
        }
    }
}


