package view.displays;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JScrollPane;

import controller.AppController;
import domain.Company;
import domain.Part;
import domain.Train;

public class BasicGraphicalDisplay extends Display
{
	private static final long serialVersionUID = 1L;

	private Company company;

	private JScrollPane scrollPane;

	private CompanyComponent companyComponent;

	public BasicGraphicalDisplay()
	{

		companyComponent = new CompanyComponent();

		this.setLayout(new GridLayout(1, 1));

		scrollPane = new JScrollPane(companyComponent);
		scrollPane.setViewportView(companyComponent);

		add(scrollPane);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		company = ((AppController) o).getCompany();
		companyComponent.setCompany(company);
	}

	@Override
	public String getDisplayName()
	{
		return "Graphical (classic)";
	}

	private class CompanyComponent extends Component
	{
		private static final long serialVersionUID = 1L;
		private Company company;

		public void setCompany(Company company)
		{
			this.company = company;

			invalidate();

			repaint();
		}

		@Override
		public Dimension getPreferredSize()
		{
			if (company != null)
			{
				int maxLength = 0;

				Train[] trains = company.getTrains();

				for (Train t : trains)
				{
					if (maxLength < t.getParts().length)
					{
						maxLength = t.getParts().length;
					}
				}

				return new Dimension(maxLength * 100, 140 * company.getTrains().length);
			}
			else
			{
				return new Dimension(0, 0);
			}
		}

		@Override
		public void paint(Graphics g)
		{
			super.paint(g);

			if (company != null)
			{
				drawCompany(g, company);
			}
		}

		private void drawCompany(Graphics g, Company c)
		{
			for (int i = 0; i < c.getTrains().length; i++)
			{
				drawTrain(g, (Train) c.getTrains()[i], 0, i * 140);
			}
		}

		private void drawTrain(Graphics g, Train t, int x, int y)
		{
			for (int i = 0; i < t.getParts().length; i++)
			{
				if (((Part) t.getParts()[i]).isLocomotive())
				{
					drawLocomotive(g, (Part) t.getParts()[i], i * 100, y);
				}
				else
				{
					drawWagon(g, (Part) t.getParts()[i], i * 100, y);
				}
			}

			revalidate();

		}

		/**
		 * Tekent een locomotiefje
		 * 
		 * @param g
		 * @param t
		 * @param x
		 * @param y
		 */
		private void drawLocomotive(Graphics g, Part t, int x, int y)
		{
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(30 + x, 80 + y, 80, 40);
			g.fillRect(80 + x, 60 + y, 30, 30);
			g.drawRoundRect(85 + x, 40 + y, 20, 20, 20, 20);
			g.drawRoundRect(85 + x, y, 40, 40, 40, 40);
			g.setColor(Color.BLACK);
			g.fillRoundRect(35 + x, 120 + y, 20, 20, 20, 20);
			g.fillRoundRect(80 + x, 120 + y, 20, 20, 20, 20);
			g.drawString(t.getName(), 40 + x, 105 + y);
		}

		/**
		 * Tekent een wagonnetje
		 * 
		 * @param g
		 * @param wagon
		 * @param x
		 * @param y
		 */
		private void drawWagon(Graphics g, Part wagon, int x, int y)
		{
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(30 + x, 80 + y, 80, 40);
			g.setColor(Color.BLACK);
			g.fillRoundRect(35 + x, 120 + y, 20, 20, 20, 20);
			g.fillRoundRect(80 + x, 120 + y, 20, 20, 20, 20);
			g.drawString(wagon.getName(), 40 + x, 105 + y);
		}
	}
}
