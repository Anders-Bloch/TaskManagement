import org.jdom.*;
import org.jdom.output.*;
import java.util.*;

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
	
	public int addProject(String name) {
		int id = doc.getRootElement().getChildren().size()+1;
		Element project = new Element("project");
		project.setAttribute("id",id+"");
		project.setAttribute("name",name);
		project.getChildren().add(newState("new",1));
		doc.getRootElement().getChildren().add(project);
		return id;
	}
	
	public void setStateOnProject(int projectId, String state) {
		List projects = doc.getRootElement().getChildren();
		for(Iterator it = projects.iterator(); it.hasNext();) {
				Element project = (Element)it.next();
				if(project.getAttributeValue("id").equals(projectId+"")) {
						int numberOfStates = project.getChildren("state").size();
						numberOfStates++;
						project.getChildren().add(newState(state,numberOfStates));
				}
		}
	}
	
	public void addIterationTo(int projectId, String name, String startDate, String endDate) {
		List projects = doc.getRootElement().getChildren();
		for(Iterator it = projects.iterator(); it.hasNext();) {
				Element project = (Element)it.next();
				if(project.getAttributeValue("id").equals(projectId+"")) {
					int numberOfIterations = project.getChildren("iteration").size();
					numberOfIterations++;
					Element iteration = new Element("iteration");
					iteration.setAttribute("id", numberOfIterations+"");
					iteration.setAttribute("name", name);
					iteration.setAttribute("startDate", startDate);
					iteration.setAttribute("endDate", endDate);
					project.getChildren().add(iteration);
				}
		}
	}
	
	private Element newState(String state, int numberOfStates) {
		  Element newState = new Element("state");
			newState.setAttribute("version", numberOfStates+"");
			return newState.addContent(state);
	}
	
	
	
	
	
}