package tetris;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.Font;
public class MyTetris extends JFrame {

	private JPanel contentPane;
	private TetrisCanvas tetrisCanvas = null;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyTetris frame = new MyTetris();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyTetris() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 470, 590);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("∞‘¿”");
		mnNewMenu.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Ω√¿€");
		mntmNewMenuItem.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 12));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.start();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("¡æ∑·");
		mntmNewMenuItem_1.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 12));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tetrisCanvas = new TetrisCanvas();
		contentPane.add(tetrisCanvas, BorderLayout.NORTH);
		tetrisCanvas.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uB2E4\uC74C \uBE14\uB7ED");
		lblNewLabel.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(312, 131, 77, 29);
		tetrisCanvas.add(lblNewLabel);
	}
}