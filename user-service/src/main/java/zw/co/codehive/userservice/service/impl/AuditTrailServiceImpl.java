package zw.co.codehive.userservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.codehive.userservice.dao.AuditTrailRepository;
import zw.co.codehive.userservice.exceptions.ActivityNotFoundException;
import zw.co.codehive.userservice.model.AuditTrail;
import zw.co.codehive.userservice.service.iface.AuditTrailService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuditTrailServiceImpl implements AuditTrailService {

    private final AuditTrailRepository auditTrailRepository;

    @Autowired
    public AuditTrailServiceImpl(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public String add(AuditTrail auditTrail) {
        auditTrailRepository.save(auditTrail);
        return "Activity added successfully";
    }

    @Override
    public AuditTrail getById(Long id) {
        Optional<AuditTrail> fromDatabaseAuditTrail = auditTrailRepository.findById(id);

        if (!fromDatabaseAuditTrail.isPresent())
            throw new ActivityNotFoundException("AuditTrail with ID " + id + " not found!");
        return fromDatabaseAuditTrail.get();
    }

    @Override
    public List<AuditTrail> getAll() {
        return auditTrailRepository.findAll();
    }

    @Override
    public List<AuditTrail> getByUserId(Long userId) {
        return auditTrailRepository.findByUser(userId);
    }
}