import org.jdom.*;
import org.jdom.xpath.*;
import org.jdom.output.*;
import java.util.*;
import java.text.*;
import java.lang.*;

class TaskXMLDAO {
	private Document doc = new Document();
	private DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	
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
	
	public int addIterationTo(int projectId, String name, String startDate, String endDate) {
		List projects = doc.getRootElement().getChildren();
		int iterationId = 0;
		for(Iterator it = projects.iterator(); it.hasNext();) {
			Element project = (Element)it.next();
			if(project.getAttributeValue("id").equals(projectId+"")) {
				int numberOfIterations = project.getChildren("iteration").size();
				iterationId = numberOfIterations + 1;
				Element iteration = new Element("iteration");
				iteration.setAttribute("id", iterationId+"");
				iteration.setAttribute("name", name);
				iteration.setAttribute("startDate", startDate);
				iteration.setAttribute("endDate", endDate);
				project.getChildren().add(iteration);
			}
		}
		return iterationId;
	}
	
	public int addTaskToIteration(int projectId, int iterationId, String name) {
		List projects = doc.getRootElement().getChildren();
		int taskId = 0;
		for(Iterator it = projects.iterator(); it.hasNext();) {
			Element project = (Element)it.next();
			if(project.getAttributeValue("id").equals(projectId+"")) {
				for(Iterator it2 = project.getChildren("iteration").iterator(); it2.hasNext();) {
					Element iteration = (Element)it2.next();
					if(iteration.getAttributeValue("id").equals(iterationId+"")) {
						int numberOftasks = iteration.getChildren("task").size();
						taskId = numberOftasks + 1;
						Element task = new Element("task");
						task.setAttribute("id", taskId+"");
						task.setAttribute("name", name);
						Element newState = new Element("state");
						newState.setAttribute("timestamp", System.currentTimeMillis()+"");
						newState.addContent("new");
						task.getChildren().add(newState);
						iteration.getChildren().add(task);
					}
				}
			}
		}
		return taskId;
	}
	
	public void setStateOnTask(int projectId, int iterationId, int taskId , String user, String state) {
		Element e = new Element("state");
		e.addContent(state);
		addElementToTask(projectId, iterationId, taskId, user, e);
	}
	
	public void addDescriptionToTask(int projectId, int iterationId, int taskId, String user, String description) {
		Element e = new Element("description");
		e.addContent(description);
		addElementToTask(projectId, iterationId, taskId, user, e);
	}
	public void addCommentToTask(int projectId, int iterationId, int taskId, String user, String comment) {
		Element e = new Element("comment");
		e.addContent(comment);
		addElementToTask(projectId, iterationId, taskId, user, e);
	}
	
	private void addElementToTask(int projectId, int iterationId, int taskId, String user, Element e) {
		List projects = doc.getRootElement().getChildren();
		for(Iterator it = projects.iterator(); it.hasNext();) {
			Element project = (Element)it.next();
			if(project.getAttributeValue("id").equals(projectId+"")) {
				for(Iterator it2 = project.getChildren("iteration").iterator(); it2.hasNext();) {
					Element iteration = (Element)it2.next();
					if(iteration.getAttributeValue("id").equals(iterationId+"")) {
						for(Iterator it3 = iteration.getChildren("task").iterator(); it3.hasNext();) {
							Element task = (Element)it3.next();
							if(task.getAttributeValue("id").equals(taskId+"")) {
								e.setAttribute("id", task.getChildren(e.getName()).size() + "");
								e.setAttribute("timestamp", System.currentTimeMillis()+"");
								e.setAttribute("user", user);
								task.getChildren().add(e);
							}
						}
					}
				}
			}
		}
	}

	private Element newState(String state, int numberOfStates) {
		Element newState = new Element("state");
		newState.setAttribute("version", numberOfStates+"");
		return newState.addContent(state);
	}
}