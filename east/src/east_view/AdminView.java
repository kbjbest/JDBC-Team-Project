package east_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DBConnection;
import east_aviationinfo.AviationView;
import east_member.MenuJTabaleExam;
import east_reservation.Reservations;
import etc.MyFunctions;

@SuppressWarnings("serial")
public class AdminView extends JFrame implements ActionListener {

	Reservations reservations, reservations_Cancel;
	
	JMenuBar menubar;
	JMenu menu1, menu2, menu3, menu4;
	JMenuItem menu_member, menu_plane, menu_reservation, menu_logOut, menu_close, menu_insert, menu_reservationCancel;
	JPanel pbtn, p_view;
	JButton btn_member, btn_plane, btn_reservation, btn_logOut, btn_close, btn_record;
	JPanel card_member, card_plane, card_reservation, card_record;
	ImageIcon icon;
	Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs;
	CardLayout card = new CardLayout();

	Member_DTO dto_UserInfo;

	public AdminView(Member_DTO dto_UserInfo) {
		super();
		this.dto_UserInfo = dto_UserInfo;
		reservations = new Reservations(this, Reservations.MODE_RESERVATION, Reservations.ACC_ADMIN, dto_UserInfo);
		reservations_Cancel = new Reservations(this, Reservations.MODE_CANCEL, Reservations.ACC_ADMIN, dto_UserInfo);

		con = DBConnection.getConnection();
		setTitle("항공권 예매 시스템 - 관리자");
		setLayout(new BorderLayout());
		menu1 = new JMenu("메뉴");
		menu2 = new JMenu("회원");
		menu3 = new JMenu("예매");
		menu4 = new JMenu("운행관리");
		menubar = new JMenuBar();
		menu_member = new JMenuItem("회원관리");
		menu_plane = new JMenuItem("운행정보 및 예매");
		menu_reservation = new JMenuItem("예매 내역");
		menu_logOut = new JMenuItem("로그아웃");
		menu_close = new JMenuItem("종료");
		menu_insert = new JMenuItem("회원추가");
		menu_reservationCancel = new JMenuItem("예매취소 내역");
		menu2.add(menu_member);
		menu4.add(menu_plane);
		menu1.add(menu_logOut);
		menu1.add(menu_close);// menu2.add(menu_insert);
		menu3.add(menu_reservation);
		menu3.add(menu_reservationCancel);
		menubar.add(menu1);
		menubar.add(menu4);
		menubar.add(menu3);
		menubar.add(menu2);

		menu_insert.addActionListener(this);
		menu_reservation.addActionListener(this);
		menu_plane.addActionListener(this);
		menu_logOut.addActionListener(this);
		menu_member.addActionListener(this);
		menu_close.addActionListener(this);
		menu_reservationCancel.addActionListener(this);

		pbtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		btn_member = new JButton("회원관리");
		btn_member.setPreferredSize(new Dimension(140, 50));
		btn_plane = new JButton("운행정보 및 예매");
		btn_plane.setPreferredSize(new Dimension(140, 50));
		btn_reservation = new JButton("예매 내역");
		btn_reservation.setPreferredSize(new Dimension(140, 50));
		btn_logOut = new JButton("로그아웃");
		btn_logOut.setPreferredSize(new Dimension(140, 50));
		btn_close = new JButton("종료");
		btn_close.setPreferredSize(new Dimension(140, 50));
		btn_record = new JButton("예매취소 내역");
		btn_record.setPreferredSize(new Dimension(140, 50));
		
		btn_member.addActionListener(this);
		btn_plane.addActionListener(this);
		btn_reservation.addActionListener(this);
		btn_logOut.addActionListener(this);
		btn_close.addActionListener(this);
		btn_record.addActionListener(this);
		
		pbtn.add(btn_plane);
		pbtn.add(btn_reservation);
		pbtn.add(btn_record);
		pbtn.add(btn_member);
		pbtn.add(MyFunctions.spacePanel(150, 60, null));
		pbtn.add(btn_logOut);
		pbtn.add(btn_close);
		pbtn.setBorder(BorderFactory.createEtchedBorder());
		pbtn.setPreferredSize(new Dimension(150, 500));

		p_view = new JPanel(card);
		card_member = new JPanel();
		card_plane = new JPanel();
		card_reservation = new JPanel();
		card_record = new JPanel();
		
		p_view.add(new MenuJTabaleExam(this), "member");
		p_view.add(new AviationView(this, dto_UserInfo), "plane");
		p_view.add(reservations, "reservation");
		p_view.add(reservations_Cancel, "record"); // card
		
		card_member.setBackground(Color.RED);
		card_plane.setBackground(Color.gray);
		card_reservation.setBackground(Color.blue);
		card_record.setBackground(Color.GREEN);
		card.show(p_view, "plane");

		setJMenuBar(menubar);
		add(pbtn, "West");
		add(p_view, "Center");
		setMinimumSize(new Dimension(850, 500));// 최소 크기 지정
		setSize(900, 500);
		setLocation(600, 360);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_member || obj == menu_member) {member();} // 로그인 버턴 액션
		if (obj == btn_plane || obj == menu_plane) {plane();} // 비밀번호 찾기 액션
		if (obj == btn_reservation || obj == menu_reservation) {reservation();} // 회원가입 액션
		if (obj == btn_record || obj == menu_reservationCancel) {record();} // 취소 내역
		if (obj == btn_logOut || obj == menu_logOut) {logOut();}
		if (obj == btn_close || obj == menu_close) {close();}
		if (obj == menu_insert) {new Login_SignUp(this);}
		
	}

	private void member() {card.show(p_view, "member");}

	private void plane() {card.show(p_view, "plane");}

	private void reservation() {
		card.show(p_view, "reservation");
		reservations.runSearchAll();
	}

	private void record() {
		card.show(p_view, "record");
		reservations_Cancel.runSearchAll();
	}

	private void logOut() {
		int ans = JOptionPane.showConfirmDialog(this,
				"로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
		if (ans == JOptionPane.YES_OPTION) {new Login();dispose();} 
		else {return;}
	}

	private void close() {
		int ans = JOptionPane.showConfirmDialog(this, 
				"종료 하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
		if (ans == JOptionPane.YES_OPTION) {DBConnection.close();System.exit(0);} 
		else {return;}
	}
}
