package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CalendarColumn extends UiPart<Region> {
    private static final String FXML = "CalendarColumn.fxml";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hha");

    private final LocalTime time;

    @FXML
    private Label timeHeader;

    public CalendarColumn(LocalTime time) {
        super(FXML);
        this.time = time;
        timeHeader.setText(time.format(TIME_FORMATTER));
    }

    public static CalendarColumn of(LocalTime time) {
        return new CalendarColumn(time);
    }

    public int getColumnIndex(LocalTime startTime) {
        System.out.println(startTime);
        System.out.println(time);
        return time.getHour() - startTime.getHour();
    }
}
