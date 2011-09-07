package richrail.view;

import richrail.control.DSLProcessor;
import richrail.control.TrainDataProvider;

import javax.swing.*;
import java.awt.*;

public class TrainView extends JFrame  {

	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private static final int TEXT_VIEW_WIDTH = 400;
	private static final int TEXT_VIEW_HEIGHT = 300;
	private static final int PANEL_VIEW_HEIGHT = 500;

	private final TrainDataProvider provider;

	public TrainView(TrainDataProvider provider) {
		super("RichRail 1.0");

		this.provider = provider;

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

		createLayout();
		setVisible(true);
	}

	private void createLayout() {
		createTop();
		createCenter();
		createBottom();
	}

	private void createBottom() {
		add(new CommandPanel(new DSLProcessor(provider)), BorderLayout.SOUTH);
	}

	private void createCenter() {
		JPanel center = new JPanel(new BorderLayout());

		TextTrainView trainView = new TextTrainView(provider);
		trainView.setPreferredSize(new Dimension(TEXT_VIEW_WIDTH, TEXT_VIEW_HEIGHT));
		trainView.setBackground(Color.LIGHT_GRAY);
		center.add(trainView, BorderLayout.WEST);

		ConsoleOutput consoleOutput = new ConsoleOutput(provider.getLogger());
		center.add(new JScrollPane(consoleOutput), BorderLayout.CENTER);

		provider.addObserver(trainView);

		add(center, BorderLayout.CENTER);
	}

	private void createTop() {
		TrainPanel trainPanel = new TrainPanel(provider);
		trainPanel.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(trainPanel);
		scrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, PANEL_VIEW_HEIGHT));
		scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_VIEW_HEIGHT));

		add(scrollPane, BorderLayout.NORTH);

		provider.addObserver(trainPanel);
	}

}
