import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartPanel extends JPanel {
	
	public StartPanel(MainFrame mf) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("APPLE BOX");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(CENTER_ALIGNMENT);

        JButton startBtn = new JButton("게임 시작");
        startBtn.setAlignmentX(CENTER_ALIGNMENT);

        JButton exitBtn = new JButton("게임 종료");
        exitBtn.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(50));
        add(startBtn);
        add(Box.createVerticalStrut(20));
        add(exitBtn);

        // 시작 버튼
        startBtn.addActionListener(e -> {
            mf.changePanel("Game");
        });

        // 종료 버튼
        exitBtn.addActionListener(e -> {
            System.exit(0); // 프로그램 종료
        });
    }
}
