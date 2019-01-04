package east_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JTextField;

import db.DBConnection;
import east_aviationinfo.AviationUserView;
import east_reservation.Reservations;
import etc.MyFunctions;

@SuppressWarnings("serial")
public class UserView extends JFrame implements ActionListener,KeyListener {

	Reservations reservations, reservations_Cancel;
	
	JMenuBar menubar;
	JMenu menu1, menu2, menu3;
	JMenuItem menu_member, menu_buy, menu_reservation, menu_logOut, menu_close, menu_cancelRecord, menu_signOut;
	JPanel pbtn, p_view;
	JButton btn_member, btn_buy, btn_reservation, btn_logOut, btn_close, btn_cancelRecord;
	JPanel card_member, card_buy, card_reservation, card_cancelRecord;
	static JButton btn_closeNoAction, btn_signOut;
	ImageIcon icon;
	Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs;
	CardLayout card = new CardLayout();
	User_panel_member panel_member;
	Member_DTO dto_UserInfo;

	public UserView(Member_DTO dto_UserInfo) {
		super();
		this.dto_UserInfo = dto_UserInfo;
		reservations = new Reservations(this, Reservations.MODE_RESERVATION, Reservations.ACC_USER, dto_UserInfo);
		reservations_Cancel = new Reservations(this, Reservations.MODE_CANCEL, Reservations.ACC_USER, dto_UserInfo);
		
		setTitle("항공권 예매 시스템 - 고객 : " + dto_UserInfo.getName()+"님");
		setLayout(new BorderLayout());

		con = DBConnection.getConnection();

		menu1 = new JMenu("메뉴");
		menu2 = new JMenu("예매");
		menu3 = new JMenu("회원");

		menubar = new JMenuBar();
		menu_member = new JMenuItem("개인정보");
		menu_buy = new JMenuItem("예매");
		menu_reservation = new JMenuItem("예매 내역");
		menu_logOut = new JMenuItem("로그아웃");
		menu_close = new JMenuItem("종료");
		menu_cancelRecord = new JMenuItem("예매취소 내역");
		menu_signOut = new JMenuItem("회원탈퇴");

		menu1.add(menu_logOut);
		menu1.addSeparator();
		menu1.add(menu_close);
		menu2.add(menu_reservation);
		menu2.add(menu_buy);
		menu2.addSeparator();
		menu2.add(menu_cancelRecord);
		menu3.add(menu_member);
		menu3.addSeparator();
		menu3.add(menu_signOut);
		
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);
		
		menu_member.addActionListener(this);
		menu_buy.addActionListener(this);
		menu_reservation.addActionListener(this);
		menu_logOut.addActionListener(this);
		menu_close.addActionListener(this);
		menu_cancelRecord.addActionListener(this);
		menu_signOut.addActionListener(this);

		pbtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		btn_member = new JButton("개인정보");
		btn_member.setPreferredSize(new Dimension(140, 50));
		btn_buy = new JButton("예매");
		btn_buy.setPreferredSize(new Dimension(140, 50));
		btn_reservation = new JButton("예매 내역");
		btn_reservation.setPreferredSize(new Dimension(140, 50));
		btn_logOut = new JButton("로그아웃");
		btn_logOut.setPreferredSize(new Dimension(140, 50));
		btn_close = new JButton("종료");
		btn_close.setPreferredSize(new Dimension(140, 50));
		btn_cancelRecord = new JButton("예매취소 내역");
		btn_cancelRecord.setPreferredSize(new Dimension(140, 50));
		btn_closeNoAction = new JButton("확인없이 로그아웃");
		btn_signOut = new JButton("회원탈퇴");
		
		btn_member.addActionListener(this);
		btn_buy.addActionListener(this);
		btn_reservation.addActionListener(this);
		btn_logOut.addActionListener(this);
		btn_close.addActionListener(this);
		btn_cancelRecord.addActionListener(this);
		btn_closeNoAction.addActionListener(this);
		btn_signOut.addActionListener(this);
		
		pbtn.add(btn_reservation);
		pbtn.add(btn_buy);
		pbtn.add(btn_cancelRecord);
		pbtn.add(btn_member);
		pbtn.add(MyFunctions.spacePanel(150, 60, null));
		pbtn.add(btn_logOut);
		pbtn.add(btn_close);
		pbtn.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		pbtn.setPreferredSize(new Dimension(150, 500));

		p_view = new JPanel(card);
		card_member = new JPanel();
		card_buy = new AviationUserView(dto_UserInfo);
		card_reservation = reservations;
		card_cancelRecord = reservations_Cancel;
		//한개 클래스에 모드 2개 있어서 서로 다른 화면 창 뜸
		// System.out.println("card 시작");
		panel_member = new User_panel_member(dto_UserInfo);

		panel_member.txt_phone1.addKeyListener(this);
		panel_member.txt_phone2.addKeyListener(this);
		panel_member.txt_phone3.addKeyListener(this);
		

		p_view.add(panel_member, "member");
		// System.out.println("card 끝");
		p_view.add(card_buy, "buy");
		p_view.add(card_cancelRecord, "cancelRecord");
		p_view.add(card_reservation, "reservation");
		
		card_member.setBackground(Color.green);
		card_buy.setBackground(Color.black);
		card_reservation.setBackground(Color.white);
		card_cancelRecord.setBackground(Color.blue);
		card.show(p_view, "reservation");

		setJMenuBar(menubar);
		add(pbtn, "West");
		add(p_view, "Center");
		
		// setMinimumSize(new Dimension(450, 500));//최소 크기 지정
		setSize(800, 500);
		setLocation(600, 360);//위치를 중간으로 함
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // 창 닫기 눌러도 반응없게 함
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_member || obj == menu_member) {member();panel_member.cancel();} 
		if (obj == btn_buy || obj == menu_buy) {plane();} 
		if (obj == btn_reservation || obj == menu_reservation) {reservation();} 
		if (obj == btn_logOut || obj == menu_logOut) {logOut();}
		if (obj == btn_close || obj == menu_close) {close();}
		if (obj == btn_closeNoAction) {noActionClose();}
		if (obj == btn_signOut || obj == menu_signOut) {signOut();}
		if (obj == btn_cancelRecord || obj == menu_cancelRecord) {cancelRecord();}
	}

	private void member() {card.show(p_view, "member");} // 회원정보

	private void plane() {card.show(p_view, "buy");}

	private void reservation() {
		card.show(p_view, "reservation");
		reservations.runSearchAll();
	}

	private void logOut() {
		int ans = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
		if (ans == JOptionPane.YES_OPTION) {new Login();dispose();} 
		else {return;}

	}

	private void close() {
		int ans = JOptionPane.showConfirmDialog(null, "종료 하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
		if (ans == JOptionPane.YES_OPTION) {DBConnection.close();System.exit(0);}
	}

	private void cancelRecord() {
		card.show(p_view, "cancelRecord");
		reservations_Cancel.runSearchAll();
	}

	private void noActionClose() {new Login();dispose();}

	private void signOut() {
		String sql = "delete from tb_member where id = ?";
		int ans = JOptionPane.showConfirmDialog(null, "정말 탈퇴 하시겠습니까?", "회원탈퇴", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (ans == JOptionPane.YES_OPTION) {
			try {
				con = DBConnection.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto_UserInfo.getId());
				pstmt.executeUpdate();
				System.out.println("회원 탈퇴 : " + dto_UserInfo.getId() + "(" + dto_UserInfo.getName() + ")");

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "try에러5");
			}
			noActionClose();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		JTextField jtf = (JTextField) e.getSource();
		if (panel_member.txt_phone1.getText().length() == 2) {
			panel_member.txt_phone2.requestFocus();
		}
		if (panel_member.txt_phone2.getText().length() == 3) {
			panel_member.txt_phone3.requestFocus();
		}
		if (jtf ==panel_member.txt_phone1) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 3) {
				e.consume();
			}

		}

		if (jtf == panel_member.txt_phone2) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 4) {
				e.consume();
			}

		}
		if (jtf == panel_member.txt_phone3) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 4) {
				e.consume();
			}

		}
	}


}
