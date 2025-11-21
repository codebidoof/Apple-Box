import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ApplePanel extends JPanel {
	GameManager gm = GameManager.getInstance();
	//private int[][] _map; 
	private JLabel[][] labels; // 각 셀을 JLabel로 관리
	
	public ApplePanel() {
		int rows = 17;
        int cols = 10;
		setLayout(new GridLayout(rows, cols, 2, 2));
		
		// 맵 초기화
        gm.setMap();
		//JLabel 배열 초기화
		setLabels();
        labels = new JLabel[rows][cols];
		
	}
	
	//정수 배열을 보고 JLabel들을 만드는 메서드
	private void setLabels() {
		int[][] _map = gm.getMap(); //현재 맵 상태를 불러옴
	    int rows = _map.length;
	    int cols = _map[0].length;
	    labels = new JLabel[rows][cols];

	    for (int r = 0; r < rows; r++) {
	        for (int c = 0; c < cols; c++) {
	            JLabel label = new JLabel();
	            label.setHorizontalAlignment(JLabel.CENTER);
	            label.setVerticalAlignment(JLabel.CENTER);
	            label.setOpaque(true); // 배경색 표시 가능하게

	            int value = _map[r][c];
	            if (value != 0) {
	                label.setText(String.valueOf(value)); // 숫자 표시
	                label.setBackground(Color.GREEN);    // 배경색 
	                label.setForeground(Color.BLACK);
	            } else { //정수 배열 값이 0일 경우
	                label.setText(""); // 빈 칸
	                label.setBackground(Color.WHITE);
	            }

	            labels[r][c] = label;
	            add(label);
	        }
	    }
	}
	
	
	

}
