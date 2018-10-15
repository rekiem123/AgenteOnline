package com.pixelbitperu.agenteonline;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pixelbitperu.agenteonline.objetos.Clientes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NuevoRegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtDNI, txtApePat, txtApeMat, txtNombre, txtRazonSocial, txtDireccion;
    private Spinner spTipoProducto;
    private Button btnFoto,btnGuardar;


    private ProgressDialog progressDialog;

    public String mensajeGPS,direccion,latitudCapturada,longitudCapturada;;

//    public double latitud,longitud;

//    private TextView mensaje1, mensaje2;

    //*CAM
    private ImageView ivFoto;
    Intent i;
    final static int cons = 0;
    Bitmap fotoCapturada;

    //Variables de seteo de coordenadas
    private TextView txtLatitud, txtLongitud;

    //RealTimeDataBase
    private DatabaseReference Clientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_registro);

        txtDNI = findViewById(R.id.txtDNI);
        txtApePat = findViewById(R.id.txtApePat);
        txtApeMat = findViewById(R.id.txtApeMat);
        txtNombre = findViewById(R.id.txtNombre);
        spTipoProducto = findViewById(R.id.spTipoProducto);
//        btnFoto = findViewById(R.id.btnFoto);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccion = findViewById(R.id.txtDireccion);

//        mensaje1 = findViewById(R.id.mensaje1);
//        mensaje2 = findViewById(R.id.mensaje2);

        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);

        btnGuardar = findViewById(R.id.btnGuardar);

        //RealTimedataBase
        Clientes = FirebaseDatabase.getInstance().getReference();

        //*CAM
        checkCameraPermission();
        init();

//        //validando permisos para CAMARA para android 6 en adelante
//        if (validarPermisosFoto()){
//            btnFoto.setEnabled(true);
//        }else {
//            btnFoto.setEnabled(false);
//        }


        //Lista para spTipoProducto
        ArrayList<String> comboTipoProducto = new ArrayList<String>();
        comboTipoProducto.add(" Seleccione un Producto ");
        comboTipoProducto.add("Préstamo Empredor");
        comboTipoProducto.add("Préstamo Mype");
        comboTipoProducto.add("Préstamos RUS");
        comboTipoProducto.add("Tarjeta Multibeneficios");
        comboTipoProducto.add("Cuenta de ahorro");
        comboTipoProducto.add("Seguro Vida y Salud");


        //Cargando el adapter
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comboTipoProducto);
        spTipoProducto.setAdapter(adapter);

        //Seteando lista en el adapter Spinner
        spTipoProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(NuevoRegistroActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        progressDialog = new ProgressDialog(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }


    //*CAM
    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }

    public void init() {
        btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(this);
        ivFoto = findViewById(R.id.ivFoto);
    }

//    private boolean validarPermisosFoto() {
//        //PRIMERO validamos la version Android en el dispositivo donde se ejecuta la aplicación
//        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
//            return true;
//        }
//        //SEGUNDO validamos ahora los permisos de CAMARA Y ESCRITURA EN MEMORIA
//        if ((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
//            return true;
//        }
//        //validando si la version de android es correcta, pero no se dieron los permisos
//        if((shouldShowRequestPermissionRationale(CAMERA))||(shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
//
//            cargarDialogoRecomendacion();
//        }else {
//            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
//        }
//
//        return false;
//    }


//    private void cargarDialogoRecomendacion() {
//        AlertDialog.Builder dialogo = new AlertDialog.Builder(NuevoRegistroActivity.this);
//        dialogo.setTitle("Permisos Desactivados");
//        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");
//
//        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
//            }
//        });
//        dialogo.show();
//
//    }

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

//        mensaje1.setText("Buscando satélites GPS....");
//        mensaje2.setText("");

//        mensaje1=("Buscando satélites GPS....");
        direccion=("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


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
//                    mensaje2.setText("Mi direccion es: \n" + DirCalle.getAddressLine(0));

                    direccion=(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
            String Latitud = ""+loc.getLatitude();
            String Longitud = ""+loc.getLongitude();
//            mensaje1.setText(Text);

            latitudCapturada=(Latitud);
            longitudCapturada=(Longitud);
            this.nuevoRegistroActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
//            mensaje1.setText("GPS Desactivado");
            Toast.makeText(nuevoRegistroActivity, "GPS Desactivado", Toast.LENGTH_SHORT).show();
//            mensajeGPS=("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
//            mensaje1.setText("GPS Activado");

            Toast.makeText(nuevoRegistroActivity, "GPS Activado", Toast.LENGTH_SHORT).show();

//            mensajeGPS=("GPS Activado");
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

    //CAM
    public void onClick(View v) {
        int id;
        id = v.getId();
        switch (id) {
            case R.id.btnFoto:
                i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, cons);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle ext = data.getExtras();
            fotoCapturada = (Bitmap) ext.get("data");
            ivFoto.setImageBitmap(fotoCapturada);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registrarCliente();

            }
        });


    }

    public void registrarCliente() {
        String dni = txtDNI.getText().toString();
        String apePat = txtApePat.getText().toString();
        String apeMat = txtApeMat.getText().toString();
        String nombre = txtNombre.getText().toString();
        String razonSocial = txtRazonSocial.getText().toString();
        String direccionFormulario = txtDireccion.getText().toString();
        String tipoProduco = spTipoProducto.getSelectedItem().toString();

        String latitud = latitudCapturada.toString();
        String longitud = longitudCapturada.toString();
        String direccionAutomatica = direccion.toString();
        String fotoID = txtDNI.getText().toString();
        
        if(!TextUtils.isEmpty(dni)){
            
            String clienteID = Clientes.push().getKey();
            Clientes clientes = new Clientes(clienteID,dni,apePat,apeMat,nombre,razonSocial,direccionFormulario,
                    tipoProduco,latitud,longitud,direccionAutomatica,fotoID);
            Clientes.child("Clientes").child(clienteID).setValue(clientes);

            Toast.makeText(this, "Cliente Registrado", Toast.LENGTH_SHORT).show();
        }else {
            txtLatitud.setText(latitudCapturada.toString());
            txtLongitud.setText(longitudCapturada.toString());

            Toast.makeText(this, "De ingresar un DNI", Toast.LENGTH_SHORT).show();
            
        }

        
        



    }

}
