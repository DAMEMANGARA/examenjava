import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CoursRepository {

    private Connection connection;

    public CoursRepository(Connection connection) {
        this.connection = connection;
    }

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

}
