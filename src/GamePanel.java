import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
	
	public GamePanel(MainFrame mf) {
		setLayout(new BorderLayout());
		InfoPanel infoPanel = new InfoPanel(mf);
		ApplePanel applePanel = new ApplePanel(infoPanel); 
		add(infoPanel, BorderLayout.NORTH); //인포패널 생성 후 추가
		add(applePanel, BorderLayout.CENTER); //애플패널 생성 후 추가
	}

}
