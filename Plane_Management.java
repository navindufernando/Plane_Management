import java.util.Scanner;

public class Plane_Management {
    private static final int[][] seatFormat = { {14}, {12},{12}, {14} };
    private static final Ticket[] tickets = new Ticket[52];
    private static int ticketCount = 0;

    // this is the entry point for the code
    // it handles the user input selections what to do with the application.
    public static void main (String[] args) {

        for (int i = 0; i < seatFormat.length; i++) {
            seatFormat[i] = new int[seatFormat[i][0]];
        }

        System.out.println("\nWelcome to the Plane Management application");

        Scanner userInput = new Scanner(System.in);
        int option;
        do{
            System.out.println("\n************************************************");
            System.out.println("*                MENU OPTIONS                  *");
            System.out.println("************************************************");

            System.out.println("1) Buy a Seat");
            System.out.println("2) Cancel a Seat");
            System.out.println("3) Find First Available Seat");
            System.out.println("4) Show Seating Plan");
            System.out.println("5) Print Tickets Information and Total Sales");
            System.out.println("6) Search Ticket");
            System.out.println("0) Quit");

            System.out.println("************************************************");
            do {
                System.out.print("Please select an option: ");
                if (userInput.hasNextInt()) {
                    option = userInput.nextInt();
                    userInput.nextLine();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number to continue.");  
                    userInput.nextLine();
                }
            } while (true);

            switch(option){
                case 1:
                    buy_seat(userInput);
                    break;
                case 2:
                    cancel_seat(userInput);
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket(userInput);
                    break;
                case 0:
                    System.out.println("\nThank you for using the Plane Management application.");
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid option.");
            }

        }while (option != 0);
        userInput.close();
    }

    /* this method get the user to input row number, seat number and person information
      such as name , surname and email address. */
    private static void buy_seat(Scanner userInput) {
        String rowNumber;
        int row;
        int seatNumber;

        while (true) {
            System.out.print("Enter the row letter (A-D) you want to reserve: ");
            rowNumber = userInput.nextLine();
            row = rowNumber.charAt(0) - 'A';

            if (row < 0 || row >= seatFormat.length) {
                System.out.println("Invalid row. Please enter a valid row letter (A-D).");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter a seat number you want to reserve: ");
            String seatInput = userInput.nextLine();

            try {
                seatNumber = Integer.parseInt(seatInput);
            } catch (NumberFormatException e) {
                System.out.println("You entered a letter. Please enter a valid seat number.");
                continue;
            }

            if (seatNumber < 1 || seatNumber > seatFormat[row].length) {
                System.out.println("Invalid seat number. Please enter a valid seat number.");
            } else {
                break;
            }
        }

        if (seatFormat[row][seatNumber - 1] == 1) {
            System.out.println("Seat is already sold. Please try another seat.");
            return;
        }

        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter your name: ");
            name = userInput.nextLine();
        }

        String surname = "";
        while (surname.isEmpty()) {
            System.out.print("Enter your surname: ");
            surname = userInput.nextLine();
        }

        String email = "";
        while (email.isEmpty()) {
            System.out.print("Enter your email: ");
            email = userInput.nextLine();
        }

        // calling the calculateSeatPrice method
        int seatCost = calculate_seat_price(seatNumber);

        seatFormat[row][seatNumber - 1] = 1;
        System.out.println("Your seat has been successfully reserved.");

        // create the person and ticket objects and calculate the total price and mark the seat as reserved.
        Person person = new Person(name, surname, email);
        Ticket ticket = new Ticket(rowNumber, seatNumber, seatCost, person);

        tickets[ticketCount] = ticket;
        ticketCount++;

        Ticket.save_ticket_file(ticket);
    }

    // calculate the seat price according to the columns.
    private static int calculate_seat_price(int seatNumber) {
        if (seatNumber <= 5) {
            return 200;
        } else if (seatNumber <= 9) {
            return 150;
        } else {
            return 180;
        }
    }

    // ask the user to enter the row letter and seat number.
    // if the seat is booked, it cancels the seat and removes the corresponding ticket from the array.
    private static void cancel_seat(Scanner userInput) {
        String rowNumber;
        int row;
        int seatNumber;

        while (true) {
            System.out.print("Enter the row letter A-D you want to cancel: ");
            rowNumber = userInput.nextLine();
            row = rowNumber.charAt(0) - 'A';

            if (row < 0 || row >= seatFormat.length) {
                System.out.println("Invalid row. Please enter a valid row letter.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter a seat number you want to cancel: ");
            String seatInput = userInput.nextLine();

            try {
                seatNumber = Integer.parseInt(seatInput);
            } catch (NumberFormatException e) {
                System.out.println("You entered a letter. Please enter a valid seat number you want to cancel.");
                continue;
            }

            if (seatNumber < 1 || seatNumber > seatFormat[row].length) {
                System.out.println("Invalid seat number. Please enter a valid seat number you want to cancel.");
            } else {
                break;
            }
        }

        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = tickets[i];

            if (ticket.getRow().equals(rowNumber) && ticket.getSeat() == seatNumber) {
                for (int j = i; j < ticketCount - 1; j++) {
                    tickets[j] = tickets[j + 1];
                }

                tickets[ticketCount - 1] = null;
                ticketCount--;

                // Update the corresponding seat in the array
                seatFormat[row][seatNumber - 1] = 0;
                // to delete the saved ticket text file.
                Ticket.delete_ticket_file(rowNumber + seatNumber + ".txt");


                System.out.println("Ticket successfully cancelled.");
                return;
            }
        }

        System.out.println("No ticket found for the specified seat.");
    }

    // iterates through the seatFormat array and finds the first seat that is not booked.
    private static void find_first_available() {
        boolean isSeatFound = false;
        char rowValue = 'A';
        int seatValue = 1;

        for (int i = 0; i < seatFormat.length && !isSeatFound; i++) {
            for (int j = 0; j < seatFormat[i].length && !isSeatFound; j++) {
                if (seatFormat[i][j] == 0) {
                    rowValue  = (char) ('A' + i);
                    seatValue = j + 1;
                    isSeatFound = true;
                }
            }
        }

        if (isSeatFound) {
            System.out.println("The first available seat is: " + rowValue  + seatValue);
        } else {
            System.out.println("No available seats found.");
        }
    }

    // this displays the current seating plan, marking booked seats with 'X' and available seats with 'O'
    private static void show_seating_plan() {
        System.out.println("\nSeat Reservation Plan:");

        int i = 0;
        while (i < seatFormat.length) {
            System.out.print((char) ('A' + i) + " ");

            int j = 0;
            while (j < seatFormat[i].length) {
                if (seatFormat[i][j] == 0) {
                    System.out.print(" O ");
                } else {
                    System.out.print(" X ");
                }
                j++;
            }

            System.out.println();
            i++;
        }
    }

    // this method iterates through the tickets array
    // prints the information of each ticket
    // calculates the total sales of tickets
    private static void print_tickets_info() {
        int totalAmount = 0;
        System.out.println("Tickets Information:");
        for (Ticket ticket : tickets) {
            if (ticket == null) {
                continue;
            }
            ticket.print_info();
            totalAmount += (int) ticket.getPrice();
        }
        if (totalAmount > 0) {
            System.out.println("Total amount: £" + totalAmount);
        } else {
            System.out.println("No tickets purchased.");
        }
    }

    // get user input of row and seat and search for the matching ticket.
    private static void search_ticket(Scanner userInput) {
        String rowNumber;
        int row;
        int seatNumber;

        while (true) {
            System.out.print("Enter the row you want to search: ");
            rowNumber = userInput.nextLine();
            row = rowNumber.charAt(0) - 'A';

            if (row < 0 || row >= seatFormat.length) {
                System.out.println("Invalid row. Please enter a valid row letter.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter a seat number you want to search: ");
            String seatInput = userInput.nextLine();

            try {
                seatNumber = Integer.parseInt(seatInput);
            } catch (NumberFormatException e) {
                System.out.println("You entered a letter. Please enter a valid seat number you want to search.");
                continue;
            }

            if (seatNumber < 1 || seatNumber > seatFormat[row].length) {
                System.out.println("Invalid seat number. Please enter a valid seat number you want to search.");
            } else {
                break;
            }
        }

        boolean ifTicketFound = false;

        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = tickets[i];

            if (ticket != null && ticket.getRow().equals(rowNumber) && ticket.getSeat() == seatNumber) {
                ticket.print_info();
                ifTicketFound = true;
                break; // here we use break to stop the search once a ticket is found
            }
        }

        if (!ifTicketFound) {
            System.out.println("No ticket found for the specified seat.");
        }
    }
}

