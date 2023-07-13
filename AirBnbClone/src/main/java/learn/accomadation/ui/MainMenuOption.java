package learn.accomadation.ui;

public enum MainMenuOption {
    VIEW(1, "View Reservations for Host"),
    MAKE(2, "Make a Reservation"),
    EDIT(3, "Edit a Reservation"),
    CANCEL(4, "Cancel a Reservation"),
    EXIT(5, "Exit");

    private int value;
    private String message;

    MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
