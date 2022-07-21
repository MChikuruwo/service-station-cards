package zw.co.jugaad.userservice.service.iface;

import zw.co.jugaad.userservice.model.Activity;

import java.util.List;

public interface ActivityService {
    String add(Activity activity);

    Activity getById(Long id);

    List<Activity> getAll();

    List<Activity> getAllByEntityId(Long entityId);
}