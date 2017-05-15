package lindagtz.co.easycarwash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
    Button iniciar;
    TextView registrar;

    private EditText editTextEmail;
    private EditText editTextPassword;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private static final String WS_URL = "http://easycarwash.esy.es/rest/users/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciar = (Button) findViewById(R.id.btnLogin);
        registrar = (TextView) findViewById(R.id.Registralink);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkLogin(v);

                /*Intent iniciar = new Intent(Login.this, Maps.class);
                startActivity(iniciar);*/
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrar = new Intent(Login.this, Choose.class);
                startActivity(registrar);
            }
        });
    }


    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {

        Toast.makeText(Login.this, "Athenticating...", Toast.LENGTH_LONG).show();

        // Get text from email and passord field
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        Log.i("email", email);
        Log.i("password", password);

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email,password);

    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Login.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(WS_URL);
                Log.i("ws_url", url.toString());

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("data[User][email]", params[0])
                        .appendQueryParameter("data[User][password]", params[1]);
                String query = builder.build().getEncodedQuery();

                Log.i("ws query", query);

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                Log.i("response code", String.valueOf(response_code));

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    Log.i("response", "ok");

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.i("result", result.toString());

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    Log.e("response", "error");

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("postExec", result);


            // todo: parse json

            //this method will be running on UI thread

            pdLoading.dismiss();

            JSONObject mainObject = null;
            String send = null;
            try {
                mainObject = new JSONObject(result);
                send = mainObject.getString("send");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(send.equalsIgnoreCase("ok"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Toast.makeText(Login.this, "Login success :)", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Login.this, Maps.class);
                startActivity(intent);
                Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(Login.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(Login.this, "Login error :(", Toast.LENGTH_LONG).show();
            }
        }

    }


}
