package repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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

    // Méthode pour ajouter un module
    public void addModule(String moduleName) throws SQLException {
        String query = "INSERT INTO Modules (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, moduleName);
            statement.executeUpdate();
        }
    }

    // Méthode pour lister tous les modules
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

    // Méthode pour ajouter un professeur
    public void addProfessor(String firstName, String lastName, String phoneNumber) throws SQLException {
        String query = "INSERT INTO Professors (first_name, last_name, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.executeUpdate();
        }
    }

    // Méthode pour ajouter un cours
    public void addCourse(LocalDate date, LocalTime startTime, LocalTime endTime, int professorId, int moduleId) throws SQLException {
        String query = "INSERT INTO Courses (date, start_time, end_time, professor_id, module_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(date));
            statement.setTime(2, Time.valueOf(startTime));
            statement.setTime(3, Time.valueOf(endTime));
            statement.setInt(4, professorId);
            statement.setInt(5, moduleId);
            statement.executeUpdate();
        }
    }

    // Méthode pour lister
