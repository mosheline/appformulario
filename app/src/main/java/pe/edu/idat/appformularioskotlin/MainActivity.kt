package pe.edu.idat.appformularioskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pe.edu.idat.appformularioskotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )
        binding.btnIrRegistro.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        // Cambiar entre actividades
        //
        startActivity(Intent(applicationContext,RegistroActivity::class.java))
    }
}