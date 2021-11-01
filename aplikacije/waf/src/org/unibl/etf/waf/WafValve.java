package org.unibl.etf.waf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.unibl.etf.waf.logging.Logger;

public class WafValve extends ValveBase {

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		
		request.getSession(true);
		boolean attack = new XSSAttack(request).isAttack() || new SQLInjectionAttack(request).isAttack();
	
		if (!attack) {
			try {
				getNext().invoke(request, response);
			} catch (Exception e) {
				Logger.log(e.toString(), e);
			}
		} else {
			request.getSession().invalidate();
			PrintWriter writer = response.getWriter();
			writer.println("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<meta charset=\"UTF-8\">\r\n"
					+ "<title>Error page</title>\r\n" + "</head>\r\n" + "<body>\r\n"
					+ "	<h1>Izvrsavate napad na stranicu, zabranjen vam je pristup</h1>\r\n"
					+ "</body>\r\n" + "</html>");
			writer.close();
		}
	}

	public static synchronized void write(String report) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File("Report.txt"), true)))) {
			writer.println(report);
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
	}

}
