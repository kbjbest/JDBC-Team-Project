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
public class Update extends JDialog implements ActionListener {
	JPanel paNorth, paSouth;
	JPanel p1, p2, p3, p4, p5, p6;
	JTextField tfPlaneCode, tfStartTime, tfArrivalTime, tfStartLoc, tfArrivalLoc;
	JButton btOK, btCancel;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmtUpdate;
	String sqlUpdate = "update tb_aviationinfo set planecode=?, planename=?, "
			+ "starttime=?, arrivaltime=?, startloc=?, arrivalloc=? where planeno=?";
	String adje;
	TB_AviationInfoDTO dto = new TB_AviationInfoDTO();
	JComboBox<String> cbPlaneName;
	Reservations_DAO dao = new Reservations_DAO();

	public Update(JFrame frame, TB_AviationInfoDTO dto) {
		super(frame, "운행정보 수정", true);
		this.dto = dto;
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
				
		cbPlaneName.setSelectedItem((AviationView.strPlaneName));
		tfStartTime.setText(AviationView.strStartTime);
		tfArrivalTime.setText(AviationView.strArrivalTime);
		tfStartLoc.setText(AviationView.strStartLoc);
		tfArrivalLoc.setText(AviationView.strArrivalLoc);
		
		btOK.addActionListener(this);
		btCancel.addActionListener(this);
		
		setResizable(false);
		setSize(300, 280);
		setLocationRelativeTo(frame);
		setVisible(true);
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
			update(dto);
			AviationView.setTableData(1);
		} else if (ae.getSource() == btCancel) {
			dispose();
		}
	}

	public void update(TB_AviationInfoDTO dto) {// 추가
		try {
			Connection con = DBConnection.getConnection();
			stmt = con.createStatement();
			pstmtUpdate = con.prepareStatement(sqlUpdate);
			String strPlaneCode = adje + tfStartTime.getText().replace(":", "")+ tfArrivalTime.getText().replace(":", "");
			String strPlaneName = (String) cbPlaneName.getSelectedItem();
			String strStartTime = tfStartTime.getText();
			String strArrivalTime = tfArrivalTime.getText();
			String strStartLoc = tfStartLoc.getText();
			String strArrivalLoc = tfArrivalLoc.getText();
			String strPlaneNo = String.valueOf(AviationView.iPlaneNo);
			System.out
					.println(strPlaneCode + strPlaneName + strStartTime + strArrivalTime + strStartLoc + strArrivalLoc);
			if (strPlaneCode.length() < 1 || strPlaneName.length() < 1 || strStartTime.length() < 1
					|| strArrivalTime.length() < 1 || strStartLoc.length() < 1 || strArrivalLoc.length() < 1) {
				JOptionPane.showMessageDialog(null, "모든 항목을 다 입력하십시오.");
				return;
			} else {
				pstmtUpdate.setString(1, strPlaneCode);
				pstmtUpdate.setString(2, strPlaneName);
				pstmtUpdate.setString(3, strStartTime);
				pstmtUpdate.setString(4, strArrivalTime);
				pstmtUpdate.setString(5, strStartLoc);
				pstmtUpdate.setString(6, strArrivalLoc);
				pstmtUpdate.setString(7, strPlaneNo);
				pstmtUpdate.executeUpdate();
				JOptionPane.showMessageDialog(null, "수정 성공");
				dispose();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}