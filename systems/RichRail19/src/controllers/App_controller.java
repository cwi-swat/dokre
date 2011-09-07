package controllers;

import trainManagement.TrainManagement;

public class App_controller {
	private TrainManagement model = null;
	private Gui_controller view = null;
	
	public App_controller(){
		this.model = new TrainManagement();
		this.view = new Gui_controller(this.model);
		
		view.show();
	}

}
