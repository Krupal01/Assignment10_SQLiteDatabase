package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "Database", null, 1);
    }
    public static int idStudent = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table studentDetails(stuId NUMBER,stuName TEXT,stuDOB TEXT,stuPhone TEXT,stuMail TEXT,stuAdd TEXT,stuGender TEXT,stuBlood TEXT,stuSource TEXT,stuRMsg TEXT,stuRMail TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String addStudent(String stuName ,String stuDOB ,String stuPhone,String stuMail ,String stuAdd ,String stuGender ,String stuBlood ,String stuSource ,String stuRMsg ,String stuRMail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String confirmMsg ;
        values.put("stuId",idStudent);
        values.put("stuName",stuName);
        values.put("stuDOB",stuDOB);
        values.put("stuPhone",stuPhone);
        values.put("stuMail",stuMail);
        values.put("stuAdd",stuAdd);
        values.put("stuGender",stuGender);
        values.put("stuBlood",stuBlood);
        values.put("stuSource",stuSource);
        values.put("stuRMsg",stuRMsg);
        values.put("stuRMail",stuRMail);
        long result = db.insert("studentDetails",null,values);
        if (result != -1){
            confirmMsg = "detail added and your id is "+String.valueOf(idStudent);
            idStudent+=1;
        } else {
            confirmMsg = "Detail not added";
        }
        return confirmMsg ;
    }

    public Cursor getStudent(int stuId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from studentDetails where stuId = ?",new String[]{String.valueOf(stuId)});
    }

    public Boolean updateDetail(int id ,String stuName ,String stuDOB ,String stuPhone,String stuMail ,String stuAdd ,String stuGender ,String stuBlood ,String stuSource ,String stuRMsg ,String stuRMail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stuId",id);
        values.put("stuName",stuName);
        values.put("stuDOB",stuDOB);
        values.put("stuPhone",stuPhone);
        values.put("stuMail",stuMail);
        values.put("stuAdd",stuAdd);
        values.put("stuGender",stuGender);
        values.put("stuBlood",stuBlood);
        values.put("stuSource",stuSource);
        values.put("stuRMsg",stuRMsg);
        values.put("stuRMail",stuRMail);
        long result = db.update("studentDetails",values,"stuId=?",new String[]{String.valueOf(id)});
        return result!= -1;
    }

    public Boolean deletStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("studentDetails","stuId=?",new String[]{String.valueOf(id)});
        return result != -1;
    }

    public ArrayList<StudentItem> getAllStudent(){
        ArrayList<StudentItem> students = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from studentDetails",null);
        while (cursor.moveToNext()){
            students.add(new StudentItem(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
        }
        return students;
    }

}
