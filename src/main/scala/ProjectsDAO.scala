import scala.xml._

class ProjectsDAO {
  
  private var allProjects : Elem = <projects></projects>
 
  def projects() : Node = {
    allProjects
  }
  
  def addProject(name : String) = {
    println(allProjects ++ <project name="{name}" id="1" ></project>)
    allProjects +: <project name={ name } id="1" ></project>
  }
  
}
