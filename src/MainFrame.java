import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
	
	//인스턴스를 미리 선언
	private CardLayout cardLayout;
    private Container container;
    
	public MainFrame() {
		setSize(600, 400);
		setTitle("Apple Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//컨텐트팬에 배치관리자(CardLayout) 설정
		cardLayout = new CardLayout();      
        container = getContentPane();       
        container.setLayout(cardLayout);
		
		//필요한 패널들 추가
		JPanel startPanel = new StartPanel(this); // 스타트 화면
		JPanel gamePanel = new GamePanel(this); //게임 화면
		JPanel endPanel = new EndPanel(this); //종료 화면
        
		//프레임에 붙임
		add(startPanel, "Start");
		add(gamePanel, "Game");
		add(endPanel, "End");
		
		//처음엔 시작 화면이 보이게
		cardLayout.show(getContentPane(), "Start");
		
		setLocationRelativeTo(null); //모니터 중앙에 오도록
		setVisible(true);
	}
	
	//패널을 전환하는 메서드
    public void changePanel(String name) {
        cardLayout.show(container, name);
    }

}
