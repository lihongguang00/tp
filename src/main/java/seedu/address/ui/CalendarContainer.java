package seedu.address.ui;

import static seedu.address.ui.UiConstants.POPUP_CALENDAR_HEIGHT;
import static seedu.address.ui.UiConstants.POPUP_CALENDAR_WIDTH;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.model.calendar.ReadOnlyCalendar;

/**
 * Represents the container for displaying the calendar view in the user interface.
 * It contains various graphical elements such as the calendar label, event space, and background.
 */
public class CalendarContainer extends UiPart<Region> {
    private static final String FXML = "CalendarContainer.fxml";

    private ReadOnlyCalendar calendar;
    @FXML
    private StackPane calendarLabelPlaceholder;

    @FXML
    private StackPane eventSpaceContainer;

    @FXML
    private GridPane eventSpaceBackground;
    @FXML
    private GridPane eventSpace;

    /**
     * Constructs a CalendarContainer with the specified ReadOnlyCalendar.
     *
     * @param calendar The ReadOnlyCalendar to be displayed in the container.
     */
    public CalendarContainer(ReadOnlyCalendar calendar) {
        super(FXML);
        this.calendar = calendar;
    }

    /**
     * Creates and returns a default CalendarContainer for the specified calendar, filling it with the default
     *     components.
     *
     * @param calendar The ReadOnlyCalendar to be displayed.
     * @return The default CalendarContainer.
     */
    public static CalendarContainer createDefaultCalendar(ReadOnlyCalendar calendar) {
        CalendarContainer defaultCalendarContainer = new CalendarContainer(calendar);
        defaultCalendarContainer.fillCalendar();
        return defaultCalendarContainer;
    }

    /**
     * Creates and returns a CalendarContainer for the specified comparison calendar, filling it solid event cards.
     *
     * @param calendar The ReadOnlyCalendar generated after comparison.
     * @return The CalendarContainer for the resultant comparison calendar.
     */
    private static CalendarContainer createComparisonCalendar(ReadOnlyCalendar calendar) {
        CalendarContainer comparisonCalendarContainer = new CalendarContainer(calendar);
        comparisonCalendarContainer.fillComparisonCalendar();
        return comparisonCalendarContainer;
    }

    /**
     * Displays a comparison calendar in a separate modal window.
     *
     * @param calendar The ReadOnlyCalendar generated after comparison.
     */
    public static void displayComparisonCalendar(ReadOnlyCalendar calendar) {
        Stage comparisonCalendarStage = new Stage();
        comparisonCalendarStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE)  {
                    comparisonCalendarStage.close();
                }
            }
        });
        comparisonCalendarStage.setResizable(false);
        comparisonCalendarStage.initModality(Modality.APPLICATION_MODAL);
        comparisonCalendarStage.setMinHeight(POPUP_CALENDAR_HEIGHT);
        comparisonCalendarStage.setMinWidth(POPUP_CALENDAR_WIDTH);
        CalendarContainer root = CalendarContainer.createComparisonCalendar(calendar);
        comparisonCalendarStage.setScene(new Scene(root.getRoot()));
        comparisonCalendarStage.show();
    }

    /**
     * Fills the calendar container with the necessary components (calendar labels and event spaces).
     */
    public void fillCalendar() {
        calendarLabelPlaceholder.getChildren().add(new CalendarLabelColumn().getRoot());
        eventSpaceBackground.getChildren().add(new EventSpaceBackground(calendar).getRoot());
        eventSpace.getChildren().add(CalendarEventSpace.createDefaultEventSpace(calendar).getRoot());
    }

    /**
     * Fills the calendar container with the necessary components for the comparison calendar view.
     */
    public void fillComparisonCalendar() {
        calendarLabelPlaceholder.getChildren().add(new CalendarLabelColumn().getRoot());
        eventSpaceBackground.getChildren().add(new EventSpaceBackground(calendar).getRoot());
        eventSpace.getChildren().add(CalendarEventSpace.createComparisonCalendarEventSpace(calendar).getRoot());
    }
}
