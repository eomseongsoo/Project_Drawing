package unit1;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameSetting extends Frame {

	// 메뉴바와 그리기 메뉴, 그리그 메뉴 하위 메뉴 변수설정
	private MenuBar mb = new MenuBar();
	
	private Menu File = new Menu("File");
	private MenuItem Save=new MenuItem("Save");
	private MenuItem Open=new MenuItem("Open");
	private MenuItem Exit=new MenuItem("Exit");
	private MenuItem New=new MenuItem("New");
	
	
	// 메뉴바로 구현할지말지 미정이다... 실제 그림판 처럼 기호로 메뉴바가 아닌 바로 버튼을 클릭해서 구현하는 것이 좋을 것 같다..
	private Menu draw = new Menu("Draw");
	private MenuItem pen = new MenuItem("Pen"); // 펜 그리기
	private MenuItem line = new MenuItem("Line"); // 선 그리기
	private MenuItem Circle = new MenuItem("Circle"); // 원 그리기
	private MenuItem recg = new MenuItem("Recgtangle"); // 사각형 그리기

	// 프레임 설정
	public FrameSetting(String title) {
		super(title);

		this.setMenuBar(mb);
		mb.add(File);
		File.add(New);
		File.add(Open);
		File.add(Save);
		File.add(Exit);
		
		mb.add(draw);
		draw.add(pen);
		draw.add(line);
		draw.add(Circle);
		draw.add(recg);

		super.setSize(300, 200);
		// Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// Dimension frame = super.getSize();
		setVisible(true);

	}

	public void paint(Graphics g) {

	}

}
