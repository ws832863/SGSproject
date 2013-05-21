package de.ovgu.sgslearn.task;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;

public class MessageTask implements Task, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1041565387708311155L;

	private ManagedReference<ClientSession> sessionRef;

	private static final String MESSAGE_CHARSET = "UTF-8";

	public MessageTask(ClientSession client) {
		sessionRef = AppContext.getDataManager().createReference(client);
	}

	@Override
	public void run() throws Exception {
		getSession().send(encodeString("A Message From Task"));
	}

	private ClientSession getSession() {
		return sessionRef.get();
	}

	/**
	 * Encodes a {@code String} into a {@link ByteBuffer}.
	 * 
	 * @param s
	 *            the string to encode
	 * @return the {@code ByteBuffer} which encodes the given string
	 */
	protected static ByteBuffer encodeString(String s) {
		try {
			return ByteBuffer.wrap(s.getBytes(MESSAGE_CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new Error("Required character set " + MESSAGE_CHARSET
					+ " not found", e);
		}
	}

}
