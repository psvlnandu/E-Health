package com.monstertechno.moderndashbord.HealthRecordPage;

public class VaccinationHelperClass {
    String recordName,vaccineDate,vaccineName,vaccineDose,vaccineCenter;
    public VaccinationHelperClass(){

    }

    public VaccinationHelperClass(String recordName,String vaccineCenter,String vaccineDate,String vaccineDose,String vaccineName) {
        this.recordName = recordName;
        this.vaccineDate = vaccineDate;
        this.vaccineName = vaccineName;
        this.vaccineDose = vaccineDose;
        this.vaccineCenter = vaccineCenter;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(String vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineDose() {
        return vaccineDose;
    }

    public void setVaccineDose(String vaccineDose) {
        this.vaccineDose = vaccineDose;
    }

    public String getVaccineCenter() {
        return vaccineCenter;
    }

    public void setVaccineCenter(String vaccineCenter) {
        this.vaccineCenter = vaccineCenter;
    }
}
