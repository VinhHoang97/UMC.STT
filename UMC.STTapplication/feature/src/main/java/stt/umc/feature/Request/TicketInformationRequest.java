package stt.umc.feature.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketInformationRequest {
    private List<ClinicalMedicalTicketRequest> lamSang;

    public TicketInformationRequest(List<ClinicalMedicalTicketRequest> lamSang, List<SubclinicalMedicalTicketRequest> cangLamSang) {
        this.lamSang = lamSang;
        this.cangLamSang = cangLamSang;
    }

    public List<ClinicalMedicalTicketRequest> getLamSang() {
        return lamSang;
    }

    public void setLamSang(List<ClinicalMedicalTicketRequest> lamSang) {
        this.lamSang = lamSang;
    }

    public List<SubclinicalMedicalTicketRequest> getCangLamSang() {
        return cangLamSang;
    }

    public void setCangLamSang(List<SubclinicalMedicalTicketRequest> cangLamSang) {
        this.cangLamSang = cangLamSang;
    }

    public TicketInformationRequest(JSONObject jsonObject) {
        try {
            this.lamSang = new ArrayList<ClinicalMedicalTicketRequest>();
            lamSang.add(new ClinicalMedicalTicketRequest(new JSONObject(jsonObject.getString("lamSang")
                    .substring(1, jsonObject.getString("lamSang").length() - 1))));
            this.cangLamSang = new ArrayList<SubclinicalMedicalTicketRequest>();
            for (int i = 0; i < jsonObject.getJSONArray("canLamSang").length(); i++) {
                cangLamSang.add(new SubclinicalMedicalTicketRequest((JSONObject) jsonObject.getJSONArray("canLamSang").get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private List<SubclinicalMedicalTicketRequest> cangLamSang;
}
