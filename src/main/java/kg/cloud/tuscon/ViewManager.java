package kg.cloud.tuscon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Stack;

import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;

public class ViewManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<String,Layout> views=new HashMap<String,Layout>();
	Stack<Layout> screenStack=new Stack<Layout>();
	Panel window;
	
	public ViewManager(Panel window){
		this.window=window;
	}
	
	public void init(){}
	
	public void switchScreen(String viewName,Layout newView){
		Layout view;
		if(newView!=null){
			view=newView;
			views.put(viewName,newView);
		}else{
			view=views.get("viewName");
		}
		window.setContent(view);
	}

	public void pushScreen(String viewName,Layout newView){
		screenStack.push((Layout)window.getContent());
		switchScreen(viewName,newView);
	}
	
	public void popScreen(){
		window.setContent(screenStack.pop());
	}
	
	public Panel getWindow(){
		return window;
	}
}