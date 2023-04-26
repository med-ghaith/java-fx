package esprit.changemakers.sportshub.services.Impl;

import esprit.changemakers.sportshub.dao.ICertificationDao;
import esprit.changemakers.sportshub.dao.Impl.CertificationDaoImpl;
import esprit.changemakers.sportshub.entities.Certification;
import esprit.changemakers.sportshub.services.ICertificationService;
import esprit.changemakers.sportshub.utils.enumerations.SpecialityEnum;
import javafx.collections.ObservableList;

import java.util.List;

public class CertificationServiceImpl implements ICertificationService {

    ICertificationDao certificationDao;

    public CertificationServiceImpl() {
        this.certificationDao = new CertificationDaoImpl();
    }

    @Override
    public Certification create(Certification entity) {
        if(certificationDao.find(entity)) return entity;
        return certificationDao.save(entity);
    }

    @Override
    public void update(Certification entity) {
        certificationDao.update(entity);
    }

    @Override
    public void remove(int id) {
        certificationDao.deleteById(id);
    }

    @Override
    public Certification getById(int id) {
        return certificationDao.getById(id);
    }

    @Override
    public ObservableList<Certification> getAll() {
        return certificationDao.getAll();
    }

    @Override
    public List<Certification> getAllCertifByUserId(int id) {
        return certificationDao.getAllCertifByUserID(id);
    }

    @Override
    public List<Certification> getAllCertifBySpeciality(Enum<SpecialityEnum> specialityEnum) {
        return certificationDao.getAllCertifBySpeciality(specialityEnum);
    }
}
