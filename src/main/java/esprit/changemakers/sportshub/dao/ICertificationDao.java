package esprit.changemakers.sportshub.dao;

import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;

import java.util.List;

public interface ICertificationDao extends IGenericDao<Certification>{
    List<Certification> getAllCertifByUserID(int id);

    List<Certification> getAllCertifBySpeciality(Enum<SpecialityEnum> specialityEnum);
}
