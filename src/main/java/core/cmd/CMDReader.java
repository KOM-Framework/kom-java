package core.cmd;

import java.io.IOException;

public class CMDReader {

	public static String get_commandline_results(String cmd, boolean waitForRespond) {
		String result = "";
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage();
			waitForRespond = false;
		}
		if (waitForRespond) {
			ProcessResultReader stderr = new ProcessResultReader(p.getErrorStream(), "STDERR");
			ProcessResultReader stdout = new ProcessResultReader(p.getInputStream(), "STDOUT");
			stderr.start();
			stdout.start();
			int exitValue = 0;
			try {
				exitValue = p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (exitValue == 0) {
				result = stdout.toString();
			} else {
				result = stderr.toString();
			}
		}
		return result;
	}
}
