import exception.ExitException;
import service.DeviceService;
import ui.ConsoleInterface;

import java.util.Scanner;

public class Main {

    private final DeviceService deviceService = DeviceService.getInstance();

    public static void main(String[] args) {

        ConsoleInterface ui = new ConsoleInterface(new Scanner(System.in));

        try {
            ui.mainLoop();
        } catch (ExitException e) {
            System.out.println("Досвидания");
        }
    }
}
