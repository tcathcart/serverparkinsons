package edu.dartmouth.cs.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dartmouth.cs.server.db.EntryDatastore;
import edu.dartmouth.cs.server.db.RegDatastore;
import edu.dartmouth.cs.server.gcm.Message;
import edu.dartmouth.cs.server.gcm.Sender;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class SendMessageServlet extends HttpServlet {

	private static final int MAX_RETRY = 5;
	protected static final Logger logger = Logger.getLogger(SendMessageServlet.class
			.getName());

	/**
	 * Processes the request to add a new message.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<String> devices = RegDatastore.getDevices();
		String msg = req.getParameter("msg");
//		String id = req.getParameter("id").toString();
//		long lid = Long.valueOf(id).longValue();
//		
//		EntryDatastore.remove(lid);
//		
		logger.info("sending message " + msg);
		logger.info("Sending message to " + devices.size() + " devices");
		Message message = new Message(devices);
		message.addData("message", msg);
//		message.addData("from_name", "Dr. Campbell");

		// Have to hard-coding the API key when creating the Sender
		Sender sender = new Sender(Globals.GCMAPIKEY);
		// Send the message to device, at most retrying MAX_RETRY times
		sender.send(message, MAX_RETRY);
		
		resp.sendRedirect("/query.do");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}
}