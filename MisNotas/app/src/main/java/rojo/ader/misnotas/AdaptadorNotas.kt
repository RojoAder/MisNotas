package rojo.ader.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.lang.Exception

class AdaptadorNotas:BaseAdapter{
    var context:Context
    var notas = ArrayList<Nota>()

    constructor(context:Context,notas:ArrayList<Nota>){
    this.context= context
    this.notas=notas
    }

    override fun getCount(): Int {
        return notas.size
    }

    override fun getItem(position: Int): Any {
        return notas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout,null)
        var nota = notas[position]
        val tituloNota = vista.findViewById(R.id.titulo_nota) as TextView
        val contenido_nota = vista.findViewById(R.id.contenido_nota) as TextView
        val btn_borrar = vista.findViewById(R.id.btn_borrar) as ImageView

        tituloNota.setText(nota.titulo)
        contenido_nota.setText(nota.contenido)

        btn_borrar.setOnClickListener {
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    private fun eliminar(titulo:String){
        if(titulo==""){
            Toast.makeText(context,"Error: titulo vacío",Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archivo = File(ubicacion(),titulo+".txt")
                archivo.delete()

                Toast.makeText(context,"Se eliminó el archivo",Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(context,"Error al eliminar el archivo",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion():String{
        val carpeta = File(context?.getExternalFilesDir(null),"notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }

        return carpeta.absolutePath
    }
}