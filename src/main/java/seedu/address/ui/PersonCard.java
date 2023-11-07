package seedu.address.ui;

import static seedu.address.ui.UiConstants.POPUP_CALENDAR_HEIGHT;
import static seedu.address.ui.UiConstants.POPUP_CALENDAR_WIDTH;

import java.util.Comparator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String TITLE_STRING_AFTER_NAME = "'s Calendar";
    private static final int NUMBER_OF_CLICK_TO_SHOW_CALENDAR = 2;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setClickListener();
    }

    /**
     * Adds a listener to detect when the user double-clicks the PersonCard.
     */
    private void setClickListener() {
        cardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                        && mouseEvent.getClickCount() == NUMBER_OF_CLICK_TO_SHOW_CALENDAR) {
                    showCalendar();
                }
            }
        });
    }

    /**
     * Display this person's calendar
     */
    private void showCalendar() {
        Stage calendarStage = new Stage();
        calendarStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE)  {
                    calendarStage.close();
                }
            }
        });
        calendarStage.setResizable(false);
        calendarStage.setTitle(person.getName().toString() + TITLE_STRING_AFTER_NAME);
        calendarStage.setMinHeight(POPUP_CALENDAR_HEIGHT);
        calendarStage.setMinWidth(POPUP_CALENDAR_WIDTH);
        CalendarContainer root = CalendarContainer.createDefaultCalendar(person.getReadOnlyCalendar());
        calendarStage.setScene(new Scene(root.getRoot()));
        calendarStage.show();
    }
}
