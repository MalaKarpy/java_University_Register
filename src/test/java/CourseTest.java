import java.util.Arrays;
import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Course firstCourse = new Course("math","MT100");
    Course secondCourse = new Course("math","MT100");
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Course myCourse = new Course("math","MT100");
    myCourse.save();
    assertTrue(Course.all().get(0).equals(myCourse));
  }

  @Test
  public void find_findCourseInDatabase_true() {
    Course myCourse = new Course("math","MT100");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Course myCourse = new Course("math","MT100");
    myCourse.save();

    //instantiating a new object of type Student
    Student myStudent = new Student("chitra","2014-09-08");
    //dereferencing the object
    myStudent.save();

    myCourse.addStudent(myStudent);
    //calls the first task in the new list of tasks from this category
    Student savedStudent = myCourse.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnsAllStudents_ArrayList() {
    Course myCourse = new Course("math","MT100");
    myCourse.save();

    Student myStudent = new Student("chitra","2014-09-08");
    myStudent.save();

    myCourse.addStudent(myStudent);
    List savedStudents = myCourse.getStudents(); //NPE here, had to change taskId to task_id
    assertEquals(savedStudents.size(), 1);
  }

  @Test
  public void delete_deletesAllStudentsAndListsAssoicationes() {
    Course myCourse = new Course("math","MT100");
    myCourse.save();

    Student myStudent = new Student("chitra","2014-09-08");
    myStudent.save();

    myCourse.addStudent(myStudent);
    myCourse.delete();
    assertEquals(myStudent.getCourses().size(), 0);
  }

}
