package kg.cloud.tuscon.ui;

import com.vaadin.ui.VerticalSplitPanel;

public class ListView extends VerticalSplitPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListView(PersonList personList, PersonForm personForm){
		setFirstComponent(personList);
        setSecondComponent(personForm);
        setSplitPosition(40);
	}

}
