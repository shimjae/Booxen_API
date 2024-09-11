
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.coupang.openapi.sdk.Hmac;


import java.io.StringReader;
import java.math.BigDecimal;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import dao.GoodsDAO;
import dao.MemberDAO;
import dao.OrderDAO;
import model.Goods;
import model.Member;
import model.Order;
import mybatis.MyBatisConnectionFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Idotbi_Coupang {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("=====사용자 start=====");

			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			MemberDAO memberDAO = new MemberDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			List<Member> MemberList = null;
			
			try {
				MemberList = memberDAO.GetMemberList();
				
				for (Member MemberInfo : MemberList) {
					work wk = new work();
				
					System.out.println("=====주문취소 start=====");
					wk.GetOrderCancel(MemberInfo);
					System.out.println("=====주문취소End=====");
					
					
					//송장입력
			    	try {
			    		System.out.println("=====송장입력 start=====");
			    		wk.GetTransNoList(); //
			    		System.out.println("=====송장입력End=====");
			    		
			    	}catch (Exception e) {
						// TODO: handle exception
					}
			    	
					System.out.println("=====주문 start=====");
					wk.GetOrderInfo(MemberInfo);
					System.out.println("=====주문End=====");
					
					
					System.out.println("=====상품 start=====");
					List<Goods> bookList = goodsDAO.GetGoodsList();
					for (Goods bookInfo : bookList) {
						try {
							
							java.util.HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("MALL_ID", MemberInfo.get_MALL_ID());
							map.put("ISBN13", bookInfo.get_ISBN13());

							int Pcnt = goodsDAO.GetExistGoodsList(map);

							if (Pcnt < 1 && (bookInfo.get_BOOKSTS().equals("정상")
									|| bookInfo.get_BOOKSTS().equals("현매"))) {
								System.out.println("상품등록: " + bookInfo.get_ISBN13());									
								wk.RegProduct(MemberInfo,bookInfo);
								
							}
							
						}catch (Exception e) {
							// TODO: handle exception
						}
						
					}
					
					// 상품업데이트
					try {

						List<Goods> bookUptList = goodsDAO.GetUptGoodsList();
						for (Goods bookInfo : bookUptList) {
							System.out.println("상품업데이트 ISBN13: " + bookInfo.get_ISBN13());
							java.util.HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("MALL_ID", MemberInfo.get_MALL_ID());
							map.put("ISBN13", bookInfo.get_ISBN13());							
							int Pcnt = goodsDAO.GetExistGoodsList(map);

							if (Pcnt > 0) {

								
								String VendorItem = wk.Get_VendorItemId(bookInfo.get_PRODUCTNO());
								wk.UptGoodsAPI(bookInfo, MemberInfo , VendorItem);
							}
						}
					} catch (Exception e) {
					}
					System.out.println("=====상품 end=====");
					
					
				
					// 상품업데이트 끝
					try {

						List<Goods> bookApproveList = goodsDAO.GetApproveGoodsList();
						for (Goods bookInfo : bookApproveList) {
							System.out.println("상품승인 ISBN13: " + bookInfo.get_ISBN13());
							wk.Product_Approval_requests(MemberInfo,bookInfo,bookInfo.get_PRODUCTNO());
						}
					} catch (Exception e) {
					}
					System.out.println("=====상품승인 end=====");
					 System.exit(0);
				}
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}

class work {
	//replace prod url when you need
    private static final String HOST = "api-gateway.coupang.com";
    private static final int PORT = 443;
    private static final String SCHEMA = "https";
    //replace with your own accessKey
    private static final String ACCESS_KEY = "42027dad-1937-49ac-867c-1d7699663a35";
    //private static final String ACCESS_KEY = "6f9f604d-4084-4dc9-9221-21fd190bad05"; //븍마우스
    //replace with your own secretKey
    private static final String SECRET_KEY = "46a6f375f0c38b9746c7ce5985a47f6410f8000d";
    //private static final String SECRET_KEY = "04907579c03a13253acf8963890c1b908522133d";//븍마우스
	
    
    
    public void GetTransNoList()
	{
    	
		try
		{
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			List list = orderDAO.GetTransNoList();
			
			for(int i=0; i < list.size();i++)
			{
				HashMap map = (HashMap)list.get(i);
				
				String shipmentBoxId = (String)map.get("SHOPLINKER_ORDER_ID");
				String orderId  = (String)map.get("ORD_ID");
				String MALL_ID = (String)map.get("MALL_ID");				
				String PRODUCTNO= (String)map.get("PRODUCTNO");
				String TRNS_NO= (String)map.get("TRNS_NO");
				String ISBN13 = (String)map.get("ISBN13");
				if (!PRODUCTNO.equals(""))
				{
					
					
					RegOrder_invoices(shipmentBoxId , orderId,PRODUCTNO,TRNS_NO,MALL_ID,ISBN13);		
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
    
    
    public void UptGoodsAPI( Goods bookInfo, Member MemberInfo, String VendorItem)
			throws ParserConfigurationException, SAXException, IOException {
    	
    	   String method = "PUT";
    	   String path = "";
    	   
    	   if (!bookInfo.get_BOOKSTS().equals("정상") && !bookInfo.get_BOOKSTS().equals("현매"))
    	   {
    		   //1.품절처리
    		  path = "/v2/providers/seller_api/apis/api/v1/marketplace/vendor-items/" + VendorItem +"/sales/stop";    		
    	   }else if(!bookInfo.get_PRICE().equals(bookInfo.get_SALEPRICE()) )
    	   {
    		   
    		double SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
   			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
   			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
   			SALEPRICE = incm_civil_amt.doubleValue();   		 
   			bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
   		
    		 //1.가격처리
     		  path = "/v2/providers/seller_api/apis/api/v1/marketplace/vendor-items/" + VendorItem +"/prices/" + String.valueOf(SALEPRICE).replace(".0", "");
    	   }else
    	   {
    		   if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))
        	   {
        		   //1.정상
        		  path = "/v2/providers/seller_api/apis/api/v1/marketplace/vendor-items/" + VendorItem +"/sales/resume";    		
        	   }
    	   }
    	   
    	   
    	   if (!path.equals(""))
    	   {

    		   java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();
				map_1.put("MALL_ID", MemberInfo.get_MALL_ID());
				map_1.put("ITEMCD", bookInfo.get_ITEMCD());
				map_1.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
				map_1.put("ISBN13", bookInfo.get_ISBN13());
				map_1.put("BOOK_NM", bookInfo.get_BOOK_NM());
				map_1.put("BOOKSTS", bookInfo.get_BOOKSTS());
				map_1.put("SALEPRICE", bookInfo.get_SALEPRICE());
				map_1.put("PRICE", bookInfo.get_PRICE());
			
				
				CloseableHttpClient client = null;
				
   	        try {
   	            //create client
   	            
   	            //build uri
   	        	client = HttpClients.createDefault();
   	        	
   	            URIBuilder uriBuilder = new URIBuilder().setPath(path);

   	            /********************************************************/
   	            //authorize, demonstrate how to generate hmac signature here
   	            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
   	            //print out the hmac key
   	            System.out.println(authorization);
   	            /********************************************************/

   	        
   	            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
   	            HttpPut requestPut = new HttpPut(uriBuilder.build().toString());
   	            
	   	         requestPut.addHeader("Authorization", authorization);
	             /********************************************************/
	             requestPut.addHeader("X-Requested-By", "A00199687");
	             requestPut.addHeader("content-type", "application/json");
	             CloseableHttpResponse response = null;
	             
	             try {
	                 //execute post request
	                 response = client.execute(requestPut);
	                 //print result
	                 System.out.println("status code:" + response.getStatusLine().getStatusCode());
	                 System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
	                 HttpEntity entity = response.getEntity();
	                 System.out.println("result:" + EntityUtils.toString(entity));
	                 
	                   
	   	                if (response.getStatusLine().getStatusCode() == 200)
	   	                {
	   						GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	   						
	   						goodsDAO.UptRegItem(map_1);
	   	                }else {
		   	                 if (response.getStatusLine().getStatusCode() == 400)
		   	                 {
		   	                	//DelGoodsAPI(  bookInfo,  MemberInfo,  VendorItem);
		   	                 }
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
   	          
   	        }
    	   }
	       
	       
    
    }
    
    
    public void DelGoodsAPI( Goods bookInfo, Member MemberInfo, String VendorItem)
			throws ParserConfigurationException, SAXException, IOException {
    	
    	   String method = "DELETE";
    	   String path = "";
    	   
    	    path = "/v2/providers/seller_api/apis/api/v1/marketplace/seller-products/" + VendorItem;  
    	   
    	   if (!path.equals(""))
    	   {

    		   java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();
				map_1.put("MALL_ID", MemberInfo.get_MALL_ID());				
				map_1.put("ISBN13", bookInfo.get_ISBN13());				
				
				CloseableHttpClient client = null;
				
   	        try {
   	            //create client
   	            
   	            //build uri
   	        	client = HttpClients.createDefault();
   	        	
   	            URIBuilder uriBuilder = new URIBuilder().setPath(path);

   	            /********************************************************/
   	            //authorize, demonstrate how to generate hmac signature here
   	            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
   	            //print out the hmac key
   	            System.out.println(authorization);
   	            /********************************************************/

   	        
   	            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
    	        HttpDelete requestDelete = new HttpDelete(uriBuilder.build().toString());

   	            
    	        requestDelete.addHeader("Authorization", authorization);	             
	             requestDelete.addHeader("content-type", "application/json");
	             CloseableHttpResponse response = null;
	             
	             try {
	                 //execute post request
	                 response = client.execute(requestDelete);
	                 //print result
	                 System.out.println("status code:" + response.getStatusLine().getStatusCode());
	                 System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
	                 HttpEntity entity = response.getEntity();
	                 System.out.println("result:" + EntityUtils.toString(entity));
	                 
	                   
	   	                if (response.getStatusLine().getStatusCode() == 200)
	   	                {
	   						GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	   						
	   						goodsDAO.DelRegItem(map_1);
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
   	          
   	        }
    	   }
	       
	       
    
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
    
    public void GetOrderCancel(Member MemberInfo)
    {
    	String sRtn = getOrderCancelHttpHTML_Get();
    	if (!sRtn.equals(""))
    	{
    		try
    		{
    			 JSONParser jsonParser = new JSONParser();
 				JSONObject jsonObj = (JSONObject)jsonParser.parse( sRtn );							 				 
 				 				 				
 				JSONArray jsonArray = (JSONArray)jsonObj.get("data");

 				for(int i=0;i<jsonArray.size();i++){

 					JSONObject jsonObjOrder = (JSONObject)jsonArray.get(i);
 					String orderId = jsonObjOrder.get("orderId").toString();
 					String createdAt = jsonObjOrder.get("createdAt").toString(); // 취소시간
 					String receiptStatus = jsonObjOrder.get("receiptStatus").toString(); // 취소(반품)진행 상태
 					
 					String cancelReason = jsonObjOrder.get("cancelReasonCategory1").toString();
 					String requesterName = jsonObjOrder.get("requesterName") == null ? "" :jsonObjOrder.get("requesterName").toString(); // 반품 신청인
 					
 				
 					JSONArray jsonArray_orderItems = (JSONArray)jsonObjOrder.get("returnItems");
 					
 					//JSONArray jsonArray_orderItems = (JSONArray)jsonObj_orderItems;
 					
 					
 					for(int ix=0;ix<jsonArray_orderItems.size();ix++){
 						JSONObject jsonObjOrder_Items = (JSONObject)jsonArray_orderItems.get(ix);
 						String sellerProductId=jsonObjOrder_Items.get("sellerProductId").toString();
 						String Item_NM = jsonObjOrder_Items.get("vendorItemName").toString();
 						String isbn13 = getOrderISBN13HttpHTML_Get(sellerProductId);
 						String cancelCount = jsonObjOrder_Items.get("cancelCount").toString(); // 총 취소수량
 						if (!isbn13.equals(""))
 						{
 							if (isbn13.substring(0,1).equals("8") || isbn13.substring(0,1).equals("9"))
							{
									if (isbn13.indexOf(".") < 0)
									{
										
										java.util.HashMap<String, Object> map = new HashMap<String, Object>();
 										map.put("MALL_ID", MemberInfo.get_MALL_ID());
 										map.put("ORD_ID", orderId);
 										map.put("ISBN13", isbn13);
 									
 										OrderDAO orderDAO = new OrderDAO(
 												MyBatisConnectionFactory.getSqlSessionFactory());

 										int OrdCnt = orderDAO.GetExistBooxenOrderList(map);
 										int CanCnt = orderDAO.GetExistCancelOrderList(map);
 										if (OrdCnt > 0 && CanCnt < 1)
 										{
 											
 											List list = orderDAO.GetOrderList(map);
 											for(int ic=0; ic < list.size();ic++)
 											{
 												HashMap map_list = (HashMap)list.get(ic);	
 												requesterName = (String)map_list.get("DLV_NM");	
 												
 											}
 											
 											java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
	 											
	 											maping.put("MALL_ID", MemberInfo.get_MALL_ID());
	 											maping.put("SITE", "이노플리아 - 쿠팡");
	 											maping.put("ORD_ID", orderId);	 	
	 											maping.put("ORDER_NM", requesterName);				
	 											maping.put("ORD_NO", 1);
	 											maping.put("SELL_QTY", cancelCount);	 											
	 											maping.put("ISBN13", isbn13);
	 											maping.put("ITEM_NM", Item_NM);
	 											maping.put("ORD_MSG", cancelReason);	
	 											
	 										
	 											SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	 											Date date = simpleDateFormat.parse(createdAt.replace("T", " "));

	 											maping.put("CANCEL_TIME", createdAt.replace("T", " "));	 											
	 											orderDAO.InsertCancelOrdList(maping);
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
    
    public void GetOrderInfo(Member MemberInfo)
    {
    	String sRtn = getOrderHttpHTML_Get();
    		
    	if (!sRtn.equals(""))
    	{
    		try
    		{
    			 JSONParser jsonParser = new JSONParser();
 				JSONObject jsonObj = (JSONObject)jsonParser.parse( sRtn );							 				 
 				 				 				
 				JSONArray jsonArray = (JSONArray)jsonObj.get("data");

 				for(int i=0;i<jsonArray.size();i++){

 					JSONObject jsonObjOrder = (JSONObject)jsonArray.get(i);
 					
 					String orderId = jsonObjOrder.get("orderId").toString();
 					String receiver = jsonObjOrder.get("receiver").toString();
 					String orderedAt = jsonObjOrder.get("orderedAt").toString().substring(0,10).replace("-", "");
 					String shippingPrice = jsonObjOrder.get("shippingPrice").toString();
 					String parcelPrintMessage= jsonObjOrder.get("parcelPrintMessage").toString();
 					JSONObject jsonObj_Receiver = (JSONObject)jsonParser.parse( receiver );	
 					String safeNumber =  jsonObj_Receiver.get("safeNumber") != null  ? jsonObj_Receiver.get("safeNumber").toString(): jsonObj_Receiver.get("receiverNumber").toString();
 					String receiverNumber =jsonObj_Receiver.get("receiverNumber")!= null? jsonObj_Receiver.get("receiverNumber").toString(): jsonObj_Receiver.get("safeNumber").toString();
 					String addr1 = jsonObj_Receiver.get("addr1").toString();
 					String addr2 = jsonObj_Receiver.get("addr2").toString();
 					String name = jsonObj_Receiver.get("name").toString();
 					String postCode = jsonObj_Receiver.get("postCode").toString();
 					
 					String shipmentBoxId = jsonObjOrder.get("shipmentBoxId").toString();
 					
 					if (orderId.equals("3000050983614"))
 					{
 						
 					}
 				
 					JSONArray jsonArray_orderItems = (JSONArray)jsonObjOrder.get("orderItems");
 					
 					//JSONArray jsonArray_orderItems = (JSONArray)jsonObj_orderItems;
 					
 					
 					for(int ix=0;ix<jsonArray_orderItems.size();ix++){
 						JSONObject jsonObjOrder_Items = (JSONObject)jsonArray_orderItems.get(ix);
 						String isbn13 = "";
 						if(jsonObjOrder_Items.get("externalVendorSkuCode") != null)
 						{
 							isbn13=jsonObjOrder_Items.get("externalVendorSkuCode").toString();
 						}
 							
 						
 						String shippingCount = jsonObjOrder_Items.get("shippingCount").toString();
 						
 						if (isbn13!=null)
 						{
 							if (!isbn13.equals(""))
 							{
 								if (isbn13.substring(0,1).equals("8") || isbn13.substring(0,1).equals("9"))
 								{
 									if (isbn13.indexOf(".") < 0)
 									{
 										

 	 									java.util.HashMap<String, Object> map_Seq = new HashMap<String, Object>();
 	 									map_Seq.put("ORD_ID", orderId);
 										
 										OrderDAO orderDAO = new OrderDAO(
 												MyBatisConnectionFactory.getSqlSessionFactory());

 										
 										int ORD_NO = orderDAO.GetOrderSeq(map_Seq);
 										
 										Order Order = new Order();
 	 									Order.Set_ORD_DATE(orderedAt);
 	 									Order.Set_ORD_ID(orderId);
 	 									Order.Set_ISBN13(isbn13);
 	 									Order.Set_ORD_NO(String.valueOf(ORD_NO));
 	 									Order.Set_SELL_QTY(shippingCount);
 	 									Order.Set_DLV_PRICE(shippingPrice);
 	 									Order.Set_DLV_POST(postCode);
 	 									Order.Set_DLV_ADDR1(addr1);
 	 									Order.Set_DLV_ADDR2(addr2);
 	 									Order.Set_DLV_MSG(parcelPrintMessage);
 	 									Order.Set_DLV_NM(name);
 	 									Order.Set_DLV_TEL(safeNumber);
 	 									Order.Set_DLV_HP(receiverNumber);
 	 									
 	 									
 	 									java.util.HashMap<String, Object> map = new HashMap<String, Object>();
 										map.put("MALL_ID", MemberInfo.get_MALL_ID());
 										map.put("ORD_ID", Order.Get_ORD_ID());
 										map.put("ISBN13", Order.Get_ISBN13());
 										map.put("ORD_NO", Order.Get_ORD_NO());
/*
 										OrderDAO orderDAO = new OrderDAO(
 												MyBatisConnectionFactory.getSqlSessionFactory());
*/
 										int MstCnt = orderDAO.GetExistBooxenOrderList(map);
 										
 										if (MstCnt < 1)
 										{
 											String strBooxen = getHttpHTML_Booxen("http://api-b.booxen.com/API/BookSaleInfoOnline",
 		 											MemberInfo, isbn13);
 		 									
 		 									strBooxen = strBooxen.replace("callback(", "");
 		 									strBooxen = strBooxen.substring(0, strBooxen.length() - 2); 				                
 		 									JSONObject jsonObj_Out = (JSONObject)jsonParser.parse(strBooxen);	
 		 									String RESULTCD = jsonObj_Out.get("RESULTCD").toString();
 		 									
 		 									if (RESULTCD.equals("200"))
 		 									{

 	 		 									String OUTRT2 = jsonObj_Out.get("OUTRT2").toString();
 	 		 									String Price = jsonObj_Out.get("PRICE").toString();
 	 		 									String ITEM_NM =jsonObj_Out.get("NAME").toString();
 	 		 									
 		 										double SELL_AMT = Double.parseDouble(Price);
 	 											double SELL_TOTAMT = SELL_AMT
 	 													* Double.parseDouble(Order.Get_SELL_QTY());
 	 											
 	 											
 	 											Order.Set_SELL_AMT(String.valueOf((int) SELL_AMT));
 	 											Order.Set_ITEM_NM(ITEM_NM);
 	 		 									
 	 											
 	 											
 	 											java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
 	 											maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
 	 											maping.put("SELLER_ID", "booxen");
 	 											maping.put("MALL_ID", MemberInfo.get_MALL_ID());
 	 											maping.put("ORD_ID", Order.Get_ORD_ID());
 	 											maping.put("ORD_NO", Order.Get_ORD_NO());
 	 											maping.put("SELL_QTY", Order.Get_SELL_QTY());
 	 											maping.put("SELL_AMT", String.valueOf((int) SELL_TOTAMT));
 	 											maping.put("SELL_TOTAMT", String.valueOf((int) SELL_TOTAMT));
 	 											maping.put("ISBN13", Order.Get_ISBN13());
 	 											maping.put("ITEM_NM", Order.Get_ITEM_NM());
 	 											maping.put("DLV_PRICE", Order.Get_DLV_PRICE());
 	 											maping.put("DLV_POST", Order.Get_DLV_POST());
 	 											maping.put("DLV_ADDR1", Order.Get_DLV_ADDR1());
 	 											maping.put("DLV_ADDR2", Order.Get_DLV_ADDR2());
 	 											maping.put("DLV_MSG", Order.Get_DLV_MSG());
 	 											maping.put("DLV_NM", Order.Get_DLV_NM());
 	 											maping.put("DLV_TEL", Order.Get_DLV_TEL());
 	 											maping.put("DLV_HP", Order.Get_DLV_HP());
 	 											maping.put("SHIPMENTBOXID", shipmentBoxId);
 	 											try {
 	 												if (orderDAO.InsertBooxenOrdList(maping) > 0) {
 	 													Set_OrderConfirm(shipmentBoxId);

 	 												}
 	 											} catch (Exception ex) {
 	 												InsertError("RegOrder", Order.Get_ORD_ID(), "",
 	 														ex.getMessage());
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
 				  e.printStackTrace();
 				  
			}
    					
			
    	}
    }
    
    public String Set_OrderConfirm(String shipmentBoxId) {
		String sReturn = "";
		try {
			
			String path = "/v2/providers/openapi/apis/api/v4/vendors/A00199687/ordersheets/acknowledgement";
			String method = "PATCH";
			
			String sBody ="";
			sBody = "{\"vendorId\":\""+ "A00199687" + "\"," +
					 "\"shipmentBoxIds\":[\"" + shipmentBoxId+ "\"" +		       			       	
					"]}";
			
			CloseableHttpClient client = null;
			
			 
			client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path);                                     
            
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            
            //print out the hmac key
            System.out.println(authorization);
            /********************************************************/
            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);    
            
            HttpPatch patch = new HttpPatch(uriBuilder.build().toString());
            patch.addHeader("Authorization", authorization);
            /********************************************************/
            patch.addHeader("content-type", "application/json");
            patch.addHeader("X-Reqeusted-By", "A00199687");
            patch.setEntity(new StringEntity(sBody, "UTF-8"));
            CloseableHttpResponse response = null;
            
           
            try {
                //execute get request
                response = client.execute(patch);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                
                String strResult = EntityUtils.toString(entity);
                System.out.println("result:" + strResult);
                   
                
               
				
				sReturn = strResult;
				
				
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
			// TODO: handle exception
		}

		return sReturn;

	}
    
    public String Get_VendorItemId(String COUPANG_ITEMCD) {
		String sReturn = "";
		try {

			String path = "/v2/providers/seller_api/apis/api/v1/marketplace/seller-products/" + COUPANG_ITEMCD;
			String method = "GET";
			
			CloseableHttpClient client = null;
			
			Date date = Calendar.getInstance().getTime();

			Date yesterday = new Date();
			yesterday.setTime(date.getTime() - ((long) 1000 * 60 * 60 ));
			
			 
			client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path);
            
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            
            //print out the hmac key
            System.out.println(authorization);
            /********************************************************/
            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);    
            HttpGet get = new HttpGet(uriBuilder.build().toString());
            get.addHeader("Authorization", authorization);
            /********************************************************/
            get.addHeader("content-type", "application/json");
            get.addHeader("X-Reqeusted-By", "A00199687");
            CloseableHttpResponse response = null;
            
           
            try {
                //execute get request
                response = client.execute(get);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                
                String strResult = EntityUtils.toString(entity);
                System.out.println("result:" + strResult);
                
                JSONParser jsonParser = new JSONParser();
 				JSONObject jsonObj = (JSONObject)jsonParser.parse( strResult );							 				 
 				String sData = jsonObj.get("data").toString();
			 
 				JSONObject jsonObj_Array = (JSONObject)jsonParser.parse( sData );	
 				
 				JSONArray jsonArray_Items = (JSONArray)jsonObj_Array.get("items");
 				
  				String vendorItemId="";
 				
  				for(int i=0;i<jsonArray_Items.size();i++){

 					JSONObject jsonObjOrder = (JSONObject)jsonArray_Items.get(i);
 					
 					vendorItemId = jsonObjOrder.get("vendorItemId").toString();
 				}
  				
 				
 						
 			
				sReturn = vendorItemId;
				
				
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
			// TODO: handle exception
		}

		return sReturn;

	}
    
    public String getOrderHttpHTML_Get() {
  		String sReturn = "";
  		try {

  			String path = "/v2/providers/openapi/apis/api/v4/vendors/A00199687/ordersheets";
  			String method = "GET";
  			
  			CloseableHttpClient client = null;
  			
  			Date date = Calendar.getInstance().getTime();

  			Date yesterday = new Date();
  			yesterday.setTime(date.getTime() - ((long) 1000 * 60 * 60 * 72 ));
  			
  			 
  			client = HttpClients.createDefault();
              //build uri
              URIBuilder uriBuilder = new URIBuilder()
                      .setPath(path)
                      .addParameter("createdAtFrom", ex1(yesterday, "yyyy-MM-dd"))
                      .addParameter("createdAtTo", ex1(date, "yyyy-MM-dd"))
                      .addParameter("maxPerPage", "50")
                      .addParameter("status", "ACCEPT")
              		  .addParameter("nextToken", "2338591715");
              
              
              String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
              
              //print out the hmac key
              System.out.println(authorization);
              /********************************************************/
              uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);    
              HttpGet get = new HttpGet(uriBuilder.build().toString());
              get.addHeader("Authorization", authorization);
              /********************************************************/
              get.addHeader("content-type", "application/json");
              get.addHeader("X-Reqeusted-By", "A00199687");
              CloseableHttpResponse response = null;
              
             
              try {
                  //execute get request
                  response = client.execute(get);
                  //print result
                  System.out.println("status code:" + response.getStatusLine().getStatusCode());
                  System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                  HttpEntity entity = response.getEntity();
                  
                  String strResult = EntityUtils.toString(entity);
                  System.out.println("result:" + strResult);
                     
                  
                 
  				
  				sReturn = strResult;
  				
  				
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
  			// TODO: handle exception
  		}

  		return sReturn;

  	}
    
    
    public String getOrderCancelHttpHTML_Get() {
  		String sReturn = "";
  		try {

  			String path = "/v2/providers/openapi/apis/api/v4/vendors/A00199687/returnRequests";
  			String method = "GET";
  			
  			CloseableHttpClient client = null;
  			
  			Date date = Calendar.getInstance().getTime();

  			Date yesterday = new Date();
  			yesterday.setTime(date.getTime() - ((long) 1000 * 60 * 60 * 24 ));
  			
  			 
  			client = HttpClients.createDefault();
              //build uri
              URIBuilder uriBuilder = new URIBuilder()
                      .setPath(path)
                      .addParameter("createdAtFrom", ex1(yesterday, "yyyy-MM-dd"))
                      .addParameter("createdAtTo", ex1(date, "yyyy-MM-dd"))
                      .addParameter("maxPerPage", "50")
                      .addParameter("status", "RU");
              
              String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
              
              //print out the hmac key
              System.out.println(authorization);
              /********************************************************/
              uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);    
              HttpGet get = new HttpGet(uriBuilder.build().toString());
              get.addHeader("Authorization", authorization);
              /********************************************************/
              get.addHeader("content-type", "application/json");
              get.addHeader("X-Reqeusted-By", "A00199687");
              CloseableHttpResponse response = null;
              
             
              try {
                  //execute get request
                  response = client.execute(get);
                  //print result
                  System.out.println("status code:" + response.getStatusLine().getStatusCode());
                  System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                  HttpEntity entity = response.getEntity();
                  
                  String strResult = EntityUtils.toString(entity);
                  System.out.println("result:" + strResult);
                     
                  
                 
  				
  				sReturn = strResult;
  				
  				
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
  			// TODO: handle exception
  		}

  		return sReturn;

  	}
      
    public String getOrderISBN13HttpHTML_Get(String sellerProductId) {
		String sReturn = "";
		try {

			String path = "/v2/providers/seller_api/apis/api/v1/marketplace/seller-products/"+sellerProductId;
			String method = "GET";
			
			CloseableHttpClient client = null;
			
			Date date = Calendar.getInstance().getTime();

			Date yesterday = new Date();
			yesterday.setTime(date.getTime() - ((long) 1000 * 60 * 60 * 24 ));
			
			 
			client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path)
                    .addParameter("sellerProductId", sellerProductId);
            
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            
            //print out the hmac key
            System.out.println(authorization);
            /********************************************************/
            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);    
            HttpGet get = new HttpGet(uriBuilder.build().toString());
            get.addHeader("Authorization", authorization);
            /********************************************************/
            get.addHeader("content-type", "application/json");
            get.addHeader("X-Reqeusted-By", "A00199687");
            CloseableHttpResponse response = null;
            
           
            try {
                //execute get request
                response = client.execute(get);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                
                String strResult = EntityUtils.toString(entity);
                    
                
                String externalVendorSku ="";
                JSONParser jsonParser = new JSONParser();
 				JSONObject jsonObj = (JSONObject)jsonParser.parse( strResult );							 				 
 				
 				JSONObject jsonArray = (JSONObject)jsonObj.get("data");	
 				JSONArray jsonArray_items = (JSONArray)jsonArray.get("items");	
 				
 				  System.out.println("result:" + jsonArray_items);
 				
 	 				for(int i=0;i<jsonArray_items.size();i++){

 	 					JSONObject jsonObjOrder = (JSONObject)jsonArray_items.get(i);
 	 					externalVendorSku = jsonObjOrder.get("externalVendorSku").toString();
 	 				}
 			  
				
				sReturn = externalVendorSku;
				
				
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
			// TODO: handle exception
		}

		return sReturn;

	}
    
	 public void RegProduct(Member MemberInfo,Goods bookInfo) {
	        //params
	        String method = "POST";
	        String path = "/targets/wing/seller_api/apis/api/v1/marketplace/seller-products";
	        
	        //replace with your own product registration json data
	        String strjson=GetProductJson(MemberInfo,bookInfo);
	        
	        CloseableHttpClient client = null;
	        try {
	            //create client
	            client = HttpClients.createDefault();
	            //build uri
	            URIBuilder uriBuilder = new URIBuilder().setPath(path);

	            /********************************************************/
	            //authorize, demonstrate how to generate hmac signature here
	            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
	            //print out the hmac key
	            System.out.println(authorization);
	            /********************************************************/

	            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
	            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

	            StringEntity params =new StringEntity(strjson,"UTF-8");
	            // set header, demonstarte how to use hmac signature here
	            requestPost.addHeader("Authorization", authorization);
	          
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
	                
	                
	                JSONParser jsonParser = new JSONParser();
					JSONObject jsonObj = (JSONObject)jsonParser.parse( strResult );							
					 
					

					String code = jsonObj.get("code").toString();
					String data ="";
					if (code.equals("SUCCESS"))
					{
						data = jsonObj.get("data").toString();
					}else {
						data = jsonObj.get("message").toString();
					}
					
	                if (code.equals("SUCCESS"))
	                {
	                	Product_Approval_requests(MemberInfo,bookInfo,data);
	                }else {
	                	InsertError("RegProduct", bookInfo.get_ISBN13(), "",data);
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
	    }
	 
	 public void RegOrder_invoices(String shipmentBoxId, String orderId , String RODUCTNO,String TRNS_NO , String MALL_ID , String ISBN13) {
	        //params
	        String method = "POST";
	        String path = "/v2/providers/openapi/apis/api/v4/vendors/A00199687/orders/invoices";
	        
	        //replace with your own product registration json data
	        
	        String VendorItem = Get_VendorItemId(RODUCTNO);
	        
	        String strjson=GetOrderInvoiceJson(shipmentBoxId,orderId,VendorItem,TRNS_NO);
	        
	        CloseableHttpClient client = null;
	        try {
	            //create client
	            client = HttpClients.createDefault();
	            //build uri
	            URIBuilder uriBuilder = new URIBuilder().setPath(path);

	            /********************************************************/
	            //authorize, demonstrate how to generate hmac signature here
	            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
	            //print out the hmac key
	            System.out.println(authorization);
	            /********************************************************/

	            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
	            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

	            StringEntity params =new StringEntity(strjson,"UTF-8");
	            // set header, demonstarte how to use hmac signature here
	            requestPost.addHeader("Authorization", authorization);
	          
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
	                
	                
	                JSONParser jsonParser = new JSONParser();
					JSONObject jsonObj = (JSONObject)jsonParser.parse( strResult );							
					 
					

					String code = jsonObj.get("code").toString();
					String data ="";
					if (code.equals("200"))
					{
						
						String sData = jsonObj.get("data").toString();
						 
		 				JSONObject jsonObj_Array = (JSONObject)jsonParser.parse( sData );	
		 				
		 				JSONArray jsonArray_Items = (JSONArray)jsonObj_Array.get("responseList");
		 				
		  				String responseCode="";
		 				
		  				for(int i=0;i<jsonArray_Items.size();i++){

		 					JSONObject jsonObjOrder = (JSONObject)jsonArray_Items.get(i);
		 					
		 					responseCode = jsonObjOrder.get("resultCode").toString();
		 					
		 					if (responseCode.equals("OK"))
		 					{
		 						 String RtnMsg="";
			 	 				 java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
			 	 				 map1.put("ORD_ID", orderId);
			 	 				 map1.put("TRNS_NO", TRNS_NO);
			 	 				 map1.put("MALL_ID", MALL_ID);
			 	 				//map1.put("ISBN13", ISBN13);
			 	 				
			 	 				OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			 					
			 					orderDAO.UptTrans(map1);
		 					}
		 							
		 						
		 						
		 				}
		  				
		  				
						
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
	    }
	 public void Product_Approval_requests(Member MemberInfo,Goods bookInfo,String Coupang_Itemcd)
	 {
		    String method = "PUT";
	        String path = "/targets/wing/seller_api/apis/api/v1/marketplace/seller-products/" + Coupang_Itemcd +"/approvals";
	        
	        //replace with your own product registration json data
	       
	       
	        try {
	            //create client
	            
	            //build uri
	            URIBuilder uriBuilder = new URIBuilder().setPath(path);

	            /********************************************************/
	            //authorize, demonstrate how to generate hmac signature here
	            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
	            //print out the hmac key
	            System.out.println(authorization);
	            /********************************************************/

	            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
	            URL  url = new URL(uriBuilder.build().toString());
	            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	            httpCon.setDoOutput(true);
	            httpCon.setRequestMethod("PUT");
	            httpCon.setRequestProperty("Authorization", authorization);
	            httpCon.setRequestProperty("X-Reqeusted-By", "A00199687");
	            httpCon.setRequestProperty("content-type", "application/json");
	            OutputStreamWriter out = new OutputStreamWriter(
	                    httpCon.getOutputStream());
	                out.write("Resource content");
	                out.close();
	                httpCon.getInputStream();
	            int iCode = httpCon.getResponseCode();
	       
	                if (String.valueOf(iCode).equals("200") )
	                {
	                	bookInfo.set_PRODUCTNO(Coupang_Itemcd);
						bookInfo.set_SALEPRICE(String.valueOf(bookInfo.get_SALEPRICE()));

						GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
						
						java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();
						map_1.put("MALL_ID", MemberInfo.get_MALL_ID());
						map_1.put("ISBN13", bookInfo.get_ISBN13());
						int Pcnt = goodsDAO.GetExistGoodsList(map_1);

						if (Pcnt < 1) {
							java.util.HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("MALL_ID", MemberInfo.get_MALL_ID());
							map.put("ITEMCD", bookInfo.get_ITEMCD());
							map.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
							map.put("ISBN13", bookInfo.get_ISBN13());
							map.put("BOOK_NM", bookInfo.get_BOOK_NM());
							map.put("BOOKSTS", bookInfo.get_BOOKSTS());
							map.put("SALEPRICE", bookInfo.get_SALEPRICE());
							map.put("PRICE", bookInfo.get_PRICE());

							goodsDAO.InsertRegItem(map);
						}
	                }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	          
	        }
	 }
	 public String getHttpHTML_Booxen(String urlStr, Member Memberinfo, String ISBN13) {
			String sReturn = "";
			try {
				URL url = new URL(urlStr +"?CompCD=50944&EanCD=" + ISBN13 +"&APIKey=61mohytqjpxer0upz86xopaxjhfh5l");
				HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
				con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
				con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			
				con.setRequestMethod("GET");
	             con.setDoOutput(false); 

				StringBuilder sb = new StringBuilder();
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//Stream을 처리해줘야 하는 귀찮음이 있음. 
					BufferedReader br = new BufferedReader(
							new InputStreamReader(con.getInputStream(), "utf-8"));
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line).append("\n");
					}
					br.close();
					sReturn= sb.toString();
				} else {
					System.out.println(con.getResponseMessage());
				}

			} catch (Exception e) {
				System.err.println(e.toString());
			}

			return sReturn;

		}
	 
	 public String GetProductJson(Member MemberInfo,Goods bookInfo) {
		 String Rtn ="";
		 Date date = Calendar.getInstance().getTime();
		 String SearchTag = bookInfo.get_BOOK_NM().length() >= 18 ? bookInfo.get_BOOK_NM().substring(0, 18) : bookInfo.get_BOOK_NM();
		 
		 double SALEPRICE = 0;
			
		 SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		 
			bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
			if (bookInfo.get_ISBN13().substring(0, 1).equals("8"))
            {
				 Rtn = "{\"displayCategoryCode\":\""+ "36274" + "\"," +
						 "\"sellerProductName\":\"" + bookInfo.get_BOOK_NM()+ "\"," +		       
				       "\"vendorId\":\""+ "A00199687"+ "\"," +		       
				       "\"saleStartedAt\":\"" + ex1(date, "yyyy-MM-dd'T'HH:mm:ss") + "\"," +		       
				       "\"saleEndedAt\":\"2099-01-01T23:59:59\"," +		      
						"\"displayProductName\":\"" + bookInfo.get_BOOK_NM() + "\"," +
						"\"brand\":\"" + bookInfo.get_PUBLISHER() + "\"," +
						"\"deliveryMethod\":\"SEQUENCIAL\"," +
						"\"deliveryCompanyCode\":\"HANJIN\"," +
						"\"deliveryChargeType\":\"" + "CONDITIONAL_FREE" + "\"," +
						"\"deliveryCharge\":\"2500\"," +
						"\"freeShipOverAmount\":\"20000\"," +
						"\"deliveryChargeOnReturn\":\"2500\"," +
						"\"remoteAreaDeliverable\":\"Y\"," +
						"\"unionDeliveryType\":\"UNION_DELIVERY\"," +
						"\"returnCenterCode\":\""+ "1000510410" + "\"," +
						"\"returnChargeName\":\"반품지\"," +
						"\"companyContactNumber\":\"031-955-6648\"," +
						"\"returnZipCode\":\"10881\"," +
						"\"returnAddress\":\"경기도 파주시 문발동 535-6 북센리빙올,\"," +
						"\"returnAddressDetail\":\"-\"," +
						"\"returnCharge\":\"2500\"," +
						"\"returnChargeVendor\":\"N\"," +
						"\"afterServiceInformation\":\"A/S는 판매자와의 연락 후 가능합니다. (단, 병행수입상품은 A/S 불가, 교환 및 반품만 가능)\"," +
						"\"afterServiceContactNumber\":\"0319556648\"," +
						"\"outboundShippingPlaceCode\":\""+"1355536"+"\"," +
						"\"vendorUserId\":\""+ "innoplia" + "\"," +
						"\"requested\":\"false\"," +

				    
		 			   "\"items\":[{" +
		 			   "\"itemName\":\"" + bookInfo.get_BOOK_NM() + "\"," +
		               "\"originalPrice\":" + bookInfo.get_PRICE() + "," +
		               "\"salePrice\":" + bookInfo.get_SALEPRICE() + "," +
		               "\"maximumBuyCount\":\"99999\"," +
		                "\"maximumBuyForPerson\":\"0\"," +
		                "\"outboundShippingTimeDay\":\"2\"," +
		                "\"maximumBuyForPersonPeriod\":\"1\"," +
		                "\"unitCount\":\"1\"," +
		                "\"adultOnly\":\"EVERYONE\"," +
		                
		                "\"taxType\":\"TAX\"," +
		                "\"parallelImported\":\"NOT_PARALLEL_IMPORTED\"," +
		                "\"overseasPurchased\":\"NOT_OVERSEAS_PURCHASED\"," +
		                "\"pccNeeded\":\"false\"," +
		                "\"externalVendorSku\":\"" + bookInfo.get_ISBN13() + "\"," +
		                "\"emptyBarcode\":\"true\"," +
		                "\"emptyBarcodeReason\":\"바코드 없음\"," +
		                "\"barcode\":\"\"," +
		                "\"saleAgentCommission\":\"10\"," +
		                "\"modelNo\":\"국내\"," +
		                            
						"\"images\":[{" +
						"\"imageOrder\":\"0\"," +
		                "\"imageType\":\"REPRESENTATION\"," +
		                "\"vendorPath\":\"" + bookInfo.get_IMG_PATH2().toString().replace(" ","") + "\"}]," +
		            "\"notices\":[{" +
		                  "\"noticeCategoryName\":\"서적\"," +
		                  "\"noticeCategoryDetailName\":\"도서명\"," +
		                  "\"content\":\"" + bookInfo.get_BOOK_NM() + "\"},{" +
		                  "\"noticeCategoryName\":\"서적\"," +
		                  "\"noticeCategoryDetailName\":\"저자, 출판사\"," +
		                  "\"content\":\"상세페이지 참조\"},{" +
		                  "\"noticeCategoryName\":\"서적\"," +
		                  "\"noticeCategoryDetailName\":\"크기(파일의 용량)\"," +
		                  "\"content\":\"상세페이지 참조\"},{" +
		                     "\"noticeCategoryName\":\"서적\"," +
		                  "\"noticeCategoryDetailName\":\"쪽수\"," +
		                  "\"content\":\"상세페이지 참조\"},{" +
		                     "\"noticeCategoryName\":\"서적\"," +
		                  "\"noticeCategoryDetailName\":\"제품 구성\"," +
		                  "\"content\":\"상세페이지 참조\"},{" +
		                   "\"noticeCategoryName\":\"서적\"," +
		                  "\"noticeCategoryDetailName\":\"발행일\"," +
		                  "\"content\":\"상세페이지 참조\"},{" +
		                   "\"noticeCategoryName\":\"서적\"," +
		                  "\"noticeCategoryDetailName\":\"목차 또는 책소개(아동용 학습 교재의 경우 사용연령을 포함)\"," +
		                  "\"content\":\"상세페이지 참조\"}]," +
		              "\"attributes\": [{" +
		                  "\"attributeTypeName\":\"저자\"," +
		                  "\"attributeValueName\":\"상세페이지 참조\"},{" +
		                  "\"attributeTypeName\":\"수상작\"," +
		                  "\"attributeValueName\":\"상세페이지 참조\"},{" +
		                  "\"attributeTypeName\":\"ISBN\"," +
		                  "\"attributeValueName\":\"\"},{" +
		                  "\"attributeTypeName\":\"그린이/일러스트 작가\"," +
		                  "\"attributeValueName\":\"\"},{" +
		                  "\"attributeTypeName\":\"발행언어\"," +
		                  "\"attributeValueName\":\"\"},{" +
		                  "\"attributeTypeName\":\"시리즈\"," +
		                  "\"attributeValueName\":\"\"},{" +
		                  "\"attributeTypeName\":\"출판사\"," +
		                  "\"attributeValueName\":\"상세페이지 참조\"},{" +
		                  "\"attributeTypeName\":\"옮긴이/역자\"," +
		                  "\"attributeValueName\":\"\"},{" +
		                  "\"attributeTypeName\":\"도서형태\"," +
		                  "\"attributeValueName\":\"\"},{" +
		                  "\"attributeTypeName\":\"원제\"," +
		                  "\"attributeValueName\":\"\"}]," +
		               "\"contents\": [{" +
		                   "\"contentsType\":\"TEXT\"," +
		                   "\"contentDetails\":[{" +
		                   "\"content\":\"" + GetGoodsBody_New(bookInfo).replaceAll("\\\"", "'").replaceAll("\\t", "").replaceAll("&nbsp;", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "") + "\"," +
		                   "\"detailType\":\"TEXT\"}]}]," +
		                "\"certifications\" : [{" +
		                   "\"certificationType\":\"NOT_REQUIRED\"," +
		                   "\"certificationCode\":\"\"}]," +
		                 "\"searchTags\": [\"" + SearchTag + "\"]}]," +
		                 "\"manufacture\":\"국내\"}";
				 
            }else {
            	 Rtn = "{\"displayCategoryCode\":\""+ "36274" + "\"," +
        				 "\"sellerProductName\":\"" + bookInfo.get_BOOK_NM()+ "\"," +		       
        		       "\"vendorId\":\""+ "A00199687"+ "\"," +		       
        		       "\"saleStartedAt\":\"" + ex1(date, "yyyy-MM-dd'T'HH:mm:ss") + "\"," +		       
        		       "\"saleEndedAt\":\"2099-01-01T23:59:59\"," +		      
        				"\"displayProductName\":\"" + bookInfo.get_BOOK_NM() + "\"," +
        				"\"brand\":\"" + bookInfo.get_PUBLISHER() + "\"," +
        				"\"deliveryMethod\":\"SEQUENCIAL\"," +
        				"\"deliveryCompanyCode\":\"HANJIN\"," +
        				"\"deliveryChargeType\":\"" + "CONDITIONAL_FREE" + "\"," +
        				"\"deliveryCharge\":\"2500\"," +
        				"\"freeShipOverAmount\":\"20000\"," +
        				"\"deliveryChargeOnReturn\":\"2500\"," +
        				"\"remoteAreaDeliverable\":\"Y\"," +
        				"\"unionDeliveryType\":\"UNION_DELIVERY\"," +
        				"\"returnCenterCode\":\""+ "1000510410" + "\"," +
        				"\"returnChargeName\":\"반품지\"," +
        				"\"companyContactNumber\":\"031-955-6648\"," +
        				"\"returnZipCode\":\"10881\"," +
        				"\"returnAddress\":\"경기도 파주시 문발동 535-6 북센리빙올,\"," +
        				"\"returnAddressDetail\":\"-\"," +
        				"\"returnCharge\":\"2500\"," +
        				"\"returnChargeVendor\":\"N\"," +
        				"\"afterServiceInformation\":\"A/S는 판매자와의 연락 후 가능합니다. (단, 병행수입상품은 A/S 불가, 교환 및 반품만 가능)\"," +
        				"\"afterServiceContactNumber\":\"0319556648\"," +
        				"\"outboundShippingPlaceCode\":\""+"1355536"+"\"," +
        				"\"vendorUserId\":\""+ "innoplia" + "\"," +
        				"\"requested\":\"false\"," +

        		    
         			   "\"items\":[{" +
         			   "\"itemName\":\"" + bookInfo.get_BOOK_NM() + "\"," +
                       "\"originalPrice\":" + bookInfo.get_PRICE() + "," +
                       "\"salePrice\":" + bookInfo.get_SALEPRICE() + "," +
                       "\"maximumBuyCount\":\"99999\"," +
                        "\"maximumBuyForPerson\":\"0\"," +
                        "\"outboundShippingTimeDay\":\"2\"," +
                        "\"maximumBuyForPersonPeriod\":\"1\"," +
                        "\"unitCount\":\"1\"," +
                        "\"adultOnly\":\"EVERYONE\"," +
                        
                        "\"taxType\":\"FREE\"," +
                        "\"parallelImported\":\"NOT_PARALLEL_IMPORTED\"," +
                        "\"overseasPurchased\":\"NOT_OVERSEAS_PURCHASED\"," +
                        "\"pccNeeded\":\"false\"," +
                        "\"externalVendorSku\":\"" + bookInfo.get_ISBN13() + "\"," +
                        "\"emptyBarcode\":\"true\"," +
                        "\"emptyBarcodeReason\":\"바코드 없음\"," +
                        "\"barcode\":\"\"," +
                        "\"saleAgentCommission\":\"10\"," +
                        "\"modelNo\":\"국내\"," +
                                    
        				"\"images\":[{" +
        				"\"imageOrder\":\"0\"," +
                        "\"imageType\":\"REPRESENTATION\"," +
                        "\"vendorPath\":\"" + bookInfo.get_IMG_PATH2().toString().replace(" ","") + "\"}]," +
                    "\"notices\":[{" +
                          "\"noticeCategoryName\":\"서적\"," +
                          "\"noticeCategoryDetailName\":\"도서명\"," +
                          "\"content\":\"" + bookInfo.get_BOOK_NM() + "\"},{" +
                          "\"noticeCategoryName\":\"서적\"," +
                          "\"noticeCategoryDetailName\":\"저자, 출판사\"," +
                          "\"content\":\"상세페이지 참조\"},{" +
                          "\"noticeCategoryName\":\"서적\"," +
                          "\"noticeCategoryDetailName\":\"크기(파일의 용량)\"," +
                          "\"content\":\"상세페이지 참조\"},{" +
                             "\"noticeCategoryName\":\"서적\"," +
                          "\"noticeCategoryDetailName\":\"쪽수\"," +
                          "\"content\":\"상세페이지 참조\"},{" +
                             "\"noticeCategoryName\":\"서적\"," +
                          "\"noticeCategoryDetailName\":\"제품 구성\"," +
                          "\"content\":\"상세페이지 참조\"},{" +
                           "\"noticeCategoryName\":\"서적\"," +
                          "\"noticeCategoryDetailName\":\"발행일\"," +
                          "\"content\":\"상세페이지 참조\"},{" +
                           "\"noticeCategoryName\":\"서적\"," +
                          "\"noticeCategoryDetailName\":\"목차 또는 책소개(아동용 학습 교재의 경우 사용연령을 포함)\"," +
                          "\"content\":\"상세페이지 참조\"}]," +
                      "\"attributes\": [{" +
                          "\"attributeTypeName\":\"저자\"," +
                          "\"attributeValueName\":\"상세페이지 참조\"},{" +
                          "\"attributeTypeName\":\"수상작\"," +
                          "\"attributeValueName\":\"상세페이지 참조\"},{" +
                          "\"attributeTypeName\":\"ISBN\"," +
                          "\"attributeValueName\":\"\"},{" +
                          "\"attributeTypeName\":\"그린이/일러스트 작가\"," +
                          "\"attributeValueName\":\"\"},{" +
                          "\"attributeTypeName\":\"발행언어\"," +
                          "\"attributeValueName\":\"\"},{" +
                          "\"attributeTypeName\":\"시리즈\"," +
                          "\"attributeValueName\":\"\"},{" +
                          "\"attributeTypeName\":\"출판사\"," +
                          "\"attributeValueName\":\"상세페이지 참조\"},{" +
                          "\"attributeTypeName\":\"옮긴이/역자\"," +
                          "\"attributeValueName\":\"\"},{" +
                          "\"attributeTypeName\":\"도서형태\"," +
                          "\"attributeValueName\":\"\"},{" +
                          "\"attributeTypeName\":\"원제\"," +
                          "\"attributeValueName\":\"\"}]," +
                       "\"contents\": [{" +
                           "\"contentsType\":\"TEXT\"," +
                           "\"contentDetails\":[{" +
                           "\"content\":\"" + GetGoodsBody_New(bookInfo).replaceAll("\\\"", "'").replaceAll("\\t", "").replaceAll("&nbsp;", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "") + "\"," +
                           "\"detailType\":\"TEXT\"}]}]," +
                        "\"certifications\" : [{" +
                           "\"certificationType\":\"NOT_REQUIRED\"," +
                           "\"certificationCode\":\"\"}]," +
                         "\"searchTags\": [\"" + SearchTag + "\"]}]," +
                         "\"manufacture\":\"국내\"}";
        		 
            }
		
		 
		 return Rtn;
		 
	 }
	 
	 
	 public String GetOrderInvoiceJson(String shipmentBoxId , String orderId , String vendorItemId, String TRNS_NO) {
		 String Rtn ="";
		 
	
		 Rtn = "{\"vendorId\":\""+ "A00199687" + "\"," +
 			   "\"orderSheetInvoiceApplyDtos\":[{" +
 			   "\"shipmentBoxId\":\"" + shipmentBoxId + "\"," +
               "\"orderId\":" + orderId + "," +
               "\"vendorItemId\":" + vendorItemId + "," +
               "\"deliveryCompanyCode\":\"HANJIN\"," +
                "\"invoiceNumber\":\""+TRNS_NO+"\"," +
                "\"splitShipping\":\"false\"," +
                "\"preSplitShipped\":\"false\"," +
                "\"estimatedShippingDate\":\"\"}]}" ;
              
               
		 return Rtn;
		 
	 }
	 
	 public String GetGoodsBody_New(Goods bookInfo)
		{
			String Str_Contents = "";

         Str_Contents = Str_Contents + "<div class=\"booxen_detail\" style=\"width: 900px; margin: 0 auto; float:none; text-align:left; \">";

         if (!"".equals(bookInfo.get_BOOK_INTRCN_CN()) && bookInfo.get_BOOK_INTRCN_CN() != null)
         {
             StringBuilder sb = new StringBuilder();
             sb.append(" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 책소개</div>");
             sb.append("<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
             sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
             sb.append("<span style = \"display:block;text-align:left;\" >     ");
             sb.append(bookInfo.get_BOOK_INTRCN_CN().replace("\r\n", "<br>") + "</div>");

             Str_Contents = Str_Contents + sb.toString();
         }
         if (!"".equals(bookInfo.get_CNTNT_CN()) && bookInfo.get_CNTNT_CN() != null)
         {
             StringBuilder sb = new StringBuilder();
             sb.append(" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 목차</div>");
             sb.append("<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
             sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
             sb.append("<span style = \"display:block;text-align:left;\" >     ");
             sb.append(bookInfo.get_CNTNT_CN().replace("\r\n", "<br>") + "</div>");

             Str_Contents = Str_Contents + sb.toString();
         }
         if (!"".equals( bookInfo.get_PLSCMPN_BKRVW_CN())&& bookInfo.get_PLSCMPN_BKRVW_CN() != null)
         {
             StringBuilder sb = new StringBuilder();
             sb.append(" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 출판사서평</div>");
             sb.append("<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
             sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
             sb.append("<span style = \"display:block;text-align:left;\" >     ");
             sb.append(bookInfo.get_PLSCMPN_BKRVW_CN().replace("\r\n", "<br>") + "</div>");

             Str_Contents = Str_Contents + sb.toString();
         }
         if (!"".equals(bookInfo.get_AUTHR_INTRCN_CN())&& bookInfo.get_AUTHR_INTRCN_CN() != null)
         {
             StringBuilder sb = new StringBuilder();
             sb.append(" <h3 style=\"padding: 0 0 4px 0px; f color:#323232;  margin-top:30px; position:relative; z-index:1; clear:both; border-bottom:1px solid #7f7f7f;font-family: '맑은 고딕', 'Gulim';font-size: 20px; font-weight: bold;\"><div class=\"tit\" style=\"vertical-align:-2px; margin-right:6px;text-align:left;\">■ 저자소개</div>");
             sb.append("<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span></h3>  ");
             sb.append("<div class=\"detail_con\" style=\"width: 745px;text-align:left;margin-top:10px;\">");
             sb.append("<span style = \"display:block;text-align:left;\" >     ");
             sb.append(bookInfo.get_AUTHR_INTRCN_CN().replace("\r\n", "<br>") + "</div>");

             Str_Contents = Str_Contents + sb.toString();
         }


         return Str_Contents + " </div>";
		}
		
	 public String ex1(Date date, String pattern) {
			String result = null;
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
			result = format.format(date);
			return result;
		}
}
