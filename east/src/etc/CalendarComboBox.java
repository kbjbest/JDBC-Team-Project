package etc;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CalendarComboBox extends JPanel implements ItemListener {

	public JComboBox<Integer> cb_Year, cb_Month, cb_Day;
	private int year, month, day;
	
	private Calendar date;
	
	private int minYear, maxYear;
	
	private void setDefaultYearRange() {
		minYear = date.get(Calendar.YEAR);
		maxYear = minYear + 3;
	}
	
	public CalendarComboBox() {
		// TODO CalendarComboBox 생성자
		date = Calendar.getInstance();
		
		cb_Year = new JComboBox<>();
		cb_Month = new JComboBox<>();
		cb_Day = new JComboBox<>();
		
		cb_Year.setSize(50,25);
		cb_Month.setSize(50,25);
		cb_Day.setSize(50,25);
		
		setDefaultYearRange();
		
		for (int y = minYear; y < maxYear; y++) {
			cb_Year.addItem(y);
		}
		
		for (int m = 1; m <= 12; m++) {
			cb_Month.addItem(m);
		}
		
		for (int d = 1; d <= date.getActualMaximum(Calendar.DATE); d++) {
			cb_Day.addItem(d);
		}
		
		add(cb_Year);
		add(new JLabel("  "));
		add(cb_Month);
		add(new JLabel("  "));
		add(cb_Day);
		
		cb_Year.addItemListener(this);
		cb_Month.addItemListener(this);
		cb_Day.addItemListener(this);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		// TODO 해당 Panel의 활성 여부에 따라 내부 컴포넌트의 활성 여부를 결정한다.
		cb_Year.setEnabled(enabled);
		cb_Month.setEnabled(enabled);
		cb_Day.setEnabled(enabled);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 콤보박스의 값이 변경된 경우
		
		if (e.getSource() == cb_Year) {
			if (cb_Year.getSelectedItem() != null) {
				year = (int)cb_Year.getSelectedItem();
				date.set(Calendar.YEAR, year);				
			}
			
		} else if (e.getSource() == cb_Month) {
			if (cb_Month.getSelectedItem() != null) {
				month = (int)cb_Month.getSelectedItem();
				date.set(Calendar.MONTH, month - 1);				
			}
			
		} else if (e.getSource() == cb_Day) {
			if (cb_Day.getSelectedItem() != null) {
				day = (int)cb_Day.getSelectedItem();
				date.set(Calendar.DATE, day);
			}
		}
		
		if (e.getSource() != cb_Day) {
			cb_Day.removeAllItems();
			for (int d = 1; d <= date.getActualMaximum(Calendar.DATE); d++) {
				cb_Day.addItem(d);
			}
		}
	}
	
	public void setYearRange(int minYear, int maxYear) {
		this.minYear = minYear;
		this.maxYear = maxYear;
		
		if (minYear > maxYear) {
			System.err.println("날짜 범위가 잘못 설정되었습니다.\n"
					+ "minYear(" + minYear + ")가 maxYear(" + maxYear + ")보다 큼.");
			setDefaultYearRange();
			return;
		}
		
		cb_Year.removeAllItems();
		for (int y = minYear; y <= maxYear; y++) {
			cb_Year.addItem(y);
		}
		for (int i = 0; i < cb_Year.getItemCount(); i++) {
			if (cb_Year.getItemAt(i) == date.get(Calendar.YEAR)) {
				cb_Year.setSelectedIndex(i);
				return;
			}
		}
	}
	
	public void setDate(int year, int month, int day) {
		this.year = year;
		this.month = month + 1;
		this.day = day;
		cb_Year.setSelectedItem(year);
		cb_Month.setSelectedItem(month + 1);
		cb_Day.setSelectedItem(day);
	}
	
	public Date getDate() {
		Date d = new Date(date.getTimeInMillis());
		return d;
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
}
