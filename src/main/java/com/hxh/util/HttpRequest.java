package com.hxh.util;

//import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url ;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
//			URLConnection connection = realUrl.openConnection();
//			// 设置通用的请求属性
//			connection.setRequestProperty("accept", "*/*");
//			connection.setRequestProperty("connection", "Keep-Alive");
//			connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.addRequestProperty("encoding", "UTF-8");//添加请求属性
//			// 建立实际的连接
//			conn.connect();
//			// 获取所有响应头字段
//			Map<String, List<String>> map = conn.getHeaderFields();
//			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
//			// 定义 BufferedReader输入流来读取URL的响应
//			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += line;
//			}
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	//public String sendPost(String url, JSONObject param) throws Exception {
	//	//StringBuilder builder = new StringBuilder();
	//	//int count = 0;
	//	//for(String key : param.keySet()){
	//	//	builder.append(key).append("=").append(param.get(key));
	//	//	count ++;
	//	//	if(count < param.size()){
	//	//		builder.append("&");
	//	//	}
	//	//}
	//	//String decodedString = URLDecoder.decode("{"+builder.toString() +"}", "UTF-8");
	//	//org.json.JSONObject jsonObject = new org.json.JSONObject(decodedString);
	//	//String jsonString = jsonObject.toString();
	//	return sendPost(url,param.toJSONString());
	//}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception 
	 */
	 public String sendPost(String url, String param) {
		 String result = null;
		 try {
			 // 请求URL
			 URL uri = new URL(url);

			 // 创建 HttpURLConnection 对象
			 HttpURLConnection connection = (HttpURLConnection) uri.openConnection();

			 // 设置请求方法为 POST
			 connection.setRequestMethod("POST");

			 // 设置请求头
			 connection.setRequestProperty("Content-Type", "application/json");
			 // 其他必要的请求头和参数

			 // 允许写入数据
			 connection.setDoOutput(true);

			 // 写入请求体数据
			 try (OutputStream os = connection.getOutputStream()) {
				 byte[] input = param.getBytes("utf-8");
				 os.write(input, 0, input.length);
			 }

			 // 获取响应
			 int responseCode = connection.getResponseCode();
			 BufferedReader reader;
			 if (responseCode == HttpURLConnection.HTTP_OK) {
				 reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			 } else {
				 reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			 }

			 // 读取响应
			 String line;
			 StringBuilder response = new StringBuilder();
			 while ((line = reader.readLine()) != null) {
				 response.append(line);
			 }
			 reader.close();

			 // 输出响应结果
			 System.out.println("Response: " + response.toString());
			 result = response.toString();

			 // 关闭连接
			 connection.disconnect();

		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 return result;
	 }
}