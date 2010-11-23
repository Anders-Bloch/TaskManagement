import org.scalatest.Spec
import scala.xml._

class TaskXMLDAOTest extends Spec {
  
  describe("A task xml dao") {
    it("should return a empty projects root element after first init") {
      val resultString = """<?xml version="1.0" encoding="UTF-8"?>
<projects />
""";
      val dao = new TaskXMLDAO();
      val projects = dao.getProjects();
      assert(projects === resultString)
    }
    
    it("you can add new project") {
      val resultString = <project id="1" name="test" ></project>
      val dao = new TaskXMLDAO();
      dao.addProject(1,"test");
      val projects = XML.loadString(dao.getProjects())
      assert(projects \"project")
    }
    it("new projects has a init state of new") {
      val state = "new";  
      val resultString = <project id="1" name="test" ><state version="1">{state}</state></project>
      val dao = new TaskXMLDAO();
      dao.addProject(1,"test");
      val projects = XML.loadString(dao.getProjects())
      assert((projects \"project") === resultString)
    }
  }
}
