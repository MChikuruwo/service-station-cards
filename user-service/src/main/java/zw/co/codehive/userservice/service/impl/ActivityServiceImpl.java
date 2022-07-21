package zw.co.codehive.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.codehive.userservice.dao.ActivityRepository;
import zw.co.codehive.userservice.service.iface.ActivityService;
import zw.co.codehive.userservice.exceptions.ActivityNotFoundException;
import zw.co.codehive.userservice.model.Activity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public String add(Activity activity) {
        activityRepository.save(activity);
        return "Activity added successfully";
    }

    @Override
    public Activity getById(Long id) {
        Optional<Activity> fromDateActivity = activityRepository.findById(id);

        if (!fromDateActivity.isPresent())
            throw new ActivityNotFoundException("Activity with ID " + id + " not found!");
        return fromDateActivity.get();
    }

    @Override
    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> getAllByEntityId(Long entityId) {
        return activityRepository.getAllByEntityId(entityId);
    }

}