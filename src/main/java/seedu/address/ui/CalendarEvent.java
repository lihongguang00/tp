package seedu.address.ui;

import java.time.LocalTime;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.event.Event;

public class CalendarEvent extends UiPart<Region> {
    private static final String FXML = "CalendarEvent.fxml";
    private static final int WIDTH_PER_HOUR_IN_PIXELS = 50;
    private final Event event;

    @FXML
    private StackPane calendarEventContainer;

    @FXML
    private Label eventDescription;

    public CalendarEvent(Event event) {
        super(FXML);
        this.event = event;
        eventDescription.setText(event.getDescriptionString());
    }


}
