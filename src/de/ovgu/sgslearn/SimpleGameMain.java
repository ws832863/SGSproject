package de.ovgu.sgslearn;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;

public class SimpleGameMain implements AppListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8099729077157574033L;
	private static final Logger log = Logger.getLogger(SimpleGameMain.class
			.getName());

	@Override
	public void initialize(Properties arg0) {
		log.info("Game Server initialized!");
	}

	@Override
	public ClientSessionListener loggedIn(ClientSession session) {
		log.info("" + "A Client login attempt");

		// log.log(Level.FINE,
		// " ClientSession Id {0},MaxMessageLength {1}",
		// new Object[] { session.getName(), session.getMaxMessageLength() });

		 return new SimpleClientListener(session);

		
	}
}
