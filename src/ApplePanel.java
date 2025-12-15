import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ApplePanel extends JPanel {
	GameManager gm = GameManager.getInstance();
	private JLabel[][] labels; // 각 셀을 JLabel로 관리
	private ArrayList<Point> selectedCells = new ArrayList<>();
	private InfoPanel infoPanel; // InfoPanel 인스턴스 -> 인포패널의 점수 란 업데이트를 위함
	//private EndPanel endPanel;
	
	public ApplePanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	    //this.endPanel = endPanel;
		
		//GridLayout과 labels의 행열 크기
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
	    labels = new JLabel[rows][cols]; //숫자 박스 2차원 배열. 맵 데이터와 대응되도록 설계

	    for (int r = 0; r < rows; r++) {
	        for (int c = 0; c < cols; c++) {
	        	
	            JLabel label = new JLabel(); //JLabel 생성
	            label.setHorizontalAlignment(JLabel.CENTER);
	            label.setVerticalAlignment(JLabel.CENTER);
	            label.setOpaque(true); // 배경색 표시 가능하게

	            int value = _map[r][c];
	            if (value != 0) {
	                label.setText(String.valueOf(value)); // 숫자 표시
	                label.setBackground(Color.GREEN);  // 배경색 
	                label.setForeground(Color.BLACK); //글자색
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
	                    whenClicked(fr, fc);
	                }
	            });

	            labels[r][c] = label; //세팅이 완료된 JLabel을 앞서 정의한 JLabel 2차원 배열에 추가.
	            add(label); //JPanel(GridLayout)에 라벨 추가
	        }
	    }
	}

	
	//선택 가능한지 체크하는 메서드
	private boolean isSelecteable(Point a, int r, int c) { //a는 이미 선택된 셀, (r, c)는 선택을 시도하는 셀의 (열, 행)
		int[][] _map = gm.getMap();
		
		//같은 행
		if (a.x == r) {
			//선택상태인 셀들과 떨어져 있는 셀을 선택하고 싶을 때 그 사이구간을 탐색하기 위하여 범위 설정    
	        int start = Math.min(a.y, c) + 1;
	        int end = Math.max(a.y, c);

	        //사이구간을 탐색
	        for (int y = start; y < end; y++) {
	            // 0이 아니고, 선택도 안 된 숫자면 벽으로 간주
	            if (_map[r][y] != 0 && !selectedCells.contains(new Point(r, y))) {
	                return false;
	            }
	        }
	        return true;
	    }
		
		//같은 열
		if (a.y == c) {
	        int start = Math.min(a.x, r) + 1;
	        int end = Math.max(a.x, r);

	        for (int x = start; x < end; x++) {
	            if (_map[x][c] != 0 && !selectedCells.contains(new Point(x, c))) {
	                return false;
	            }
	        }
	        return true;
	    }
		
		//두 분기를 다 통과 못하면 선택 불가능
		return false;
	}
	
	//클릭했을 때 라벨을 선택하는 메서드
	private void whenClicked(int r, int c) {
	    int value = gm.getMap()[r][c]; //정수 맵에서 해당하는 값을 가지고 옮
	    if (value == 0) return; // 빈 칸 무시

	    Point p = new Point(r, c); //selectedCells의 원소와 비교하기 위함

	    // 이미 선택된 셀이면 선택 해제
	    if (selectedCells.contains(p)) {
	        selectedCells.remove(p);
	        labels[r][c].setBackground(Color.GREEN);
	        
	        checkAndRemove(); //선택을 해제했을 때 조건이 만족될 수도 있으니까 호출
	        
	        return;
	    }

	    // 첫 선택은 자유
	    if (selectedCells.isEmpty()) {
	        selectedCells.add(p);
	        labels[r][c].setBackground(Color.YELLOW);
	        return;
	    }

	    //선택 가능한지 아닌지를 판단하는 boolean 지역변수
	    boolean selectable = false;

	    for (Point s : selectedCells) {
	        //int dist = Math.abs(s.x - r) + Math.abs(s.y - c);
	    	if (isSelecteable(s, r, c)) {
	    		selectable = true;
	    		break;
	    	}
//	        if (dist == 1) {
//	            selectable = true;
//	            break;
//	        }
	    }

	    if (!selectable) return; //선택 불가하다면 메서드 종료

	    // selectable==true → 선택
	    selectedCells.add(p);
	    labels[r][c].setBackground(Color.YELLOW);

	    checkAndRemove(); //조건 만족 체크 후 삭제 
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

}