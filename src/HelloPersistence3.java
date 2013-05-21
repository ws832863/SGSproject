import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;
import com.sun.sgs.app.TaskManager;

public class HelloPersistence3 implements Serializable, AppListener, Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(HelloPersistence3.class.getName());

	public static final int DELAY_MS = 5000;
	public static final int PERIOD_MS = 500;
	/** a reference to our subtask */
	private ManagedReference<TrivialTimedTask> subTaskRef = null;

	@Override
	public void initialize(Properties arg0) {
		TrivialTimedTask task = new TrivialTimedTask();
		// logger.log(Level.INFO, "Created task:{0}", task);
		setSubTask(task);
		TaskManager taskManager = AppContext.getTaskManager();
		taskManager.schedulePeriodicTask(this, DELAY_MS, PERIOD_MS);

	}

	public TrivialTimedTask getSubTask() {
		if (subTaskRef == null) {
			return null;
		}
		return subTaskRef.get();
	}

	public void setSubTask(TrivialTimedTask subTask) {
		if (subTask == null) {
			subTaskRef = null;
			return;
		}
		DataManager dataManager = AppContext.getDataManager();
		subTaskRef = dataManager.createReference(subTask);

	}

	@Override
	public ClientSessionListener loggedIn(ClientSession arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() throws Exception {
		TrivialTimedTask subTask = getSubTask();
		if (subTask == null) {
			logger.log(Level.WARNING, "subTask is null");
			return;
		}
		subTask.run();
	}

}
