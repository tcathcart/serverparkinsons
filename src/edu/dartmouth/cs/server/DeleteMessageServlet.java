package tdc.myruns.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tdc.myruns.server.db.EntryDatastore;
import tdc.myruns.server.db.RegDatastore;
import tdc.myruns.server.gcm.Message;
import tdc.myruns.server.gcm.Sender;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class DeleteMessageServlet extends HttpServlet {

	private static final int MAX_RETRY = 5;
	protected static final Logger logger = Logger.getLogger(DeleteMessageServlet.class
			.getName());

	/**
	 * Processes the request to add a new message.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<String> devices = RegDatastore.getDevices();
		
		String id = req.getParameter("id").toString();
		long lid = Long.valueOf(id).longValue();
		
		EntryDatastore.remove(lid);
		
		logger.info("Just deleted entry " + id);

		logger.info("Sending message to " + devices.size() + " devices");
		Message message = new Message(devices);
		message.addData("message", id);
//		message.addData("del_id", id);

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