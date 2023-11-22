package ViewModel;

import Model.User;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class BestGymEverViewModel
{
    // File paths for input (customers.txt) and output (customers_recurring.txt) files
    private static final String FILENAME = "/Users/gustavsoderberg/IdeaProjects/Best_Gym_Ever_Inlamningsuppgift_2_Gustav_Soderberg_JAVAD23/src/Model/customers.txt";
    private static final String RECURRING_CUSTOMERS_FILE = "/Users/gustavsoderberg/IdeaProjects/Best_Gym_Ever_Inlamningsuppgift_2_Gustav_Soderberg_JAVAD23/src/Model/customers_recurring.txt";

    // Method to check membership status based on user input (id or name)
    public String checkMembership(String userInput)
    {
        try (Scanner scanner = new Scanner(new File(FILENAME)))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");

                if (parts.length == 2)
                {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    LocalDate lastPaymentDate = LocalDate.parse(scanner.nextLine().trim());

                    // Check if the input matches id or name in the file
                    if (id.equals(userInput.trim()) || name.equals(userInput.trim()))
                    {
                        LocalDate today = LocalDate.now();
                        // Check if the membership is within the last year
                        if (ChronoUnit.DAYS.between(lastPaymentDate, today) < 365)
                        {
                            // Update or save recurring customer info
                            updateOrSaveRecurringCustomerInfo(new User(id, name, LocalDate.now()));
                            return "Current Member";
                        }
                        else {
                            return "Former Customer";
                        }
                    }
                }
            }

            return "Unauthorized"; // User not found in the file
        }

        catch (FileNotFoundException e) {
            return "File not found"; // Handle file not found exception
        }
    }

    // Method to update or save recurring customer information
    public void updateOrSaveRecurringCustomerInfo(User user)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECURRING_CUSTOMERS_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(RECURRING_CUSTOMERS_FILE + ".tmp")))
        {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(", ");
                if (parts.length >= 2) {
                    String id = parts[0];
                    String name = parts[1];

                    // Check for a match with the user's ID and name
                    if (id.equals(user.getId()) && name.equals(user.getName()))
                    {
                        found = true;
                        // Update the existing entry with new user info
                        writer.write(user.getId() + ", " + user.getName() + ", " + user.getLastCheckedIn() + "\n");
                    }
                    else
                    {
                        writer.write(line + "\n"); // Write the unchanged entry to the temp file
                    }
                }
            }

            if (!found)
            {
                // Write a new entry if the user was not found in the file
                writer.write(user.getId() + ", " + user.getName() + ", " + user.getLastCheckedIn() + "\n");
            }
        }
        catch (IOException e)
        {
            System.err.println("Error writing to file: " + e.getMessage()); // Handle IO exceptions
        }

        // Replace the original file with the modified temp file
        File originalFile = new File(RECURRING_CUSTOMERS_FILE);
        File tempFile = new File(RECURRING_CUSTOMERS_FILE + ".tmp");

        if (tempFile.renameTo(originalFile))
        {
            System.out.println("File updated successfully"); // Successful file update
        }
        else
        {
            System.err.println("Error updating file"); // Error while updating file
        }
    }
}