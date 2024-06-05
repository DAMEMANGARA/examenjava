import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import repositories.CoursRepository;

public class CoursService {

    private CoursRepository coursRepository;

    public CoursService(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    public void addCourse(LocalDate date, LocalTime startTime, LocalTime endTime, int professorId, int moduleId) throws SQLException {
        coursRepository.addCourse(date, startTime, endTime, professorId, moduleId);
    }

    public List<String> listAllCourses() throws SQLException {
        return coursRepository.listAllCourses();
    }

    // Autres méthodes de service pour les cours peuvent être ajoutées ici

}
