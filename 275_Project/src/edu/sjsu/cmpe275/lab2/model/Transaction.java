package edu.sjsu.cmpe275.lab2.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRANSACTIONID")
	private String transid;
	@Column(name = "BOOKID")
	private String bookid;
	@Column(name = "USERID")
	private String userid;
	@Column(name = "Checkout_Date")
	private String checkoutDate;
	@Column(name = "Due_Date")
	private String dueDate;
	
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public Transaction() {
		super();
		
	}
	public Transaction(String transid, String bookid, String userid, String checkoutDate, String dueDate) {
		super();
		this.transid = transid;
		this.bookid = bookid;
		this.userid = userid;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}
	
	
	
	
}
