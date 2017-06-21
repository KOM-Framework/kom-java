package core.cmd;

import core.Log;

import java.io.IOException;

public class CMDReader {

	public static String executeCommand(String cmd, boolean waitForRespond) throws IOException, InterruptedException {
        Log.info(String.format("Execution '%s' command", cmd));
		String result = "";
		Process p = Runtime.getRuntime().exec(cmd);
		if (waitForRespond) {
			ProcessResultReader stderr = new ProcessResultReader(p.getErrorStream(), "STDERR");
			ProcessResultReader stdout = new ProcessResultReader(p.getInputStream(), "STDOUT");
			stderr.start();
			stdout.start();
			int exitValue = p.waitFor();;
			if (exitValue == 0) {
				result = stdout.toString();
			} else {
				result = stderr.toString();
			}
		}
		return result;
	}
}
