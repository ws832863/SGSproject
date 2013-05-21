import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.Task;

public class TrivialTimedTask implements Serializable, ManagedObject, Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(TrivialTimedTask.class.getName());

	/** The timestamp when this task was last run. */
	private long lastTimestamp = System.currentTimeMillis();

	@Override
	public void run() throws Exception {
		//AppContext.getDataManager().markForUpdate(this);
		long timestamp = System.currentTimeMillis();
		long delta = timestamp - lastTimestamp;

		lastTimestamp = timestamp;
		logger.log(Level.INFO, "timestamp={0},number,#,delta={1},number,#",
				new Object[] { timestamp, delta });
	}
}
