package com.infatuation.logsplorer;

import com.infatuation.logsplorer.entity.Log;
import com.infatuation.logsplorer.service.LogService;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

class LogsplorerApplicationTests {
	
	final String LOG_REGEX = "^(\\S+) (\\S+) (\\S+) \\[(.+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\S+)";
	private LogService logService = new LogService();

	/**
	 * Test valid log parser
	 */
	@Test
	public void parseLog() throws ParseException {

		String rawLog = "86.176.116.151 - williamsims [01/Jul/2000 08:46:04 +0000] \"GET /followers/77 HTTP/1.0\" 200 266";

		Log log = logService.parse(rawLog, LOG_REGEX);
		assertThat(log.getRemoteHost()).isEqualTo("86.176.116.151");
		assertThat(log.getRfc931()).isEqualTo("-");
		assertThat(log.getAuthUser()).isEqualTo("williamsims");
		assertThat(log.getDate()).isEqualTo(logService.parseDate("01/Jul/2000 08:46:04 +0000"));
		assertThat(log.getMethod()).isEqualTo("GET");
		assertThat(log.getUriPath()).isEqualTo("/followers/77");
		assertThat(log.getProtocol()).isEqualTo("HTTP/1.0");
		assertThat(log.getStatus()).isEqualTo(200);
		assertThat(log.getBytes()).isEqualTo(266);
		assertThat(log.getRaw()).isEqualTo(rawLog);
	}

	/**
	 * Test invalid log parser
	 */
	@Test
	public void parseBadLog() throws ParseException {

		String rawLog = "86.176.116.151 - williamsims [01/Jul/2000 08:46:04 +0000] \"GET /followers/77 HTTP/1.0\" 200 266 -";

		Log log = logService.parse(rawLog, LOG_REGEX);
		assertThat(log.getRemoteHost()).isEqualTo(null);
		assertThat(log.getRfc931()).isEqualTo(null);
		assertThat(log.getAuthUser()).isEqualTo(null);
		assertThat(log.getDate()).isEqualTo(null);
		assertThat(log.getMethod()).isEqualTo(null);
		assertThat(log.getUriPath()).isEqualTo(null);
		assertThat(log.getProtocol()).isEqualTo(null);
		assertThat(log.getStatus()).isEqualTo(null);
		assertThat(log.getBytes()).isEqualTo(null);
		assertThat(log.getRaw()).isEqualTo(rawLog);
	}
}
