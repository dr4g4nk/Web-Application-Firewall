package org.unibl.etf.waf;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import org.apache.catalina.connector.Request;
import org.unibl.etf.waf.logging.Logger;

public class XSSAttack {
	private Request request;
	private boolean attack;
	private String[] reg;
	private String report;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	public XSSAttack(Request request) {
		this.request = request;
		attack = false;
		report = "==========================================\n" + format.format(new Date())
				+ "\nPotentially malicious request:\nMethod: ";
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("xssconfig.properties"));
			reg = prop.getProperty("patterns").split(",");
		} catch (Exception e) {
			Logger.log(e.toString(), e);
		}

	}

	public boolean isAttack() {
		report += request.getMethod() + "\n";

		request.getParameterNames().asIterator().forEachRemaining(e -> {

			String parameter = request.getParameter(e);
			report += e + ": " + parameter + "\n";
			Arrays.stream(reg).forEach(r -> {
				Pattern pattern = Pattern.compile(r);
				Matcher matcher = pattern.matcher(parameter);
				if (matcher.find()) {
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
				Arrays.stream(reg).forEach(r -> {
					Pattern pattern = Pattern.compile(r);
					Matcher matcher = pattern.matcher(value);
					if (matcher.find()) {
						attack = true;
					}
				});
			});
		}
		report += "\nAddress: " + request.getRemoteAddr() + "\n";
		report += "==========================================\n";
		if (attack)
			WafValve.write(report);
		return attack;
	}

}
