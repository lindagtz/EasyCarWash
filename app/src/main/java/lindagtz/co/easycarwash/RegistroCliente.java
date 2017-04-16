package lindagtz.co.easycarwash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroCliente extends AppCompatActivity {
Button regCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrocli);

        regCancelar = (Button) findViewById(R.id.RegCancel);

        regCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regCancelar= new Intent(RegistroCliente.this, Login.class);
                startActivity(regCancelar);
            }
        });








    }













}
