package core.videorecording;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class VideoRecorder {

	private ScreenRecorder screenRecorder;

	public void startRecording(String folder, String fileName){
		File file = new File(folder);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		Rectangle captureSize = new Rectangle(0, 0, width, height);

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		try {
			this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                            Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, fileName);
			this.screenRecorder.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void stopRecording(){
		try {
			this.screenRecorder.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
