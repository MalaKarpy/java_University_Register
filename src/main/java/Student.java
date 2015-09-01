import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Student {
  private int id;
  private String name;
  private String dt_enroll;

  public Student(String name, dt_enroll) {
    this.name = name;
    this.dt_enroll = dt_enroll;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDateEnroll() {
    return dt_enroll;
  }

  public static List<Student> all() {
    String sql = "SELECT * FROM students";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  @Override
  public boolean equals(Object otherStudent){
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id";
      Student Student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return Student;
    }
  }

  public void addCourse(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id)" +
                   " VALUES (:student_id, :course_id)";
      con.createQuery(sql)
        .addParameter("student_id", this.getId())
        .addParameter("course_id", course.getId())
        .executeUpdate();
    }
  }

  // public ArrayList<Course> getCourses() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT course_id FROM students_courses WHERE Student_id = :Student_id";
  //     //all the course_ids that match the Student_id are returned as a List<Integer>
  //     List<Integer> courseIds = con.createQuery(sql)
  //       .addParameter("Student_id", this.getId())
  //       .executeAndFetch(Integer.class);
  //
  //     //create an empty array list to hold the new Course objects
  //     ArrayList<Course> courses = new ArrayList<Course>();
  //
  //     //loop through course_ids
  //     for (Integer courseId : courseIds) {
  //       String courseQuery = "SELECT * FROM courses WHERE id = :course_id";
  //       //for each id, create a new query that fetches the course and adds to [courses]
  //       Course course = con.createQuery(courseQuery)
  //         .addParameter("course_id", courseId)
  //         .executeAndFetchFirst(Course.class);
  //       courses.add(course);
  //     }
  //     return courses;
  //   }
  // }

  public List<Course> getCourses() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT courses.* FROM students JOIN students_courses ON (students_courses.student_id = students.id) JOIN courses ON (students_courses.course_id = courses.id) WHERE student_id=:id ORDER BY dt_enroll ASC";
    return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Course.class);
    }
  }


  public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String deleteQuery = "DELETE FROM students WHERE id = :id;";
          con.createQuery(deleteQuery)
            .addParameter("id", id)
            .executeUpdate();

        String joinDeleteQuery = "DELETE FROM students_courses WHERE student_id = :student_id";
          con.createQuery(joinDeleteQuery)
            .addParameter("student_id", this.getId())
            .executeUpdate();
      }
    }

}
