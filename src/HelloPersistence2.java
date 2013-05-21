import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.TaskManager;

public class HelloPersistence2 implements Serializable, AppListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(HelloPersistence2.class.getName());

	public static final int DELAY_MS = 5000;
	public static final int PERIOD_MS = 500;

	@Override
	public void initialize(Properties arg0) {
		TrivialTimedTask task = new TrivialTimedTask();
		logger.log(Level.INFO, "Created task:{0}", task);

		TaskManager taskManager = AppContext.getTaskManager();
		taskManager.schedulePeriodicTask(task, DELAY_MS, PERIOD_MS);

	}

	@Override
	public ClientSessionListener loggedIn(ClientSession arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
