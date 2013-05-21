/*
 * Copyright 2007-2010 Sun Microsystems, Inc.
 *
 * This file is part of Project Darkstar Server.
 *
 * Project Darkstar Server is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation and
 * distributed hereunder to you.
 *
 * Project Darkstar Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * --
 */

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

/**
 * Hello World example for the Project Darkstar Server. Prints
 * {@code "Hello World!"} to the console the first time it is started.
 */
public class HalloWorld implements AppListener, // to get called during
												// application startup.
		Serializable, Task // since all AppListeners are ManagedObjects.
{
	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HalloWorld.class
			.getName());
	public static final int DELAY_MS = 5000;
	public static final int PERIOD_MS = 500;

	/**
	 * {@inheritDoc}
	 * <p>
	 * Prints our well-known greeting during application startup.
	 */
	public void initialize(Properties props) {
		System.out.println("test app");
		TaskManager taskManager = AppContext.getTaskManager();
		taskManager.schedulePeriodicTask(this, DELAY_MS, PERIOD_MS);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Prevents client logins by returning {@code null}.
	 */
	public ClientSessionListener loggedIn(ClientSession session) {
		return null;
	}

	@Override
	public void run() throws Exception {
		logger.log(Level.INFO,
				"HelloTimer task:running at timestamp{0,nummer,#}",
				System.currentTimeMillis());
	}
}
