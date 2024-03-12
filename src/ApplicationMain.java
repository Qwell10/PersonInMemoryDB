import java.util.Scanner;

public class ApplicationMain {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PersonService personService = new PersonService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add a person");
            System.out.println("2. Remove person");
            System.out.println("3. Search for a person by identification number");
            System.out.println("0. Exit the program");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    try {
                        personService.addPerson();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        personService.removePersonById();
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        personService.findPersonById();
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Application was closed.");
                    return;
                }
                default -> System.out.println("invalid choice.");
            }
        }
    }
}
