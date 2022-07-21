package zw.co.codehive.userservice.service.iface;

import zw.co.codehive.userservice.model.AuditTrail;

import java.util.List;

public interface AuditTrailService {
    String add(AuditTrail auditTrail);

    List<AuditTrail> getAll();

    List<AuditTrail> getByUserId(Long userId);

    AuditTrail getById(Long id);
}