import org.jdom.*;
import org.jdom.output.*;

class TaskXMLDAO {
	private Document doc = new Document();
	
	public TaskXMLDAO() {
		Element root = new Element("projects");
		doc.setRootElement(root);
	}
	
	public String getProjects() {
		XMLOutputter outputter = new XMLOutputter();
		return outputter.outputString(doc);
	}
	
	public void addProject(int id, String name) {
		Element project = new Element("project");
		project.setAttribute("id",id+"");
		project.setAttribute("name",name);
		doc.getRootElement().getChildren().add(project);
	}
	
	
	
	
}