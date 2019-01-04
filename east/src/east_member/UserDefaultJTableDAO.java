package east_member;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class UserDefaultJTableDAO {
	
	
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public UserDefaultJTableDAO() {
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager
					.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
							"east", "1234");

		} catch (ClassNotFoundException e) {
			System.out.println(e + "=> 로드 fail");
		} catch (SQLException e) {
			System.out.println(e + "=> 연결 fail");
		}
	} 

	
	public void dbClose() {
		try {
			if (rs != null) rs.close();
			if (st != null)	st.close();
			if (ps != null)	ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}

	
	public boolean getMembernoByCheck(String memberno) {
		boolean result = true;

		try {
			ps = con.prepareStatement("SELECT * FROM TB_MEMBER WHERE memberno=? order by memberNO");
			ps.setString(1, memberno.trim());
			rs = ps.executeQuery();
			if (rs.next())
				result = false;

		} catch (SQLException e) {
			System.out.println(e + "=>  getmembernoByCheck fail");
		} finally {
			dbClose();
		}

		return result;

	}
	public void userSelectAll(DefaultTableModel t_model) {
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from TB_MEMBER where id != 'admin' order by memberNO desc");

			
			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6)
						,rs.getString(7)};

				t_model.addRow(data); 
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelectAll fail");
		} finally {
			dbClose();
		}
	}

	public int userDelete(String memberno) {
		int result = 0;
		
		try {
			ps = con.prepareStatement("delete TB_MEMBER where memberno = ? ");
			ps.setString(1, memberno.trim());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> userDelete fail");
		}finally {
			
			dbClose();
		}

		return result;
	}
	

	public int userUpdate(UserJDailogGUI user) {
		int result = 0;
		String sql = "UPDATE TB_MEMBER SET pw=?, name=?, address=?,email=?,phone=? "
				+ "WHERE memberno=?";

		String phone = (user.phone1.getText().equals("") ?
				"" : user.phone1.getText() + "-"+ user.phone2.getText() + "-" + user.phone3.getText());
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1, user.pwd.getText());
			ps.setString(2, user.name.getText().trim());
			ps.setString(3, user.address.getText().trim());
			ps.setString(4, user.email.getText().trim());
			ps.setString(5, phone);
			ps.setString(6, user.memberno.getText().trim());
			System.out.println(user.phone1.getText() + user.phone2.getText() + user.phone3.getText());

		result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}

	/**
	 * 검색단어에 해당하는 레코드 검색하기 (like연산자를 사용하여 _, %를 사용할때는 PreparedStatemnet안된다. 반드시
	 * Statement객체를 이용함)
	 * */
	public void getUserSearch(DefaultTableModel dt, String fieldName, String word) {
		String sql = "SELECT * FROM TB_MEMBER WHERE " + getColumnName(fieldName.trim())
						+ " LIKE '%" + word.trim() + "%' "
						+ "order by memberNO";

		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);

			
			for (int i = 0; i < dt.getRowCount();) {
				dt.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6)
						,rs.getString(7) };

				dt.addRow(data);
			}

		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbClose();
		}

	}
	
	public String getColumnName(String fieldName) {
		String columnName;
		switch (fieldName) {
		case "회원번호":
			columnName = "memberNO";
			break;

		case "아이디":
			columnName = "id";
			break;
			
		case "비밀번호":
			columnName = "pw";
			break;
			
		case "이름":
			columnName = "name";
			break;
			
		case "주소":
			columnName = "address";
			break;
			
		case "이메일":
			columnName = "email";
			break;
			
		case "전화번호":
			columnName = "phone";
			break;
			
		default:
			columnName = "";
			break;
		}
		return columnName;
	}

}
