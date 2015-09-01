import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    /* Index */
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> Categories*/
    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Course> courses = Course.all();
      model.put("courses", courses);
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Course list/form --> POST a new category */
    post("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String description = request.queryParams("description");
      String course_number = request.queryParams("course_number");
      Course newCourse = new Course(description,course_number);
      newCourse.save();
      response.redirect("/courses");
      return null;
    });

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Student> students = Student.all();
      model.put("students", students);
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Course page --> POST a task to this category */
    post("/students", (request, response) -> {
      String student_name = request.queryParams("name");
      String dt_enroll = request.queryParams("dt_enroll");
      Student newStudent = new Student(student_name, dt_enroll);
      newStudent.save();
      response.redirect("/students");
      return null;
    });

    get("/students/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params(":id"));
      Student student = Student.find(id);
      model.put("student", student);
      model.put("allCourses", Course.all());
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students/:id", (request, response) -> {
      int studentId = Integer.parseInt(request.queryParams("student_id"));
      int courseId = Integer.parseInt(request.queryParams("course_name"));
      Student student = Student.find(studentId);
      Course course = Course.find(courseId);
      student.addCourse(course);
      response.redirect("/students/" + studentId);
      return null;
      });

    // /* Course list/form --> See a particular category */
    // get("/categories/:id", (request,response) ->{
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params(":id"));
    //   Course category = Course.find(id);
    //   model.put("category", category);
    //   model.put("allTasks", Task.all());
    //   model.put("template", "templates/category.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // /* Task list/form --> POST a new task */
    // post("/students", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String description = request.queryParams("description");
    //   String due_date = request.queryParams("due_date");
    //   boolean is_completed =  Boolean.parseBoolean(request.queryParams("is_completed"));
    //   Task newTask = new Task(description, due_date, is_completed);
    //   newTask.save();
    //   response.redirect("/students");
    //   return null;
    // });
    //
    // /* Index --> Tasks*/
    // get("/students", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   List<Task> students = Task.all();
    //   model.put("students", students);
    //   model.put("template", "templates/students.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // /* Course page --> POST a task to this category */
    // post("/add_students", (request, response) -> {
    //   int taskId = Integer.parseInt(request.queryParams("task_id"));
    //   int categoryId = Integer.parseInt(request.queryParams("category_id"));
    //   Course category = Course.find(categoryId);
    //   Task task = Task.find(taskId);
    //   category.addTask(task);
    //   response.redirect("/categories/" + categoryId);
    //   return null;
    // });
    //
    // get("/students/:id", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params(":id"));
    //   Task task = Task.find(id);
    //   model.put("task", task);
    //   model.put("allCategories", Course.all());
    //   model.put("template", "templates/task.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    //
    //
    // /* Task page --> POST a category to this task */
    // post("/add_categories", (request, response) -> {
    //   int taskId = Integer.parseInt(request.queryParams("task_id"));
    //   int categoryId = Integer.parseInt(request.queryParams("category_id"));
    //   Course category = Course.find(categoryId);
    //   Task task = Task.find(taskId);
    //   task.addCourse(category);
    //   response.redirect("/students/" + taskId);
    //   return null;
    // });
    //
    // get("/students/:id/edit", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Task task = Task.find(Integer.parseInt(request.params("id")));
    //   //String new_task_name = request.queryParams("new_task_name");
    //   //task.update("new_task_name");
    //   model.put("task", task);
    //   model.put("template", "templates/students-edit-form.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/students/:id/edit", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Task task = Task.find(Integer.parseInt(request.params(":id")));
    //   String new_task_name = request.queryParams("new_task_name");
    //   boolean is_completed =  Boolean.parseBoolean(request.queryParams("is_completed"));
    //   task.update(new_task_name);
    //   task.updateIsCompleted(is_completed);
    //
    //   model.put("students",Task.all());
    //   model.put("task", task);
    //   model.put("template", "templates/students.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/students/:id/delete", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Task task = Task.find(Integer.parseInt(request.params("id")));
    //   task.delete();
    //   response.redirect("/students");
    //   return null;
    //
    // });

  }
}
