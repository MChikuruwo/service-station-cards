package zw.co.codehive.userservice.service.iface;

import zw.co.codehive.userservice.model.Activity;

import java.util.List;

public interface ActivityService {
    String add(Activity activity);

    Activity getById(Long id);

    List<Activity> getAll();

    List<Activity> getAllByEntityId(Long entityId);
}