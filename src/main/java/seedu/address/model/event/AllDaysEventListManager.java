package seedu.address.model.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Manages the SingleDayEventList objects for every day.
 */
public class AllDaysEventListManager {
    private static final int NUMBER_OF_DAYS_PER_WEEK = 7;
    private final NavigableMap<String, SingleDayEventList> dayToEventListMap;

    /**
     * Creates a AllDaysEventListManager to manage the daily events.
     */
    public AllDaysEventListManager() {
        this.dayToEventListMap = new TreeMap<String, SingleDayEventList>();
    }

    /**
     * Check if the event is conflicting with any events stored in the manager.
     *
     * @param event event to be added.
     * @return true if the event can be added, false otherwise.
     */
    public boolean canAddEvent(Event event) {
        List<LocalDate> eventDays = event.getEventDays();
        return eventDays.stream().filter(x -> dayToEventListMap.containsKey(x.toString()))
                .allMatch(x -> dayToEventListMap.get(x.toString()).isEventAddValid(event));
    }

    /**
     * Stores the event to the appropriate SingleDayEventList objects corresponding to the event's dates.
     *
     * @param event event to be added.
     */
    public void addEvent(Event event) {
        List<LocalDate> eventDays = event.getEventDays();
        for (LocalDate date : eventDays) {
            if (!(dayToEventListMap.containsKey(date.toString()))) {
                dayToEventListMap.put(date.toString(), new SingleDayEventList(date));
            }
            dayToEventListMap.get(date.toString()).addEvent(event);
        }
    }

    public List<SingleDayEventList> getDaysInCurrentWeek() {
        LocalDate dateToday = LocalDate.now();
        LocalDate startOfWeekDate = dateToday.minusDays(dateToday.getDayOfWeek().getValue() - 1);
        return Stream.<LocalDate>iterate(startOfWeekDate, x -> x.plusDays(1)).limit(NUMBER_OF_DAYS_PER_WEEK)
                .map(x -> dayToEventListMap.getOrDefault(x.toString(), new SingleDayEventList(x)))
                .collect(Collectors.toList());
    }

    public LocalTime getEarliestEventTimeInCurrentWeek() {
        return this.getDaysInCurrentWeek().stream().filter(x -> !x.isEmpty()).map(SingleDayEventList::getEarliestEvent)
                .min(new SortEventByStartComparator()).map(Event::getStartTime).orElse(LocalTime.MAX);
    }

    /**
     * Clears the manager.
     */
    public void clear() {
        this.dayToEventListMap.clear();
    }

    /**
     * Check if the event is stored in this manager.
     *
     * @param event event to be checked.
     * @return true if it is stored, false otherwise.
     */
    public boolean contains(Event event) {
        for (LocalDate date : event.getEventDays()) {
            if (!(this.dayToEventListMap.getOrDefault(date.toString(), new SingleDayEventList(date))
                    .containsEvent(event))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if this manager has an equivalent SingleDayEventList object stored in its dayToEventListMap.
     *
     * @param day SingleDayEventList object to be checked.
     * @return true if there is a equivalent SingleDayEventList object stored in this manager, false otherwise.
     */
    private boolean contains(SingleDayEventList day) {
        for (SingleDayEventList thisDay : this.dayToEventListMap.values()) {
            if (thisDay.equals(day)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the manager is empty.
     *
     * @return true if the manager has no events stored, false otherwise.
     */
    public boolean isEmpty() {
        return this.dayToEventListMap.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AllDaysEventListManager)) {
            return false;
        }

        AllDaysEventListManager otherManager = (AllDaysEventListManager) other;

        if (!(this.dayToEventListMap.size() == otherManager.dayToEventListMap.size())) {
            return false;
        }

        for (SingleDayEventList day : this.dayToEventListMap.values()) {
            if (!otherManager.contains(day)) {
                return false;
            }
        }
        return true;
    }
}
