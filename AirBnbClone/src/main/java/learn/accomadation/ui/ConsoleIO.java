package learn.accomadation.ui;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class ConsoleIO {
    private static final String INVALID_NUMBER = "[INVALID] Enter a valid number.";
    private static final String INVALID_DATE = "[INVALID] Enter a date in MM/dd/yyyy format.";
    private static final String REQUIRED = "[INVALID] Field is required";

    private static final String NUMBER_OUT_OF_RANGE = "[INVALID] Enter a number between %s and %s.";

    private final Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message){
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public String readString(String prompt){
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        while(true){
            String result = readString(prompt);
            if(!result.isBlank()) {
                return result;
            }
            println(REQUIRED);
        }
    }

    public int readInt(String prompt) {
        while(true){
            try{
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException exception){
                println(INVALID_NUMBER);
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while(true){
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public int readPosInt(String prompt) {
        while (true){
            int result = readInt(prompt);
            if (result > 0) {
                return result;
            }
            println(INVALID_NUMBER);
        }
    }
    public LocalDate readLocalDate(String prompt) {
        while(true){
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input,formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")){
                return true;
            } else if (input.equals("n")){
                return false;
            }
            println("[INVALID] Please enter 'y' or 'n'.");
        }
    }
}
