package unit1;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameSetting extends Frame {

	// �޴��ٿ� �׸��� �޴�, �׸��� �޴� ���� �޴� ��������
	private MenuBar mb = new MenuBar();
	
	private Menu File = new Menu("File");
	private MenuItem Save=new MenuItem("Save");
	private MenuItem Open=new MenuItem("Open");
	private MenuItem Exit=new MenuItem("Exit");
	private MenuItem New=new MenuItem("New");
	
	
	// �޴��ٷ� ������������ �����̴�... ���� �׸��� ó�� ��ȣ�� �޴��ٰ� �ƴ� �ٷ� ��ư�� Ŭ���ؼ� �����ϴ� ���� ���� �� ����..
	private Menu draw = new Menu("Draw");
	private MenuItem pen = new MenuItem("Pen"); // �� �׸���
	private MenuItem line = new MenuItem("Line"); // �� �׸���
	private MenuItem Circle = new MenuItem("Circle"); // �� �׸���
	private MenuItem recg = new MenuItem("Recgtangle"); // �簢�� �׸���

	// ������ ����
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
