import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.*;
import java.math.BigDecimal;
import java.io.Reader;
import java.io.StringReader;

import com.ibatis.sqlmap.client.*;
import com.coupang.openapi.sdk.Hmac;
import com.google.gson.Gson;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.sun.jna.platform.win32.OaIdl.DECIMAL;

import dao.GoodsDAO;
import dao.OrderDAO;
import model.Goods;
import model.Order;
import mybatis.MyBatisConnectionFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
 


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Locale; 
import java.util.TimeZone; 

import java.util.Date;

public class LotteOn_API {
	public static void main(String[] args) {
		
		try 
		{
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			System.out.println("=====주문 start=====");
			   
		    try {
		    	 work wk = new work();
		    	 wk.Get_OrderInfo();
		    	
		    }catch (Exception e) {				
			}
		    System.out.println("=====주문 end=====");
		    
		}catch (Exception e) {
			
			// TODO: handle exception
		}
	}
}

class work {
	public void Get_OrderInfo() {
		//테스트
		//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
		//운영
		String Key="Bearer 5d5b2cb498f3d20001665f4e913ee45609ea4ee382e309d3a4f19efb";
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        
        JSONObject OrderInfo = new JSONObject();
        
        
        cal.add(Calendar.HOUR, -23);
        //OrderInfo.put("fromDate", df.format(cal.getTime()));
        OrderInfo.put("srchStrtDt", df.format(cal.getTime()));
        
        cal.add(Calendar.HOUR, 23);
        //OrderInfo.put("toDate", df.format(cal.getTime()));
        OrderInfo.put("srchEndDt", df.format(cal.getTime()));
        OrderInfo.put("odTypCd", "10");
        
        String sVal = getHttpHTML_POST_Order(OrderInfo.toJSONString(),Key,"https://openapi.lotteon.com/v1/openapi/delivery/v1/SellerDeliveryOrdersSearch");
          
	}
	 public String getHttpHTML_POST_Order(String sParam, String Key, String surl) {
	        //params
	        String method = "POST";
	       String sVal="";
	       
	        CloseableHttpClient client = null;
	        try {
	            //create client
	            client = HttpClients.createDefault();
	            //build uri
	            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
	           
	            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

	            StringEntity params =new StringEntity(sParam,"UTF-8");
	            // set header, demonstarte how to use hmac signature here
	            requestPost.addHeader("Authorization",Key);	          
	            requestPost.addHeader("content-type", "application/json");
	            requestPost.setEntity(params);
	            CloseableHttpResponse response = null;
	            try {
	                //execute post request
	                response = client.execute(requestPost);
	                //print result
	                System.out.println("status code:" + response.getStatusLine().getStatusCode());
	                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
	                HttpEntity entity = response.getEntity();
	                sVal = EntityUtils.toString(entity);
		               
	                System.out.println("result:" + sVal);
	                
	               
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                if (response != null) {
	                    try {
	                        response.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (client != null) {
	                try {
	                    client.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        return sVal;
	    }
	

}
