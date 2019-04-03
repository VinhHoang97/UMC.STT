package stt.umc.feature.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryRequest {
    private String idHistory;
    private String hoTen;
    private Integer tuoi;

    public HistoryRequest(String idHistory, String hoTen, Integer tuoi) {
        this.idHistory = idHistory;
        this.hoTen = hoTen;
        this.tuoi = tuoi;
    }

    public HistoryRequest(JSONObject jsonObject) {
        try {
            this.idHistory = jsonObject.getString("idHistory");
            this.hoTen = jsonObject.getString("hoTen");
            this.tuoi = Integer.parseInt(jsonObject.getString("tuoi"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(String idHistory) {
        this.idHistory = idHistory;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Integer getTuoi() {
        return tuoi;
    }

    public void setTuoi(Integer tuoi) {
        this.tuoi = tuoi;
    }
}
