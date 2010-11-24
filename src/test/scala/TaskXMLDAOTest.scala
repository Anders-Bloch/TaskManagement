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
	it("cannot add iteration with overlapping or worng dates") {
	  val dao = new TaskXMLDAO()
      dao.addProject("test")
      dao.addIterationTo(1,"First iteration","01.01.2010","01.02.2010")
	  try {
		dao.addIterationTo(1,"First iteration","15.01.2010","15.02.2010")
		fail("fail - you have added two overlapping iterations")
	  } catch {
		case ex: IllegalArgumentException => // :-) 
	  }
	  try {
		dao.addIterationTo(1,"First iteration","15.12.2009","15.01.2010")
		fail("fail - you have added two overlapping iterations 15.12.2009, 15.01.2010")
	  } catch {
		case ex: IllegalArgumentException => // :-) 
	  }
	  try {
		dao.addIterationTo(1,"First iteration","15.12.2009","15.02.2010")
		fail("fail - you have added two overlapping iterations 15.12.2009,15.02.2010")
	  } catch {
		case ex: IllegalArgumentException => // :-) 
	  }
	  try {
		dao.addIterationTo(1,"First iteration","15.01.2010","17.01.2010")
		fail("fail - you have added two overlapping iterations 15.01.2010,17.01.2010")
	  } catch {
		case ex: IllegalArgumentException => // :-) 
	  }
	  try {
		dao.addIterationTo(1,"First iteration","30.05.2010","30.04.2010")
		fail("fail - you have added two overlapping iterations 30.05.2010,30.04.2010")
	  } catch {
		case ex: IllegalArgumentException => // :-)
	  }
	}
	it("can add task to iteration in specific project") (pending)
  }
}
