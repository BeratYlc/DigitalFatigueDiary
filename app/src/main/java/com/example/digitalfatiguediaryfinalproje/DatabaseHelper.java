package com.example.digitalfatiguediaryfinalproje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "digital_fatigue.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GUNLUK = "gunluk";

    private static final String COL_ID = "id";
    private static final String COL_TARIH = "tarih";              // YYYY-MM-DD
    private static final String COL_EKRAN_DK = "ekran_dk";         // int
    private static final String COL_YORGUNLUK = "yorgunluk";       // 1-5
    private static final String COL_ODAK = "odak";                 // 1-5
    private static final String COL_RUH_HALI = "ruh_hali";         // Iyi/Orta/Kotu
    private static final String COL_BILDIRIM = "bildirim";         // Dusuk/Orta/Yuksek
    private static final String COL_NOTLAR = "notlar";             // opsiyonel

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create =
                "CREATE TABLE " + TABLE_GUNLUK + " ("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL_TARIH + " TEXT, "
                        + COL_EKRAN_DK + " INTEGER, "
                        + COL_YORGUNLUK + " INTEGER, "
                        + COL_ODAK + " INTEGER, "
                        + COL_RUH_HALI + " TEXT, "
                        + COL_BILDIRIM + " TEXT, "
                        + COL_NOTLAR + " TEXT"
                        + ")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GUNLUK);
        onCreate(db);
    }

    // Aynı gün için kayıt var mı?
    public boolean isKayitVar(String tarih) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " + COL_ID + " FROM " + TABLE_GUNLUK + " WHERE " + COL_TARIH + "=?",
                new String[]{tarih}
        );

        boolean var = c.moveToFirst();
        c.close();
        return var;
    }

    // Kayıt ekle
    public void addKayit(String tarih, int ekranDk, int yorgunluk, int odak,
                         String ruhHali, String bildirim, String notlar) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(COL_TARIH, tarih);
        v.put(COL_EKRAN_DK, ekranDk);
        v.put(COL_YORGUNLUK, yorgunluk);
        v.put(COL_ODAK, odak);
        v.put(COL_RUH_HALI, ruhHali);
        v.put(COL_BILDIRIM, bildirim);
        v.put(COL_NOTLAR, notlar);

        db.insert(TABLE_GUNLUK, null, v);
        db.close();
    }

    // Tüm kayıtlar (sondan başa)
    public Cursor getAllKayitlar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_GUNLUK + " ORDER BY " + COL_ID + " DESC", null);
    }

    // Kayıt sil
    public void deleteKayit(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GUNLUK, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Bugünün kaydını çek
    public Cursor getKayitByTarih(String tarih) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_GUNLUK + " WHERE " + COL_TARIH + "=? LIMIT 1",
                new String[]{tarih}
        );
    }

    /*
     * GERÇEK "Son N Gün" ortalaması:
     * Bugünden geriye (n-1) gün dahil olacak şekilde tarih aralığına göre hesaplar.
     * Ayrıca COUNT(*) ile bu aralıkta kaç gün veri olduğunu döndürür.
     *
     * Dönüş kolonları:
     * 0: avg_ekran
     * 1: avg_yorgunluk
     * 2: avg_odak
     * 3: kayit_sayisi
     */
    public Cursor getAvgLastNDaysByDateRange(int n) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Ör: n=7 için date('now','-6 day') ... date('now')
        String sql = "SELECT " +
                "AVG(" + COL_EKRAN_DK + ") AS avg_ekran, " +
                "AVG(" + COL_YORGUNLUK + ") AS avg_yorgunluk, " +
                "AVG(" + COL_ODAK + ") AS avg_odak, " +
                "COUNT(*) AS kayit_sayisi " +
                "FROM " + TABLE_GUNLUK + " " +
                "WHERE " + COL_TARIH + " >= date('now','-" + (n - 1) + " day') " +
                "AND " + COL_TARIH + " <= date('now')";

        return db.rawQuery(sql, null);
    }
}
