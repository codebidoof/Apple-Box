import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EndPanel extends JPanel {
	
	private JLabel scoreLabel;
    private GameManager gm;

    public EndPanel(MainFrame mf) {

        gm = GameManager.getInstance();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        // 타이틀
        JLabel title = new JLabel("게임 종료!");
        //title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(CENTER_ALIGNMENT);

        // 점수 표시
        scoreLabel = new JLabel("Score: " + gm.getScore());
        
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);

        // 버튼들 담을 패널
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setOpaque(false); // 배경 따라가기

        // 홈 버튼 정의
        JButton homeBtn = new JButton("홈으로");
        homeBtn.setAlignmentX(CENTER_ALIGNMENT);
        homeBtn.addActionListener(e -> {
            gm.setScoreZero();
            
            mf.changePanel("Start");
        });

        // 다시 하기 버튼 정의
        JButton restartBtn = new JButton("다시 하기");
        restartBtn.setAlignmentX(CENTER_ALIGNMENT);
        restartBtn.addActionListener(e -> {
            gm.setScoreZero();
            mf.changePanel("Game");
        });

        // 종료 버튼 정의
        JButton exitBtn = new JButton("게임 종료");
        exitBtn.setAlignmentX(CENTER_ALIGNMENT);
        exitBtn.addActionListener(e -> System.exit(0));

        // 버튼들을 btnPanel에 세로로 정렬 + 간격
        btnPanel.add(homeBtn);
        btnPanel.add(Box.createVerticalStrut(10));
        btnPanel.add(restartBtn);
        btnPanel.add(Box.createVerticalStrut(10));
        btnPanel.add(exitBtn);

        // EndPanel 구성
        add(Box.createVerticalStrut(60));  // 위쪽 공간
        add(title);
        add(Box.createVerticalStrut(20));
        add(scoreLabel);
        add(Box.createVerticalStrut(40));
        add(btnPanel);                     // 버튼 묶음 중앙
    }
    
    //점수 갱신 메서드
    public void updateScore() {
        scoreLabel.setText("Score: " + gm.getScore());
    }
   
}
