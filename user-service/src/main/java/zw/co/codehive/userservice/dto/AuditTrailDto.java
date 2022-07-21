package zw.co.codehive.userservice.dto;

import lombok.Data;
import zw.co.codehive.userservice.model.User;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class AuditTrailDto implements Serializable {
    private Long id;
    private Timestamp dateAdded;
    private Timestamp dateUpdated;
    private User user;
}
