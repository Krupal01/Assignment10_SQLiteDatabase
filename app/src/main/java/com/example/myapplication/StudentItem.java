package com.example.myapplication;

public class StudentItem {
    int stuID;
    String stuName ;
    String stuDOB ;
    String stuPhone;
    String stuMail ;
    String stuAdd ;
    String stuGender ;
    String stuBlood ;
    String stuSource ;
    String stuRMsg ;
    String stuRMail;

    public boolean isEmpty(){
        if (this.stuName.isEmpty() && this.stuDOB.isEmpty() && this.stuPhone.isEmpty() && this.stuMail.isEmpty() &&
                this.stuAdd.isEmpty() && this.stuGender.isEmpty() && this.stuBlood.isEmpty() && this.stuSource.isEmpty() &&
                this.stuRMsg.isEmpty()&&this.stuRMail.isEmpty()){
            return true;
        }
        return false;
    }


    public StudentItem(int stuID,String stuName, String stuDOB, String stuPhone, String stuMail, String stuAdd, String stuGender, String stuBlood, String stuSource, String stuRMsg, String stuRMail) {
        this.stuID = stuID;
        this.stuName = stuName;
        this.stuDOB = stuDOB;
        this.stuPhone = stuPhone;
        this.stuMail = stuMail;
        this.stuAdd = stuAdd;
        this.stuGender = stuGender;
        this.stuBlood = stuBlood;
        this.stuSource = stuSource;
        this.stuRMsg = stuRMsg;
        this.stuRMail = stuRMail;
    }
}
