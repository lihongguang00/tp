package seedu.address.model.event;

import java.util.Comparator;

public class SortEventByEndComparator implements Comparator<Event> {
    @Override
    public int compare(Event event, Event other) {
        return event.compareEndTime(other);
    }
}
