import java.util.Objects;

public record PersonEntity(String name, String surname, String identificationNumber) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonEntity person)) return false;
        return Objects.equals(name(), person.name()) && Objects.equals(surname(), person.surname()) && Objects.equals(identificationNumber(), person.identificationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), surname(), identificationNumber());
    }
}
