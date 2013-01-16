package net.virtualqueues.model;

import java.io.Serializable;

import org.joda.time.*;

/**
 * This is Ticket model
 * @author Vicho
 *
 */
public class Ticket implements Serializable {
	/**
	 * generated
	 */
	private static final long serialVersionUID = -5558035346929155305L;
	
	// the ticket number on the queue order
	private int uniqueID;
	
	// the reason for the client's visit
	private String reason;
	
	// the expected duration of the visit (in seconds)
	private int duration;
	
	// the time when the visit is expected to start
	private DateTime startDate;
	
	// the time when the visit is expected to end
	private DateTime endDate;
	
	// a number for categorizing the tickets by same reasons
	// types will change by clients' wishes, so no enumerations
	private int type;
	
	/*
	 * Tickets will be created by the desktop and then sent over to 
	 * the queue board
	 */
	/**
	 * 
	 * @param reason_arg
	 * @param duration_arg
	 * @param orderNum_arg
	 * @param startDate_arg
	 * @param type_arg
	 */
	public Ticket(final String reason_arg, int duration_arg, int orderNum_arg, final  DateTime startDate_arg, int type_arg){
		this.setUniqueID(orderNum_arg);
		this.setReason(reason_arg);
		this.duration = duration_arg;
		this.startDate = startDate_arg;
		this.endDate = startDate_arg.plusMinutes(duration_arg);
		this.setType(type_arg);
	}
	public Ticket(final TicketType templateTicket, final DateTime startDate_arg, int orderNum_arg){
		this.setUniqueID(orderNum_arg);
		
		this.startDate = startDate_arg;
		this.endDate = startDate_arg.plusMinutes(templateTicket.duration);
		
		this.setReason(templateTicket.reason);
		this.duration = templateTicket.duration;
		this.setType(templateTicket.type);
	}
	/**
	 * 
	 * @param right
	 * @return returns >= comparison (true if this end date is equal to start date of right)
	 */
	public boolean isBefore(final Ticket right) {
		return this.endDate.isBefore(right.startDate.plus(1));
	}
	/**
	 * 
	 * @param left
	 * @return returns <= comparison (true if this start date is equal to the end date of left)
	 */
	public boolean isAfter(Ticket left) {
		return this.startDate.isAfter(left.endDate.minus(1));
	}
	public boolean overlaps(Ticket ticket) {
		boolean startOverlaps = (ticket.startDate.isAfter(this.startDate) && ticket.startDate.isBefore(this.endDate));
		boolean endOverlaps = (ticket.endDate.isAfter(this.startDate) && ticket.endDate.isBefore(this.endDate));
		
		return (startOverlaps || endOverlaps);
	}

	public DateTime getStartDate(){
		return this.startDate;
	}
	
	public DateTime getEndDate(){
		return this.endDate;
	}
	public int getType() {
		return type;
	}
	
	/**
	 * @see TicketType
	 * @param type
	 */
	public void setType(int type) {
		//TODO use type to change fields like reason, duration, etc (@see constructor w/ TicketType)
		this.type = type;
	}
	public void moveInTime(long timeDeltaMillis) {
		startDate = startDate.plus(timeDeltaMillis);
		endDate = endDate.plus(timeDeltaMillis);
	}
	public String getReason() {
		return reason;
	}
	public void setReason(final String reason) {
		this.reason = reason;
	}
	public int getUniqueID() {
		return uniqueID;
	}
	private void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	@Override
	public String toString(){
		return (this.startDate.toString() + this.endDate.toString() + this.reason + this.uniqueID);	
	}
}


