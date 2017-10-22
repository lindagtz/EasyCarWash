package lindagtz.co.easycarwash;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistroCliente extends AppCompatActivity {

//declaramos todas las variables a usar
    EditText name, email, password, direccion;
    TextView auth_id;
    Button regCancelar, regGuardar;
    TextInputLayout inputEmail;
    boolean Inpmail=false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cli);

        //Edittext para registrar en la BD
        name = (EditText) findViewById(R.id.edNamec);
        email = (EditText) findViewById(R.id.edEmailc);
        password = (EditText) findViewById(R.id.edPasswordc);
        direccion = (EditText) findViewById(R.id.edDireccionc);
        auth_id=(TextView) findViewById(R.id.txtperm);
        regGuardar = (Button) findViewById(R.id.regGuardar);
        inputEmail=(TextInputLayout)findViewById(R.id.inpemail);

        final String emaail = email.getText().toString();
        final String namee = name.getText().toString();
        final String pass = password.getText().toString();
        final String dir = direccion.getText().toString();



        regGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || password.getText().toString().isEmpty() || email.getText().toString().isEmpty() || direccion.getText().toString().isEmpty()){
                    Snackbar.make(v, "Por favor llene todos los datos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();Log.i("ss",email.getText().toString());
                    Log.i("so",emaail);


                } else if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()==false){
                    Snackbar.make(v, "Correo inválido", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //inputEmail.setError("Correo inválido");
                    Inpmail=false;
                    Log.i("ss",email.getText().toString());
                    Log.i("so",emaail);

                }else {
                    Inpmail = true;
                    inputEmail.setError(null);

                    //new RegistroCliente.CargarDatos().execute("http://easycarwash.hol.es/registrocli.php?name=" + name.getText().toString() + "&email=" + email.getText().toString()+ "&password=" + password.getText().toString()+ "&direccion=" + direccion.getText().toString()+ "&telefono=" + telefono.getText().toString()+ "&tipo=" + tipo.getText().toString()+ "&color=" + color.getText().toString()+ "&marca=" + marca.getText().toString() + "&auth_id=" + auth_id.getText().toString());
                    new RegistroCliente.CargarDatos().execute("http://easycarwash.hol.es/registrocli.php?name=" +
                            name.getText().toString() + "&email=" + email.getText().toString() + "&password=" +
                            password.getText().toString() + "&direccion=" +
                            direccion.getText().toString() + "&auth_id=" + auth_id.getText().toString());
                }

            }
        });


        regCancelar = (Button) findViewById(R.id.regCancelar);

        regCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regCancelar= new Intent(RegistroCliente.this, Login.class);
                startActivity(regCancelar);
            }
        });


    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Registro exitoso!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Intent Login= new Intent(RegistroCliente.this, Login.class);
            startActivity(Login);
        }
    }
    private class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                // username.setText(ja.getString(1));
                //etTelefono.setText(ja.getString(2));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }















}
