package com.example.digitalfatiguediaryfinalproje;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class KayitlarActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView lvKayitlar;

    ArrayList<String> liste;
    ArrayList<Integer> idListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitlar);

        db = new DatabaseHelper(this);
        lvKayitlar = findViewById(R.id.lvKayitlar);

        lvKayitlar.setOnItemLongClickListener((parent, view, position, id) -> {
            int secilenId = idListe.get(position);
            silmeDialog(secilenId);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKayitlar();
    }

    private void loadKayitlar() {
        liste = new ArrayList<>();
        idListe = new ArrayList<>();

        Cursor c = db.getAllKayitlar();

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String tarih = c.getString(1);
                int ekranDk = c.getInt(2);
                int yorgunluk = c.getInt(3);
                int odak = c.getInt(4);
                String ruhHali = c.getString(5);
                String bildirim = c.getString(6);
                String notlar = c.getString(7);

                String satir = tarih
                        + " | Ekran: " + ekranDk + "dk"
                        + " | Yor: " + yorgunluk + "/5"
                        + " | Odak: " + odak + "/5"
                        + " | Ruh: " + ruhHali
                        + " | Bild: " + bildirim;

                if (notlar != null && !notlar.trim().isEmpty()) {
                    satir = satir + " (" + notlar + ")";
                }

                liste.add(satir);
                idListe.add(id);

            } while (c.moveToNext());
        }

        c.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                liste
        );

        lvKayitlar.setAdapter(adapter);
    }

    private void silmeDialog(int kayitId) {
        new AlertDialog.Builder(this)
                .setTitle("Silme Onayi")
                .setMessage("Bu kaydi silmek istiyor musunuz?")
                .setPositiveButton("Evet", (dialog, which) -> {
                    db.deleteKayit(kayitId);
                    loadKayitlar();
                })
                .setNegativeButton("Hayir", null)
                .show();
    }
}
