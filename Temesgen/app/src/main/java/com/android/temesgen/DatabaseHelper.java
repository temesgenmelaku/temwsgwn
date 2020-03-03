package com.android.temesgen;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;



    // Database Name
    private static final String DATABASE_NAME = "BeneficiaryManager.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME + " (" +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME+ " Text NOT NULL," +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_pass + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL + " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_fullname+ " TEXT NOT NULL, " +
                BeneficiaryContract.BeneficiaryEntry. COLUMN_BENEFICIARY_mobile+ " TEXT NOT NULL ," +
                BeneficiaryContract.BeneficiaryEntry. COLUMN_BENEFICIARY_sex + " TEXT NOT NULL "+
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }
    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }


    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

        //Drop User Table if exist

        db1.execSQL(DROP_BENEFICIARY_TABLE);

        // Create tables again
        onCreate(db1);

    }


    //Method to create beneficiary records

    public void addBeneficiary(Beneficiary beneficiary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME, beneficiary.getName());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_pass, beneficiary.getPass());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL, beneficiary.getEmail());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_fullname, beneficiary.getFullname());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_mobile, beneficiary.getMobile());
        values.put(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_sex, beneficiary.getSex());
        db.insert(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        Cursor cursor = db.query(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }





    public List<Beneficiary> getAllBeneficiary() {
        // array of columns to fetch
        String[] columns = {

                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_pass,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_fullname,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_mobile,
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_sex

        };
        // sorting orders
        String sortOrder =
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME + " ASC";
        List<Beneficiary> beneficiaryList = new ArrayList<Beneficiary>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Beneficiary beneficiary = new Beneficiary();
                beneficiary.setName(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME)));
                beneficiary.setEmail(cursor.getString(cursor.getColumnIndex(BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL)));

                // Adding user record to list
                beneficiaryList.add(beneficiary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return beneficiaryList;
    }
    public boolean getUser(String user, String email){
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "select *from  " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME + " where " +
                BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME + " = " + "'"+user+"'" + " and "
                +BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL+ " = " + "'"+email+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }
    public  Cursor getUserDetails(String userr){
        HashMap<String, String> user = new HashMap<String, String>();

        String selectQuery = "select * from  " + BeneficiaryContract.BeneficiaryEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();

        return cursor;
    }
    String[] columns = {

            BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME,
            BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_pass,
            BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL,
            BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_fullname,
            BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_mobile,
            BeneficiaryContract.BeneficiaryEntry.COLUMN_BENEFICIARY_sex

    };
    public boolean deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete( BeneficiaryContract.BeneficiaryEntry.TABLE_NAME, "null",
                columns );
        return false;

    }

}
