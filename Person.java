// encapsulates the data of a person by providing private fields such as name , surname and email.
// have created public methods to access and modify this data.
public class Person {
    private String name;
    private String surname;
    private String email;

    //constructor to initializes a Person object with the provided name, surname, and email.
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // print information of the peron.
    public void printInfo() {
        System.out.println("\nName: " + name + " " + "Surname: " + surname + " " + "Email: " + email);
    }
}
