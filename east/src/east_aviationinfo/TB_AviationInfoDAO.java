package east_aviationinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import east_reservation.Reservations_DAO;

public class TB_AviationInfoDAO {
	Connection con = DBConnection.getConnection();
	Statement st;
	PreparedStatement psmt;
	ResultSet rs;
	Reservations_DAO rd = new Reservations_DAO();

	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
		} catch (Exception e) {
		}
	}

	public int insert(TB_AviationInfoDTO dto) {
		int affectedRow = 0;
		String sql = "insert into tb_aviationinfo values(aviationinfo_seq.nextval, ?, ?, ?, ?, ?, ?)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, dto.getPlaneNo());
			psmt.setString(2, dto.getPlaneCode());
			psmt.setString(3, dto.getPlaneName());
			psmt.setString(4, dto.getStartTime());
			psmt.setString(5, dto.getArrivalTime());
			psmt.setString(6, dto.getStartLoc());
			;
			psmt.setString(7, dto.getArrivalLoc());
			affectedRow = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("입력시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	public List<TB_AviationInfoDTO> getRecordAll() {
		List<TB_AviationInfoDTO> list = null;
		try {
			list = new Vector<TB_AviationInfoDTO>();
			String sql = "select * from tb_aviationinfo order by planeno desc";
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				TB_AviationInfoDTO dto = new TB_AviationInfoDTO(rs.getInt(1), rs.getString(2),
						rd.getPlaneInitialToPlaneName(rs.getString(3)), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("전체 레코드 반환시 오류:" + e.getMessage());
		}
		return list;
	}

	public int update(TB_AviationInfoDTO dto) {
		int affectedRow = 0;
		String sql = "update tb_aviationinfo set planecode=?, planename=?, "
				+ "starttime=?, arrivaltime=?, startloc=?, arrivalloc=? where planeno=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getPlaneCode());
			psmt.setString(2, dto.getPlaneName());
			psmt.setString(3, dto.getStartTime());
			;
			psmt.setString(4, dto.getArrivalTime());
			psmt.setString(5, dto.getStartLoc());
			psmt.setString(6, dto.getArrivalLoc());
			psmt.setInt(7, dto.getPlaneNo());
			affectedRow = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("수정시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	public int delete(int iPlaneNo) {
		int affectedRow = 0;
		String sql = "delete tb_aviationinfo where planeno=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, iPlaneNo);
			affectedRow = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("삭제시 오류:" + e.getMessage());
		}
		return affectedRow;
	}

	public void getUserSearch(DefaultTableModel dt, String fieldName, String word) {
		if (fieldName == "비행기코드") {
			fieldName = "planecode";
		} else if (fieldName == "비행기명") {
			fieldName = "planename";
		} else if (fieldName == "출발시간") {
			fieldName = "starttime";
		} else if (fieldName == "도착시간") {
			fieldName = "arrivaltime";
		} else if (fieldName == "출발지") {
			fieldName = "startloc";
		} else if (fieldName == "도착지") {
			fieldName = "arrivalloc";
		}

		String sql = "";

		if (fieldName.equals("planename")) {
			sql = "SELECT * FROM tb_aviationinfo WHERE ";
			ArrayList<String> arrStr = new ArrayList<>();

			for (String str_planeName : rd.getPlaneNames()) {
				if (str_planeName.contains(word)) {
					arrStr.add(fieldName.trim() + " LIKE '%" + rd.getPlaneNameToPlaneInitial(str_planeName) + "%'");
				}
			}

			for (int i = 0; i < arrStr.size(); i++) {
				sql += arrStr.get(i);
				if (i != arrStr.size() - 1) {
					sql += " or ";
				}
			}

			if (arrStr.size() == 0) {
				sql = "SELECT * FROM tb_aviationinfo WHERE " + fieldName.trim() + " LIKE '%" + word.trim() + "%'";
			}

		} else {
			sql = "SELECT * FROM tb_aviationinfo WHERE " + fieldName.trim() + " LIKE '%" + word.trim() + "%'";
		}

		sql += " order by planeno desc";

		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);

			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < dt.getRowCount();) {
				dt.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getInt(1), rs.getString(2), rd.getPlaneInitialToPlaneName(rs.getString(3)),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7) };

				dt.addRow(data);
			}

		} catch (SQLException e) {
			System.out.println("");
		}

	}

}