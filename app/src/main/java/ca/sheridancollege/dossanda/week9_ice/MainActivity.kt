package ca.sheridancollege.dossanda.week9_ice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.sheridancollege.dossanda.week9_ice.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btSave = binding.btSave
        val btOpen = binding.btOpen
        val btDelete = binding.btDelete

        //Toolbar
        setSupportActionBar(binding.toolbar)

        //Functions
        btSave.setOnClickListener {save()}
        btOpen.setOnClickListener {read()}
        btDelete.setOnClickListener {delete()}
    }

    fun save() {
        try {
            val fileName = binding.tfFileName.text.toString()
            val fileContent = binding.ptDataInfo.text.toString()

            openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(fileContent.toByteArray())
            }
            clear()
            Toast.makeText(this, "Saved to $fileName", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error saving the file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun read() {
        try {
            val fileName = binding.tfFileName.text.toString()
            val fileContents = binding.lbFileContent
            applicationContext.openFileInput(fileName).use { fileInputStream ->
                BufferedReader(InputStreamReader(fileInputStream)).use { bufferedReader ->
                    val stringBuilder = StringBuilder()
                    var text: String?
                    while (bufferedReader.readLine().also { text = it } != null) {
                        stringBuilder.append(text).append("\n")
                    }
                    fileContents.text = stringBuilder.toString()
                }
            }
            Toast.makeText(this, "Read from $fileName", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error reading the file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun delete() {
        try {
            val fileName = binding.tfFileName.text.toString()
            deleteFile(fileName)

            clear()

            Toast.makeText(this, "Deleted $fileName", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error deleting the file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun clear(){
        val fileName = binding.tfFileName
        val fileContents = binding.lbFileContent
        val fileContent = binding.ptDataInfo

        fileName.text = null
        fileContents.text = null
        fileContent.text = null
    }


}