package com.utic.appmusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // Variables y objetos
    MediaPlayer mediaPlayer;
    Button btnPlay, btnPause, btnResume, btnStop;
    RadioButton radio1, radio2, radio3, radio4, radio5, radio6, radio7;
    int posicion = 0;

    // Lista de pistas
    int[] pistasLocales = {
            R.raw.pista01,
            R.raw.pista02,
            R.raw.pista03,
            R.raw.pista04,
            R.raw.pista05
    };

    String[] pistasOnline = {
            "https://techtransfer.com.py/music/pista06.mp3",
            "https://techtransfer.com.py/music/pista07.mp3"
    };

    int currentTrackIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Asignación de botones
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnResume = findViewById(R.id.btnResume);
        btnStop = findViewById(R.id.btnStop);

        // Asignación de radio botones
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
        radio5 = findViewById(R.id.radio5);
        radio6 = findViewById(R.id.radio6);
        radio7 = findViewById(R.id.radio7);

        // Configurar eventos de selección para los RadioButtons
        radio1.setOnClickListener(v -> seleccionarPista(0));
        radio2.setOnClickListener(v -> seleccionarPista(1));
        radio3.setOnClickListener(v -> seleccionarPista(2));
        radio4.setOnClickListener(v -> seleccionarPista(3));
        radio5.setOnClickListener(v -> seleccionarPista(4));
        radio6.setOnClickListener(v -> seleccionarPista(5));
        radio7.setOnClickListener(v -> seleccionarPista(6));
    }

    // Método para seleccionar la pista según el RadioButton
    private void seleccionarPista(int index) {
        currentTrackIndex = index;
    }

    public void destruir() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void reproducir(View view) {
        destruir();
        iniciarReproduccion();
    }

    private void iniciarReproduccion() {
        if (currentTrackIndex < 5) { // Reproducción de pistas locales
            mediaPlayer = MediaPlayer.create(this, pistasLocales[currentTrackIndex]);
        } else { // Reproducción de pistas online
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(pistasOnline[currentTrackIndex - 5]);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mediaPlayer.start();

        // Configurar listener para reproducir la siguiente pista cuando termine la actual
        mediaPlayer.setOnCompletionListener(mp -> {
            currentTrackIndex++;
            if (currentTrackIndex < pistasLocales.length + pistasOnline.length) {
                destruir();
                iniciarReproduccion();
            }
        });
    }

    public void pausar(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            posicion = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    public void continuar(View view) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(posicion);
            mediaPlayer.start();
        }
    }

    public void detener(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            posicion = 0;
            currentTrackIndex = 0; // Resetear el índice de la pista
        }
    }
}
