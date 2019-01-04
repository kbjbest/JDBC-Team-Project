package east_aviationinfo;

public class TB_AviationInfoDTO {
	private int planeNo;
	private String planeCode, planeName, startLoc, arrivalLoc, startTime, arrivalTime;

	public TB_AviationInfoDTO() {
		super();
	}

	public TB_AviationInfoDTO(int planeNo, String planeCode, String planeName, String startTime, String arrivalTime, String startLoc,
			String arrivalLoc) {
		this.planeNo = planeNo;
		this.planeCode = planeCode;
		this.planeName = planeName;
		this.startLoc = startLoc;
		this.arrivalLoc = arrivalLoc;
		this.startTime = startTime;
		this.arrivalTime = arrivalTime;
	}

	public int getPlaneNo() {
		return planeNo;
	}

	public void setPlaneNo(int planeNo) {
		this.planeNo = planeNo;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
}