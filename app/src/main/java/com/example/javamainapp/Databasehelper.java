package com.example.javamainapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Databasehelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static  final String DB_NAME="Finance_Tracking";
    private static  final String userTable_NAME="Users";
    private static  final String transactionsTable_NAME="Transactions";
    public Databasehelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, email TEXT NOT NULL UNIQUE, password TEXT NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE Transactions(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount REAL NOT NULL, " +
                "type TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "userID INTEGER NOT NULL, " +
                "FOREIGN KEY(userID) REFERENCES Users(id) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+userTable_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+transactionsTable_NAME);
        onCreate(sqLiteDatabase);

    }
    public boolean registerUser(String username,String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("email",email);
        contentValues.put("password",password);

        long response=db.insert(userTable_NAME,null,contentValues);
        db.close();
        return response != -1;
    }
    public String[] loginUser(String email,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM USERS WHERE email= ? AND password= ? ",new String[]{email,password});
        String[] returnArray=new String[2];
        if(cursor.moveToFirst()){
            returnArray[0]=cursor.getString(0);
            returnArray[1]=cursor.getString(1);

            cursor.close();
            return  returnArray;

        }
        return null;
    }
    public boolean addtransactions(float amount,String category,String type,int userId){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("userID",userId);
        contentValues.put("amount",amount);
        contentValues.put("type",type);
        contentValues.put("category",category);

        long res=db.insert(transactionsTable_NAME,null,contentValues);

        return res != -1;

    }
    public ArrayList<Transection> loadTransectionsAccTOUSER(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transection> transactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Transactions WHERE userID = ?", new String[]{String.valueOf(userId)});

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            float amount = cursor.getFloat(1);
            String type = cursor.getString(2);
            String category = cursor.getString(3);
            Transection transaction = new Transection(id, amount, type, category);
            transactions.add(transaction);
        }

        cursor.close();
        return transactions;
    }
    public Double[] transectionAmountCalculations(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Transactions WHERE userID = ?", new String[]{String.valueOf(userId)});

        double totalBalance = 0;
        double income = 0;
        double expense = 0;

        while (cursor.moveToNext()) {
            float amount = cursor.getFloat(1);
            String type = cursor.getString(2);

            if (type.equalsIgnoreCase("Income")) {
                income += amount;
            } else if (type.equalsIgnoreCase("Expense")) {
                expense += amount;
            }
        }

        cursor.close();

        totalBalance = income - expense;


        return new Double[]{totalBalance, income, expense};
    }


}
