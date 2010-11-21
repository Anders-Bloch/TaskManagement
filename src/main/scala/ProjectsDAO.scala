import org.scala.xml._

class ProjectsDAO {
  
  projects + "<projects></projects>"
  
  private val projects = new Document()
  
  def projects() : Node = {
    "<projects></projects>"
  }
  
  def addProject(name : String) = {
     
  }
}
