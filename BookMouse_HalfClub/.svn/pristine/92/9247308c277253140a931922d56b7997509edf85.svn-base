
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.coupang.openapi.sdk.Hmac;
import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import dao.GoodsDAO;
import dao.OrderDAO;
import model.Goods;
import model.Order;
import mybatis.MyBatisConnectionFactory;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookMouse_HalfClub {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			/*
			//송장입력
	    	try {
	    		work wk = new work();	
	    		
	    		
	    		 wk.GetTransNoList("50289"); //스토어팜
	    		
	    	}catch (Exception e) {
				// TODO: handle exception
			}
	   
			 System.out.println("=====주문 start=====");
			   
			    try {
			    	 work wk = new work();
			    	 wk.Get_OrderInfo();
			    	
			    }catch (Exception e) {
				
				}
			    System.out.println("=====주문 end=====");
			  
				
			try {
				OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				List list = orderDAO.GetResetList();

				for (int i = 0; i < list.size(); i++) {
					HashMap map = (HashMap) list.get(i);

					String ITEMCD = (String) map.get("ITEMCD");
					

				}
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			 */
			
			
			// 상품등록
			try {
				
				System.out.println("=====상품등록 start=====");

				List<Goods> bookList = goodsDAO.GetGoodsList();
				// wait
				for (Goods bookInfo : bookList) {
					try
					{
						//int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());
						//Pcnt =0;
						if ((bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))) 
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
				
				
				 //상품가격
			    try {
			    	System.out.println("=====상품가격 업데이트 시작=====");   		   
				    List<Goods> bookUptList = goodsDAO.GetUptGoodsList_PRICE();
				    for(Goods bookInfo: bookUptList){
			            System.out.println("ISBN13: "+bookInfo.get_ISBN13());  
			            work wk = new work();
			            String sJson = "";
			            
			        	
			    		try
			    		{
			    			  JSONObject jsonObject = new JSONObject();    
			    			  JSONArray prdStatCdArray = new JSONArray();               
			    			  JSONArray prdCdArray = new JSONArray();               
			    		        
			    			  prdStatCdArray.add("05");
			    			  prdCdArray.add(bookInfo.get_ISBN13());
			    			  
			    			  jsonObject.put("prdStatCdArray", prdStatCdArray);    
			    			  jsonObject.put("prdCdTyp", "01");
			    			  jsonObject.put("prdCdArray",prdCdArray);
			    		      
			    			String selAcntCd="SA000586";
						    String pwd="cb27df9e-9920-488b-8eef-1c41d33f6f7f";
					
								
			    			String sRtn_PrdStdInfo_Result = wk.getHttp_POST_Json(jsonObject.toJSONString(), selAcntCd, pwd, "http://api.seller-club.co.kr/rest/product/productRegister/getPrdList");
			    		
			    			  
			    			JSONParser jsonParser = new JSONParser();
			    	     	JSONObject jsonObj = (JSONObject)jsonParser.parse(sRtn_PrdStdInfo_Result);							 				 				 				 			   		     	
			    	     	String data = jsonObj.get("data").toString();
			    	     	
			    	     	JSONObject jsonBundle = (JSONObject)jsonParser.parse(data);							 				 				
			    	     	JSONArray jsonArray_Bundle = (JSONArray)jsonBundle.get("result");
			    	     	
			    	     	Order Order = new Order();
			    			
			    	   	 
			    	    	for(int i=0;i<jsonArray_Bundle.size();i++)
			    		   {
			       		 		JSONObject jsonObjOrder = (JSONObject)jsonArray_Bundle.get(i);
			       		 		
			       		 		String prdNo = jsonObjOrder.get("prdNo").toString();
			       		 		
			       		 		bookInfo.set_BORIBORI_ITEMCD(prdNo);
			       		 	    sJson = wk.GetXml_PrdPlcyDtlInfo_Upt(bookInfo);
				        	
					            String sRtn_Plcy = wk.requestPutJson(sJson, selAcntCd, pwd, "http://api.seller-club.co.kr/rest/product/productRegister/putPrdPlcyDtlInfo");
					            
					            JSONParser jsonParser_Price = new JSONParser();
							    JSONObject jsonObj_Price = (JSONObject)jsonParser_Price.parse(sRtn_Plcy);							 				 				 				 			   		     	
							    String data_resultStatus = jsonObj_Price.get("resultStatus").toString();
							     	
							   JSONObject json_data_resultStatus = (JSONObject)jsonParser_Price.parse(data_resultStatus);
							   

								System.out.println(json_data_resultStatus.get("message").toString());
								
							   if (json_data_resultStatus.get("message").toString().equals("success"))
							   {
								   java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
									 map.put("ITEMCD", bookInfo.get_ITEMCD());
									 map.put("BORIBORI_ITEMCD", bookInfo.get_BORIBORI_ITEMCD());
									 map.put("ISBN13", bookInfo.get_ISBN13());
									 map.put("BOOK_NM", bookInfo.get_BOOK_NM());
									 map.put("BOOKSTS", bookInfo.get_BOOKSTS());
									
									 map.put("PRICE", bookInfo.get_PRICE());
									 map.put("PRICE_SALE", bookInfo.get_PRICE_SALE());
									 
									 goodsDAO.UptRegItem(map);
									 
							   }
			    		   }
			    		}catch (Exception e) {
							// TODO: handle exception
						}
			            
						
				    }
					
			    }catch (Exception e) {
				}
			    System.out.println("=====상품가격업데이트 end=====");
			    
			//상품 상태 업데이트
				 try {
				    	System.out.println("=====상품상태 업데이트 시작=====");   		   
					    List<Goods> bookUptList = goodsDAO.GetUptGoodsList_STATUS();
					    for(Goods bookInfo: bookUptList){
				            System.out.println("ISBN13: "+bookInfo.get_ISBN13());  
				            work wk = new work();
				            String sJson = "";
				            
				        	String selAcntCd="SA000586";
							String pwd="cb27df9e-9920-488b-8eef-1c41d33f6f7f";
						
				         
							try
							{
								  JSONObject jsonObject = new JSONObject();    
								  JSONArray prdStatCdArray = new JSONArray();               
								  JSONArray prdCdArray = new JSONArray();               
							        
								  prdStatCdArray.add("05");
								  prdCdArray.add(bookInfo.get_ISBN13());
								  
								  jsonObject.put("prdStatCdArray", prdStatCdArray);    
								  jsonObject.put("prdCdTyp", "01");
								  jsonObject.put("prdCdArray",prdCdArray);
							      
										
								String sRtn_PrdStdInfo_Result = wk.getHttp_POST_Json(jsonObject.toJSONString(), selAcntCd, pwd, "http://api.seller-club.co.kr/rest/product/productRegister/getPrdList");

								  
								JSONParser jsonParser = new JSONParser();
							 	JSONObject jsonObj = (JSONObject)jsonParser.parse(sRtn_PrdStdInfo_Result);							 				 				 				 			   		     	
							 	String data = jsonObj.get("data").toString();
							 	
							 	JSONObject jsonBundle = (JSONObject)jsonParser.parse(data);							 				 				
							 	JSONArray jsonArray_Bundle = (JSONArray)jsonBundle.get("result");
							 	
							 	Order Order = new Order();
								
							 
								for(int i=0;i<jsonArray_Bundle.size();i++)
							   {
									try
									{
										JSONObject jsonObjOrder = (JSONObject)jsonArray_Bundle.get(i);
								 		
								 		String prdNo = jsonObjOrder.get("prdNo").toString();
								 		
								 		String sRtn_Plcy ="";
								 		
								 		JSONObject jsonObject1 = new JSONObject();                  
								 		jsonObject1.put("prdNo", prdNo);    
								 		
								 		
								 	    if ((bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))) 
										 {
								 	    	jsonObject1.put("prdSelCd", "02");
								 	    	sJson = jsonObject1.toJSONString();
										 	 sRtn_Plcy = wk.getHttp_PUT_Json(sJson, selAcntCd, pwd, "http://api.seller-club.co.kr/rest/product/productRegister/putPrdSelCdInfo");
										 }else {
											 
											 jsonObject1.put("prdSelCd", "04");
											 sJson = jsonObject1.toJSONString();
										 	 sRtn_Plcy = wk.getHttp_PUT_Json(sJson, selAcntCd, pwd, "http://api.seller-club.co.kr/rest/product/productRegister/putPrdSelCdInfo");	
										 }
										
										
								 	    if (!sRtn_Plcy.equals(""))
								 	    {
								 	    	jsonParser = new JSONParser();
											  jsonObj = (JSONObject)jsonParser.parse(sRtn_Plcy);							 				 				 				 			   		     	
											 String data_resultStatus = jsonObj.get("resultStatus").toString();
											  	
											JSONObject json_data_resultStatus = (JSONObject)jsonParser.parse(data_resultStatus);
											
										
											if (json_data_resultStatus.get("message").toString().equals("success"))
											{
											   java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
												 map.put("ITEMCD", bookInfo.get_ITEMCD());
												 map.put("BORIBORI_ITEMCD", bookInfo.get_BORIBORI_ITEMCD());
												 map.put("ISBN13", bookInfo.get_ISBN13());
												 map.put("BOOK_NM", bookInfo.get_BOOK_NM());
												 map.put("BOOKSTS", bookInfo.get_BOOKSTS());
												
												 map.put("PRICE", bookInfo.get_PRICE());
												 map.put("PRICE_SALE", bookInfo.get_PRICE_SALE());
												 
												 goodsDAO.UptRegItem(map);
												 
											}								 	    }
										 
										 
									}catch (Exception e) {
										// TODO: handle exception
									}
							 		
							   }
							}catch (Exception e) {
								// TODO: handle exception
							}
					    }
						
				    }catch (Exception e) {
					}
				    System.out.println("=====상품상태업데이트 end=====");
				
			  
				
				System.exit(0);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		
			
		} catch (Exception e) {
		}

	}

}

class work {
	
	public void GetTransNoList(String MALL_ID) {
		try {
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			java.util.HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("MALL_ID", MALL_ID);

			List list = orderDAO.GetTransNoList(map1);

			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap) list.get(i);

				String TRNS_NO = (String) map.get("TRNS_NO");
				String ORD_ID = (String) map.get("ORD_ID");
				String ORD_NO = (String) map.get("ORD_NO");


				if (!TRNS_NO.equals("")) {
					SendTranNo(MALL_ID, TRNS_NO, ORD_ID,ORD_NO );
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void SendTranNo(String MALL_ID, String TRNS_NO, String ORD_ID, String ORD_NO)
			throws ParserConfigurationException, SAXException, IOException, ParseException {

		String selAcntCd = "A8943";
		String pwd = "42094E24-91EF-4851-A9B6-CEDE5B3426E8";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ordNo", ORD_ID);
		jsonObject.put("ordNoNm", ORD_NO);
		jsonObject.put("cjNo", TRNS_NO);
		jsonObject.put("offNo", selAcntCd);

		String sRtn_PrdStdInfo_Result = getHttp_POST_Json(jsonObject.toJSONString(), selAcntCd, pwd,
				"http://api.seller-club.co.kr//rest/order/setDelivery");

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParser.parse(sRtn_PrdStdInfo_Result);
		String data_resultStatus = jsonObj.get("resultStatus").toString();

		JSONObject json_data_resultStatus = (JSONObject) jsonParser.parse(data_resultStatus);

		String prdNo = "";
		if (json_data_resultStatus.get("message").toString().equals("success")) {
			java.util.HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("ORD_ID", ORD_ID);
			map1.put("TRNS_NO", TRNS_NO);
			map1.put("MALL_ID", MALL_ID);

			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			orderDAO.UptTrans(map1);
		}

	}
	public void Get_OrderInfo() throws ParseException
	{
		Date date = Calendar.getInstance().getTime();
				
		Date yesterday = new Date ( );
        yesterday.setTime ( date.getTime ( ) - ( (long) 1000 * 60 * 60 * 720 ) );
        
    	//	테스트
		
		String selAcntCd="SA000586";
		String pwd="cb27df9e-9920-488b-8eef-1c41d33f6f7f";
		
			
		try
		{
			String sRtn_PrdStdInfo_Result = getOrderHttpHTML_Get(selAcntCd, pwd, "http://api.seller-club.co.kr/rest/order/getOrderInfo");
			
			JSONParser jsonParser = new JSONParser();
	     	JSONObject jsonObj = (JSONObject)jsonParser.parse(sRtn_PrdStdInfo_Result);							 				 				 				 			   		     	
	     	String data = jsonObj.get("data").toString();
	     	
	     	JSONObject jsonBundle = (JSONObject)jsonParser.parse(data);							 				 				
	     	JSONArray jsonArray_Bundle = (JSONArray)jsonBundle.get("result");
	     	
	     	Order Order = new Order();
			
	   	 
	    	for(int i=0;i<jsonArray_Bundle.size();i++)
		   {
   		 		JSONObject jsonObjOrder = (JSONObject)jsonArray_Bundle.get(i);
   		 		
   		 		String ordNo = jsonObjOrder.get("ordNo").toString();
   		 		
   		 		String toAddr = jsonObjOrder.get("toAddr") != null ? jsonObjOrder.get("toAddr").toString() :"";
   		 		String toZiCd = jsonObjOrder.get("toZiCd").toString();
		   		String toNm = jsonObjOrder.get("toNm").toString();
		   		String toTel = jsonObjOrder.get("toTel").toString();
		   		String memo = jsonObjOrder.get("memo") != null ? jsonObjOrder.get("memo").toString() :"";
		   		String guestTel = jsonObjOrder.get("guestTel") != null ? jsonObjOrder.get("guestTel").toString() :"";
		   		String fromNm = jsonObjOrder.get("fromNm").toString();
		   		String deliPri = jsonObjOrder.get("deliPri").toString();
		   		String prdQPri = jsonObjOrder.get("prdQPri").toString();
		   		String payPri = jsonObjOrder.get("payPri").toString();
		   		String sumQty = jsonObjOrder.get("sumQty").toString();
		   		String OrderYmd = jsonObjOrder.get("prdSalYmd").toString().substring(0,10).replace("-", "");
		   		
		   		if (toTel.equals(""))
		   		{
		   			toTel = jsonObjOrder.get("guestTel").toString();
		   		}
		   		
		   		JSONArray jsonArray_orderProduct = (JSONArray)jsonObjOrder.get("saleDetailList");
		   		for(int ix=0;ix<jsonArray_orderProduct.size();ix++)
   		 		{
   		 			JSONObject jsonObjOrder_Item = (JSONObject)jsonArray_orderProduct.get(ix);
   		 			ordNo = jsonObjOrder_Item.get("ordNo").toString();
   		 			String ordNoNm = jsonObjOrder_Item.get("ordNoNm").toString();
   		 			String qty= jsonObjOrder_Item.get("qty").toString();
   		 			String ISBN13 =jsonObjOrder_Item.get("prdCd").toString();
   		 			String salPri=jsonObjOrder_Item.get("salPri").toString();
   		 			
   		 			java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
					map.put("ORD_ID", ordNo);
				
					
   		 			String[] arr_ISBN13 = ISBN13.split("_");
   		 			
	   		 		if (ISBN13.indexOf("_UWK") < 1 && (ISBN13.substring(0, 1).equals("8") || ISBN13.substring(0, 1).equals("9")))
	   		 		{
	   		 			arr_ISBN13 = (ISBN13 + "_UWK").split("_");
	   		 		}
					if (arr_ISBN13.length > 1)
					{
						if (arr_ISBN13[0].substring(0, 1).equals("8") || arr_ISBN13[0].substring(0, 1).equals("9"))
						{
							
	 						if (arr_ISBN13[1].replace(" ", "").toUpperCase().equals("UWK"))
	 						{
	 							 ISBN13 = arr_ISBN13[0];	
 								 map.put("ISBN13", ISBN13);
			 					 
			 					 OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			 					 
			 					 int MstCnt = orderDAO.GetExistBooxenOrderList(map);
			 					 
			 					 map.put("ORD_NO", ordNo);
			 					 map.put("ORDNUM_NM", ordNoNm);
			 					
			 					 int DetailCnt = orderDAO.GetExistBooxenDetailOrderList(map);
			 					 
			 					if (MstCnt < 1 )
			 					 {
			 						 double SELL_AMT = Double.parseDouble(salPri);
			 						 double SELL_TOTAMT = SELL_AMT * Double.parseDouble(qty);
			 						 GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			 						 java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
			 						 map1.put("ISBN13", ISBN13);
			 						 
			 						 List<Goods> bookList = goodsDAO.GetBoriboriGoodsList(map1);
			 						 String ITEM_NM ="";
			 						
			 						 for(Goods bookInfo: bookList){
			 							 ITEM_NM = bookInfo.get_BOOK_NM();
			 							 ISBN13 = bookInfo.get_ISBN13();
			 						 }
			 						 
			 						  
			 						 java.util.HashMap<String, Object> maping = new HashMap<String, Object>(); 
			 						 maping.put("ORDERYMD", OrderYmd);
			 						 maping.put("MALL_ID", "50289");
			 						 maping.put("SELLER_ID", "booxen");
			 						 maping.put("PORDNUM", ordNo);
			 						 maping.put("ORDSEQ", ordNoNm);
			 						 maping.put("QTY", qty);
			 						 maping.put("SELL_AMT",   String.valueOf((int)SELL_TOTAMT));
			 						 maping.put("SALPRI", String.valueOf((int)SELL_TOTAMT));
			 						 maping.put("ISBN13", ISBN13);
			 						 maping.put("ITEM_NM", ITEM_NM);
			 						 maping.put("DELIPRI", deliPri);
			 						 maping.put("TOZICD", toZiCd);
			 						 maping.put("TOADDR", toAddr);
			 						 maping.put("MEMO", memo);
			 						 maping.put("TONM", toNm);
			 						 maping.put("TOTEL", toTel);
			 						 maping.put("TOEMTEL", toTel);
			 						 maping.put("ORDNUM_NM", ordNoNm);	
			 						 
			 						maping.put("PCODE", jsonObjOrder_Item.get("prdCd").toString());
			 						maping.put("OPTCD", "1");
			 						maping.put("OPTNM", jsonObjOrder_Item.get("optValueNm").toString());
			 						
			 						if (!toAddr.equals("")) {
			 							 try
				 						 {
				 							if (orderDAO.InsertBooxenOrdList(maping) > 0)
					 						 {			 								 
				 								if (DetailCnt < 1)
				 								{
				 									orderDAO.InsertBooxenDetailList(maping);
				 								}
					 							   Set_OrderConfirm(ordNo,ordNoNm);
					 							
					 						 }
				 						 }catch(Exception ex)
				 						 {
				 							InsertError("RegOrder",ordNo,"",ex.getMessage());
				 						 }
			 						}
			 						
			 						 
			 					 }else {
			 						if (DetailCnt < 1)
			 						 {
			 							 if (MstCnt > 0)
			 							 {
			 								double SELL_AMT = Double.parseDouble(salPri);
					 						 double SELL_TOTAMT = SELL_AMT * Double.parseDouble(qty);
					 						 GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					 						 java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
					 						 map1.put("ISBN13", ISBN13);
					 						 
					 						 List<Goods> bookList = goodsDAO.GetBoriboriGoodsList(map1);
					 						 String ITEM_NM ="";
					 						
					 						 for(Goods bookInfo: bookList){
					 							 ITEM_NM = bookInfo.get_BOOK_NM();
					 							 ISBN13 = bookInfo.get_ISBN13();
					 						 }
					 						 
					 						 java.util.HashMap<String, Object> maping = new HashMap<String, Object>(); 
					 						 maping.put("ORDERYMD", OrderYmd);
					 						 maping.put("MALL_ID", "50289");
					 						 maping.put("SELLER_ID", "booxen");
					 						maping.put("PORDNUM", ordNo);
					 						 maping.put("ORDSEQ", ordNoNm);
					 						 maping.put("QTY", qty);
					 						 maping.put("SELL_AMT",   String.valueOf((int)SELL_TOTAMT));
					 						 maping.put("SALPRI", String.valueOf((int)SELL_TOTAMT));
					 						 maping.put("ISBN13", ISBN13);
					 						 maping.put("ITEM_NM", ITEM_NM);
					 						 maping.put("DELIPRI", deliPri);
					 						 maping.put("TOZICD", toZiCd);
					 						 maping.put("TOADDR", toAddr);
					 						 maping.put("MEMO", memo);
					 						 maping.put("TONM", toNm);
					 						 maping.put("TOTEL", toTel);
					 						 maping.put("TOEMTEL", "");
					 						 maping.put("ORDNUM_NM", ordNoNm);	
					 						 
					 						maping.put("PCODE", jsonObjOrder_Item.get("prdCd").toString());
					 						maping.put("OPTCD", "1");
					 						maping.put("OPTNM", jsonObjOrder_Item.get("optValueNm").toString());
					 						
					 						 
					 						 try
					 						 {
					 							orderDAO.InsertBooxenDetailList(maping);							 							
					 							orderDAO.UptBooxenOrdList(maping);
					 							Set_OrderConfirm(ordNo,ordNoNm);
					 							
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
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	public void Set_OrderConfirm(String ordNo, String ordNoNm) {
		try {
			String selAcntCd="SA000586";
			String pwd="cb27df9e-9920-488b-8eef-1c41d33f6f7f";		

			JSONObject jsonObject = new JSONObject();			
			jsonObject.put("ordNo", ordNo);
			jsonObject.put("ordNoNm", ordNoNm);
			jsonObject.put("selAcntNo", "107976");
			
			JSONArray NoticeValueArray = new JSONArray();
			NoticeValueArray.add(jsonObject);
			
			String sRtn_PrdStdInfo_Result = getHttp_POST_Json(NoticeValueArray.toString(), selAcntCd, pwd,
					"http://api.seller-club.co.kr/rest/order/setOrderConfirm");
			// 상품 기준정보 저장

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	public void RegGoodsAPI(Goods bookInfo) {
		
		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 80) return ;

			
			//	테스트
			
			String selAcntCd="SA000586";
			String pwd="cb27df9e-9920-488b-8eef-1c41d33f6f7f";
			
			//상품 기준정보 저장
			String sRtn_PrdStdInfo = GetXml_postPrdStdInfo(bookInfo);
			
			String sRtn_PrdStdInfo_Result = getHttp_POST_Json(sRtn_PrdStdInfo, selAcntCd, pwd, "http://api.seller-club.co.kr//rest/product/productRegister/postPrdStdInfo");
			
			
			double PRICE_SALE = 0;

			if (Double.parseDouble(bookInfo.get_INRT()) <= 70)
	        {
				 PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
					BigDecimal incm_civil_amt = new BigDecimal (PRICE_SALE);   
					incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
					PRICE_SALE = incm_civil_amt.doubleValue();
	        }
	        else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76)
	        {
	            
	            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (PRICE_SALE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				PRICE_SALE = incm_civil_amt.doubleValue();
				

	        }
	        else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81)
	        {
	            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE());
	        }
	        else
	        {
	            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE());
	        }
			
			JSONParser jsonParser = new JSONParser();
		    JSONObject jsonObj = (JSONObject)jsonParser.parse(sRtn_PrdStdInfo_Result);							 				 				 				 			   		     	
		    String data_resultStatus = jsonObj.get("resultStatus").toString();
		     	
		   JSONObject json_data_resultStatus = (JSONObject)jsonParser.parse(data_resultStatus);
		   
		   String prdNo ="";
		   if (json_data_resultStatus.get("message").toString().equals("success"))
		   {
			   String str_result = jsonObj.get("data").toString();			   
			   JSONObject Obj_result = (JSONObject)jsonParser.parse(str_result);
			   
			   String str_resultData = Obj_result.get("result").toString();			   
			   JSONObject Obj_resultData = (JSONObject)jsonParser.parse(str_resultData);
			   
			   String str_prdMstInfo = Obj_resultData.get("prdMstInfo").toString();					   
			   JSONObject Obj_prdMstInfo = (JSONObject)jsonParser.parse(str_prdMstInfo);
			   
			   prdNo =Obj_prdMstInfo.get("prdNo").toString();
			   
			   if (!prdNo.equals(""))
			   {
				   //상품 정책 상세 정보 저장
				   String sRtn_PrdPlcyDtlInfo ="";
				   sRtn_PrdPlcyDtlInfo = GetXml_PrdPlcyDtlInfo(bookInfo ,prdNo);
				   
					String sRtn_Plcy = getHttp_POST_Json(sRtn_PrdPlcyDtlInfo, selAcntCd, pwd, "http://api.seller-club.co.kr//rest/product/productRegister/postPrdPlcyDtlInfo");
					
					 jsonObj = (JSONObject)jsonParser.parse(sRtn_Plcy);	
					 data_resultStatus = jsonObj.get("resultStatus").toString();
					 json_data_resultStatus = (JSONObject)jsonParser.parse(data_resultStatus);
					 if (json_data_resultStatus.get("message").toString().equals("success"))
					 {
						 //상품 컨텐츠 상세 정보 저장
						 String DtlInfo ="";
						 String sRtn_PrdCntsDtlInfo = GetXml_PrdCntsDtlInfo(bookInfo ,prdNo);	
						 System.out.println(sRtn_PrdCntsDtlInfo);
						 String sRtn_DtlInfo = getHttpHTML_POST(sRtn_PrdCntsDtlInfo, selAcntCd, pwd,
									"http://api.seller-club.co.kr//rest/product/productRegister/postPrdCntsDtlInfo");	
							 jsonObj = (JSONObject)jsonParser.parse(sRtn_DtlInfo);	
							 data_resultStatus = jsonObj.get("resultStatus").toString();
							json_data_resultStatus = (JSONObject)jsonParser.parse(data_resultStatus);
							if (json_data_resultStatus.get("message").toString().equals("success"))
							{
								 bookInfo.set_BORIBORI_ITEMCD(prdNo);							
								 bookInfo.set_PRICE_SALE(String.valueOf(PRICE_SALE));
								 GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());			
								 
								 int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());
						            
								 if (Pcnt < 1)
								 {
									 java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
									 map.put("ITEMCD", bookInfo.get_ITEMCD());
									 map.put("BORIBORI_ITEMCD", bookInfo.get_BORIBORI_ITEMCD());
									 map.put("ISBN13", bookInfo.get_ISBN13());
									 map.put("BOOK_NM", bookInfo.get_BOOK_NM());
									 map.put("BOOKSTS", bookInfo.get_BOOKSTS());
									 map.put("PRICE_SALE", bookInfo.get_PRICE_SALE());
									 map.put("PRICE", bookInfo.get_PRICE());
			
									 goodsDAO.InsertRegItem(map);
								 }
							}
					 }
					
			   }
		   }else {
			   System.out.println(json_data_resultStatus.get("message").toString()); 
		   }
		   
		     	
		

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	
	public String GetXml_PrdCntsDtlInfo(Goods bookInfo,String prdNo) {
		
		String sRtn ="";
		
		 JSONObject jsonObject = new JSONObject();        
	         
	     
	        JSONObject prdMstInfo = new JSONObject();
	        
	        prdMstInfo.put("prdNo", prdNo);        
	        jsonObject.put("prdMstInfo", prdMstInfo);
	        
	      
	        JSONObject prdBaseInfo = new JSONObject();	        	        
	        jsonObject.put("prdBaseInfo", prdBaseInfo);
	        
	        
	        JSONObject prdNotiInfo = new JSONObject();
	        JSONArray Arr_notiItemList = new JSONArray();               
		    
	        JSONObject notiItemList01 = new JSONObject();
	        notiItemList01.put("notiItemGrpCd", "NO012");
	        notiItemList01.put("notiItemCd", "001");
	        notiItemList01.put("notiItemValue",bookInfo.get_BOOK_NM());
	        
	        JSONObject notiItemList02 = new JSONObject();
	        notiItemList02.put("notiItemGrpCd", "NO012");
	        notiItemList02.put("notiItemCd", "002");
	        notiItemList02.put("notiItemValue",bookInfo.get_AUTHR()+"," + bookInfo.get_PUBLISHER());
	        
	        JSONObject notiItemList03 = new JSONObject();
	        notiItemList03.put("notiItemGrpCd", "NO012");
	        notiItemList03.put("notiItemCd", "003");
	        notiItemList03.put("notiItemValue",bookInfo.get_BOOK_MG_VAL());
	        
	        JSONObject notiItemList04 = new JSONObject();
	        notiItemList04.put("notiItemGrpCd", "NO012");
	        notiItemList04.put("notiItemCd", "004");
	        notiItemList04.put("notiItemValue",bookInfo.get_BOOK_PAGE_VAL());
	        
	        JSONObject notiItemList05 = new JSONObject();
	        notiItemList05.put("notiItemGrpCd", "NO012");
	        notiItemList05.put("notiItemCd", "005");
	        notiItemList05.put("notiItemValue","상세페이지 참조");
	        
	        JSONObject notiItemList06 = new JSONObject();
	        notiItemList06.put("notiItemGrpCd", "NO012");
	        notiItemList06.put("notiItemCd", "006");
	        notiItemList06.put("notiItemValue",bookInfo.get_OPENDATE());
	        
	        JSONObject notiItemList07 = new JSONObject();
	        notiItemList07.put("notiItemGrpCd", "NO012");
	        notiItemList07.put("notiItemCd", "007");
	        notiItemList07.put("notiItemValue","상세페이지 참조");
	        
	        Arr_notiItemList.add(notiItemList01);
	        Arr_notiItemList.add(notiItemList02);
	        Arr_notiItemList.add(notiItemList03);
	        Arr_notiItemList.add(notiItemList04);
	        Arr_notiItemList.add(notiItemList05);
	        Arr_notiItemList.add(notiItemList06);
	        Arr_notiItemList.add(notiItemList07);
	        
	        prdNotiInfo.put("notiItemList",Arr_notiItemList);	        
	        jsonObject.put("prdNotiInfo", prdNotiInfo);
		    
	        JSONObject prdCertInfo = new JSONObject();
	        JSONArray Array_prdCertInfo = new JSONArray();     
	        prdCertInfo.put("certiTypCd", "01");
	        prdCertInfo.put("certiItemList", Array_prdCertInfo);
	        prdCertInfo.put("adltCertYn", "N"); 
	        jsonObject.put("prdCertiInfo", prdCertInfo);
			 
	        JSONObject prdDescInfo = new JSONObject();
	        prdDescInfo.put("prdDescTypCd", "02");
	        prdDescInfo.put("prdDescContClob", GetGoodsBody_New(bookInfo));
	        prdDescInfo.put("prdTag", "");
	        prdDescInfo.put("orgnTypCd", "01");
	        prdDescInfo.put("orgnCd", "KR");
	        jsonObject.put("prdDescInfo", prdDescInfo);
	        
	        JSONObject prdImgInfo = new JSONObject();
	        prdImgInfo.put("basicImgUrl", bookInfo.get_IMG_PATH2().toString());
	        jsonObject.put("prdImgInfo",prdImgInfo);
	        
	        JSONObject prdAttrInfo = new JSONObject();
	        JSONObject attrItemList = new JSONObject();
	        JSONObject Col_attrItemList1_01 = new JSONObject();
	        JSONObject Col_attrItemList1_02 = new JSONObject();
	        JSONObject Col_attrItemList1_03 = new JSONObject();
	        
	        JSONObject Col_attrItemList2_01 = new JSONObject();
	        JSONObject Col_attrItemList2_02 = new JSONObject();
	        JSONObject Col_attrItemList2_03 = new JSONObject();
	        JSONObject Col_attrItemList2_04 = new JSONObject();
	        JSONObject Col_attrItemList2_05 = new JSONObject();
	        
	        JSONObject Col_attrItemList1_Big = new JSONObject();
	        JSONObject Col_attrItemList2_Big = new JSONObject();
	        
	        JSONArray Array_comAttrList_01 = new JSONArray();     
	        JSONArray Array_comAttrList_02 = new JSONArray();
	        
	        JSONObject Col_attrItemList_Big = new JSONObject();
	        
	        
	        Col_attrItemList1_01.put("attrGrpCd", "AC001");
	        Col_attrItemList1_01.put("attrCd", "01");
	        Col_attrItemList1_01.put("chkYn", "N");
	        
	        Col_attrItemList1_02.put("attrGrpCd", "AC001");
	        Col_attrItemList1_02.put("attrCd", "02");
	        Col_attrItemList1_02.put("chkYn", "N");
	        
	        Col_attrItemList1_03.put("attrGrpCd", "AC001");
	        Col_attrItemList1_03.put("attrCd", "03");
	        Col_attrItemList1_03.put("chkYn", "Y");
	        
	        
	        Col_attrItemList2_01.put("attrGrpCd", "AC002");
	        Col_attrItemList2_01.put("attrCd", "01");
	        Col_attrItemList2_01.put("chkYn", "N");
	        
	        Col_attrItemList2_02.put("attrGrpCd", "AC002");
	        Col_attrItemList2_02.put("attrCd", "02");
	        Col_attrItemList2_02.put("chkYn", "N");
	        
	        Col_attrItemList2_03.put("attrGrpCd", "AC002");
	        Col_attrItemList2_03.put("attrCd", "03");
	        Col_attrItemList2_03.put("chkYn", "N");
	        
	        Col_attrItemList2_04.put("attrGrpCd", "AC002");
	        Col_attrItemList2_04.put("attrCd", "04");
	        Col_attrItemList2_04.put("chkYn", "N");
	       
	        Col_attrItemList2_05.put("attrGrpCd", "AC002");
	        Col_attrItemList2_05.put("attrCd", "05");
	        Col_attrItemList2_05.put("chkYn", "Y");
	        
	        
	        
	        Array_comAttrList_01.add(Col_attrItemList1_01);
	        Array_comAttrList_01.add(Col_attrItemList1_02);
	        Array_comAttrList_01.add(Col_attrItemList1_03);
	        
	        Col_attrItemList1_Big.put("attrItemList", Array_comAttrList_01);
	        
	        
	        Array_comAttrList_02.add(Col_attrItemList2_01);
	        Array_comAttrList_02.add(Col_attrItemList2_02);
	        Array_comAttrList_02.add(Col_attrItemList2_03);
	        Array_comAttrList_02.add(Col_attrItemList2_04);
	        Array_comAttrList_02.add(Col_attrItemList2_05);
	        
	        Col_attrItemList2_Big.put("attrItemList", Array_comAttrList_02);
	        
	        JSONArray Array_comAttrList_Big = new JSONArray();  
	        Array_comAttrList_Big.add(Col_attrItemList1_Big);
	        Array_comAttrList_Big.add(Col_attrItemList2_Big);
	        
	        prdAttrInfo.put("comAttrList", Array_comAttrList_Big);	        
	        jsonObject.put("prdAttrInfo", prdAttrInfo);	        
	        jsonObject.put("prevYn", "Y");
	        
  
		return jsonObject.toJSONString();
	}
	public String GetXml_PrdPlcyDtlInfo(Goods bookInfo,String prdNo)
	{
		double PRICE_SALE = 0;

		if (Double.parseDouble(bookInfo.get_INRT()) <= 70)
        {
			 PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (PRICE_SALE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				PRICE_SALE = incm_civil_amt.doubleValue();
        }
        else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76)
        {
            
            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
			BigDecimal incm_civil_amt = new BigDecimal (PRICE_SALE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			PRICE_SALE = incm_civil_amt.doubleValue();
			

        }
        else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81)
        {
            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE());
        }
        else
        {
            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE());
        }
		
		//최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();        
        JSONArray basicArray = new JSONArray();               
         
        Gson gson = new Gson();
      

        JSONObject prdMstInfo = new JSONObject();
        
        //prdMstInfo.put("productNo", prdNo);
        prdMstInfo.put("prdNo", prdNo);        
        jsonObject.put("prdMstInfo", prdMstInfo);
        
        JSONObject prdStdInfo = new JSONObject();
        prdStdInfo.put("bkshwSelYn", "Y");
        prdStdInfo.put("isbnNo", bookInfo.get_ISBN13());
        jsonObject.put("prdStdInfo", prdStdInfo);
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        
        JSONObject prdBaseInfo = new JSONObject();
        prdBaseInfo.put("prdNm", bookInfo.get_BOOK_NM());
        prdBaseInfo.put("dispChanTypCd","01" );
        /*
        prdBaseInfo.put("decoWord", bookInfo.get_BOOK_NM());
        prdBaseInfo.put("decoWordStrDt",df.format(cal.getTime()) );
        prdBaseInfo.put("decoWordStrHr", "00");
        prdBaseInfo.put("decoWordStrMi","00" );
        prdBaseInfo.put("decoWordEndDt", "2999/12/31" );
        prdBaseInfo.put("decoWordEndHr", "23");
        prdBaseInfo.put("decoWordEndMi", "59");
        */
        jsonObject.put("prdBaseInfo", prdBaseInfo);
        
        
        JSONObject prdDispInfo = new JSONObject();
        JSONArray prdDispCtgrList = new JSONArray();   
        
        JSONObject dispCtgrNo01 = new JSONObject();
        dispCtgrNo01.put("dispCtgrNo", "");
        dispCtgrNo01.put("mode", "");
        
        JSONObject dispCtgrNo02 = new JSONObject();
        dispCtgrNo02.put("dispCtgrNo", "");
        dispCtgrNo02.put("mode", "");
        
        JSONObject dispCtgrNo03 = new JSONObject();
        dispCtgrNo03.put("dispCtgrNo", "");
        dispCtgrNo03.put("mode", "");
        
        prdDispCtgrList.add(dispCtgrNo01);
        prdDispCtgrList.add(dispCtgrNo02);
        prdDispCtgrList.add(dispCtgrNo03);
        
        prdDispInfo.put("prdDispCtgrList",prdDispCtgrList);
        jsonObject.put("prdDispInfo", prdDispInfo);
        
        JSONObject prdSelInfo = new JSONObject(); 
        prdSelInfo.put("normPrc", bookInfo.get_PRICE());  //정상가
        prdSelInfo.put("selPrc", PRICE_SALE);  //판매가
        prdSelInfo.put("puchPrc", PRICE_SALE);  //사입원가
        prdSelInfo.put("selYr", bookInfo.get_OPENDATE().toString().substring(0,4));  //판매년도
        prdSelInfo.put("maxPchsQty", "999");  //최대구매수량
        
        if (bookInfo.get_ISBN13().substring(0,1).equals("9"))
        {
        	prdSelInfo.put("vtaxTypCd", "02");  //부가세 (검색 후 작성)  (01:과세상품 / 02:면세상품 / 03:영세상품)
        }else {
        	prdSelInfo.put("vtaxTypCd", "01");
        }
        
        prdSelInfo.put("dispYn", "Y");  //전시여부
        prdSelInfo.put("noDispResnCd", ""); //미전시사유
        prdSelInfo.put("prdWght", "");   //상품중량
        prdSelInfo.put("wghtUnit", "");   //단위
        prdSelInfo.put("refundYn", "Y");    //환불가능여부
        prdSelInfo.put("outStockDispYn", "N");  //품절시 노출 여부
        
        jsonObject.put("prdSelInfo", prdSelInfo);
        
        JSONObject prdDlvInfo = new JSONObject();
        JSONObject dlvRtrnAmtInfo = new JSONObject();
        prdDlvInfo.put("buyAgntYn", "N");  //구매대행 여부
        dlvRtrnAmtInfo.put("dlvTmpltSeq", "121315");
        prdDlvInfo.put("dlvRtrnAmtInfo", dlvRtrnAmtInfo);
        
        jsonObject.put("prdDlvInfo", prdDlvInfo);
        
        JSONObject prdOptInfo = new JSONObject();
        prdOptInfo.put("optItemNm1", "One color | One Size");
        
        JSONObject optStckList = new JSONObject();
        optStckList.put("optValueNm1", "One color | One Size");
        optStckList.put("addPrc", "0");
        optStckList.put("realStckQty", "999");
        optStckList.put("tempStckQty", "999");
        optStckList.put("useYn", "Y");
        optStckList.put("noUseResnCd", "");
        
        JSONArray Array_optStckList = new JSONArray();   
        Array_optStckList.add(optStckList);
        prdOptInfo.put("optStckList", Array_optStckList);
        
        jsonObject.put("prdOptInfo", prdOptInfo);
        
        JSONArray Array_stckNoDelList = new JSONArray();      
        jsonObject.put("stckNoDelList", Array_stckNoDelList);
        
        
		return jsonObject.toJSONString();
	}


	//상품가격수정
	public String GetXml_PrdPlcyDtlInfo_Upt(Goods bookInfo)
	{
		double PRICE_SALE = 0;

		if (Double.parseDouble(bookInfo.get_INRT()) <= 70)
        {
			 PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (PRICE_SALE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				PRICE_SALE = incm_civil_amt.doubleValue();
        }
        else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76)
        {
            
            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
			BigDecimal incm_civil_amt = new BigDecimal (PRICE_SALE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			PRICE_SALE = incm_civil_amt.doubleValue();
			

        }
        else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81)
        {
            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE());
        }
        else
        {
            PRICE_SALE = Double.parseDouble(bookInfo.get_PRICE());
        }
		
		bookInfo.set_PRICE_SALE(String.valueOf(Integer.parseInt(String.valueOf(Math.round(PRICE_SALE)))));
		
		//최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();        
        JSONArray basicArray = new JSONArray();               
         
        Gson gson = new Gson();
      

        JSONObject prdMstInfo = new JSONObject();
        
        //prdMstInfo.put("productNo", prdNo);
        prdMstInfo.put("prdNo", bookInfo.get_BORIBORI_ITEMCD());        
        jsonObject.put("prdMstInfo", prdMstInfo);
        
        JSONObject prdStdInfo = new JSONObject();
        
        if (bookInfo.get_BOOKSTS().equals("현매")||bookInfo.get_BOOKSTS().equals("정상") )
        {
        	prdStdInfo.put("prdSelCd", "02");
        }else {
        	prdStdInfo.put("prdSelCd", "04");
        }
        	
        
        prdStdInfo.put("prdCd",bookInfo.get_BORIBORI_ITEMCD());
    //	prdStdInfo.put("prdSelCd", "04");
        prdStdInfo.put("bkshwSelYn", "Y");
        prdStdInfo.put("isbnNo", bookInfo.get_ISBN13());
        jsonObject.put("prdStdInfo", prdStdInfo);
        
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        
        JSONObject prdBaseInfo = new JSONObject();
        prdBaseInfo.put("prdNm", bookInfo.get_BOOK_NM());
        prdBaseInfo.put("dispChanTypCd","01" );
        /*
        prdBaseInfo.put("decoWord", bookInfo.get_BOOK_NM());
        prdBaseInfo.put("decoWordStrDt",df.format(cal.getTime()) );
        prdBaseInfo.put("decoWordStrHr", "00");
        prdBaseInfo.put("decoWordStrMi","00" );
        prdBaseInfo.put("decoWordEndDt", "2999/12/31" );
        prdBaseInfo.put("decoWordEndHr", "23");
        prdBaseInfo.put("decoWordEndMi", "59");
        */
        jsonObject.put("prdBaseInfo", prdBaseInfo);
        
        
        JSONObject prdDispInfo = new JSONObject();
        JSONArray prdDispCtgrList = new JSONArray();   
        
        JSONObject dispCtgrNo01 = new JSONObject();
        dispCtgrNo01.put("dispCtgrNo", "");
        dispCtgrNo01.put("mode", "");
        
        JSONObject dispCtgrNo02 = new JSONObject();
        dispCtgrNo02.put("dispCtgrNo", "");
        dispCtgrNo02.put("mode", "");
        
        JSONObject dispCtgrNo03 = new JSONObject();
        dispCtgrNo03.put("dispCtgrNo", "");
        dispCtgrNo03.put("mode", "");
        
        prdDispCtgrList.add(dispCtgrNo01);
        prdDispCtgrList.add(dispCtgrNo02);
        prdDispCtgrList.add(dispCtgrNo03);
        
        prdDispInfo.put("prdDispCtgrList",prdDispCtgrList);
        jsonObject.put("prdDispInfo", prdDispInfo);
      
        
        JSONObject prdSelInfo = new JSONObject(); 
        prdSelInfo.put("normPrc", bookInfo.get_PRICE());  //정상가
        prdSelInfo.put("selPrc", Integer.parseInt(String.valueOf(Math.round(PRICE_SALE))));  //판매가
        prdSelInfo.put("puchPrc", Integer.parseInt(String.valueOf(Math.round(PRICE_SALE))));;  //사입원가
        prdSelInfo.put("selYr", bookInfo.get_OPENDATE().toString().substring(0,4));  //판매년도
        prdSelInfo.put("maxPchsQty", "999");  //최대구매수량
        
        if (bookInfo.get_ISBN13().substring(0,1).equals("9"))
        {
        	prdSelInfo.put("vtaxTypCd", "02");  //부가세 (검색 후 작성)  (01:과세상품 / 02:면세상품 / 03:영세상품)
        }else {
        	prdSelInfo.put("vtaxTypCd", "01");
        }
        
        prdSelInfo.put("dispYn", "Y");  //전시여부
        prdSelInfo.put("noDispResnCd", ""); //미전시사유
        prdSelInfo.put("prdWght", "");   //상품중량
        prdSelInfo.put("wghtUnit", "");   //단위
        prdSelInfo.put("refundYn", "Y");    //환불가능여부
        prdSelInfo.put("outStockDispYn", "N");  //품절시 노출 여부
        
        jsonObject.put("prdSelInfo", prdSelInfo);
        
        JSONObject prdDlvInfo = new JSONObject();
        JSONObject dlvRtrnAmtInfo = new JSONObject();
        prdDlvInfo.put("buyAgntYn", "N");  //구매대행 여부
        dlvRtrnAmtInfo.put("dlvTmpltSeq", "121315");
        prdDlvInfo.put("dlvRtrnAmtInfo", dlvRtrnAmtInfo);
        
        jsonObject.put("prdDlvInfo", prdDlvInfo);
        
       
        JSONObject prdOptInfo = new JSONObject();
        prdOptInfo.put("optItemNm1", "One color | One Size");
        
        JSONObject optStckList = new JSONObject();
        optStckList.put("optValueNm1", "One color | One Size");
        optStckList.put("addPrc", "0");
        optStckList.put("realStckQty", "999");
        optStckList.put("tempStckQty", "999");
        optStckList.put("useYn", "Y");
        optStckList.put("noUseResnCd", "");
        
        JSONArray Array_optStckList = new JSONArray();   
        Array_optStckList.add(optStckList);
        prdOptInfo.put("optStckList", Array_optStckList);
        
        jsonObject.put("prdOptInfo", prdOptInfo);
        
        JSONArray Array_stckNoDelList = new JSONArray();      
        jsonObject.put("stckNoDelList", Array_stckNoDelList);
       
        
		return jsonObject.toJSONString();
	}

	public String GetXml_postPrdStdInfo(Goods bookInfo)
	{
		//최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();        
        JSONArray basicArray = new JSONArray();               
         
        Gson gson = new Gson();
      
        
        //siteCd : 1 하프        
        //siteCd : 2 보리보리
        jsonObject.put("siteCd", "1");
        jsonObject.put("prdCd", bookInfo.get_ISBN13());
        jsonObject.put("prdGroupNo", "1117548");
        jsonObject.put("mdNo", "112619");
        jsonObject.put("stdCtgrNo3", "113007008");
        
      
		return jsonObject.toJSONString();
	}
	
	 public String ex1(Date date, String pattern) {
			String result = null;
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
			result = format.format(date);
			return result;
		}
		public String getHttp_PUT_Json(String json, String selAcntCd, String pwd, String surl) {
			String strResult ="";
			 String method = "PUT";
		      
		       
		        try {
		            //create client
		            
		            //build uri
		            URIBuilder uriBuilder = new URIBuilder().setPath(surl);

		            /********************************************************/
		          
		            /********************************************************/

		            URL  url = new URL(surl);
		            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		            httpCon.setDoOutput(true);
		            httpCon.setRequestMethod("PUT");
		            httpCon.setRequestProperty("selAcntCd", selAcntCd);
		            httpCon.setRequestProperty("pwd", pwd);		            
		            httpCon.setRequestProperty("content-type", "application/json");
		            httpCon.setRequestProperty("Accept", "application/json");
		            
		            httpCon.setDoInput(true);
		            httpCon.setDoOutput(true);
		            OutputStreamWriter out = new  OutputStreamWriter(httpCon.getOutputStream());
	                out.write(json);
	                out.flush();
	                out.close();
		            
	                httpCon.connect();
	                BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
	                String temp = null;
	                StringBuilder sb = new StringBuilder();
	                while((temp = in.readLine()) != null){
	                    sb.append(temp).append(" ");
	                }
	                strResult = sb.toString();
	                in.close();
	                
		        } catch (IOException  e) {
		            e.printStackTrace();
		        } finally {
		          
		        }
	        return strResult;
	    }
		
		
		public String requestPutJson(String json, String selAcntCd, String pwd, String surl) throws Exception {
		      
		       
	        URL url = new URL(surl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("selAcntCd", selAcntCd);
			conn.setRequestProperty("pwd", pwd);		          
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("PUT");

	        OutputStream os = conn.getOutputStream();
	        os.write(json.getBytes("UTF-8"));
	        os.close();

	        // read the response
	        Charset charset = Charset.defaultCharset();
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String resp = "";
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, charset))) {
	           resp = br.lines().collect(Collectors.joining(System.lineSeparator()));
	        }

	       // System.out.println(resp);

	        in.close();
	        conn.disconnect();
			
			return resp;
	    }
		
	 public String getOrderHttpHTML_Get(String selAcntCd, String pwd, String surl)  {
			String sReturn = "";
			try {
				  CloseableHttpClient httpClient = HttpClients.createDefault();
			       
				  Date date = Calendar.getInstance().getTime();
				  Date yesterday = new Date();
					yesterday.setTime(date.getTime() - ((long) 1000 * 60 * 60 * 480  ));
					
			        //get 메서드와 URL 설정
				  URIBuilder uriBuilder = new URIBuilder()
		                    .setPath(surl)
		                    .addParameter("fromYMD", ex1(yesterday, "yyyyMMdd"))
		                    .addParameter("toYMD", ex1(date, "yyyyMMdd"))
		                    .addParameter("deliState", "c");
				  
			        HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
			        
			        //agent 정보 설정			        
			        httpGet.addHeader("Content-type", "application/json");
			        httpGet.addHeader("selAcntCd", selAcntCd);
			        httpGet.addHeader("pwd", pwd);
			        //get 요청
			        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			        
			        System.out.println("GET Response Status");
			        System.out.println(httpResponse.getStatusLine().getStatusCode());
			        String json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			        
			         
			        httpClient.close();
			        return json;
				
			} catch (Exception e) {
				// TODO: handle exception
			}

			return sReturn;

		}
	public String getHttp_POST_Json(String json, String selAcntCd, String pwd, String surl) {
        //params
        String method = "POST";
       
        String strResult ="";
        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
           
            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

            StringEntity params =new StringEntity(json,"UTF-8");
            // set header, demonstarte how to use hmac signature here
            requestPost.addHeader("selAcntCd", selAcntCd);
            requestPost.addHeader("pwd", pwd);
          
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
                 strResult = EntityUtils.toString(entity);
	               
              //  System.out.println("result:" + strResult);
                
                
              
               
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
        
        return strResult;
    }
	public String getHttpHTML_POST(String json, String selAcntCd, String pwd, String surl) {
		String sReturn = "";
		/*
		try {

			URL url = new URL(surl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "multipart/form-data");
			con.setRequestProperty("selAcntCd", selAcntCd);
			con.setRequestProperty("pwd", pwd);

			json = "metaData=" +json;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(new String(json.getBytes("UTF-8"), "8859_1"));
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			System.out.println(responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);

			}

			in.close();
			sReturn = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(sReturn);
		*/
		
		try {
			RequestConfig.Builder requestBuilder = RequestConfig.custom();
			HttpClientBuilder builder = HttpClientBuilder.create();
			builder.setDefaultRequestConfig(requestBuilder.build());
			HttpClient client = builder.build();

			HttpPost httpPost = new HttpPost(surl);
			String BOUNDARY = "----------fa94669c2a0ffcbe";
			httpPost.setHeader("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
			httpPost.setHeader("selAcntCd", selAcntCd);
			httpPost.setHeader("pwd", pwd);

			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			entityBuilder.addPart("metaData", new StringBody(json, ContentType.APPLICATION_JSON));
			entityBuilder.setBoundary(BOUNDARY);

			//entityBuilder.addPart("fileMainImg", data);

			httpPost.setEntity(entityBuilder.build());
			HttpResponse response = client.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {

				HttpEntity entity = response.getEntity();

				sReturn = EntityUtils.toString(entity, "UTF-8");

				//System.out.println("result : " + sReturn);

			} else {

				System.out.println(response.toString());

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sReturn;

	}

	 public String SendPostMultipart(String json, String selAcntCd, String pwd, String surl , String fileMainImg) throws IOException,ClientProtocolException
	 {
		 String result="";
		 URL url = new URL(fileMainImg);

		 String fileName = fileMainImg.substring( fileMainImg.lastIndexOf('/')+1, fileMainImg.length() ); // 이미지 파일명 추출

		 String ext = fileMainImg.substring( fileMainImg.lastIndexOf('.')+1, fileMainImg.length() );  // 이미지 확장자 추출

		 InputStream inStream = null;
	     OutputStream out = null;
	     
	     try {
	    	 inStream = url.openStream();
	    	 out = new FileOutputStream("D:/Back/"+fileName); //저장경로
	    	 
	            while(true){
	                //이미지를 읽어온다.
	                int data = inStream.read();
	                if(data == -1){
	                    break;
	                }
	                //이미지를 쓴다.
	                out.write(data);
	 
	            }
	 
	            inStream.close();
	            out.close();
	     }catch (Exception e) {
			// TODO: handle exception
		}finally{
 
            if(inStream != null){inStream.close();}
            if(out != null){out.close();}
 
        }
	     		 
	     try {	  
	    	 RequestConfig.Builder requestBuilder = RequestConfig.custom();
	    	 HttpClientBuilder builder = HttpClientBuilder.create();
	    	 builder.setDefaultRequestConfig(requestBuilder.build()); 
	    	 HttpClient client = builder.build();


	    	 
	    	 File fileToUse = new File("D:/Back/"+fileName);		 
			 FileBody data = new FileBody(fileToUse);
			 
			  
			 HttpPost httpPost = new HttpPost(surl);
			 String BOUNDARY = "----------fa94669c2a0ffcbe";
			 httpPost.setHeader("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
			 httpPost.setHeader("selAcntCd",selAcntCd);
			 httpPost.setHeader("pwd",pwd);
			 
			 MultipartEntityBuilder entityBuilder  = MultipartEntityBuilder.create();
			 entityBuilder.addPart("metaData", new StringBody(json, ContentType.APPLICATION_JSON));
			 entityBuilder.setBoundary(BOUNDARY);
			 
			 entityBuilder.addPart("fileMainImg", data);
			 
	   		 httpPost.setEntity(entityBuilder.build());
	   		 HttpResponse response = client.execute(httpPost);
	   		 
	   		  
	   		if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201) {

				HttpEntity entity = response.getEntity();

				result = EntityUtils.toString(entity, "UTF-8");

				System.out.println("result : " + result);

			} else {

				System.out.println(response.toString());

			}

	   		 
	     }catch (ClientProtocolException e) 
	     { 
	    	 e.printStackTrace(); 
	    	 } 
	     catch (IOException e) 
	     { e.printStackTrace(); 
	     }


         return result;

	 }
	 
	 
	public String GetXml(Goods bookInfo, double PRICE_SALE) {
		//최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();        
        JSONArray basicArray = new JSONArray();               
         
        Gson gson = new Gson();
      
        jsonObject.put("productNo", "0");
        
        JSONObject basicInfo = new JSONObject();
        basicInfo.put("productName", bookInfo.get_BOOK_NM());
        basicInfo.put("productType", "N");
        basicInfo.put("dcateCode", GetCategory(bookInfo));
        
        //테스트
        //basicInfo.put("shipPolicyNo", 3094);
        
        //실서버
        basicInfo.put("shipPolicyNo", 153263);
        
        basicInfo.put("adultLimitYn", "N");
        basicInfo.put("displayYn", "Y");
        basicInfo.put("bizYn", "N");
        basicInfo.put("brandNo", "0");
        basicInfo.put("makerNo", "0");
        
        
        
        jsonObject.put("basic", basicInfo);
        
        
        
        JSONObject SaleInfo = new JSONObject();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        
        SaleInfo.put("saleStartDate", df.format(cal.getTime())+ " 01:00");
        
        cal.add(Calendar.MONTH, 47);
        SaleInfo.put("saleEndDate", df.format(cal.getTime())+ " 01:00");
        
        SaleInfo.put("originPrice", null);
        SaleInfo.put("PRICE_SALE", PRICE_SALE);
        SaleInfo.put("stockCount", 99999);
        SaleInfo.put("taxYn", "N");
        SaleInfo.put("purchaseMinCount", 1);
        SaleInfo.put("purchaseLimitYn", "N");
        
        SaleInfo.put("purchaseLimitDuration", null);
        SaleInfo.put("purchaseLimitDay", 0);
        
        SaleInfo.put("purchaseLimitCount", 0);
        
        SaleInfo.put("referencePriceType", "WMP");
     
       
       
        jsonObject.put("sale", SaleInfo);
        
      
       
        JSONArray descImgUrlList = new JSONArray();
        JSONArray optionValuelList = new JSONArray();
        
        JSONArray addImgUrlList = new JSONArray();
        
        
        		
        JSONObject DetailInfo = new JSONObject();
        DetailInfo.put("basicImgUrl", bookInfo.get_IMG_PATH2());
        DetailInfo.put("addImgUrlList", addImgUrlList);
        DetailInfo.put("listImgUrl", bookInfo.get_IMG_PATH2());
        DetailInfo.put("descType", "HTML");
        DetailInfo.put("descImgUrlList", descImgUrlList);
        DetailInfo.put("descHtml", GetGoodsBody_New(bookInfo));
      
        
       
        jsonObject.put("detail", DetailInfo);
        
     
        
        JSONObject OptionInfo = new JSONObject();
        OptionInfo.put("selectOptionUseYn", "N");                       
        OptionInfo.put("textOptionUseYn", "N");        
        jsonObject.put("option", OptionInfo);
        
        
        JSONObject NoticeInfo = new JSONObject();        
        NoticeInfo.put("groupNoticeNo", 26);
   
        JSONObject NoticeValue1 = new JSONObject();
        NoticeValue1.put("noticeNo", "313");
        NoticeValue1.put("noticeName","도서명");
        NoticeValue1.put("description", bookInfo.get_BOOK_NM());
        
        JSONObject NoticeValue2 = new JSONObject();
        NoticeValue2.put("noticeNo", "314");
        NoticeValue2.put("noticeName","저자/출판사");
        NoticeValue2.put("description", bookInfo.get_AUTHR()+ "/" + bookInfo.get_PUBLISHER());
        
        JSONObject NoticeValue3 = new JSONObject();
        NoticeValue3.put("noticeNo", "315");
        NoticeValue3.put("noticeName","크기 (전자책의 경우 파일의 용량)");
        NoticeValue3.put("description", bookInfo.get_BOOK_WT_VAL());
        
        JSONObject NoticeValue4 = new JSONObject();
        NoticeValue4.put("noticeNo", "316");
        NoticeValue4.put("noticeName","쪽수 (전자책의 경우 제외)");
        NoticeValue4.put("description", bookInfo.get_BOOK_PAGE_VAL());
        
        JSONObject NoticeValue5 = new JSONObject();
        NoticeValue5.put("noticeNo", "317");
        NoticeValue5.put("noticeName","제품 구성 (전집 또는 세트일 경우 낱권 구성, CD 등)");
        NoticeValue5.put("description", "상품상세 설명 참고");
        
        JSONObject NoticeValue6 = new JSONObject();
        NoticeValue6.put("noticeNo", "318");
        NoticeValue6.put("noticeName","출간일");
        NoticeValue6.put("description", "상품상세 설명 참고");
        
        JSONObject NoticeValue7 = new JSONObject();
        NoticeValue7.put("noticeNo", "319");
        NoticeValue7.put("noticeName","목차 또는 책소개 (아동용 학습교재의 경우 사용연령을 포함)");
        NoticeValue7.put("description", "상품상세 설명 참고");
        
        
        JSONArray NoticeValueArray = new JSONArray();
        NoticeValueArray.add(NoticeValue1);
        NoticeValueArray.add(NoticeValue2);
        NoticeValueArray.add(NoticeValue3);
        NoticeValueArray.add(NoticeValue4);
        NoticeValueArray.add(NoticeValue5);
        NoticeValueArray.add(NoticeValue6);
        NoticeValueArray.add(NoticeValue7);
        
        NoticeInfo.put("noticeList", NoticeValueArray);        
    
        
        JSONArray Notice_array = new JSONArray();
        Notice_array.add(NoticeInfo);        
        
        
        jsonObject.put("noticeList", Notice_array);
        
        
        
        
        
        JSONArray kcKidCertificationList = new JSONArray();
        JSONArray kcLifeCertificationList = new JSONArray();        
        JSONArray kcElectricCertificationList = new JSONArray();
        JSONArray kcReportCertificationList = new JSONArray();
        JSONArray kcLifeChemistryCertificationList = new JSONArray();
        JSONArray labelNoList = new JSONArray();
        
        JSONObject etcInfo = new JSONObject();
        etcInfo.put("parallelImportYn", "N");
        etcInfo.put("parallelImportUrl", null);
        
        GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        int Pcnt = goodsDAO.GetExisKidsList(bookInfo.get_BX_CATECD());
        
        if (Pcnt > 0)
        {
        	etcInfo.put("kcKidIsCertification", "Y");
        	JSONObject kcKidValue = new JSONObject();
            kcKidValue.put("certificationType", "KD_SPL");
            kcKidValue.put("certificationNo", null);
            JSONArray kcKidArray = new JSONArray();
            kcKidArray.add(kcKidValue);        
            etcInfo.put("kcKidCertificationList", kcKidArray);
        }else
        {
        	etcInfo.put("kcKidIsCertification", "N");	
        	etcInfo.put("kcKidCertificationList", kcKidCertificationList);
        }
                
        
        etcInfo.put("kcLifeIsCertification", "N");       
        etcInfo.put("kcLifeCertificationList", kcLifeCertificationList);
        
        etcInfo.put("kcElectricIsCertification", "N");       
        etcInfo.put("kcElectricCertificationList", kcElectricCertificationList);
        
        etcInfo.put("kcReportIsCertification", "N");
        etcInfo.put("kcReportCertificationList", kcReportCertificationList);   
        
        etcInfo.put("kcLifeChemistryIsCertification", "N");
        etcInfo.put("kcLifeChemistryCertificationList", kcLifeChemistryCertificationList); 
        
        etcInfo.put("sellerProdCode", bookInfo.get_ISBN13());        
        etcInfo.put("priceComparisonSiteYn", "Y");
        etcInfo.put("keywordPriceComparisonSite", "");
        etcInfo.put("keywordWemakeprice", "");
        etcInfo.put("isbn13", bookInfo.get_ISBN13());
        etcInfo.put("isbn10", "");
        etcInfo.put("labelNoList", labelNoList);
        
        jsonObject.put("etc", etcInfo);
        
        
    
        

		return jsonObject.toJSONString();
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

			StrCateGory = "4105258";
		}

		return StrCateGory;
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
	
	public void InsertError(String ERROR_GUBUN, String ISBN13, String ITEMCD, String MSG) {
		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ERROR_GUBUN", ERROR_GUBUN);
		map.put("ISBN13", ISBN13);
		map.put("ITEMCD", ITEMCD);
		map.put("MSG", MSG);

		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		goodsDAO.InsertError(map);
	}
}