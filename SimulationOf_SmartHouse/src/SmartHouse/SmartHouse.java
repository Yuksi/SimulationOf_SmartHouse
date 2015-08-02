package SmartHouse;

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;

import alive.Man;
import controllers.Controller;
import lights.LightCeil;
import rooms.Hall;
import rooms.Room;
import sensor.SensorMan;

public class SmartHouse extends JFrame {
	private static int xClicked=0;
	private static int yClicked=0;
	
	public static int getXClick() {
		return xClicked;
	}
	public static int getYClick() {
		return yClicked;
	}
	

	public class PicturePanel extends JPanel {
		// Храним 2 изображения: оригинальное и текущее.
		// Оригинальное используется для получения текущего в зависимости от размеров панели.
		// Текущее непосредственно прорисовывается на панели.
		private BufferedImage originalImage = null;
		private Image image = null;
		public PicturePanel() {
			initComponents();
			addMouseListener(new JFrameMouseListener());
		}

		private void initComponents() {
			setLayout(null);
			addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent evt) {
					formComponentResized(evt);
				}
			});
		}

		private void formComponentResized(ComponentEvent evt) {		// Реакция на изменение размеров панели - изменение размера изображения.
			int w = this.getWidth();
			int h = this.getHeight();
			if ((originalImage != null) && (w > 0) && (h > 0)) {
				image = originalImage.getScaledInstance(740, 640, Image.SCALE_DEFAULT);
				this.repaint();
			}
		}
		
		public void paintMan(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawOval(man.getA()-20, man.getB()-20, 40, 40);
			g.setColor(Color.RED);
			g.fillOval(man.getA()-20, man.getB()-20, 40, 40);
			g.setColor(Color.WHITE);
			g.fillOval(man.getA(), man.getB()-10, 10,16);
			g.fillOval(man.getA()-10, man.getB()-10, 10,16);
			g.drawLine(man.getA()-7, man.getB()+11, man.getA()+3, man.getB()+12);
			g.setColor(Color.BLACK);
			g.fillOval(man.getA()+3, man.getB()-5, 5,5);
			g.fillOval(man.getA()-7, man.getB()-5, 5,5);
		}
		
		public void paintSensors(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawArc(sMHall1.getX()-5, sMHall1.getY()-5, 10, 10, 0,-180);
			g.drawArc(sMHall2.getX(), sMHall2.getY()-15, 10, 10, 90,180);
			g.drawOval(sMRoom1.getX()+5, sMRoom1.getY(), 10, 10);
			g.drawOval(sMRoom2.getX()+5, sMRoom2.getY(), 10, 10);
			g.drawOval(sMKitch1.getX()+5, sMKitch1.getY(), 10, 10);
			g.drawOval(sMKitch2.getX()+5, sMKitch2.getY(), 10, 10);
			g.drawOval(sMBath.getX(), sMBath.getY()-15, 10, 10);
			
			if (sMHall1.seeMan(flat, man)) {
				g.setColor(Color.BLUE);
				g.fillArc(sMHall1.getX()-5, sMHall1.getY()-5, 10, 10, 0,-180);
			}
			else {
				g.setColor(Color.CYAN);
				g.fillArc(sMHall1.getX()-5, sMHall1.getY()-5, 10, 10, 0,-180);
			}
			
			if (sMHall2.seeMan(flat, man)) {
				g.setColor(Color.BLUE);
				g.fillArc(sMHall2.getX(), sMHall2.getY()-15, 10, 10, 90,180);
			}
			else {
				g.setColor(Color.CYAN);
				g.fillArc(sMHall2.getX(), sMHall2.getY()-15, 10, 10, 90,180);
			}
			
			for (int i=0; i<sManRoom.length; i++) {
				if (sManRoom[i].seeMan(flat, man)) {
					g.setColor(Color.BLUE);
					g.fillOval(sManRoom[i].getX()+5, sManRoom[i].getY(), 10, 10);
				}
				else {
					g.setColor(Color.CYAN);
					g.fillOval(sManRoom[i].getX()+5, sManRoom[i].getY(), 10, 10);
				}
			}
			for (int i=0; i<sManKitch.length; i++) {
				if (sManKitch[i].seeMan(flat, man)) {
					g.setColor(Color.BLUE);
					g.fillOval(sManKitch[i].getX()+5, sManKitch[i].getY(), 10, 10);
				}
				else {
					g.setColor(Color.CYAN);
					g.fillOval(sManKitch[i].getX()+5, sManKitch[i].getY(), 10, 10);
				}
			}
			
			if (sMBath.seeMan(flat, man)) {
				g.setColor(Color.BLUE);
				g.fillOval(sMBath.getX(), sMBath.getY()-15, 10, 10);
			}
			else {
				g.setColor(Color.CYAN);
				g.fillOval(sMBath.getX(), sMBath.getY()-15, 10, 10);
			}
		}
		
		public void paintLightsCeil(Graphics g) {
			g.setColor(Color.YELLOW);
			g.drawRect(lC1Room.getX()+5, lC1Room.getY(), 15, 15);
			g.drawRect(lC2Room.getX()+5, lC2Room.getY(), 15, 15);
			g.drawRect(lC1Kitch.getX()+5, lC1Kitch.getY(), 15, 15);
			g.drawRect(lC2Kitch.getX()+5, lC2Kitch.getY(), 15, 15);
			g.drawRect(lC1Bath.getX(), lC1Bath.getY()-15, 15, 15);
			g.drawRect(lC2Bath.getX(), lC2Bath.getY()-15, 15, 15);
			g.drawRect(lC1Hall.getX(), lC1Hall.getY()-15, 15, 15);
			g.drawRect(lC2Hall.getX(), lC2Hall.getY()-15, 15, 15);
			g.drawRect(lC3Hall.getX(), lC3Hall.getY()-15, 15, 15);
			g.drawRect(lC4Hall.getX(), lC4Hall.getY()-15, 15, 15);
			
			for (int i=0; i<lCRoom.length; i++) {
				if (lCRoom[i].getFlare()) {
					g.setColor(Color.YELLOW);
					g.fillRect(lCRoom[i].getX()+6, lCRoom[i].getY()+1, 14, 14);
					g.setColor(new Color(255, 255, 50, 50));
					g.fillOval(lCRoom[i].getX()-lCRoom[i].getRad(), lCRoom[i].getY()-lCRoom[i].getRad(), lCRoom[i].getRad()*2, lCRoom[i].getRad()*2);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(lCRoom[i].getX()+6, lCRoom[i].getY()+1, 14, 14);
				}
			}
			for (int i=0; i<lCKitch.length; i++) {
				if (lCKitch[i].getFlare()) {
					g.setColor(Color.YELLOW);
					g.fillRect(lCKitch[i].getX()+6, lCKitch[i].getY()+1, 14, 14);
					g.setColor(new Color(255, 255, 50, 50));
					g.fillOval(lCKitch[i].getX()-lCKitch[i].getRad(), lCKitch[i].getY()-lCKitch[i].getRad(), lCKitch[i].getRad()*2, lCKitch[i].getRad()*2);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(lCKitch[i].getX()+6, lCKitch[i].getY()+1, 14, 14);
				}
			}
			for (int i=0; i<lCBath.length; i++) {
				if (lCBath[i].getFlare()) {
					g.setColor(Color.YELLOW);
					g.fillRect(lCBath[i].getX()+1, lCBath[i].getY()-14, 14, 14);
					g.setColor(new Color(255, 255, 50, 80));
					g.fillOval(lCBath[i].getX()-lCBath[i].getRad(), lCBath[i].getY()-lCBath[i].getRad(), lCBath[i].getRad()*2, lCBath[i].getRad()*2);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(lCBath[i].getX()+1, lCBath[i].getY()-14, 14, 14);
				}
			}
			for (int i=0; i<lCHall.length; i++) {
				if (lCHall[i].getFlare()) {
					g.setColor(Color.YELLOW);
					g.fillRect(lCHall[i].getX()+1, lCHall[i].getY()-14, 14, 14);
					g.setColor(new Color(255, 255, 50, 50));
					g.fillOval(lCHall[i].getX()-lCHall[i].getRad(), lCHall[i].getY()-lCHall[i].getRad(), lCHall[i].getRad()*2, lCHall[i].getRad()*2);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(lCHall[i].getX()+1, lCHall[i].getY()-14, 14, 14);
				}
			}
		}
		
		public void paint(Graphics g) {
			if (image != null) {					// Рисуем картинку
				g.drawImage(image, 0, 0, null);
			}
			super.paintChildren(g);			// Рисуем подкомпоненты.
			super.paintBorder(g);			// Рисуем рамку      
			//g.drawOval(x, y, 40, 40);
			
			paintMan(g);
			paintSensors(g);
			paintLightsCeil(g);
		}

		// Методы для настройки картинки.
		public BufferedImage getImage() {
			return originalImage;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
		}
		public void setImageFile(File imageFile) {
			try {
				if (imageFile == null) {
					originalImage = null;
				}
				BufferedImage bi = ImageIO.read(imageFile);
				originalImage = bi;
			} catch (IOException ex) {
				System.err.println("Error image");
				ex.printStackTrace();
			}
			repaint();
		}
	}

	private static JToggleButton followLight, lightInRoom, lightInKitch, lightInBath, lightInHall, lights;
	private JPanel jPan;
	public static PicturePanel picPan;

	public SmartHouse() {
		initComponents();
		setSize(740, 670);
	}

	private void initComponents() {
		
		picPan = new PicturePanel();
		jPan = new JPanel();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		picPan.setLayout(new BorderLayout());
		picPan.setImageFile(new File("./src/images/plan.jpg"));
		jPan.setLayout(new BoxLayout(jPan,BoxLayout.Y_AXIS));
		jPan.setOpaque(false);
		setResizable(false);
		setTitle("This is Simulation of the system: SmartHouse");

		ImageIcon icon = new ImageIcon("./src/images/eye2.png");
		followLight = new JToggleButton(icon);
		followLight.setSelectedIcon(new ImageIcon("./src/images/eye.png"));
		followLight.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		followLight.setMaximumSize(new Dimension(80, 500));
		followLight.setMinimumSize(new Dimension(20, 20));
		followLight.setPreferredSize(new Dimension(80, 80));
		
		ImageIcon iconLightRoom = new ImageIcon("./src/images/light_of.png");
		lightInRoom = new JToggleButton(iconLightRoom);
		lightInRoom.setSelectedIcon(new ImageIcon("./src/images/light_on.png"));
		lightInRoom.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		lightInRoom.setMaximumSize(new Dimension(80, 500));
		lightInRoom.setMinimumSize(new Dimension(20, 20));
		lightInRoom.setPreferredSize(new Dimension(80, 80));
		
		ImageIcon iconLightKitch = new ImageIcon("./src/images/light_of_kitch.png");
		lightInKitch = new JToggleButton(iconLightKitch);
		lightInKitch.setSelectedIcon(new ImageIcon("./src/images/light_on_kitch.png"));
		lightInKitch.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		lightInKitch.setMaximumSize(new Dimension(80, 500));
		lightInKitch.setMinimumSize(new Dimension(20, 20));
		lightInKitch.setPreferredSize(new Dimension(80, 80));
		
		ImageIcon iconLightBath = new ImageIcon("./src/images/light_of_bath.png");
		lightInBath = new JToggleButton(iconLightBath);
		lightInBath.setSelectedIcon(new ImageIcon("./src/images/light_on_bath.png"));
		lightInBath.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		lightInBath.setMaximumSize(new Dimension(80, 500));
		lightInBath.setMinimumSize(new Dimension(20, 20));
		lightInBath.setPreferredSize(new Dimension(80, 80));
		
		ImageIcon iconLightHall = new ImageIcon("./src/images/light_of_hall.png");
		lightInHall = new JToggleButton(iconLightHall);
		lightInHall.setSelectedIcon(new ImageIcon("./src/images/light_on_hall.png"));
		lightInHall.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		lightInHall.setMaximumSize(new Dimension(80, 500));
		lightInHall.setMinimumSize(new Dimension(20, 20));
		lightInHall.setPreferredSize(new Dimension(80, 80));
		
		ImageIcon iconLight = new ImageIcon("./src/images/all_of.png");
		lights = new JToggleButton(iconLight);
		lights.setSelectedIcon(new ImageIcon("./src/images/all_on.png"));
		lights.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		lights.setMaximumSize(new Dimension(80, 500));
		lights.setMinimumSize(new Dimension(20, 20));
		lights.setPreferredSize(new Dimension(80, 80));
		
		Box box = Box.createVerticalBox();
		box.add(followLight);
		box.add(Box.createVerticalStrut(0));
		box.add(lightInRoom);
		box.add(Box.createVerticalStrut(0));
		box.add(lightInKitch);
		box.add(Box.createVerticalStrut(0));
		box.add(lightInBath);
		box.add(Box.createVerticalStrut(0));
		box.add(lightInHall);
		box.add(Box.createVerticalStrut(0));
		box.add(lights);

		ItemListener followLightListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					LightsFollowProfileOn();
					followLights();
					repaint();
				} else {
					LightsFollowProfileOf();
					contrRoom.turnOFlineCeil();
					contrKitch.turnOFlineCeil();
					contrBath.turnOFlineCeil();
					contrHall.turnOFlineCeil();;
					repaint();
				}
			}
		};
		
		ItemListener lightInRoomListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					contrRoom.turnONlineCeil();
					repaint();
				} else {
					contrRoom.turnOFlineCeil();;
					repaint();
				}
			}
		};
		
		ItemListener lightInKitchListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					contrKitch.turnONlineCeil();
					repaint();
				} else {
					contrKitch.turnOFlineCeil();;
					repaint();
				}
			}
		};
		
		ItemListener lightInBathListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					contrBath.turnONlineCeil();
					repaint();
				} else {
					contrBath.turnOFlineCeil();;
					repaint();
				}
			}
		};
		
		ItemListener lightInHallListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					contrHall.turnONlineCeil();
					repaint();
				} else {
					contrHall.turnOFlineCeil();;
					repaint();
				}
			}
		};
		
		ItemListener lightsListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					contrRoom.turnONlineCeil();
					contrKitch.turnONlineCeil();
					contrBath.turnONlineCeil();
					contrHall.turnONlineCeil();
					repaint();
				} else {
					contrRoom.turnOFlineCeil();
					contrKitch.turnOFlineCeil();
					contrBath.turnOFlineCeil();
					contrHall.turnOFlineCeil();;
					repaint();
				}
			}
		};

		followLight.addItemListener(followLightListener);
		lightInRoom.addItemListener(lightInRoomListener);
		lightInKitch.addItemListener(lightInKitchListener);
		lightInBath.addItemListener(lightInBathListener);
		lightInHall.addItemListener(lightInHallListener);
		lights.addItemListener(lightsListener);
		
		jPan.add(box);
		picPan.add(jPan, BorderLayout.NORTH);
		getContentPane().add(picPan, BorderLayout.CENTER);
	}

	private class JFrameMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			if (e.getX()>15*n+20) {
				xClicked=15*n+20;
			}
			if (e.getY()>15*n) {
				yClicked=15*n;
			}
			if (e.getX()<1*n) {
				xClicked=1*n;
			}
			if (e.getY()<1*n) {
				yClicked=1*n;
			}
			
			if (e.getX()>=1*n&&e.getX()<=15*n) {
				if (e.getX()>bedroom.getX2()&&e.getX()<bedroom.getX2()+33&&e.getY()<=bedroom.getY2()&&e.getY()>bedroom.getY1())
					xClicked = bedroom.getX2()+33;
				else if (e.getX()<=bedroom.getX2()&&e.getX()>bedroom.getX2()-n/2&&e.getY()<=bedroom.getY2()&&e.getY()>bedroom.getY1())
					xClicked = bedroom.getX2()-15;
				else if (e.getX()>bathroom.getX1()&&e.getX()<bathroom.getX1()+33&&e.getY()<bathroom.getY2()&&e.getY()>=bathroom.getY1())
					xClicked = bathroom.getX1()+33;
				else if (e.getX()<=bathroom.getX1()&&e.getX()>bathroom.getX1()-n/2&&e.getY()<bathroom.getY2()&&e.getY()>bathroom.getY1())
					xClicked = bathroom.getX1()-15;
				else
					xClicked=e.getX();
			}
			if (e.getY()>=1*n&&e.getY()<=15*n) {
				if (e.getY()>=hallway.getY2()&&e.getY()<hallway.getY2()+n/2&&e.getX()>hallway.getX1()&&e.getX()<=hallway.getX2()+20)
					yClicked=hallway.getY2()+n/2;
				else if (e.getY()<hallway.getY2()&&e.getY()>hallway.getY2()-n/2&&e.getX()>hallway.getX1()&&e.getX()<=hallway.getX2()+20)
					yClicked=hallway.getY2()-25;
				else if (e.getY()>=kitchen.getY2()&&e.getY()<kitchen.getY2()+n/2&&e.getX()>=kitchen.getX1()&&e.getX()<kitchen.getX2()+20)
					yClicked=hallway.getY1()+n/2;
				else if (e.getY()<kitchen.getY2()&&e.getY()>kitchen.getY2()-n/2&&e.getX()>=kitchen.getX1()&&e.getX()<kitchen.getX2()+20)
					yClicked=hallway.getY1()-25;
				else
				yClicked=e.getY();
			}
			click.setLocation(xClicked,yClicked);
			man.goClick();
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			if (e.getX()>15*n+20) {
				xClicked=15*n+20;
			}
			if (e.getY()>15*n) {
				yClicked=15*n;
			}
			if (e.getX()<1*n) {
				xClicked=1*n;
			}
			if (e.getY()<1*n) {
				yClicked=1*n;
			}
			
			if (e.getX()>=1*n&&e.getX()<=15*n) {
				if (e.getX()>bedroom.getX2()&&e.getX()<bedroom.getX2()+33&&e.getY()<=bedroom.getY2()&&e.getY()>bedroom.getY1())
					xClicked = bedroom.getX2()+33;
				else if (e.getX()<=bedroom.getX2()&&e.getX()>bedroom.getX2()-n/2&&e.getY()<=bedroom.getY2()&&e.getY()>bedroom.getY1())
					xClicked = bedroom.getX2()-15;
				else if (e.getX()>bathroom.getX1()&&e.getX()<bathroom.getX1()+33&&e.getY()<bathroom.getY2()&&e.getY()>=bathroom.getY1())
					xClicked = bathroom.getX1()+33;
				else if (e.getX()<=bathroom.getX1()&&e.getX()>bathroom.getX1()-n/2&&e.getY()<bathroom.getY2()&&e.getY()>bathroom.getY1())
					xClicked = bathroom.getX1()-15;
				else
					xClicked=e.getX();
			}
			if (e.getY()>=1*n&&e.getY()<=15*n) {
				if (e.getY()>=hallway.getY2()&&e.getY()<hallway.getY2()+n/2&&e.getX()>hallway.getX1()&&e.getX()<=hallway.getX2()+20)
					yClicked=hallway.getY2()+n/2;
				else if (e.getY()<hallway.getY2()&&e.getY()>hallway.getY2()-n/2&&e.getX()>hallway.getX1()&&e.getX()<=hallway.getX2()+20)
					yClicked=hallway.getY2()-25;
				else if (e.getY()>=kitchen.getY2()&&e.getY()<kitchen.getY2()+n/2&&e.getX()>=kitchen.getX1()&&e.getX()<kitchen.getX2()+20)
					yClicked=hallway.getY1()+n/2;
				else if (e.getY()<kitchen.getY2()&&e.getY()>kitchen.getY2()-n/2&&e.getX()>=kitchen.getX1()&&e.getX()<kitchen.getX2()+20)
					yClicked=hallway.getY1()-25;
				else
				yClicked=e.getY();
			}
			click.setLocation(xClicked,yClicked);
			man.goClick();
		}
		public void mouseReleased(MouseEvent e) {}
	}


	public static int n=40;

	private static Man man = new Man(1*n,14*n);							//объект человечек, который будет двигаться к точке (в планах добавить управление им с клавитатуры кнопками стрелки)
	private static Point click = new Point();				//создаём точку, которую мы ставим мышкой (с появлением интерфейса - она уйдёт)

	private static Room bedroom = new Room(0*n, 8*n, 0*n, 12*n, 6*n, 11*n, 6*n, 13*n);			//комнаты с необходимыми параметрами
	private static Room kitchen = new Room(8*n, 16*n, 0*n, 10*n, 10*n, 9*n, 10*n, 11*n);
	private static Room bathroom = new Room(12*n, 16*n, 10*n, 16*n, 13*n, 14*n, 11*n, 14*n);
	private static Hall hallway = new Hall(0*n, 8*n, 12*n, 10*n, 12*n, 16*n, 9*n, 13*n, 9*n, 13*n);			
	private static Room[] flat = {bedroom, bathroom, kitchen, hallway};
	
	private static SensorMan sMHall1 = new SensorMan(4*n, 12*n);
	private static SensorMan sMHall2 = new SensorMan(12*n, 13*n);
	private static SensorMan sMRoom1 = new SensorMan(4*n, 8*n);
	private static SensorMan sMRoom2 = new SensorMan(4*n, 4*n);
	private static SensorMan sMKitch1 = new SensorMan(12*n, 6*n);
	private static SensorMan sMKitch2 = new SensorMan(12*n, 4*n);
	private static SensorMan sMBath = new SensorMan(14*n, 13*n);

	private static SensorMan[] sManHall = {sMHall1, sMHall2};					//группы сенсоров по комнатам
	private static SensorMan[] sManRoom = {sMRoom1, sMRoom2};
	private static SensorMan[] sManKitch = {sMKitch1, sMKitch2};
	private static SensorMan[] sManBath = {sMBath};

	private static LightCeil lC1Room = new LightCeil(4*n, 3*n, 3*n);
	private static LightCeil lC2Room = new LightCeil(4*n, 9*n, 3*n);
	private static LightCeil lC1Kitch = new LightCeil(12*n, 3*n, 3*n);
	private static LightCeil lC2Kitch = new LightCeil(12*n, 7*n, 3*n);
	private static LightCeil lC1Bath = new LightCeil(14*n, 12*n, 2*n);
	private static LightCeil lC2Bath = new LightCeil(14*n, 14*n, 2*n);
	private static LightCeil lC1Hall = new LightCeil(2*n, 14*n, 2*n);
	private static LightCeil lC2Hall = new LightCeil(6*n, 14*n, 2*n);
	private static LightCeil lC3Hall = new LightCeil(10*n, 14*n, 2*n);
	private static LightCeil lC4Hall = new LightCeil(10*n, 12*n, 2*n);

	private static LightCeil[] lCRoom = {lC1Room, lC2Room};					//группы потолочных светильников
	private static LightCeil[] lCKitch = {lC1Kitch, lC2Kitch};
	private static LightCeil[] lCBath = {lC1Bath, lC2Bath};
	private static LightCeil[] lCHall = {lC1Hall, lC2Hall, lC3Hall, lC4Hall};

	private static Controller contrRoom = new Controller(sManRoom, lCRoom);	
	private static Controller contrKitch = new Controller(sManKitch, lCKitch);
	private static Controller contrBath = new Controller(sManBath, lCBath);
	private static Controller contrHall = new Controller(sManHall, lCHall);

	private static boolean lightsFollowProfile;
	
	public static void LightsFollowProfileOn(){
		lightsFollowProfile=true;
	}
	public static void LightsFollowProfileOf(){
		lightsFollowProfile=false;
	}

	public static Room getRoom() {
		return bedroom;
	}
	public static Room getBath() {
		return bathroom;
	}
	public static Room getKitch() {
		return kitchen;
	}
	public static Hall getHall() {
		return hallway;
	}
	public static Point getClick() {
		return click;
	}

	public static void followLights(){
		if (lightsFollowProfile) {
			contrRoom.turnLineCeil(flat,man);
			contrKitch.turnLineCeil(flat,man);
			contrBath.turnLineCeil(flat,man);
			contrHall.turnLineCeil(flat,man);
		}
	}

	public static Room whereClick() {
		if (bedroom.isRoomClick(click))
			return  bedroom;
		else if (kitchen.isRoomClick(click))
			return kitchen;
		else if (bathroom.isRoomClick(click))
			return bathroom;
		else return hallway;
	}
	
	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SmartHouse().setVisible(true);
			}
		});
	}

}
