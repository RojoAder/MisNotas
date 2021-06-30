package rojo.ader.misnotas

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.jar.Manifest

class AgregarNotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        val btn_guardar = findViewById(R.id.btn_guardar) as Button

        btn_guardar.setOnClickListener {
            guardar_nota()
        }

    }

    fun guardar_nota() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                235
            )
        } else {
            guardar()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            235 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    guardar()
                } else {
                    Toast.makeText(this, "Error:permisos denegados", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    public fun guardar() {
        val titulo = findViewById(R.id.et_titulo) as EditText
        val contenido = findViewById(R.id.et_contenido) as EditText

        var titulo_et = titulo.text.toString()
        var contenido_et = contenido.text.toString()

        if (titulo_et == "" || contenido_et == "") {
            Toast.makeText(this, "Error: Campos vacíos", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val archivo = File(ubicacion(), titulo_et + "txt")
                val fos = FileOutputStream(archivo)
                fos.write(contenido_et.toByteArray())
                fos.close()
                Toast.makeText(
                    this,
                    "Se guardó el archivo en la carpeta pública",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error: no se guardó el archivo", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun ubicacion(): String {
        val carpeta = File(getExternalFilesDir(null), "notas")
        if (!carpeta.exists()) {
            carpeta.mkdir()
        }

        return carpeta.absolutePath
    }
}