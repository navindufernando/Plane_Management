import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

// the Ticket class encapsulates the ticket information and provides methods to access and modify it
// this contains an instance of the Person class to represent the person who owns the ticket
public class Ticket {
    // instance Variables
    private String row;
    private int seat;
    private double price;
    private Person person;

    // constructor
    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // prints the ticket information and the person information
    public void print_info() {
        System.out.println("\nRow letter: " + row + " " + "Seat number: " + seat +" " + "Ticket price: £" + price);
        System.out.println("\nPerson Information:");
        person.printInfo();
    }
    // saves the ticket information to a file
    public static void save_ticket_file(Ticket ticket) {
        String fileName = ticket.getRow() + ticket.getSeat() + ".txt";

        try (PrintWriter writer = new PrintWriter(fileName)) {
            write_ticket_information(writer, ticket);
            write_person_information(writer, ticket);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to save ticket information.");
        }
    }

    private static void write_ticket_information(PrintWriter writer, Ticket ticket) {
        writer.println("Ticket Information:");
        writer.println("\nRow letter: " + ticket.getRow());
        writer.println("Seat number: " + ticket.getSeat());
        writer.println("Ticket price: £" + ticket.getPrice());
    }

    private static void write_person_information(PrintWriter writer, Ticket ticket) {
        writer.println("\nPerson Information:");
        writer.println("\nName: " + ticket.getPerson().getName());
        writer.println("Surname: " + ticket.getPerson().getSurname());
        writer.println("Email: " + ticket.getPerson().getEmail());
    }

    // deletes the ticket file
    public static void delete_ticket_file(String fileName) {
        File file = new File(fileName);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }



}
