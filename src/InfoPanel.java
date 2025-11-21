import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class InfoPanel extends JPanel {
	private JLabel scoreLabel;  // 현재 점수 표시
    private GameManager gm;     // 싱글톤

    public InfoPanel(MainFrame mf) {

        gm = GameManager.getInstance();

        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // 점수 표시 라벨
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        //scoreLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

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
        JButton testBtn = new JButton("테스트");
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
        add(btnPanel, BorderLayout.EAST);
    }

}
