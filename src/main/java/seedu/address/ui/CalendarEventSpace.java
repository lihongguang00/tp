package seedu.address.ui;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import seedu.address.model.calendar.ReadOnlyCalendar;
import seedu.address.model.event.Event;

/**
 * The UI component that represents the space for displaying events in a calendar view.
 */
public class CalendarEventSpace extends UiPart<Region> {
    private static final String FXML = "CalendarEventSpace.fxml";
    private static final LocalTime DEFAULT_CALENDAR_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime DEFAULT_CALENDAR_END_TIME = LocalTime.of(18, 0);
    private static final int NUMBER_OF_ROWS = 8;
    private static final int NUMBER_OF_HALF_HOURS_IN_HOUR = 2;
    private static final int NUMBER_OF_MINUTES_IN_HALF_HOUR = 30;
    private static final int NUMBER_OF_MINUTES_IN_AN_HOUR = 60;
    private static final int NODE_WIDTH_PER_HALF_HOUR = 25;
    private static final int MAXIMUM_DISPLAY_HOUR_OF_DAY = 23;
    private static final int NODE_HEIGHT = 30;

    private final ReadOnlyCalendar calendar;
    private final ObservableList<Event> eventList;
    private final ObservableList<AnchorPane> rowList = FXCollections.<AnchorPane>observableArrayList();
    private LocalTime calendarStartTime = DEFAULT_CALENDAR_START_TIME;
    private LocalTime calendarEndTime = DEFAULT_CALENDAR_END_TIME;

    @FXML
    private GridPane eventSpace;

    /**
     * Constructs a CalendarEventSpace with the given ReadOnlyCalendar.
     *
     * @param calendar The ReadOnlyCalendar to be displayed in the event space.
     */
    public CalendarEventSpace(ReadOnlyCalendar calendar) {
        super(FXML);
        this.calendar = calendar;
        eventList = calendar.getCurrentWeekEventList();
    }

    /**
     * Creates the default EventSpace layout for calendar UI.
     *
     * @param calendar ReadOnlyCalendar to be displayed in the event space.
     * @return CalendarEventSpace object with default layout.
     */
    public static CalendarEventSpace createDefaultEventSpace(ReadOnlyCalendar calendar) {
        CalendarEventSpace defaultEventSpace = new CalendarEventSpace(calendar);
        defaultEventSpace.updateStartAndEnd();
        defaultEventSpace.initializeEventSpace();
        defaultEventSpace.addEventCards();
        defaultEventSpace.addListenerToEventList();
        return defaultEventSpace;
    }

    /**
     * Creates the EventSpace layout for comparison calendar UI.
     *
     * @param calendar ReadOnlyCalendar generated from comparison to be displayed in the event space.
     * @return CalendarEventSpace object with comparison layout.
     */
    public static CalendarEventSpace createComparisonCalendarEventSpace(ReadOnlyCalendar calendar) {
        CalendarEventSpace comparisonEventSpace = new CalendarEventSpace(calendar);
        comparisonEventSpace.updateStartAndEnd();
        comparisonEventSpace.initializeEventSpace();
        comparisonEventSpace.addSolidEventCards();
        comparisonEventSpace.addListenerToComparisonCalendarEventList();
        return comparisonEventSpace;
    }

    /**
     * Initializes the event space by creating and adding empty rows to the grid.
     */
    private void initializeEventSpace() {
        Stream.<AnchorPane>generate(AnchorPane::new).limit(NUMBER_OF_ROWS).forEachOrdered(pane -> {
            pane.setPrefHeight(NODE_HEIGHT);
            pane.setPrefWidth(calculateCalendarWidth());
            pane.setMaxWidth(calculateCalendarWidth());
            eventSpace.addRow(eventSpace.getRowCount(), pane);
            rowList.add(pane);
        });
    }

    /**
     * Adds a listener to the event list to update the event space when events change.
     */
    private void addListenerToEventList() {
        eventList.addListener((ListChangeListener<Event>) c -> {
            clear();
            updateStartAndEnd();
            initializeEventSpace();
            addEventCards();
        });
    }

    /**
     * Adds a listener to the event list for the comparison calendar to update the event space when events change.
     */
    private void addListenerToComparisonCalendarEventList() {
        eventList.addListener((ListChangeListener<Event>) c -> {
            clear();
            updateStartAndEnd();
            initializeEventSpace();
            addSolidEventCards();
        });
    }

    /**
     * Clears the event space by removing all event cards.
     */
    private void clear() {
        eventSpace.getChildren().clear();
        rowList.clear();
    }

    /**
     * Calculates the width of the calendar space based on the time range being displayed.
     *
     * @return The calculated width of the calendar space.
     */
    private int calculateCalendarWidth() {
        long minutesBetweenStartAndEnd = MINUTES.between(calendarStartTime, calendarEndTime);
        int numberOfHoursToShow = calendarEndTime.getHour() - calendarStartTime.getHour();
        if (minutesBetweenStartAndEnd % NUMBER_OF_MINUTES_IN_AN_HOUR > 0) {
            numberOfHoursToShow++;
        }
        return numberOfHoursToShow * NODE_WIDTH_PER_HALF_HOUR * NUMBER_OF_HALF_HOURS_IN_HOUR;
    }


    /**
     * Updates the start and end times for the displayed calendar.
     */
    private void updateStartAndEnd() {
        LocalTime newStartTime = calendar.getEarliestEventStartTimeInCurrentWeek()
                .map(time -> {
                    return time.minusMinutes(time.getMinute());
                })
                .orElse(DEFAULT_CALENDAR_START_TIME);
        LocalTime newEndTime = calendar.getLatestEventEndTimeInCurrentWeek()
                .map(time -> {
                    if (time.getMinute() == 0 || time.getHour() == MAXIMUM_DISPLAY_HOUR_OF_DAY) {
                        return time;
                    }
                    return time.plusMinutes(NUMBER_OF_MINUTES_IN_AN_HOUR - time.getMinute());
                })
                .orElse(DEFAULT_CALENDAR_END_TIME);

        calendarStartTime = newStartTime.isBefore(DEFAULT_CALENDAR_START_TIME)
                ? newStartTime : DEFAULT_CALENDAR_START_TIME;
        calendarEndTime = newEndTime.isAfter(DEFAULT_CALENDAR_END_TIME)
                ? newEndTime : DEFAULT_CALENDAR_END_TIME;
    }

    /**
     * Adds event cards to the event space for all events.
     */
    private void addEventCards() {
        eventList.forEach(event -> {
            StackPane eventNode = generateEventCard(event);
            rowList.get(findRow(event)).getChildren().add(eventNode);
            AnchorPane.setLeftAnchor(eventNode, findLeftOffset(event));
        });
    }

    /**
     * Adds solid event cards to the event space for comparison calendar.
     */
    private void addSolidEventCards() {
        eventList.forEach(event -> {
            StackPane eventNode = generateSolidEventCard(event);
            rowList.get(findRow(event)).getChildren().add(eventNode);
            AnchorPane.setLeftAnchor(eventNode, findLeftOffset(event));
        });
    }

    /**
     * Generates an event card for the given event.
     *
     * @param event The event for which to create a card.
     * @return A StackPane containing the event card.
     */
    private StackPane generateEventCard(Event event) {
        double widthMultiplier = (double) event.getDurationOfEvent().toMinutes() / NUMBER_OF_MINUTES_IN_AN_HOUR;
        StackPane cardHolder = new StackPane();

        Rectangle cardRectangle = new Rectangle();
        cardRectangle.setHeight(NODE_HEIGHT);
        cardRectangle.setWidth(widthMultiplier * NODE_WIDTH_PER_HALF_HOUR * NUMBER_OF_HALF_HOURS_IN_HOUR);
        cardRectangle.setFill(Color.CRIMSON);
        cardRectangle.setStroke(Color.BLACK);
        cardRectangle.setStrokeType(StrokeType.INSIDE);


        Label description = new Label();
        description.setMaxHeight(NODE_HEIGHT);
        description.setMaxWidth(widthMultiplier * NODE_WIDTH_PER_HALF_HOUR * NUMBER_OF_HALF_HOURS_IN_HOUR);
        description.setText(event.getDescriptionString());
        description.setAlignment(Pos.CENTER);
        description.setEllipsisString("");

        cardHolder.getChildren().addAll(cardRectangle, description);
        return cardHolder;
    }

    /**
     * Generates a solid event card (no description and single color) for the given event.
     *
     * @param event The event for which to create a card.
     * @return A StackPane containing the solid event card.
     */
    private StackPane generateSolidEventCard(Event event) {
        double widthMultiplier = (double) event.getDurationOfEvent().toMinutes() / NUMBER_OF_MINUTES_IN_AN_HOUR;
        StackPane cardHolder = new StackPane();
        Rectangle cardRectangle = new Rectangle();
        cardRectangle.setHeight(NODE_HEIGHT);
        cardRectangle.setWidth(widthMultiplier * NODE_WIDTH_PER_HALF_HOUR * NUMBER_OF_HALF_HOURS_IN_HOUR);
        cardRectangle.setFill(Color.GREY);
        cardHolder.getChildren().add(cardRectangle);
        return cardHolder;
    }

    /**
     * Determines the row (day) in which an event should be placed in the event space.
     *
     * @param event The event for which to find the row.
     * @return The row (day) index.
     */
    private int findRow(Event event) {
        return event.getDayOfWeek().getValue();
    }


    /**
     * Calculates the left offset for positioning an event card in the event space.
     *
     * @param event The event for which to calculate the left offset.
     * @return The left offset in pixels.
     */
    private double findLeftOffset(Event event) {
        return ((double) event.getMinutesFromTimeToStartTime(calendarStartTime)
                / NUMBER_OF_MINUTES_IN_HALF_HOUR) * NODE_WIDTH_PER_HALF_HOUR;
    }
}
