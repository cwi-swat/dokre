package com.ns.richrail.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;

import com.ns.richrail.command.CommandParserFacade;
import com.ns.richrail.core.RollingStockPool;
import com.ns.richrail.core.RollingStockPoolObserver;
import com.ns.richrail.core.Train;
import com.ns.richrail.core.Wagon;

public class RichRailController implements RollingStockPoolObserver, ActionListener, WindowListener
{
	private RichRailFrame frame = new RichRailFrame(this);
	private CommandParserFacade parser;

	public RichRailController(CommandParserFacade parser)
	{
		this.parser = parser;
	}

	public void run()
	{
		// build a frame
		frame.initFrame();

		// registers at the pool
		RollingStockPool.getInstance().attach(this);
		parser.attachOutputListener(frame.getCommandOutputPanel().getPrintWriter());

		// call initial update
		update();
	}

	public void close()
	{
		parser.detachOutputListener(frame.getCommandOutputPanel().getPrintWriter());
		RollingStockPool.getInstance().detach(this);
	}

	@Override
	public void update()
	{
		Collection<Train> allTrains = RollingStockPool.getInstance().getAllTrains();
		Collection<Wagon> allDecoupledWagons = RollingStockPool.getInstance().getAllDecoupledWagons();

		// reset the graphic & text panels
		frame.getGraphicPanel().clear();
		frame.getTextPanel().clear();

		// have the graphic & text panels draw all the trains
		int track = 0;
		for (Train train : allTrains)
		{
			frame.getGraphicPanel().addTrain(track, train.getId());
			frame.getTextPanel().addTrain(train.getId());
			int slot = 0;
			for (Wagon wagon : train.getWagons())
			{
				frame.getGraphicPanel().addWagon(track, slot++, wagon.getId());
				frame.getTextPanel().addCoupledWagon(train.getId(), wagon.getId());
			}
			track++;
		}

		// have the graphic & text panels draw all uncoupled wagons
		for (Wagon wagon : allDecoupledWagons)
		{
			frame.getGraphicPanel().addWagon(track, -1, wagon.getId());
			frame.getTextPanel().addWagon(wagon.getId(), wagon.getAmountOfSeats());
			track++;
		}

		frame.getGraphicPanel().draw();
		frame.getTextPanel().draw();
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		parser.runCommand(frame.getCommandInputPanel().getCommand());
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		close();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
	}
}
