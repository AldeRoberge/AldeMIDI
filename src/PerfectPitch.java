import java.awt.EventQueue;

import javax.swing.JFrame;

import midi.Device;

public class PerfectPitch {

	private JFrame frame;

	//
	
	Device device;

	public void setDevice(Device device) {
		this.device = device;
		this.device.open();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PerfectPitch window = new PerfectPitch();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PerfectPitch() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 801, 435);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
