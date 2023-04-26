package esprit.changemakers.sportshub.entities;

import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;

public class Certification {
    int id;
    private String certifName;
    private SpecialityEnum speciality;
    private int idUser;

    public Certification() {
    }

    public Certification(int id, String certifName, SpecialityEnum speciality, int idUser) {
        this.id = id;
        this.certifName = certifName;
        this.speciality = speciality;
        this.idUser = idUser;
    }

    public Certification(String certifName, SpecialityEnum speciality, int idUser) {
        this.certifName = certifName;
        this.speciality = speciality;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCertifName() {
        return certifName;
    }

    public void setCertifName(String certifName) {
        this.certifName = certifName;
    }

    public SpecialityEnum getSpeciality() {
        return speciality;
    }

    public void setSpeciality(SpecialityEnum speciality) {
        this.speciality = speciality;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj ) return true;
        if(obj == null) return false;
        if(!(obj instanceof User)) return false;

        Certification other = (Certification)obj;
        return this.certifName.equals(other.getCertifName()) && this.idUser == other.getIdUser()
                && this.speciality.toString().equals(other.getSpeciality().toString());
    }

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", certifName='" + certifName + '\'' +
                ", speciality=" + speciality +
                ", idUser=" + idUser +
                '}';
    }
}

