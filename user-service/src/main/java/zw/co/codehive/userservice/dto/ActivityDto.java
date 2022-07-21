package zw.co.codehive.userservice.dto;

import lombok.Data;
import zw.co.codehive.userservice.model.AuditTrail;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ActivityDto implements Serializable {
    private Long id;
    private Long entityId;
    private String narrative;
    private Timestamp dateAdded;
    private AuditTrail auditTrail;
}
