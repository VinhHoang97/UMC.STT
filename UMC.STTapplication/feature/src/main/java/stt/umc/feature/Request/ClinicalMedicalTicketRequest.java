package stt.umc.feature.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class ClinicalMedicalTicketRequest {
    protected String roomID;
    protected Integer ticketNumber;
    protected Integer roomCurrentNumber;
    protected String expectedTime;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Integer getRoomCurrentNumber() {
        return roomCurrentNumber;
    }

    public void setRoomCurrentNumber(Integer roomCurrentNumber) {
        this.roomCurrentNumber = roomCurrentNumber;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public ClinicalMedicalTicketRequest(String roomID, Integer ticketNumber, Integer roomCurrentNumber, String expectedTime) {
        this.roomID = roomID;
        this.ticketNumber = ticketNumber;
        this.roomCurrentNumber = roomCurrentNumber;
        this.expectedTime = expectedTime;
    }

    public ClinicalMedicalTicketRequest(JSONObject jsonObject) {
        try {
            this.roomID = jsonObject.getString("maPhong");
            this.ticketNumber = Integer.parseInt(jsonObject.getString("stt"));
            this.roomCurrentNumber = Integer.parseInt(jsonObject.getString("sttHienTai"));
            this.expectedTime = jsonObject.getString("thoiGianDuKien");;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
