
public class GameManager {
	
	//싱글톤 패턴 사용
	private static GameManager instance = null;
	
	//점수와 정수 맵은 여기서 관리
	private int score = 0;
	private int[][] map = new int[17][10]; //초기화(메모리 할당)
	
	// 생성자를 private으로 하기
    private GameManager() {}

    // 필요할 때만 인스턴스를 하나 생성해서 반환
    public static GameManager getInstance() {
        if (instance == null) {      // 처음 호출될 때 생성됨
            instance = new GameManager();
        }
        return instance;
    }
    
    // 점수 증가 메서드
    public void addScore(int value) {
        score += value;
    }
    
    //현재 스코어를 반환하는 메서드
    public int getScore() {
        return score;
    }
    
    //스코어 초기화 메서드
    public void setScoreZero() {
    	score = 0;
    }
    
    //사과 맵 초기화 메서드
    public void setMap() {
    	for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = (int)(Math.random() * 9) + 1; // 1~9 과일 랜덤
            }
        }
    }
    
    //현재 정수 맵을 반환하는 메서드
    public int[][] getMap() {
        return map;
    }
	
}
