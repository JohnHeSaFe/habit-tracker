package com.john.habits.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HabitDAO {
    private static String url = "jdbc:sqlite:habits.db";
    private static Connection con;

    public HabitDAO() {
        try {
            con = DriverManager.getConnection(url);
            createTableHabits();
        } catch (SQLException e) {
            System.out.println("Error connecting to the BBDD");
            e.printStackTrace();
        }
    }

    private void createTableHabits() {
        String query = "CREATE TABLE IF NOT EXISTS habits ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(200) NOT NULL, "
                + "frequency TEXT NOT NULL"
                + ");";

        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error creating table habits");
            e.printStackTrace();
        }
    }

    public int insertHabit(Habit habit) {
        String query = "INSERT INTO habits (name, frequency) VALUES (?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, habit.getName());
            pstmt.setString(2, habit.getFrequency().name());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error inserting an habit");
            e.printStackTrace();
        }

        return -1;
    }

    public List<Habit> getAllHabits() {
        List<Habit> habits = new ArrayList<>();
        String query = "SELECT * FROM habits";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Habit habit = new Habit(
                        rs.getInt("id"),
                        rs.getString("name"),
                        Frequency.valueOf(rs.getString("frequency"))
                );
                habits.add(habit);
            }
        } catch (SQLException e) {
            System.out.println("Error getting habits");
            e.printStackTrace();
        }

        return habits;
    }

    public void deleteHabit(int id) {
        String query = "DELETE FROM habits WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting an habit");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection with the BD");
            e.printStackTrace();
        }
    }
}

