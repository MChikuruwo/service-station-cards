package zw.co.jugaad.userservice.service.iface;

import zw.co.jugaad.userservice.model.AuditTrail;

import java.util.List;

public interface AuditTrailService {
    String add(AuditTrail auditTrail);

    List<AuditTrail> getAll();

    List<AuditTrail> getByUserId(Long userId);

    AuditTrail getById(Long id);
}