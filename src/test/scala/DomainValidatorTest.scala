import org.scalatest.Spec
import scala.xml._
import org.jdom._

class DomainValidatorTest extends Spec {

  val doc = new org.jdom.Document()
	
  describe("A domain validator") {
    it("- can verify that start and end dates is in corrent order") {
      val root = new Element("projects")
	  val project = new Element("project")
	  val iteration : Element = new Element("iteration")
	  iteration.setAttribute("id","1")
	  iteration.setAttribute("startDate","01.01.2010")
	  iteration.setAttribute("endDate","01.02.2010")
	  //project.getChildren().add(iteration)
	  //root.getChildren().add(project)
	  doc.setRootElement(root)
	  
	  val validator = DomainValidator
	  assert(validator.datesInOrder("01.01.2010","01.02.2010"))
	  assert(!validator.datesInOrder("01.02.2010","01.01.2010"))
    }
  }
}