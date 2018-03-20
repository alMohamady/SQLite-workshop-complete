package com.masafa_eg.sqliteteast01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by amohamady on 01/14/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public static final String TABLE_NAME = "PEOPLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";

    private SQLiteDatabase database;

    public SQLiteHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                     + COLUMN_FIRST_NAME + " VARCHAR ,"
                     + COLUMN_LAST_NAME + " VARCHAR );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        //values.put(this.COLUMN_ID, contact.getID());
        values.put(this.COLUMN_FIRST_NAME, contact.getFirstName());
        values.put(this.COLUMN_LAST_NAME, contact.getLastName());
        database.insert(this.TABLE_NAME, null, values);
        database.close();
    }

    public  void insertRecord2(ContactModel contact){
        database = this.getReadableDatabase();
        database.execSQL("Insert InTo " + this.TABLE_NAME + " (" + this.COLUMN_ID + " , " + this.COLUMN_FIRST_NAME
        + " , " + this.COLUMN_LAST_NAME + " )  Values ( '" + contact.getID() + "' , '" +  contact.getFirstName()
        + "' , '" + contact.getLastName() + "' ) ");
        database.close();
    }

    public void updateRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        //values.put(this.COLUMN_ID, contact.getID());
        values.put(this.COLUMN_FIRST_NAME, contact.getFirstName());
        values.put(this.COLUMN_LAST_NAME, contact.getLastName());
        database.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public void updateRecord2(ContactModel contact) {
        database = this.getReadableDatabase();
        database.execSQL("UPDATE " + TABLE_NAME + " SET " + this.COLUMN_FIRST_NAME + " = '" + contact.getFirstName() +
                         "' , " + this.COLUMN_LAST_NAME + " = '" + contact.getLastName() + "' WHERE " +
                         this.COLUMN_ID + " = '" + contact.getID() + "' ");
        database.close();
    }

    public void deleteRecord(ContactModel contact){
        database = this.getReadableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + " =?",new String[]{contact.getID()}  );
        database.close();
    }

    public void deleteRecord2(ContactModel contact) {
        database = this.getReadableDatabase();
        database.execSQL("Delete From " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + contact.getID() + "' ");
        database.close();
    }

    public ArrayList<ContactModel> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();

        ContactModel contact;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                contact = new ContactModel();
                contact.setID(cursor.getString(0));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contacts.add(contact);
            }
        }
        cursor.close();
        database.close();
        return contacts;
    }

    public ArrayList<ContactModel> getAllRecords2() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * From " + TABLE_NAME, null);
        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
        ContactModel contact;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                contact = new ContactModel();
                contact.setID(cursor.getString(0));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contacts.add(contact);
            }
        }
        cursor.close();
        database.close();
        return contacts;
    }
}
