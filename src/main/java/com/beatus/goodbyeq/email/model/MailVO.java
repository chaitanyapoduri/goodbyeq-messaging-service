package com.beatus.goodbyeq.email.model;

import java.util.List;

public class MailVO {
	private String mailId;
	private String fromAddress;
	private String toAddress;
	private String subject;
	private String body;
	private List<String> ccAddress;
	private List<String> bccAddress;
	private List<MailAttachmentVO> attachments;
	private String htmlBody;

	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<String> getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(List<String> ccAddress) {
		this.ccAddress = ccAddress;
	}
	public List<String> getBccAddress() {
		return bccAddress;
	}
	public void setBccAddress(List<String> bccAddress) {
		this.bccAddress = bccAddress;
	}
	public List<MailAttachmentVO> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<MailAttachmentVO> attachments) {
		this.attachments = attachments;
	}
	public String getHtmlBody() {
		return htmlBody;
	}
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
}
