package com.infatuation.logsplorer.service;

import com.infatuation.logsplorer.entity.Log;
import com.infatuation.logsplorer.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogService {

	@Autowired
	LogRepository repository;

	public void persistLine(String line) {
		try{
			Log log = parse(line);
			repository.save(log);
		} catch( Exception e ){
			System.out.println(String.format("Error parsing %s. Save raw", line));
			Log log = new Log();
			log.setRaw(line);
			repository.save(log);
		}
	}

	public Log parse(String line) throws ParseException, IllegalStateException {
		System.out.println(line);
		//  "([^"].)" "([^"].)"   -- Referrer and Browser info
		final String regex = "^(\\S+) (\\S+) (\\S+) \\[(.+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\S+)";
		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss Z");
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(line);
		matcher.matches();

		Log log = new Log();
		String host = matcher.group(1);
		String rfc931 = matcher.group(2);
		String user = matcher.group(3);
		Date date = formatter.parse(matcher.group(4));
		Timestamp time = new Timestamp(date.getTime());
		String method = matcher.group(5);
		String uriPath = matcher.group(6);
		String protocol = matcher.group(7);
		int status = Integer.parseInt(matcher.group(8));
		int bytes = Integer.parseInt(matcher.group(9));

		log.setRaw(line);
		log.setRemoteHost(host);
		log.setRfc931(rfc931);
		log.setAuthUser(user);
		log.setDate(time);
		log.setMethod(method);
		log.setUriPath(uriPath);
		log.setProtocol(protocol);
		log.setStatus(status);
		log.setBytes(bytes);

		return log;
	}
}
