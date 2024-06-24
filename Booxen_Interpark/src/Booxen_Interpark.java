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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.lang.*;
import java.math.BigDecimal;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

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
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.util.Date;


public class Booxen_Interpark {
	public static void main(String[] args) {
		
		
		
		// TODO Auto-generated method stub
		try {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			// 상품등록
			try {
				/*
				System.out.println("=====송장입력 start=====");
				//송장입력
		    	try {
		    		 work wk = new work();
		    	//	wk.GetTransNoList("22275"); //
		    		
		    	}catch (Exception e) {
					// TODO: handle exception
				}
		    	
		    	System.out.println("=====송장입력 end=====");
		    	  
		    	
			   */
				
				
				
			
				System.out.println("=====주문 start=====");
				   
			    try {
			    	 work wk = new work();
			    	 //wk.Get_OrderBaljuInfo();
			    	 //wk.GetOrderDbList();
			    	 wk.Get_OrderInfo();
			    	 Thread.sleep(5000);
			    	 wk.Get_OrderInfo2();
			    }catch (Exception e) {				
				}
			    System.out.println("=====주문 end=====");
			
	        		
				System.out.println("=====상품등록 start=====");

				List<Goods> bookList = goodsDAO.GetGoodsList();
				// wait
				for (Goods bookInfo : bookList) {
					try
					{
						int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());
						Pcnt =0;
						if (Pcnt < 1 && (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))) 
						{
							System.out.println("ISBN13: " + bookInfo.get_ISBN13());

							
							work wk = new work();
							wk.RegGoodsAPI(bookInfo);
						}
					}catch (Exception e) {
						continue;
						// TODO: handle exception
					}			 
				}
				
				System.out.println("=====상품등록  end=====");
				
				 
				System.out.println("=====상품수정  start=====");
				try
				{
					List<Goods> bookStatusList = goodsDAO.GetStatusGoodsList();
					
					for (Goods bookInfo : bookStatusList) {
						System.out.println("상품수정 ISBN13: " + bookInfo.get_ISBN13());					
						int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

						if (Pcnt > 0) {
							work wk = new work();
							System.out.println("=====상품상태수정  start=====" + bookInfo.get_ISBN13());
							wk.UptStatusAPI(bookInfo);
						}
					}

					
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				System.out.println("=====상품수정  end=====");
				
			
				
				//Thread.sleep(100000000);
				//System.exit(0);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		
			
		} catch (Exception e) {
		}

	}

}

class work {
	
	public void GetTransNoList( String sMALL_ID)
	{
		try
		{
			//테스트
			//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
			//운영
			String Key="Bearer 5d5b2cb498f3d20001665f4e30b4177b121a46df889ae695b790dc2a";
			
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			 java.util.HashMap<String, Object> Map_Mall = new HashMap<String, Object>(); 
			 Map_Mall.put("MALL_ID", sMALL_ID);
				 
			List list = orderDAO.GetTransNoList(Map_Mall);
			
			for(int i=0; i < list.size();i++)
			{
				HashMap map = (HashMap)list.get(i);
				
				String TRNS_NO = (String)map.get("TRNS_NO");
				String ORD_ID  = (String)map.get("ORD_ID");
				String MALL_ID = (String)map.get("MALL_ID");
				String SHOPLINKER_ORDER_ID = (String)map.get("SHOPLINKER_ORDER_ID");
				
				if (!SHOPLINKER_ORDER_ID.equals(""))
				{
					try
					{
						JSONObject ObjectBundle = new JSONObject();						        
						ObjectBundle.put("bundleNo", SHOPLINKER_ORDER_ID);               							       
						ObjectBundle.put("shipMethod", "PARCEL");
						ObjectBundle.put("parcelCompanyCode", "D002");
						ObjectBundle.put("invoiceNo", TRNS_NO);
						
						
						 String code = setOrderConfirm(ObjectBundle.toJSONString(),Key,"https://w-ap.wemakeprice.com/order/in/setOrderDelivery");
						
						 if (code.equals("200"))
						 {
							 String RtnMsg="";
		 	 				 java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
		 	 				 map1.put("ORD_ID", ORD_ID);
		 	 				 map1.put("MALL_ID", MALL_ID);
		 					
		 					
		 					orderDAO.UptTrans(map1);
						 }
						
	 					
					}catch (Exception e) {
						// TODO: handle exception
					}
					
					
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void GetOrderDbList()
	{
		try
		{
			//테스트
			//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
			//운영
			
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			 java.util.HashMap<String, Object> Map_Mall = new HashMap<String, Object>(); 
			 Map_Mall.put("MALL_ID", "54316");
				 
			 List listOrder = orderDAO.GetOrderDbList(Map_Mall);
			
			for(int iorder=0; iorder < listOrder.size();iorder++)
			{
				HashMap map = (HashMap)listOrder.get(iorder);
				
				String MSG = (String)map.get("MSG");
				
				if (!MSG.equals("")) {
		        	try
		        	{
		        		
		        		/*
		        		   InputSource file = new InputSource(new InputStreamReader(new FileInputStream("d:\\\\1.xml"), "UTF-8"));
		    
		                 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		                 DocumentBuilder db = dbf.newDocumentBuilder();
		                 
		                 Document doc = db.parse(file);
		                 doc.getDocumentElement().normalize();
		                 System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
		                 */
		        		
		       		     Document doc = toXmlDocument(MSG);
		                 NodeList list = doc.getElementsByTagName("ORDER");                
		                 Order Order = new Order();
		         		
		      			
		                 if (list != null && list.getLength() > 0) {
		      				for (int i = 0; i < list.getLength(); i++) {

		      					Node node = list.item(i);
		      					if (node.getNodeType() == Node.ELEMENT_NODE) {
		      						Element eElement = (Element) node;     						
		      						Order.Set_ORD_ID(getTagValue("ORD_NO", eElement));
		      						Order.Set_ORD_DATE(getTagValue("ORDER_DT", eElement).substring(0,8));
		      						
		      						Order.Set_DLV_MSG(getTagValue("DELI_COMMENT", eElement));
		      					    Order.Set_DLV_PRICE("0");
									Order.Set_DLV_POST(getTagValue("DEL_ZIP", eElement));
									Order.Set_DLV_ADDR1(getTagValue("DELI_ADDR1", eElement));
									Order.Set_DLV_ADDR2(getTagValue("DELI_ADDR2", eElement));																		
									Order.Set_DLV_NM(getTagValue("RCVR_NM", eElement));	
									Order.Set_DLV_TEL(getTagValue("DELI_TEL", eElement));
									Order.Set_DLV_HP(getTagValue("DELI_TEL", eElement));
		      						
		      						
		      						NodeList nlList_ProductData = eElement.getElementsByTagName("PRODUCT");
		 							if (nlList_ProductData != null && nlList_ProductData.getLength() > 0) {
		 								for (int ix = 0; ix < nlList_ProductData.getLength(); ix++) {
		 									
		 									
		 									Node OrderInfoValue = nlList_ProductData.item(ix);
		 									

		 		      						Element ProdElement = (Element) OrderInfoValue;     						
		 		      						
		 		      						NodeList PRD_NodeList = eElement.getElementsByTagName("PRD");
		 		      						
		 		      						for (int icnt = 0; icnt < PRD_NodeList.getLength(); icnt++) { 	
		 		      							Node PRD_Value = PRD_NodeList.item(icnt);

		 	 									if (PRD_Value.getNodeType() == Node.ELEMENT_NODE) {
		 	 										Element nElement = (Element) PRD_Value; 									 										
		 	 										Order.Set_ISBN13(getTagValue("ENTR_PRD_NO", nElement)); 									
		 	 										Order.Set_ORD_NO(String.valueOf(icnt+1));
		 	 										Order.Set_SELL_QTY(getTagValue("ORD_QTY", nElement).replace(".0", "")); 										
		 	 										Order.Set_ITEM_NM(getTagValue("PRD_NM", nElement));
		 	 										Order.Set_SELL_AMT(getTagValue("REAL_SALE_UNITCOST", nElement));
		 	 										
		 	 										
		 	 										if (Order.Get_ISBN13().substring(0, 1).equals("9") || Order.Get_ISBN13().substring(0, 1).equals("8"))
		 	 			 							{
		 	 			 								orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		 	 			 								map = new HashMap<String, Object>();
		 	 			 								map.put("ORD_ID", Order.Get_ORD_ID());
		 	 			 								map.put("ISBN13", Order.Get_ISBN13());
		 	 			 								int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
		 	 			 								if (API_MstCnt < 1) {
		 	 												
		 	 			 									
		 	 												double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
		 	 												double SELL_TOTAMT = SELL_AMT
		 	 														* Double.parseDouble(Order.Get_SELL_QTY());
		 	 												
		 	 												
		 	 												java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
		 	 												maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
		 	 												maping.put("SELLER_ID", "BOOXEN_INTER");
		 	 												maping.put("MALL_ID", "54316");
		 	 												maping.put("ORD_ID", Order.Get_ORD_ID());
		 	 												maping.put("ORD_NO", Order.Get_ORD_NO());
		 	 												maping.put("SELL_QTY", Order.Get_SELL_QTY());
		 	 												maping.put("SELL_AMT", String.valueOf((int)SELL_TOTAMT));
		 	 												maping.put("SELL_TOTAMT", String.valueOf((int)SELL_TOTAMT));
		 	 												maping.put("ISBN13", Order.Get_ISBN13());
		 	 												maping.put("ITEM_NM", Order.Get_ITEM_NM());
		 	 												maping.put("DLV_PRICE", Order.Get_DLV_PRICE());
		 	 												maping.put("DLV_POST", Order.Get_DLV_POST());
		 	 												maping.put("DLV_ADDR1", Order.Get_DLV_ADDR1());
		 	 												maping.put("DLV_ADDR2", Order.Get_DLV_ADDR2());
		 	 												maping.put("DLV_MSG", Order.Get_DLV_MSG());
		 	 												maping.put("DLV_NM", StringReplace(Order.Get_DLV_NM()));
		 	 												maping.put("DLV_TEL", Order.Get_DLV_TEL());
		 	 												maping.put("DLV_HP", Order.Get_DLV_HP());
		 	 												
		 	 												
		 	 												try {
		 	 													
		 	 													if (orderDAO.InsertBooxenOrdList(maping) > 0) {
		 	 														
		 	 														
		 	 													}
		 	 													
		 	 													
		 	 												}catch (Exception e) {
		 	 													// TODO: handle exception
		 	 												}
		 	 											
		 	 											}
		 	 			 							}
		 	 									}
		 		      							
		 		      						}
		 		      						
		 		      						
		 		      						
		 		      						

		 								}
		 							}
		 							
		 							
		      					}
		      					
		      					
		      					
		      				}
		      				
		      				
		      				
		      			}
		      			
		   		    
		   		     	
		        	}catch (Exception e) {
						// TODO: handle exception
					}
		        	 
		        }
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void Get_OrderInfo() throws ClientProtocolException, IOException {
		//운영
	
	Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                                          
    JSONObject OrderInfo = new JSONObject();
    String startDadte ="";
    String EndDadte ="";
    
    cal.add(Calendar.HOUR, -23);        
    startDadte = df.format(cal.getTime());
 
    cal.add(Calendar.HOUR, 23);
    EndDadte = df.format(cal.getTime());
    
    
    String sUrl = "https://joinapi.interpark.com/order/OrderClmAPI.do?_method=orderListForSingle&sc.entrId=WJBOOXEN&sc.supplyEntrNo=3002983773&sc.supplyCtrtSeq=1&sc.strDate=2023120700001&sc.endDate=2023120800001";        			   
    //String sUrl = "https://joinapi.interpark.com/order/OrderClmAPI.do?_method=orderListForSingle&sc.entrId=WJBOOXEN&sc.supplyEntrNo=3002983773&sc.supplyCtrtSeq=1&sc.strDate=2023011100001&sc.endDate=2023011200001";
         
    //운영
    String sVal = getHttpHTML_POST_Order(sUrl);
 
    if (!sVal.equals("")) {
    	try
    	{
    		InsertInterpark("RegOrder", EndDadte, EndDadte, sVal);
    		
    		/*
    		   InputSource file = new InputSource(new InputStreamReader(new FileInputStream("d:\\\\1.xml"), "UTF-8"));

             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
             DocumentBuilder db = dbf.newDocumentBuilder();
             
             Document doc = db.parse(file);
             doc.getDocumentElement().normalize();
             System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
             */
    		
   		     Document doc = toXmlDocument(sVal);
             NodeList list = doc.getElementsByTagName("ORDER");                
             Order Order = new Order();
     		
  			
             if (list != null && list.getLength() > 0) {
  				for (int i = 0; i < list.getLength(); i++) {

  					Node node = list.item(i);
  					if (node.getNodeType() == Node.ELEMENT_NODE) {
  						Element eElement = (Element) node;     						
  						Order.Set_ORD_ID(getTagValue("ORD_NO", eElement));
  						Order.Set_ORD_DATE(getTagValue("ORDER_DT", eElement).substring(0,8));
  						
  						Order.Set_DLV_MSG(getTagValue("DELI_COMMENT", eElement));
  					    Order.Set_DLV_PRICE("0");
						Order.Set_DLV_POST(getTagValue("DEL_ZIP", eElement));
						Order.Set_DLV_ADDR1(getTagValue("DELI_ADDR1", eElement));
						Order.Set_DLV_ADDR2(getTagValue("DELI_ADDR2", eElement));																		
						Order.Set_DLV_NM(getTagValue("RCVR_NM", eElement));	
						Order.Set_DLV_TEL(getTagValue("DELI_TEL", eElement));
						Order.Set_DLV_HP(getTagValue("DELI_TEL", eElement));
  						
  						
  						NodeList nlList_ProductData = eElement.getElementsByTagName("PRODUCT");
							if (nlList_ProductData != null && nlList_ProductData.getLength() > 0) {
								for (int ix = 0; ix < nlList_ProductData.getLength(); ix++) {
									
									
									Node OrderInfoValue = nlList_ProductData.item(ix);
									

		      						Element ProdElement = (Element) OrderInfoValue;     						
		      						
		      						NodeList PRD_NodeList = eElement.getElementsByTagName("PRD");
		      						
		      						for (int icnt = 0; icnt < PRD_NodeList.getLength(); icnt++) { 	
		      							Node PRD_Value = PRD_NodeList.item(icnt);

	 									if (PRD_Value.getNodeType() == Node.ELEMENT_NODE) {
	 										Element nElement = (Element) PRD_Value; 									 										
	 										Order.Set_ISBN13(getTagValue("ENTR_PRD_NO", nElement)); 									
	 										Order.Set_ORD_NO(String.valueOf(icnt+1));
	 										Order.Set_SELL_QTY(getTagValue("ORD_QTY", nElement).replace(".0", "")); 										
	 										Order.Set_ITEM_NM(getTagValue("PRD_NM", nElement));
	 										Order.Set_SELL_AMT(getTagValue("REAL_SALE_UNITCOST", nElement));
	 										
	 										
	 										if (Order.Get_ISBN13().substring(0, 1).equals("9") || Order.Get_ISBN13().substring(0, 1).equals("8"))
	 			 							{
	 			 								OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	 			 								java.util.HashMap<String, Object> map = new HashMap<String, Object>();
	 			 								map.put("ORD_ID", Order.Get_ORD_ID());
	 			 								map.put("ISBN13", Order.Get_ISBN13());
	 			 								int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
	 			 								if (API_MstCnt < 1) {
	 												
	 			 									
	 												double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
	 												double SELL_TOTAMT = SELL_AMT
	 														* Double.parseDouble(Order.Get_SELL_QTY());
	 												
	 												
	 												java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
	 												maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
	 												maping.put("SELLER_ID", "BOOXEN_INTER");
	 												maping.put("MALL_ID", "54316");
	 												maping.put("ORD_ID", Order.Get_ORD_ID());
	 												maping.put("ORD_NO", Order.Get_ORD_NO());
	 												maping.put("SELL_QTY", Order.Get_SELL_QTY());
	 												maping.put("SELL_AMT", String.valueOf((int)SELL_TOTAMT));
	 												maping.put("SELL_TOTAMT", String.valueOf((int)SELL_TOTAMT));
	 												maping.put("ISBN13", Order.Get_ISBN13());
	 												maping.put("ITEM_NM", Order.Get_ITEM_NM());
	 												maping.put("DLV_PRICE", Order.Get_DLV_PRICE());
	 												maping.put("DLV_POST", Order.Get_DLV_POST());
	 												maping.put("DLV_ADDR1", Order.Get_DLV_ADDR1());
	 												maping.put("DLV_ADDR2", Order.Get_DLV_ADDR2());
	 												maping.put("DLV_MSG", Order.Get_DLV_MSG());
	 												maping.put("DLV_NM", StringReplace(Order.Get_DLV_NM()));
	 												maping.put("DLV_TEL", Order.Get_DLV_TEL());
	 												maping.put("DLV_HP", Order.Get_DLV_HP());
	 												
	 												
	 												try {
	 													
	 													if (orderDAO.InsertBooxenOrdList(maping) > 0) {
	 														
	 														
	 													}
	 													
	 													
	 												}catch (Exception e) {
	 													// TODO: handle exception
	 												}
	 											
	 											}
	 			 							}
	 									}
		      							
		      						}
		      						
		      						
		      						
		      						

								}
							}
							
							
  					}
  					
  					
  					
  				}
  				
  				
  				
  			}
  			
		    
		     	
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	 
    }
   
    
}


	public void Get_OrderInfo2() throws ClientProtocolException, IOException {
		//운영
	
	Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                                          
    JSONObject OrderInfo = new JSONObject();
    String startDadte ="";
    String EndDadte ="";
    
    cal.add(Calendar.HOUR, -23);        
    startDadte = df.format(cal.getTime());
 
    cal.add(Calendar.HOUR, 23);
    EndDadte = df.format(cal.getTime());
    
    
    String sUrl = "https://joinapi.interpark.com/order/OrderClmAPI.do?_method=orderListDelvForSingle&sc.entrId=WJBOOXEN&sc.supplyEntrNo=3002983773&sc.supplyCtrtSeq=1&sc.strDate="+startDadte+"&sc.endDate=" + EndDadte;        			   
    //String sUrl = "https://joinapi.interpark.com/order/OrderClmAPI.do?_method=orderListDelvForSingle&sc.entrId=WJBOOXEN&sc.supplyEntrNo=3002983773&sc.supplyCtrtSeq=1&sc.strDate=2023011100001&sc.endDate=2023011200001";
         
    //운영
    String sVal = getHttpHTML_POST_Order(sUrl);
 
    if (!sVal.equals("")) {
    	try
    	{
    		InsertInterpark("RegOrder", EndDadte, EndDadte, sVal);
    		
    		/*
    		   InputSource file = new InputSource(new InputStreamReader(new FileInputStream("d:\\\\1.xml"), "UTF-8"));

             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
             DocumentBuilder db = dbf.newDocumentBuilder();
             
             Document doc = db.parse(file);
             doc.getDocumentElement().normalize();
             System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
             */
    		
   		     Document doc = toXmlDocument(sVal);
             NodeList list = doc.getElementsByTagName("ORDER");                
             Order Order = new Order();
     		
  			
             if (list != null && list.getLength() > 0) {
  				for (int i = 0; i < list.getLength(); i++) {

  					Node node = list.item(i);
  					if (node.getNodeType() == Node.ELEMENT_NODE) {
  						Element eElement = (Element) node;     						
  						Order.Set_ORD_ID(getTagValue("ORD_NO", eElement));
  						Order.Set_ORD_DATE(getTagValue("ORDER_DT", eElement).substring(0,8));
  						
  						Order.Set_DLV_MSG(getTagValue("DELI_COMMENT", eElement));
  					    Order.Set_DLV_PRICE("0");
						Order.Set_DLV_POST(getTagValue("DEL_ZIP", eElement));
						Order.Set_DLV_ADDR1(getTagValue("DELI_ADDR1", eElement));
						Order.Set_DLV_ADDR2(getTagValue("DELI_ADDR2", eElement));																		
						Order.Set_DLV_NM(getTagValue("RCVR_NM", eElement));	
						Order.Set_DLV_TEL(getTagValue("DELI_TEL", eElement));
						Order.Set_DLV_HP(getTagValue("DELI_TEL", eElement));
  						
						if( Order.Get_ORD_ID().equals("20230109144221649928"))
						{
							Order.Set_DLV_HP(getTagValue("DELI_TEL", eElement));
						}
						
						
  						NodeList nlList_ProductData = eElement.getElementsByTagName("PRODUCT");
							if (nlList_ProductData != null && nlList_ProductData.getLength() > 0) {
								for (int ix = 0; ix < nlList_ProductData.getLength(); ix++) {
									
									
									Node OrderInfoValue = nlList_ProductData.item(ix);
									

		      						Element ProdElement = (Element) OrderInfoValue;     						
		      						
		      						NodeList PRD_NodeList = eElement.getElementsByTagName("PRD");
		      						
		      						for (int icnt = 0; icnt < PRD_NodeList.getLength(); icnt++) { 	
		      							Node PRD_Value = PRD_NodeList.item(icnt);

	 									if (PRD_Value.getNodeType() == Node.ELEMENT_NODE) {
	 										Element nElement = (Element) PRD_Value; 									 										
	 										Order.Set_ISBN13(getTagValue("ENTR_PRD_NO", nElement)); 									
	 										Order.Set_ORD_NO(String.valueOf(icnt+1));
	 										Order.Set_SELL_QTY(getTagValue("ORD_QTY", nElement).replace(".0", "")); 										
	 										Order.Set_ITEM_NM(getTagValue("PRD_NM", nElement));
	 										Order.Set_SELL_AMT(getTagValue("REAL_SALE_UNITCOST", nElement));
	 										
	 										
	 										if (Order.Get_ISBN13().substring(0, 1).equals("9") || Order.Get_ISBN13().substring(0, 1).equals("8"))
	 			 							{
	 			 								OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	 			 								java.util.HashMap<String, Object> map = new HashMap<String, Object>();
	 			 								map.put("ORD_ID", Order.Get_ORD_ID());
	 			 								map.put("ISBN13", Order.Get_ISBN13());
	 			 								int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
	 			 								if (API_MstCnt < 1) {
	 												
	 			 									
	 												double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
	 												double SELL_TOTAMT = SELL_AMT
	 														* Double.parseDouble(Order.Get_SELL_QTY());
	 												
	 												
	 												java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
	 												maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
	 												maping.put("SELLER_ID", "BOOXEN_INTER");
	 												maping.put("MALL_ID", "54316");
	 												maping.put("ORD_ID", Order.Get_ORD_ID());
	 												maping.put("ORD_NO", Order.Get_ORD_NO());
	 												maping.put("SELL_QTY", Order.Get_SELL_QTY());
	 												maping.put("SELL_AMT", String.valueOf((int)SELL_TOTAMT));
	 												maping.put("SELL_TOTAMT", String.valueOf((int)SELL_TOTAMT));
	 												maping.put("ISBN13", Order.Get_ISBN13());
	 												maping.put("ITEM_NM", Order.Get_ITEM_NM());
	 												maping.put("DLV_PRICE", Order.Get_DLV_PRICE());
	 												maping.put("DLV_POST", Order.Get_DLV_POST());
	 												maping.put("DLV_ADDR1", Order.Get_DLV_ADDR1());
	 												maping.put("DLV_ADDR2", Order.Get_DLV_ADDR2());
	 												maping.put("DLV_MSG", Order.Get_DLV_MSG());
	 												maping.put("DLV_NM", StringReplace(Order.Get_DLV_NM()));
	 												maping.put("DLV_TEL", Order.Get_DLV_TEL());
	 												maping.put("DLV_HP", Order.Get_DLV_HP());
	 												
	 												
	 												try {
	 													
	 													if (orderDAO.InsertBooxenOrdList(maping) > 0) {
	 														
	 														
	 													}
	 													
	 													
	 												}catch (Exception e) {
	 													// TODO: handle exception
	 												}
	 											
	 											}
	 			 							}
	 									}
		      							
		      						}
		      						
		      						
		      						
		      						

								}
							}
							
							
  					}
  					
  					
  					
  				}
  				
  				
  				
  			}
  			
		    
		     	
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	 
    }
   
    
}

	public void Get_OrderBaljuInfo() throws ClientProtocolException, IOException {
			//운영
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                                              
        JSONObject OrderInfo = new JSONObject();
        String startDadte ="";
        String EndDadte ="";
        
        cal.add(Calendar.HOUR, -23);        
        startDadte = df.format(cal.getTime());
     
        cal.add(Calendar.HOUR, 23);
        EndDadte = df.format(cal.getTime());
        
        
        String sUrl = "https://joinapi.interpark.com/order/OrderClmAPI.do?_method=delvCompListForSingle&sc.entrId=WJBOOXEN&sc.supplyEntrNo=3002983773&sc.supplyCtrtSeq=1&sc.strDate="+startDadte+"&sc.endDate=" + EndDadte;        			   
        //String sUrl = "https://joinapi.interpark.com/order/OrderClmAPI.do?_method=orderListForSingle&sc.entrId=WJBOOXEN&sc.supplyEntrNo=3002983773&sc.supplyCtrtSeq=1&sc.strDate=20221220000001&sc.endDate=20221221000001";
             
        //운영
        String sVal = getHttpHTML_POST_Order(sUrl);
     
        if (!sVal.equals("")) {
        	try
        	{
        		
        		/*
        		   InputSource file = new InputSource(new InputStreamReader(new FileInputStream("d:\\\\1.xml"), "UTF-8"));
    
                 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                 DocumentBuilder db = dbf.newDocumentBuilder();
                 
                 Document doc = db.parse(file);
                 doc.getDocumentElement().normalize();
                 System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
                 */
        		
       		     Document doc = toXmlDocument(sVal);
                 NodeList list = doc.getElementsByTagName("ORDER");                
                 Order Order = new Order();
         		
      			
                 if (list != null && list.getLength() > 0) {
      				for (int i = 0; i < list.getLength(); i++) {

      					Node node = list.item(i);
      					if (node.getNodeType() == Node.ELEMENT_NODE) {
      						Element eElement = (Element) node;     						
      						Order.Set_ORD_ID(getTagValue("ORD_NO", eElement));
      						Order.Set_ORD_DATE(getTagValue("ORDER_DT", eElement).substring(0,8));
      						
      						Order.Set_DLV_MSG(getTagValue("DELI_COMMENT", eElement));
      					    Order.Set_DLV_PRICE("0");
							Order.Set_DLV_POST(getTagValue("DEL_ZIP", eElement));
							Order.Set_DLV_ADDR1(getTagValue("DELI_ADDR1", eElement));
							Order.Set_DLV_ADDR2(getTagValue("DELI_ADDR2", eElement));																		
							Order.Set_DLV_NM(getTagValue("RCVR_NM", eElement));	
							Order.Set_DLV_TEL(getTagValue("DELI_TEL", eElement));
							Order.Set_DLV_HP(getTagValue("DELI_TEL", eElement));
      						
      						
      						NodeList nlList_ProductData = eElement.getElementsByTagName("PRODUCT");
 							if (nlList_ProductData != null && nlList_ProductData.getLength() > 0) {
 								for (int ix = 0; ix < nlList_ProductData.getLength(); ix++) {
 									
 									
 									Node OrderInfoValue = nlList_ProductData.item(ix);
 									

 		      						Element ProdElement = (Element) OrderInfoValue;     						
 		      						
 		      						NodeList PRD_NodeList = eElement.getElementsByTagName("PRD");
 		      						
 		      						for (int icnt = 0; icnt < PRD_NodeList.getLength(); icnt++) { 	
 		      							Node PRD_Value = PRD_NodeList.item(icnt);

 	 									if (PRD_Value.getNodeType() == Node.ELEMENT_NODE) {
 	 										Element nElement = (Element) PRD_Value; 									 										
 	 										Order.Set_ISBN13(getTagValue("ENTR_PRD_NO", nElement)); 									
 	 										Order.Set_ORD_NO(String.valueOf(icnt+1));
 	 										Order.Set_SELL_QTY(getTagValue("ORD_QTY", nElement).replace(".0", "")); 										
 	 										Order.Set_ITEM_NM(getTagValue("PRD_NM", nElement));
 	 										Order.Set_SELL_AMT(getTagValue("SALE_UNITCOST", nElement));
 	 										/*
 	 										 java.util.HashMap<String, Object> Map_ISBN = new HashMap<String, Object>(); 
 	 										Map_ISBN.put("ISBN13", Order.Get_ISBN13());
 	 										 OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());	 
 	 										 List listOrder = orderDAO.GetGoodsPriceList(Map_ISBN);
 	 										
 	 										for(int iorder=0; iorder < listOrder.size();iorder++)
 	 										{
 	 											HashMap map = (HashMap)listOrder.get(iorder);
 	 											
 	 											Order.Set_SELL_AMT(String.valueOf((int)map.get("SALEPRICE")));
 	 	 	 									
 	 											
 	 										}
 	 										*/
 	 										
 	 										if (Order.Get_ISBN13().substring(0, 1).equals("9") || Order.Get_ISBN13().substring(0, 1).equals("8"))
 	 			 							{
 	 			 								
 	 			 								java.util.HashMap<String, Object> map = new HashMap<String, Object>();
 	 			 								map.put("ORD_ID", Order.Get_ORD_ID());
 	 			 								map.put("ISBN13", Order.Get_ISBN13());
 	 			 								OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());	 
 	 			 								int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
 	 			 								if (API_MstCnt < 1) {
 	 												
 	 			 									
 	 												double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
 	 												double SELL_TOTAMT = SELL_AMT
 	 														* Double.parseDouble(Order.Get_SELL_QTY());
 	 												
 	 												
 	 												java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
 	 												maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
 	 												maping.put("SELLER_ID", "BOOXEN_INTER");
 	 												maping.put("MALL_ID", "54316");
 	 												maping.put("ORD_ID", Order.Get_ORD_ID());
 	 												maping.put("ORD_NO", Order.Get_ORD_NO());
 	 												maping.put("SELL_QTY", Order.Get_SELL_QTY());
 	 												maping.put("SELL_AMT", String.valueOf((int)SELL_TOTAMT));
 	 												maping.put("SELL_TOTAMT", String.valueOf((int)SELL_TOTAMT));
 	 												maping.put("ISBN13", Order.Get_ISBN13());
 	 												maping.put("ITEM_NM", Order.Get_ITEM_NM());
 	 												maping.put("DLV_PRICE", Order.Get_DLV_PRICE());
 	 												maping.put("DLV_POST", Order.Get_DLV_POST());
 	 												maping.put("DLV_ADDR1", Order.Get_DLV_ADDR1());
 	 												maping.put("DLV_ADDR2", Order.Get_DLV_ADDR2());
 	 												maping.put("DLV_MSG", Order.Get_DLV_MSG());
 	 												maping.put("DLV_NM", StringReplace(Order.Get_DLV_NM()));
 	 												maping.put("DLV_TEL", Order.Get_DLV_TEL());
 	 												maping.put("DLV_HP", Order.Get_DLV_HP());
 	 												
 	 												
 	 												try {
 	 													
 	 													if (orderDAO.InsertBooxenOrdList(maping) > 0) {
 	 														
 	 														
 	 													}
 	 													
 	 													
 	 												}catch (Exception e) {
 	 													// TODO: handle exception
 	 												}
 	 											
 	 											}
 	 			 							}
 	 									}
 		      							
 		      						}
 		      						
 		      						
 		      						
 		      						

 								}
 							}
 							
 							
      					}
      					
      					
      					
      				}
      				
      				
      				
      			}
      			
   		    
   		     	
        	}catch (Exception e) {
				// TODO: handle exception
			}
        	 
        }
       
        
	}
	
	public String setOrderConfirm(String sParam,String Key,String surl)
	{
		//params
        String method = "POST";
        String code ="";
       
        CloseableHttpClient client = null;
        try {
        	 //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
           
            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

            StringEntity params =new StringEntity(sParam,"UTF-8");
            // set header, demonstarte how to use hmac signature here
            requestPost.addHeader("apiKey", Key);
          
            requestPost.addHeader("content-type", "application/json");
            requestPost.setEntity(params);
            CloseableHttpResponse response = null;
            try {
                //execute post request
                response = client.execute(requestPost);
                //print result
                System.out.println("OrderConfirm status code:" + response.getStatusLine().getStatusCode());
                System.out.println("OrderConfirm status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);
	               
                System.out.println("OrderConfirm result:" + strResult);
                
                
                JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject)jsonParser.parse( strResult );							
				 
				
				code = jsonObj.get("resultCode").toString();
				String data ="";
				if (code.equals("200"))
				{
					//data = jsonObj.get("data").toString();
					//JSONObject jsonObj_data = (JSONObject)jsonParser.parse( data );						
					
					
				}else {
					data = jsonObj.get("error").toString();
				}
				
               
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
        
        return code;
	}
	public static String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, " ");
		return str;
	}
	
		
	public void UptStatusAPI(Goods bookInfo) {
		
		double SALEPRICE = 0;

		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 80)
				return;

			if (Double.parseDouble(bookInfo.get_INRT()) <= 70) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();

			} else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			}

			
			bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
			String sRtn = GetXml_Status(bookInfo,SALEPRICE);
			
			if (!"".equals(sRtn)) {
				String sVal = "";							
				sVal = getHttpHTML_POST_Upt(sRtn,bookInfo);				

			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	
	}
	
	

public  String substringByBytes(String str, int beginBytes, int endBytes) {
    if (str == null || str.length() == 0) {
        return "";
    }
 

     if (beginBytes < 0) {
        beginBytes = 0;
    }

    if (endBytes < 1) {
        return "";
    }

    int len = str.length();

    int beginIndex = -1;
    int endIndex = 0;

    int curBytes = 0;
    String ch = null;
    for (int i = 0; i < len; i++) {
        ch = str.substring(i, i + 1);
        curBytes += ch.getBytes().length;
 

        if (beginIndex == -1 && curBytes >= beginBytes) {
            beginIndex = i;
        }

        if (curBytes > endBytes) {
            break;
        } else {
            endIndex = i + 1;
        }
    }
 

    return str.substring(beginIndex, endIndex);
}

	
	public void RegGoodsAPI(Goods bookInfo) {
		double SALEPRICE = 0;

		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 80)
				return;

			if (Double.parseDouble(bookInfo.get_INRT()) <= 70) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();

			} else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			}

			String sRtn = GetXml(bookInfo, SALEPRICE);
			
			System.out.println(sRtn);
			
			if (!"".equals(sRtn)) {
				String sVal = "";	
				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Reg(sRtn , bookInfo);				

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	
	

	public String GetXml(Goods bookInfo, double SALEPRICE) throws SQLException, ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// 루트 엘리먼트
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("result");
		doc.appendChild(rootElement);
		
		Element title = doc.createElement("title");
		rootElement.appendChild(title);
		
		Element description = doc.createElement("description");
		rootElement.appendChild(description);
		
		Element item = doc.createElement("item");
		rootElement.appendChild(item);
		
		Element prdStat = doc.createElement("prdStat");
		prdStat.appendChild(doc.createTextNode("01"));
		item.appendChild(prdStat);
		
		Element shopNo = doc.createElement("shopNo");
		shopNo.appendChild(doc.createTextNode("0000100000"));
		item.appendChild(shopNo);
		
		String sCate = GetCategory(bookInfo);
		
		Element omDispNo = doc.createElement("omDispNo");
		omDispNo.appendChild(doc.createTextNode(sCate));
		item.appendChild(omDispNo);
		
		Element prdNm = doc.createElement("prdNm");
		prdNm.appendChild(doc.createCDATASection(bookInfo.get_BOOK_NM()));
		item.appendChild(prdNm);
		
		Element hdelvMafcEntrNm = doc.createElement("hdelvMafcEntrNm");
		hdelvMafcEntrNm.appendChild(doc.createCDATASection(bookInfo.get_PUBLISHER()));
		item.appendChild(hdelvMafcEntrNm);
		
		Element prdOriginTp = doc.createElement("prdOriginTp");
		prdOriginTp.appendChild(doc.createCDATASection("상세설명참고"));
		item.appendChild(prdOriginTp);
		
		
		Element taxTp = doc.createElement("taxTp");
		if (bookInfo.get_ISBN13().substring(0,1).equals("8"))
		{
			taxTp.appendChild(doc.createTextNode("01"));
		}else {
			taxTp.appendChild(doc.createTextNode("02"));
		}
		item.appendChild(taxTp);
         
		Element ordAgeRstrYn = doc.createElement("ordAgeRstrYn");
		ordAgeRstrYn.appendChild(doc.createTextNode("N"));
		item.appendChild(ordAgeRstrYn);
		
		Element saleStatTp = doc.createElement("saleStatTp");
		saleStatTp.appendChild(doc.createTextNode("01"));
		item.appendChild(saleStatTp);
		
		Element saleUnitcost = doc.createElement("saleUnitcost");
		saleUnitcost.appendChild(doc.createTextNode(String.valueOf((int)SALEPRICE)));
		item.appendChild(saleUnitcost);
		
		Element saleLmtQty = doc.createElement("saleLmtQty");
		saleLmtQty.appendChild(doc.createTextNode("99999"));
		item.appendChild(saleLmtQty);
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        
        
		Element saleStrDts = doc.createElement("saleStrDts");
		saleStrDts.appendChild(doc.createTextNode(df.format(cal.getTime())));
		item.appendChild(saleStrDts);
		
		Element saleEndDts = doc.createElement("saleEndDts");
		saleEndDts.appendChild(doc.createTextNode("99991231"));
		item.appendChild(saleEndDts);
		
		Element proddelvCostUseYn = doc.createElement("proddelvCostUseYn");
		proddelvCostUseYn.appendChild(doc.createTextNode("N"));   //업체배송비정책사용:N
		item.appendChild(proddelvCostUseYn);
		
		Element delvPlcNo = doc.createElement("delvPlcNo");
		delvPlcNo.appendChild(doc.createTextNode("828851"));   //묶음배송비번호 
		item.appendChild(delvPlcNo);
		
		Element prdrtnCostUseYn = doc.createElement("prdrtnCostUseYn");
		prdrtnCostUseYn.appendChild(doc.createTextNode("Y"));   //상품반품택배비사용 
		item.appendChild(prdrtnCostUseYn);
		
		Element rtndelvCost = doc.createElement("rtndelvCost");
		rtndelvCost.appendChild(doc.createTextNode("2500"));   //	상품 반품택배비. 
		item.appendChild(rtndelvCost);
		
		Element rtndelvNo = doc.createElement("rtndelvNo");
		rtndelvNo.appendChild(doc.createTextNode("0"));   //	상품 반품배송지번호.. 
		item.appendChild(rtndelvNo);
		
		
		
		Element mktPr = doc.createElement("mktPr");
		mktPr.appendChild(doc.createTextNode(String.valueOf((int)SALEPRICE)));   //	
		item.appendChild(mktPr);
		
		String Desc = GetGoodsBody_New(bookInfo);
		
		Element prdBasisExplanEd = doc.createElement("prdBasisExplanEd");
		prdBasisExplanEd.appendChild(doc.createCDATASection(Desc));   //	
		item.appendChild(prdBasisExplanEd);
		
		Element zoomImg = doc.createElement("zoomImg");
		zoomImg.appendChild(doc.createTextNode(bookInfo.get_IMG_PATH2()));   //	
		item.appendChild(zoomImg);
		
		Element delvAmtPayTpCom = doc.createElement("delvAmtPayTpCom");
		delvAmtPayTpCom.appendChild(doc.createTextNode("02"));   //	
		item.appendChild(delvAmtPayTpCom);
		
		Element jejuetcDelvCostUseYn = doc.createElement("jejuetcDelvCostUseYn");
		jejuetcDelvCostUseYn.appendChild(doc.createTextNode("Y"));   //	
		item.appendChild(jejuetcDelvCostUseYn);
		
		Element jejuDelvCost = doc.createElement("jejuDelvCost");
		jejuDelvCost.appendChild(doc.createTextNode("3000"));   //	
		item.appendChild(jejuDelvCost);
		
		Element etcDelvCost = doc.createElement("etcDelvCost");
		etcDelvCost.appendChild(doc.createTextNode("3000"));   //	
		item.appendChild(etcDelvCost);
		
		Element delvMthd = doc.createElement("delvMthd");
		delvMthd.appendChild(doc.createTextNode("01"));   //	
		item.appendChild(delvMthd);
		
		Element ippSubmitYn = doc.createElement("ippSubmitYn");
		ippSubmitYn.appendChild(doc.createTextNode("Y"));   //	
		item.appendChild(ippSubmitYn);
		
		Element originPrdNo = doc.createElement("originPrdNo");
		originPrdNo.appendChild(doc.createTextNode(bookInfo.get_ISBN13()));   //	
		item.appendChild(originPrdNo);
		
		Element asInfo = doc.createElement("asInfo");
		asInfo.appendChild(doc.createTextNode("Y"));   //	
		item.appendChild(asInfo);
		
	
		Element isbn = doc.createElement("isbn");
		isbn.appendChild(doc.createTextNode(bookInfo.get_ISBN13()));   //	
		item.appendChild(isbn);
		
		Element cultureDeductionYn = doc.createElement("cultureDeductionYn");
		cultureDeductionYn.appendChild(doc.createTextNode("Y"));   //	
		item.appendChild(cultureDeductionYn);
		
		if (Double.parseDouble(bookInfo.get_INRT()) <61 && Double.parseDouble(bookInfo.get_PRICE()) >= 17000 )
		{
			double ipoint = Double.parseDouble(bookInfo.get_PRICE()) * 0.05;
			
			Element entrPoint = doc.createElement("entrPoint");
			entrPoint.appendChild(doc.createTextNode( String.valueOf((int)ipoint)));   //	
			item.appendChild(entrPoint);	
		}
		if (Double.parseDouble(bookInfo.get_INRT()) <65 && Double.parseDouble(bookInfo.get_PRICE()) >= 22000 )
		{
			double ipoint = Double.parseDouble(bookInfo.get_PRICE()) * 0.03;
			Element entrPoint = doc.createElement("entrPoint");
			entrPoint.appendChild(doc.createTextNode( String.valueOf((int)ipoint)));   //	
			item.appendChild(entrPoint);	
		}
		
		Element publicationDate = doc.createElement("publicationDate");
		publicationDate.appendChild(doc.createTextNode(bookInfo.get_OPENDATE().replace("-", "")));	
		item.appendChild(publicationDate);
		
		
		Element authors = doc.createElement("authors");
		authors.appendChild(doc.createTextNode(bookInfo.get_AUTHR()));   //	
		item.appendChild(authors);
		
		
		Element prdinfoNoti = doc.createElement("prdinfoNoti");
		Element info1 = doc.createElement("info");
		Element infoSubNo1 = doc.createElement("infoSubNo");
		Element infoCd1 = doc.createElement("infoCd");
		Element infoTx1 = doc.createElement("infoTx");
		
		prdinfoNoti.appendChild(info1);
		info1.appendChild(infoSubNo1);
		info1.appendChild(infoCd1);
		info1.appendChild(infoTx1);
		
		infoSubNo1.appendChild(doc.createCDATASection("2601"));
		infoCd1.appendChild(doc.createTextNode("I"));
		infoTx1.appendChild(doc.createCDATASection(bookInfo.get_BOOK_NM()));
		
		
	
		Element info2 = doc.createElement("info");
		Element infoSubNo2 = doc.createElement("infoSubNo");
		Element infoCd2 = doc.createElement("infoCd");
		Element infoTx2 = doc.createElement("infoTx");
		
		prdinfoNoti.appendChild(info2);
		info2.appendChild(infoSubNo2);
		info2.appendChild(infoCd2);
		info2.appendChild(infoTx2);
		
		infoSubNo2.appendChild(doc.createCDATASection("2602"));
		infoCd2.appendChild(doc.createTextNode("I"));
		infoTx2.appendChild(doc.createCDATASection(bookInfo.get_AUTHR()+ "/" + bookInfo.get_PUBLISHER()));
		
		Element info3 = doc.createElement("info");
		Element infoSubNo3 = doc.createElement("infoSubNo");
		Element infoCd3 = doc.createElement("infoCd");
		Element infoTx3 = doc.createElement("infoTx");
		
		prdinfoNoti.appendChild(info3);
		info3.appendChild(infoSubNo3);
		info3.appendChild(infoCd3);
		info3.appendChild(infoTx3);
		
		infoSubNo3.appendChild(doc.createCDATASection("2603"));
		infoCd3.appendChild(doc.createTextNode("I"));
		infoTx3.appendChild(doc.createCDATASection( bookInfo.get_BOOK_MG_VAL()));
		
		Element info4 = doc.createElement("info");
		Element infoSubNo4 = doc.createElement("infoSubNo");
		Element infoCd4 = doc.createElement("infoCd");
		Element infoTx4 = doc.createElement("infoTx");
		
		prdinfoNoti.appendChild(info4);
		info4.appendChild(infoSubNo4);
		info4.appendChild(infoCd4);
		info4.appendChild(infoTx4);
		
		infoSubNo4.appendChild(doc.createCDATASection("2604"));
		infoCd4.appendChild(doc.createTextNode("I"));
		infoTx4.appendChild(doc.createCDATASection(bookInfo.get_BOOK_PAGE_VAL()));
		
		
		Element info5 = doc.createElement("info");
		Element infoSubNo5 = doc.createElement("infoSubNo");
		Element infoCd5 = doc.createElement("infoCd");
		Element infoTx5 = doc.createElement("infoTx");
		
		prdinfoNoti.appendChild(info5);
		info5.appendChild(infoSubNo5);
		info5.appendChild(infoCd5);
		info5.appendChild(infoTx5);
		
		infoSubNo5.appendChild(doc.createCDATASection("2605"));
		infoCd5.appendChild(doc.createTextNode("I"));
		infoTx5.appendChild(doc.createCDATASection("상품상세설명 참조"));
		
		Element info6 = doc.createElement("info");
		Element infoSubNo6 = doc.createElement("infoSubNo");
		Element infoCd6 = doc.createElement("infoCd");
		Element infoTx6 = doc.createElement("infoTx");
		
		prdinfoNoti.appendChild(info6);
		info6.appendChild(infoSubNo6);
		info6.appendChild(infoCd6);
		info6.appendChild(infoTx6);
		
		infoSubNo6.appendChild(doc.createCDATASection("2606"));
		infoCd6.appendChild(doc.createTextNode("I"));
		infoTx6.appendChild(doc.createCDATASection(bookInfo.get_OPENDATE().replace("-", "")));
		
		
		Element info7 = doc.createElement("info");
		Element infoSubNo7 = doc.createElement("infoSubNo");
		Element infoCd7 = doc.createElement("infoCd");
		Element infoTx7 = doc.createElement("infoTx");

		prdinfoNoti.appendChild(info7);
		info7.appendChild(infoSubNo7);
		info7.appendChild(infoCd7);
		info7.appendChild(infoTx7);

		infoSubNo7.appendChild(doc.createCDATASection("2607"));
		infoCd7.appendChild(doc.createTextNode("I"));
		infoTx7.appendChild(doc.createCDATASection("상품상세설명 참조"));
		
		item.appendChild(prdinfoNoti);
		
		//String sPath= "d:\\ShapLinker\\XML\\Interpark\\" + bookInfo.get_ISBN13() + ".xml";
		String sPath= "d:\\ShapLinker\\XML\\InterPark\\" + bookInfo.get_ISBN13() + ".xml";
		 try (FileOutputStream output = new FileOutputStream(sPath))
		 {
			 writeXml(doc, output);
		 }catch (IOException e) {
		        e.printStackTrace();
		    }
		 
		 //return  "http://211.115.107.33/XML/InterPark/" +bookInfo.get_ISBN13() + ".xml";
		 return "http://shoplinker.booxen.com/XML/Interpark/" + bookInfo.get_ISBN13() + ".xml";
	}
	
	 public  void writeXml(Document doc,
             OutputStream output)
		throws TransformerException {
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);
		
		transformer.transform(source, result);
		
		}
			 
	public static String DocumentToString( Document doc )
	  {
	   try
	   {
	    StringWriter clsOutput = new StringWriter( );
	    Transformer clsTrans = TransformerFactory.newInstance( ).newTransformer( );
	 
	    clsTrans.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );
	    clsTrans.setOutputProperty( OutputKeys.METHOD, "xml" );
	    clsTrans.setOutputProperty( OutputKeys.INDENT, "yes" );
	    clsTrans.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
	    clsTrans.setOutputProperty( OutputKeys.STANDALONE, "yes" );
	 
	    clsTrans.transform( new DOMSource( doc ), new StreamResult( clsOutput ) );

	    return clsOutput.toString( );
	   }
	   catch( Exception ex )
	   {
	    return "";
	   }
	 }
	

	public String GetXml_Status(Goods bookInfo,double SALEPRICE) throws SQLException, ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// 루트 엘리먼트
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("result");
		doc.appendChild(rootElement);
		
		Element title = doc.createElement("title");
		rootElement.appendChild(title);
		
		Element description = doc.createElement("description");
		rootElement.appendChild(description);
		
		Element item = doc.createElement("item");
		rootElement.appendChild(item);
		
		Element prdNo = doc.createElement("prdNo");
		prdNo.appendChild(doc.createTextNode(bookInfo.get_PRODUCTNO()));
		item.appendChild(prdNo);
		
		Element prdNm = doc.createElement("prdNm");
		prdNm.appendChild(doc.createCDATASection(bookInfo.get_BOOK_NM()));
		item.appendChild(prdNm);
		
		
		Element saleStatTp = doc.createElement("saleStatTp");
		 if (bookInfo.get_BOOKSTS().equals("현매") || bookInfo.get_BOOKSTS().equals("정상"))
		 {
			 saleStatTp.appendChild(doc.createTextNode("01"));
		 }else {
			 saleStatTp.appendChild(doc.createTextNode("02"));	 
		 }
		
		item.appendChild(saleStatTp);
		
		Element saleUnitcost = doc.createElement("saleUnitcost");
		saleUnitcost.appendChild(doc.createTextNode(String.valueOf((int)SALEPRICE)));
		item.appendChild(saleUnitcost);
		
		Element cultureDeductionYn = doc.createElement("cultureDeductionYn");
		cultureDeductionYn.appendChild(doc.createTextNode("Y"));   //	
		item.appendChild(cultureDeductionYn);
	
		Element mktPr = doc.createElement("mktPr");
		mktPr.appendChild(doc.createTextNode(String.valueOf((int)SALEPRICE)));   //	
		item.appendChild(mktPr);

		
		if (Double.parseDouble(bookInfo.get_INRT()) <61 && Double.parseDouble(bookInfo.get_PRICE()) >= 17000 )
		{
			double ipoint = Double.parseDouble(bookInfo.get_PRICE()) * 0.05;
			
			Element entrPoint = doc.createElement("entrPoint");
			entrPoint.appendChild(doc.createTextNode( String.valueOf((int)ipoint)));   //	
			item.appendChild(entrPoint);	
		}
		if (Double.parseDouble(bookInfo.get_INRT()) <65 && Double.parseDouble(bookInfo.get_PRICE()) >= 22000 )
		{
			double ipoint = Double.parseDouble(bookInfo.get_PRICE()) * 0.03;
			Element entrPoint = doc.createElement("entrPoint");
			entrPoint.appendChild(doc.createTextNode( String.valueOf((int)ipoint)));   //	
			item.appendChild(entrPoint);	
		}
		
		
		Element publicationDate = doc.createElement("publicationDate");
		publicationDate.appendChild(doc.createTextNode(bookInfo.get_OPENDATE().replace("-", "")));   //	
		item.appendChild(publicationDate);
		
		
		Element authors = doc.createElement("authors");
		authors.appendChild(doc.createTextNode(bookInfo.get_AUTHR()));   //	
		item.appendChild(authors);
		
		Element isbn = doc.createElement("isbn");
		isbn.appendChild(doc.createTextNode(bookInfo.get_ISBN13()));   //	
		item.appendChild(isbn);
		
		
		
		//String sPath= "d:\\ShapLinker\\XML\\Interpark\\" + bookInfo.get_ISBN13() + ".xml";
		String sPath= "d:\\ShapLinker\\XML\\InterPark\\" + bookInfo.get_ISBN13() + ".xml";
		try (FileOutputStream output = new FileOutputStream(sPath))
		{
			 writeXml(doc, output);
		}catch (IOException e) {
		      e.printStackTrace();
		}
		//return  "http://211.115.107.33/XML/InterPark/" +bookInfo.get_ISBN13() + ".xml";
		return "http://shoplinker.booxen.com/XML/Interpark/" + bookInfo.get_ISBN13() + ".xml";
	}
	
	
	
	
	public String GetGoodsBody_New(Goods bookInfo) {
		String Str_Contents = "";

		Str_Contents = Str_Contents
				+ "<div class=\"booxen_detail_left booxen_tl\" style=\"width: 900px; margin: 0 auto; float:none; text-align:left; \">";

		if (!"".equals(bookInfo.get_BOOK_INTRCN_CN()) && bookInfo.get_BOOK_INTRCN_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;line-height:180%;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 책소개</div>");
			sb.append(
					"<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
			sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
			sb.append("<span style = \"display:block;text-align:left;\" >     ");
			sb.append(bookInfo.get_BOOK_INTRCN_CN().replace("\r\n", "<br>") + "</div>");

			Str_Contents = Str_Contents + sb.toString();
		}
		
		if (!"".equals(bookInfo.get_IMG_DETAIL_PATH()) && bookInfo.get_IMG_DETAIL_PATH() != null) {
			
			if (bookInfo.get_IMG_DETAIL_PATH().indexOf("IMG544") > -1)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(
						" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;line-height:180%;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 상세이미지</div>");
				sb.append(
						"<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
				sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
				sb.append("<span style = \"display:block;text-align:left;\" >     ");
				sb.append("<img src='" + bookInfo.get_IMG_DETAIL_PATH() + "'></img></div>");

				Str_Contents = Str_Contents + sb.toString();
			}
			
		}
		
		if (!"".equals(bookInfo.get_CNTNT_CN()) && bookInfo.get_CNTNT_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;line-height:180%;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 목차</div>");
			sb.append(
					"<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
			sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
			sb.append("<span style = \"display:block;text-align:left;\" >     ");
			sb.append(bookInfo.get_CNTNT_CN().replace("\r\n", "<br>") + "</div>");

			Str_Contents = Str_Contents + sb.toString();
		}
		if (!"".equals(bookInfo.get_PLSCMPN_BKRVW_CN()) && bookInfo.get_PLSCMPN_BKRVW_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;line-height:180%;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 출판사서평</div>");
			sb.append(
					"<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
			sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
			sb.append("<span style = \"display:block;text-align:left;\" >     ");
			sb.append(bookInfo.get_PLSCMPN_BKRVW_CN().replace("\r\n", "<br>") + "</div>");

			Str_Contents = Str_Contents + sb.toString();
		}
		if (!"".equals(bookInfo.get_AUTHR_INTRCN_CN()) && bookInfo.get_AUTHR_INTRCN_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;line-height:180%;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 저자소개</div>");
			sb.append(
					"<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
			sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
			sb.append("<span style = \"display:block;text-align:left;\" >     ");
			sb.append(bookInfo.get_AUTHR_INTRCN_CN().replace("\r\n", "<br>") + "</div>");

			Str_Contents = Str_Contents + sb.toString();
		}

		 	GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	        int Pcnt = goodsDAO.GetExisKidsList(bookInfo.get_BX_CATECD());
	        
	        if (Pcnt > 0)
	        {
	        	 StringBuilder sb = new StringBuilder();
	        	 sb.append("<br>");
	        	 sb.append("<div style=\"text-align:left;\">");
	        	 sb.append("<img src=\"http://shoplinker.booxen.com/Image/kcImage.jpg\"></img>");
	        	 sb.append("</div>");
	        	 Str_Contents = Str_Contents + sb.toString();
	        }


		
		return Str_Contents + " </div>";
	}
	public String GetCategory(Goods bookInfo) {
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>();

		if (bookInfo.get_BX_CATECD() == null) {
			bookInfo.set_BX_CATECD("178452");
		}

		Result_map = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());
		String StrCateGory = "";
		
		if (Result_map !=null)
		{
			Iterator iter = Result_map.entrySet().iterator();
			
			while (iter.hasNext()) {

				Entry entry = (Entry) iter.next();

				StrCateGory = entry.getValue().toString();

			}

		}
		
		if (StrCateGory.equals("")) {

			StrCateGory = "001930001001001";
		}

		return StrCateGory;
	}
	
	 public String ex1(Date date, String pattern) {
			String result = null;
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
			result = format.format(date);
			return result;
		}
	 
	 
	 public String getHttpHTML_POST_Reg(String dataUrl, Goods bookInfo) {
	     String resultMessage = "";
	     String sReturn = "";
	     try {

				URL url = new URL("http://ipss1.interpark.com/openapi/product/ProductAPIService.do"); // 호출할 url
				Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
				params.put("_method", "InsertProductAPIData");
				params.put("citeKey", "Qk7wrgQSrXi8Ba4Qp3N05Tn4BT/1MLkV");
				params.put("secretKey", "9v57^MExnS8pI2cVY4czlK^/01vJR7xl");
				params.put("dataUrl", dataUrl);

				StringBuilder postData = new StringBuilder();
				for (Map.Entry<String, Object> param : params.entrySet()) {
					if (postData.length() != 0)
						postData.append('&');
					postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				}
				byte[] postDataBytes = postData.toString().getBytes("UTF-8");

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
				conn.setDoOutput(true);
				conn.getOutputStream().write(postDataBytes); // POST 호출

				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) { // response 출력
					response.append(inputLine);
				}
				System.out.println(response.toString());
				in.close();
				sReturn = response.toString();

				 Document doc = toXmlDocument(sReturn);
				 NodeList list = doc.getElementsByTagName("result");
				 
				 String title ="";
				 String message ="";
				 String productNo = "";
				 String originPrdNo = "";
				 
				 if (list != null && list.getLength() > 0) {
						for (int i = 0; i < list.getLength(); i++) {

							Node node = list.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								title = getTagValue("title", eElement);
								
								NodeList nlList_sucess = eElement.getElementsByTagName("success");
								if (nlList_sucess != null && nlList_sucess.getLength() > 0) {
									Node sucessNode = nlList_sucess.item(0);
									Element nElement = (Element) sucessNode;
									productNo = getTagValue("prdNo", nElement);
								}
								
								NodeList nlList_error = eElement.getElementsByTagName("error");
								if (nlList_error != null && nlList_error.getLength() > 0) {
									Node errorNode = nlList_error.item(0);
									Element nElement = (Element) errorNode;
									message = getTagValue("explanation", nElement);
								}
								NodeList nlList_originPrdNo = eElement.getElementsByTagName("originPrdNo");
								if (nlList_originPrdNo != null && nlList_originPrdNo.getLength() > 0) {
									Node originPrdNoNode = nlList_originPrdNo.item(0);
									Element nElement = (Element) originPrdNoNode;
									originPrdNo = getTagValue("originPrdNo", nElement);
								}
								
								System.out.println(title);

							}
						}
				 }
					
				if (!productNo.equals(""))
				{
					
					GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					
					java.util.HashMap<String, Object> map = new HashMap<String, Object>();						
					map.put("ITEMCD", bookInfo.get_ITEMCD());
					map.put("PRODUCTNO", productNo);
					map.put("ISBN13", bookInfo.get_ISBN13());
					map.put("BOOK_NM", bookInfo.get_BOOK_NM());
					map.put("BOOKSTS", bookInfo.get_BOOKSTS());
					map.put("SALEPRICE", bookInfo.get_SALEPRICE());
					map.put("PRICE", bookInfo.get_PRICE());

					goodsDAO.InsertRegItem(map);
				}else {
					InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), message);
				}
				 
				 

			} catch (Exception e) {
				// TODO: handle exception
			}
	     return resultMessage;

	    }
	 
	 public String getTagValue(String tag, Element eElement) {
			NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
			Node nValue = (Node) nlList.item(0);
			if (nValue == null)
				return "";
			return nValue.getNodeValue();
		}
	 
	 public Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse(new InputSource(new StringReader(str)));

			return document;
		}
	 public String getHttpHTML_POST_Upt(String dataUrl, Goods bookInfo) {

		 String resultMessage = "";
	     String sReturn = "";
	     try {

				URL url = new URL("http://ipss1.interpark.com/openapi/product/ProductAPIService.do"); // 호출할 url
				Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
				params.put("_method", "UpdateProductAPIData");
				params.put("citeKey", "M5xTY3Gj80aYo^1mFh^2AggrCt25x6mi");
				params.put("secretKey", "iUzCdueylhApI2cVY4czlC3ntWg1jKpN");
				params.put("dataUrl", dataUrl);

				StringBuilder postData = new StringBuilder();
				for (Map.Entry<String, Object> param : params.entrySet()) {
					if (postData.length() != 0)
						postData.append('&');
					postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				}
				byte[] postDataBytes = postData.toString().getBytes("UTF-8");

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
				conn.setDoOutput(true);
				conn.getOutputStream().write(postDataBytes); // POST 호출

				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) { // response 출력
					response.append(inputLine);
				}
				System.out.println(response.toString());
				in.close();
				sReturn = response.toString();

				 Document doc = toXmlDocument(sReturn);
				 NodeList list = doc.getElementsByTagName("result");
				 
				 String title ="";
				 String message ="";
				 String productNo = "1";
				 if (list != null && list.getLength() > 0) {
						for (int i = 0; i < list.getLength(); i++) {

							Node node = list.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								title = getTagValue("title", eElement);
								
								NodeList nlList_sucess = eElement.getElementsByTagName("success");
								if (nlList_sucess != null && nlList_sucess.getLength() > 0) {
									Node sucessNode = nlList_sucess.item(0);
									Element nElement = (Element) sucessNode;
									productNo = getTagValue("prdNo", nElement);
								}
								
								NodeList nlList_error = eElement.getElementsByTagName("error");
								if (nlList_error != null && nlList_error.getLength() > 0) {
									Node errorNode = nlList_error.item(0);
									Element nElement = (Element) errorNode;
									message = getTagValue("explanation", nElement);
								}
								System.out.println(title);

							}
						}
				 }
					
				if (message.equals(""))
				{
					
					GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					
					java.util.HashMap<String, Object> map = new HashMap<String, Object>();						
					map.put("ITEMCD", bookInfo.get_ITEMCD());
					map.put("PRODUCTNO", productNo);
					map.put("ISBN13", bookInfo.get_ISBN13());
					map.put("BOOK_NM", bookInfo.get_BOOK_NM());
					map.put("BOOKSTS", bookInfo.get_BOOKSTS());
					map.put("SALEPRICE", bookInfo.get_SALEPRICE());
					map.put("PRICE", bookInfo.get_PRICE());

					goodsDAO.UptRegItem(map);
				}else {
					InsertError("UptGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), message);
				}
				 
				 

			} catch (Exception e) {
				// TODO: handle exception
			}
	     return resultMessage;

	    }
	 
	 
	 public String Send_POST_Upt(String sParam, String Key, String surl,Goods bookInfo) {

	        //params
	        String method = "POST";

	        String resultMessage = "";
	       
	        CloseableHttpClient client = null;
	        try {
	            //create client
	            client = HttpClients.createDefault();
	            //build uri
	            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
	           
	            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

	            StringEntity params =new StringEntity(sParam,"UTF-8");
	            // set header, demonstarte how to use hmac signature here
	            requestPost.addHeader("Authorization", Key);
	            requestPost.addHeader("Accept-Language", "ko");
	            requestPost.addHeader("X-Timezone", "GMT+09:00");
	          
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
	                String strResult = EntityUtils.toString(entity);
		               
	                System.out.println("result:" + strResult);
	                
	                String spdNo = "";
					String resultCode = "";
					
	                
	               
					
	               
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
	        
	        return "";
	    }
	 

	 public String getHttpHTML_POST_Order(String GET_URL) throws ClientProtocolException, IOException {
		 StringBuffer response = new StringBuffer();		  	
		 try {
			 	CloseableHttpClient httpClient = HttpClients.createDefault();
			  
		        //get 메서드와 URL 설정
		        HttpGet httpGet = new HttpGet(GET_URL);
		 
		        //agent 정보 설정
		        httpGet.addHeader("User-Agent", "Mozila/5.0");
		        
		        //get 요청
		        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		        
		        System.out.println("::GET Response Status::");
		        
		        //response의 status 코드 출력
		        System.out.println(httpResponse.getStatusLine().getStatusCode());
		 
		        BufferedReader reader = new BufferedReader(new InputStreamReader(
		                httpResponse.getEntity().getContent()));
		 
		        String inputLine;
		       
		        while ((inputLine = reader.readLine()) != null) {
		            response.append(inputLine);
		        }
		        
		        reader.close();
		 
		        //Print result
		        System.out.println(response.toString());
		        httpClient.close();
		 	
		  	}catch (Exception e) {
				// TODO: handle exception
		  		System.out.println(e.getMessage().toString());
			}

	  	       return response.toString();
	    }
	
	public void InsertError(String ERROR_GUBUN, String ISBN13, String ITEMCD, String MSG) {
		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ERROR_GUBUN", ERROR_GUBUN);
		map.put("ISBN13", ISBN13);
		map.put("ITEMCD", ITEMCD);
		map.put("MSG", MSG);

		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		goodsDAO.InsertError(map);
	}
	public void InsertInterpark(String ERROR_GUBUN, String ISBN13, String ITEMCD, String MSG) {
		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ERROR_GUBUN", ERROR_GUBUN);
		map.put("ISBN13", ISBN13);
		map.put("ITEMCD", ITEMCD);
		map.put("MSG", MSG);

		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		goodsDAO.InsertInterpark(map);
	}
}

