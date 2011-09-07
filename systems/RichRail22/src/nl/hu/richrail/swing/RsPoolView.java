package nl.hu.richrail.swing;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import nl.hu.richrail.swing.gfx.TrainGFX;

public abstract class RsPoolView extends JInternalFrame
{

	public RsPoolView() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RsPoolView(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}
	public RsPoolView(String title, boolean resizable, boolean closable,
			boolean maximizable) {
		super(title, resizable, closable, maximizable);
		// TODO Auto-generated constructor stub
	}
	public RsPoolView(String title, boolean resizable, boolean closable) {
		super(title, resizable, closable);
		// TODO Auto-generated constructor stub
	}
	public RsPoolView(String title, boolean resizable) {
		super(title, resizable);
		// TODO Auto-generated constructor stub
	}
	public RsPoolView(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	public abstract void add(JTrainComponent train);
	public abstract void remove(String id);

}
