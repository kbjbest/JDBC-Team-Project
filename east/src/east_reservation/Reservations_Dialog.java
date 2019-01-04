package east_reservation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import east_view.Member_DTO;
import etc.CalendarComboBox;
import etc.MyFunctions;

@SuppressWarnings("serial")
public class Reservations_Dialog extends JDialog implements ActionListener, KeyListener, ItemListener {

	public final static int SEARCH = 1, MODIFY = 2;

	private JPanel pan_Center, pan_South;
	private JPanel pan_Member_Group, pan_Plane_Group;
	private JPanel pan_Plane, pan_Date, pan_Date2, pan_Location, pan_Button, pan_Result, pan_AviationInfo;
	private JTextField jtf_MemberNO, jtf_Name, jtf_StartLoc, jtf_ArrivalLoc;
	private JComboBox<String> jcb_PlaneName, jcb_Seat, jcb_AviationInfo;
	private CalendarComboBox ccb_startDate, ccb_arrivalDate;
	private JButton btn_OK, btn_Cancel;
	private JLabel lbl_MemberNO, lbl_Name, lbl_PlaneName, lbl_Seat, lbl_Date, lbl_Date2,
					lbl_StartLoc, lbl_ArrivalLoc, lbl_AviationInfo;
	private Calendar date;
	private JCheckBox jck_AllDate;

	private JTable mainTable;
	private int check_DialogMode;
	private String isReservation;
	private String account;
	private Member_DTO dto_userInfo;
	private Reservations_DAO dao;
	
	public Reservations_Dialog(JFrame frame, JTable table, int dialogMode, String isReservation, String account, Member_DTO dto_userInfo) {
		// TODO Reservation_Admin_Dialog 생성자
		super(frame, true);
		mainTable = table;
		check_DialogMode = dialogMode;
		this.isReservation = isReservation;
		this.account = account;
		this.dto_userInfo = dto_userInfo;
		dao = new Reservations_DAO();
		
		setLayout(new BorderLayout());
		setSize(470, 380);
		setResizable(false);

		pan_Center = new JPanel();
		pan_Center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pan_South = new JPanel(new BorderLayout());
		
		switch (dialogMode) {
		case SEARCH:
			// TODO 검색모드
			setTitle("검색");
			
			// 회원정보 Panel(그룹)
			pan_Member_Group = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pan_Member_Group.setBorder(BorderFactory.createTitledBorder("회원 정보"));
			pan_Member_Group.setPreferredSize(new Dimension(430, 65));

//			lbl_MemberNO = new JLabel("회원번호 :", JLabel.CENTER);
//			lbl_MemberNO.setPreferredSize(new Dimension(80, 25));
//			jtf_MemberNO = new JTextField();
//			jtf_MemberNO.setPreferredSize(new Dimension(100, 25));
//			jtf_MemberNO.addKeyListener(this);

			lbl_Name = new JLabel("회원명 :", JLabel.CENTER);
			lbl_Name.setPreferredSize(new Dimension(80, 25));
			jtf_Name = new JTextField();
			jtf_Name.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			jtf_Name.setPreferredSize(new Dimension(100, 25));
			jtf_Name.addKeyListener(this);

//			pan_Member_Group.add(lbl_MemberNO);
//			pan_Member_Group.add(jtf_MemberNO);
			pan_Member_Group.add(lbl_Name);
			pan_Member_Group.add(jtf_Name);
			
			// 운행정보 Panel(그룹)
			pan_Plane_Group = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pan_Plane_Group.setBorder(BorderFactory.createTitledBorder("운행 정보"));
			pan_Plane_Group.setPreferredSize(new Dimension(430, 220));

			// 비행기명, 좌석 Panel
			pan_Plane = new JPanel(new FlowLayout(FlowLayout.LEFT));

			lbl_PlaneName = new JLabel("비행기명 :", JLabel.CENTER);
			lbl_PlaneName.setPreferredSize(new Dimension(80, 25));
			jcb_PlaneName = new JComboBox<>();
			jcb_PlaneName.setPreferredSize(new Dimension(100, 25));
			jcb_PlaneName.addItem("모두 선택");
			for (String planeName : dao.getPlaneNames()) {
				jcb_PlaneName.addItem(planeName);
			}

			lbl_Seat = new JLabel("좌석 :", JLabel.CENTER);
			lbl_Seat.setPreferredSize(new Dimension(80, 25));
			jcb_Seat = new JComboBox<>();
			jcb_Seat.setPreferredSize(new Dimension(100, 25));
			jcb_Seat.addItem("모두 선택");
			for (String seat : dao.getSeatInfo()) {
				jcb_Seat.addItem(seat);
			}

			pan_Plane.add(lbl_PlaneName);
			pan_Plane.add(jcb_PlaneName);
			pan_Plane.add(lbl_Seat);
			pan_Plane.add(jcb_Seat);

			// 출발날짜 Panel
			pan_Date = new JPanel(new FlowLayout(FlowLayout.LEFT));

			lbl_Date = new JLabel("날짜 :", JLabel.CENTER);
			lbl_Date.setPreferredSize(new Dimension(80, 25));

			date = Calendar.getInstance();

			ccb_startDate = new CalendarComboBox();
			ccb_startDate.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1);
			ccb_startDate.setYearRange(date.get(Calendar.YEAR) - 3, date.get(Calendar.YEAR) + 3);
			ccb_startDate.cb_Year.setSelectedItem(date.get(Calendar.YEAR));
			ccb_startDate.cb_Day.setSelectedItem(date.get(Calendar.DATE));
			ccb_startDate.setEnabled(false);

			pan_Date.add(lbl_Date);
			pan_Date.add(ccb_startDate);
			pan_Date.add(new JLabel("부터"));

			// 도착날짜 Panel
			pan_Date2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

			lbl_Date2 = new JLabel("", JLabel.CENTER);
			lbl_Date2.setPreferredSize(new Dimension(80, 25));

			ccb_arrivalDate = new CalendarComboBox();
			ccb_arrivalDate.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
					date.getActualMaximum(Calendar.DATE));
			ccb_arrivalDate.setYearRange(date.get(Calendar.YEAR) - 3, date.get(Calendar.YEAR) + 3);
			ccb_arrivalDate.cb_Year.setSelectedItem(date.get(Calendar.YEAR));
			ccb_arrivalDate.cb_Day.setSelectedItem(date.getMaximum(Calendar.DATE));
			ccb_arrivalDate.setEnabled(false);

			jck_AllDate = new JCheckBox("모든 기간");
			jck_AllDate.setSelected(true);
			jck_AllDate.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent ce) {
					JCheckBox jck = (JCheckBox) ce.getSource();
					ccb_startDate.setEnabled(!jck.isSelected());
					ccb_arrivalDate.setEnabled(!jck.isSelected());
				}
			});
			
			pan_Date2.add(lbl_Date2);
			pan_Date2.add(ccb_arrivalDate);
			pan_Date2.add(new JLabel("까지 "));
			pan_Date2.add(jck_AllDate);

			// 출발지, 도착지 Panel
			pan_Location = new JPanel(new FlowLayout(FlowLayout.LEFT));

			lbl_StartLoc = new JLabel("출발지 :", JLabel.CENTER);
			lbl_StartLoc.setPreferredSize(new Dimension(80, 25));

			jtf_StartLoc = new JTextField();
			jtf_StartLoc.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			jtf_StartLoc.setPreferredSize(new Dimension(100, 25));
			jtf_StartLoc.addKeyListener(this);

			lbl_ArrivalLoc = new JLabel("도착지 :", JLabel.CENTER);
			lbl_ArrivalLoc.setPreferredSize(new Dimension(80, 25));

			jtf_ArrivalLoc = new JTextField();
			jtf_ArrivalLoc.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			jtf_ArrivalLoc.setPreferredSize(new Dimension(100, 25));
			jtf_ArrivalLoc.addKeyListener(this);

			pan_Location.add(lbl_StartLoc);
			pan_Location.add(jtf_StartLoc);
			pan_Location.add(lbl_ArrivalLoc);
			pan_Location.add(jtf_ArrivalLoc);

			pan_Plane_Group.add(pan_Plane);
			pan_Plane_Group.add(MyFunctions.spacePanel(400, 5, getBackground()));
			pan_Plane_Group.add(pan_Date);
			pan_Plane_Group.add(pan_Date2);
			pan_Plane_Group.add(MyFunctions.spacePanel(400, 5, getBackground()));
			pan_Plane_Group.add(pan_Location);

			// 버튼 Panel
			pan_Button = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			btn_OK = new JButton("검색");
			btn_Cancel = new JButton("닫기");
			pan_Button.add(btn_OK);
			pan_Button.add(btn_Cancel);

			// 결과 메시지 Panel
			pan_Result = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

			pan_Center.add(pan_Member_Group);				
			pan_Center.add(pan_Plane_Group);

			pan_South.add(pan_Result, BorderLayout.CENTER);
			pan_South.add(pan_Button, BorderLayout.EAST);

			add(pan_Center, BorderLayout.CENTER);
			add(pan_South, BorderLayout.SOUTH);

			btn_OK.addActionListener(this);
			btn_Cancel.addActionListener(this);

			break;

		case MODIFY:
			// TODO 수정모드
			setTitle("수정");
			
			// 회원정보 Panel(그룹)
			pan_Member_Group = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pan_Member_Group.setBorder(BorderFactory.createTitledBorder("회원 정보"));
			pan_Member_Group.setPreferredSize(new Dimension(430, 65));

			lbl_MemberNO = new JLabel("회원번호 :", JLabel.CENTER);
			lbl_MemberNO.setPreferredSize(new Dimension(80, 25));
			jtf_MemberNO = new JTextField();
			jtf_MemberNO.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			jtf_MemberNO.setPreferredSize(new Dimension(100, 25));
			jtf_MemberNO.addKeyListener(this);
			jtf_MemberNO.setText(dao.getTableSelectedCell(table, "회원번호"));
			jtf_MemberNO.setEditable(false);

			lbl_Name = new JLabel("회원명 :", JLabel.CENTER);
			lbl_Name.setPreferredSize(new Dimension(80, 25));
			jtf_Name = new JTextField();
			jtf_Name.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			jtf_Name.setPreferredSize(new Dimension(100, 25));
			jtf_Name.addKeyListener(this);
			jtf_Name.setText(dao.getTableSelectedCell(table, "회원명"));
			jtf_Name.setEditable(false);

			pan_Member_Group.add(lbl_MemberNO);
			pan_Member_Group.add(jtf_MemberNO);
			pan_Member_Group.add(lbl_Name);
			pan_Member_Group.add(jtf_Name);

			// 운행정보 Panel(그룹)
			pan_Plane_Group = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pan_Plane_Group.setBorder(BorderFactory.createTitledBorder("운행 정보"));
			pan_Plane_Group.setPreferredSize(new Dimension(430, 220));

			// 비행기명, 좌석 Panel
			pan_Plane = new JPanel(new FlowLayout(FlowLayout.LEFT));

			lbl_PlaneName = new JLabel("비행기명 :", JLabel.CENTER);
			lbl_PlaneName.setPreferredSize(new Dimension(80, 25));
			jcb_PlaneName = new JComboBox<>();
			jcb_PlaneName.setPreferredSize(new Dimension(100, 25));
			for (String planeName : dao.getPlaneNames()) {
				jcb_PlaneName.addItem(planeName);
			}
			jcb_PlaneName.setSelectedItem(dao.getTableSelectedCell(table, "비행기명"));

			lbl_Seat = new JLabel("좌석 :", JLabel.CENTER);
			lbl_Seat.setPreferredSize(new Dimension(80, 25));
			jcb_Seat = new JComboBox<>();
			jcb_Seat.setPreferredSize(new Dimension(100, 25));
			for (String seat : dao.getSeatInfo()) {
				jcb_Seat.addItem(seat);
			}
			jcb_Seat.setSelectedItem(dao.getTableSelectedCell(table, "좌석"));

			pan_Plane.add(lbl_PlaneName);
			pan_Plane.add(jcb_PlaneName);
			pan_Plane.add(lbl_Seat);
			pan_Plane.add(jcb_Seat);

			// 출발날짜와 도착날짜를 DTO로 변환
			Reservations_DTO dto = new Reservations_DTO();
			dto = dao.getDateAndTime(
					dao.getTableSelectedCell(table, "출발날짜/시간"),
					dao.getTableSelectedCell(table, "도착날짜/시간"));
			date = Calendar.getInstance();
			
			// 출발날짜 Panel
			pan_Date = new JPanel(new FlowLayout(FlowLayout.LEFT));

			lbl_Date = new JLabel("날짜 :", JLabel.CENTER);
			lbl_Date.setPreferredSize(new Dimension(80, 25));

			date.setTime(dto.getStartDate());
			
			ccb_startDate = new CalendarComboBox();
			ccb_startDate.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));

			pan_Date.add(lbl_Date);
			pan_Date.add(ccb_startDate);
			pan_Date.add(new JLabel("부터"));

			// 도착날짜 Panel
			pan_Date2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

			lbl_Date2 = new JLabel("", JLabel.CENTER);
			lbl_Date2.setPreferredSize(new Dimension(80, 25));

			date.setTime(dto.getArrivalDate());
			
			ccb_arrivalDate = new CalendarComboBox();
			ccb_arrivalDate.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));

			pan_Date2.add(lbl_Date2);
			pan_Date2.add(ccb_arrivalDate);
			pan_Date2.add(new JLabel("까지"));
			
			ccb_arrivalDate.setEnabled(false);

			// 운행정보 Panel
			pan_AviationInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			lbl_AviationInfo = new JLabel("운행 정보 :");
			lbl_AviationInfo.setPreferredSize(new Dimension(80, 25));
			jcb_AviationInfo = new JComboBox<>();
			
			for ( String str_Info : dao.getAviationList() ) {
				if (str_Info.contains(dao.getPlaneNameToPlaneInitial( dao.getTableSelectedCell(table, "비행기명") ))) {
					jcb_AviationInfo.addItem(str_Info);					
				}
			}
			
			for (int i = 0; i < jcb_AviationInfo.getItemCount(); i++) {
				if (jcb_AviationInfo.getItemAt(i).contains(dao.getTableSelectedCell(table, "비행기코드"))) {
					jcb_AviationInfo.setSelectedIndex(i);
					break;
				}
				
			}
			
			pan_AviationInfo.add(lbl_AviationInfo);
			pan_AviationInfo.add(jcb_AviationInfo);

			pan_Plane_Group.add(pan_Plane);
			pan_Plane_Group.add(MyFunctions.spacePanel(400, 5, getBackground()));
			pan_Plane_Group.add(pan_Date);
			pan_Plane_Group.add(pan_Date2);
			pan_Plane_Group.add(MyFunctions.spacePanel(400, 5, getBackground()));
			pan_Plane_Group.add(pan_AviationInfo);

			// 버튼 Panel
			pan_Button = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			
			btn_OK = new JButton("수정");
			btn_Cancel = new JButton("닫기");
			pan_Button.add(btn_OK);
			pan_Button.add(btn_Cancel);

			// 결과 메시지 Panel
			pan_Result = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

			pan_Center.add(pan_Member_Group);
			pan_Center.add(pan_Plane_Group);

			pan_South.add(pan_Result, BorderLayout.CENTER);
			pan_South.add(pan_Button, BorderLayout.EAST);

			add(pan_Center, BorderLayout.CENTER);
			add(pan_South, BorderLayout.SOUTH);

			btn_OK.addActionListener(this);
			btn_Cancel.addActionListener(this);
			
			jcb_PlaneName.addItemListener(this);
			
			break;
		}

		if (account.equals(Reservations.ACC_USER)) {
			pan_Member_Group.setVisible(false);
			setSize(470, 310);
		}
		
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	// 검색
	public void runSearch() {
		Reservations_DTO dto_search = new Reservations_DTO();

//		if (!jtf_MemberNO.getText().equals("")) {
//			dto_search.setMemberNO(Integer.parseInt(jtf_MemberNO.getText()));
//		}
		
		// 유저일 때
		if (account.equals(Reservations.ACC_USER)) {
			jtf_Name.setText(dto_userInfo.getName());
		}
		
		if (!jtf_Name.getText().equals("")) {
			dto_search.setName(jtf_Name.getText());
		}					
		
		if (jcb_PlaneName.getSelectedIndex() != 0) {
			dto_search.setPlaneName(jcb_PlaneName.getSelectedItem().toString());
		}
		if (jcb_Seat.getSelectedIndex() != 0) {
			dto_search.setSeat(jcb_Seat.getSelectedItem().toString());
		}
		if (!jck_AllDate.isSelected() && ccb_startDate.getDate().getTime() > ccb_arrivalDate.getDate().getTime()) {
			JOptionPane.showMessageDialog(this, "도착날짜가 출발날짜보다 전일 수 없습니다.", "날짜 오류", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			if (!jck_AllDate.isSelected()) {
				dto_search.setStartDate(ccb_startDate.getDate());
				dto_search.setArrivalDate(ccb_arrivalDate.getDate());
			}
		}
		if (!jtf_StartLoc.getText().equals("")) {
			dto_search.setStartLoc(jtf_StartLoc.getText());
		}
		if (!jtf_ArrivalLoc.getText().equals("")) {
			dto_search.setArrivalLoc(jtf_ArrivalLoc.getText());
		}
		if (isReservation.equals(Reservations.MODE_CANCEL)) {
			dto_search.setCancel("y");
		}
		
		ArrayList<Reservations_DTO> result_search = dao.search(dto_search);

		dao.showSearchResult(result_search, mainTable, account);
	}
	
	// 수정
	public void runModify() {
		Reservations_DTO dto_update = new Reservations_DTO();
		dto_update.setMemberNO( Integer.parseInt(jtf_MemberNO.getText()) );
		dto_update.setPlaneNO( dao.getPlaneNoToPlaneCode(dao.getPlaneCodeToAviationListItem(String.valueOf(jcb_AviationInfo.getSelectedItem()))) );
		dto_update.setSeat( jcb_Seat.getSelectedItem().toString() );
		dto_update.setStartDate( ccb_startDate.getDate() );
		dto_update.setArrivalDate( ccb_startDate.getDate() );
		dto_update.setCancel("n");
		dto_update.setListNO( Integer.parseInt(dao.getTableSelectedCell(mainTable, "예매번호")) );
		
		for (int i = 0; i < mainTable.getRowCount(); i++) {
			if (i == mainTable.getSelectedRow()) {
				continue;
				
			} else if ( Integer.parseInt( String.valueOf(mainTable.getValueAt(i, dao.getColumnIndex("비행기번호"))) ) == dto_update.getPlaneNO()
					&& String.valueOf(mainTable.getValueAt(i, dao.getColumnIndex("좌석"))).equals(dto_update.getSeat()) ) {
				JOptionPane.showMessageDialog(this, "해당 시간의 비행기에 이미 좌석이 등록되어 있습니다. \n다른 좌석을 선택해주세요.", 
						"좌석 중복", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		int result_update = dao.update(dto_update);
		if (result_update > 0) {
			JOptionPane.showMessageDialog(this, "수정되었습니다.", "수정 완료", JOptionPane.INFORMATION_MESSAGE);
			if (account.equals(Reservations.ACC_ADMIN)) {
				dao.showSearchResult(dao.searchAll(isReservation, null), mainTable, account);
				
			} else { // 유저일 때
				dao.showSearchResult(dao.searchAll(isReservation, dto_userInfo), mainTable, account);
			}
		} else {
			JOptionPane.showMessageDialog(this, "수정하지 못했습니다.", "수정 실패", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Reservation_Admin_Dialog에서 클릭 시 처리할 작업

		if (e.getSource() == btn_OK) {
			
			switch (check_DialogMode) {
			case SEARCH:
				// TODO Dialog에서 검색 버튼을 클릭했을 때
				runSearch();
				dispose();
				
				break;

			case MODIFY:
				// TODO Dialog에서 수정 버튼을 클릭했을 때
				runModify();
				dispose();
				
				break;
			}

		} else if (e.getSource() == btn_Cancel) {
			// TODO Dialog에서 닫기 버튼을 클릭했을 때
			dispose();

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			btn_OK.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		JTextField jtf = (JTextField) e.getSource();

		if (jtf == jtf_MemberNO) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 5) {
				e.consume();
			}

		} else if (jtf == jtf_Name || jtf == jtf_StartLoc || jtf == jtf_ArrivalLoc) {
			if (jtf.getText().length() >= 5) {
				e.consume();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		JComboBox<String> jcb = (JComboBox<String>) e.getSource();
		
		if (jcb == jcb_PlaneName) {
			jcb_AviationInfo.removeAllItems();
			
			for ( String str_Info : dao.getAviationList() ) {
				if (str_Info.contains(dao.getPlaneNameToPlaneInitial( jcb_PlaneName.getSelectedItem().toString() ))) {
					jcb_AviationInfo.addItem(str_Info);
				}
			}
			
		}
		
	} // end itemStateChanged

}
