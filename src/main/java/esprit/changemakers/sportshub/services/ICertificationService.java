package esprit.changemakers.sportshub.services;

import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;

import java.util.List;

public interface ICertificationService extends IGenericService<Certification>{
    public List<Certification> getAllCertifByUserId(int id);
    public List<Certification> getAllCertifBySpeciality(Enum<SpecialityEnum> specialityEnum);
}
