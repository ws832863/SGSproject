import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ChannelListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedReference;

public class HelloChannelsChannelListener implements ChannelListener,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(HelloChannelsChannelListener.class.getName());

	@Override
	public void receivedMessage(Channel arg0, ClientSession arg1,
			ByteBuffer arg2) {
		if (logger.isLoggable(Level.INFO)) {
			logger.log(Level.INFO, "Channel message from {0} on channel {1}",
					new Object[] { arg1.getName(), arg0.getName() });
		}
	}

}
