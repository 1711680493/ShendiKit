package shendi.kit.test;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import shendi.kit.util.FileUtil;
import shendi.kit.util.StreamUtils;

public class TestStreamUtils {
	
	public static void main(String[] args) throws Exception {
		
		HttpsURLConnection huc = (HttpsURLConnection) new URL("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF").openConnection();
		InputStream input = huc.getInputStream();
//		byte b = (byte) input.read();
//		b = (byte) input.read();
//		b = (byte) input.read();
//		System.out.println("字节：" + b);
		
//		byte[] data = new byte[input.available()];
//		byte[] temp = new byte[1024];
//		int len = -1, curLen = 0;
//		while ((len = input.read(temp)) != -1) {
//			System.out.println(len);
//			if (len + curLen > data.length) {
//				byte[] tmp = data;
//				data = new byte[data.length + 1048576];
//				
//				System.arraycopy(tmp, 0, data, 0, tmp.length);
//			}
//			
//			System.arraycopy(temp, 0, data, curLen, len);
//			curLen += len;
//		}
//		
//		byte[] tmp = data;
//		data = new byte[curLen];
//		System.arraycopy(tmp, 0, data, 0, curLen);
		
		// 102082
		long time = System.currentTimeMillis();
		byte[] data = StreamUtils.readAllByte(input);
		// 102436
//		byte[] data = input.readAllBytes();
		System.out.println(System.currentTimeMillis() - time);
		huc.disconnect();
		
		FileUtil.update("H:/1.jpg", data);
		System.out.println(data.length);
//		System.out.println(Arrays.toString(data));
//		System.out.println(new String(data));
	}
	
}
