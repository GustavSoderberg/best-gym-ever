package View;

import ViewModel.BestGymEverViewModel;
import java.util.Scanner;

public class BestGymEver
{

    private final BestGymEverViewModel viewModel;

    // Constructor initializes the BestGymEverViewModel
    public BestGymEver()
    {
        this.viewModel = new BestGymEverViewModel();
    }

    // Method to start the interaction with the user
    public void start()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user input (id or name): ");
        String userInput = scanner.nextLine(); // Get user input

        String result = viewModel.checkMembership(userInput); // Check membership based on user input
        System.out.println("Membership Status: " + result); // Display membership status
    }

    // Main method creates an instance of BestGymEver and starts the program
    public static void main(String[] args)
    {
        BestGymEver view = new BestGymEver(); // Create an instance of the BestGymEver view
        view.start(); // Start the program by calling the start method
    }
}
