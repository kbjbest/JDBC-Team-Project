package east_member;

//MenuJTabaleExam.java
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import east_reservation.Reservations_DAO;

@SuppressWarnings("serial")
public class MenuJTabaleExam extends JPanel implements ActionListener {

	String[] name = { "회원번호", "아이디", "비밀번호", "이름", "주소", "이메일", "전화번호" };
	JFrame mainFrame;

	DefaultTableModel dt = new DefaultTableModel(name, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			switch (columnIndex) {
			case 0:
				return Integer.class;

			default:
				return String.class;
			}
		}
	};
	JTable jt = new JTable(dt);
	Reservations_DAO reser_dao = new Reservations_DAO();
	JScrollPane jsp = new JScrollPane(jt);

	JPanel pan_South = new JPanel();

	JComboBox<String> combo = new JComboBox<String>(name);
	JTextField jtf = new JTextField(15);

	JButton search = new JButton("검색");
	JButton update = new JButton("수정");
	JButton delete = new JButton("삭제");
	JButton searchAll = new JButton("전체보기");

	UserDefaultJTableDAO dao = new UserDefaultJTableDAO();

	public MenuJTabaleExam(JFrame frame) {
		mainFrame = frame;
		reser_dao.setTableSort(jt);

		jt.getTableHeader().setPreferredSize(new Dimension(100, 30));
		jt.setRowHeight(30);
		jt.getTableHeader().setReorderingAllowed(false);
		Reservations_DAO reser_dao = new Reservations_DAO();
		reser_dao.setTableCellCenter(jt); // 셀 내용 가운데 정렬

		pan_South.setBackground(Color.LIGHT_GRAY);
		pan_South.add(combo);
		pan_South.add(jtf);
		pan_South.add(search);
		pan_South.add(update);
		pan_South.add(delete);
		pan_South.add(searchAll);

		setLayout(new BorderLayout());
		add(jsp, "Center");
		add(pan_South, "South");

		setSize(700, 400);

		search.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		searchAll.addActionListener(this);

		dao.userSelectAll(dt);

		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setContentPane(new MenuJTabaleExam(frame));
		frame.setSize(650, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == update) {
			new UserJDailogGUI(mainFrame, this, "수정");
		}
		if (e.getSource() == delete) {

			int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.NO_OPTION) {
				return;
			}

			int row = jt.getSelectedRow();
			System.out.println("선택행 : " + row);

			Object obj = jt.getValueAt(row, 0);
			Object obj1 = jt.getValueAt(row, 2);
			System.out.println("값 : " + obj);

			if (!obj1.toString().equals("admin") && dao.userDelete(obj.toString()) > 0) {
				JOptionPane.showMessageDialog(mainFrame, "회원정보가 삭제되었습니다.");

				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else {
				if (obj1.toString().equals("admin"))
					JOptionPane.showMessageDialog(mainFrame, "관리자는 삭제할 수 없습니다.");
				else
					JOptionPane.showMessageDialog(mainFrame, "회원정보가 삭제되지 않았습니다.");
			}

		}

		if (e.getSource() == search) {
			String fieldName = combo.getSelectedItem().toString();

			if (jtf.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(mainFrame, "검색단어를 입력해주세요!");
				jtf.requestFocus();
			} else {
				dao.getUserSearch(dt, fieldName, jtf.getText());
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			}

		}

		if (e.getSource() == searchAll) {
			dao.userSelectAll(dt);
			if (dt.getRowCount() > 0)
				jt.setRowSelectionInterval(0, 0);
		}

	}

}
