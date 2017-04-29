package com.calitech.appmtarea01;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN";

    private int TABLE_SIZE = 9;
    private int totalMoves = 0;
    private int ranaColor;
    private int sapoColor;
    private int vacioColor;
    private int[] table = new int[4 + TABLE_SIZE];

    private boolean playing = true;

    private TextView textStatisticMoves;

    private ImageView[] placeHolders;

    private Button buttonGameRestart;
    private Button buttonInfoRanas;
    private Button buttonInfoSapos;

    private MediaPlayer soundRana;
    private MediaPlayer soundSapo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundRana = MediaPlayer.create(this,R.raw.rana);
        soundSapo = MediaPlayer.create(this,R.raw.sapo);

        soundRana.setOnCompletionListener(new MyCompletionListener());

        buttonGameRestart = (Button) findViewById(R.id.buttonRestartGame);
        buttonGameRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartGame();
            }
        });
        buttonInfoRanas = (Button) findViewById(R.id.buttonRanasInfo);
        buttonInfoRanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoInfoRanas();
            }
        });
        buttonInfoSapos = (Button) findViewById(R.id.buttonSaposInfo);
        buttonInfoSapos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoInfoSapos();
            }
        });

        textStatisticMoves = (TextView) findViewById(R.id.textStatisticsMoves);
        textStatisticMoves.setText((totalMoves + ""));

        ranaColor = Color.parseColor("#0088FF");
        sapoColor = Color.parseColor("#FF0000");
        vacioColor = Color.parseColor("#FFFFFF");

        placeHolders = new ImageView[TABLE_SIZE];

        for (int i = 0 ; i < TABLE_SIZE ; i++){
            String imageViewId = "placeHolder"+i;
            int resID = getResources().getIdentifier(imageViewId, "id", getPackageName());
            placeHolders[i] = (ImageView) findViewById(resID);
            placeHolders[i].setOnClickListener(new MyListener());
        }


        RestartGame();
    }

    private void GoInfoRanas() {
        Intent intent = new Intent(this,Ranas.class);
        //intent.putExtra(getString(R.string.key_name), name);
        startActivity(intent);
    }

    private void GoInfoSapos() {
        Intent intent = new Intent(this,Sapos.class);
        //intent.putExtra(getString(R.string.key_name), name);
        startActivity(intent);
    }


    private void Move(int currentPosition){
        /*
        ranas = 1 , parten en la izquierda
        sapos = 2 , parten en la derehca
         */
        if( table[currentPosition] == 1){
            //Salta si el la casilla de la derecha es vacia
            if(currentPosition + 1 < TABLE_SIZE && table[currentPosition + 1] == 0){
                RanaPaintMoves(currentPosition ,currentPosition + 1 );
            }
            //Salta si el la casilla de la derecha es sapo o rana y la que le sigue al sapo o rana es vacia
            else if(currentPosition + 2 < TABLE_SIZE && table[currentPosition + 2] == 0 && (table[currentPosition + 1] == 2 || table[currentPosition + 1] == 1) ){
                RanaPaintMoves(currentPosition ,currentPosition + 2 );
            }
        }
        // Si la imagen apretada es sapo
        else if(table[currentPosition] == 2){


            //Salta si el la casilla de la derecha es vacia
            if(currentPosition - 1 >= 0 && table[currentPosition - 1] == 0 ){
                SapoPaintMoves(currentPosition ,currentPosition - 1 );
            }
            //Salta si el la casilla de la derecha es sapo o rana y la que le sigue al sapo o rana es vacia
            else if(currentPosition - 2 >= 0 && table[currentPosition - 2] == 0 && (table[currentPosition - 1] == 2 || table[currentPosition - 1] == 1) ){
                SapoPaintMoves(currentPosition ,currentPosition - 2 );
            }
        }
        CheckGameStatus();
    }

    private boolean IsGameOver(){
        for (int i = 0; i < TABLE_SIZE ; i++){
            /*
            ranas = 1 , parten en la izquierda
            sapos = 2 , parten en la derehca
         */
            // Si la imagen apretada es rana
            if( table[i] == 1){
                //Salta si el la casilla de la derecha es vacia
                if( (i + 1)  < TABLE_SIZE && table[i + 1] == 0 ){
                    Log.v(getApplicationContext().toString(), "Casilla: " + i + "se puede mover");
                    playing = true;
                    break;
                }
                //Salta si el la casilla de la derecha es sapo o rana y la que le sigue al sapo o rana es vacia
                else if( (i + 2) < TABLE_SIZE && table[i + 2] == 0 && (table[i + 1] == 2 || table[i + 1] == 1) ){
                    Log.v(getApplicationContext().toString(), "Casilla: " + i + " se puede mover");
                    playing = true;
                    break;
                }
                // No puede moverse
                else{
                    playing = false;
                    Log.v(getApplicationContext().toString(), "Casilla: " + i + " no se puede mover");
                }
            }
            // Si la imagen apretada es sapo
            else if(table[i] == 2){
                //Salta si el la casilla de la derecha es vacia
                if( (i - 1) >= 0 && table[i - 1] == 0 ){
                    Log.v(getApplicationContext().toString(), "Casilla: " + i + "se puede mover");
                    playing = true;
                    break;
                }
                //Salta si el la casilla de la derecha es sapo o rana y la que le sigue al sapo o rana es vacia
                else if( (i - 2) >= 0 &&  table[i - 2] == 0 && (table[i - 1] == 2 || table[i - 1] == 1) ){
                    Log.v(getApplicationContext().toString(), "Casilla: " + i + " se puede mover");
                    playing = true;
                    break;
                }
                // No puede moverse
                else{
                    playing = false;
                    Log.v(getApplicationContext().toString(), "Casilla: " + i + " no se puede mover");
                }
            }else{
                Log.v(getApplicationContext().toString(), "Casilla: " + i + " no se puede mover es vacia ");
            }
        }
        return playing;
    }

    private void SapoPaintMoves(int position, int nexPosition){
        soundSapo.start();

        String positionId = "placeHolder"  +   (position);
        String nextPositionId = "placeHolder"  +   (nexPosition);

        int positionRes = getResources().getIdentifier(positionId, "id", getPackageName());
        int nextPositionRes = getResources().getIdentifier(nextPositionId, "id", getPackageName());

        ImageView positionIV = (ImageView) findViewById(positionRes);
        ImageView nextPositionIV = (ImageView) findViewById(nextPositionRes);


        positionIV.setImageResource(R.drawable.hoja);
        table[position] = 0;
        nextPositionIV.setImageResource(R.drawable.sapo);
        table[nexPosition] = 2;
    }

    private void RanaPaintMoves(int position, int nexPosition){

        soundRana.start();
        String positionId = "placeHolder"  +   (position);
        String nextPositionId = "placeHolder"  +   (nexPosition);

        int positionRes = getResources().getIdentifier(positionId, "id", getPackageName());
        int nextPositionRes = getResources().getIdentifier(nextPositionId, "id", getPackageName());

        ImageView positionIV = (ImageView) findViewById(positionRes);
        ImageView nextPositionIV = (ImageView) findViewById(nextPositionRes);


        positionIV.setImageResource(R.drawable.hoja);
        table[position] = 0;
        nextPositionIV.setImageResource(R.drawable.rana);
        table[nexPosition] = 1;
    }

    private void RestartGame(){

        playing = true;
        totalMoves = 0;
        textStatisticMoves.setText((totalMoves + ""));

        placeHolders[0].setImageResource(R.drawable.rana);
        placeHolders[1].setImageResource(R.drawable.rana);
        placeHolders[2].setImageResource(R.drawable.rana);
        placeHolders[3].setImageResource(R.drawable.rana);
        placeHolders[4].setImageResource(R.drawable.hoja);
        placeHolders[5].setImageResource(R.drawable.sapo);;
        placeHolders[6].setImageResource(R.drawable.sapo);;
        placeHolders[7].setImageResource(R.drawable.sapo);;
        placeHolders[8].setImageResource(R.drawable.sapo);;

        table[0]= 1;
        table[1]= 1;
        table[2]= 1;
        table[3]= 1;
        table[4]= 0;
        table[5]= 2;
        table[6]= 2;
        table[7]= 2;
        table[8]= 2;


    }

    private void CheckGameStatus(){

        if(!IsGameOver()){
            GameOver();
        }else{
            ++totalMoves;
            textStatisticMoves.setText((totalMoves + ""));
        }

        if( table[0]== 2 &&
            table[1]== 2 &&
            table[2]== 2 &&
            table[3]== 2 &&
            table[4]== 0 &&
            table[5]== 1 &&
            table[6]== 1 &&
            table[7]== 1 &&
            table[8]== 1){
                Win();
        }

    }

    private void GameOver(){
        Toast.makeText(getApplicationContext(), "Game Over" , Toast.LENGTH_SHORT).show();
    }

    private void Win(){
        Toast.makeText(getApplicationContext(), "You Win!" , Toast.LENGTH_SHORT).show();
    }

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Move(position);

        }
    }

    private class MyCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //mp.release();
        }
    }
}
