import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ChannelManager;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedReference;

public class HelloChannelsSessionListener implements ClientSessionListener,
		Serializable {
	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;

	/** The {@link Logger} for this class. */
	private static final Logger logger = Logger
			.getLogger(HelloChannelsSessionListener.class.getName());

	/** The session this {@code ClientSessionListener} is listening to. */
	private final ManagedReference<ClientSession> sessionRef;

	/** The name of the {@code ClientSession} for this listener. */
	private final String sessionName;

	public HelloChannelsSessionListener(ClientSession session,
			ManagedReference<Channel> channel1) {
		if (session == null) {
			throw new NullPointerException("null session");
		}
		sessionRef = AppContext.getDataManager().createReference(session);
		sessionName = session.getName();
		// Join the session to all channels. We obtain the channel
		// in two different ways, by reference and by name.
		ChannelManager channelMgr = AppContext.getChannelManager();

		// We were passed a reference to the first channel.
		channel1.get().join(session);

		// We look up the second channel by name.
		Channel channel2 = channelMgr.getChannel(HelloChannels.CHANNEL_2_NAME);
		channel2.join(session);

	}

	protected ClientSession getSession() {
		// We created the ref with a non-null session, so no need to check it.
		return sessionRef.get();
	}

	@Override
	public void receivedMessage(ByteBuffer message) {
		ClientSession session = getSession();
		logger.log(Level.INFO, "Message from {0}", sessionName);
		// Echo message back to sender
		session.send(message);
	}

	@Override
	public void disconnected(boolean arg0) {
		String grace = arg0 ? "graceful" : "forced";
		logger.log(Level.INFO, "User {0} has logged out {1}", new Object[] {
				sessionName, grace });
	}

}
