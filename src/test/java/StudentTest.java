import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Student firstStudent = new Student("chitra","2012-04-09");
    Student secondStudent = new Student("chitra","2012-04-09");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Student myStudent = new Student("chitra","2012-04-09");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(savedStudent.equals(myStudent));
  }

  @Test
  public void save_assignsIdToObject() {
    Student myStudent = new Student("chitra","2012-04-09");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertEquals(myStudent.getId(), savedStudent.getId());
  }



  @Test
  public void addCourse_addsCourseToStudent() {
    Course myCourse = new Course("Biology", "BIO12");
    myCourse.save();

    Student myStudent = new Student("chitra","2012-04-09");
    myStudent.save();

    myStudent.addCourse(myCourse);
    Course savedCourse = myStudent.getCourses().get(0);
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void getCourses_returnsAllCourses_ArrayList() {
    Course myCourse = new Course("Biology","BIO12");
    myCourse.save();

    Student myStudent = new Student("chitra","2012-04-09");
    myStudent.save();

    myStudent.addCourse(myCourse);
    List<Course> savedCourses = myStudent.getCourses();
    assertEquals(savedCourses.size(), 1);
  }

}
