package east_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBConnection;
import etc.MyFunctions;

@SuppressWarnings("serial")
public class Login_LostPW extends JDialog implements ActionListener {
	
	private JPanel panbtn,pancen,p01,p02,p03;
	private JButton yes,reset,cancel;
	private JTextField id,name,pw;
	private JLabel lb_id,lb_name,lb_pw;
	Connection con;
	PreparedStatement pstmt=null;
	ResultSet rs;
	int miss=0;
	
	public Login_LostPW(JFrame frame) {
		super(frame, true);
		setTitle("비밀번호 찾기");
		setLayout(new BorderLayout());
		
		panbtn = new JPanel();pancen=new JPanel();
		p01=new JPanel(new GridLayout(2, 1));
		p02=new JPanel(new GridLayout(2, 1));
		p03=new JPanel(new GridLayout(2, 1));
		//p04=new JPanel();
		yes =  new JButton("확인");reset =  new JButton("다시입력"); cancel =  new JButton("취소");
		yes.addActionListener(this);reset.addActionListener(this);cancel.addActionListener(this);
		id = new JTextField(""); name = new JTextField("");pw = new JTextField("1111");
		pw.setEnabled(false);pw.setBackground(Color.WHITE);pw.setHorizontalAlignment(getY());
		pw.setVisible(false);
		lb_id = new JLabel("아이디");lb_name = new JLabel("이름");
		lb_pw = new JLabel("귀하의 비밀번호는");lb_pw.setHorizontalAlignment(getX());
		lb_pw.setVisible(false);
		p01.add(lb_id); p01.add(id);
		p02.add(lb_name); p02.add(name);
		p03.add(lb_pw); p03.add(pw);
		pancen.setLayout(new GridLayout(3,1,5,5));
		pancen.add(p01);pancen.add(p02);pancen.add(p03);
		panbtn.add(yes);panbtn.add(reset);panbtn.add(cancel);
		
		add(pancen,"Center");add(panbtn,"South");
		
		setSize(250, 250);
		setLocation(MyFunctions.getCenterLocation(this));
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DBConnection.close();System.out.println("2");dispose();   //con.close();
			}
		});
		
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(yes) ){	yes();}
		if(obj.equals(reset) ){	reset();	}
		if(obj.equals(cancel) ){	cancel();	}

	}
	public void yes(){
		if(id.getText().equals("")){JOptionPane.showMessageDialog(this, "아이디 입력해주세요"); return;}
		String sql = "select ID,PW,NAME from tb_member where id = ?  " ;
		pw.setVisible(false);
		lb_pw.setVisible(false);
		try{ 
			con = DBConnection.getConnection();
			pstmt=con.prepareStatement(sql);		
			pstmt.setString(1, id.getText()); 
			rs = pstmt.executeQuery();rs.next();
			
			if(id.getText().equals(rs.getString("id"))&&name.getText().equals(rs.getString("name"))){
				pw.setVisible(true);pw.setText(rs.getString("pw"));
				lb_pw.setVisible(true);
			}else{miss++;
			JOptionPane.showMessageDialog(this, "고객정보 "+miss+"번 틀림");}
			if(miss>2){System.exit(0);};
			}catch (Exception e) {JOptionPane.showMessageDialog(this, "try에러2");}
	}
	public void reset(){
		id.setText("");name.setText("");pw.setText("");
		pw.setVisible(false);
		lb_pw.setVisible(false);
		id.requestFocus();
	}
	public void cancel(){dispose();}
}
