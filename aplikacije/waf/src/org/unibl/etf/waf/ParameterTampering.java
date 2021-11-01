package org.unibl.etf.waf;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Request;
import org.unibl.etf.waf.logging.Logger;

public class ParameterTampering {
	private Request request;
	private boolean attack;
	private List<String> parameters;
	private String report;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	public ParameterTampering(Request request) {
		this.request = request;
		attack = false;
		report = "==========================================\n" + format.format(new Date())
				+ "\nPotentially malicious request:\nMethod: ";
		try {
			parameters = Files.readAllLines(Paths.get("parameter_tempering_config.properties"));
		} catch (Exception e) {
			Logger.log(e.toString(), e);
		}
	}

	public boolean isAttack() {
		report += request.getMethod() + "\n";
		request.getParameterNames().asIterator().forEachRemaining(paramName -> {
			String parameter = request.getParameter(paramName);
			filter(paramName, parameter);
		});
		
		report += "\nCookies:\n";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Arrays.stream(cookies).forEach(cookie -> {
				filter(cookie.getName(), cookie.getValue());
			});
		}

		report += "Address: " + request.getRemoteAddr() + "\n";
		report += "==========================================\n";
		if (attack)
			WafValve.write(report);
		return attack;
	}
	
	private void filter(String paramName, String parameter) {
		parameters.forEach(e -> {
			String parts[] = e.split(":");
			if (parts[0].equals(paramName)) {
				report += paramName + ": " + parameter;
				if (parts[1].equals("string")) {
					int min = Integer.parseInt(parts[2]), max = Integer.parseInt(parts[3]);
					if (parameter.length() > max) {
						attack=true;
						report += " // " + paramName + ".length < " + min + " || " + paramName + ".length > " + max
								+ "\n";
					} else {
						report += "\n";
					}
				} else if (parts[1].equals("double")) {

					try {
						Double.parseDouble(parameter);
						int x = Integer.parseInt(parts[2]), y = Integer.parseInt(parts[3]);
						if (parameter.contains(".")) {
							String number[] = parameter.split("\\.");
							if (number[0].length() > x || number[1].length() > y) {
								attack=true;
								report += " // bad format";
							}
						} else if(parameter.length()>x) {
							attack=true;
							report += " // bad format";
						}
					} catch (NumberFormatException e1) {
						attack=true;
						report += " // nan";
					} finally {
						report+="\n";
					}
				}
			}
		});
	}
}
