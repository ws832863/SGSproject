import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ChannelManager;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.Delivery;
import com.sun.sgs.app.ManagedReference;

public class HelloChannels implements AppListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HelloChannels.class
			.getName());
	static final String CHANNEL_1_NAME = "foo";
	static final String CHANNEL_2_NAME = "bar";

	private ManagedReference<Channel> channel1 = null;

	@Override
	public void initialize(Properties arg0) {
		ChannelManager channelMgr = AppContext.getChannelManager();
		Channel c1 = channelMgr.createChannel(CHANNEL_1_NAME, null,
				Delivery.RELIABLE);

		channel1 = AppContext.getDataManager().createReference(c1);

		channelMgr.createChannel(CHANNEL_2_NAME,
				new HelloChannelsChannelListener(), Delivery.RELIABLE);
	}

	@Override
	public ClientSessionListener loggedIn(ClientSession session) {
		logger.log(Level.INFO, "User {0} has logged in", session.getName());
		return new HelloChannelsSessionListener(session, channel1);
	}

}
