package stt.umc.feature.Request;

import java.util.Date;

public class patientRequest {
    private String patientName;
    private Date patientBirthday;
    private String  gender;

    public patientRequest(String patientName, Date patientBirthday, String gender) {
        this.patientName = patientName;
        this.patientBirthday = patientBirthday;
        this.gender = gender;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(Date patientBirthday) {
        this.patientBirthday = patientBirthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
