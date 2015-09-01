import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Course {
  private int id;
  private String description;
  private String course_number;

  public Course(String description, String course_number) {
    this.description = description;
    this.course_number = course_number;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getCourseNumber() {
    return course_number;
  }


  public static Course getFirstDBEntry() {
    return all().get(0);
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getDescription().equals(newCourse.getDescription()) &&
             this.getId() == newCourse.getId();
    }
  }


  public static List<Course> all() {
    String sql = "SELECT * FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses(description, course_number) VALUES (:description, :course_number)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .addParameter("course_number", course_number)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  // public void updateIsCompleted(boolean is_completed) {
  //  try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE courses SET is_completed = :is_completed WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("is_completed", is_completed)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }


  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM students_courses WHERE course_id = :course_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("course_id", this.getId())
          .executeUpdate();
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (:student_id, :course_id)";
      con.createQuery(sql)
        .addParameter("student_id", student.getId())
        .addParameter("course_id", this.getId())
        .executeUpdate();
    }
  }


  public List<Student> getStudents() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT students.* FROM courses JOIN students_courses ON (students_courses.course_id = courses.id) JOIN students ON (students_courses.student_id = students.id) WHERE course_id=:id";
    return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Student.class);
    }
  }


}
