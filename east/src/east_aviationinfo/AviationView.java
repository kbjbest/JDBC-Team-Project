package east_aviationinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import east_reservation.Reservations_DAO;
import east_view.Member_DTO;
import etc.CalendarComboBox;

@SuppressWarnings("serial")
public class AviationView extends JPanel implements ActionListener, ItemListener {
	public static final int ALL = 1, SEARCH = 2;
	static CalendarComboBox ccb = new CalendarComboBox();
	static TB_AviationInfoDAO dao = new TB_AviationInfoDAO();
	static Object[] columnNames = { "비행기번호", "비행기코드", "비행기명", "출발시간", "도착시간", "출발지", "도착지" };
	static Object rowData[][];
	static JTable table;
	static DefaultTableModel model;
	JScrollPane jsp;
	JPanel panNorth, panSouth, panCenter, p1, p2;
	JTextField tfSearch;
	JButton btSearch, btInsert, btUpdate, btDelete, btReservation;
	JLabel lbPlaneDate, lbSeat, lbEmpty1, lbEmpty2;
	Statement stmt = null;
	PreparedStatement pstmtTotalScroll, pstmtSearchScroll, pstmtTotal, pstmtSearch;
	PreparedStatement pstmtInsert, pstmtDelete;
	PreparedStatement pstmt;
	ResultSet rs = null;
	static public int iPlaneNo;
	static public String strPlaneCode, strPlaneName, strStartTime, strArrivalTime, strStartLoc, strArrivalLoc,
			strStartDate;
	private String sqlDelete = "delete from tb_aviationinfo where planeno = ?";
	private String sqlInsert = "insert into tb_reservations values(reservations_seq.nextval, ?, ?, ?, ?, ?, 'n')";
	private String sqlSelect = "select memberno from tb_member where id = ";
	private String sqlSeat = "select seat from tb_reservations r, tb_aviationinfo a where a.planecode = ? and startdate = ? and r.planeno = a.planeno and cancel = 'n'";
	String strMN;
	String strSelectedSeat;
	String[] strSearch = { "전체보기", "비행기코드", "비행기명", "출발시간", "도착시간", "출발지", "도착지" };
	static String[] strSeat = { "1-A", "1-B", "1-C", "1-D", "1-E", "2-A", "2-B", "2-C", "2-D", "2-E", "3-A", "3-B",
			"3-C", "3-D", "3-E", "4-A", "4-B", "4-C", "4-D", "4-E", "5-A", "5-B", "5-C", "5-D", "5-E", "6-A", "6-B",
			"6-C", "6-D", "6-E" };
	JComboBox<String> cbSearch = new JComboBox<String>(strSearch);
	static JComboBox<String> cbSeat = new JComboBox<String>(strSeat);

	JFrame mainFrame;

	TB_AviationInfoDTO dto = new TB_AviationInfoDTO();

	public AviationView() {
	}

	public AviationView(JFrame mainFrame, Member_DTO dto_userInfo) {
		this.mainFrame = mainFrame;
		sqlSelect += "'" + dto_userInfo.getId() + "' ";
		setLayout(new BorderLayout());
		createComponent();
		ccb.cb_Year.addItemListener(this);
		ccb.cb_Month.addItemListener(this);
		ccb.cb_Day.addItemListener(this);
	}

	public void createComponent() {
		p1 = new JPanel(new GridLayout(2, 1));
		p2 = new JPanel();
		panNorth = new JPanel();
		panCenter = new JPanel();
		panSouth = new JPanel();
		panSouth.setBackground(Color.LIGHT_GRAY);

		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		Reservations_DAO reser_dao = new Reservations_DAO();
		reser_dao.setTableCellCenter(table);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 마우스로 클릭한 테이블의 행 인덱스 얻기
				if (table.getSelectedRow() != -1) {
					getInsertItem();
				}
				seat();
				cbSeat.setSelectedIndex(0);

				if (e.getClickCount() == 2) {

					seat();
					btUpdate.doClick();
				}

			}
		});

		jsp = new JScrollPane(table);

		setTableData(ALL);
		add(jsp);

		panSouth.add(cbSearch);
		panSouth.add(tfSearch = new JTextField(12));
		panSouth.add(btSearch = new JButton("검색"));
		panSouth.add(btInsert = new JButton("추가"));
		panSouth.add(btUpdate = new JButton("수정"));
		panSouth.add(btDelete = new JButton("삭제"));
		p1.add(p2);
		p1.add(panSouth);

		tfSearch.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btSearch.doClick();
				}
			}
		});

		Calendar date = Calendar.getInstance();
		ccb.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));

		p2.add(lbPlaneDate = new JLabel("비행날짜 : "));
		p2.add(ccb);
		p2.add(lbEmpty1 = new JLabel("                 "));
		p2.add(lbSeat = new JLabel("좌석 : "));
		p2.add(cbSeat);
		p2.add(lbEmpty2 = new JLabel("                 "));
		p2.add(btReservation = new JButton("예매"));
		add(p1, "South");

		// 이벤트 처리
		btSearch.addActionListener(this);
		btInsert.addActionListener(this);
		btUpdate.addActionListener(this);
		btDelete.addActionListener(this);
		btReservation.addActionListener(this);

	}
	
	public void init_Seat() {
		cbSeat.removeAllItems();
		for (String str : strSeat) {
			cbSeat.addItem(str);
		}
	}

	public void getInsertItem() {
		if (table.getSelectedRow() != -1) {
			dto.setPlaneNo(iPlaneNo = ((int) table.getValueAt(table.getSelectedRow(), 0)));
			dto.setPlaneCode(strPlaneCode = ((String) table.getValueAt(table.getSelectedRow(), 1)));
			dto.setPlaneName(strPlaneName = ((String) table.getValueAt(table.getSelectedRow(), 2)));
			dto.setStartTime(strStartTime = ((String) table.getValueAt(table.getSelectedRow(), 3)));
			dto.setArrivalTime(strArrivalTime = ((String) table.getValueAt(table.getSelectedRow(), 4)));
			dto.setStartLoc(strStartLoc = ((String) table.getValueAt(table.getSelectedRow(), 5)));
			dto.setArrivalLoc(strArrivalLoc = ((String) table.getValueAt(table.getSelectedRow(), 6)));
//			System.out.println(strPlaneName);
		}

	}

	public static void setTableData(int flag) {
		// 1]DAO에서 모든 레코드 데이타를 컬렉션으로 얻기

		List<TB_AviationInfoDTO> list = null;
		if (flag == ALL)
			list = dao.getRecordAll();

		// 2]컬렉션에 저장된 모든 레코드를 model에 저장
		rowData = new Object[list.size()][columnNames.length];
		for (int i = 0; i < list.size(); i++) {
			TB_AviationInfoDTO dto = list.get(i);
			rowData[i][0] = dto.getPlaneNo();
			rowData[i][1] = dto.getPlaneCode();
			rowData[i][2] = dto.getPlaneName();
			rowData[i][3] = dto.getStartTime();
			rowData[i][4] = dto.getArrivalTime();
			rowData[i][5] = dto.getStartLoc();
			rowData[i][6] = dto.getArrivalLoc();
		}
		model.setDataVector(rowData, columnNames);
		// 3]model을 table에 연결:model저장된 데이타가 table에 뿌려짐
		table.setModel(model);
		Reservations_DAO reser_dao = new Reservations_DAO();
		reser_dao.setTableCellCenter(table);
	}

	public void actionPerformed(ActionEvent ae) {
		getInsertItem();

		if (ae.getSource() == btSearch) {// 검색 버튼 클릭
			// JComboBox에 선택된 value 가져오기
			String fieldName = cbSearch.getSelectedItem().toString();

			if (fieldName.trim().equals("전체보기")) {// 전체검색
				setTableData(ALL);
				if (model.getRowCount() > 0)
					table.setRowSelectionInterval(0, 0);
			} else {
				if (tfSearch.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "검색 단어를 입력해주세요!");
					tfSearch.requestFocus();
				} else {// 검색어를 입력했을경우
					dao.getUserSearch(model, fieldName, tfSearch.getText());
					if (model.getRowCount() > 0) {
						table.setRowSelectionInterval(0, 0);
						Reservations_DAO reser_dao = new Reservations_DAO();
						reser_dao.setTableCellCenter(table);
					}
				}
			}
		} else if (ae.getSource() == btInsert) {
			new Insert(mainFrame);
		} else if (ae.getSource() == btUpdate) {
			if (table.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(null, "수정할 목록을 선택하세요!");
			else
				new Update(mainFrame, dto);
		} else if (ae.getSource() == btDelete) {
			delete();
		} else if (ae.getSource() == btReservation) {
			if (table.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(null, "예매할 목록을 선택하세요!");
			else {
				new Reservation(mainFrame);
				seat();
			}
		}
	}

	public void delete() { // 삭제
		try {
			Connection con = DBConnection.getConnection();
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "삭제할 레코드를 고르세요.");
				return;
			} else {
				int msg = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "WARNING", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (msg == JOptionPane.OK_OPTION) {
					pstmtDelete = con.prepareStatement(sqlDelete);
					String strPlaneNo = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
					pstmtDelete.setString(1, strPlaneNo);
					int result = pstmtDelete.executeUpdate();
					if (result > 0) {
						JOptionPane.showMessageDialog(null, "삭제 성공");
					} else {
						JOptionPane.showMessageDialog(null, "삭제 실패");
					}
					setTableData(ALL);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void seat() {
		init_Seat();
		try {
			Connection con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sqlSeat);
			pstmt.setString(1, strPlaneCode);
			pstmt.setString(2, String.valueOf(ccb.getDate()));
			rs = pstmt.executeQuery();

			while (rs.next()) {
				strSelectedSeat = rs.getString("seat");
				cbSeat.removeItem(strSelectedSeat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		seat();
	}

}