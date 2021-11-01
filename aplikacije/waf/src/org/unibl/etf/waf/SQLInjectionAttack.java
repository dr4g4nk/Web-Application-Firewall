package org.unibl.etf.waf;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.servlet.http.Cookie;
import org.apache.catalina.connector.Request;
import org.unibl.etf.waf.logging.Logger;

public class SQLInjectionAttack {
	private Request request;
	private boolean attack;
	private String[] keywords;
	private String report;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	public SQLInjectionAttack(Request request) {
		this.request = request;
		attack = false;
		report = "==========================================\n" + format.format(new Date())
				+ "\nPotentially malicious request:\nMethod: ";
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("sql_injection_config.properties"));
			keywords = prop.getProperty("keywords").split(",");
		} catch (Exception e) {
			Logger.log(e.toString(), e);
		}
	}

	public boolean isAttack() {
		report += request.getMethod() + "\n";

		request.getParameterNames().asIterator().forEachRemaining(e -> {

			String parameter = request.getParameter(e);
			report += e + ": " + parameter + "\n";
			Arrays.stream(keywords).forEach(keyword -> {
				if (parameter.toLowerCase().contains(keyword) && !attack) {
					attack = true;
				}
			});
		});
		report += "\nCookies:\n";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Arrays.stream(cookies).forEach(cookie -> {
				String name = cookie.getName();
				String value = cookie.getValue();
				report += name + ": " + value + "\n";
				Arrays.stream(keywords).forEach(keyword -> {
					if (value.toLowerCase().contains(keyword)) {
						attack = true;
					}
				});
			});
		}
		report += "Address: " + request.getRemoteAddr() + "\n";
		report += "==========================================\n";
		if (attack)
			WafValve.write(report);
		return attack;
	}

}
