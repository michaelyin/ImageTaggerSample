/**
 * 
 */
package net.wyun.ocr.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.wyun.recognition.client.ImageTagger;
import net.wyun.recognition.client.domain.Image;
import net.wyun.recognition.client.domain.ImageType;

import org.junit.Before;
import org.junit.Test;

/**
 * @author michael
 *
 */
public class ImageTaggerTest {
	
	private static final String host = "http://localhost:8080";
	private static final String user = "wyunClient";
	private static final String pass = "lank1ng$";
	Image success = null;
	
	@Before
	public void startUp() throws IOException {
		
			File file = new File("src/test/resources/pngimages/7_5.png");
			InputStream input = null;
			byte[] data = new byte[(int) file.length()];

			try {
				try {
					FileInputStream fis = new FileInputStream(file);
					input = new BufferedInputStream(fis);
					input.read(data);
				} finally {
					input.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			success = new Image(ImageType.PNG, data);
	}
	
	
	@Test
	public void testRecognizeImage() throws Exception {
		Image image = success;
		ImageTagger reco = new ImageTagger(host, user, pass);
		
		String response = reco.readImage(image);
		System.out.println(response);
			

	}

}
