package east_reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;

import east_view.Member_DTO;
import etc.MyFunctions;

@SuppressWarnings("serial")
public class Reservations extends JPanel implements ActionListener {

	public final static String ACC_ADMIN = "Admin", ACC_USER = "User";
	public final static String MODE_RESERVATION = "Reservation", MODE_CANCEL = "Cancel";
	
	private JScrollPane jsp_reservation;
	private JTable tb_reservation;
	private JButton btn_search, btn_modify, btn_delete, btn_cancel, btn_searchAll;

	private JFrame mainFrame;
	private String isReservation;
	private String account;
	private Member_DTO dto_userInfo;
	private Reservations_DAO dao;
	
	/**
	 * 예매내역 생성자
	 * @param frame : 사용할 프레임
	 * @param isReservation : 예매내역 모드
	 * @param account : 사용할 계정 모드
	 * @param dto_userInfo : 로그인한 유저 정보
	 */
	public Reservations(JFrame frame, String isReservation, String account, Member_DTO dto_userInfo) {
		// TODO Reservation_Admin 생성자
		setLayout(new BorderLayout());
		mainFrame = frame;
		this.isReservation = isReservation;
		this.account = account;
		this.dto_userInfo = dto_userInfo;
		dao = new Reservations_DAO();

		// 테이블 설정
		tb_reservation = new JTable();
		tb_reservation.getTableHeader().setPreferredSize(new Dimension(100, 30));
		tb_reservation.setRowHeight(30);
		tb_reservation.getTableHeader().setReorderingAllowed(false);
		dao.setTableSort(tb_reservation);
		jsp_reservation = new JScrollPane(tb_reservation);

		JPanel pan_bottom = new JPanel();
		pan_bottom.setBackground(Color.LIGHT_GRAY);
		pan_bottom.add(btn_search = new JButton("검색..."));
		pan_bottom.add(btn_modify = new JButton("수정..."));
		pan_bottom.add(btn_delete = new JButton("삭제"));			
		pan_bottom.add(btn_cancel = new JButton("예매취소"));
		pan_bottom.add(btn_searchAll = new JButton("전체보기"));
		
		btn_search.addActionListener(this);
		btn_modify.addActionListener(this);
		btn_delete.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_searchAll.addActionListener(this);
		
		tb_reservation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btn_modify.isVisible() && e.getClickCount() == 2) {
					runModify();
				}
			}
		});
		tb_reservation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_DELETE && account.equals(Reservations.ACC_ADMIN)) {
					runDelete();
					
				} else if (btn_modify.isVisible() && e.getKeyCode() == KeyEvent.VK_SPACE) {
					runModify();
				}
			}
		});
		
		if (isReservation.equals(MODE_CANCEL)) {
			btn_cancel.setVisible(false);
			btn_modify.setVisible(false);
		}
		if (account.equals(Reservations.ACC_USER)) {
			btn_delete.setVisible(false);
		}

		add(jsp_reservation, BorderLayout.CENTER);
		add(pan_bottom, BorderLayout.SOUTH);
		
		// 실행 시 전체보기 자동클릭
		runSearchAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Reservation_Admin Panel에서 클릭 시 처리할 작업

		if (e.getSource() == btn_search) {
			// TODO 검색 버튼 -> 다이얼로그(검색모드)
			runSearch();

		} else if (e.getSource() == btn_modify) {
			// TODO 수정 버튼 -> 다이얼로그(수정모드)
			runModify();

		} else if (e.getSource() == btn_delete) {
			// TODO 삭제 버튼
			runDelete();
			// 삭제 후 전체보기
			runSearchAll();

		} else if (e.getSource() == btn_cancel) {
			// TODO 예매취소 버튼
			runCancel();
			
		} else if (e.getSource() == btn_searchAll) {
			// TODO 전체보기 버튼
			runSearchAll();
		}

	}
	
	// 검색
	public void runSearch() {
		new Reservations_Dialog(mainFrame, tb_reservation, Reservations_Dialog.SEARCH, isReservation, account, dto_userInfo);
	}
	
	// 수정
	public void runModify() {
		if (tb_reservation.getSelectedRow() == -1) { // 테이블에서 선택한 항목이 없을 때
			JOptionPane.showMessageDialog(this, "수정할 항목을 선택하세요.", "항목 선택", JOptionPane.ERROR_MESSAGE);
		} else {
			new Reservations_Dialog(mainFrame, tb_reservation, Reservations_Dialog.MODIFY, isReservation, account, dto_userInfo);
		}
	}
	
	// 삭제
	public void runDelete() {
		if (tb_reservation.getSelectedRow() == -1) { // 테이블에서 선택한 항목이 없을 때
			JOptionPane.showMessageDialog(this, "삭제할 항목을 선택하세요.", "항목 선택", JOptionPane.ERROR_MESSAGE);
			
		} else {
			int msg = JOptionPane.showConfirmDialog(this,
					"회원명 : " + dao.getTableSelectedCell(tb_reservation, "회원명") + "\n" + 
					"비행기명 : " + dao.getTableSelectedCell(tb_reservation, "비행기명") + "\n\n" +
					dao.getTableSelectedCell(tb_reservation, "출발지") + " -> " + dao.getTableSelectedCell(tb_reservation, "도착지") + "\n" +
					"출발날짜/시간 : " + dao.getTableSelectedCell(tb_reservation, "출발날짜/시간") + "\n" +
					"도착날짜/시간 : " + dao.getTableSelectedCell(tb_reservation, "도착날짜/시간") + "\n" +
					"좌석 : " + dao.getTableSelectedCell(tb_reservation, "좌석") + "\n" +
					"\n - 정말 삭제하시겠습니까?\n ",
					"삭제", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (msg == JOptionPane.YES_OPTION) { // 삭제 시
				Reservations_DTO dto_delete = new Reservations_DTO();
				dto_delete.setListNO( Integer.parseInt(dao.getTableSelectedCell(tb_reservation, "예매번호")) );

				if (dao.delete(dto_delete) > 0) {
					JOptionPane.showMessageDialog(this, "삭제되었습니다.", "삭제 성공", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "삭제하지 못했습니다.", "삭제 실패", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
	}
	
	// 예매취소
	public void runCancel() {
		Reservations_DTO dto_update = new Reservations_DTO();
		Reservations_DTO dto_Date = new Reservations_DTO();
		
		if (tb_reservation.getSelectedRow() == -1) { // 테이블에서 선택한 항목이 없을 때
			JOptionPane.showMessageDialog(this, "예매 취소할 항목을 선택하세요.", "항목 선택", JOptionPane.ERROR_MESSAGE);
			
		} else {
			int msg = JOptionPane.showConfirmDialog(mainFrame, "해당 항목을 예매 취소하겠습니까?", "예매 취소",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (msg == JOptionPane.YES_OPTION) {
				dto_Date = dao.getDateAndTime( dao.getTableSelectedCell(tb_reservation, "출발날짜/시간") ,
						dao.getTableSelectedCell(tb_reservation, "도착날짜/시간") );
				
				dto_update.setMemberNO( Integer.parseInt( dao.getTableSelectedCell(tb_reservation, "회원번호") ) );
				dto_update.setPlaneNO( Integer.parseInt( dao.getTableSelectedCell(tb_reservation, "비행기번호") ) );
				dto_update.setSeat( dao.getTableSelectedCell(tb_reservation, "좌석") );
				
				dto_update.setStartDate( dto_Date.getStartDate() );
				dto_update.setArrivalDate( dto_Date.getArrivalDate() );
				dto_update.setCancel("y");
				dto_update.setListNO( Integer.parseInt(dao.getTableSelectedCell(tb_reservation, "예매번호")) );
				
				int result_update = dao.update(dto_update);
				if (result_update > 0) {
					JOptionPane.showMessageDialog(this, "예매가 취소 되었습니다.", "예매 취소 완료", JOptionPane.INFORMATION_MESSAGE);
					runSearchAll();
				} else {
					JOptionPane.showMessageDialog(this, "예매를 취소하지 못했습니다.", "취소 실패", JOptionPane.ERROR_MESSAGE);
				}	
			}
		}
	}
	
	// 전체보기
	public void runSearchAll() {
		if (account.equals(Reservations.ACC_ADMIN)) {
			dao.showSearchResult(dao.searchAll(isReservation, null), tb_reservation, account);
			
		} else if (account.equals(Reservations.ACC_USER)) { // 유저일 때
			dao.showSearchResult(dao.searchAll(isReservation, dto_userInfo), tb_reservation, account);
		}
	}
	
	public static void main(String[] args) {
		MyFunctions.setUIFont(new FontUIResource(new Font("맑은 고딕", Font.BOLD, 13)));
		JFrame frame = new JFrame("예매내역 - 관리자");
		frame.setContentPane(new Reservations(frame, Reservations.MODE_RESERVATION, Reservations.ACC_ADMIN, new Member_DTO()));
		frame.setSize(800, 500);
		frame.setLocation(MyFunctions.getCenterLocation(frame));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JFrame frame2 = new JFrame("취소내역 - 관리자");
		frame2.setContentPane(new Reservations(frame2, Reservations.MODE_CANCEL, Reservations.ACC_ADMIN, new Member_DTO()));
		frame2.setSize(800, 500);
		frame2.setLocation(MyFunctions.getCenterLocation(frame2));
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);
		
		Member_DTO member_dto = new Member_DTO();
		member_dto.setMemberNO(2);
		member_dto.setId("aht");
		
		JFrame frame3 = new JFrame("예매내역 - 고객");
		frame3.setContentPane(new Reservations(frame3, Reservations.MODE_RESERVATION, Reservations.ACC_USER, member_dto));
		frame3.setSize(800, 500);
		frame3.setLocation(MyFunctions.getCenterLocation(frame3));
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setVisible(true);
		
		JFrame frame4 = new JFrame("취소내역 - 고객");
		frame4.setContentPane(new Reservations(frame4, Reservations.MODE_CANCEL, Reservations.ACC_USER, member_dto));
		frame4.setSize(800, 500);
		frame4.setLocation(MyFunctions.getCenterLocation(frame4));
		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame4.setVisible(true);
	}

}