package cinema;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 * Cinema Room Manager: Manage cinema ticketing, non-graphics
 * All variables/methods are declared as static (belong to the class)
 * Presented by: pad89
 */

public class Cinema {
    // Size of the cinema
    public static int totalRows;
    public static int totalSeats;

    // Ticket coordinate
    public static int numRow;
    public static int numSeat;

    // Ticket price
    public static int ticketPrice;

    // Cinema statistics
    public static int purchasedTicket = 0;
    public static double percentage = 0;
    public static int currentIncome = 0;
    public static int totalIncome = 0;

    // User input menu
    public static final byte EXIT = 0;
    public static final byte SHOW_SEATS = 1;
    public static final byte BUY_TICKET = 2;
    public static final byte STATISTICS = 3;
    public static byte userInput = 4;

    public static String[][] cinema;

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        getCinemaSize();
        System.out.println();
        cinema = new String[totalRows + 1][totalSeats + 1];
        getCinemaRoom();

        outer:
        do {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            userInput = scanner.nextByte();
            System.out.println();

            if (userInput == SHOW_SEATS) {
                printCinemaRoom();
            } else if (userInput == BUY_TICKET) {
                do {
                    getTicketBought();
                    System.out.println();
                    if (numRow > totalRows || numSeat > totalSeats) {
                        System.out.println("Wrong input!");
                        System.out.println();
                    } else if (cinema[numRow][numSeat].equals("B")) {
                        System.out.println("That ticket has already been purchased!");
                        System.out.println();
                    } else {
                        boughtTicket();
                        ticketPrice();
                        currentIncome += ticketPrice;
                        continue outer;
                    }
                } while (userInput == 2);


            } else if (userInput == STATISTICS) {
                printCinemaStatistic();
                System.out.println();
            }

        } while (userInput != EXIT);
    }

    public static void getCinemaRoom() {
        for (int row = 0; row <= totalRows; row++) {
            for (int seat = 0; seat <= totalSeats; seat++) {
                if (row == 0 && seat == 0) {
                    cinema[0][0] = " ";
                } else if (row == 0 && seat > 0) {
                    cinema[row][seat] = Integer.toString(seat);
                } else if (row > 0 && seat == 0) {
                    cinema[row][seat] = Integer.toString(row);
                } else {
                    cinema[row][seat] = "S";
                }
            }
        }
    }

    public static void printCinemaRoom() {
        System.out.println("Cinema:");
        for (int row = 0; row <= totalRows; row++) {
            for (int seat = 0; seat <= totalSeats; seat++) {
                if (seat == totalSeats) {
                    System.out.print(cinema[row][seat] + " ");
                    System.out.println();
                } else {
                    System.out.print(cinema[row][seat] + " ");
                }

            }
        }
        System.out.println();
    }

    public static void printCinemaStatistic() {
        DecimalFormat df1 = new DecimalFormat("#0.00'%'");
        DecimalFormat df2 = new DecimalFormat("$#");
        totalIncome();
        getTicketPercentage();
        System.out.println("Number of purchased tickets: " + purchasedTicket);
        System.out.println("Percentage: " + df1.format(percentage));
        System.out.println("Current income: " + df2.format(currentIncome));
        System.out.println("Total income: " + df2.format(totalIncome));
    }

    // Get Data
    public static void getCinemaSize() {
        System.out.println("Enter the number of rows:");
        totalRows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        totalSeats = scanner.nextInt();
    }

    public static void getTicketBought() {
        System.out.println("Enter a row number:");
        numRow = scanner.nextInt();

        System.out.println("Enter a seat number in that row:");
        numSeat = scanner.nextInt();
    }

    // Get result data
    public static void totalIncome() {
        if (totalRows * totalSeats <= 60) {
            totalIncome = totalRows * totalSeats * 10;
        } else {
            totalIncome = (totalRows / 2) * totalSeats * 10 + ((totalRows - totalRows / 2)
                    * totalSeats * 8);
        }
    }

    public static void ticketPrice() {
        if (totalRows * totalSeats <= 60) {
            ticketPrice = 10;
        } else {
            if (numRow > totalRows / 2) {
                ticketPrice = 8;
            } else {
                ticketPrice = 10;
            }
        }

        System.out.print("Ticket price: ");
        System.out.println(NumberFormat
                .getCurrencyInstance(new Locale("en", "US"))
                .format(ticketPrice));
        System.out.println();
    }

    public static void boughtTicket() {
        cinema[numRow][numSeat] = "B";
        ++purchasedTicket;
    }

    public static void getTicketPercentage() {
        percentage = 100 * (double) purchasedTicket / (double) (totalRows * totalSeats);
        System.out.println(percentage);
    }
}