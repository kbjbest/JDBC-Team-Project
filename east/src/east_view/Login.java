package east_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DBConnection;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener,MouseListener{
	static String username,userid,userMemberNO;
	JPanel pan_login,pan_loginView,pan_main;
	//JPanel login2 = new JPanel(new GridLayout(2, 1));
	JPanel pan_word ,pan_idpw,pan_loginButton,pan_image,p1,p2,p3,p4,p5,p6,p7;
	JTextField txt_id ;JPasswordField txt_pw;
	JButton btnLogin;
	JLabel lb_pw,lb_aa,lb_bb,lb_Lost,lb_Join,lb_title,lb_projectBy;
	ImageIcon icon1,icon2;
	Connection con;
	PreparedStatement pstmt=null;
	ResultSet rs;
	int miss=0;
	Member_DTO dto;
	public Login() {
		super();
		setTitle("Login");
		icon1 = new ImageIcon(ClassLoader.getSystemResource("east_index.jpg"));
		icon2 = new ImageIcon("login.png");
		lb_aa = new JLabel();
		lb_bb = new JLabel();
		lb_aa.setIcon(icon1);
		lb_bb.setIcon(icon2);
		
		pan_image = new JPanel();
		
		btnLogin = new JButton("로그인");
		btnLogin.setFont(new Font("맑은 고딕", Font.BOLD , 13));
		
		lb_Lost = new JLabel("[ 비밀번호? ]"); 
		lb_Lost.setForeground(Color.WHITE);
		lb_Join = new JLabel("[ 회원가입 ]");
		lb_Join.setForeground(Color.WHITE);
		
		lb_Lost.setFont(new Font("맑은 고딕", Font.BOLD , 13));
		lb_Join.setFont(new Font("맑은 고딕", Font.BOLD , 13));
		
		lb_title = new JLabel("Ticket reservation system");
		lb_title.setFont(new Font("Segoe UI", Font.BOLD + Font.ITALIC, 30));
		lb_title.setForeground(Color.WHITE);
		
		lb_projectBy = new JLabel("Project by EAST");
		lb_projectBy.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lb_projectBy.setForeground(new Color(100, 100, 100));
		
		btnLogin.addActionListener(this);
		lb_Lost.addMouseListener(this);
		lb_Join.addMouseListener(this);
		
		pan_login = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pan_loginView = new JPanel(new FlowLayout(FlowLayout.CENTER));

		txt_id= new JTextField(15);
		txt_id.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		txt_id.setText("아이디");
		txt_id.addFocusListener(new FocusListener() {									//아이디 표시
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txt_id.getText().equals("")){txt_id.setText("아이디");}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txt_id.getText().equals("아이디"))txt_id.setText("");
			}
		});
		txt_id.addKeyListener(new KeyListener() {										//enter 누르면 로그인
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()==KeyEvent.VK_ENTER){login();}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		

		txt_pw= new JPasswordField(15);
		txt_pw.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		txt_pw.setText("비밀번호");
		txt_pw.setEchoChar((char)0);
		txt_pw.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txt_pw.getText().equals("")){txt_pw.setText("비밀번호");
				txt_pw.setEchoChar((char)0);}
				}
			
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txt_pw.getText().equals("비밀번호")){
					txt_pw.setText("");txt_pw.setEchoChar('*');}
					
			}
		});
		txt_pw.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()==KeyEvent.VK_ENTER){login();}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txt_id.setPreferredSize(new Dimension(180, 28));
		txt_pw.setPreferredSize(new Dimension(180, 28));
		btnLogin.setPreferredSize(new Dimension(160, 25));
		
		pan_login.setBackground(new Color(0, 51, 153,50));
		pan_login.setPreferredSize(new Dimension(200, 170));
		pan_loginView.setBounds(150, 100, 430, 320);
		pan_loginView.setBackground( new Color(255, 255, 0,0));
		
		
		pan_login.add(newPanel(180, 2));
		pan_login.add(txt_id);
		pan_login.add(newPanel(180, 2));
		pan_login.add(txt_pw);
		pan_login.add(newPanel(180, 2));
		pan_login.add(btnLogin);
		JPanel a=newPanel(180, 1);a.setBackground(Color.white);
		pan_login.add(a);
		pan_login.add(newPanel(180, 1));
		pan_login.add(lb_Lost);
		pan_login.add(lb_Join);
		

		 pan_loginView.add(lb_title);
		 pan_loginView.add(newPanel(400, 30));
		 pan_loginView.add(pan_login);
		 pan_loginView.add(newPanel(400, 30));
		 pan_loginView.add(lb_projectBy);
		 


			

			
		 add(pan_loginView);
		 add(lb_aa);//메인 화면
		 
		 
		setLocation(500, 360);
		setSize(700,465);
		setVisible(true); setResizable(false);  
		btnLogin.requestFocus();																//초점을 버튼으로 이동
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DBConnection.close();System.exit(0);
			}
		});
		
	}
	
	public static void main(String[] args) {new Login(); }
	
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == btnLogin){login();}//로그인 버턴 액션
		
	}
	public void login(){
		dto = new Member_DTO();
		if(txt_id.getText().equals("아이디")){JOptionPane.showMessageDialog(this, "아이디를 입력해주세요."); return;}
		if(txt_pw.getText().equals("비밀번호")){JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요."); return;}
		String sql = "select memberNO,ID,PW,name,address,email,phone from tb_member where id = ? " ;
		try{ 
			con = DBConnection.getConnection();
			pstmt=con.prepareStatement(sql);		
			pstmt.setString(1, txt_id.getText());
			rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString("id"); String pw = rs.getString("pw");
			username = rs.getString("name"); userid = rs.getString("id");
			
			userMemberNO = rs.getString("memberNO");
			dto.setMemberNO(rs.getInt("memberNO"));
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAddress(rs.getString("pw"));
			dto.setEmail(rs.getString("email"));
			dto.setPhone(rs.getString("phone"));
			if(txt_id.getText().equals(id) &&txt_pw.getText().equals(pw) ){
				
				if(id.equals("admin") ){JOptionPane.showMessageDialog(this, "관리자로 로그인 하셨습니다.");//admin창 이동
				new AdminView(dto);dispose();
				}else{
					new UserView(dto);dispose();
				}
			}else{miss++;
			JOptionPane.showMessageDialog(this, "비밀번호 "+miss+"번 틀림");
			if(miss>2){DBConnection.close();System.exit(0);};
			}
		}catch (Exception e) {JOptionPane.showMessageDialog(this, "존재하지 않는 회원입니다.");}//
		
	}
	
	private void add(){
		Login_SignUp signup = new Login_SignUp(this);
	}
	
	private void lost(){
		Login_Lost lost = new Login_Lost(this);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel label = (JLabel) e.getSource();
		if( lb_Lost.getText().equals(label.getText())){
			lost();
		}else if (lb_Join.getText().equals(label.getText())){
			add();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public JPanel  newPanel(int x, int y){
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(x,y));
		jp.setBackground(new Color(0, 0, 0, 0));
		return jp;
		
	}
}
