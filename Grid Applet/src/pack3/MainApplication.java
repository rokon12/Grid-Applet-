package pack3;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class MainApplication implements Runnable {

	@Override
	public void run() {
		new ApplicationFrame();
	}

	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {
		Runnable run = new MainApplication();
		SwingUtilities.invokeAndWait(run);
	}
}
