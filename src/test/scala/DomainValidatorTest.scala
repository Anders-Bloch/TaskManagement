import org.scalatest.Spec
import scala.xml._
//import org.jdom._

//import java.io.File
//import java.io.IOException
//import org.jdom.input.SAXBuilder
//import org.jdom.output._

class DomainValidatorTest extends Spec {
	
  describe("A domain validator") {
    it("- can verify that start and end dates is in corrent order") {
      val validator = DomainValidator
	  assert(validator.datesInOrder("01.01.2010","01.02.2010"))
	  assert(!validator.datesInOrder("01.02.2010","01.01.2010"))
    }
	
	it("- it can check for overlapping iteration dates") {
	  val doc = XML.loadFile("./src/test/scala/projects.xml")
	  val validator = DomainValidator
	  val project = for(n <- doc \ "project"; if (n \ "@id" text) == "1") yield n
	  //Valid
	  assert(!validator.isIterationDatesInConflict(project, "01.03.2010","01.04.2010"))
	  assert(!validator.isIterationDatesInConflict(project, "01.03.2009","01.04.2009"))
	  //Invalid
	  assert(validator.isIterationDatesInConflict(project, "01.01.2010","01.02.2010"))
	  assert(validator.isIterationDatesInConflict(project, "15.01.2010","15.02.2010"))
	  assert(validator.isIterationDatesInConflict(project, "15.12.2009","15.01.2010"))
	  assert(validator.isIterationDatesInConflict(project, "15.12.2009","15.03.2010"))
	}
	
  }
}