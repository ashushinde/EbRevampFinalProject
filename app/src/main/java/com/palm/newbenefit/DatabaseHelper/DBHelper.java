package com.palm.newbenefit.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.palm.newbenefit.Module.AddNominee;
import com.palm.newbenefit.Module.BillAllData;
import com.palm.newbenefit.Module.BillData;
import com.palm.newbenefit.Module.BillDate;
import com.palm.newbenefit.Module.Claim_amount;
import com.palm.newbenefit.Module.Cost;
import com.palm.newbenefit.Module.ImageData;
import com.palm.newbenefit.Module.comment;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteOpenHelper dbHelper;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String ENCODING_SETTING = "PRAGMA encoding ='windows-1256'";
    // Database Name
    private static final String DATABASE_NAME = "EB.db";

    // all table name
    private static final String TABLE_bill_no = "BILLNO";

    private static final String TABLE_bill_date = "BILLDATE";
    private static final String TABLE_bill_comment = "COMMENT";
    private static final String TABLE_bill_cost = "COST";
    private static final String TABLE_claim_amount = "CLAIM_AMOUNT";
    private static final String TABLE_Bill_All_DATA = "BillAllData";
    private static final String TABLE_Bill_IMAGE = "BillImage";
    private static final String TABLE_ADD_NOMINEE = "AddNominee";


    private static final String COLUMN_ID = "Nominee_ID";
    private static final String COLUMN_N_FIRST_NAME = "n_first_name";
    private static final String COLUMN_N_LAST_NAME = "n_last_name";
    private static final String COLUMN_N_DOB = "n_dob";
    private static final String COLUMN_N_RELATION = "n_relation";
    private static final String COLUMN_G_FIRST_NAME = "g_first_name";
    private static final String COLUMN_G_LAST_NAME = "g_last_name";
    private static final String COLUMN_G_DOB = "g_dob";
    private static final String COLUMN_G_RELATION = "g_relation";
    private static final String COLUMN_SHARE = "g_share";


    private static final String COLUMN_IMAGE = "BILL_IMAGE";

    private static final String COLUMN_BILL_DATE_All = "BILL_DATE_All";
    private static final String COLUMN_BILL_NO_All = "BILL_NO_All";
    private static final String COLUMN_BILL_AMOUNT_All = "BILL_AMOUNT_All";
    private static final String COLUMN_COST_All = "COST_All";
    private static final String COLUMN_COMMNET_All = "COMMNET_All";


    // Persnol Table Columns names
    private static final String COLUMN_BILL_DATE = "BILL_DATE";
    private static final String COLUMN_BILL_NO = "BILL_NO";
    private static final String COLUMN_BILL_AMOUNT = "BILL_AMOUNT";
    private static final String COLUMN_COST = "COST";
    private static final String COLUMN_COMMNET = "COMMNET";


    private String CREATE_NOMINEE_TABLE = "CREATE TABLE " + TABLE_ADD_NOMINEE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_N_FIRST_NAME + " TEXT," + COLUMN_N_LAST_NAME + " TEXT,"
            + COLUMN_N_DOB + " TEXT," + COLUMN_N_RELATION + " TEXT,"
            + COLUMN_G_FIRST_NAME + " TEXT," + COLUMN_G_LAST_NAME + " TEXT,"
            + COLUMN_G_DOB + " TEXT," + COLUMN_G_RELATION + " TEXT,"
            + COLUMN_SHARE + " TEXT " + ")";


    // create table sql query
    private String CREATE_BILL_ALL_TABLE = "CREATE TABLE " + TABLE_Bill_All_DATA + "("
            + COLUMN_BILL_NO_All + "  TEXT PRIMARY KEY," + COLUMN_BILL_DATE_All + " TEXT,"
            + COLUMN_BILL_AMOUNT_All + " TEXT," + COLUMN_COST_All + " TEXT,"
            + COLUMN_COMMNET_All + " TEXT " + ")";


    // create table sql query
    private String CREATE_BILL_NO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_bill_no + "("
            + COLUMN_BILL_NO + " TEXT PRIMARY KEY " + ")";


    private String CREATE_IMAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_Bill_IMAGE + "("
            + COLUMN_IMAGE + " TEXT" + ")";

    private String CREATE_BILL_DATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_bill_date + "("
            + COLUMN_BILL_DATE + " TEXT" + ")";

    private String CREATE_BILL_AMOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_claim_amount + "("
            + COLUMN_BILL_AMOUNT + " TEXT" + ")";

    private String CREATE_BILL_COST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_bill_cost + "("
            + COLUMN_COST + " TEXT" + ")";

    private String CREATE_BILL_COMMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_bill_comment + "("
            + COLUMN_COMMNET + " TEXT" + ")";


    // drop table sql query
    private String DROP_BILL_NO_TABLE = "DROP TABLE IF EXISTS " + TABLE_bill_no;
    private String DROP_BILL_DATE_TABLE = "DROP TABLE IF EXISTS " + TABLE_bill_date;
    private String DROP_BILL_AMOUNT_TABLE = "DROP TABLE IF EXISTS " + TABLE_bill_comment;
    private String DROP_COST_TABLE = "DROP TABLE IF EXISTS " + TABLE_bill_cost;
    private String DROP_COMMNET_TABLE = "DROP TABLE IF EXISTS " + TABLE_bill_comment;
    private String DROP_BILL_ALL_TABLE = "DROP TABLE IF EXISTS " + TABLE_Bill_All_DATA;
    private String DROP_IMAGE = "DROP TABLE IF EXISTS " + TABLE_Bill_IMAGE;
    private String DROP_NOMINEE = "DROP TABLE IF EXISTS " + TABLE_ADD_NOMINEE;

    /**
     * Constructor
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BILL_NO_TABLE);
        db.execSQL(CREATE_BILL_DATE_TABLE);
        db.execSQL(CREATE_BILL_AMOUNT_TABLE);
        db.execSQL(CREATE_BILL_COST_TABLE);
        db.execSQL(CREATE_BILL_COMMENT_TABLE);
        db.execSQL(CREATE_BILL_ALL_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_NOMINEE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_BILL_NO_TABLE);
        db.execSQL(DROP_BILL_DATE_TABLE);
        db.execSQL(DROP_BILL_AMOUNT_TABLE);
        db.execSQL(DROP_COST_TABLE);
        db.execSQL(DROP_COMMNET_TABLE);
        db.execSQL(DROP_BILL_ALL_TABLE);
        db.execSQL(DROP_IMAGE);
        db.execSQL(DROP_NOMINEE);


        // Create tables again
        onCreate(db);

    }


    public Boolean AddNominee(AddNominee user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_N_FIRST_NAME, user.getNom_first_name());
        values.put(COLUMN_N_LAST_NAME, user.getNom_last_name());
        values.put(COLUMN_N_DOB, user.getNom_dob());
        values.put(COLUMN_N_RELATION, user.getNom_relation());
        values.put(COLUMN_G_FIRST_NAME, user.getG_first_name());
        values.put(COLUMN_G_LAST_NAME, user.getG_last_name());
        values.put(COLUMN_G_DOB, user.getG_dob());
        values.put(COLUMN_G_RELATION, user.getG_relation());
        values.put(COLUMN_SHARE, user.getShare_percent());


        long result = db.insert(TABLE_ADD_NOMINEE, null, values);
        if (result == -1)
            return false;
        else
            return true;
        // db.close();
    }


    public Boolean AddImage(ImageData user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, user.getImage());


        long result = db.insert(TABLE_Bill_IMAGE, null, values);
        if (result == -1)
            return false;
        else
            return true;
        // db.close();
    }


    public Boolean AddAllData(BillAllData user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_NO_All, user.getBill_number());
        values.put(COLUMN_BILL_AMOUNT_All, user.getBill_amount());
        values.put(COLUMN_BILL_DATE_All, user.getBill_Date());
        values.put(COLUMN_COMMNET_All, user.getComment());
        values.put(COLUMN_COST_All, user.getType());


        long result = db.insert(TABLE_Bill_All_DATA, null, values);
        if (result == -1)
            return false;
        else
            return true;




        // db.close();
    }


    public Boolean AddBillNo(BillData user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_NO, user.getBill_no());

        long result = db.insert(TABLE_bill_no, null, values);
        if (result == -1)
            return false;
        else
            return true;
        // db.close();
    }


    public Boolean AddBillAmount(Claim_amount user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_AMOUNT, user.getClaim_amount());

        long result = db.insert(TABLE_claim_amount, null, values);
        if (result == -1)
            return false;
        else
            return true;
        // db.close();
    }

    public Boolean AddBillDate(BillDate user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_DATE, user.getBill_date());

        long result = db.insert(TABLE_bill_date, null, values);
        if (result == -1)
            return false;
        else
            return true;
        // db.close();
    }


    public Boolean AddBillCost(Cost user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COST, user.getCost_arr());

        long result = db.insert(TABLE_bill_cost, null, values);
        if (result == -1)
            return false;
        else
            return true;
        // db.close();
    }


    public Boolean AddBillComment(comment user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMNET, user.getComment_arr());

        long result = db.insert(TABLE_bill_comment, null, values);
        if (result == -1)
            return false;
        else
            return true;
        // db.close();
    }


    public List<BillData> getBillNo() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BILL_NO,


        };
        // sorting orders
        String sortOrder =
                COLUMN_BILL_NO + " ASC";
        List<BillData> userList = new ArrayList<BillData>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_bill_no, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                BillData user = new BillData();

                user.setBill_no(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_NO)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    public ArrayList<BillAllData> getAllData() {
        ArrayList<BillAllData> doglist = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_Bill_All_DATA, null);

        while (res.moveToNext()) {
            String id = res.getString(0);   //0 is the number of id column in your database table
            String name = res.getString(1);
            String age = res.getString(2);
            String breed = res.getString(3);
            String weight = res.getString(4);

            BillAllData newDog = new BillAllData(id, name, age, breed, weight);
            doglist.add(newDog);
        }
        return doglist;
    }


    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Bill_All_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    public int getimageCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Bill_IMAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public List<BillAllData> getdata() {
        // DataModel dataModel = new DataModel();
        List<BillAllData> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_Bill_All_DATA + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        BillAllData dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new BillAllData();


            String name = cursor.getString(cursor.getColumnIndexOrThrow("BILL_NO_All"));
            String country = cursor.getString(cursor.getColumnIndexOrThrow("BILL_AMOUNT_All"));
            String city = cursor.getString(cursor.getColumnIndexOrThrow("BILL_DATE_All"));
            String amt = cursor.getString(cursor.getColumnIndexOrThrow("COMMNET_All"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("COST_All"));
            dataModel.setBill_number(name);
            dataModel.setBill_Date(city);
            dataModel.setBill_amount(country);
            dataModel.setComment(amt);
            dataModel.setType(type);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (BillAllData mo : data) {

            Log.i("Hellomo", "" + mo.getBill_number());
        }

        //

        return data;
    }


    public List<AddNominee> getNomineeData() {
        // DataModel dataModel = new DataModel();
        List<AddNominee> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ADD_NOMINEE + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        AddNominee dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new AddNominee();

            String id = cursor.getString(cursor.getColumnIndexOrThrow("Nominee_ID"));
            String n_first_name = cursor.getString(cursor.getColumnIndexOrThrow("n_first_name"));
            String n_last_name = cursor.getString(cursor.getColumnIndexOrThrow("n_last_name"));
            String n_dob = cursor.getString(cursor.getColumnIndexOrThrow("n_dob"));
            String n_relation = cursor.getString(cursor.getColumnIndexOrThrow("n_relation"));
            String g_first_name = cursor.getString(cursor.getColumnIndexOrThrow("g_first_name"));
            String g_last_name = cursor.getString(cursor.getColumnIndexOrThrow("g_last_name"));
            String g_dob = cursor.getString(cursor.getColumnIndexOrThrow("g_dob"));
            String g_relation = cursor.getString(cursor.getColumnIndexOrThrow("g_relation"));
            String g_share = cursor.getString(cursor.getColumnIndexOrThrow("g_share"));
            dataModel.setId(id);
            dataModel.setNom_first_name(n_first_name);
            dataModel.setNom_last_name(n_last_name);
            dataModel.setNom_dob(n_dob);
            dataModel.setNom_relation(n_relation);
            dataModel.setG_first_name(g_first_name);
            dataModel.setG_last_name(g_last_name);
            dataModel.setG_dob(g_dob);
            dataModel.setG_relation(g_relation);
            dataModel.setShare_percent(g_share);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (AddNominee mo : data) {

            Log.i("Hellomo", "" + mo.getG_first_name());
        }

        //

        return data;
    }


    public List<ImageData> getImage() {
        // DataModel dataModel = new DataModel();
        List<ImageData> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_Bill_IMAGE + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        ImageData dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new ImageData();


            String name = cursor.getString(cursor.getColumnIndexOrThrow("BILL_IMAGE"));

            dataModel.setImage(name);

            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (ImageData mo : data) {

            Log.i("Hellomo", "" + mo.getImage());
        }

        //

        return data;
    }


    public void deleteImage(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Bill_IMAGE + " WHERE " + COLUMN_IMAGE + "='" + value + "'");
        db.close();
    }


    public void deleteNominee(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ADD_NOMINEE + " WHERE " + COLUMN_ID + "='" + value + "'");
        db.close();
    }


    public void deleteRow(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Bill_All_DATA + " WHERE " + COLUMN_BILL_NO_All + "='" + value + "'");
        db.close();
    }

    public void deletedate(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_bill_date + " WHERE " + COLUMN_BILL_DATE + "='" + value + "'");
        db.close();
    }

    public void deleteRowno(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_bill_no + " WHERE " + COLUMN_BILL_NO + "='" + value + "'");
        db.close();
    }


    public void deleteRowamt(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_claim_amount + " WHERE " + COLUMN_BILL_AMOUNT + "='" + value + "'");
        db.close();
    }


    public void deleteRowcost(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_bill_cost + " WHERE " + COLUMN_COST + "='" + value + "'");
        db.close();
    }

    public void deleteRowcomment(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_bill_comment + " WHERE " + COLUMN_COMMNET + "='" + value + "'");
        db.close();
    }

    public void deletebillldata(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Bill_All_DATA + " WHERE " + COLUMN_BILL_NO_All + "='" + value + "'");
        db.close();
    }


    public List<BillAllData> getAllBanks() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BILL_AMOUNT_All,
                COLUMN_BILL_DATE_All,
                COLUMN_COST_All,
                COLUMN_COMMNET_All,
                COLUMN_BILL_NO_All,

        };
        // sorting orders
        String sortOrder =
                COLUMN_BILL_NO_All + " ASC";
        List<BillAllData> userList = new ArrayList<BillAllData>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_Bill_All_DATA, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                BillAllData user = new BillAllData();

                user.setBill_amount(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_AMOUNT_All)));
                user.setBill_Date(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DATE_All)));
                user.setType(cursor.getString(cursor.getColumnIndex(COLUMN_COST_All)));
                user.setComment(cursor.getString(cursor.getColumnIndex(COLUMN_COMMNET_All)));
                user.setBill_number(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_NO_All)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    public List<Claim_amount> getBillAmount() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BILL_AMOUNT,


        };
        // sorting orders
        String sortOrder =
                COLUMN_BILL_AMOUNT + " ASC";
        List<Claim_amount> userList = new ArrayList<Claim_amount>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_claim_amount, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Claim_amount user = new Claim_amount();

                user.setClaim_amount(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_AMOUNT)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    public List<BillDate> getbillDate() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BILL_DATE,


        };
        // sorting orders
        String sortOrder =
                COLUMN_BILL_DATE + " ASC";
        List<BillDate> userList = new ArrayList<BillDate>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_bill_date, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                BillDate user = new BillDate();

                user.setBill_date(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DATE)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    public List<Cost> getbillCost() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_COST,


        };
        // sorting orders
        String sortOrder =
                COLUMN_COST + " ASC";
        List<Cost> userList = new ArrayList<Cost>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_bill_cost, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Cost user = new Cost();

                user.setCost_arr(cursor.getString(cursor.getColumnIndex(COLUMN_COST)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public List<comment> getbillComment() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_COMMNET,


        };
        // sorting orders
        String sortOrder =
                COLUMN_COMMNET + " ASC";
        List<comment> userList = new ArrayList<comment>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_bill_comment, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                comment user = new comment();

                user.setComment_arr(cursor.getString(cursor.getColumnIndex(COLUMN_COMMNET)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    public boolean removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.delete(TABLE_bill_comment, null, null);
        db.delete(TABLE_bill_cost, null, null);
        db.delete(TABLE_bill_date, null, null);
        db.delete(TABLE_claim_amount, null, null);
        db.delete(TABLE_bill_no, null, null);
        db.delete(TABLE_Bill_All_DATA, null, null);
        db.delete(TABLE_Bill_IMAGE, null, null);
        db.delete(TABLE_ADD_NOMINEE, null, null);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return true;
    }


}