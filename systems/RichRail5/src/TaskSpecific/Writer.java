package TaskSpecific;

import java.util.ArrayList;

public abstract class Writer {
	 protected String name;
	    protected String description;
//	    protected Controller controller = Controller.getInstance();
	    
	    public Writer()
	    {
	    	this.name = "Writer";
	    	this.name = "No description available";
	    }
	    
	    public Writer(String name)
	    {
	    	this();
	    	this.name = name;
	    }
	    
	    public Writer(String name, String description)
	    {
	    	this(name);
	    	this.description = description;
	    }
	    
	    public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public abstract boolean write();

}
