package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.event.SingleDayEventList;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class CalendarWeek extends UiPart<Region> {
    private static final String FXML = "CalendarWeek.fxml";
    private static final String CALENDAR_TITLE = "My Calendar";
    private static final String HEADER_TEXT = "Time";
    private static final LocalTime DEFAULT_CALENDAR_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime DEFAULT_CALENDAR_END_TIME = LocalTime.of(18, 0);
    private static final int COLUMN_TIME_INTERVAL_IN_HOUR = 1;

    private final Calendar calendar;

    @FXML
    private HBox calendarDayHolder;

    @FXML
    private GridPane eventSpace;

    @FXML
    private Label calendarText;

    @FXML
    private Label headerText;

    @FXML
    private Label mondayText;

    @FXML
    private Label tuesdayText;

    @FXML
    private Label wednesdayText;

    @FXML
    private Label thursdayText;

    @FXML
    private Label fridayText;

    @FXML
    private Label saturdayText;

    @FXML
    private Label sundayText;

    public CalendarWeek(Calendar calendar) {
        super(FXML);
        this.calendar = calendar;
        setText();
        populateEvents();
        initializeCalendar();
    }

    public void setText() {
        calendarText.setText(CALENDAR_TITLE);
        headerText.setText(HEADER_TEXT);
        mondayText.setText(DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        tuesdayText.setText(DayOfWeek.TUESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        wednesdayText.setText(DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        thursdayText.setText(DayOfWeek.THURSDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        fridayText.setText(DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        saturdayText.setText(DayOfWeek.SATURDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        sundayText.setText(DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
    }

    public void initializeCalendar() {
        Stream.<LocalTime>iterate(DEFAULT_CALENDAR_START_TIME, time -> time.plusHours(COLUMN_TIME_INTERVAL_IN_HOUR))
                .limit(getNumberOfColumns()).map(CalendarColumn::of)
                .forEachOrdered(x -> eventSpace.addColumn(x.getColumnIndex(DEFAULT_CALENDAR_START_TIME), x.getRoot()));
    }

    public int getNumberOfColumns() {
        return DEFAULT_CALENDAR_END_TIME.getHour() - DEFAULT_CALENDAR_START_TIME.getHour();
    }

    public void populateEvents() {
        List<SingleDayEventList> listOfDays = calendar.getCurrentWeekDailyEvents();
        LocalTime timeLoopVariable = DEFAULT_CALENDAR_START_TIME;

        Stream.<LocalTime>iterate(DEFAULT_CALENDAR_START_TIME, time -> time.plusHours(1))
                .filter(time -> !time.isAfter(DEFAULT_CALENDAR_END_TIME));

    }
}
