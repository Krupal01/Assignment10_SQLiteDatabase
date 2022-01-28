package com.example.myapplication;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.myapplication.databinding.FragmentAddStudentBinding;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.IntSummaryStatistics;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStudentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStudentFragment newInstance(String param1, String param2) {
        AddStudentFragment fragment = new AddStudentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FragmentAddStudentBinding binding;
    private String[] spBloodgrp = {"select any one","A+","B+","AB+","O+","A-","B-","AB-","O-"};
    private String[] spSource = {"select any one","From Text Massage","From Mail","From Friends","From Google","From Advertise","other"};
    private boolean IsUpdate = false;
    private Database db;
    StudentItem student ;
    private int StudentId = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddStudentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new Database(getContext());
        setInitialScreen();

        Bundle bundle = this.getArguments();
        if (bundle != null){
            StudentId = bundle.getInt("StudentId");
            Toast.makeText(getContext(),String.valueOf(StudentId),Toast.LENGTH_SHORT).show();
            IsUpdate = true;
            db = new Database(getContext());
            Cursor c = db.getStudent(StudentId);

            if (c.getCount()!=0) {
                while (c.moveToNext()) {
                    student = new StudentItem(StudentId, c.getString(1), c.getString(2), c.getString(3), c.getString(4),
                            c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9),
                            c.getString(10));
                }
                setUpdateScr(student);
            }
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateData()){
                    if (IsUpdate){
                        UpdateData(StudentId , getDate());
                    }else {
                        AddNewData(getDate());
                    }
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activityMain,new StudentListFragment())
                            .commit();
                }
            }
        });
    }

    private void AddNewData(StudentItem item) {

        String msg = db.addStudent(item.stuName,item.stuDOB,item.stuPhone,item.stuMail,item.stuAdd,item.stuGender,item.stuBlood,item.stuSource,item.stuRMsg,item.stuRMail);
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
    private void UpdateData(int id,StudentItem item) {

        boolean result = db.updateDetail(id,item.stuName,item.stuDOB,item.stuPhone,item.stuMail,item.stuAdd,item.stuGender,item.stuBlood,item.stuSource,item.stuRMsg,item.stuRMail);
        if (result){
            Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Update Fail" , Toast.LENGTH_SHORT).show();
        }
    }

    private StudentItem getDate() {
        String name = binding.etName.getText().toString();
        String Dob = binding.etBirthdate.getText().toString();
        String phone = binding.etPhone.getText().toString();
        String mail = binding.etMail.getText().toString();
        String Add = binding.etAddress.getText().toString();

        String Gender ;
        if (binding.rbFemale.isChecked()){
            Gender = binding.rbFemale.getText().toString();
        }else {
            Gender = binding.rbMale.getText().toString();
        }

        String Blood = binding.spBloodGrp.getSelectedItem().toString();
        String Source = binding.spSource.getSelectedItem().toString();

        String RMsg;
        if (binding.swReMsg.isChecked()){ RMsg = "Yes"; } else {RMsg = "No"; }

        String RMail;
        if (binding.swReMails.isChecked()){ RMail = "Yes"; } else {RMail = "No"; }

        StudentItem item = new StudentItem(0,name,Dob,phone,mail,Add,Gender,Blood,Source,RMsg,RMail);
        return item;
    }
    public boolean ValidateData(){
        boolean valid = true;
        if (binding.etName.getText().toString().isEmpty()){
            binding.etName.setError("put valid date");
            valid = false;
        }
        if (binding.etBirthdate.getText().toString().isEmpty()){
            binding.etBirthdate.setError("put valid date");
            valid = false;
        }
        if (binding.etPhone.getText().toString().isEmpty()){
            binding.etPhone.setError("put valid date");
            valid = false;
        }
        if (binding.etMail.getText().toString().isEmpty()){
            binding.etMail.setError("put valid date");
            valid = false;
        }
        if (binding.etAddress.getText().toString().isEmpty()){
            binding.etAddress.setError("put valid date");
            valid = false;
        }
        if (binding.rgGender.getCheckedRadioButtonId()==-1){
            binding.tvGender.setError("select any one");
            valid = false;
        }
        if (binding.spBloodGrp.getSelectedItem().equals("select any one")){
            binding.tvBloodgrp.setError("select any one");
            valid = false;
        }
        if (binding.spBloodGrp.getSelectedItem().equals("select any one")){
            binding.tvSource.setError("select any one");
            valid = false;
        }
        return valid;
    }

    private void setInitialScreen() {

        ArrayAdapter BloodAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,spBloodgrp);
        BloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spBloodGrp.setAdapter(BloodAdapter);

        ArrayAdapter SourceAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,spSource);
        SourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSource.setAdapter(SourceAdapter);

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String myFormat="dd/MM/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                binding.etBirthdate.setText(dateFormat.format(calendar.getTime()));
            }
        };
        binding.etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),
                        listener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void setUpdateScr(StudentItem student) {
            binding.tvSCRTitle.setText("Update Detail");
            binding.etName.setText(student.stuName);
            binding.etBirthdate.setText(student.stuDOB);
            binding.etPhone.setText(student.stuPhone);
            binding.etMail.setText(student.stuMail);
            binding.etAddress.setText(student.stuAdd);

            if (student.stuGender.equals("Male")) {
                binding.rbMale.setChecked(true);
            } else {
                binding.rbFemale.setChecked(true);
            }

            binding.spBloodGrp.setSelection(Arrays.asList(spBloodgrp).indexOf(student.stuBlood));

            binding.spSource.setSelection(Arrays.asList(spSource).indexOf(student.stuSource));

            if (student.stuRMail.equals("Yes")) {
                binding.swReMails.setChecked(true);
            }
            if (student.stuRMsg.equals("Yes")) {
                binding.swReMsg.setChecked(true);
            }
            binding.btnSubmit.setText("Update Details");

    }
}