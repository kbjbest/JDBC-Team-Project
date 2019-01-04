package east_aviationinfo;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBConnection;
import east_reservation.Reservations_DAO;

@SuppressWarnings("serial")
public class Insert extends JDialog implements ActionListener {
	JPanel paNorth, paSouth;
	JPanel p1, p2, p3, p4, p5, p6;
	JTextField tfPlaneCode, tfStartTime, tfArrivalTime, tfStartLoc, tfArrivalLoc;
	JButton btOK, btCancel;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmtInsert;
	String sqlInsert = "insert into tb_aviationinfo values(aviationinfo_seq.nextval, ?, ?, ?, ?, ?, ?)";
	String adje;
	JComboBox<String> cbPlaneName;
	Reservations_DAO dao = new Reservations_DAO();

	public Insert(JFrame frame) {
		super(frame, "운행정보 추가", true);
		paNorth = new JPanel(new GridLayout(6, 0));
		cbPlaneName = new JComboBox<>(dao.getPlaneNames());
		cbPlaneName.setPreferredSize(new Dimension(148, 25));
		
		p2 = new JPanel();
		JLabel lb_PlaneName = new JLabel("비행기명 : ", JLabel.RIGHT);
		lb_PlaneName.setPreferredSize(new Dimension(70, 25));
		p2.add(lb_PlaneName);
		p2.add(cbPlaneName);
		paNorth.add(p2);

		p3 = new JPanel();
		JLabel lb_StartTime = new JLabel("출발시간 : ", JLabel.RIGHT);
		lb_StartTime.setPreferredSize(new Dimension(70, 25));
		p3.add(lb_StartTime);
		p3.add(tfStartTime = new JTextField(12));
		paNorth.add(p3);

		p4 = new JPanel();
		JLabel lb_ArrivalTime = new JLabel("도착시간 : ", JLabel.RIGHT);
		lb_ArrivalTime.setPreferredSize(new Dimension(70, 25));
		p4.add(lb_ArrivalTime);
		p4.add(tfArrivalTime = new JTextField(12));
		paNorth.add(p4);

		p5 = new JPanel();
		JLabel lb_StartLoc = new JLabel("출발지 : ", JLabel.RIGHT);
		lb_StartLoc.setPreferredSize(new Dimension(70, 25));
		p5.add(lb_StartLoc);
		p5.add(tfStartLoc = new JTextField(12));
		paNorth.add(p5);

		p6 = new JPanel();
		JLabel lb_ArrivalLoc = new JLabel("도착지 : ", JLabel.RIGHT);
		lb_ArrivalLoc.setPreferredSize(new Dimension(70, 25));
		p6.add(lb_ArrivalLoc);
		p6.add(tfArrivalLoc = new JTextField(12));
		paNorth.add(p6);

		paSouth = new JPanel();
		paSouth.add(btOK = new JButton("확인"));
		paSouth.add(btCancel = new JButton("닫기"));
		
		
		
		add(paNorth, "North");
		add(paSouth, "South");

		btOK.addActionListener(this);
		btCancel.addActionListener(this);
		
		setResizable(false);
		setSize(300, 280);
		setLocationRelativeTo(frame);;
		setVisible(true);
		// dbConnect();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == btOK) {
			if (cbPlaneName.getSelectedItem().toString().equals("아시아나")) {
				adje = "A";
			} else if (cbPlaneName.getSelectedItem().toString().equals("대한항공")) {
				adje = "D";
			} else if (cbPlaneName.getSelectedItem().toString().equals("진에어")) {
				adje = "J";
			} else if (cbPlaneName.getSelectedItem().toString().equals("에미레이트")) {
				adje = "E";
			} 
			insert();
			dispose();
			AviationView.setTableData(1);

		} else if (ae.getSource() == btCancel) {
			dispose();
		}
	}

	public void insert() {// 추가
		try {
			Connection con = DBConnection.getConnection();
			stmt = con.createStatement();
			pstmtInsert = con.prepareStatement(sqlInsert);
			String strPlaneCode = adje + tfStartTime.getText().replace(":", "")+ tfArrivalTime.getText().replace(":", "");
			String strPlaneName = adje;
			String strStartDate = tfStartTime.getText();
			String strArrivalDate = tfArrivalTime.getText();
			String strStartLoc = tfStartLoc.getText();
			String strArrivalLoc = tfArrivalLoc.getText();
			if (strPlaneCode.length() < 1 || strPlaneName.length() < 1 || strStartDate.length() < 1
					|| strArrivalDate.length() < 1 || strStartLoc.length() < 1 || strArrivalLoc.length() < 1) {
				JOptionPane.showMessageDialog(null, "모든 항목을 다 입력하십시오.");
				return;
			} else {
				pstmtInsert.setString(1, strPlaneCode);
				pstmtInsert.setString(2, strPlaneName);
				pstmtInsert.setString(3, strStartDate);
				pstmtInsert.setString(4, strArrivalDate);
				pstmtInsert.setString(5, strStartLoc);
				pstmtInsert.setString(6, strArrivalLoc);
				pstmtInsert.executeUpdate();
				JOptionPane.showMessageDialog(null, "추가 성공");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}