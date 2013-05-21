package de.ovgu.sgslearn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedReference;

import de.ovgu.sgslearn.items.Item;

public class SimpleClientListener implements ClientSessionListener,
		Serializable {

	/**
	 * 
	 */
	private static final String MESSAGE_CHARSET = "UTF-8";

	private static final Logger log = Logger
			.getLogger(SimpleClientListener.class.getName());

	private static final long serialVersionUID = 1L;

	private final ManagedReference<ClientSession> sessionRef;

	private ManagedReference<Inventory> inventoryRef;

	private ManagedReference<Shopper> shopperRef;
	private static long staticid = 0;

	// Counstructor
	public SimpleClientListener(ClientSession client) {
		Inventory inventory = new Inventory();
		Shopper shopper = new Shopper();
		sessionRef = AppContext.getDataManager().createReference(client);
		inventoryRef = AppContext.getDataManager().createReference(inventory);
		shopperRef = AppContext.getDataManager().createReference(shopper);
	}

	protected ClientSession getSession() {
		return sessionRef.get();
	}

	@Override
	public void disconnected(boolean graceful) {
		if (graceful) {
			log.info("Graceful disconnected from client");
		} else {
			log.info("Forecd disconnected from client");

		}
	}

	@Override
	public void receivedMessage(ByteBuffer msg) {
		String msgFromClient = decodeString(msg);
		System.out.println("------add all swords to inventory");
		for (int i = 0; i < 10; i++) {
			Item item = new Item(100);
			item.setItemName(msgFromClient + i);
			inventoryRef.get().addItem(item);
		}
		inventoryRef.get().listAllItems();
		int i = 0;
		do {
			i++;
			System.out.println("the shopper has money:"
					+ shopperRef.get().getMoney());
			
			inventoryRef.get().listAllItems();

			shopperRef.get().listStuffs();

			shopperRef.get().buy(inventoryRef.get().getItem(0),
					inventoryRef.get());
			inventoryRef.get().listAllItems();
			System.out.println("the shopper has money:"
					+ shopperRef.get().getMoney());
			shopperRef.get().listStuffs();
		} while (i < 6);

		log.info(msgFromClient);

		msgFromClient = "[" + staticid++ + "] sever broadcast : "
				+ msgFromClient;
		// sessionRef.get().send(encodeString(msgFromClient));

		this.getSession().send(encodeString(msgFromClient));

	}

	public void receivedObjectMsg(byte[] msg) {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(msg));
			Object m = ois.readObject();

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * send a java object to a client
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void sendObjectMsg(Object msg) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bytes);
		oos.writeObject(msg);
		oos.flush();
		this.getSession().send(ByteBuffer.wrap(bytes.toByteArray()));

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

	/**
	 * Decodes a {@link ByteBuffer} into a {@code String}.
	 * 
	 * @param buf
	 *            the {@code ByteBuffer} to decode
	 * @return the decoded string
	 */
	protected static String decodeString(ByteBuffer buf) {
		try {
			byte[] bytes = new byte[buf.remaining()];
			buf.get(bytes);
			return new String(bytes, MESSAGE_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new Error("Required character set " + MESSAGE_CHARSET
					+ " not found", e);
		}
	}

}
