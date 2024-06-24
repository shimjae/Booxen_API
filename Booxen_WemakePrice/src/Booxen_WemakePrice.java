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

import java.awt.print.Book;
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


public class Booxen_WemakePrice {
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
		    		wk.GetTransNoList("22275"); //
		    		
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
			    
			    System.out.println("=====상품수정  start=====");
				try
				{
					List<Goods> bookStatusList = goodsDAO.GetStatusGoodsList();
					
					for (Goods bookInfo : bookStatusList) {
						System.out.println("상품상태수정 ISBN13: " + bookInfo.get_ISBN13());					
						int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

						if (Pcnt > 0) {
							work wk = new work();
							System.out.println("=====상품상태수정  start=====" + bookInfo.get_ISBN13());
							wk.UptGoodsAPI(bookInfo);
						}
					}

					
				}catch (Exception e) {
					// TODO: handle exception
				}
				
			    
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
				 
				
				
			
			
			   
				
				
				 /*
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
							wk.Get_ProductInfo(bookInfo);
							
						}
					}
				} catch (Exception e) {
				}
				System.out.println("=====상품가격수정 End=====");
				*/
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
			String Key="ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
			
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
	
	public void Get_ProductInfo(Goods bookInfo) {
		//테스트
		//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
		//운영
		String Key="ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
		
		String sReturn = "";
		try {
			URL url = new URL("https://w-api.wemakeprice.com/product/out/getProduct?productNo=" + bookInfo.get_PRODUCTNO());
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
		
			con.setRequestMethod("GET");
			con.setRequestProperty("apiKey", Key);
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
				
				 JSONParser jsonParser = new JSONParser();
				 JSONObject jsonObj = (JSONObject)jsonParser.parse( sReturn );				
				 
				 String code = jsonObj.get("resultCode").toString();
					String data ="";
					if (code.equals("200"))
					{
						data = jsonObj.get("data").toString();
						JSONObject jsonObj_data = (JSONObject)jsonParser.parse( data );	
						String str_sale = jsonObj_data.get("sale").toString();
						
						JSONObject jsonObj_sale = (JSONObject)jsonParser.parse( str_sale );
						
						String saleStartDate =  jsonObj_sale.get("saleStartDate").toString();
						String saleEndDate =  jsonObj_sale.get("saleEndDate").toString();
						
						UptGoodsSaleAPI(bookInfo,saleStartDate,saleEndDate);
					}
						
			} else {
				System.out.println(con.getResponseMessage());
			}

		} catch (Exception e) {
			System.err.println(e.toString());
		}
		
					
			 
        
	}
	
	
	public void Get_OrderInfo() {
		//테스트
		//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
		//운영
		String Key="ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        JSONObject OrderInfo = new JSONObject();
        
        
        cal.add(Calendar.HOUR, -23);
        //OrderInfo.put("fromDate", df.format(cal.getTime()));
        OrderInfo.put("fromDate", df.format(cal.getTime()));
        
        cal.add(Calendar.HOUR, 23);
        //OrderInfo.put("toDate", df.format(cal.getTime()));
        OrderInfo.put("toDate", df.format(cal.getTime()));
        OrderInfo.put("type", "NEW");
        OrderInfo.put("searchDateType", "NEW");
        
        //테스트
        //String sVal = getHttpHTML_POST_Order(OrderInfo.toJSONString(),Key,"https://wapi-stg.wemakeprice.com/order/out/getOrderList");
        //운영
        String sVal = getHttpHTML_POST_Order(OrderInfo.toJSONString(),Key,"https://w-api.wemakeprice.com/order/out/getOrderList");
       /*
        try {
            OutputStream output = new FileOutputStream("D:/Output.txt");
            String str =sVal;
            byte[] by=str.getBytes();
            output.write(by);
        		
        } catch (Exception e) {
                e.getStackTrace();
        }
      
        
        String sVal="";
        try {
 	       // 바이트 단위로 파일읽기
 	        String filePath = "D:/Output.txt"; // 대상 파일
 	        FileInputStream fileStream = null; // 파일 스트림
 	        
 	        fileStream = new FileInputStream( filePath );// 파일 스트림 생성
 	        //버퍼 선언
 	        byte[ ] readBuffer = new byte[fileStream.available()];
 	        while (fileStream.read( readBuffer ) != -1){}
 	        System.out.println(new String(readBuffer)); //출력
 	       sVal = new String(readBuffer);
 	        fileStream.close(); //스트림 닫기
 	    } catch (Exception e) {
 		e.getStackTrace();
 	    }
        
          */
        if (!sVal.equals("")) {
        	try
        	{
        		JSONParser jsonParser = new JSONParser();
   		     	JSONObject jsonObj = (JSONObject)jsonParser.parse(sVal);							 				 				 				 			   		     	
   		     	String data = jsonObj.get("data").toString();
   		     	
   		     	JSONObject jsonBundle = (JSONObject)jsonParser.parse(data);							 				 				
   		     	JSONArray jsonArray_Bundle = (JSONArray)jsonBundle.get("bundle");
	   		 	
   		     	for(int i=0;i<jsonArray_Bundle.size();i++)
   		     	{
	   		 		JSONObject jsonObjOrder = (JSONObject)jsonArray_Bundle.get(i);
	   		 		
	   		 		String orderDate = jsonObjOrder.get("orderDate").toString();
					String buyerName = jsonObjOrder.get("buyerName").toString();
					String name = "";
					String phone = "";
					String zipcode="";
					String addrFixed="";
					String addrDetail="";
					String message="";
					String orderNo="";
					String productNo="";
					String productName="";
					String productPrice="";
					String productQty="";
					String sellerProductCode="";
					String shipPrice = jsonObjOrder.get("shipPrice").toString();
					String bundleNo =jsonObjOrder.get("bundleNo").toString();
					
					boolean Wmp = false;
					
					
					JSONObject JSONObject_delivery = (JSONObject)jsonParser.parse(jsonObjOrder.get("delivery").toString());					
					name = JSONObject_delivery.get("name").toString();
	   		 		phone = JSONObject_delivery.get("phone").toString();
	   		 		
	   		 		JSONObject JSONObject_shipAddress= (JSONObject)jsonParser.parse(JSONObject_delivery.get("shipAddress").toString());
	   		 		zipcode = JSONObject_shipAddress.get("zipcode").toString();
	   		 		addrFixed = JSONObject_shipAddress.get("addrFixed").toString();
	   		 		addrDetail = JSONObject_shipAddress.get("addrDetail").toString();
	   		 		message = JSONObject_shipAddress.get("message").toString();
	   		 
	   		 		
	   		 		JSONArray jsonArray_orderProduct = (JSONArray)jsonObjOrder.get("orderProduct");
	   		 		for(int ix=0;ix<jsonArray_orderProduct.size();ix++)
	   		 		{
	   		 			JSONObject jsonObjOrder_Item = (JSONObject)jsonArray_orderProduct.get(ix);
	   		 			orderNo = jsonObjOrder_Item.get("orderNo").toString();
	   		 			productNo = jsonObjOrder_Item.get("productNo").toString();
	   		 			productName = jsonObjOrder_Item.get("productName").toString();
	   		 			productPrice = jsonObjOrder_Item.get("productPrice").toString();
	   		 			productQty = jsonObjOrder_Item.get("productQty").toString();
	   		 			sellerProductCode = jsonObjOrder_Item.get("sellerProductCode").toString();
	   		 			
	   		 			
	   		 		 if (!sellerProductCode.equals("") && (sellerProductCode.substring(0, 1).equals("8") || sellerProductCode.substring(0, 1).equals("9"))) {
	   		 			Order Order = new Order();
	   		 			
		   		 		
						Order.Set_ORD_DATE(orderDate.toString().substring(0,10).replace("-", ""));
						Order.Set_ORD_ID(orderNo);
						Order.Set_ISBN13(sellerProductCode);
						Order.Set_ORD_NO("1");
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
						
	   		 			
		   		 		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("ORD_ID", Order.Get_ORD_ID());
						map.put("ISBN13", Order.Get_ISBN13());
						map.put("ORD_NO", Order.Get_ORD_NO());
	
						OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	
						int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
						int Wmp_Cnt = orderDAO.GetExistOrderList(map);
						
						if (API_MstCnt < 1) {
							double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
	 						double SELL_TOTAMT = SELL_AMT * Double.parseDouble(Order.Get_SELL_QTY());
	 						 
							java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
							maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
							maping.put("SELLER_ID", "booxen");
							maping.put("MALL_ID", "22275");
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
							maping.put("SHOPLINKER_ORDER_ID", bundleNo);
							try {
							
								if (orderDAO.InsertBooxenOrdList(maping) > 0) {
									
									System.out.println("=====Wmp=====true" + bundleNo);
									
									Wmp = true;
								}
								
							} catch (Exception ex) {
								
								System.out.println(ex.getMessage());
								
								InsertError("RegOrder", Order.Get_ORD_ID(), "", ex.getMessage());
							}
							
							
						}
	   		 		 }
	   		 			
	   		 		}
	   		 	
	   		 		if (Wmp)
	   		 		{
		   		 		try
						{
							System.out.println("=====setOrderConfirm=====" + bundleNo);
							JSONObject ObjectBundle = new JSONObject();						        
							ObjectBundle.put("bundleNo", bundleNo);               							       
							
							String sCode = setOrderConfirm(ObjectBundle.toJSONString(),Key,"https://w-api.wemakeprice.com/order/in/setOrderConfirm");
							
							System.out.println("=====setOrderConfirm End=====" + bundleNo);
							
							
							
						}catch (Exception e) {
							// TODO: handle exception
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
	
	public void UptGoodsSaleAPI(Goods bookInfo,String saleStartDate,String saleEndDate) {
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
			String sRtn = GetXml_Sale_Upt(bookInfo, SALEPRICE,saleStartDate,saleEndDate);
			
			sRtn = sRtn.replace("&", "");
			// String sRtn = Upt_GetXml();
			//테스트
			//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
			
			//운영
			String Key = "ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
			
			if (!"".equals(sRtn)) {
				String sVal = "";
				// Set_GoodsRegister
				// Set_GoodsState
				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Upt(sRtn, Key, "https://w-api.wemakeprice.com/product/in/setProductSale",bookInfo);				

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

			//String sRtn = GetXml_Upt(bookInfo, SALEPRICE);
			String sRtn = GetXml_Status(bookInfo);
			
			sRtn = sRtn.replace("&", "");
			// String sRtn = Upt_GetXml();
			//테스트
			//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
			
			//운영
			String Key = "ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
			
			if (!"".equals(sRtn)) {
				String sVal = "";			
				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Upt(sRtn, Key, "https://w-api.wemakeprice.com/product/in/setProductStatus",bookInfo);				

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
			String Key = "ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
			
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

			String sRtn = GetXml(bookInfo, SALEPRICE,"NEW");
			
			sRtn = sRtn.replace("&", "");
			// String sRtn = Upt_GetXml();
			
			//테스트
			//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
			
			//운영
			String Key="ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
			if (!"".equals(sRtn)) {
				String sVal = "";
				// Set_GoodsRegister
				// Set_GoodsState

				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Reg(sRtn, Key, "https://w-api.wemakeprice.com/product/in/setProduct", bookInfo);				

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	
	
	public void UptGoodsAPI(Goods bookInfo) {
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

			String sRtn = GetXml(bookInfo, SALEPRICE,"OLD");
			
			sRtn = sRtn.replace("&", "");
			// String sRtn = Upt_GetXml();
			
			//테스트
			//String Key = "d43090c6bb2eece68837a25ab0778be0f583b34d55c29b337326dfd3e6337a08beba1ff5fc8c9fff6221d924cd080d26fbb76b288721682dd61533f02d769f74";
			
			//운영
			String Key="ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
			if (!"".equals(sRtn)) {
				String sVal = "";
				// Set_GoodsRegister
				// Set_GoodsState

				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Upt(sRtn, Key, "https://w-api.wemakeprice.com/product/in/setProduct", bookInfo);				

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	
	public String GetXml(Goods bookInfo, double SALEPRICE ,String sGubun) {
		//최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();        
        JSONArray basicArray = new JSONArray();               
         
        Gson gson = new Gson();
      
        if (sGubun.equals("NEW")) {
        	jsonObject.put("productNo", "0");	
        }else {
        	jsonObject.put("productNo", bookInfo.get_PRODUCTNO());
        }
        
        
        JSONObject basicInfo = new JSONObject();
        /*basicInfo.put("productName", bookInfo.get_BOOK_NM());*/
        basicInfo.put("productName", "[5%적립] " + bookInfo.get_BOOK_NM());
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
        
        SaleInfo.put("salePeriod", "A");
        String Key="ea6324649b0fef21878fea999c6d69f140287270ed8e5b630792ff0989c344cc38e4fb945927fc363d435d6af816ff93ff5156d1dd86f3159ed47a29aec0c5ba";
		
        if (bookInfo.get_PRODUCTNO() != null && !bookInfo.get_PRODUCTNO().equals(""))
        {
        	String saleStartDate = getHttpHTML_StartDate(Key,"https://w-api.wemakeprice.com//product/out/getProduct?productNo=" + bookInfo.get_PRODUCTNO(),"START");
            
            SaleInfo.put("saleStartDate", saleStartDate);
            	
        }else {
        	SaleInfo.put("saleStartDate", df.format(cal.getTime())+ " 01:00");
        }
        
        if (bookInfo.get_PRODUCTNO() != null && !bookInfo.get_PRODUCTNO().equals(""))
        {
        	SaleInfo.put("saleEndDate", "2037-12-31 23:00");	
        	//String saleEndDate = getHttpHTML_StartDate(Key,"https://w-api.wemakeprice.com//product/out/getProduct?productNo=" + bookInfo.get_PRODUCTNO(),"END");
            
        	//SaleInfo.put("saleEndDate", saleEndDate);
        }else {
        	SaleInfo.put("saleEndDate", "2037-12-31 23:00");	
        }
        
        if (bookInfo.get_PRODUCTNO() != null && !bookInfo.get_PRODUCTNO().equals(""))
        {
        	if (SaleInfo.get("saleStartDate").equals(""))
            {
            	DelRegItem(bookInfo.get_ISBN13());
            }
        }
       
        SaleInfo.put("originPrice", null);
        SaleInfo.put("salePrice", SALEPRICE);
        
        if (bookInfo.get_BOOKSTS().equals("현매") || bookInfo.get_BOOKSTS().equals("정상"))
        {
        	SaleInfo.put("stockCount", 99999);
//SaleInfo.put("stockCount", 0);	
        	
        	//DelRegItem(bookInfo.get_ISBN13());;
            
        }else {
        	SaleInfo.put("stockCount", 0);	
        }
        
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
        NoticeValue3.put("description", "상품상세 설명 참고");
        
        JSONObject NoticeValue4 = new JSONObject();
        NoticeValue4.put("noticeNo", "316");
        NoticeValue4.put("noticeName","쪽수 (전자책의 경우 제외)");
        NoticeValue4.put("description", "상품상세 설명 참고");
        
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

	
	
	public String GetXml_Sale_Upt(Goods bookInfo, double SALEPRICE,String saleStartDate,String saleEndDate) {

		//최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();        
        JSONArray basicArray = new JSONArray();               
         
        Gson gson = new Gson();
      
        jsonObject.put("productNo", bookInfo.get_PRODUCTNO());
        
        JSONObject SaleInfo = new JSONObject();
        
        
        
       
        
        SaleInfo.put("salePeriod", "A");
        SaleInfo.put("saleStartDate", saleStartDate);        
        SaleInfo.put("saleEndDate", "2037-12-31 23:00");
        
        
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
        basicInfo.put("productName", bookInfo.get_BOOK_NM());
        basicInfo.put("productType", "N");
        basicInfo.put("dcateCode", GetCategory(bookInfo));
        //테스트
        //basicInfo.put("shipPolicyNo", 3094);
        
        //실서버
        basicInfo.put("shipPolicyNo", 153263);
        
        
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

	 public String ex1(Date date, String pattern) {
			String result = null;
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
			result = format.format(date);
			return result;
		}
	 
	  public String getHttpHTML_StartDate(String Key, String surl,String sGubun) {
		  String response = "";
			
			try {
				
				URL url = new URL(surl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET"); // 전송 방식
				conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
				conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초) 
				conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
				conn.setDoOutput(true);
				conn.setRequestProperty("apiKey", Key);
				
		      
				Charset charset = Charset.forName("UTF-8");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				
				String inputLine;			
				StringBuffer sb = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}
				br.close();
				
				response = sb.toString();
				
				
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject)jsonParser.parse( response );		
				
				String code = jsonObj.get("resultCode").toString();
				String data ="";
				if (code.equals("200"))
				{
					data = jsonObj.get("data").toString();
					JSONObject jsonObj_data = (JSONObject)jsonParser.parse( data );
					String _Sale = jsonObj_data.get("sale").toString();
					
					JSONObject _jsonObj_sale= (JSONObject)jsonParser.parse( _Sale );
					
					if (sGubun.equals("START"))
					{
						return _jsonObj_sale.get("saleStartDate").toString();
					}else {
						return _jsonObj_sale.get("saleEndDate").toString();	
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return response;
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
					if (code.equals("200"))
					{
						data = jsonObj.get("data").toString();
						JSONObject jsonObj_data = (JSONObject)jsonParser.parse( data );	
						String productNo = jsonObj_data.get("productNo").toString();
						
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
						InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), jsonObj.get("error").toString());
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
						map_1.put("BOOK_NM", bookInfo.get_BOOK_NM());
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

	public void DelRegItem(String ISBN13) {
		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ISBN13", ISBN13);
		
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		goodsDAO.DelRegItem(map);
	}
	
	public void DelRegItem_Dupple(String ISBN13 ) {
		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ISBN13", ISBN13);
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		goodsDAO.DelRegItem_Dupple(map);
	}
}

