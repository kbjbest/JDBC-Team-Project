package east_view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DBConnection;
import etc.MyFunctions;

@SuppressWarnings("serial")
public class Login_SignUp extends JDialog implements ActionListener, KeyListener {

	private JPanel pan_Info, p2, p3, p_phone, p01, p02, p03, p04, p05, p06,p07;
	private JTextField txt_id,txt_idCheck ,txt_name, txt_addr, txt_email, txt_phone1, txt_phone2, txt_phone3;
	private JPasswordField pf_pw, pf_pwc;
	private JButton btn_insert, btn_reset, btn_cancel,btn_idCheck;
	private JLabel lb_id, lb_pw, lb_pwc, lb_name, lb_addr, lb_email, lb_phone;
	Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs;
	int  idCheck=0 ; //0은 아이디 미 체크 1은 중복 2  가입가능
	
	public Login_SignUp(JFrame frame) {// JFrame frame
		super(frame, true); // true시 자식 폼 닫히기 전까지 부모폼을 닫을수 없다
		setTitle("회원가입");

		pan_Info = new JPanel(new BorderLayout());
		p2 = new JPanel(new GridLayout(7, 1, 5, 5));
		p3 = new JPanel(new FlowLayout());
		p01 = new JPanel(new GridLayout(2, 1, 3, 3));
		p02 = new JPanel(new GridLayout(2, 1, 3, 3));
		p03 = new JPanel(new GridLayout(2, 1, 3, 3));
		p04 = new JPanel(new GridLayout(2, 1, 3, 3));
		p05 = new JPanel(new GridLayout(2, 1, 3, 3));
		p06 = new JPanel(new GridLayout(2, 1, 3, 3));
		p07=new JPanel(new BorderLayout());
		p_phone = new JPanel();
		
		txt_id = new JTextField(5);
		txt_name = new JTextField(10);
		txt_addr = new JTextField(10);
		txt_email = new JTextField(10);
		txt_phone1 = new JTextField(3);
		txt_phone2 = new JTextField(4);
		txt_phone3 = new JTextField(4);
		
		btn_insert = new JButton("가입");
		btn_insert.addActionListener(this);
		btn_reset = new JButton("다시 작성");
		btn_reset.addActionListener(this);
		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);
		btn_idCheck = new JButton("IDCheck");
		btn_idCheck.addActionListener(this);
		pf_pw = new JPasswordField();
		pf_pwc = new JPasswordField();
		
		lb_id = new JLabel("아이디");
		lb_pw = new JLabel("비밀번호");
		lb_pwc = new JLabel("비밀번호 확인");
		lb_name = new JLabel("이름");
		lb_addr = new JLabel("주소");
		lb_email = new JLabel("이메일");
		lb_phone = new JLabel("핸드폰 번호");
		p01.add(lb_id);
		p07.add(txt_id,"Center");p07.add(btn_idCheck,"East");p01.add(p07);
		p02.add(lb_pw);
		p02.add(pf_pw);
		p03.add(lb_pwc);
		p03.add(pf_pwc);
		p04.add(lb_name);
		p04.add(txt_name);
		p05.add(lb_addr);
		p05.add(txt_addr);
		p06.add(lb_email);
		p06.add(txt_email);
		// p07.add(lb_phone);p07.add(east_phone1);p07.add(east_phone2);p07.add(east_phone3);
		p2.add(p01);
		p2.add(p02);
		p2.add(p03);
		p2.add(p04);
		p2.add(p05);
		p2.add(p06);// p2.add(p07);
		p_phone.add(lb_phone);
		p_phone.add(txt_phone1);
		txt_phone1.addKeyListener(this);
		p_phone.add(txt_phone2);
		txt_phone2.addKeyListener(this);
		p_phone.add(txt_phone3);
		txt_phone3.addKeyListener(this);
		p2.add(p_phone);
		p3.add(btn_insert);
		p3.add(btn_reset);
		p3.add(btn_cancel);
		pan_Info.add(p2, "Center");
		pan_Info.add(p3, "South");

		add(pan_Info);

		setSize(300, 500);
		setLocation(MyFunctions.getCenterLocation(this));
		setVisible(true);
		setResizable(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("2");
				dispose(); // con.close();
			}
		});

	}

	// public static void main(String[] args) {new Login_SignUp(); }
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(btn_insert)) {insert();}
		
		if (obj.equals(btn_reset)) {reset();}
		
		if (obj.equals(btn_cancel)) {cancel();}
		
		if(obj.equals(btn_idCheck)){idCheck();}
	}

	public void insert() {
		
		String phone;
		String sql = "insert into tb_member values(member_seq.nextval,?,?,?,?,?,?) ";
		if(txt_id.getText().length()==0){
		JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.");
		idCheck = 0;return;
		}
	if(idCheck==0){JOptionPane.showMessageDialog(this, "먼저 ID를 체크해주세요.");return;}
	if (idCheck ==1){JOptionPane.showMessageDialog(this, "ID 중복입니다.");return;}
	
	if(!txt_idCheck.getText().equals(txt_id.getText())){
		JOptionPane.showMessageDialog(this, "체크하신 ID와 다릅니다.");
		idCheck = 0;return;
	}
	if(txt_id.getText().length()==0 || txt_idCheck.getText().length()==0 || pf_pw.getText().length()==0
			|| pf_pwc.getText().length()==0 || txt_name.getText().length()==0){
		JOptionPane.showMessageDialog(this,"필수 란에 빈칸이 있습니다.");return;
	}
	if(txt_phone1.getText().length()==0&&txt_phone2.getText().length()==0&&
			txt_phone3.getText().length()==0){
		phone = "";
	}else if(txt_phone1.getText().length()==3&&txt_phone2.getText().length()==4&&
			txt_phone3.getText().length()==4){
			phone = txt_phone1.getText() + "-" + txt_phone2.getText() + "-" + txt_phone3.getText();
			}else{
		JOptionPane.showMessageDialog(this,"전화번호를 정확히 입력해주세요.");return;
	}
		if( !pf_pw.getText().equals(pf_pwc.getText())){
			JOptionPane.showMessageDialog(this,"비밀번호가 일치하지 않습니다.");return;
		}


		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, txt_id.getText());
			pstmt.setString(2, String.valueOf(pf_pw.getPassword()));
			pstmt.setString(3, txt_name.getText());
			pstmt.setString(4, txt_addr.getText());
			pstmt.setString(5, txt_email.getText());
			pstmt.setString(6, phone);

			int a = pstmt.executeUpdate();
			if (a > 0) {
				JOptionPane.showMessageDialog(this, "가입 축하");
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "가입 실패");
				return;
			}

			// east_id,east_name,east_addr,east_email,east_phone1,east_phone2,east_phone3
			// pf_pw, pf_pwc;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "try에러");
		}
	}

	public void reset() {

		txt_id.setText("");
		txt_name.setText("");
		txt_addr.setText("");
		txt_email.setText("");
		txt_phone1.setText("");
		txt_phone2.setText("");
		txt_phone3.setText("");
		pf_pw.setText("");
		pf_pwc.setText("");
		txt_id.requestFocus();

	}

	public void cancel() {
		dispose();
	}

	public void idCheck(){
		if(txt_id.getText().equals("")){JOptionPane.showMessageDialog(this, "아이디 입력해주세요.");return;}
		String sql = "select ID from tb_member  " ;
		ArrayList<String> idList = new ArrayList<>();
		try{ 
			con = DBConnection.getConnection();
			pstmt=con.prepareStatement(sql);		
			rs = pstmt.executeQuery();
			while(rs.next()){
				idList.add(rs.getString("id"));
			}
			
			txt_idCheck = new JTextField(txt_id.getText());
			
			for(String a:idList){
				if (txt_id.getText().equals(a)){idCheck=1;break;}
				else{idCheck = 2; }
			}
			
			if(idCheck==2){JOptionPane.showMessageDialog(this, "사용가능한 아이디 입니다.");}
			else if(idCheck==1){JOptionPane.showMessageDialog(this, "이미 사용된 아이디 입니다.");}
				
		}catch (Exception e) {JOptionPane.showMessageDialog(this, "체크 에러");}
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
		if (txt_phone1.getText().length() == 2) {
			txt_phone2.requestFocus();
		}
		if (txt_phone2.getText().length() == 3) {
			txt_phone3.requestFocus();
		}
		if (jtf == txt_phone1) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 3) {
				e.consume();
			}

		}

		if (jtf == txt_phone2) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 4) {
				e.consume();
			}

		}
		if (jtf == txt_phone3) {
			if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || jtf.getText().length() >= 4) {
				e.consume();
			}

		}
	}

}
