package Model;

import java.time.LocalDate;

public class User
{
    private final String id;
    private final String name;
    private final LocalDate lastCheckedIn;

    // Constructor to initialize User object with ID, name, and lastCheckedIn date
    public User(String id, String name, LocalDate lastCheckedIn)
    {
        this.id = id;
        this.name = name;
        this.lastCheckedIn = lastCheckedIn;
    }

    // Getter for retrieving User ID
    public String getId()
    {
        return id;
    }

    // Getter for retrieving User name
    public String getName()
    {
        return name;
    }

    // Getter for retrieving the date when the user last checked in
    public LocalDate getLastCheckedIn()
    {
        return lastCheckedIn;
    }
}