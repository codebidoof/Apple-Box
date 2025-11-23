import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ApplePanel extends JPanel {
	GameManager gm = GameManager.getInstance();
	//private int[][] _map; 
	private JLabel[][] labels; // 각 셀을 JLabel로 관리
	private ArrayList<Point> selectedCells = new ArrayList<>();
	private InfoPanel infoPanel; // InfoPanel 참조 -> 인포패널의 점수 란 업데이트를 위함
	//private EndPanel endPanel;
	
	public ApplePanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	    //this.endPanel = endPanel;
		
		int rows = 17;
        int cols = 10;
		setLayout(new GridLayout(rows, cols, 2, 2));
		
		// 맵 초기화
        gm.setMap();
		//JLabel 배열 초기화
		setLabels();
		
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
	            
	            //익명 클래스 안에서 사용하는 지역 변수는 반드시 final 또는 effectively final 이어야 한다.
	            //따라서 final 변수를 만들고 거기다 반복문의 지역변수 값을 복사해옴
	            final int fr = r;
	            final int fc = c;

	            // 라벨들에 클릭 리스너 추가
	            label.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    onCellClicked(fr, fc);
	                }
	            });

	            labels[r][c] = label;
	            add(label);
	        }
	    }
	}
	
	//클릭했을 때 라벨을 선택하는 함수
	private void onCellClicked(int r, int c) {
	    int value = gm.getMap()[r][c]; //정수 맵에서 해당하는 값을 가지고 옮
	    if (value == 0) return; // 빈 칸 무시

	    Point p = new Point(r, c);

	    // 이미 선택된 셀이면 선택 해제
	    if (selectedCells.contains(p)) {
	        selectedCells.remove(p);
	        labels[r][c].setBackground(Color.GREEN);
	        return;
	    }

	    // 첫 선택은 자유
	    if (selectedCells.isEmpty()) {
	        selectedCells.add(p);
	        labels[r][c].setBackground(Color.YELLOW);
	        return;
	    }

	    //지금까지 선택된 셀 중 하나라도 인접하면 선택 가능
	    boolean adjacent = false;

	    for (Point s : selectedCells) {
	        int dist = Math.abs(s.x - r) + Math.abs(s.y - c);
	        if (dist == 1) {
	            adjacent = true;
	            break;
	        }
	    }

	    if (!adjacent) return; // 인접한 셀이 없다면 선택 불가

	    // 인접 → 선택
	    selectedCells.add(p);
	    labels[r][c].setBackground(Color.YELLOW);

	    checkAndRemove();
	}
	
	//선택된 셀의 합이 10이면 제거하는 함수
	private void checkAndRemove() {
	    if (selectedCells.isEmpty()) return;

	    int[][] map = gm.getMap();
	    int sum = 0;

	    for (Point p : selectedCells) {
	        sum += map[p.x][p.y];
	    }

	    if (sum == 10) {
	        // 제거
	        for (Point p : selectedCells) {
	            map[p.x][p.y] = 0;
	        }
	        
	        //사라질 셀 개수만큼 점수 증가
	        gm.addScore(selectedCells.size());
	        System.out.println(gm.getScore()); //점수 출력. 디버깅용

	        selectedCells.clear();
	        
	        // InfoPanel 점수 업데이트
            if (infoPanel != null) {
                infoPanel.updateScore();
            }
            
//            if (endPanel != null) {
//            	endPanel.updateScore();
//            }

	        // UI 업데이트
	        removeAll(); //모든 JLabel을 제거
	        setLabels(); //다시 라벨들을 세팅
	        revalidate(); //배치관리자에게 자식컴포넌트를 재배치하도록 지시
	        repaint(); //컴포넌트의 변화를 알려주고, 강제로 페인팅할 것을 지시
	    }
	}
	
	// 필요하면 InfoPanel에 접근
    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

}