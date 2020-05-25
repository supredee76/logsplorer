package com.infatuation.logsplorer.service;

import com.infatuation.logsplorer.entity.Log;
import com.infatuation.logsplorer.repository.LogRepository;
import com.infatuation.logsplorer.repository.LogRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogService {
	final String LOG_REGEX = "^(\\S+) (\\S+) (\\S+) \\[(.+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\S+)";
	final String TIMESTAMP_FORMAT = "dd/MMM/yyyy hh:mm:ss Z";

	Logger logger = LoggerFactory.getLogger(LogService.class);

	@Autowired
	LogRepository repository;

	@Autowired
	LogRepositoryCustom logRepositoryCustom;

	public List<String> searchLog(String code, String method, String user){
		return logRepositoryCustom.searchLogsWithCriteria(code, method, user);
	}

	/**
	 * This function parse and persist log file to database.
	 * It save raw if parse failed.
	 */
	public void persistLine(String line) {
		try{
			Log log = parse(line, LOG_REGEX);
			repository.save(log);
		} catch( Exception e ){
			logger.error(String.format("Error parsing %s. Save raw", line), e);
			Log log = new Log();
			log.setRaw(line);
			repository.save(log);
		}
	}

	/**
	 * Parse log file using regular expression
	 * @param line - Raw log string
	 * @param regex - Regular expression string
	 * @return - Log entity of the log
	 */
	public Log parse(String line, String regex) throws ParseException, IllegalStateException {

		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(line);
		matcher.matches();

		Log log = new Log();
		String host = matcher.group(1);
		String rfc931 = matcher.group(2);
		String user = matcher.group(3);
		Timestamp time = parseDate(matcher.group(4));
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

	/**
	 * Parse string timestamp to java Timestamp
	 */
	public Timestamp parseDate(String strDate) throws ParseException {
		final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT);
		Date date = formatter.parse(strDate);
		Timestamp time = new Timestamp(date.getTime());
		return time;
	}
}
