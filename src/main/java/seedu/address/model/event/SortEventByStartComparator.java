package seedu.address.model.event;

import java.util.Comparator;

public class SortEventByStartComparator implements Comparator<Event> {
    @Override
    public int compare(Event event, Event otherEvent) {
        return event.compareStartTime(otherEvent);
    }
}
