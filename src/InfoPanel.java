import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class InfoPanel extends JPanel {
	private JLabel scoreLabel;  // 현재 점수 표시
	
	private Timer timer;
	private JLabel timeLabel; // 남은 시간 표시
	private int timeLeft = 60; //제한시간 (60초)
	
    private GameManager gm;     // 게임매니저 선언
    //private EndPanel endPanel;

    public InfoPanel(MainFrame mf) {
    	//this.endPanel = endPanel;

        gm = GameManager.getInstance();

        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // 점수 표시 라벨
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        //남은 시간 표시 라벨
        timeLabel = new JLabel("  timeleft: 60");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // 시작 화면으로 돌아가기 버튼 
        JButton homeBtn = new JButton("홈으로");
        homeBtn.addActionListener(e -> {
        	//점수를 0으로 세팅하고 화면 전환
        	gm.setScoreZero();
            mf.changePanel("Start");
        });

        // 종료 버튼 
        JButton exitBtn = new JButton("게임 종료");
        exitBtn.addActionListener(e -> System.exit(0));
        
        //테스트 버튼
        JButton testBtn = new JButton("결과 보기");
        testBtn.addActionListener(e-> {
        	mf.changePanel("End");
        });

        // 버튼들 담을 패널
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        //만든 버튼들을 추가
        btnPanel.add(homeBtn);
        btnPanel.add(exitBtn);
        btnPanel.add(testBtn);

        // BorderLayout에 배치
        add(scoreLabel, BorderLayout.WEST);
        add(timeLabel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.EAST);
        
        //타이머 생성
        timer = new Timer(1000, e -> {
        	timeLeft--;
        	timeLabel.setText("  timeleft: " + timeLeft);
        	
        	//남은 시간이 0초가 되면 타이머를 멈추고 종료화면으로 전환
        	if (timeLeft <= 0) {
        		timer.stop();
        		mf.changePanel("End");
        	}
        	
        });
        
        //패널이 생성된 시점에 바로 타이머 시작
        timer.start();
        
        
    }
    
    //점수 업데이트 (나중에 필요 시 외부에서 호출)
    public void updateScore() {
        scoreLabel.setText("Score: " + gm.getScore());
    }

}
