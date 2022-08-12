package com.avaand.app.cache.impl;

import com.avaand.app.cache.Tracker;
import com.avaand.app.cache.TrackerService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackerServiceImpl implements TrackerService {

    private List<Tracker> trackers;

    public TrackerServiceImpl(){
        this.trackers = new ArrayList<>(); // initialize on constructor
        this.trackers.add(new Tracker(1,"Lumen"));
        this.trackers.add(new Tracker(2,"Matrix"));
        this.trackers.add(new Tracker(3,"Darknet"));
    }

    @Cacheable(cacheNames = "tracker", key = "#tracker.trackerId")
    @Override
    public Tracker findTracker(Tracker tracker) {
        return trackers.get(tracker.getTrackerId());
    }

    @CachePut(cacheNames = "tracker", key = "#tracker.trackerId")
    @Override
    public Tracker updateTracker(Tracker tracker) {
        return trackers.set(tracker.getTrackerId(),tracker);
    }

    @CacheEvict(cacheNames = "tracker", key = "#tracker.trackerId")
    @Override
    public Tracker deleteTracker(Tracker tracker) {
        return this.trackers.remove((int)tracker.getTrackerId());
    }

    @Cacheable(cacheNames = "tracker")
    @Override
    public List<Tracker> getTrackers() {
        return trackers;
    }

}
