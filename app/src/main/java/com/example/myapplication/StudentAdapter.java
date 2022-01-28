package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.StudentRowItemBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    public ArrayList<StudentItem> students ;
    public Database db;
    public Context context;
    public StudentRowItemBinding studentRowItemBinding;

    public StudentAdapter(Context context,ArrayList<StudentItem> students) {
        this.students = students;
        this.context = context;
    }


    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.student_row_item,parent,false);
        StudentViewHolder holder = new StudentViewHolder(StudentRowItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        studentRowItemBinding.tvItemName.setText("Name : "+students.get(position).stuName);
        studentRowItemBinding.tvItemPhone.setText("Phone : "+students.get(position).stuPhone);
        studentRowItemBinding.tvItemMail.setText("Mail : "+students.get(position).stuMail);
        studentRowItemBinding.tvItemAdd.setText("Address : "+students.get(position).stuAdd);
        studentRowItemBinding.tvItemDOB.setText("BirthDate : "+students.get(position).stuDOB);
        studentRowItemBinding.tvItemGender.setText("Gender : "+students.get(position).stuGender);
        studentRowItemBinding.tvItemBloodgrp.setText("Blood Grp : "+students.get(position).stuBlood);
        studentRowItemBinding.itemDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletItem(holder.getAdapterPosition());
            }
        });
        studentRowItemBinding.itemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditItem(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public StudentViewHolder(@NonNull StudentRowItemBinding binding) {
            super(binding.getRoot());
            studentRowItemBinding = binding;
        }
    }

    private void EditItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("StudentId",students.get(position).stuID);
        Fragment fragment = new AddStudentFragment();
        fragment.setArguments(bundle);
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activityMain,fragment)
                .addToBackStack(null)
                .commit();
    }

//    private void setUpdateScr(View view, StudentItem student) {
//        String[] spBloodgrp = {"select any one","A+","B+","AB+","O+","A-","B-","AB-","O-"};
//        EditText etName = view.findViewById(R.id.etName);
//        EditText etBirthdate = view.findViewById(R.id.etBirthdate);
//        EditText etPhone = view.findViewById(R.id.etPhone);
//        EditText etMail = view.findViewById(R.id.etMail);
//        EditText etAddress = view.findViewById(R.id.etAddress);
//        RadioButton rbMale = view.findViewById(R.id.rbMale);
//        RadioButton rbFemale = view.findViewById(R.id.rbFemale);
//        Spinner spBloodGrp = view.findViewById(R.id.spBloodGrp);
//        Spinner spSource = view.findViewById(R.id.spSource);
//        Switch swReMails = view.findViewById(R.id.swReMails);
//        Switch swReMsg = view.findViewById(R.id.swReMsg);
//        Button btnSubmit = view.findViewById(R.id.btnSubmit);
//
//        etName.setText(student.stuName);
//        etBirthdate.setText(student.stuDOB);
//        etPhone.setText(student.stuPhone);
//        etMail.setText(student.stuMail);
//        etAddress.setText(student.stuAdd);
//
//        if (student.stuGender.equals("Male")){
//            rbMale.setChecked(true);
//        }else {
//            rbFemale.setChecked(true);
//        }
//
//        spBloodGrp.setSelection(Arrays.asList(spBloodgrp).indexOf(student.stuBlood)+1);
//
//        spSource.setPrompt(student.stuSource);
//
//        if (student.stuRMail.equals("yes")){
//            swReMails.setChecked(true);}
//        if (student.stuRMsg.equals("yes")){
//            swReMsg.setChecked(true);
//        }
//        btnSubmit.setText("Update Details");
//    }

    private void DeletItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm...");
        builder.setMessage("Are you sure ??");
        builder.setPositiveButton("Delet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = new Database(context);
                db.deletStudent(students.get(position).stuID);
                Toast.makeText(context,String.valueOf(students.get(position).stuID),Toast.LENGTH_LONG).show();
                students.remove(students.get(position));
                notifyDataSetChanged();
            }
        });
        builder.setNeutralButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
