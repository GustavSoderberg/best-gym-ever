package ViewModel;

import Model.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BestGymEverViewModelTest {

    private static final String TEST_FILENAME = "/Users/gustavsoderberg/IdeaProjects/Best_Gym_Ever_Inlamningsuppgift_2_Gustav_Soderberg_JAVAD23/src/Model/customers.txt";
    private static final String TEST_RECURRING_CUSTOMERS_FILE = "/Users/gustavsoderberg/IdeaProjects/Best_Gym_Ever_Inlamningsuppgift_2_Gustav_Soderberg_JAVAD23/src/Model/customers_recurring.txt";

    @Test
    public void testCheckMembership_CurrentMember() {
        BestGymEverViewModel viewModel = new BestGymEverViewModel();
        String result = viewModel.checkMembership("7703021234");
        assertEquals("Current Member", result);
    }

    @Test
    public void testCheckMembership_FormerCustomer() {
        BestGymEverViewModel viewModel = new BestGymEverViewModel();
        String result = viewModel.checkMembership("8204021234");
        assertEquals("Former Customer", result);
    }

    @Test
    public void testCheckMembership_Unauthorized() {
        BestGymEverViewModel viewModel = new BestGymEverViewModel();
        String result = viewModel.checkMembership("Unknown User");
        assertEquals("Unauthorized", result);
    }

    @Test
    public void testUpdateOrSaveRecurringCustomerInfo() throws IOException {
        BestGymEverViewModel viewModel = new BestGymEverViewModel();
        User user = new User("1234567890", "Test User", LocalDate.now().minus(200, ChronoUnit.DAYS));
        viewModel.updateOrSaveRecurringCustomerInfo(user);

        // Check if the entry was written or updated correctly
        boolean found = false;
        try (Scanner scanner = new Scanner(new File(TEST_RECURRING_CUSTOMERS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length >= 3 && parts[0].equals(user.getId()) && parts[1].equals(user.getName())) {
                    found = true;
                    break;
                }
            }
        }
        assertEquals(true, found);
    }
}
