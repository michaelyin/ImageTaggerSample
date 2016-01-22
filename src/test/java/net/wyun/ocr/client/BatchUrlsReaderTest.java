/**
 * 
 */
package net.wyun.ocr.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.wyun.recognition.client.ImageRecognizer;
import net.wyun.recognition.client.domain.Image;
import net.wyun.recognition.client.domain.ImageType;
import net.wyun.recognition.client.domain.RequestType;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * @author michael
 *
 */
public class BatchUrlsReaderTest {
	
	private static final String host = "http://localhost:8080"; 
	private static final String user = "wyunClient";
	private static final String pass = "lank1ng$";
	private static final String ERROR = "error";

	@Test
	public void readTextFile() throws IOException{
		File txtFile = new File("src/test/resources/getPic.txt");
		List<String> list = FileUtils.readLines(txtFile);
		int cnt = 0;
		for(String s:list){
			if(s.startsWith("http")){
				System.out.println(s + " ===>");
				URL url = new URL(s);
				byte[] bytes = readUrlImage(url);
				Image image = new Image(ImageType.PNG, bytes);
				this.printOcr(image);
				cnt++;
				if(cnt > 10) break;
			}
		}
	}
	
	public byte[] readUrlImage(URL url) throws IOException{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		InputStream stream = null;
	    try {
	        byte[] chunk = new byte[4096];
	        int bytesRead;
	        stream = url.openStream();

	        while ((bytesRead = stream.read(chunk)) > 0) {
	        	//System.out.println("bytes read: " + bytesRead);
	            outputStream.write(chunk, 0, bytesRead);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    } finally{
	    	if(stream != null) {stream.close();}
	    }

	    return outputStream.toByteArray();

	}
	
		
	private void printOcr(Image image){
        ImageRecognizer reco = new ImageRecognizer(host, user, pass);
		
		Map<String, String> response = reco.readImage(image, RequestType.ADDRESS, "USA");
		
		
		for(String key : response.keySet()) {
			if(key.startsWith("entry")){
				System.out.println(response.get(key));
			}
			
			if ((ERROR.equalsIgnoreCase(key)) && (!response.get(key).isEmpty())) {
				System.out.println("got error on read");
			}
		}
		System.out.println("");
	}
	
	
}
