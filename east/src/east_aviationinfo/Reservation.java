package east_aviationinfo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import east_reservation.Reservations_DAO;
import east_view.Member_DTO;

@SuppressWarnings("serial")
public class Reservation extends JDialog implements ActionListener {
	JPanel paCenter, paSouth;
	JPanel panel;
	JButton btOK, btCancel;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt, pstmtInsert;
	ResultSet rs;
	private String sqlInsert = "insert into tb_reservations values(reservations_seq.nextval, ?, ?, ?, ?, ?, 'n')";
	private String sqlSelect = "select memberno from tb_member where id = ?";
	String strMN;
	String strStartDate;
	String strId, strName;

	public Reservation(JFrame frame) {
		super(frame, true);
		dtm = new DefaultTableModel();
		table = new JTable(dtm);
		jsp = new JScrollPane(table);
		

		panel = new JPanel(new BorderLayout(5, 5));
		
		paSouth = new JPanel();
		paSouth.add(btOK = new JButton("확인"));
		paSouth.add(btCancel = new JButton("닫기"));
		
		paCenter = new JPanel();
		paCenter.add(jsp);
		
		panel.add(paCenter, "Center");
		panel.add(paSouth, "South");

		getContentPane().add(panel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() == -1){
					return;
				} else {
					strId = ((String) table.getValueAt(table.getSelectedRow(), 0));
					strName = ((String) table.getValueAt(table.getSelectedRow(), 1));
				}
			}
		});
		
		btOK.addActionListener(this);
		btCancel.addActionListener(this);
		
		setTableData();
		
		setBounds(600, 300, 500, 500);
		setVisible(true);
		
	}

	public void setTableData() {

		List<Member_DTO> list = null;
		list = getUserIdName();

		// 2]컬렉션에 저장된 모든 레코드를 model에 저장
		Object[] columnNames = { "ID", "이름" };
		Object rowData[][] = new Object[list.size()][columnNames.length];

		for (int i = 0; i < list.size(); i++) {
			Member_DTO dto = list.get(i);
			rowData[i][0] = dto.getId();
			rowData[i][1] = dto.getName();
		}
		dtm.setDataVector(rowData, columnNames);
		// 3]model을 table에 연결:model저장된 데이타가 table에 뿌려짐
		table.setModel(dtm);
		Reservations_DAO reser_dao = new Reservations_DAO();
		reser_dao.setTableCellCenter(table);
	}

	public List<Member_DTO> getUserIdName() {
		List<Member_DTO> list = null;
		try {
			con = DBConnection.getConnection();
			list = new Vector<Member_DTO>();
			String sql = "select id, name from tb_member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				Member_DTO dto = new Member_DTO();
				dto.setId(rs.getString(1));
				dto.setName(rs.getString(2));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("전체 레코드 반환시 오류:" + e.getMessage());
		}
		return list;
	}
	
	public void reservation() {// 예매
		try {
			
			Connection con = DBConnection.getConnection();
			pstmtInsert = con.prepareStatement(sqlInsert);
			pstmt = con.prepareStatement(sqlSelect);
			pstmt.setString(1, strId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				strMN = rs.getString("memberno");
			}
			String strMemberNo = strMN;
			String strPlaneNo =  String.valueOf(AviationView.iPlaneNo);
			String strSeat = String.valueOf(AviationView.cbSeat.getSelectedItem());
			String strStartDate = String.valueOf(AviationView.ccb.getDate());
			String strArrivalDate = String.valueOf(AviationView.ccb.getDate());
			System.out.println(strMemberNo + strPlaneNo + strSeat + strStartDate + strArrivalDate);
			
			pstmtInsert.setString(1, strMemberNo);
			pstmtInsert.setString(2, strPlaneNo);
			pstmtInsert.setString(3, strSeat);
			pstmtInsert.setString(4, strStartDate);
			pstmtInsert.setString(5, strArrivalDate);
			pstmtInsert.executeUpdate();
			JOptionPane.showMessageDialog(null, "예매 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == btOK) {
			if (table.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(null, "회원을 선택하십시오.");
			else {
				reservation();
				dispose();
			}
		} else if (ae.getSource() == btCancel) {
			dispose();
		}
	}
}