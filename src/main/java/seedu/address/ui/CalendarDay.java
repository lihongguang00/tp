package seedu.address.ui;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.SingleDayEventList;

public class CalendarDay extends UiPart<Region> {
    private static final String FXML = "CalendarDay.fxml";

    public final SingleDayEventList dayEventList;

    @FXML
    private Label day;

    @FXML
    private HBox hourlyScheduleHolder;


    public CalendarDay(SingleDayEventList dayEventList) {
        super(FXML);
        this.dayEventList = dayEventList;
        this.day.setText(dayEventList.getDayString());
    }


}
