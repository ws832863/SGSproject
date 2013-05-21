

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.Task;
import com.sun.sgs.app.TaskManager;

public class HalloPersistence implements AppListener, Serializable, Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(HalloPersistence.class.getName());

	public static final int DELAY_MS = 5000;
	public static final int PERIOD_MS = 500;
	private long lastTimestamp = System.currentTimeMillis();

	@Override
	public void run() throws Exception {
		long timestamp = System.currentTimeMillis();
		long delta = timestamp - lastTimestamp;

		lastTimestamp = timestamp;
		logger.log(Level.INFO, "timestamp=({0},nummer,#),delta=({1},number,#)",
				new Object[] { timestamp, delta });
	}

	@Override
	public void initialize(Properties arg0) {
		TaskManager taskManager = AppContext.getTaskManager();
		taskManager.schedulePeriodicTask(this, DELAY_MS, PERIOD_MS);
	}

	@Override
	public ClientSessionListener loggedIn(ClientSession arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
