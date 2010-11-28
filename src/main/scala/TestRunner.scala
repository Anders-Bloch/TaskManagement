import org.scalatest._

object TestRunner {
	def main(args : Array[String]) = {
	  (new DomainValidatorTest).execute()
	  println("hello")
	}
}