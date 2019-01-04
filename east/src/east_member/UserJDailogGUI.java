package east_member;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserJDailogGUI extends JDialog implements ActionListener, KeyListener {
	JPanel pw = new JPanel(new GridLayout(8, 1));
	JPanel pc = new JPanel(new GridLayout(8, 1));
	JPanel pan_phone = new JPanel();

	@Override
	public void setBounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		super.setBounds(600, 300, 300, 350);
	}

	JPanel ps = new JPanel();
	JLabel lable_Memberno = new JLabel("회원번호");
	JLabel lable_Id = new JLabel("아이디");
	JLabel lable_Pwd = new JLabel("비밀번호");
	JLabel lable_Name = new JLabel("이름");
	JLabel lable_Address = new JLabel("주소");
	JLabel lable_Email = new JLabel("이메일");
	JLabel lable_Phone = new JLabel("전화번호");

	JTextField memberno = new JTextField(10);
	JTextField id = new JTextField(10);
	JTextField pwd = new JTextField(10);
	JTextField name = new JTextField(10);
	JTextField address = new JTextField(10);
	JTextField email = new JTextField(10);
	JTextField phone1 = new JTextField(3);
	JTextField phone2 = new JTextField(4);
	JTextField phone3 = new JTextField(4);
	JButton confirm = new JButton("수정");
	JButton reset = new JButton("취소");

	MenuJTabaleExam me;

	UserDefaultJTableDAO dao = new UserDefaultJTableDAO();

	public UserJDailogGUI(JFrame frame, MenuJTabaleExam me, String index) {
		super(frame, "회원정보 수정", true);
		setLayout(new BorderLayout());

		this.me = me;
		int row = me.jt.getSelectedRow();
		memberno.setText(me.jt.getValueAt(row, 0).toString());
		id.setText(me.jt.getValueAt(row, 1).toString());
		pwd.setText(me.jt.getValueAt(row, 2).toString());
		name.setText(me.jt.getValueAt(row, 3).toString());

		if (me.jt.getValueAt(row, 4) != null) {
			address.setText(me.jt.getValueAt(row, 4).toString());
		}

		if (me.jt.getValueAt(row, 5) != null) {
			email.setText(me.jt.getValueAt(row, 5).toString());
		}

		String str_phone = "";
		if (me.jt.getValueAt(row, 6) != null) {
			str_phone = me.jt.getValueAt(row, 6).toString();
		}
		if (!str_phone.equals("")) {
			phone1.setText(str_phone.split("-")[0]);
			phone2.setText(str_phone.split("-")[1]);
			phone3.setText(str_phone.split("-")[2]);
		}

		memberno.setEnabled(false);
		id.setEnabled(false);
		
		phone1.addKeyListener(this);
		pan_phone.add(phone1);
		phone2.addKeyListener(this);
		pan_phone.add(phone2);
		phone3.addKeyListener(this);
		pan_phone.add(phone3);
		
		pw.add(lable_Memberno);
		pc.add(memberno);
		pw.add(lable_Id);
		pc.add(id);
		pw.add(lable_Pwd);
		pc.add(pwd);
		pw.add(lable_Name);
		pc.add(name);
		pw.add(lable_Address);
		pc.add(address);
		pw.add(lable_Email);
		pc.add(email);
		pw.add(lable_Phone);
		pc.add(pan_phone);

		confirm.addActionListener(this);
		reset.addActionListener(this);
		
		ps.add(confirm);
		ps.add(reset);
		
		add(pw, "West");
		add(pc, "Center");
		add(ps, "South");
		
		setResizable(false);
		setSize(500, 500);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnLabel = e.getActionCommand();
		if (btnLabel.equals("수정")) {

			if (pwd.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해야 합니다.");
				return;
			}

			if (name.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "이름은 필수 항목입니다.");
				return;
			}

			// 전화번호 체크
			if (phone1.getText().length() != 0 || phone2.getText().length() != 0 || phone3.getText().length() != 0) {
				if (phone1.getText().length() != 3 || phone2.getText().length() != 4
						|| phone3.getText().length() != 4) {
					JOptionPane.showMessageDialog(this, "전화번호 형식이 올바르지 않습니다.");
					return;
				}
			}

			// 수정
			if (dao.userUpdate(this) > 0) {
				JOptionPane.showMessageDialog(this, "수정이 완료 되었습니다.");
				dispose();
				dao.userSelectAll(me.dt);
				if (me.dt.getRowCount() > 0)
					me.jt.setRowSelectionInterval(0, 0);
			} else {
				JOptionPane.showMessageDialog(this, "수정되지 않았습니다.");
			}
			
		} else if (btnLabel.equals("취소")) {
			dispose();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		JTextField jtf = (JTextField) e.getSource();
		
		if (jtf == phone1) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 3) {
				e.consume();
			}
			if (phone1.getText().length() == 2) {
				phone2.requestFocus();
			}
		} else if (jtf == phone2) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 4) {
				e.consume();
			}
			if (phone2.getText().length() == 3) {
				phone3.requestFocus();
			}
		} else if (jtf == phone3) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 4) {
				e.consume();
			}
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

}