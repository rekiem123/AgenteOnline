package com.pixelbitperu.agenteonline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LoginActivity extends AppCompatActivity {

    //definiciendo las vistas
    private EditText txtEmail, txtContrasena;
    private Button btnIngresar;
    private ProgressDialog progressDialog;
    private TextView lblResetearContrasenia;

    //Declaración de objeto firebaseAuth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializamos la declaración FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Referencias a las vistas
        txtEmail = findViewById(R.id.txtEmail);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        lblResetearContrasenia = findViewById(R.id.lblResetearContrasenia);

        progressDialog = new ProgressDialog(this);

    }


    protected void onResume() {
        super.onResume();

        //Iniciamos sesión
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validamos campos antes del inicio de sesión
                //eliminando espacios vacios
                String email = txtEmail.getText().toString().trim();
                String password = txtContrasena.getText().toString().trim();

                progressDialog.setMessage("Conectando con los servidores");
                progressDialog.show();

                //verificamos que las cajas no esten vacias
                if (TextUtils.isEmpty(email)|| (TextUtils.isEmpty(password))){
                    Toast.makeText(LoginActivity.this, "Alguno de los datos de sesión está vacío", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {

                    //Iniciando autenticación
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,RegistrosActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(LoginActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();

                            }

                            progressDialog.dismiss();

                        }
                    });

                }


                }
        });

        lblResetearContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetearContraseniaActivity.class);
                startActivity(intent);
            }
        });


    }


}
