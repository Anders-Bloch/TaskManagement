import org.scalatest.Spec
import scala.xml._

class TaskXMLDAOTest extends Spec {
  val user = "Anders"
  describe("A task xml dao") {
    it("should return a empty projects root element after first init") {
      val resultString = XML.loadString("<?xml version='1.0' encoding='UTF-8'?><projects />");
      val dao = new TaskXMLDAO();
      val projects = XML.loadString(dao.getProjects());
      assert(projects === resultString)
    }
    
    it("can add new project") {
      val resultString = <project id="1" name="test" ></project>
      val dao = new TaskXMLDAO();
      val id = dao.addProject("test");
      val projects = XML.loadString(dao.getProjects())
      assert((projects \"project" \ "@name").toString === "test")
      assert(id == 1)
    }
    it("- new projects has a init state of new") {
      val state = "new";  
      val dao = new TaskXMLDAO();
      dao.addProject("test");
      val projects = XML.loadString(dao.getProjects())
      assert((projects \ "project" \ "state").text === state)
      assert((projects \ "project" \ "state" \ "@version").toString() === "1")
    }
    it("can update the state of a project") {
      val state = "new"
      val dao = new TaskXMLDAO() 
      dao.addProject("test")
      dao.setStateOnProject(1,"started")
      val projects = XML.loadString(dao.getProjects())
      assert((projects \ "project" \ "state").size === 2)
      (projects \ "project" \ "state").foreach( s => { 
        assert(s.text == "new" || s.text == "started") 
      });
    }
    it("can add iteration to project") {
      val dao = new TaskXMLDAO()
      dao.addProject("test")
      dao.addIterationTo(1,"First iteration","01.01.2010","01.02.2010")
	  	val projects = XML.loadString(dao.getProjects())
      assert((projects \ "project" \ "iteration").size === 1)
    }
	it("can add task to iteration in specific project") {
	  val dao = new TaskXMLDAO()
	  val projectId = dao.addProject("test")
      val iterationId = dao.addIterationTo(projectId,"First iteration","01.01.2010","01.02.2010")
	  //var projects = XML.loadString(dao.getProjects())
	  //assert((projects \ "project" \ "iteration" \ "task").size === 0)
	  dao.addTaskToIteration(projectId, iterationId, "First test task")
	  val projects = XML.loadString(dao.getProjects())
	  assert((projects \ "project" \ "iteration" \ "task").size === 1)
	}
	it("- new tasks has state new") {
	  val dao = new TaskXMLDAO()
	  val projectId = dao.addProject("test")
      val iterationId = dao.addIterationTo(projectId,"First iteration","01.01.2010","01.02.2010")
	  dao.addTaskToIteration(projectId, iterationId, "First test task")
	  val projects = XML.loadString(dao.getProjects())
	  assert((projects \ "project" \ "iteration" \ "task" \ "state").text === "new")
	}
	it("can change state on task") {
	  val dao = new TaskXMLDAO()
	  val projectId = dao.addProject("test")
      val iterationId = dao.addIterationTo(projectId,"First iteration","01.01.2010","01.02.2010")
	  val taskId = dao.addTaskToIteration(projectId, iterationId, "First test task")
	  dao.setStateOnTask(projectId, iterationId, taskId, user, "waiting")
	  val projects = XML.loadString(dao.getProjects())
	  assert((projects \ "project" \ "iteration" \ "task" \ "state").size === 2)
	}
	it("can add description to task") {
	  val d = "Description test"
	  val dao = new TaskXMLDAO()
	  val projectId = dao.addProject("test")
      val iterationId = dao.addIterationTo(projectId,"First iteration","01.01.2010","01.02.2010")
	  val taskId = dao.addTaskToIteration(projectId, iterationId, "First test task")
	  dao.addDescriptionToTask(projectId, iterationId, taskId, user, d)
	  val projects = XML.loadString(dao.getProjects())
	  assert((projects \ "project" \ "iteration" \ "task" \ "description").text === d)
	}
	it("can add comment to task") {
	  val d = "Comment test"
	  val dao = new TaskXMLDAO()
	  val projectId = dao.addProject("test")
      val iterationId = dao.addIterationTo(projectId,"First iteration","01.01.2010","01.02.2010")
	  val taskId = dao.addTaskToIteration(projectId, iterationId, "First test task")
	  dao.addCommentToTask(projectId, iterationId, taskId, user, d)
	  val projects = XML.loadString(dao.getProjects())
	  assert((projects \ "project" \ "iteration" \ "task" \ "comment").text === d)
	  assert((projects \ "project" \ "iteration" \ "task" \ "comment").size === 1)
	  dao.addCommentToTask(projectId, iterationId, taskId, user, d)
	  val projects2 = XML.loadString(dao.getProjects())
	  assert((projects2 \ "project" \ "iteration" \ "task" \ "comment").size === 2)
	}
  }
}
