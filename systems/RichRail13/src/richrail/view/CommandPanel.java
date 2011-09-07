package richrail.view;

import richrail.control.DSLProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CommandPanel extends JPanel {

	CommandPanel(final DSLProcessor processor) {
		setLayout(new BorderLayout());
		final JTextField text = new JTextField("");

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processor.processDSL(text.getText());
			}
		};

		text.addActionListener(listener);
		JButton button = new JButton("Execute");
		button.addActionListener(listener);

		add(new JLabel("Command:"), BorderLayout.WEST);
		add(text, BorderLayout.CENTER);
		add(button, BorderLayout.EAST);


	}
}
