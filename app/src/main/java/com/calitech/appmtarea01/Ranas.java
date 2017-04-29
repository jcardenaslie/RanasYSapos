package com.calitech.appmtarea01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ranas extends AppCompatActivity {

    private Button buttonSeguirJugando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranas);

        buttonSeguirJugando = (Button) findViewById(R.id.buttonSapoSeguirJugando);
        buttonSeguirJugando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //intent.putExtra(getString(R.string.key_name), name);
                startActivity(intent);
            }
        });
    }
}
