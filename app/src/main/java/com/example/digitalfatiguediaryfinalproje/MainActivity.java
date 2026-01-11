package com.example.digitalfatiguediaryfinalproje;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    TextView tvBugunDurum, tvBugunOzet;
    TextView tvAvgEkran, tvAvgYorgunluk, tvAvgOdak;
    Button btnKayitEkle, btnKayitlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        tvBugunDurum = findViewById(R.id.tvBugunDurum);
        tvBugunOzet = findViewById(R.id.tvBugunOzet);

        tvAvgEkran = findViewById(R.id.tvAvgEkran);
        tvAvgYorgunluk = findViewById(R.id.tvAvgYorgunluk);
        tvAvgOdak = findViewById(R.id.tvAvgOdak);

        btnKayitEkle = findViewById(R.id.btnKayitEkle);
        btnKayitlar = findViewById(R.id.btnKayitlar);

        btnKayitEkle.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, KayitEkleActivity.class);
            startActivity(i);
        });

        btnKayitlar.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, KayitlarActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBugun();
        loadAvgGercek7Gun();
    }

    private String bugunTarih() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void loadBugun() {
        String bugun = bugunTarih();

        if (!db.isKayitVar(bugun)) {
            tvBugunDurum.setText("Bugun kayit: Yok");
            tvBugunOzet.setText("Bugun icin kayit ekleyebilirsiniz.");
            return;
        }

        Cursor c = db.getKayitByTarih(bugun);
        if (c.moveToFirst()) {
            int ekranDk = c.getInt(2);
            int yorgunluk = c.getInt(3);
            int odak = c.getInt(4);
            String ruhHali = c.getString(5);
            String bildirim = c.getString(6);

            tvBugunDurum.setText("Bugun kayit: Var");
            tvBugunOzet.setText("Ekran: " + ekranDk + " dk | Yorgunluk: " + yorgunluk
                    + "/5 | Odak: " + odak + "/5 | Ruh: " + ruhHali + " | Bildirim: " + bildirim);
        }
        c.close();
    }

    private void loadAvgGercek7Gun() {
        Cursor c = db.getAvgLastNDaysByDateRange(7);

        if (c.moveToFirst()) {
            // Eğer aralıkta hiç kayıt yoksa AVG null döner, COUNT 0 döner
            int kayitSayisi = c.getInt(3);

            double avgEkran = c.isNull(0) ? 0 : c.getDouble(0);
            double avgYorgunluk = c.isNull(1) ? 0 : c.getDouble(1);
            double avgOdak = c.isNull(2) ? 0 : c.getDouble(2);

            int ekranYuvarla = (int) Math.round(avgEkran);
            double yorg2 = Math.round(avgYorgunluk * 10.0) / 10.0;
            double odak2 = Math.round(avgOdak * 10.0) / 10.0;

            tvAvgEkran.setText("Ekran: " + ekranYuvarla + " dk (" + kayitSayisi + " gunluk veri)");
            tvAvgYorgunluk.setText("Yorgunluk: " + yorg2 + "/5");
            tvAvgOdak.setText("Odak: " + odak2 + "/5");
        }

        c.close();
    }
}
