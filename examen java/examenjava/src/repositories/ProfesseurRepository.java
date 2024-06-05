package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/votre_base_de_donnees";
    private static final String DB_USER = "utilisateur";
    private static final String DB_PASSWORD = "mot_de_passe";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void addModule(String moduleName) throws SQLException {
        String query = "INSERT INTO Modules (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, moduleName);
            statement.executeUpdate();
        }
    }

    public List<String> listModules() throws SQLException {
        List<String> modules = new ArrayList<>();
        String query = "SELECT name FROM Modules";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                modules.add(name);
            }
        }
        return modules;
    }

    public void addProfessor(String firstName, String lastName, String phoneNumber) throws SQLException {
        String query = "INSERT INTO Professors (first_name, last_name, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.executeUpdate();
        }
    }

    public List<String> listAllCourses() throws SQLException {
        List<String> courses = new ArrayList<>();
        String query = "SELECT c.id, c.date, c.start_time, c.end_time, m.name AS module_name FROM Courses c " +
                "JOIN Modules m ON c.module_id = m.id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();
                String moduleName = resultSet.getString("module_name");
                String courseInfo = String.format("%d - %s %s-%s (%s)", id, date, startTime, endTime, moduleName);
                courses.add(courseInfo);
            }
        }
        return courses;
    }

    // Autres méthodes CRUD pour les cours peuvent être ajoutées ici

    // Méthode pour fermer la connexion à la base de données
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        try {
            DatabaseManager manager = new DatabaseManager();

            // Exemples d'utilisation
            manager.addModule("Java");
            manager.addProfessor("John", "Doe", "1234567890");

            List<String> modules = manager.listModules();
            System.out.println("Modules disponibles : " + modules);

            // Autres opérations ici

            manager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
