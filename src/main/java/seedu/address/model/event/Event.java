package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Represents an event in the calendar
 */
public class Event {
    private EventDescription description;
    private EventPeriod eventPeriod;

    /**
     * Constructs an event with a given description occurring over the given time period.
     *
     * @param description The description of the event.
     * @param eventPeriod The period of time when the event occurs.
     */
    public Event(EventDescription description, EventPeriod eventPeriod) {
        requireAllNonNull(description, eventPeriod);

        this.description = description;
        this.eventPeriod = eventPeriod;
    }

    /**
     * Creates and returns a new Event object that is guaranteed to not conflict with any other event.
     *
     * @return A new non-conflicting Event object.
     */
    public static Event createNonConflictingEvent() {
        return new Event(EventDescription.createUnusedDescription(), EventPeriod.createNonConflictingPeriod());
    }

    /**
     * Gets the description of the event.
     *
     * @return The description of the event.
     */
    public EventDescription getDescription() {
        return this.description;
    }

    /**
     * Gets the time period during which the event occurs.
     *
     * @return The time period of the event.
     */
    public EventPeriod getEventPeriod() {
        return this.eventPeriod;
    }

    /**
     * Checks if this event conflicts with another event.
     *
     * @param other The other event to check for conflicts with.
     * @return True if there is a conflict, false otherwise.
     */
    public boolean isConflicting(Event other) {
        requireNonNull(other);

        return this.eventPeriod.isOverlapping(other.eventPeriod);
    }

    /**
     * Changes the description of the event.
     *
     * @param description The new description for the event.
     */
    public void changeDescription(EventDescription description) {
        requireNonNull(description);

        this.description = description;
    }

    /**
     * Changes the time period during which the event occurs.
     *
     * @param eventPeriod The new time period for the event.
     */
    public void changeEventPeriod(EventPeriod eventPeriod) {
        requireNonNull(eventPeriod);

        this.eventPeriod = eventPeriod;
    }

    /**
     * Get the dates the event spans, stored in a list.
     *
     * @return list of the dates the event spans.
     */
    public List<LocalDate> getEventDays() {
        return this.eventPeriod.getDates();
    }

    public int compareStartTime(Event other) {
        return this.eventPeriod.compareTo(other.eventPeriod);
    }

    public int compareEndTime(Event other) {
        return this.eventPeriod.compareEnd(other.eventPeriod);
    }

    public LocalTime getStartTime() {
        return this.eventPeriod.getStartTime();
    }

    public LocalTime getEndTime() {
        return this.eventPeriod.getEndTime();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return (otherEvent.description.equals(this.description) && otherEvent.eventPeriod.equals(this.eventPeriod));
    }
}
