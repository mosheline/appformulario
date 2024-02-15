package pe.edu.idat.appformularioskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import pe.edu.idat.appformularioskotlin.databinding.ActivityRegistroBinding
import pe.edu.idat.appformularioskotlin.util.AppMensaje
import pe.edu.idat.appformularioskotlin.util.TipoMensaje

class RegistroActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityRegistroBinding
    private var estadocivil = ""
    private val listaPersonas = ArrayList<String>()
    private val listaPreferencias = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_registro)

        ArrayAdapter.createFromResource(
            applicationContext, R.array.estadocivil,
            android.R.layout.simple_spinner_item
        ).also {
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spestadocivil.adapter = adapter
        }

        binding.btnRegistrarPersona.setOnClickListener(this)
        binding.btnListarPersonas.setOnClickListener(this)
        binding.spestadocivil.onItemSelectedListener = this
        binding.chkDeporte.setOnClickListener(this)
        binding.chkMusica.setOnClickListener(this)
        binding.chkOtros.setOnClickListener(this)
    }

    fun validarNombresApellidos():Boolean{
        var respuesta = true
        if(binding.etNombres.text.toString().trim().isEmpty()){
            binding.etNombres.isFocusableInTouchMode=true
            binding.etNombres.requestFocus()
            respuesta = false
        }else if (binding.etApellidos.text.toString().trim().isEmpty()){
            binding.etApellidos.isFocusableInTouchMode=true
            binding.etApellidos.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    fun validarGenero():Boolean{
        var respuesta = true
        if(binding.rgGenero.checkedRadioButtonId == -1) {
            respuesta = false
        }
        return respuesta
    }

    fun validarPreferencia():Boolean{
        var respuesta = true
        if(binding.chkDeporte.isChecked || binding.chkMusica.isChecked || binding.chkOtros.isChecked)
            respuesta = true
        return respuesta
    }

    fun validarEstadoCivil():Boolean{
        return estadocivil != ""
    }

    fun validarFormulario():Boolean{
        var respuesta = false
        var mensaje = ""

        if (!validarNombresApellidos()) {
             //mensaje = "Ingrese nombre y apellido"
            mensaje = getString(R.string.errorNombreApellido)
        }else if(!validarGenero()){
            mensaje = getString(R.string.errorValidarGenero)
        }else if(!validarEstadoCivil()){
            mensaje = getString(R.string.errorEstadoCivil)
        }else if(!validarPreferencia()){
            mensaje = getString(R.string.errorPreferencia)
        }else{
            respuesta = true
        }

        if(!respuesta) AppMensaje.enviarMensaje(binding.root, mensaje, TipoMensaje.ERROR)

        return respuesta
    }

    override fun onClick(v: View?) {
        if (v!! is CheckBox){
            agregarQuitarPreferencia(v!!)
        }else{
            when (v!!.id){
            R.id.btnListarPersonas -> irListadoPersonas()
            R.id.btnRegistrarPersona -> registrarPersonas()
            }
        }
    }

    private fun agregarQuitarPreferencia(v: View) {
        val checkBox = v as CheckBox
        if(checkBox.isChecked){
            listaPreferencias.add(checkBox.toString())
        }else{
            listaPreferencias.remove(checkBox.toString())
        }
    }

    private fun registrarPersonas() {
        if(validarFormulario()){
            var infoPersona = binding.etNombres.text.toString()+" "+
                    binding.etApellidos.text.toString()+" "+
                    obtenerGenero()+" " +
                    listaPreferencias.toArray()+" "+
                    estadocivil+" "+
                    binding.swnotificacion.isChecked
            listaPersonas.add(infoPersona)
            AppMensaje.enviarMensaje(binding.root, getString(R.string.mensajeRegistro),TipoMensaje.CORRECTO)
        }
    }

    fun obtenerGenero():String{
        return if(binding.rgGenero.checkedRadioButtonId == (R.id.rbMasculino))
            binding.rbMasculino.text.toString()
        else binding.rbFemenino.text.toString()
    }

    private fun irListadoPersonas() {
        var intenListado = Intent(applicationContext, ListaActivity::class.java).apply {
            putExtra("listaPersonas",listaPersonas)
        }
        startActivity(intenListado)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        estadocivil = if (position > 0) parent!!.getItemAtPosition(position).toString() else ""

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}