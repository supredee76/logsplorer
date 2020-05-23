package com.infatuation.logsplorer.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "LOG")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_SEQ_ID")
	private Long id;

	@Column(name = "RAW")
	private String raw;

	@Column(name = "REMOTE_HOST")
	private String remoteHost;

	@Column(name = "RFC931")
	private String rfc931;

	@Column(name = "AUTH_USER")
	private String authUser;

	@Column(name = "DATE")
	private Timestamp date;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "URI_PATH")
	private String uriPath;

	@Column(name = "PROTOCOL")
	private String protocol;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "BYTES")
	private int bytes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public String getRfc931() {
		return rfc931;
	}

	public void setRfc931(String rfc931) {
		this.rfc931 = rfc931;
	}

	public String getAuthUser() {
		return authUser;
	}

	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getMethod() {
		return method;
	}

	public String getUriPath() {
		return uriPath;
	}

	public void setUriPath(String uriPath) {
		this.uriPath = uriPath;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBytes() {
		return bytes;
	}

	public void setBytes(int bytes) {
		this.bytes = bytes;
	}
}
