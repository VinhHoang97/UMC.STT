package stt.umc.feature.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class SubclinicalMedicalTicketRequest extends ClinicalMedicalTicketRequest {
    public String getFunctionalName() {
        return functionalName;
    }

    public void setFunctionalName(String functionalName) {
        this.functionalName = functionalName;
    }

    private String functionalName;
    public SubclinicalMedicalTicketRequest(String roomID, Integer ticketNumber, Integer roomCurrentNumber, String expectedTime) {
        super(roomID, ticketNumber, roomCurrentNumber, expectedTime);
    }


    public SubclinicalMedicalTicketRequest(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("maPhongCls"),
                Integer.parseInt(jsonObject.getString("stt")),
                Integer.parseInt(jsonObject.getString("sttHienTai")),
                jsonObject.getString("thoiGianDuKien")
        );
        this.functionalName=jsonObject.getString("tenPhong");
    }
}
