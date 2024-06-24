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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
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


import java.util.Date;


public class Idotbi_SSG {	
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			try {
				GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				
				// 상품등록
				try {
					
					System.out.println("=====송장입력 start=====");
					
					//송장입력
			    	try {
			    		 work wk = new work();
			    		wk.GetTransNoList("50944"); //
			    		
			    	}catch (Exception e) {
						// TODO: handle exception
					}
			    	
			    	System.out.println("=====송장입력 end=====");
			    	 
					System.out.println("=====주문 start=====");
					
				    try {
				    	 work wk = new work();
				    	 wk.Get_OrderInfo();
				    	
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
					
					 
					System.out.println("=====상품상태수정  start=====");
					try
					{
						List<Goods> bookStatusList = goodsDAO.GetStatusGoodsList();
						
						for (Goods bookInfo : bookStatusList) {
							System.out.println("상품상태수정 ISBN13: " + bookInfo.get_ISBN13());					
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
					
					System.out.println("=====상품상태수정  end=====");
					
					System.out.println("=====상품가격수정  Start=====");
					
				
					try {

						List<Goods> bookUptSaleList = goodsDAO.GetUptSaleGoodsList();
						for (Goods bookInfo : bookUptSaleList) {
							System.out.println("상품가격수정 ISBN13: " + bookInfo.get_ISBN13());					
							int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

							if (Pcnt > 0) {
								work wk = new work();
								
								System.out.println("=====상품가격수정  start=====" + bookInfo.get_ISBN13());
								wk.UptGoodsSaleAPI(bookInfo);
								
							}
						}
					} catch (Exception e) {
					}
					System.out.println("=====상품가격수정 End=====");
					
					//Thread.sleep(100000000);
					System.exit(0);
					
				} catch (Exception e) {
					e.printStackTrace();
					//ㅎㅐㅂㅗㅏ ㄸㅗㄱㄱㅏㅌㅇㅣ
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
				String Key="4b6f4482-ed14-4f5e-b8ed-6f9bd3a59149";
				
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
					String shppNo = (String)map.get("ORD_TMP3");
					String shppSeq = (String)map.get("ORD_TMP4");
					BigDecimal bd = (BigDecimal)map.get("SELL_QTY");

					int SELL_QTY = bd.intValue();

					
					if (!shppNo.equals(""))
					{
						try
						{
							/*
							JSONObject ObjectBundle = new JSONObject();						        
							ObjectBundle.put("shppNo", shppNo);               							       
							ObjectBundle.put("shppSeq", shppSeq);
							ObjectBundle.put("wblNo", TRNS_NO);
							ObjectBundle.put("delicoVenId", "0000033071");
							ObjectBundle.put("shppTypeCd", "20");
							ObjectBundle.put("shppTypeDtlCd", "22");
							*/
							
							
							DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
							DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
							
							Document doc = docBuilder.newDocument();
							Element rootElement = doc.createElement("requestWhOutCompleteProcess");
							doc.appendChild(rootElement);
							
							Element shppNo_E = doc.createElement("shppNo");
							shppNo_E.appendChild(doc.createTextNode(shppNo));
							rootElement.appendChild(shppNo_E);
							
							
							Element shppSeq_E = doc.createElement("shppSeq");
							shppSeq_E.appendChild(doc.createTextNode(shppSeq));
							rootElement.appendChild(shppSeq_E);
							
							Element wblNo_E = doc.createElement("wblNo");
							wblNo_E.appendChild(doc.createTextNode(TRNS_NO));
							rootElement.appendChild(wblNo_E);
							
							Element delicoVenId_E = doc.createElement("delicoVenId");
							delicoVenId_E.appendChild(doc.createTextNode("0000033071"));
							rootElement.appendChild(delicoVenId_E);
							
							
							Element shppTypeCd_E = doc.createElement("shppTypeCd");
							shppTypeCd_E.appendChild(doc.createTextNode("20"));
							rootElement.appendChild(shppTypeCd_E);
							
							Element shppTypeDtlCd_E = doc.createElement("shppTypeDtlCd");
							shppTypeDtlCd_E.appendChild(doc.createTextNode("22"));
							rootElement.appendChild(shppTypeDtlCd_E);
							
					
							
							

							String resultCode = getHttpHTML_POST_Xml(DocumentToString(doc),Key,"http://eapi.ssgadm.com/api/pd/1/saveWblNo.ssg");
							
							 if (resultCode.equals("00"))
							 {
								 String RtnMsg="";
			 	 				 java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
			 	 				 map1.put("ORD_ID", ORD_ID);
			 	 				 map1.put("MALL_ID", MALL_ID);		 					
			 					
			 					orderDAO.UptTrans(map1);
			 					
			 					/*
			 					<requestWhOutCompleteProcess>
			 				    <shppNo>D2105535509</shppNo>
			 				    <shppSeq>1</shppSeq>
			 				    <procItemQty>1</procItemQty>
			 				</requestWhOutCompleteProcess>
			 				*/
			 					
			 					
								 doc = docBuilder.newDocument();
								 rootElement = doc.createElement("requestWhOutCompleteProcess");
								 doc.appendChild(rootElement);
								
								shppNo_E = doc.createElement("shppNo");
								shppNo_E.appendChild(doc.createTextNode(shppNo));
								rootElement.appendChild(shppNo_E);
								
								
								shppSeq_E = doc.createElement("shppSeq");
								shppSeq_E.appendChild(doc.createTextNode(shppSeq));
								rootElement.appendChild(shppSeq_E);
								
								Element shppSeq_Q = doc.createElement("procItemQty");
								shppSeq_Q.appendChild(doc.createTextNode(String.valueOf(SELL_QTY) ));
								rootElement.appendChild(shppSeq_Q);
								
								resultCode = getHttpHTML_POST_Xml(DocumentToString(doc),Key,"http://eapi.ssgadm.com/api/pd/1/saveWhOutCompleteProcess.ssg");
								
								
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
		
		

		
		public String Get_ProductList(String PRODUCTNO) {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>();

			
			Result_map = goodsDAO.GetISBNList(PRODUCTNO);
			String ISBN13 = "";
			
			if (Result_map !=null)
			{
				Iterator iter = Result_map.entrySet().iterator();
				
				while (iter.hasNext()) {

					Entry entry = (Entry) iter.next();

					ISBN13 = entry.getValue().toString();

				}

			}
			
			return ISBN13;
		}
		
		
		public void Get_OrderInfo() {
			//테스트
			//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
			//운영
			String Key="4b6f4482-ed14-4f5e-b8ed-6f9bd3a59149";
			Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        
	        JSONObject OrderInfo = new JSONObject();              
	        cal.add(Calendar.DAY_OF_MONTH, -6);        
	        OrderInfo.put("perdStrDts", df.format(cal.getTime()));
	        
	        cal.add(Calendar.DAY_OF_MONTH, 6);
	        OrderInfo.put("perdEndDts", df.format(cal.getTime()));
	        OrderInfo.put("perdType", "02");
	        //OrderInfo.put("mallTypeCd", "10");
	        
	        
	        JSONObject response = new JSONObject();
	        response.put("requestShppDirection", OrderInfo);

	        
	        
	        //테스트
	        //String sVal = getHttpHTML_POST_Order(OrderInfo.toJSONString(),Key,"https://wapi-stg.wemakeprice.com/order/out/getOrderList");
	        //운영
	        String sVal = getHttpHTML_POST_Order(response.toJSONString(),Key,"http://eapi.ssgadm.com/api/pd/1/listShppDirection.ssg");
	      
	        if (!sVal.equals("")) {
	        	try
	        	{
	        		JSONParser jsonParser = new JSONParser();
	   		     	JSONObject jsonObj = (JSONObject)jsonParser.parse(sVal);
	   		     	
	   		     	String sResult = jsonObj.get("result").toString();
	   		     	
	   		     	
	   		       
	   		        JSONObject Obj_sResult = (JSONObject)jsonParser.parse(sResult);   	
	   		        
	   		        
	   		        
	   		        JSONArray Arr_shpp = (JSONArray)Obj_sResult.get("shppDirections");
		   		     for(int i=0;i<Arr_shpp.size();i++)
		   		     {
		   		    	JSONObject Obj_Arr_shpp = (JSONObject)Arr_shpp.get(i);
		   		    	
		   		    	JSONArray Arr_shpp1 = null;
		   		    	try {
		   		    		 Arr_shpp1 = (JSONArray)Obj_Arr_shpp.get("shppDirection");	
		   		    	}catch (Exception e) {
		   		    			
						}
		   		    	
		   		    	if (Arr_shpp1 == null)
		   		    	{
		   		    		JSONObject jsonObjOrder =(JSONObject)Obj_Arr_shpp.get("shppDirection");
			   		    	
			   		 		String orderDate = jsonObjOrder.get("ordRcpDts").toString().substring(0,10).replace("-", "");
							String buyerName = jsonObjOrder.get("ordpeNm").toString();
							String name   = jsonObjOrder.get("ordpeNm").toString();
							String phone = jsonObjOrder.get("rcptpeHpno").toString();
							String zipcode=jsonObjOrder.get("shpplocZipcd").toString();
							String addrFixed=jsonObjOrder.get("shpplocBascAddr").toString();
							String addrDetail=jsonObjOrder.get("shpplocDtlAddr").toString();
							String message=jsonObjOrder.get("ordMemoCntt") == null ? "" : jsonObjOrder.get("ordMemoCntt").toString();
							String orderNo=jsonObjOrder.get("ordNo").toString();
							String productNo=jsonObjOrder.get("itemId").toString();
							String productName=jsonObjOrder.get("itemNm").toString();
							String productPrice=jsonObjOrder.get("sellprc").toString();
							String productQty=jsonObjOrder.get("ordQty").toString();					
							String shipPrice = jsonObjOrder.get("shppcst").toString();
							/*String ordItemSeq =jsonObjOrder.get("ordItemSeq").toString();*/
							String ISBN13 =Get_ProductList(productNo);
							String mallTypeCd = jsonObjOrder.get("mallTypeCd").toString();
							String shppNo = jsonObjOrder.get("shppNo").toString();
							String shppSeq = jsonObjOrder.get("shppSeq").toString();
							
							if (mallTypeCd.equals("20"))
							{
								if (!ISBN13.equals("") && (ISBN13.substring(0, 1).equals("8") || ISBN13.substring(0, 1).equals("9"))) {
									java.util.HashMap<String, Object> map_Seq = new HashMap<String, Object>();
								    map_Seq.put("ORD_ID", orderNo);
										
									OrderDAO orderDAO = new OrderDAO(
									MyBatisConnectionFactory.getSqlSessionFactory());

										
									int ordItemSeq = orderDAO.GetOrderSeq(map_Seq);
									
									
									Order Order = new Order();				   		 			
									Order.Set_ORD_DATE(orderDate.toString());
									Order.Set_ORD_ID(orderNo);
									Order.Set_ISBN13(ISBN13);
									Order.Set_ORD_NO(String.valueOf(ordItemSeq));
									Order.Set_SELL_QTY(productQty);
									Order.Set_SELL_AMT(productPrice);
									Order.Set_DLV_PRICE(shipPrice);
									Order.Set_DLV_POST(zipcode);
									Order.Set_DLV_ADDR1(addrFixed);
									Order.Set_DLV_ADDR2(addrDetail);
									Order.Set_DLV_MSG(message);
									Order.Set_DLV_NM(name);
									Order.Set_DLV_TEL(phone);
									Order.Set_DLV_HP(phone);
									Order.Set_ITEM_NM(productName);
									Order.Set_SELL_AMT(productPrice);
									Order.Set_SELL_TOTAMT(productPrice);
				   		 			
					   		 		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
									map.put("ORD_ID", Order.Get_ORD_ID());
									map.put("ISBN13", Order.Get_ISBN13());
									map.put("ORD_NO", Order.Get_ORD_NO());
				
									/*OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());*/
				
									int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
									
									if (API_MstCnt < 1) {
										
										String strBooxen = getHttpHTML_Booxen("http://api-b.booxen.com/API/BookSaleInfoOnline", Order.Get_ISBN13());
										
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
											maping.put("SELLER_ID", "Idotbi_ssg");
											maping.put("MALL_ID", "50944");
											maping.put("ORD_ID", Order.Get_ORD_ID());
											maping.put("ORD_NO", Order.Get_ORD_NO());
											maping.put("SELL_QTY", Order.Get_SELL_QTY());
											maping.put("SELL_AMT", String.valueOf((int)SELL_TOTAMT));
											maping.put("SELL_TOTAMT", String.valueOf((int)SELL_TOTAMT));
											maping.put("ISBN13", Order.Get_ISBN13());
											maping.put("ITEM_NM", ITEM_NM);
											maping.put("DLV_PRICE", Order.Get_DLV_PRICE());
											maping.put("DLV_POST", Order.Get_DLV_POST());
											maping.put("DLV_ADDR1", Order.Get_DLV_ADDR1());
											maping.put("DLV_ADDR2", Order.Get_DLV_ADDR2());
											maping.put("DLV_MSG", Order.Get_DLV_MSG());
											maping.put("DLV_NM", StringReplace(Order.Get_DLV_NM()));
											maping.put("DLV_TEL", Order.Get_DLV_TEL());
											maping.put("DLV_HP", Order.Get_DLV_HP());
											maping.put("ORD_TMP3", shppNo);
											maping.put("ORD_TMP4", shppSeq);
										
										
										//maping.put("SHOPLINKER_ORDER_ID", bundleNo);
										try {
										
											if (orderDAO.InsertBooxenOrdList(maping) > 0) {
												
												
												try
												{
													System.out.println("=====setOrderConfirm=====" + Order.Get_ORD_NO());
													
													
													DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
													DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
													
													Document doc = docBuilder.newDocument();
													Element rootElement = doc.createElement("requestOrderSubjectManage");
													doc.appendChild(rootElement);
													
													Element shppNo_E = doc.createElement("shppNo");
													shppNo_E.appendChild(doc.createTextNode(shppNo));
													rootElement.appendChild(shppNo_E);
													
													
													Element shppSeq_E = doc.createElement("shppSeq");
													shppSeq_E.appendChild(doc.createTextNode(shppSeq));
													rootElement.appendChild(shppSeq_E);
													/*
													JSONObject ObjectBundle = new JSONObject();						        
													ObjectBundle.put("shppNo", shppNo);               							       
													ObjectBundle.put("shppSeq", shppSeq);
													//ObjectBundle.put("shppInstlExpcDt", df.format(cal.getTime()));
													
													JSONObject ObjConfirm = new JSONObject();
													ObjConfirm.put("requestOrderSubjectManage",ObjectBundle.toJSONString());
													*/
													String sCode = getHttpHTML_POST_Xml(DocumentToString(doc),Key,"http://eapi.ssgadm.com/api/pd/1/updateOrderSubjectManage.ssg");
													
													System.out.println("=====setOrderConfirm End=====" + Order.Get_ORD_NO());
													
													
												}catch (Exception e) {
													// TODO: handle exception
												}
											}
											
										} catch (Exception ex) {
											
											System.out.println(ex.getMessage());
											
											InsertError("RegOrder", Order.Get_ORD_ID(), "", ex.getMessage());
										}
											
									}
								}
				   		 	 }
						}
							
		   		    	}else {
		   		    		for(int ix=0;ix<Arr_shpp1.size();ix++)
				   		     {
				   		    	JSONObject jsonObjOrder = (JSONObject)Arr_shpp1.get(ix);
				   		    	
				   		 		String orderDate = jsonObjOrder.get("ordRcpDts").toString().substring(0,10).replace("-", "");
								String buyerName = jsonObjOrder.get("ordpeNm").toString();
								String name   = jsonObjOrder.get("ordpeNm").toString();
								String phone = jsonObjOrder.get("rcptpeHpno").toString();
								String zipcode=jsonObjOrder.get("shpplocZipcd").toString();
								String addrFixed=jsonObjOrder.get("shpplocBascAddr").toString();
								String addrDetail=jsonObjOrder.get("shpplocDtlAddr").toString();
								String message=jsonObjOrder.get("ordMemoCntt") == null ? "" : jsonObjOrder.get("ordMemoCntt").toString();
								String orderNo=jsonObjOrder.get("ordNo").toString();
								String productNo=jsonObjOrder.get("itemId").toString();
								String productName=jsonObjOrder.get("itemNm").toString();
								String productPrice=jsonObjOrder.get("sellprc").toString();
								String productQty=jsonObjOrder.get("ordQty").toString();					
								String shipPrice = jsonObjOrder.get("shppcst").toString();
								//String ordItemSeq =jsonObjOrder.get("ordItemSeq").toString();
								String ISBN13 =Get_ProductList(productNo);
								String mallTypeCd = jsonObjOrder.get("mallTypeCd").toString();
								String shppNo = jsonObjOrder.get("shppNo").toString();
								String shppSeq = jsonObjOrder.get("shppSeq").toString();
								
								if (mallTypeCd.equals("20"))
								{
									if (!ISBN13.equals("") && (ISBN13.substring(0, 1).equals("8") || ISBN13.substring(0, 1).equals("9"))) {
					   		 			
										java.util.HashMap<String, Object> map_Seq = new HashMap<String, Object>();
									    map_Seq.put("ORD_ID", orderNo);
											
										OrderDAO orderDAO1 = new OrderDAO(
										MyBatisConnectionFactory.getSqlSessionFactory());

											
										int ordItemSeq = orderDAO1.GetOrderSeq(map_Seq);
										
										Order Order = new Order();
					   		 			Order.Set_ORD_DATE(orderDate.toString());
										Order.Set_ORD_ID(orderNo);
										Order.Set_ISBN13(ISBN13);
										Order.Set_ORD_NO(String.valueOf(ordItemSeq));
										Order.Set_SELL_QTY(productQty);
										Order.Set_SELL_AMT(productPrice);
										Order.Set_DLV_PRICE(shipPrice);
										Order.Set_DLV_POST(zipcode);
										Order.Set_DLV_ADDR1(addrFixed);
										Order.Set_DLV_ADDR2(addrDetail);
										Order.Set_DLV_MSG(message);
										Order.Set_DLV_NM(name);
										Order.Set_DLV_TEL(phone);
										Order.Set_DLV_HP(phone);
										Order.Set_ITEM_NM(productName);
										Order.Set_SELL_AMT(productPrice);
										Order.Set_SELL_TOTAMT(productPrice);
					   		 			
						   		 		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("ORD_ID", Order.Get_ORD_ID());
										map.put("ISBN13", Order.Get_ISBN13());
										map.put("ORD_NO", Order.Get_ORD_NO());
					
										OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					
										int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
										
										if (API_MstCnt < 1) {
											
											
											String strBooxen = getHttpHTML_Booxen("http://api-b.booxen.com/API/BookSaleInfoOnline", Order.Get_ISBN13());
											
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
												maping.put("SELLER_ID", "Idotbi_ssg");
												maping.put("MALL_ID", "50944");
												maping.put("ORD_ID", Order.Get_ORD_ID());
												maping.put("ORD_NO", Order.Get_ORD_NO());
												maping.put("SELL_QTY", Order.Get_SELL_QTY());
												maping.put("SELL_AMT", String.valueOf((int)SELL_TOTAMT));
												maping.put("SELL_TOTAMT", String.valueOf((int)SELL_TOTAMT));
												maping.put("ISBN13", Order.Get_ISBN13());
												maping.put("ITEM_NM", ITEM_NM);
												maping.put("DLV_PRICE", Order.Get_DLV_PRICE());
												maping.put("DLV_POST", Order.Get_DLV_POST());
												maping.put("DLV_ADDR1", Order.Get_DLV_ADDR1());
												maping.put("DLV_ADDR2", Order.Get_DLV_ADDR2());
												maping.put("DLV_MSG", Order.Get_DLV_MSG());
												maping.put("DLV_NM", StringReplace(Order.Get_DLV_NM()));
												maping.put("DLV_TEL", Order.Get_DLV_TEL());
												maping.put("DLV_HP", Order.Get_DLV_HP());
												maping.put("ORD_TMP3", shppNo);
												maping.put("ORD_TMP4", shppSeq);
												
											
											//maping.put("SHOPLINKER_ORDER_ID", bundleNo);
											try {
											
												if (orderDAO.InsertBooxenOrdList(maping) > 0) {
													
													
													try
													{
														System.out.println("=====setOrderConfirm=====" + Order.Get_ORD_NO());
														
														
														DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
														DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
														
														Document doc = docBuilder.newDocument();
														Element rootElement = doc.createElement("requestOrderSubjectManage");
														doc.appendChild(rootElement);
														
														Element shppNo_E = doc.createElement("shppNo");
														shppNo_E.appendChild(doc.createTextNode(shppNo));
														rootElement.appendChild(shppNo_E);
														
														
														Element shppSeq_E = doc.createElement("shppSeq");
														shppSeq_E.appendChild(doc.createTextNode(shppSeq));
														rootElement.appendChild(shppSeq_E);
														/*
														JSONObject ObjectBundle = new JSONObject();						        
														ObjectBundle.put("shppNo", shppNo);               							       
														ObjectBundle.put("shppSeq", shppSeq);
														//ObjectBundle.put("shppInstlExpcDt", df.format(cal.getTime()));
														
														JSONObject ObjConfirm = new JSONObject();
														ObjConfirm.put("requestOrderSubjectManage",ObjectBundle.toJSONString());
														*/
														String sCode = getHttpHTML_POST_Xml(DocumentToString(doc),Key,"http://eapi.ssgadm.com/api/pd/1/updateOrderSubjectManage.ssg");
														
														System.out.println("=====setOrderConfirm End=====" + Order.Get_ORD_NO());
														
													}catch (Exception e) {
														// TODO: handle exception
													}
												}
												
											} catch (Exception ex) {
												
												System.out.println(ex.getMessage());
												
												InsertError("RegOrder", Order.Get_ORD_ID(), "", ex.getMessage());
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
		
		public String getHttpHTML_Booxen(String urlStr,  String ISBN13) {
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
		
		public void UptGoodsSaleAPI(Goods bookInfo) {
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

				String Key="4b6f4482-ed14-4f5e-b8ed-6f9bd3a59149";
				String sRtn = GetXml_Option(bookInfo, bookInfo.get_PRODUCTNO());
				String resultCode = getHttpHTML_POST_Notification(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/option/0.1/updateOption.ssg", bookInfo);
			
				if (resultCode.equals("00"))
				{
					java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();						
					map_1.put("ITEMCD", bookInfo.get_ITEMCD());
					map_1.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
					map_1.put("ISBN13", bookInfo.get_ISBN13());
					map_1.put("BOOK_NM", StringReplace(bookInfo.get_BOOK_NM()));
					map_1.put("BOOKSTS", bookInfo.get_BOOKSTS());
					map_1.put("SALEPRICE", bookInfo.get_SALEPRICE());
					map_1.put("PRICE", bookInfo.get_PRICE());
					
					GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
						
						goodsDAO.UptRegItem(map_1);
				}

			} catch (Exception e) {
				InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
			}
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

				//운영
				String Key="4b6f4482-ed14-4f5e-b8ed-6f9bd3a59149";
			
				String sRtn = GetXml_inventory(bookInfo, bookInfo.get_PRODUCTNO());
				String resultCode = getHttpHTML_POST_Notification(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/inventory/0.1/updateInventory.ssg", bookInfo);
				
				
				if (resultCode.equals("00")) {
					
					bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
					java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();						
					map_1.put("ITEMCD", bookInfo.get_ITEMCD());
					map_1.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
					map_1.put("ISBN13", bookInfo.get_ISBN13());
					map_1.put("BOOK_NM", StringReplace(bookInfo.get_BOOK_NM()));
					map_1.put("BOOKSTS", bookInfo.get_BOOKSTS());
					map_1.put("SALEPRICE", bookInfo.get_SALEPRICE());
					map_1.put("PRICE", bookInfo.get_PRICE());
					
					GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
						
						goodsDAO.UptRegItem(map_1);
				}
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		
		}
		
		public void UptGoodsBagicAPI(Goods bookInfo) {
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

				//String sRtn = GetXml_Upt(bookInfo, SALEPRICE);
				String sRtn = GetXml_Bagic_Upt(bookInfo, SALEPRICE);
				
				sRtn = sRtn.replace("&", "");
				// String sRtn = Upt_GetXml();
				//테스트
				//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
				
				//운영
				String Key = "a3d24312879f973596e6b9e6da0070f19c2c640042ae6b572c4ff1b7344057d789de3363b25cbfed1096306c496793bd4803316b532225049e3451c88dcba35e";
				
				if (!"".equals(sRtn)) {
					String sVal = "";
					// Set_GoodsRegister
					// Set_GoodsState
					bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
					sVal = getHttpHTML_POST_Upt(sRtn, Key, "https://w-api.wemakeprice.com/product/in/setProductBasic",bookInfo);				

				}

			} catch (Exception e) {
				InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
			}
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

				String sRtn = GetXml_BasicInfo(bookInfo, SALEPRICE);
				
						
				//운영
				String Key="4b6f4482-ed14-4f5e-b8ed-6f9bd3a59149";
				if (!"".equals(sRtn)) {
					String itemId = "";
					String resultCode = "";
					// Set_GoodsState

					bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
					
					//1 단계. *상품기본정보 등록 API
					itemId = getHttpHTML_POST_Reg(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/itemBasic/0.1/updateItemBasic.ssg", bookInfo);
					
					if (!itemId.equals(""))
					{
						//배송정책 등록
						sRtn = GetXml_Delivery(bookInfo, itemId);
						resultCode = getHttpHTML_POST_Notification(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/delivery/0.1/updateDelivery.ssg", bookInfo);					
					}

					if (resultCode.equals("00"))
					{
						//상세설명 등록
						sRtn = GetXml_Description(bookInfo, itemId);
						resultCode = getHttpHTML_POST_Notification(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/description/0.1/updateDescription.ssg", bookInfo);					
					}else {
						InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), resultCode);
					}
					if (resultCode.equals("00"))
					{
						//상품고시
						sRtn = GetXml_Notification(bookInfo, itemId);
						resultCode = getHttpHTML_POST_Notification(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/notification/0.1/updateNotification.ssg", bookInfo);					
					}else {
						InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), resultCode);
					}
					
					if (resultCode.equals("00"))
					{
						//옵션가
						sRtn = GetXml_Option(bookInfo, itemId);
						resultCode = getHttpHTML_POST_Notification(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/option/0.1/updateOption.ssg", bookInfo);
					
					}else {
						InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), resultCode);
					}
					if (resultCode.equals("00"))
					{
						//재고
						sRtn = GetXml_inventory(bookInfo, itemId);
						resultCode = getHttpHTML_POST_Notification(sRtn, Key, "http://eapi.ssgadm.com/item/om/itemedit/inventory/0.1/updateInventory.ssg", bookInfo);
						
						if (resultCode.equals("00")) 
						{
							GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
							
							java.util.HashMap<String, Object> map = new HashMap<String, Object>();						
							map.put("ITEMCD", bookInfo.get_ITEMCD());
							map.put("PRODUCTNO", itemId);
							map.put("ISBN13", bookInfo.get_ISBN13());
							map.put("BOOK_NM", StringReplace(bookInfo.get_BOOK_NM()));
							map.put("BOOKSTS", bookInfo.get_BOOKSTS());
							map.put("SALEPRICE", bookInfo.get_SALEPRICE());
							map.put("PRICE", bookInfo.get_PRICE());

							goodsDAO.InsertRegItem(map);
						}else {
							InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), resultCode);
						}
					}
			
			}

			} catch (Exception e) {
				InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
			}
		}
		
		public String GetXml_BasicInfo(Goods bookInfo, double SALEPRICE) {
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// 루트 엘리먼트
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("om_updateItemBase");
				doc.appendChild(rootElement);
				
				Element itemBasic = doc.createElement("itemBasic");
				rootElement.appendChild(itemBasic);
				
				Element itemNm = doc.createElement("itemNm");
				itemNm.appendChild(doc.createTextNode(StringReplace(bookInfo.get_BOOK_NM())));
				itemBasic.appendChild(itemNm);
				

				String sCate = GetCategory(bookInfo);
				if (bookInfo.get_ISBN13().substring(0,1).equals("8"))
				{
					sCate = "1000029415";
				}
				
				Element stdCtgId = doc.createElement("stdCtgId");
				stdCtgId.appendChild(doc.createTextNode(sCate));
				itemBasic.appendChild(stdCtgId);
				
				Element itemChrctDivCd = doc.createElement("itemChrctDivCd");
				itemChrctDivCd.appendChild(doc.createTextNode("TX"));
				itemBasic.appendChild(itemChrctDivCd);
				
				Element itemChrctDtlCd = doc.createElement("itemChrctDtlCd");
				itemChrctDtlCd.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(itemChrctDtlCd);
				
				Element exusItemDivCd = doc.createElement("exusItemDivCd");
				exusItemDivCd.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(exusItemDivCd);
				
				Element exusItemDtlCd = doc.createElement("exusItemDtlCd");
				exusItemDtlCd.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(exusItemDtlCd);
				
				Element adultItemTypeCd = doc.createElement("adultItemTypeCd");
				adultItemTypeCd.appendChild(doc.createTextNode("90"));
				itemBasic.appendChild(adultItemTypeCd);
				
				Element itemStatTypeCd = doc.createElement("itemStatTypeCd");
				itemStatTypeCd.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(itemStatTypeCd);
				
				Element itemSellWayCd = doc.createElement("itemSellWayCd");
				itemSellWayCd.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(itemSellWayCd);
				
				Element palimpItemYn = doc.createElement("palimpItemYn");
				palimpItemYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(palimpItemYn);
				
				Element giftPsblYn = doc.createElement("giftPsblYn");
				giftPsblYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(giftPsblYn);
				
				Element whinNotiYn = doc.createElement("whinNotiYn");
				whinNotiYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(whinNotiYn);
				
				Element itemSrchwdNm = doc.createElement("itemSrchwdNm");
				itemSrchwdNm.appendChild(doc.createTextNode(StringReplace(bookInfo.get_BOOK_NM())));
				itemBasic.appendChild(itemSrchwdNm);
				
				Element minOnetOrdPsblQty = doc.createElement("minOnetOrdPsblQty");
				minOnetOrdPsblQty.appendChild(doc.createTextNode("1"));
				itemBasic.appendChild(minOnetOrdPsblQty);
				
				Element maxOnetOrdPsblQty = doc.createElement("maxOnetOrdPsblQty");
				maxOnetOrdPsblQty.appendChild(doc.createTextNode("999"));
				itemBasic.appendChild(maxOnetOrdPsblQty);
				
				Element max1dyOrdPsblQty = doc.createElement("max1dyOrdPsblQty");
				max1dyOrdPsblQty.appendChild(doc.createTextNode("999"));
				itemBasic.appendChild(max1dyOrdPsblQty);
				
				
				return DocumentToString(doc);
				
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			return "";
		}

		
		public String GetXml_Delivery(Goods bookInfo, String itemId_) {
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// 루트 엘리먼트
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("om_updateDelivery");
				doc.appendChild(rootElement);
				
				Element itemBasic = doc.createElement("delivery");
				rootElement.appendChild(itemBasic);
				
				Element itemId = doc.createElement("itemId");
				itemId.appendChild(doc.createTextNode(itemId_));
				itemBasic.appendChild(itemId);
				
				
				Element shppMthdCd = doc.createElement("shppMthdCd");
				shppMthdCd.appendChild(doc.createTextNode("20"));
				itemBasic.appendChild(shppMthdCd);
				
				Element shppItemDivCd = doc.createElement("shppItemDivCd");
				shppItemDivCd.appendChild(doc.createTextNode("01"));
				itemBasic.appendChild(shppItemDivCd);
				
				Element shppRqrmDcnt = doc.createElement("shppRqrmDcnt");
				shppRqrmDcnt.appendChild(doc.createTextNode("3"));
				itemBasic.appendChild(shppRqrmDcnt);
				
				Element retExchPsblYn = doc.createElement("retExchPsblYn");
				retExchPsblYn.appendChild(doc.createTextNode("Y"));
				itemBasic.appendChild(retExchPsblYn);
				
				Element tdShppPsblYn = doc.createElement("tdShppPsblYn");
				tdShppPsblYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(tdShppPsblYn);
				
				//출고 배송비 ID 
				Element whoutShppcstId = doc.createElement("whoutShppcstId");
				whoutShppcstId.appendChild(doc.createTextNode("0000676468"));
				itemBasic.appendChild(whoutShppcstId);
				
				//반품 배송비 ID
				Element retShppcstId = doc.createElement("retShppcstId");
				retShppcstId.appendChild(doc.createTextNode("0000689638"));
				itemBasic.appendChild(retShppcstId);
				
				//출고 주소 ID
				Element whoutAddrId = doc.createElement("whoutAddrId");
				whoutAddrId.appendChild(doc.createTextNode("0007999907"));
				itemBasic.appendChild(whoutAddrId);
				
				//반품 주소 ID
				Element snbkAddrId = doc.createElement("snbkAddrId");
				snbkAddrId.appendChild(doc.createTextNode("0007999907"));
				itemBasic.appendChild(snbkAddrId);
				
				//수도권 배송여부(Y/N)
				Element mareaShppYn = doc.createElement("mareaShppYn");
				mareaShppYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(mareaShppYn);
				
				//제주도 배송불가 여부(Y/N)
				Element jejuShppDisabYn = doc.createElement("jejuShppDisabYn");
				jejuShppDisabYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(jejuShppDisabYn);
				
				//도서산간 배송불가 여부(Y/N)	
				Element ismtarShppDisabYn = doc.createElement("ismtarShppDisabYn");
				ismtarShppDisabYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(ismtarShppDisabYn);
				
				//해외 배송 가능 여부(Y/N)
				Element frgShppPsblYn = doc.createElement("frgShppPsblYn");
				frgShppPsblYn.appendChild(doc.createTextNode("N"));
				itemBasic.appendChild(frgShppPsblYn);
				
				//도서산간 추가배송비 ID	-
				Element ismtarAddShppcstId = doc.createElement("ismtarAddShppcstId");
				ismtarAddShppcstId.appendChild(doc.createTextNode("0000676470"));
				itemBasic.appendChild(ismtarAddShppcstId);
				
				//제주도 추가배송비 ID
				Element jejuAddShppcstId = doc.createElement("jejuAddShppcstId");
				jejuAddShppcstId.appendChild(doc.createTextNode("0000676469"));
				itemBasic.appendChild(jejuAddShppcstId);
				
				
				return DocumentToString(doc);
				
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			return "";
		}

		
		public String GetXml_Description(Goods bookInfo, String itemId_) {
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// 루트 엘리먼트
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("om_updateDescription");
				doc.appendChild(rootElement);
				
				Element itemBasic = doc.createElement("description");
				rootElement.appendChild(itemBasic);
				
				Element itemId = doc.createElement("itemId");
				itemId.appendChild(doc.createTextNode(itemId_));
				itemBasic.appendChild(itemId);
				
				
				Element itemDescription = doc.createElement("itemDescription");
				
				String Desc = GetGoodsBody_New(bookInfo);
				Element htmlCntt = doc.createElement("htmlCntt");
				htmlCntt.appendChild(doc.createTextNode(Desc));
				itemDescription.appendChild(htmlCntt);
				itemBasic.appendChild(itemDescription);
				
				Element itemImages = doc.createElement("itemImages");
				Element itemImage = doc.createElement("itemImage");
				
				Element dataSeq = doc.createElement("dataSeq");
				dataSeq.appendChild(doc.createTextNode("1"));
				itemImage.appendChild(dataSeq);
				
				Element dataFileNm = doc.createElement("dataFileNm");
				dataFileNm.appendChild(doc.createTextNode(bookInfo.get_IMG_PATH2()));
				itemImage.appendChild(dataFileNm);
				
				Element rplcTextNm = doc.createElement("rplcTextNm");
				rplcTextNm.appendChild(doc.createTextNode(bookInfo.get_IMG_PATH2()));
				itemImage.appendChild(rplcTextNm);
				
				Element uitemImgAssoUseYn = doc.createElement("uitemImgAssoUseYn");
				uitemImgAssoUseYn.appendChild(doc.createTextNode("N"));
				itemImage.appendChild(uitemImgAssoUseYn);
				itemImages.appendChild(itemImage);
				
				itemBasic.appendChild(itemImages);
				
				return DocumentToString(doc);
				
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			return "";
		}

		public String GetXml_Notification(Goods bookInfo, String itemId_) {
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// 루트 엘리먼트
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("om_updateNotification");
				doc.appendChild(rootElement);
				
				Element itemBasic = doc.createElement("notification");
				rootElement.appendChild(itemBasic);
				
				Element itemId = doc.createElement("itemId");
				itemId.appendChild(doc.createTextNode(itemId_));
				itemBasic.appendChild(itemId);
				
				Element itemMngPropClsId = doc.createElement("itemMngPropClsId");
				itemMngPropClsId.appendChild(doc.createTextNode("0000000028"));
				itemBasic.appendChild(itemMngPropClsId);
				
				
				
				Element notificationProps = doc.createElement("notificationProps");
			
				Element notificationProp = doc.createElement("notificationProp");
				
				Element itemMngPropId = doc.createElement("itemMngPropId");			
				itemMngPropId.appendChild(doc.createTextNode("0000000117"));
				notificationProp.appendChild(itemMngPropId);
				
				
				Element itemMngCntt = doc.createElement("itemMngCntt");			
				itemMngCntt.appendChild(doc.createTextNode(StringReplace(bookInfo.get_BOOK_NM())));
				notificationProp.appendChild(itemMngCntt);			
				notificationProps.appendChild(notificationProp);
				
				
				Element notificationProp1 = doc.createElement("notificationProp");
				
				Element itemMngPropId_1 = doc.createElement("itemMngPropId");			
				itemMngPropId_1.appendChild(doc.createTextNode("0000000118"));
				notificationProp1.appendChild(itemMngPropId_1);
				
				Element itemMngCntt_1 = doc.createElement("itemMngCntt");			
				itemMngCntt_1.appendChild(doc.createTextNode(bookInfo.get_AUTHR() + "/" + bookInfo.get_PUBLISHER()));
				notificationProp1.appendChild(itemMngCntt_1);
				notificationProps.appendChild(notificationProp1);
				
				Element notificationProp2 = doc.createElement("notificationProp");
				Element itemMngPropId_2 = doc.createElement("itemMngPropId");			
				itemMngPropId_2.appendChild(doc.createTextNode("0000000015"));
				notificationProp2.appendChild(itemMngPropId_2);
				
				Element itemMngCntt_2 = doc.createElement("itemMngCntt");			
				itemMngCntt_2.appendChild(doc.createTextNode(bookInfo.get_BOOK_WT_VAL()== null || bookInfo.get_BOOK_WT_VAL().equals(" ")|| bookInfo.get_BOOK_WT_VAL().equals("g")? "상세참조" : bookInfo.get_BOOK_WT_VAL()));
				notificationProp2.appendChild(itemMngCntt_2);
				notificationProps.appendChild(notificationProp2);
				
				Element notificationProp3 = doc.createElement("notificationProp");
				Element itemMngPropId_3 = doc.createElement("itemMngPropId");			
				itemMngPropId_3.appendChild(doc.createTextNode("0000000119"));
				notificationProp3.appendChild(itemMngPropId_3);
				
				Element itemMngCntt_3 = doc.createElement("itemMngCntt");			
				itemMngCntt_3.appendChild(doc.createTextNode(bookInfo.get_BOOK_PAGE_VAL()));
				notificationProp3.appendChild(itemMngCntt_3);
				notificationProps.appendChild(notificationProp3);
				
				Element notificationProp4 = doc.createElement("notificationProp");
				Element itemMngPropId_4 = doc.createElement("itemMngPropId");			
				itemMngPropId_4.appendChild(doc.createTextNode("0000000021"));
				notificationProp4.appendChild(itemMngPropId_4);
				
				Element itemMngCntt_4 = doc.createElement("itemMngCntt");			
				itemMngCntt_4.appendChild(doc.createTextNode("상품상세참조"));
				notificationProp4.appendChild(itemMngCntt_4);
				notificationProps.appendChild(notificationProp4);
				
				
				Element notificationProp5 = doc.createElement("notificationProp");
				Element itemMngPropId_5 = doc.createElement("itemMngPropId");			
				itemMngPropId_5.appendChild(doc.createTextNode("0000000120"));
				notificationProp5.appendChild(itemMngPropId_5);
				
				Element itemMngCntt_5 = doc.createElement("itemMngCntt");			
				itemMngCntt_5.appendChild(doc.createTextNode(bookInfo.get_OPENDATE()));
				notificationProp5.appendChild(itemMngCntt_5);
				notificationProps.appendChild(notificationProp5);
				
				
				Element notificationProp6 = doc.createElement("notificationProp");
				Element itemMngPropId_6 = doc.createElement("itemMngPropId");			
				itemMngPropId_6.appendChild(doc.createTextNode("0000000121"));
				notificationProp6.appendChild(itemMngPropId_6);
				
				Element itemMngCntt_6 = doc.createElement("itemMngCntt");			
				itemMngCntt_6.appendChild(doc.createTextNode("상품상세참조"));
				notificationProp6.appendChild(itemMngCntt_6);
				notificationProps.appendChild(notificationProp6);
				
				itemBasic.appendChild(notificationProps);
				
				
				Element manufcoNm = doc.createElement("manufcoNm");
				manufcoNm.appendChild(doc.createTextNode(bookInfo.get_PUBLISHER()));
				itemBasic.appendChild(manufcoNm);
				
				Element prodManufCntryId = doc.createElement("prodManufCntryId");
				prodManufCntryId.appendChild(doc.createTextNode("4000000302"));
				itemBasic.appendChild(prodManufCntryId);
				
				
				
				TransformerFactory tf = TransformerFactory.newInstance();

				Transformer transformer = tf.newTransformer();

				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

				StringWriter writer = new StringWriter();

				transformer.transform(new DOMSource(doc), new StreamResult(writer));

				String output = writer.getBuffer().toString();

	
				return output;
				
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			return "";
		}

		
		public String GetXml_Option(Goods bookInfo, String itemId_) {
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// 루트 엘리먼트
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("om_updateOption");
				doc.appendChild(rootElement);
				
				Element itemBasic = doc.createElement("option");
				rootElement.appendChild(itemBasic);
				
				Element itemId = doc.createElement("itemId");
				itemId.appendChild(doc.createTextNode(itemId_));
				itemBasic.appendChild(itemId);
				
				Element txnDivCd = doc.createElement("txnDivCd");
				if (bookInfo.get_ISBN13().substring(0,1).equals("8"))
				{
					txnDivCd.appendChild(doc.createTextNode("10"));
				}else {
					txnDivCd.appendChild(doc.createTextNode("20"));	
				}
				itemBasic.appendChild(txnDivCd);
				
				
				Element itemSellTypeCd = doc.createElement("itemSellTypeCd");
				itemSellTypeCd.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(itemSellTypeCd);
				/*
				Element uitemOptnTypeCd1 = doc.createElement("uitemOptnTypeCd1");
				uitemOptnTypeCd1.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(uitemOptnTypeCd1);
				
				Element uitemOptnTypeNm1 = doc.createElement("uitemOptnTypeNm1");
				uitemOptnTypeNm1.appendChild(doc.createTextNode("상품명"));
				itemBasic.appendChild(uitemOptnTypeNm1);
				*/
				
				
				Element splVenItemId = doc.createElement("splVenItemId");
				splVenItemId.appendChild(doc.createTextNode(bookInfo.get_ISBN13()));
				itemBasic.appendChild(splVenItemId);
				
				Element sellprc = doc.createElement("sellprc");
				sellprc.appendChild(doc.createTextNode(bookInfo.get_SALEPRICE()));
				itemBasic.appendChild(sellprc);
				
				return DocumentToString(doc);
				
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			return "";
		}
		
		public String GetXml_inventory(Goods bookInfo, String itemId_) {
			
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// 루트 엘리먼트
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("om_updateInventory");
				doc.appendChild(rootElement);
				
				Element itemBasic = doc.createElement("inventory");
				rootElement.appendChild(itemBasic);
				
				Element itemId = doc.createElement("itemId");
				itemId.appendChild(doc.createTextNode(itemId_));
				itemBasic.appendChild(itemId);
				
				
				Calendar cal = Calendar.getInstance();
		        cal.setTime(new Date());
		        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		        cal.add(Calendar.MINUTE, 1);
		        
				Element dispStrtDt = doc.createElement("dispStrtDt");
				dispStrtDt.appendChild(doc.createTextNode(df.format(cal.getTime())));
				itemBasic.appendChild(dispStrtDt);
				
				
				Element dispEndDt = doc.createElement("dispEndDt");
				dispEndDt.appendChild(doc.createTextNode("9999/12/31 23:59:00"));
				itemBasic.appendChild(dispEndDt);
				
				Element sellFrmCd = doc.createElement("sellFrmCd");
				sellFrmCd.appendChild(doc.createTextNode("10"));
				itemBasic.appendChild(sellFrmCd);
				
				
				Element sellStatCd = doc.createElement("sellStatCd");
				if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매")||bookInfo.get_BOOKSTS().equals("판매중")) {
					sellStatCd.appendChild(doc.createTextNode("20"));
				}else {
					sellStatCd.appendChild(doc.createTextNode("80"));	
				}
				
				itemBasic.appendChild(sellStatCd);
				
				Element usablInvQty = doc.createElement("usablInvQty");
				usablInvQty.appendChild(doc.createTextNode("9999"));
				itemBasic.appendChild(usablInvQty);
				
				
				
				return DocumentToString(doc);
				
				
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			return "";
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
		
		public String GetXml_Sale_Upt(Goods bookInfo, double SALEPRICE,String saleStartDate,String saleEndDate) {

			//최종 완성될 JSONObject 선언(전체)
	        JSONObject jsonObject = new JSONObject();        
	        JSONArray basicArray = new JSONArray();               
	         
	        Gson gson = new Gson();
	      
	        jsonObject.put("productNo", bookInfo.get_PRODUCTNO());
	        
	        JSONObject SaleInfo = new JSONObject();
	        
	        
	        
	        SaleInfo.put("saleStartDate", saleStartDate);
	        
	        SaleInfo.put("saleEndDate", saleEndDate);        
	        
	        SaleInfo.put("originPrice", null);
	        SaleInfo.put("salePrice", SALEPRICE);
	        SaleInfo.put("stockCount", 99999);
	        SaleInfo.put("taxYn", "N");
	        SaleInfo.put("purchaseMinCount", 1);
	        SaleInfo.put("purchaseLimitYn", "N");
	        
	        SaleInfo.put("purchaseLimitDuration", null);
	        SaleInfo.put("purchaseLimitDay", 0);
	        
	        SaleInfo.put("purchaseLimitCount", 0);
	        
	        SaleInfo.put("referencePriceType", "WMP");
	     
	       
	       
	        jsonObject.put("sale", SaleInfo);
	        
	        
			return jsonObject.toJSONString();
		}

		public String GetXml_Status(Goods bookInfo)
		{
			//최종 완성될 JSONObject 선언(전체)
	        JSONObject jsonObject = new JSONObject();        
	        JSONArray basicArray = new JSONArray();               
	         
	        Gson gson = new Gson();
	      
	        jsonObject.put("productNo", bookInfo.get_PRODUCTNO());
	        
	        if (bookInfo.get_BOOKSTS().equals("현매") || bookInfo.get_BOOKSTS().equals("정상"))
	        {
	        	jsonObject.put("productStatus", "A");	
	        }else {
	        	jsonObject.put("productStatus", "S");
	        }
	        
			return jsonObject.toJSONString();
		}
		
		
		public String GetXml_Bagic_Upt(Goods bookInfo, double SALEPRICE) {

			//최종 완성될 JSONObject 선언(전체)
	        JSONObject jsonObject = new JSONObject();        
	        JSONArray basicArray = new JSONArray();               
	         
	        Gson gson = new Gson();
	      
	        jsonObject.put("productNo", bookInfo.get_PRODUCTNO());
	        
	        JSONObject basicInfo = new JSONObject();
	        basicInfo.put("productName", StringReplace(bookInfo.get_BOOK_NM()));
	        basicInfo.put("productType", "N");
	        basicInfo.put("dcateCode", GetCategory(bookInfo));
	        //테스트
	        //basicInfo.put("shipPolicyNo", 3094);
	        
	        //실서버
	        basicInfo.put("shipPolicyNo", 364703);
	        
	        
	        basicInfo.put("adultLimitYn", "N");
	        
	        if (bookInfo.get_BOOKSTS().equals("현매") || bookInfo.get_BOOKSTS().equals("정상"))
	        {
	        	basicInfo.put("displayYn", "Y");	
	        }else {
	        	basicInfo.put("displayYn", "N");
	        }
	        
	        basicInfo.put("bizYn", "N");
	        basicInfo.put("brandNo", "0");
	        basicInfo.put("makerNo", "0");
	        
	        
	        
	        jsonObject.put("basic", basicInfo);
	        
	        
			return jsonObject.toJSONString();
		}

		public String GetCategory(Goods bookInfo) {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>();

			if (bookInfo.get_BX_CATECD() == null) {
				bookInfo.set_BX_CATECD("179873");
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

				StrCateGory = "1000029379";
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

		 public String ex1(Date date, String pattern) {
				String result = null;
				java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
				result = format.format(date);
				return result;
			}
		 
		 
		 public String getHttpHTML_POST_Notification(String json, String Key, String surl,Goods bookInfo) {
		        //params
		        String method = "POST";
		       
		       
		        CloseableHttpClient client = null;
		        try {
		            //create client
		            client = HttpClients.createDefault();
		            //build uri
		            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
		           
		            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

		            StringEntity params =new StringEntity(json,"UTF-8");
		            // set header, demonstarte how to use hmac signature here
		            requestPost.addHeader("Authorization", Key);
		          
		            requestPost.addHeader("content-type", "application/xml");
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
						 
						
						String result = jsonObj.get("result").toString();
						String data ="";
						JSONObject jsonObj_data = (JSONObject)jsonParser.parse( result );	
						String resultCode = jsonObj_data.get("resultCode").toString();
						String resultMessage = jsonObj_data.get("resultMessage").toString();
						String itemId = "";
						
						
						
						return resultCode;
						
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
		 

		 
		 public String getHttpHTML_POST_Reg(String json, String Key, String surl,Goods bookInfo) {
		        //params
		        String method = "POST";
		       
		       
		        CloseableHttpClient client = null;
		        try {
		            //create client
		            client = HttpClients.createDefault();
		            //build uri
		            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
		           
		            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

		            StringEntity params =new StringEntity(json,"UTF-8");
		            // set header, demonstarte how to use hmac signature here
		            requestPost.addHeader("Authorization", Key);
		          
		            requestPost.addHeader("content-type", "application/xml");
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
						 
						
						String result = jsonObj.get("result").toString();
						String data ="";
						JSONObject jsonObj_data = (JSONObject)jsonParser.parse( result );	
						String resultCode = jsonObj_data.get("resultCode").toString();
						String resultMessage = jsonObj_data.get("resultMessage").toString();
						String itemId = "";
						
						if (resultCode.equals("00"))
						{
							itemId = jsonObj_data.get("itemId").toString();
							
						}
						
						return itemId;
						
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
		 
		        
		    	public  Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException{
		            
		            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		            Document document = docBuilder.parse(new InputSource(new StringReader(str)));
		           
		            return document;
		       }
		    	
		    	
		 public String getHttpHTML_POST_Upt(String json, String Key, String surl,Goods bookInfo) {

		        //params
		        String method = "POST";
		       
		       
		        CloseableHttpClient client = null;
		        try {
		            //create client
		            client = HttpClients.createDefault();
		            //build uri
		            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
		           
		            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

		            StringEntity params =new StringEntity(json,"UTF-8");
		            // set header, demonstarte how to use hmac signature here
		            requestPost.addHeader("apiKey", Key);
		          
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
						 
						
						String code = jsonObj.get("resultCode").toString();
						String data ="";
						
						System.out.println("=====상품수정  code=====" + code);
						
						
						
						if (code.equals("200"))
						{
							data = jsonObj.get("data").toString();
							JSONObject jsonObj_data = (JSONObject)jsonParser.parse( data );	
							String productNo = jsonObj_data.get("productNo").toString();
							
							java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();						
							map_1.put("ITEMCD", bookInfo.get_ITEMCD());
							map_1.put("PRODUCTNO", productNo);
							map_1.put("ISBN13", bookInfo.get_ISBN13());
							map_1.put("BOOK_NM", StringReplace(bookInfo.get_BOOK_NM()));
							map_1.put("BOOKSTS", bookInfo.get_BOOKSTS());
							map_1.put("SALEPRICE", bookInfo.get_SALEPRICE());
							map_1.put("PRICE", bookInfo.get_PRICE());
							
							GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	   						
	   						goodsDAO.UptRegItem(map_1);
							
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
		        
		        return "";
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
		            requestPost.addHeader("Authorization", Key);
		          
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
		
		public void InsertError(String ERROR_GUBUN, String ISBN13, String ITEMCD, String MSG) {
			java.util.HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ERROR_GUBUN", ERROR_GUBUN);
			map.put("ISBN13", ISBN13);
			map.put("ITEMCD", ITEMCD);
			map.put("MSG", MSG);

			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			goodsDAO.InsertError(map);
		}
		
		 public String getHttpHTML_POST_Xml(String json, String Key, String surl) {
		        //params
		        String method = "POST";
		       
		       
		        CloseableHttpClient client = null;
		        try {
		            //create client
		            client = HttpClients.createDefault();
		            //build uri
		            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
		           
		            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

		            StringEntity params =new StringEntity(json,"UTF-8");
		            // set header, demonstarte how to use hmac signature here
		            requestPost.addHeader("Authorization", Key);
		          
		            requestPost.addHeader("content-type", "application/xml");
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
						 
						
						String result = jsonObj.get("result").toString();
						String data ="";
						JSONObject jsonObj_data = (JSONObject)jsonParser.parse( result );	
						String resultCode = jsonObj_data.get("resultCode").toString();
						String resultMessage = jsonObj_data.get("resultMessage").toString();
						String itemId = "";
						
						
						
						return resultCode;
						
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
	 


	}

