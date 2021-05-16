package edu.foothill.kwang.photostore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var caption: EditText
    lateinit var tags: TextView
    private var imageUri: Uri? = null
    private val SELECT_PICTURE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val choose_btn = findViewById<Button>(R.id.btn_choose_img)
        caption = findViewById(R.id.et_caption)
        tags = findViewById(R.id.tv_tags)
        imageView = findViewById<ImageView>(R.id.iv_userImg)
        choose_btn.setOnClickListener {
            imageChooser()
        }
    }
    fun imageChooser() {
        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            imageUri = data?.data
            Log.d("Main", imageUri.toString())
            imageView.setImageURI(imageUri)
            caption.setText("New image!!!")
            tags.setText("Sample tags!!")
        }
    }
}