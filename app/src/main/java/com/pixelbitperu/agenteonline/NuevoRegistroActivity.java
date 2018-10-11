package com.pixelbitperu.agenteonline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NuevoRegistroActivity extends AppCompatActivity {

    private EditText txtDNI, txtApePat, txtApeMat, txtNombre;
    private Spinner spTipoProducto;
    private Button btnFoto;

    private ProgressDialog progressDialog;

    private TextView mensaje1, mensaje2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_registro);

        txtDNI = findViewById(R.id.txtDNI);
        txtApePat = findViewById(R.id.txtApePat);
        txtApeMat = findViewById(R.id.txtApeMat);
        txtNombre = findViewById(R.id.txtNombre);
        spTipoProducto = findViewById(R.id.spTipoProducto);
        btnFoto = findViewById(R.id.btnFoto);

        mensaje1 = findViewById(R.id.mensaje1);
        mensaje2 = findViewById(R.id.mensaje2);

        //validando permisos para CAMARA para android 6 en adelante
        if (validarPermisosFoto()){
            btnFoto.setEnabled(true);
        }else {
            btnFoto.setEnabled(false);
        }


        //Lista para spTipoProducto
        ArrayList<String> comboTipoProducto  = new ArrayList<String>();
        comboTipoProducto.add(" Seleccione un Producto ");
        comboTipoProducto.add("Préstamo Empredor");
        comboTipoProducto.add("Préstamo Mype");
        comboTipoProducto.add("Préstamos RUS");
        comboTipoProducto.add("Tarjeta Multibeneficios");
        comboTipoProducto.add("Cuenta de ahorro");
        comboTipoProducto.add("Seguro Vida y Salud");


        //Cargando el adapter
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,comboTipoProducto);
        spTipoProducto.setAdapter(adapter);

        //Seteando lista en el adapter Spinner
        spTipoProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NuevoRegistroActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(NuevoRegistroActivity.this, "Debe seleccionar un producto", Toast.LENGTH_SHORT).show();
            }
        });


        progressDialog = new ProgressDialog(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    private boolean validarPermisosFoto() {
        //PRIMERO validamos la version Android en el dispositivo donde se ejecuta la aplicación
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        //SEGUNDO validamos ahora los permisos de CAMARA Y ESCRITURA EN MEMORIA
        if ((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        //validando si la version de android es correcta, pero no se dieron los permisos
        if((shouldShowRequestPermissionRationale(CAMERA))||(shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){

            cargarDialogoRecomendacion();
        }

        return false;
    }

    private void cargarDialogoRecomendacion() {
        
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setNuevoRegistroActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        mensaje1.setText("Buscando satélites GPS....");
        mensaje2.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    mensaje2.setText("Mi direccion es: \n" + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        NuevoRegistroActivity nuevoRegistroActivity;

        public NuevoRegistroActivity getNuevoRegistroActivity() {
            return nuevoRegistroActivity;
        }

        public void setNuevoRegistroActivity(NuevoRegistroActivity nuevoRegistroActivity) {
            this.nuevoRegistroActivity = nuevoRegistroActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            mensaje1.setText(Text);
            this.nuevoRegistroActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }


}
