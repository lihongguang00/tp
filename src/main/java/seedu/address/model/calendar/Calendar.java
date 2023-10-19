package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.util.List;

import seedu.address.model.event.AllDaysEventListManager;
import seedu.address.model.event.Event;
import seedu.address.model.event.SingleDayEventList;

/**
 * Represents a calendar that stores and manages events.
 */
public class Calendar {
    private AllDaysEventListManager eventManager;

    /**
     * Constructs a Calendar object with an empty event tree.
     */
    public Calendar() {
        this.eventManager = new AllDaysEventListManager();
    }

    /**
     * Return the AllDaysEventListManager managing the events for this calendar.
     *
     * @return the AllDaysEventListManager managing the events.
     */
    public AllDaysEventListManager getEventManager() {
        return this.eventManager;
    }

    /**
     * Check if the event can be added to the calendar
     *
     * @param event event to be checked.
     * @return true if it can be added, false otherwise.
     */
    public boolean canAddEvent(Event event) {
        return this.eventManager.canAddEvent(event);
    }

    /**
     * Adds an event to the calendar.
     *
     * @param event The event to be added.
     */
    public void addEvent(Event event) {
        requireNonNull(event);
        this.eventManager.addEvent(event);
    }

    /**
     * Clears the calendar
     */
    public void clear() {
        this.eventManager.clear();
    }

    /**
     * Check if the calendar contains the event.
     *
     * @param event event to be checked.
     * @return true if it is contained in the calendar, false otherwise.
     */
    public boolean contains(Event event) {
        return this.eventManager.contains(event);
    }

    /**
     * Checks if the calendar is empty.
     *
     * @return true if calendar is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.eventManager.isEmpty();
    }

    public List<SingleDayEventList> getCurrentWeekDailyEvents() {
        return eventManager.getDaysInCurrentWeek();
    }

    public LocalTime getEarliestEventTimeInCurrentWeek() {
        return eventManager.getEarliestEventTimeInCurrentWeek();
    }

    public LocalTime getLatestEventTimeInCurrentWeek() {
        return eventManager.getLatestEventTimeInCurrentWeek();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Calendar)) {
            return false;
        }

        Calendar otherCalendar = (Calendar) other;
        return this.eventManager.equals(otherCalendar.eventManager);
    }
}
