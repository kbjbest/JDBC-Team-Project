package east_reservation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import db.DBConnection;
import east_view.Member_DTO;

public class Reservations_DAO {

	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// TODO searchAll : 전체보기
	/**
	 * 예매현황에 관련된 모든 항목을 가져옴.
	 * @param isReservation 예매내역 또는 취소내역 구분
	 * @param dto_member 유저일 경우 유저 정보를 전달하여 해당 유저의 정보만 검색
	 * @return 예매현황 리스트
	 */
	public ArrayList<Reservations_DTO> searchAll(String isReservation, Member_DTO dto_member) {
		ArrayList<Reservations_DTO> result = new ArrayList<>();
		con = DBConnection.getConnection();

		try {
			stmt = con.createStatement();

			String sql = "select m.MemberNO, m.Name, "
					+ "a.PlaneNO, a.PlaneCode, a.PlaneName, "
					+ "r.StartDate, a.StartTime, "
					+ "r.ArrivalDate, a.ArrivalTime, "
					+ "a.StartLoc, a.ArrivalLoc, r.Seat, r.ListNO "
					+ "from TB_RESERVATIONS r, TB_MEMBER m, TB_AVIATIONINFO a "
					+ "where m.MemberNO = r.MemberNO "
					+ ((dto_member != null) ? "and m.MemberNO = " + dto_member.getMemberNO() + " " : "")
					+ "and a.PlaneNO = r.PlaneNO "
					+ "and r.Cancel = '" + (isReservation.equals(Reservations.MODE_CANCEL) ? "y" : "n") + "' "
					+ "order by r.ListNO desc";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Reservations_DTO data = new Reservations_DTO();
				data.setMemberNO(rs.getInt(1));
				data.setName(rs.getString(2));
				data.setPlaneNO(rs.getInt(3));
				data.setPlaneCode(rs.getString(4));
				data.setPlaneName(getPlaneInitialToPlaneName(rs.getString(5)));
				data.setStartDate(rs.getDate(6));
				data.setStartTime(rs.getString(7));
				data.setArrivalDate(rs.getDate(8));
				data.setArrivalTime(rs.getString(9));
				data.setStartLoc(rs.getString(10));
				data.setArrivalLoc(rs.getString(11));
				data.setSeat(rs.getString(12));
				data.setListNO(rs.getInt(13));

				result.add(data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	// TODO search : 조건 검색
	/**
	 * 조건을 통해서 예매내역 항목을 가져옴.
	 * @param dto : 조건에 사용할 DTO 객체
	 * @return 필터링된 예매현황 리스트
	 */
	public ArrayList<Reservations_DTO> search(Reservations_DTO dto) {
		ArrayList<Reservations_DTO> result = new ArrayList<>();
		con = DBConnection.getConnection();

		try {
			stmt = con.createStatement();

			String sql = "select m.MemberNO, m.Name, "
					+ "a.PlaneNO, a.PlaneCode, a.PlaneName, "
					+ "r.StartDate, a.StartTime, "
					+ "r.ArrivalDate, a.ArrivalTime, "
					+ "a.StartLoc, a.ArrivalLoc, r.Seat, r.ListNO "
					+ "from TB_RESERVATIONS r, TB_MEMBER m, TB_AVIATIONINFO a ";
			String sql_Where = "where (m.MemberNO = r.MemberNO "
					+ "and a.PlaneNO = r.PlaneNO) "
					+ "and r.Cancel = '" + ((dto.getCancel() == null) ? "n" : dto.getCancel()) + "' ";
			ArrayList<String> arr_Where = new ArrayList<>();
			String sql_Order = " order by r.ListNO desc";

			if (dto.getMemberNO() != 0) {
				arr_Where.add("m.MemberNO = " + dto.getMemberNO());
			}
			if (dto.getName() != null && !dto.getName().equals("")) {
				arr_Where.add("m.Name like '%' || '" + dto.getName() + "' || '%'");
			}
			if (dto.getPlaneName() != null && !dto.getPlaneName().equals("")) {
				arr_Where.add("a.planeName = '" + getPlaneNameToPlaneInitial(dto.getPlaneName()) + "'");
			}
			if (dto.getSeat() != null && !dto.getSeat().equals("")) {
				arr_Where.add("r.Seat = '" + dto.getSeat() + "'");
			}
			if (dto.getStartDate() != null && dto.getArrivalDate() != null) {
				arr_Where.add("(r.StartDate >= '" + dto.getStartDate().toString() + "' and r.ArrivalDate <= '"
						+ dto.getArrivalDate() + "')");
			}
			if (dto.getStartLoc() != null) {
				arr_Where.add("a.StartLoc = '" + dto.getStartLoc() + "'");
			}
			if (dto.getArrivalLoc() != null) {
				arr_Where.add("a.ArrivalLoc = '" + dto.getArrivalLoc() + "'");
			}

			for (int i = 0; i < arr_Where.size(); i++) {
				sql_Where += " and ";
				sql_Where += arr_Where.get(i);
				if (i != arr_Where.size() - 1) {
				}
			}

			String query = sql + sql_Where + sql_Order;
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				Reservations_DTO data = new Reservations_DTO();
				data.setMemberNO(rs.getInt(1));
				data.setName(rs.getString(2));
				data.setPlaneNO(rs.getInt(3));
				data.setPlaneCode(rs.getString(4));
				data.setPlaneName(getPlaneInitialToPlaneName(rs.getString(5)));
				data.setStartDate(rs.getDate(6));
				data.setStartTime(rs.getString(7));
				data.setArrivalDate(rs.getDate(8));
				data.setArrivalTime(rs.getString(9));
				data.setStartLoc(rs.getString(10));
				data.setArrivalLoc(rs.getString(11));
				data.setSeat(rs.getString(12));
				data.setListNO(rs.getInt(13));
				
				result.add(data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	// TODO delete : 삭제
	/**
	 * 예매항목 삭제
	 * @param dto : 조건에 사용할 DTO 객체
	 * @return 삭제 결과
	 */
	public int delete(Reservations_DTO dto) {
		int result = 0;
		con = DBConnection.getConnection();

		try {
			String sql = "delete from tb_reservations "
					+ "where ListNO = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getListNO());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// TODO update : 수정
	/**
	 * 예매항목 수정
	 * @param dto : 조건에 사용할 DTO 객체
	 * @return 수정 결과
	 */
	public int update(Reservations_DTO dto) {
		int result = 0;
		con = DBConnection.getConnection();
		
		try {
			String sql = "update tb_reservations ";
			if (dto.getCancel().equals("n")) {
				sql += "set MemberNo = ?, PlaneNO = ?, Seat = ?, StartDate = ?, ArrivalDate = ?, Cancel = ? "
						+ "where ListNO = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, dto.getMemberNO());
				pstmt.setInt(2, dto.getPlaneNO());
				pstmt.setString(3, dto.getSeat());
				pstmt.setDate(4, dto.getStartDate());
				pstmt.setDate(5, dto.getArrivalDate());
				pstmt.setString(6, dto.getCancel());
				pstmt.setInt(7, dto.getListNO());
				
			} else if (dto.getCancel().equals("y")) {
				sql += "set Cancel = ? "
						+ "where ListNO = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getCancel());
				pstmt.setInt(2, dto.getListNO());
				
			}

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// 사용 중인 객체들 닫기
	private void close() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO getPlaneInitialToPlaneName : 비행기이니셜 -> 비행기이름
	public String getPlaneInitialToPlaneName(String PlaneInitial) {
		String PlaneName = "";

		switch (PlaneInitial) {
		case "A":
			PlaneName = "아시아나";
			break;

		case "D":
			PlaneName = "대한항공";
			break;
			
		case "J":
			PlaneName = "진에어";
			break;
			
		case "E":
			PlaneName = "에미레이트";
			break;

		default:
			PlaneName = PlaneInitial;
			break;
		}
		return PlaneName;
	}

	// TODO getPlaneNameToPlaneInitial : 비행기이름 -> 비행기이니셜
	public String getPlaneNameToPlaneInitial(String PlaneName) {
		String PlaneInitial = "";

		switch (PlaneName) {
		case "아시아나":
			PlaneInitial = "A";
			break;

		case "대한항공":
			PlaneInitial = "D";
			break;

		case "진에어":
			PlaneInitial = "J";
			break;
			
		case "에미레이트":
			PlaneInitial = "E";
			break;
			
		default:
			PlaneInitial = PlaneName;
			break;
		}
		return PlaneInitial;
	}
	
	// TODO getAviationInfo : 항공정보를 가져옴
	public ArrayList<Reservations_DTO> getAviationInfo() {
		ArrayList<Reservations_DTO> result = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.createStatement();
			String sql = "select PlaneNO, PlaneCode, PlaneName, StartTime, ArrivalTime, StartLoc, ArrivalLoc "
					+ "from TB_AVIATIONINFO ";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Reservations_DTO data = new Reservations_DTO();
				data.setPlaneNO(rs.getInt(1));
				data.setPlaneCode(rs.getString(2));
				data.setPlaneName(getPlaneInitialToPlaneName(rs.getString(3)));
				data.setStartTime(rs.getString(4));
				data.setArrivalTime(rs.getString(5));
				data.setStartLoc(rs.getString(6));
				data.setArrivalLoc(rs.getString(7));

				result.add(data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (Exception e) {
			}
		}
		return result;
	}
	
	// TODO getAviationList : 항공정보를 리스트로 출력
	public ArrayList<String> getAviationList() {
		ArrayList<String> arr_str = new ArrayList<>();
		ArrayList<Reservations_DTO> dto = getAviationInfo();

		for (Reservations_DTO re_dto : dto) {
			arr_str.add("[" + re_dto.getStartTime() + " " + re_dto.getStartLoc() + "] ~ "
					+ "[" + re_dto.getArrivalTime() + " " + re_dto.getArrivalLoc() + "] (" + re_dto.getPlaneCode() + ")");
		}

		return arr_str;
	}
	
	// TODO getPlaneCodeToAviationListItem : 항공정보 리스트에 있는 항목에서 비행기코드를 추출
	public String getPlaneCodeToAviationListItem(String aviationListItem) {
		return aviationListItem.substring(aviationListItem.lastIndexOf("(") + 1, aviationListItem.length() - 1);
	}
	
	// TODO getPlaneNoToPlaneCode : 비행기코드를 비행기번호로 변환
	public int getPlaneNoToPlaneCode(String planeCode) {
		int result = 0;
		ArrayList<Reservations_DTO> dto = getAviationInfo();
		
		for (Reservations_DTO re_dto : dto) {
			if (re_dto.getPlaneCode().equals(planeCode)) {
				result = re_dto.getPlaneNO();
				break;
			}
		}
		return result;
	}
	
	// TODO getColumnNames : 테이블에 사용할 컬럼명들
	public String[] getColumnNames() {
		String[] str_columnNames = { "예매번호", "회원번호", "회원명", "비행기번호", "비행기코드", "비행기명",
				"출발날짜/시간", "도착날짜/시간", "출발지", "도착지", "좌석" };
		return str_columnNames;
	}
	
	// TODO getPlaneNames : 비행기이름 목록 반환
	public String[] getPlaneNames() {
		String[] str_planeNames = { "아시아나", "대한항공", "진에어", "에미레이트" };
		return str_planeNames;
	}

	// TODO getSeatInfo : 좌석 좌표 목록 반환
	public String[] getSeatInfo() {
		String[] str_seat = new String[30];
		String[] columns = { "A", "B", "C", "D", "E" };
		int count = 0;

		for (int r = 1; r <= str_seat.length / columns.length; r++) {
			for (int c = 0; c < columns.length; c++) {
				str_seat[count] = r + "-" + columns[c];
				count++;
			}
		}
		return str_seat;
	}
	
	// TODO getDateAndTime : 날짜/시간 문자열을 DTO 형태로 변환
	public Reservations_DTO getDateAndTime(String startDateTime, String arrivalDateTime) {
		Reservations_DTO dto = new Reservations_DTO();
		String str_Div = " / ";
		String str_Date = startDateTime.substring(0, startDateTime.indexOf(str_Div));
		String str_Time = startDateTime.substring(startDateTime.indexOf(str_Div) + str_Div.length(), startDateTime.length());

		dto.setStartDate(Date.valueOf(str_Date));
		dto.setStartTime(str_Time);

		str_Date = arrivalDateTime.substring(0, arrivalDateTime.indexOf(str_Div));
		str_Time = arrivalDateTime.substring(arrivalDateTime.indexOf(str_Div) + str_Div.length(), startDateTime.length());

		dto.setArrivalDate(Date.valueOf(str_Date));
		dto.setArrivalTime(str_Time);

		return dto;
	}
	
	// TODO getDTOto2DObject : DTO를 2차원 배열 형태로
	public Object[][] getDTOto2DObject(ArrayList<Reservations_DTO> arr_dto) {
		int columnSize = getColumnNames().length;
		Object[][] arr_obj = new Object[arr_dto.size()][columnSize];

		for (int i = 0; i < arr_dto.size(); i++) {
			Reservations_DTO dto = arr_dto.get(i);

			arr_obj[i][0] = dto.getListNO();
			arr_obj[i][1] = dto.getMemberNO();
			arr_obj[i][2] = dto.getName();
			arr_obj[i][3] = dto.getPlaneNO();
			arr_obj[i][4] = dto.getPlaneCode();
			arr_obj[i][5] = dto.getPlaneName();
			arr_obj[i][6] = dto.getStartDate() + " / " + dto.getStartTime();
			arr_obj[i][7] = dto.getArrivalDate() + " / " + dto.getArrivalTime();
			arr_obj[i][8] = dto.getStartLoc();
			arr_obj[i][9] = dto.getArrivalLoc();
			arr_obj[i][10] = dto.getSeat();
		}
		return arr_obj;
	}

	// TODO getColumnIndex : 테이블의 컬럼명으로 컬럼 인덱스를 반환
	public int getColumnIndex(String column) {
		ArrayList<String> arr_str = new ArrayList<>();
		for (String str : getColumnNames()) {
			arr_str.add(str);
		}
		return arr_str.indexOf(column);
	}
	
	// TODO getTableSelectedCell : 컬럼명으로 테이블에 선택된 셀을 문자열로 반환
	public String getTableSelectedCell(JTable table, String columnName) {
		return String.valueOf(table.getValueAt(table.getSelectedRow(), getColumnIndex(columnName)));
	}
	
	// TODO setColumnSize : 테이블의 컬럼 사이즈 변경 및 조절 여부
	public void setColumnSize(JTable table, String column, int width, boolean isResize) {
		table.getColumnModel().getColumn(getColumnIndex(column)).setResizable(isResize);
		if (isResize) {
			table.getColumnModel().getColumn(getColumnIndex(column)).setPreferredWidth(width);			
		} else {
			table.getColumnModel().getColumn(getColumnIndex(column)).setWidth(width);
			table.getColumnModel().getColumn(getColumnIndex(column)).setMinWidth(width);
			table.getColumnModel().getColumn(getColumnIndex(column)).setMaxWidth(width);
		}
	}

	// TODO setTableCellCenter : 테이블의 셀 내용 가운데 정렬하기
	public void setTableCellCenter(JTable table) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
	}

	// TODO setTableForm : 테이블의 폼 형식 초기화
	public void setTableForm(JTable table, String account) {
		if (account.equals(Reservations.ACC_USER)) {
			setColumnSize(table, "회원명", 0, false);
		} else {
			setColumnSize(table, "회원명", 80, false);			
		}
		
		setColumnSize(table, "예매번호", 60, false);
		setColumnSize(table, "회원번호", 0, false);
		setColumnSize(table, "비행기번호", 0, false);
		
		setColumnSize(table, "비행기코드", 80, false);
		setColumnSize(table, "출발지", 60, false);
		setColumnSize(table, "도착지", 60, false);
		setColumnSize(table, "비행기명", 80, false);
		setColumnSize(table, "좌석", 40, false);

		setTableCellCenter(table);
	}
	
	// TODO showSearchResult : 검색결과를 테이블에 표시
	public void showSearchResult(ArrayList<Reservations_DTO> arr_dto, JTable table, String account) {
		Object[][] obj = getDTOto2DObject(arr_dto);

		@SuppressWarnings("serial")
		DefaultTableModel tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 셀 편집 불가능
			}
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// 0번째 컬럼은 정수형으로 정렬, 나머지는 문자열 정렬
				switch (columnIndex) {
				case 0:
					return Integer.class;

				default:
					return String.class;
				}
			}
		};
		tableModel.setDataVector(obj, getColumnNames());
		table.setModel(tableModel);
		setTableForm(table, account);
	}
	
	// TODO setTableSort : 테이블 컬럼별 정렬기능
	public void setTableSort(JTable table) {
		table.setAutoCreateRowSorter(true);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
	}
	
}
