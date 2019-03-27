package stt.umc.feature.Request;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;

import stt.umc.feature.Utils.GlobalUtils;

public class PatientRequest {
    private String patientID;
    private String FirstName; //Tên
    private String LastName;//Họ
    private String MiddleName;//Tên lót
    private String patientBirthday;
    private String HomeTown;//quê quán
    private String gender;// Giới tính
    private String profession;//nghề nghiệp
    private String phoneNumber;// SDT
    private String address; // Địa chỉ

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(String patientBirthday) {
        this.patientBirthday = patientBirthday;
    }

    public String getHomeTown() {
        return HomeTown;
    }

    public void setHomeTown(String homeTown) {
        HomeTown = homeTown;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PatientRequest(String patientID, String firstName, String lastName, String middleName, String patientBirthday,
                          String homeTown, String gender, String profession, String phoneNumber, String address) {
        this.patientID = patientID;
        FirstName = firstName;
        LastName = lastName;
        MiddleName = middleName;
        this.patientBirthday = patientBirthday;
        HomeTown = homeTown;
        this.gender = gender;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public PatientRequest(JSONObject jsonObject) {
        try {
            this.patientID = jsonObject.getString("ID");
            FirstName = jsonObject.getString("Ten");
            LastName = jsonObject.getString("Ho");
            ;
            MiddleName = jsonObject.getString("TenLot");
            ;
            this.patientBirthday = jsonObject.getString("NgaySinh");
            HomeTown = jsonObject.getString("QueQuan");
            ;
            this.gender = jsonObject.getString("GioiTinh");
            ;
            this.profession = jsonObject.getString("NgheNghiep");
            ;
            this.phoneNumber = jsonObject.getString("SDT");
            ;
            this.address = jsonObject.getString("Diachi");
            ;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
