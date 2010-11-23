import org.scalatest.Spec

class ProjectsDAOTest extends Spec {
  
  describe("A projects dao") {
    it("should return a empty projects root element after first init") {
      val dao = new ProjectsDAO();
      val projects = dao.projects();
      assert(projects.toString() == "<projects></projects>");
    }
    it("should make it possibly to add a new project") {
      val dao = new ProjectsDAO()
      dao.addProject("First project")
      println(dao.projects.toString())
      assert(dao.projects.toString() == """<projects><project name="First project" id="1"></project></projects>""")
    }
  }
}
