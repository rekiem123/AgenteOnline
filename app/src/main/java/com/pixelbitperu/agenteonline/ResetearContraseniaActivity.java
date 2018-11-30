package com.pixelbitperu.agenteonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetearContraseniaActivity extends AppCompatActivity {

    private EditText txtEmail;
    private Button btnResetearContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetear_contrasenia);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnResetearContrasena = (Button) findViewById(R.id.btnResetearContrasena);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnResetearContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetearContraseniaActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(ResetearContraseniaActivity.this, "Listo! Por favor verifique su bandeja de correo", Toast.LENGTH_LONG).show();
            }
        });
    }
}
