package etc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MyFunctions {

	/**
	 * 화면 크기를 얻어오는 메소드
	 * 
	 * @return 화면 크기
	 */
	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * 프레임을 기준으로 화면의 중앙 위치를 계산하는 메소드
	 * 
	 * @param component
	 *            컴포넌트
	 * @return 중앙 위치
	 */
	public static Point getCenterLocation(Component component) {
		return new Point((getScreenSize().width - component.getWidth()) / 2,
				(getScreenSize().height - component.getHeight()) / 2);
	}

	/**
	 * 이미지 아이콘 크기 변경 메소드
	 * 
	 * @param imgIcon
	 *            원본 이미지
	 * @param resizeWidth
	 *            변경할 넓이
	 * @param resizeHeight
	 *            변경할 높이
	 * @return 변경된 이미지
	 */
	public static ImageIcon resizeImageIcon(ImageIcon imgIcon, int resizeWidth, int resizeHeight) {
		return new ImageIcon(imgIcon.getImage().getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_SMOOTH));
	}

	/**
	 * Swing 컴포넌트의 모든 폰트를 변경하는 메소드
	 * 
	 * @param f
	 *            폰트 리소스
	 */
	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}

	/**
	 * 빈공간을 JPanel 형태로 리턴하는 메소드
	 * 
	 * @param width
	 *            넓이
	 * @param height
	 *            높이
	 * @param color
	 *            배경색
	 * @return JPanel 형태의 빈공간
	 */
	public static JPanel spacePanel(int width, int height, Color color) {
		JPanel pan = new JPanel();
		color = (color == null) ? new Color(0, 0, 0, 0) : color;

		pan.setBackground(color);
		pan.setPreferredSize(new Dimension(width, height));

		return pan;
	}

}