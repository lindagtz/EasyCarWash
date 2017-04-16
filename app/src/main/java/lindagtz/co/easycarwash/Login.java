package lindagtz.co.easycarwash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    Button iniciar;
    TextView registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciar = (Button) findViewById(R.id.btnLogin);
        registrar= (TextView) findViewById(R.id.Registralink);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciar= new Intent(Login.this, Maps.class);
                startActivity(iniciar);
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrar= new Intent(Login.this, Choose.class);
                startActivity(registrar);
            }
        });


    }
}
