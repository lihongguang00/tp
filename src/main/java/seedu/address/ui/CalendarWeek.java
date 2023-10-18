package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.event.SingleDayEventList;

public class CalendarWeek extends UiPart<Region> {
    private static final String FXML = "CalendarWeek.fxml";
    private static final String CALENDAR_TITLE = "My Calendar";

    @FXML
    private VBox calendarDayHolder;

    @FXML
    private Label calendarText;

    public CalendarWeek() {
        super(FXML);
        calendarText.setText(CALENDAR_TITLE);
    }
}
