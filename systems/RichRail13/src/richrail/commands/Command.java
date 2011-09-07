package richrail.commands;

public interface Command {
	boolean process(String dsl);

	boolean canProcess(String dsl);
}
