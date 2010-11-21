import java.io._

object TaskManager {
  def main(agrs : Array[String]) = {
    val doc = <projects><project>first</project></projects>
    println("Type command:")
    val input = readLine
    println("Your command: " + input)
    println(readToFile())
  }
  
  def readToFile() : String = {
    val file = new File("projects.xml")
    if(!file.exists()) file.createNewFile()
    val bufferedReader = new BufferedReader(new FileReader(file))
    val readFile = new StringBuffer()
    while(bufferedReader.ready())
      readFile.append(bufferedReader.readLine())
    bufferedReader.close()
    readFile.toString()
  }
}
