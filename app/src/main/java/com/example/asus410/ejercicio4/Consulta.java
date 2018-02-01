package com.example.asus410.ejercicio4;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Consulta extends AppCompatActivity {

    private EditText contacto, tlf;
   private Spinner s;
    private Button botonConsulta, botonTlf;
    private Bundle bundle;
    private AdaptadorBD db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        s = (Spinner) findViewById(R.id.spinnerConsulta);
        contacto = (EditText) findViewById(R.id.editTextContactoConsulta);
        tlf = (EditText) findViewById(R.id.editTextTlfConsulta);
        botonConsulta = (Button) findViewById(R.id.consultaBoton);
        botonTlf = (Button) findViewById(R.id.BotonLlamaTlf);

        bundle = getIntent().getExtras();

        // instancia de la base de datos
        db = new AdaptadorBD(this);

        if (bundle.getInt("CLAVE")==0) consultaId(db);
        //evento onClick botón 'ver datos contacto'
        botonConsulta.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {

                if(bundle.getInt("CLAVE")==1){
                    ImprimeNombre();
                }else{
                    //muestra datos del contacto seleccionado
                    Imprime();
                }

                botonTlf.setEnabled(true);
            }
        });

        //evento onClick botón "llamar por telefono"
        botonTlf.setOnClickListener(new ImageButton.OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(View v) {

                String tlfS = tlf.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tlfS));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    // imprime los datos del contacto seleccionado en el spinner
    // en las View correspondientes
    public void Imprime() {

        // selecciona el elemento cursor escogido en el Spinner
        Cursor c = (Cursor) s.getSelectedItem();

        // Establece en el cuadro de texto contacto (nombre)
        // el nombre del contacto
        contacto.setText(c.getString(1));

        // Establece en el cuadro de texto del teléfono el teléfono del contacto
        tlf.setText(c.getString(3));

    }

    public void ImprimeNombre() {

        try {
        Cursor c = db.getContactoNombre(contacto.getText().toString());


            SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
                    android.R.layout.simple_spinner_item, c,
                    new String[]{"_id"}, new int[]{android.R.id.text1});

            //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // establecer el adaptador para el spinner s
            s.setAdapter(adapter2);

            //s.setSelection(c.getInt(0));

            contacto.setText(c.getString(1));
            tlf.setText(c.getString(2));

        }catch (Exception e){
            Toast.makeText(this,"no exite el contacto",Toast.LENGTH_LONG).show();
        }



    }

    public void consultaId(AdaptadorBD db){

        // abre la base de datos
        db.open();

        //obtiene todos los contactos de la BD
        Cursor cursor = db.getTodosContactos();

        // adaptador para manejar los ID de contactos devueltos por la consulta
        // (contexto, layout-adaptador, objeto cursor, datos a mostrar
        // coge solo de la consulta el _id => la clave primaria => ID contactos
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, cursor,
                new String[]{"_id"}, new int[]{android.R.id.text1});

        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // establecer el adaptador para el spinner s
        s.setAdapter(adapter2);

        //total de contactos devueltos en consulta
        int numElementos = cursor.getCount();

        //si no hay datos
        if (numElementos == 0) {
            // ocultar elementos
            botonConsulta.setEnabled(false);
            s.setEnabled(false);
            contacto.setEnabled(false);
            tlf.setEnabled(false);
            Toast.makeText(this,
                    "Base de Datos Vacía. Inserte contactos en Menu Principal",
                    Toast.LENGTH_LONG).show();
        }

        // se cierra la BD => ya no se necesita que esté abierta
        db.close();

    }

    public void consultaNombre(AdaptadorBD db){
        // abre la base de datos
        db.open();

        //obtiene todos los contactos de la BD
        Cursor cursor = db.getTodosContactos();

        //total de contactos devueltos en consulta
        int numElementos = cursor.getCount();

        //si no hay datos
        if (numElementos == 0) {
            // ocultar elementos
            botonConsulta.setEnabled(false);
            s.setEnabled(false);
            contacto.setEnabled(false);
            tlf.setEnabled(false);
            Toast.makeText(this,
                    "Base de Datos Vacía. Inserte contactos en Menu Principal",
                    Toast.LENGTH_LONG).show();
        }

        // se cierra la BD => ya no se necesita que esté abierta
        db.close();
    }

}

