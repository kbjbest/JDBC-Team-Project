package east_view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DBConnection;

@SuppressWarnings("serial")
public class User_panel_member extends JPanel implements ActionListener{

	JPanel pan_information,pan_btn;
	JPanel p01,p02,p03,p04,p05,p06,p07,p08,p09,p10,
			  p11,p12,p13,p14,p15,p16,p17,p18,p19,p20;
	JLabel lb_id,lb_name,lb_pw,lb_newPw,lb_newPwC,lb_addr,lb_email,lb_phone;
	JTextField txt_id,txt_name,txt_addr,txt_email,txt_phone,txt_phone1,txt_phone2,txt_phone3;
	JPasswordField txt_pw,txt_newPw,txt_newPwC;
	JButton btn_cancel, btn_correct;
	Connection con1,con2;
	PreparedStatement pstmt1=null,pstmt2 = null;
	ResultSet rs;
	
	Member_DTO dto_UserInfo;
	
	public User_panel_member(Member_DTO dto_UserInfo){
		this.dto_UserInfo = dto_UserInfo;
		
		setLayout(new BorderLayout());
		
		p09 = new JPanel();
		pan_information = new JPanel(new GridLayout(8, 1));							//정보수정 입력창
		pan_btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		lb_id = new JLabel(dto_UserInfo.getName() + "님의 회원정보", JLabel.RIGHT);
		lb_pw = new JLabel("기존 비밀번호 : ", JLabel.RIGHT);
		lb_newPw = new JLabel("새 비밀번호 : ", JLabel.RIGHT);
		lb_newPwC = new JLabel("새 비밀번호 확인 : ", JLabel.RIGHT);
		lb_addr = new JLabel("주소 : ", JLabel.RIGHT);lb_email = new JLabel("이메일 : ", JLabel.RIGHT);
		lb_phone = new JLabel("전화번호 : ", JLabel.RIGHT);lb_name = new JLabel("이름 : ", JLabel.RIGHT);
		lb_name.setPreferredSize(new Dimension(120, 25));
		lb_email.setPreferredSize(new Dimension(120, 25));
		lb_addr.setPreferredSize(new Dimension(120, 25));
		lb_phone.setPreferredSize(new Dimension(120, 25));
		lb_newPwC.setPreferredSize(new Dimension(120, 25));
		lb_pw.setPreferredSize(new Dimension(120, 25));
		lb_newPw.setPreferredSize(new Dimension(120, 25));
		txt_id = new JTextField(15);txt_pw = new JPasswordField(15);
		txt_newPw = new JPasswordField(15);txt_newPwC = new JPasswordField(15);
		txt_addr = new JTextField(15);txt_email = new JTextField(15);
		txt_phone = new JTextField(15);txt_name = new JTextField(15);
		txt_phone1 = new JTextField(3);txt_phone2 = new JTextField(4);
		txt_phone3 = new JTextField(4);
/*		txt_addr.setBackground(new Color(238, 238, 238));txt_email.setBackground(Color.GRAY);
		txt_phone.setBackground(Color.GRAY);txt_pw.setBackground(Color.GRAY);
		txt_newPw.setBackground(getBackground());txt_newPwC.setBackground(Color.GRAY);*/
		
		p01 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p02 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p04 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p05 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p06 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p07 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p08 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btn_cancel = new JButton("취소");btn_cancel.addActionListener(this);
		btn_correct = new JButton("회원정보 수정");btn_correct.addActionListener(this);
		informationView();
		
		p01.add(lb_id);
		p02.add(lb_addr);p02.add(txt_addr);
		p03.add(lb_email);p03.add(txt_email);
		p04.add(lb_phone);p04.add(txt_phone1);p04.add(txt_phone2);p04.add(txt_phone3);
		p05.add(lb_pw);p05.add(txt_pw);
		p06.add(lb_newPw);p06.add(txt_newPw);
		p07.add(lb_newPwC);p07.add(txt_newPwC);
		p08.add(lb_name);p08.add(txt_name);
		pan_information.add(p01);pan_information.add(p08);
		pan_information.add(p02);pan_information.add(p03);
		pan_information.add(p04);pan_information.add(p05);
		pan_information.add(p06);pan_information.add(p07);
		

																										
		pan_btn.add(UserView.btn_signOut);
		pan_btn.add(btn_correct);
		pan_btn.add(btn_cancel);
		btn_cancel.setVisible(false);UserView.btn_signOut.setVisible(true);
		
		add(pan_information, "Center");add(pan_btn,"South");  						//위치 설정
		//setSize(800,500);
		setVisible(true); 
		
	}
	//public static void main(String[] args) {}
	
	private void informationView(){
		String sql = "select ID, name, PW, NAME, ADDRESS, Email, phone "
				+ "from tb_member where name = ? " ;
		try{
			con1 = DBConnection.getConnection();
			pstmt1=con1.prepareStatement(sql);		
			pstmt1.setString(1, dto_UserInfo.getName());
			rs = pstmt1.executeQuery();
			rs.next();

			txt_id.setText(rs.getString("id"));txt_pw.setText("");
			txt_name.setText(rs.getString("name"));
			txt_newPw.setText("");txt_newPwC.setText("");
			txt_addr.setText(rs.getString("ADDRESS"));
			txt_email.setText(rs.getString("email"));
//			System.out.println(rs.getString("phone"));
			if(rs.getString("phone")==null){
				txt_phone.setText("");
				txt_phone1.setText("");
				txt_phone2.setText("");
				txt_phone3.setText("");
			}else if(rs.getString("phone").length()>0){
			txt_phone.setText(rs.getString("phone"));
			txt_phone1.setText(txt_phone.getText(0, 3));
			txt_phone2.setText(txt_phone.getText(4, 4));
			txt_phone3.setText(txt_phone.getText(9, 4));
			}
			txt_id.setEnabled(false);
			txt_addr.setEnabled(false);
			txt_email.setEnabled(false);
			
			txt_name.setEnabled(false);
			txt_phone1.setEnabled(false);
			txt_phone2.setEnabled(false);
			txt_phone3.setEnabled(false);
			
			lb_pw.setVisible(false);txt_pw.setVisible(false);
			lb_newPw.setVisible(false);txt_newPw.setVisible(false);
			lb_newPwC.setVisible(false);txt_newPwC.setVisible(false);
			UserView.btn_signOut.setVisible(true);
		}catch (Exception e) {JOptionPane.showMessageDialog(this, "에러3");}
	}
	
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		if(obj == btn_cancel){cancel();}
		if(obj == btn_correct){correct();}
	}
	
	public void cancel(){
		btn_cancel.setVisible(false);
		btn_correct.setText("회원정보 수정");
		
		informationView();
	}
	
	private void correct(){
		
		if(btn_correct.getText().equals("회원정보 수정")){
			lb_pw.setVisible(true);txt_pw.setVisible(true);
			lb_newPw.setVisible(true);txt_newPw.setVisible(true);
			lb_newPwC.setVisible(true);txt_newPwC.setVisible(true);
			txt_addr.setEnabled(true);txt_email.setEnabled(true);
			txt_name.setEnabled(true);
			
			txt_phone1.setEnabled(true);//txt_phone1.setText("");
			txt_phone2.setEnabled(true);//txt_phone2.setText("");
			txt_phone3.setEnabled(true);//txt_phone3.setText("");
			
			btn_correct.setText("수정");btn_cancel.setVisible(true);
			UserView.btn_signOut.setVisible(false);
		}else if(btn_correct.getText().equals("수정")){
			
			
			if(txt_pw.getPassword().length>0&&txt_newPw.getPassword().length>0&&
					txt_newPwC.getPassword().length>0&&txt_addr.getText().length()>0
					&&txt_name.getText().length()>0
					){
			
			pwupdate();
			UserView.btn_signOut.setVisible(true);
			btn_correct.setText("회원정보 수정");btn_cancel.setVisible(false);
			}else{JOptionPane.showMessageDialog(this,"빈칸이 있습니다.");}
		}

	}
	public void pwupdate(){
		String phone=null;
		
		if(txt_phone1.getText().length()==0&&txt_phone2.getText().length()==0&&
				txt_phone3.getText().length()==0){
			phone = "";
		}else if(txt_phone1.getText().length()==3&&txt_phone2.getText().length()==4&&
				txt_phone3.getText().length()==4){
				phone = txt_phone1.getText() + "-" + txt_phone2.getText() + "-" + txt_phone3.getText();
				}else{
			JOptionPane.showMessageDialog(this,"전화번호를 정확히 입력해주세요.");
			informationView();
			return;
		}
		
		txt_phone.setText(phone);
		String sql1 = "select PW from tb_member where id = ? " ;
		String sql2="update tb_member set pw=?,address=?, email=?, phone=?,name=?  where id = ?";
		//txt_phone.setText(phone);
		try{ 
			con1 = DBConnection.getConnection();
			con2 = DBConnection.getConnection();
		pstmt1=con1.prepareStatement(sql1);		
		pstmt1.setString(1, dto_UserInfo.getId());
		rs = pstmt1.executeQuery(); rs.next();
		String pw = rs.getString("pw");
		if(String.valueOf(txt_pw.getPassword()).equals(pw)) {
			if( String.valueOf(txt_newPw.getPassword()).equals(String.valueOf(txt_newPwC.getPassword())) ){
			pstmt2 = con2.prepareStatement(sql2);
			pstmt2.setString(1, String.valueOf(txt_newPw.getPassword()));
			pstmt2.setString(2, txt_addr.getText());
			pstmt2.setString(3, txt_email.getText());
			pstmt2.setString(4, txt_phone.getText());
			pstmt2.setString(5, txt_name.getText());
			pstmt2.setString(6, dto_UserInfo.getId());
			pstmt2.executeUpdate();
			JOptionPane.showMessageDialog(this, "비밀번호 변경 성공하셨습니다. 로그인 다시 해주세요.");
			UserView.btn_closeNoAction.doClick();
			
			}else{JOptionPane.showMessageDialog(this,"새 비밀번호가 일치하지 않습니다. ");informationView();}
			
		}else{JOptionPane.showMessageDialog(this,"비밀번호가 틀렸습니다.");informationView();}
		
	}catch (Exception e) {JOptionPane.showMessageDialog(this, "try에러1");}
	
}


}