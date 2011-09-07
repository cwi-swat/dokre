package view.displays;

import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.AppController;

import domain.Company;

public class TextDisplay extends Display
{
	private static final long serialVersionUID = 1L;

	private JTextArea tfOutput;

	public TextDisplay()
	{
		super();

		this.setLayout(new GridLayout(1, 1));

		tfOutput = new JTextArea();
		tfOutput.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(tfOutput);
		this.add(scrollPane);
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		Company company = ((AppController) arg0).getCompany();

		tfOutput.setText(company.toString());

		this.repaint();
	}

	@Override
	public String getDisplayName()
	{
		return "Text";
	}

}
