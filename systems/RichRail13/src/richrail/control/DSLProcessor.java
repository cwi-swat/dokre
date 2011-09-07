package richrail.control;

import richrail.commands.*;
import richrail.control.Logger;
import richrail.control.TrainDataProvider;

import java.util.ArrayList;
import java.util.List;

public class DSLProcessor {

	private final List<Command> commands = new ArrayList<Command>();
	private final Logger logger;

	public DSLProcessor(TrainDataProvider controller) {
		this.logger = controller.getLogger();

		commands.add(new RemoveCommand(controller));
		commands.add(new AddCommand(controller));
		commands.add(new GetCommand(controller));
		commands.add(new DeleteCommand(controller));
		commands.add(new NewTrainCommand(controller));
		commands.add(new NewWagonCommand(controller));
	}

	public boolean processDSL(String dsl) {
		boolean canProcess = false;
		for (Command command : commands) {
			canProcess = command.canProcess(dsl);
			System.out.println("Can process " + canProcess + " by " + command.getClass().getName());
			if (canProcess) {
				boolean success = command.process(dsl);
				System.out.println("Was successful " + success);
				if (!success) {
					logger.addLogEntry("Unable to execute command");
				}
				break;
			}
		}

		if(!canProcess) {
			logger.addLogEntry("Unknown command");
		}


		return canProcess;
	}


}
