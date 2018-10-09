package com.pixelbitperu.agenteonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NuevoRegistroActivity extends AppCompatActivity {

    private EditText txtDNI, txtApePat, txtApeMat, txtNombre;
    private Spinner spTipoProducto;
    private Button btnFoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_registro);
    }
}
