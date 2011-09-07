package richrail.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Train implements Seatable {
	private final String name;
	private final List<Seatable> seatables = new ArrayList<Seatable>();

	public Train(String name) {
		this.name = name;
	}

	public void addSeatable(Seatable seatable) {
		seatables.add(seatable);
	}

	public boolean removeSeatable(Seatable seatable) {
		return seatables.remove(seatable);
	}

	public List<Seatable> getSeatables() {
		return Collections.unmodifiableList(seatables);
	}

	@Override
	public int getNumberOfSeats() {
		int sum = 0;
		for(Seatable seatable : seatables) {
			sum += seatable.getNumberOfSeats();
		}
		return sum;
	}

	@Override
	public String getName() {
		return name;
	}
}
