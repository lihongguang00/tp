package seedu.address.ui;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.event.SingleDayEventList;

public class CalendarWeek extends UiPart<Region> {
    private static final String FXML = "CalendarWeek.fxml";
    private static final String CALENDAR_TITLE = "My Calendar";
    private static final String HEADER_TEXT = "Time";

    private Calendar calendar;

    @FXML
    private HBox calendarHeader;

    @FXML
    private VBox calendarDayHolder;

    @FXML
    private Label calendarTitle;

    @FXML
    private Label headerText;

    public CalendarWeek(Calendar calendar) {
        super(FXML);
        calendarTitle.setText(CALENDAR_TITLE);
        calendarTitle.setText(HEADER_TEXT);
        this.calendar = calendar;
    }

    private void setForEmptyCalendar() {

    }
}
