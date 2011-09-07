package Facade;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;

import Domain.Train;
import Domain.Wagon;


public class TrainTextViewer extends JPanel implements TrainObserver {
	
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();
	private JTextArea textArea;
	public TrainTextViewer() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		update();
	}

	@Override
	public void update() {
		String s = "Wagons\n";
		for(Wagon w : tfs.getWagons()) {
			s += " (" + w.getNaam()+ " : " + w.getWagonType().getSeats() + ")";
		}
		s+= "\ntrains\n";
		for(Train t : tfs.getTrains()) {
			s += "(" + t.getNaam() + ")";
			for (Wagon g : t.getWagons()) {
				s+= "-(" + g.getNaam() + ")";
			}
			s+="\n";
		}
		textArea.setText(s);
	}
}


