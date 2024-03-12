import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PersonService {
    /**
     * Format YYMMDD/XXXX.
     */
    private static final String regex1 = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])/\\d{4}$";
    /**
     * Format YYMMDDXXX.
     */
    private static final String regex2 = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}$";

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<PersonEntity> persons = new ArrayList<>();


    /**
     * Add new person to database.
     */
    public void addPerson() {
        System.out.println("Enter a name:");
        String name = scanner.next();

        System.out.println("Enter a surname:");
        String surname = scanner.next();

        System.out.println("Enter an identification number:");
        String id = scanner.next();
        if (validateId(id)) {
            System.out.println("Identification number is not in the format YYMMDDXXX or YYMMDD/XXXX");
            return;
        }

        PersonEntity person = new PersonEntity(name, surname, id);
        if (persons.contains(person)) {
            throw new IllegalArgumentException("This person already exists in the database.");
        }
        persons.add(person);

        System.out.printf("Person %s %s %s was added to database.", name, surname, id);
    }

    /**
     * Remove person from database.
     */
    public void removePersonById() {
        System.out.println("Enter the identification number of the person you want to remove:");
        String id = scanner.next();
        if (validateId(id)) {
            System.out.println("Identification number is not in the format YYMMDDXXX or YYMMDD/XXXX");
            return;
        }

        PersonEntity findingPerson = persons.stream()
                .filter(p -> p.identificationNumber().equals(id))
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException(id));

        persons.remove(findingPerson);
        System.out.printf("Person with identification number %s was removed.", id);
    }

    /**
     * Find person by identification number.
     */
    public void findPersonById() {
        System.out.println("Enter the identification number of the person you want to find:");
        String id = scanner.next();
        if (validateId(id)) {
            System.out.println("Identification number is not in the format YYMMDDXXX or YYMMDD/XXXX");
            return;
        }

        PersonEntity findingPerson = persons.stream()
                .filter(p -> p.identificationNumber().equals(id))
                .findAny()
                .orElseThrow(() -> new PersonNotFoundException(id));

        LocalDate birthDate = parseBirtDate(id);
        Period age = Period.between(birthDate, LocalDate.now());

        System.out.printf("Person found: %s %s %s %s years old.", findingPerson.name(), findingPerson.surname(), findingPerson.identificationNumber(), age.getYears());
    }

    /**
     * Validate identification number for the correct format -> YYMMDD/XXXX or YYMMDDXXX.
     */
    private boolean validateId(String id) throws IllegalArgumentException {
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);

        return !pattern1.matcher(id).matches() && !pattern2.matcher(id).matches();
    }

    /**
     * Calculates age from identification number.
     */
    private static LocalDate parseBirtDate(String identificationNumber) {
        int year = Integer.parseInt(identificationNumber.substring(0, 2));
        if (year < 50) {
            year += 2000;
        } else {
            year += 1900;
        }
        int month = Integer.parseInt(identificationNumber.substring(2, 4));
        if (month > 50) {
            month -= 50;
        }
        int day = Integer.parseInt(identificationNumber.substring(4, 6));
        return LocalDate.of(year, month, day);
    }

    private static class PersonNotFoundException extends RuntimeException {
        public PersonNotFoundException(String id) {
            super("Person with identification number " + id + " was not found.");
        }
    }
}
