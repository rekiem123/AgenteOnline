package com.pixelbitperu.agenteonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RegistrosActivity extends AppCompatActivity {

    private FloatingActionButton fabRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabRegistro = findViewById(R.id.fabRegistro);

    }


    @Override
    protected void onResume() {
        super.onResume();

        fabRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistrosActivity.this,NuevoRegistroActivity.class);
                startActivity(intent);


            }
        });

    }
}
