package edu.foothill.kwang.photostore

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream


private const val COMPVISION_URL = "https://kathleencompvision.cognitiveservices.azure.com/"
class MainActivity : AppCompatActivity() {

    val REACT_APP_CV_AZURE_KEY = "847a34790f094bbb897d84b1f258ac2c"
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
        choose_btn.setOnClickListener {
            imageChooser()
        }
        take_photo_btn.setOnClickListener {
            takePhoto()
        }
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(COMPVISION_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val imageService = retrofit.create(CVService::class.java)
        imageService.searchImages("Tags,Description").enqueue(object : Callback<Any>
        {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

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
            caption.setText("New image!!!")
            tags.setText("Sample tags!!")
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            val imageByteArray = imageToBitmap(imageView)
            Log.d("Main Take Photo", imageByteArray.toString())
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