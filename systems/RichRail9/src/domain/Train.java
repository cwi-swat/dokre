package domain;

import java.util.ArrayList;

import utils.StringUtils;

public class Train {
	private String name;
	private ArrayList<Part> parts;

	public Train(String name) {
		this.name = name;
		this.parts = new ArrayList<Part>();

		// De locomotief, zit standaard op plek 1 in de trein
		this.parts.add(new Part(name, 0, 1));
	}

	public String getName() {
		return name;
	}

	public Object[] getParts() {
		return parts.toArray();
	}

	/**
	 * Add a part to the train. If it belonged to another train, it will be
	 * detached from that train first.
	 * 
	 * @param Part
	 *            p the part to add to this train.
	 * @throws Exception
	 */
	public void addPart(Part p) throws Exception {
		if (!p.isLocomotive()) {
			if (parts.contains(p)) {
				throw new Exception("part " + p.getName() + " is already a member of the train " + this.getName());
			}

			p.detach();
			parts.add(p);
			p.setTrain(this);
		} else {
			throw new Exception("can't add a locomotive");
		}
	}

	public boolean removePart(Part p) {
		return parts.remove(p);
	}

	public int getNumberOfSeats() {
		int seats = 0;

		for (Part p : parts) {
			seats += p.getNumberOfSeats();
		}

		return seats;
	}

	public String toString() {
		return StringUtils.join(parts, "-");
	}

}