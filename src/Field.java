import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Field extends JPanel{
	// ���� ������������������ ��������
	private boolean pausedFast;
	private boolean paused;
	// ������������ ������ �������� �����
	private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
	// ����� ������ �������� �� ���������� ��������� ������� ActionEvent
	// ��� �������� ��� ���������� ������������ ��������� �����, 
	// ����������� ��������� ActionListener
	private Timer repaintTimer = new Timer(10, new ActionListener() {
	public void actionPerformed(ActionEvent ev) {
	// ������ ����������� ������� ActionEvent - ����������� ����
	repaint();
	}
	});
	// ����������� ������ BouncingBall
	public Field() {
	// ���������� ���� ������� ���� �����
	setBackground(Color.WHITE);
	// ��������� ������
	repaintTimer.start();
	}
	// �������������� �� JPanel ����� ����������� ����������
	public void paintComponent(Graphics g) {
	// ������� ������ ������, �������������� �� ������
	super.paintComponent(g);
	Graphics2D canvas = (Graphics2D) g;
	// ��������������� ��������� ���������� �� ���� ����� �� ������
	for (BouncingBall ball: balls) {
	ball.paint(canvas);
	}
	}
	// ����� ���������� ������ ���� � ������
	public void addBall() {
	//����������� � ���������� � ������ ������ ���������� BouncingBall
	// ��� ������������� ���������, ��������, �������, ����� 
	// BouncingBall ��������� ��� � ������������
	balls.add(new BouncingBall(this));
	}
	// ����� ������������������, �.�. ������ ���� ����� ����� 
	// ������������ ���� ������
	public synchronized void pauseFast() 
	{
		pausedFast=true;
	}
	
	public synchronized void resumeFast() 
	{
		pausedFast=false;
		notifyAll();
	}
	public synchronized void pause() {
	// �������� ����� �����
	paused = true;
	}
	// ����� ������������������, �.�. ������ ���� ����� ����� 
	// ������������ ���� ������
	public synchronized void resume() {
	// ��������� ����� �����
	paused = false;
	pausedFast=false;
	// ����� ��� ��������� ����������� ������
	notifyAll();
	}
	// ������������������ ����� ��������, ����� �� ��� ��������� 
	// (�� ������� �� ����� �����?)
	public synchronized void canMove(BouncingBall ball) throws
	InterruptedException {
	if (paused) {
	// ���� ����� ����� �������, �� �����, �������� 
	// ������ ������� ������, ��������
	wait();
	}
	}
	public synchronized void canMoveFast(BouncingBall ball) throws
	InterruptedException {
	if (pausedFast) {
	// ���� ����� ����� �������, �� �����, �������� 
	// ������ ������� ������, ��������
	wait();
	}
	}
}
