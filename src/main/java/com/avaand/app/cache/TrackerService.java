package com.avaand.app.cache;

import java.util.List;

public interface TrackerService {
    Tracker findTracker(Tracker tracker);
    Tracker updateTracker(Tracker tracker);
    Tracker deleteTracker(Tracker tracker);
    List<Tracker> getTrackers();
}
