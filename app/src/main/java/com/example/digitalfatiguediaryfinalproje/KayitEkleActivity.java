package com.example.digitalfatiguediaryfinalproje;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KayitEkleActivity extends AppCompatActivity {

    DatabaseHelper db;

    EditText etTarih, etEkranDk, etNot;
    Spinner spYorgunluk, spOdak, spRuhHali, spBildirim;
    Button btnKaydet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekle);

        db = new DatabaseHelper(this);

        etTarih = findViewById(R.id.etTarih);
        etEkranDk = findViewById(R.id.etEkranDk);
        etNot = findViewById(R.id.etNot);

        spYorgunluk = findViewById(R.id.spYorgunluk);
        spOdak = findViewById(R.id.spOdak);
        spRuhHali = findViewById(R.id.spRuhHali);
        spBildirim = findViewById(R.id.spBildirim);

        btnKaydet = findViewById(R.id.btnKaydet);

        // Tarihi otomatik bugün yap
        String bugun = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        etTarih.setText(bugun);

        // Spinner'ları bağla
        bindSpinner(spYorgunluk, R.array.seviye_1_5);
        bindSpinner(spOdak, R.array.seviye_1_5);
        bindSpinner(spRuhHali, R.array.ruh_hali);
        bindSpinner(spBildirim, R.array.bildirim_yogunlugu);

        btnKaydet.setOnClickListener(v -> kaydet());
    }

    private void bindSpinner(Spinner sp, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }

    private void kaydet() {
        String tarih = etTarih.getText().toString().trim();
        String ekranStr = etEkranDk.getText().toString().trim();

        if (tarih.isEmpty() || ekranStr.isEmpty()) {
            Toast.makeText(this, "Lutfen tarih ve ekran suresi girin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aynı güne 2 kayıt olmasın
        if (db.isKayitVar(tarih)) {
            Toast.makeText(this, "Bu tarih icin zaten kayit var!", Toast.LENGTH_SHORT).show();
            return;
        }

        int ekranDk = Integer.parseInt(ekranStr);

        int yorgunluk = Integer.parseInt(spYorgunluk.getSelectedItem().toString());
        int odak = Integer.parseInt(spOdak.getSelectedItem().toString());

        String ruhHali = spRuhHali.getSelectedItem().toString();
        String bildirim = spBildirim.getSelectedItem().toString();

        String notlar = etNot.getText().toString().trim();

        db.addKayit(tarih, ekranDk, yorgunluk, odak, ruhHali, bildirim, notlar);

        Toast.makeText(this, "Kayit eklendi", Toast.LENGTH_SHORT).show();
        finish();
    }
}
