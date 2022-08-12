package com.avaand.app.cache.impl;

import com.avaand.app.cache.model.Tracker;
import com.avaand.app.cache.TrackerService;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Log
@Component
public class TrackerServiceImpl implements TrackerService {

    private LinkedList<Tracker> trackers;

    public TrackerServiceImpl(){
        this.trackers = new LinkedList<>(); // initialize on constructor
        this.trackers.add(new Tracker(1,"Lumen"));
        this.trackers.add(new Tracker(2,"Matrix"));
        this.trackers.add(new Tracker(3,"Darknet"));
    }

    @Cacheable(cacheNames = "tracker", key = "#tracker.trackerId")
    @Override
    public Tracker findTracker(Tracker tracker) {
        log.info("Tracker Found");
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
        log.info("Getting Tracker");
        return trackers;
    }

}
