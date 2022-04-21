package courselist.persistence;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import courselist.Course;

public class CourseDao {
    // a connection
    private Connection connection;

    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieve a course from the database given its id.
     * @param id
     * @return a Course with the requested id
     */

     public Course get(int id) {
        Course course = null;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM courses WHERE id = " + id;
            ResultSet rs = statement.executeQuery(query);
            // create a Course object from the first row of ResultSet
            if (rs.next()) {
                course = makeCourse(id, rs);
            }
        }
        catch (SQLException e) {
            // rethrow the exception with a descriptive message
            throw new RuntimeException("Problem getting course fron database", e);
        }

        return course;
     }

    private Course makeCourse(int id, ResultSet rs) throws SQLException {
        String courseNumber = rs.getString("course_number");
        String title = rs.getString("title");
        int credits = rs.getInt("credits");
        double difficulty = rs.getDouble("difficulty");
        Course course = new Course(courseNumber, title, credits);
        course.setDifficulty(difficulty);
        course.setId(id);
        return course;
    }
}