package nl.hu.richrail.rrdsl;

import java.util.HashMap;

import nl.hu.richrail.trains.*;

public interface Command
{
 CmdResult interpret(RollingStockPool context);

}
