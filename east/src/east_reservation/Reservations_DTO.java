package east_reservation;

import java.sql.Date;

public class Reservations_DTO {

	private int listNO;
	private int memberNO;
	private String name;
	private int planeNO;
	private String planeCode;
	private String planeName;
	private Date startDate;
	private String startTime;
	private Date arrivalDate;
	private String arrivalTime;
	private String startLoc;
	private String arrivalLoc;
	private String seat;
	private String cancel;
	
	public int getListNO() {
		return listNO;
	}
	public void setListNO(int listNO) {
		this.listNO = listNO;
	}
	public int getMemberNO() {
		return memberNO;
	}
	public void setMemberNO(int memberNO) {
		this.memberNO = memberNO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlaneNO() {
		return planeNO;
	}
	public void setPlaneNO(int planeNO) {
		this.planeNO = planeNO;
	}
	public String getPlaneCode() {
		return planeCode;
	}
	public void setPlaneCode(String planeCode) {
		this.planeCode = planeCode;
	}
	public String getPlaneName() {
		return planeName;
	}
	public void setPlaneName(String planeName) {
		this.planeName = planeName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getStartLoc() {
		return startLoc;
	}
	public void setStartLoc(String startLoc) {
		this.startLoc = startLoc;
	}
	public String getArrivalLoc() {
		return arrivalLoc;
	}
	public void setArrivalLoc(String arrivalLoc) {
		this.arrivalLoc = arrivalLoc;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getCancel() {
		return cancel;
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
	
}
