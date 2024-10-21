
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.StringReader;
import java.math.BigDecimal;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import dao.GoodsDAO;
import dao.OrderDAO;
import model.Goods;
import model.Order;
import mybatis.MyBatisConnectionFactory;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Booxen_Shaplinker {

	public static void main(String[] args) {
		try {
			
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			work wk = new work();	
			
			
			
		    try {
		    	System.out.println("=====샵링커  주문start=====");
		    	 /*
		    	List<Goods> bookList = goodsDAO.GetGoodsList();
		    	
		    	 for(Goods bookInfo: bookList){
		    		
				    	try {
				    	      System.out.println(bookInfo.get_ISBN13());  
				    	      
				    	     if (bookInfo.get_SMCARD_PRODUCT_ID()==null || bookInfo.get_SMCARD_PRODUCT_ID().equals(""))
				    	      {
				    	    		 //5.SMCARD				    	    		    
				    	    		  wk.MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0231", "booxen01", "00000008","SMCARD");
									
								 
				    	      }
				    					    	       if (bookInfo.get_WMP_PRODUCT_ID()==null || bookInfo.get_WMP_PRODUCT_ID().equals(""))
					    	      {
					    	    		 //6.WMP	    
					    	    		  wk.MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0287", "booxen2020", "00000036","WMP");
										
									 
					    	      }
				    	       
				    	      
				    	      if (bookInfo.get_SSG_PRODUCT_ID()==null || bookInfo.get_SSG_PRODUCT_ID().equals(""))
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //4.SSG				    	    		    
				    	    		  wk.MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0170", "0024774739", "00000029","SSG");
											 
									 }
								 
				    	      }
				    	      if (bookInfo.get_EMART_PRODUCT_ID()==null || bookInfo.get_EMART_PRODUCT_ID().equals(""))
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //4.EMART				    	    		    
				    	    		  wk.MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0063", "0024774739", "00000035","EMART");
											 
									 }
								 
				    	      }
				    	     
				     		/*
				    	 	 //3.지마켓
				    	  
				    	      if (bookInfo.get_GMARKET_PRODUCT_ID() == null || bookInfo.get_GMARKET_PRODUCT_ID() =="")
				    	      {
				    	    	  wk.MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0010", "booxen4", "00000026","GMARKET"); 
				    	      }
				    	      
				    	      if (bookInfo.get_TMON_PRODUCT_ID() ==null || bookInfo.get_TMON_PRODUCT_ID() =="")
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //4.티몬				    	    		    
				    	    		  wk.MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0182", "booxen1", "00000025","TMON");
											 
									 }
								 
				    	      }
				    	     
				    	     
				    	    
				    	      if (bookInfo.get_LOTTE_PRODUCT_ID() ==null || bookInfo.get_LOTTE_PRODUCT_ID() =="")
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //4.롯데
				    	    		     wk.UptGoodsAPI(bookInfo);
				    	    		     
				    	    		     
				    	    		     String StrCateGory ="";
				    	    		     
				    	    		     if (bookInfo.get_BX_CATECD() ==null ||bookInfo.get_BX_CATECD().equals(""))
				    	    				{
				    	    					StrCateGory ="010101";
				    	    				}else {
				    	    					java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>(); 
				    	    					Result_map = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());
				    	    					
				    	    					if (Result_map == null){
				    	    						StrCateGory ="010101";
				    	    					} else {
				    	    						Iterator iter = Result_map.entrySet().iterator();
				    	    						while (iter.hasNext()) {

				    	    						    Entry entry = (Entry) iter.next();

				    	    						    StrCateGory = entry.getValue().toString();

				    	    						}
				    	    					}				    	    				
				    	    				}
				    	    		     
				    	    		     String Group_id ="";
				    	    		     
				    	    		     if (StrCateGory.substring(0, 2).equals("39"))
				    	    		     {
				    	    		    	 Group_id ="00000031";
				    	    		     }else if(StrCateGory.substring(0, 2).equals("41"))
				    	    		     {
				    	    		    	 Group_id ="00000032";
				    	    		     }else if(StrCateGory.substring(0, 2).equals("42"))
				    	    		     {
				    	    		    	 Group_id ="00000034";
				    	    		     }else if(StrCateGory.substring(0, 2).equals("45"))
				    	    		     {
				    	    		    	 Group_id ="00000030";
				    	    		     }else if(StrCateGory.substring(0, 2).equals("25"))
				    	    		     {
				    	    		    	 Group_id ="00000031";
				    	    		     }else {
				    	    		    	 Group_id ="00000027";
				    	    		     }
				    	    		     
									     wk.MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0022", "406735", Group_id,"LOTTE");
											 
									 }
								 
				    	      }
								
							
				    	    } catch(Exception e) {			    	    	
				    	    	wk.InsertError("SHAPLINKER","RegGoods","2","",e.getMessage());
				    	    	System.out.println(e.getMessage());   
				    	    }
		
			          }
				  
		    	 */
		    	//송장입력
		    	try {
		    		wk.GetTransNoList("APISHOP_0148","dasoda11","33313"); //스토어팜
		    		wk.GetTransNoList("APISHOP_0276","booxen","50235");   //베네피아
		    		//wk.GetTransNoList("APISHOP_0033","AKM1418519641","52816");   //AK
		    	}catch (Exception e) {
					// TODO: handle exception
				}
		    	
		    	//주문
		    	
		    	
			    try
			    {
			    
			    	
			    	wk.Get_OrderInfo("APISHOP_0148", "dasoda11", "33313");  //스토어팜
			          wk.Get_OrderInfo("APISHOP_0276", "booxen", "50235");  //베네피아		
			          //wk.Get_OrderInfo("APISHOP_0010", "booxen4", "22276");	//지마켓	
			         // wk.Get_OrderInfo("APISHOP_0182", "booxen1", "22464"); //티몬
			          //wk.Get_OrderInfo("APISHOP_0170", "0024774739", "51072"); //신세계닷컴
			          //wk.Get_OrderInfo("APISHOP_0063", "0024774739", "51173"); //이마트
			          wk.Get_OrderInfo("APISHOP_0231", "booxen01", "38112"); //삼성카드
			          //wk.Get_OrderInfo("APISHOP_0287", "booxen2020", "22275");//위메프2.0
			         // wk.Get_OrderInfo("APISHOP_0033", "AKM1418519641", "52816");//AK 몰
			          wk.Get_OrderInfo("APISHOP_0297", "booxen", "38112"); //도매꾹
			          
			          /*
			          wk.Get_OrderInfo("APISHOP_0321", "LD477577", "51071");  //롯데온
			          
			         
			          java.util.HashMap<String, Object> map_mall = new HashMap<String, Object>(); 
						map_mall.put("MALL_ID", "1");
						
				    	List list = goodsDAO.GetOrderList(map_mall);
						
						for(int i=0; i < list.size();i++)
						{
							HashMap map = (HashMap)list.get(i);
							
							String MALL_ORDER_ID = (String)map.get("MALL_ORDER_ID");
							
							wk.Get_OrderDetailInfo(MALL_ORDER_ID, "LD477577", "51071");  //롯데온
						}
			          */
			    	
			    }catch (Exception e) {			    	
					// TODO: handle exception
			    	wk.InsertError("SHAPLINKER","RegGoods","","",e.getMessage());
				}
			    
			   
			    System.out.println("=====샵링커  주문End=====");
			   
			    
		    	System.out.println("=====샵링커 상품등록 start=====");   		        
		    	System.out.println("=====GetGoodsList start=====");   	
			    List<Goods> bookList = goodsDAO.GetGoodsList();
			    System.out.println("=====GetGoodsList End=====");
			
			    for(Goods bookInfo: bookList){
			    	try {
			    	      System.out.println(bookInfo.get_ISBN13());   
			    	      if (Integer.parseInt( bookInfo.get_INRT()) <=75)
			    	      {
			    	    	  if (bookInfo.get_BX_CATECD()==null)
				            	{
				            		bookInfo.set_BX_CATECD("178452");
				            	}
				            	
				    		   //int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());
					            
					            if ((bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매") ))
					            {
					            		wk.RegGoodsAPI(bookInfo);						            	
					            } 
					          
			    	      }
			    	      
				          
			    	    } catch(Exception e) {			    	    	
			    	    	wk.InsertError("SHAPLINKER","RegGoods","2","",e.getMessage());
			    	    	System.out.println(e.getMessage());   
			    	    }
		          
		          }
			   
			    System.out.println("=====샵링커 상품등록 End=====");
			
			    System.out.println("=====샵링커 상품수정 start=====");
			    List<Goods> bookUptList = goodsDAO.GetUptGoodsList();
			    for(Goods bookInfo: bookUptList){
		           	           
		          	 wk.UptGoodsAPI(bookInfo);
		          }
			    System.out.println("=====샵링커 상품수정 End=====");
			
			    /*
			    System.out.println("=====지마켓 기간연장 start=====");
			    
			    List<Goods> PeriodList = goodsDAO.GetGmarketPeriod();
			    
			    for(Goods PeriodInfo: PeriodList){
			    	wk.OpenMarketContinue(PeriodInfo);
			    }
			    
			
			    System.out.println("=====지마켓 기간연장 End=====");
			*/
			
			    System.exit(0);
			   
			}catch (Exception e) {
				wk.InsertError("SHAPLINKER","RegGoods","3","",e.getMessage());
			}
		}catch (Exception e) {
			// TODO: handle exception
			
			System.exit(0);
		}
	}
	
}

class work{
	
	public void GetTransNoList(String mall_order_code , String mall_user_id , String MALL_ID)
	{
		try
		{
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			java.util.HashMap<String, Object> map_mall = new HashMap<String, Object>(); 
			map_mall.put("MALL_ID", MALL_ID);
				 
			List list = orderDAO.GetTransNoList(map_mall);
			
			for(int i=0; i < list.size();i++)
			{
				HashMap map = (HashMap)list.get(i);
				
				String TRNS_NO = (String)map.get("TRNS_NO");
				String ORD_ID  = (String)map.get("ORD_ID");				
				String SHOPLINKER_ORDER_ID = (String)map.get("SHOPLINKER_ORDER_ID");
				
				if (!SHOPLINKER_ORDER_ID.equals(""))
				{
					String BasongStatus = Get_OrderStatusInfo(SHOPLINKER_ORDER_ID);
					
					if (BasongStatus.equals("발주확인"))
					{
						SendTranNo(ORD_ID , TRNS_NO,MALL_ID,SHOPLINKER_ORDER_ID,mall_order_code,mall_user_id);	
					}
					else if (BasongStatus.equals("송장등록"))
					{
						 String RtnMsg="";
	 	 				 java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
	 	 				 map1.put("ORD_ID", ORD_ID);
	 	 				 map1.put("MALL_ID", MALL_ID);
	 					
	 					
	 					orderDAO.UptTrans(map1);
					}
					
					
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void SendTranNo(String ORD_ID ,String delivery_invoice,String MALL_ID , String SHOPLINKER_ORDER_ID,String mall_order_code , String master_id)
	{
		try
		{
			
			Date date = Calendar.getInstance().getTime();

	         StringBuilder sb = new StringBuilder();
	         sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
	         sb.append("<Shoplinker>");
	         sb.append("<Delivery>");	        
	         sb.append("<customer_id>a0010102</customer_id>");
	         sb.append("<order_id>"+SHOPLINKER_ORDER_ID+"</order_id>");
	         sb.append("<delivery_name><![CDATA[CJGLS]]></delivery_name>");
	         sb.append("<delivery_invoice>"+delivery_invoice+"</delivery_invoice>");	       
	         sb.append("</Delivery>");
	         sb.append("</Shoplinker>");	
	         
	         File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\Trans");            
	         if (!desti.exists()) desti.mkdirs();
	      
	          stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\Trans\\"  + delivery_invoice +  ".xml");
	          
	          String sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Order/delivery.php",HOST_IP() + "/Trans/" +  delivery_invoice +  ".xml");
				        
			  Document doc = toXmlDocument(sVal);
				
			  NodeList list = doc.getElementsByTagName("ResultMessage");
			  if (list !=null)
 			 {
 				 String result=""; 				 
 				 String message="";
 				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
 					 if(node.getNodeName().equals("result")) {
 						 result = node.getTextContent();
 					 }
 					
 			       }
 				if (result.equals("true"))
 				{
 					SendTranNo_Mall(ORD_ID , delivery_invoice, MALL_ID ,  SHOPLINKER_ORDER_ID,mall_order_code,master_id);
 					
 				}
 				
 			 }		  
			  
		}catch (Exception e) {
			// TODO: handle exception
		}
		 
	}
	
	
	public void SendTranNo_Mall(String ORD_ID ,String delivery_invoice,String MALL_ID , String SHOPLINKER_ORDER_ID,String mall_order_code , String master_id)
	{
		try
		{
			
			Date date = Calendar.getInstance().getTime();

	         StringBuilder sb = new StringBuilder();
	         sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
	         sb.append("<Shoplinker>");
	         sb.append("<OrderInfo>");	        
	         sb.append("<customer_id>a0010102</customer_id>");
	         sb.append("<mall_id>"+mall_order_code+"</mall_id>");	         
	         sb.append("<Delivery>");	        
	         sb.append("<order_id>"+SHOPLINKER_ORDER_ID+"</order_id>");
	         sb.append("<user_id>"+master_id+"</user_id>");
	         sb.append("<delivery_name><![CDATA[CJGLS]]></delivery_name>");
	         sb.append("<delivery_invoice>"+delivery_invoice+"</delivery_invoice>");
	         sb.append("</Delivery>");
	         sb.append("</OrderInfo>");
	         sb.append("</Shoplinker>");	
	         
	         File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\DeliveryLinkage");            
	         if (!desti.exists()) desti.mkdirs();
	      
	          stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\DeliveryLinkage\\"  + delivery_invoice +  ".xml");
	          
	          String sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Order/DeliveryLinkage.php",HOST_IP() + "/DeliveryLinkage/" +  delivery_invoice +  ".xml");
				        
			  Document doc = toXmlDocument(sVal);
				
			  NodeList list = doc.getElementsByTagName("ResultMessage");
			  if (list !=null)
 			 {
 				 String result=""; 				 
 				 String message="";
 				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
 					 if(node.getNodeName().equals("result")) {
 						 result = node.getTextContent();
 					 }
 					
 			       }
 				if (result.equals("true"))
 				{
 					 String RtnMsg="";
 	 				 java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
 					 map.put("ORD_ID", ORD_ID);
 					 map.put("MALL_ID", MALL_ID);
 					
 					OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
 					
 					orderDAO.UptTrans(map);
 					
 				}
 				
 			 }		  
			  
		}catch (Exception e) {
			// TODO: handle exception
		}
		 
	}
	
	public String Get_OrderStatusInfo(String SHOPLINKER_ORDER_ID)
	{
		  String sVal ="";
		try
		{
			Date date = Calendar.getInstance().getTime();
			
			Date yesterday = new Date ( );
	        yesterday.setTime ( date.getTime ( ) - ( (long) 1000 * 60 * 60 * 96 ) );
	        
			StringBuilder sb = new StringBuilder();
	    	sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
	    	sb.append("<Shoplinker>                                                 ");
	    	sb.append("	<OrderInfo>                                                 ");
	    	sb.append("		<Order>                                                 ");
	    	sb.append("			<customer_id>a0010102</customer_id>                 ");
	    	sb.append("			<id><![CDATA["+SHOPLINKER_ORDER_ID+"]]></id>   ");	    
	    	sb.append("		</Order>                                                ");
	    	sb.append("	</OrderInfo>                                                ");
	    	sb.append("</Shoplinker>                                                ");
	     
	    	File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\orderDetail\\");            
	        if (!desti.exists())
	        {
	        	desti.mkdirs();
	        }
	        
	      
	        
	        stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\orderDetail\\" + SHOPLINKER_ORDER_ID+".xml");
	       
	        sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Order/orderDetail.php",HOST_IP() + "/orderDetail/" + SHOPLINKER_ORDER_ID + ".xml");
	      	  
	    	Document doc = toXmlDocument(sVal);
	    			
	    	NodeList list = doc.getElementsByTagName("order");
	    	Order Order = new Order();
	   	 
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		
			 Element root = doc.getDocumentElement();
			 if (list !=null)
			 {
				 String mall_name ="";
				 
				 int length = doc.getElementsByTagName("order").getLength();
				 for(int i=0; i<length; i++) {
					 Node bNode = doc.getElementsByTagName("order").item(i);
					 sVal = ((Element)bNode).getElementsByTagName("baesong_status").item(0).getTextContent();
				 }
			 }
			 
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return sVal;
	}
	
	public void Get_OrderInfo(String mall_order_code , String mall_user_id , String Booxen_Code)
	{
		
		try
		{
			Date date = Calendar.getInstance().getTime();
			
			Date yesterday = new Date ( );
	        yesterday.setTime ( date.getTime ( ) - ( (long) 1000 * 60 * 60 * 48 ) );
	       
	        
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
	    	sb.append("<Shoplinker>                                                 ");
	    	sb.append("	<OrderInfo>                                                 ");
	    	sb.append("		<Order>                                                 ");
	    	sb.append("			<customer_id>a0010102</customer_id>                 ");
	    	sb.append("			<shoplinker_id><![CDATA[BOOXEN]]></shoplinker_id>   ");
	    	sb.append("			<order_flag>000</order_flag>                 ");
	    	sb.append("			<st_date>"+ex1(yesterday, "yyyyMMdd") +"</st_date>  ");
	    	sb.append("			<ed_date>"+ ex1(date, "yyyyMMdd")+"</ed_date>       ");
	    	sb.append("			<mall_order_code>"+ mall_order_code+"</mall_order_code>     ");
	    	sb.append("			<mall_user_id><![CDATA["+ mall_user_id +"]]></mall_user_id>   ");
	    	
	    	if (mall_order_code.equals("APISHOP_0321"))
	    	{
	    		sb.append("			<page_no>1</page_no>                 ");
	    		sb.append("			<total_standard_count>60</total_standard_count>                 ");
	    	}

	    	sb.append("		</Order>                                                ");
	    	sb.append("	</OrderInfo>                                                ");
	    	sb.append("</Shoplinker>                                                ");
	     
	    	File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\orderlist\\");            
	        if (!desti.exists())
	        {
	        	desti.mkdirs();
	        }
	       
	        String sVal ="";
	        
	        stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\orderlist\\" + mall_order_code+".xml");
		       
	       
	        System.out.println("=====주문  리스트 가져오는중=====");
			
	        sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Order/orderlist.php",HOST_IP() + "/orderlist/" + mall_order_code + ".xml");
	        System.out.println("=====주문  리스트   End=====");
			  
	    	Document doc = toXmlDocument(sVal);
	    			
	    	NodeList list = doc.getElementsByTagName("order");
	    	Order Order = new Order();
	   	 
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		
			 Element root = doc.getDocumentElement();
			 
	    	if (list !=null)
			 {
				 String mall_name ="";
				 
				 int length = doc.getElementsByTagName("order").getLength();
				 for(int i=0; i<length; i++) {
					 Node bNode = doc.getElementsByTagName("order").item(i);
					 
					 Node tNode= ((Element)bNode).getElementsByTagName("partner_product_id").item(0);
					 if (!tNode.getTextContent().equals(""))
					 {
					
						 Order.set_orderdate(((Element)bNode).getElementsByTagName("order_reg_date").item(0).getTextContent().substring(0, 8));
						 Order.set_mall_order_id(((Element)bNode).getElementsByTagName("mall_order_id").item(0).getTextContent());
						 Order.set_shoplinker_order_id(((Element)bNode).getElementsByTagName("shoplinker_order_id").item(0).getTextContent());
						 Order.set_quantity(((Element)bNode).getElementsByTagName("quantity").item(0).getTextContent());
						 if (mall_order_code.equals("APISHOP_0276") || mall_order_code.equals("APISHOP_0010")|| mall_order_code.equals("APISHOP_0231")) //베네피아 공급가
						 {
							 double SELL_TOTAMT =  Double.parseDouble(((Element)bNode).getElementsByTagName("supply_price").item(0).getTextContent()) * Double.parseDouble(Order.get_quantity());
							 
							 Order.set_sale_price(String.valueOf(SELL_TOTAMT));
						 }else
						 {
							 Order.set_sale_price(((Element)bNode).getElementsByTagName("order_price").item(0).getTextContent());
						 }
						 
						 Order.set_order_product_id(((Element)bNode).getElementsByTagName("partner_product_id").item(0).getTextContent());
						 Order.set_product_name(((Element)bNode).getElementsByTagName("product_name").item(0).getTextContent());
						 Order.set_baesong_bi(((Element)bNode).getElementsByTagName("baesong_bi").item(0).getTextContent());
						 Order.set_receive_zipcode(((Element)bNode).getElementsByTagName("receive_zipcode").item(0).getTextContent());
						 Order.set_receive_addr(((Element)bNode).getElementsByTagName("receive_addr").item(0).getTextContent());
						 Order.set_delivery_msg(((Element)bNode).getElementsByTagName("delivery_msg").item(0).getTextContent());
						 Order.set_receive(((Element)bNode).getElementsByTagName("receive").item(0).getTextContent());
						 Order.set_receive_tel(((Element)bNode).getElementsByTagName("receive_tel").item(0).getTextContent());
						 Order.set_receive_cel(((Element)bNode).getElementsByTagName("receive_cel").item(0).getTextContent());
						 
						 if (!Order.get_order_product_id().equals("") && (Order.get_order_product_id().substring(0, 1).equals("8") || Order.get_order_product_id().substring(0, 1).equals("9"))) {
								java.util.HashMap<String, Object> map = new HashMap<String, Object>();
								 map.put("MALL_ID", Booxen_Code);
								 map.put("ORD_ID", Order.get_mall_order_id());
								 map.put("ISBN13", Order.get_order_product_id());
								 
							     System.out.println(Order.get_order_product_id());
								
							     if (Order.get_delivery_msg().length() > 90)
							     {
							    	 Order.set_delivery_msg(Order.get_delivery_msg().substring(0, 90));
							     }
								 
								 if (orderDAO.GetExistBooxenOrderList(map) < 1 )
								 {
									 
									 int OrdSeq = 0;
										java.util.HashMap<String, Object> mapSEQ = new HashMap<String, Object>();
										
										if (Booxen_Code.equals("51072") ||Booxen_Code.equals("51173") )
										{											
											mapSEQ.put("ORD_ID", Order.get_mall_order_id());
										    OrdSeq = orderDAO.GetOrderSeq_SSG(mapSEQ);
										}else
										{
											mapSEQ.put("MALL_ID", Booxen_Code);
											mapSEQ.put("ORD_ID", Order.get_mall_order_id());
										    OrdSeq = orderDAO.GetOrderSeq(mapSEQ);
										}
										
									 
									 
									 java.util.HashMap<String, Object> maping = new HashMap<String, Object>(); 
									 maping.put("ORDERYMD", Order.get_orderdate());
									 maping.put("MALL_ID", Booxen_Code);
									 maping.put("SELLER_ID", mall_user_id);
									 maping.put("PORDNUM", Order.get_mall_order_id());
									 maping.put("ORDSEQ",  Integer.toString(OrdSeq));
									 maping.put("QTY", Order.get_quantity());
									 maping.put("SELL_AMT",   Order.get_sale_price());
									 maping.put("SALPRI", ((Element)bNode).getElementsByTagName("order_price").item(0).getTextContent());
									 maping.put("ISBN13", Order.get_order_product_id());
									 maping.put("ITEM_NM", Order.get_product_name());
									 maping.put("DELIPRI", Order.get_baesong_bi());
									 maping.put("TOZICD", Order.get_receive_zipcode());
									 maping.put("TOADDR", Order.get_receive_addr());
									 maping.put("MEMO", Order.get_delivery_msg());
									 maping.put("TONM", Order.get_receive());
									 maping.put("TOTEL", Order.get_receive_tel() ==null || Order.get_receive_tel().equals("")? Order.get_receive_cel() : Order.get_receive_tel());
									 maping.put("TOEMTEL", Order.get_receive_cel()==null|| Order.get_receive_cel().equals("")?Order.get_receive_tel(): Order.get_receive_cel());
									 maping.put("ORDNUM_NM", "");	
									 maping.put("ORD_TMP1", Order.get_shoplinker_order_id());
									 maping.put("SHOPLINKER_ORDER_ID", Order.get_shoplinker_order_id());
									 
									 if (orderDAO.InsertBooxenOrdList(maping) > 0)
									 {
										// order_modify(Order,mall_order_code);
										 //발주확인
									 }
								 }
							}
					 }
				 }
				 
				/*
				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
					 if(node.getNodeName().equals("order_reg_date")) {		
						 Order.set_orderdate(node.getTextContent().substring(0, 8));
					 }
					 if(node.getNodeName().equals("mall_order_id")) {		
						 Order.set_mall_order_id(node.getTextContent());
					 }
					 
					 if(node.getNodeName().equals("shoplinker_order_id")) {		
						 Order.set_shoplinker_order_id(node.getTextContent());
					 }
					 
					 
					 if(node.getNodeName().equals("quantity")) {		
						 Order.set_quantity(node.getTextContent());
					 }
					 
					 if(node.getNodeName().equals("sale_price")) {		
						 Order.set_sale_price(node.getTextContent());
					 }
					 if(node.getNodeName().equals("partner_product_id")) {		
						 Order.set_order_product_id(node.getTextContent());
						 
					 }
					 if(node.getNodeName().equals("product_name")) {		
						 Order.set_product_name(node.getTextContent());
					 }
					 
					 if(node.getNodeName().equals("baesong_bi")) {		
						 Order.set_baesong_bi(node.getTextContent());
					 }
					 if(node.getNodeName().equals("baesong_bi")) {		
						 Order.set_baesong_bi(node.getTextContent());
					 }
					 if(node.getNodeName().equals("receive_zipcode")) {		
						 Order.set_receive_zipcode(node.getTextContent());
					 }
					 
					 if(node.getNodeName().equals("receive_addr")) {		
						 Order.set_receive_addr(node.getTextContent());
					 }
					 if(node.getNodeName().equals("delivery_msg")) {		
						 Order.set_delivery_msg(node.getTextContent());
					 }
					 
					 if(node.getNodeName().equals("receive")) {		
						 Order.set_receive(node.getTextContent());
					 }
					 
					 if(node.getNodeName().equals("receive_tel")) {		
						 Order.set_receive_tel(node.getTextContent());
					 }
					 
					 if(node.getNodeName().equals("receive_cel")) {		
						 Order.set_receive_cel(node.getTextContent());
					 }
					 
					if (node.getNodeName().equals("pan_type")) 
					{
						if (!Order.get_order_product_id().equals("")) {
							java.util.HashMap<String, Object> map = new HashMap<String, Object>();
							 map.put("MALL_ID", Booxen_Code);
							 map.put("ORD_ID", Order.get_mall_order_id());
							 map.put("ISBN13", Order.get_order_product_id());
							 if (orderDAO.GetExistBooxenOrderList(map) < 1 )
							 {
								 java.util.HashMap<String, Object> maping = new HashMap<String, Object>(); 
								 maping.put("ORDERYMD", Order.get_orderdate());
								 maping.put("MALL_ID", Booxen_Code);
								 maping.put("SELLER_ID", mall_user_id);
								 maping.put("PORDNUM", Order.get_mall_order_id());
								 maping.put("ORDSEQ", "1");
								 maping.put("QTY", Order.get_quantity());
								 maping.put("SELL_AMT",   Order.get_sale_price());
								 maping.put("SALPRI", Order.get_sale_price());
								 maping.put("ISBN13", Order.get_order_product_id());
								 maping.put("ITEM_NM", Order.get_product_name());
								 maping.put("DELIPRI", Order.get_baesong_bi());
								 maping.put("TOZICD", Order.get_receive_zipcode());
								 maping.put("TOADDR", Order.get_receive_addr());
								 maping.put("MEMO", Order.get_delivery_msg());
								 maping.put("TONM", Order.get_receive());
								 maping.put("TOTEL", Order.get_receive_tel());
								 maping.put("TOEMTEL", Order.get_receive_cel()==""?Order.get_receive_tel(): Order.get_receive_cel());
								 maping.put("ORDNUM_NM", "");	
								
								 if (orderDAO.InsertBooxenOrdList(maping) > 0)
								 {
									 order_modify(Order,mall_order_code);
									 //발주확인
								 }
							 }
						}
						
					
					}
				 
			       }
				 			*/
			 }
		}catch (Exception e) {
			// TODO: handle exception
			  System.out.println("Error:" + e.getMessage());
				 
				
		}
		
	}
	
	
	public void Get_OrderDetailInfo(String mall_order_code , String mall_user_id , String Booxen_Code)
	{
		
		try
		{
			
			Date date = Calendar.getInstance().getTime();
			
			Date yesterday = new Date ( );
	        yesterday.setTime ( date.getTime ( ) - ( (long) 1000 * 60 * 60 * 24 ) );
	        
	      
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
	    	sb.append("<Shoplinker>                                                 ");
	    	sb.append("	<OrderInfo>                                                 ");
	    	sb.append("		<Order>                                                 ");
	    	sb.append("			<customer_id>a0010102</customer_id>                 ");	    	
	    	sb.append("			<order_id>"+ mall_order_code+"</order_id>     ");
	    	
	    	sb.append("		</Order>                                                ");
	    	sb.append("	</OrderInfo>                                                ");
	    	sb.append("</Shoplinker>                                                ");
	     
	    	File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\orderlist\\");            
	        if (!desti.exists())
	        {
	        	desti.mkdirs();
	        }
	       
	        String sVal ="";
	        
	        stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\orderlist\\" + mall_order_code+".xml");
	       
	        System.out.println("=====주문  리스트 가져오는중=====");
			
	        sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Order/orderDetail.php",HOST_IP() + "/orderlist/" + mall_order_code + ".xml");
	        System.out.println("=====주문  리스트   End=====");
			  
	    	Document doc = toXmlDocument(sVal);
	    			
	    	NodeList list = doc.getElementsByTagName("order");
	    	Order Order = new Order();
	   	 
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		
			 Element root = doc.getDocumentElement();
			 
	    	if (list !=null)
			 {
				 String mall_name ="";
				 
				 int length = doc.getElementsByTagName("order").getLength();
				 for(int i=0; i<length; i++) {
					 Node bNode = doc.getElementsByTagName("order").item(i);
					 
					 Node tNode= ((Element)bNode).getElementsByTagName("partner_product_id").item(0);
					 if (!tNode.getTextContent().equals(""))
					 {
					
						 Order.set_orderdate(((Element)bNode).getElementsByTagName("order_reg_date").item(0).getTextContent().substring(0, 8));
						 Order.set_mall_order_id(((Element)bNode).getElementsByTagName("mall_order_id").item(0).getTextContent());
						 Order.set_shoplinker_order_id(((Element)bNode).getElementsByTagName("shoplinker_order_id").item(0).getTextContent());
						 Order.set_quantity(((Element)bNode).getElementsByTagName("quantity").item(0).getTextContent());
						 if (mall_order_code.equals("APISHOP_0276") || mall_order_code.equals("APISHOP_0010")) //베네피아 공급가
						 {
							 double SELL_TOTAMT =  Double.parseDouble(((Element)bNode).getElementsByTagName("supply_price").item(0).getTextContent()) * Double.parseDouble(Order.get_quantity());
							 
							 Order.set_sale_price(String.valueOf(SELL_TOTAMT));
						 }else
						 {
							 Order.set_sale_price(((Element)bNode).getElementsByTagName("order_price").item(0).getTextContent());
						 }
						 
						 Order.set_order_product_id(((Element)bNode).getElementsByTagName("partner_product_id").item(0).getTextContent());
						 Order.set_product_name(((Element)bNode).getElementsByTagName("product_name").item(0).getTextContent());
						 Order.set_baesong_bi(((Element)bNode).getElementsByTagName("baesong_bi").item(0).getTextContent());
						 Order.set_receive_zipcode(((Element)bNode).getElementsByTagName("receive_zipcode").item(0).getTextContent());
						 Order.set_receive_addr(((Element)bNode).getElementsByTagName("receive_addr").item(0).getTextContent());
						 Order.set_delivery_msg(((Element)bNode).getElementsByTagName("delivery_msg").item(0).getTextContent());
						 Order.set_receive(((Element)bNode).getElementsByTagName("receive").item(0).getTextContent());
						 Order.set_receive_tel(((Element)bNode).getElementsByTagName("receive_tel").item(0).getTextContent());
						 Order.set_receive_cel(((Element)bNode).getElementsByTagName("receive_cel").item(0).getTextContent());
						 
						 if (!Order.get_order_product_id().equals("") && (Order.get_order_product_id().substring(0, 1).equals("8") || Order.get_order_product_id().substring(0, 1).equals("9"))) {
								java.util.HashMap<String, Object> map = new HashMap<String, Object>();
								 map.put("MALL_ID", Booxen_Code);
								 map.put("ORD_ID", Order.get_mall_order_id());
								 map.put("ISBN13", Order.get_order_product_id());
								 
							     System.out.println(Order.get_order_product_id());
								
							     if (Order.get_delivery_msg().length() > 90)
							     {
							    	 Order.set_delivery_msg(Order.get_delivery_msg().substring(0, 90));
							     }
								 
								 if (orderDAO.GetExistBooxenOrderList(map) < 1 )
								 {
									 
									 int OrdSeq = 0;
										java.util.HashMap<String, Object> mapSEQ = new HashMap<String, Object>();
										
										if (Booxen_Code.equals("51072") ||Booxen_Code.equals("51173") )
										{											
											mapSEQ.put("ORD_ID", Order.get_mall_order_id());
										    OrdSeq = orderDAO.GetOrderSeq_SSG(mapSEQ);
										}else
										{
											mapSEQ.put("MALL_ID", Booxen_Code);
											mapSEQ.put("ORD_ID", Order.get_mall_order_id());
										    OrdSeq = orderDAO.GetOrderSeq(mapSEQ);
										}
										
									 
									 
									 java.util.HashMap<String, Object> maping = new HashMap<String, Object>(); 
									 maping.put("ORDERYMD", Order.get_orderdate());
									 maping.put("MALL_ID", Booxen_Code);
									 maping.put("SELLER_ID", mall_user_id);
									 maping.put("PORDNUM", Order.get_mall_order_id());
									 maping.put("ORDSEQ",  Integer.toString(OrdSeq));
									 maping.put("QTY", Order.get_quantity());
									 maping.put("SELL_AMT",   Order.get_sale_price());
									 maping.put("SALPRI", ((Element)bNode).getElementsByTagName("order_price").item(0).getTextContent());
									 maping.put("ISBN13", Order.get_order_product_id());
									 maping.put("ITEM_NM", Order.get_product_name());
									 maping.put("DELIPRI", Order.get_baesong_bi());
									 maping.put("TOZICD", Order.get_receive_zipcode());
									 maping.put("TOADDR", Order.get_receive_addr());
									 maping.put("MEMO", Order.get_delivery_msg());
									 maping.put("TONM", Order.get_receive());
									 maping.put("TOTEL", Order.get_receive_tel() ==null || Order.get_receive_tel().equals("")? Order.get_receive_cel() : Order.get_receive_tel());
									 maping.put("TOEMTEL", Order.get_receive_cel()==null|| Order.get_receive_cel().equals("")?Order.get_receive_tel(): Order.get_receive_cel());
									 maping.put("ORDNUM_NM", "");	
									 maping.put("ORD_TMP1", Order.get_shoplinker_order_id());
									 maping.put("SHOPLINKER_ORDER_ID", Order.get_shoplinker_order_id());
									 
									 if (orderDAO.InsertBooxenOrdList(maping) > 0)
									 {
										 order_modify(Order,mall_order_code);
										 //발주확인
									 }
								 }
							}
					 }
				 }
				 
			
			 }
		}catch (Exception e) {
			// TODO: handle exception
			  System.out.println("Error:" + e.getMessage());
				 
				
		}
		
	}
	public void order_modify(Order Orderinfo ,String mall_order_code)
	{
		Date date = Calendar.getInstance().getTime();
		
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>									");
			sb.append("<Shoplinker>                                                                  ");
			sb.append("	<customer_id>a0010102</customer_id>                                          ");
			sb.append("	<shoplinker_id>BOOXEN</shoplinker_id>                                       ");
			sb.append("	<order_list>                                                                 ");
			sb.append("		<mall_order_id><![CDATA["+ Orderinfo.get_mall_order_id()+"]]></mall_order_id>    ");
			sb.append("		<shoplinker_order_id><![CDATA["+Orderinfo.get_shoplinker_order_id()+"]]></shoplinker_order_id>                                 ");
			sb.append("		<mall_id><![CDATA["+ mall_order_code +"]]></mall_id>                         ");	
			sb.append("		<delivery_status><![CDATA[002]]></delivery_status>                    ");		
			sb.append("	</order_list>                                                                ");
			sb.append("</Shoplinker>                                                                 ");
			
			
			File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\order_modify\\");            
	        if (!desti.exists())
	        {
	        	desti.mkdirs();
	        }
	        String sVal ="";
	        
	        stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\order_modify\\order_modify_" + mall_order_code+".xml");
	        
	        sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Order/order_modify.php",HOST_IP() + "/order_modify/order_modify_" + mall_order_code + ".xml");
	            
	    	Document doc = toXmlDocument(sVal);
	    			
	    	NodeList list = doc.getElementsByTagName("ResultMessage");
	    	if (list !=null)
			 {
				 String result="";
				 String id="";
				 String message="";
				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
					 if(node.getNodeName().equals("result")) {
						 result = node.getTextContent();
					 }
					 if(node.getNodeName().equals("id")) {
						 id = node.getTextContent();
						 }
					 if(node.getNodeName().equals("message")) {
						 message = node.getTextContent();
						 }
			       }
				 
				 String RtnMsg="";
				 if (result.equals("true"))
				 {	
					
				 }
			 }		    
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void UptGoodsAPI(Goods bookInfo)
	{
		try
		{
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());					
			Date date = Calendar.getInstance().getTime();
			String StrCateGory="";
			
			if (bookInfo.get_BX_CATECD() ==null ||bookInfo.get_BX_CATECD().equals(""))
			{
				StrCateGory ="010101";
			}else {
				java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>(); 
				Result_map = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());
				
				if (Result_map == null){
					StrCateGory ="010101";
				} else {
					Iterator iter = Result_map.entrySet().iterator();
					while (iter.hasNext()) {

					    Entry entry = (Entry) iter.next();

					    StrCateGory = entry.getValue().toString();

					}
				}
				
			
			
				
				
				

			}
			
			
			if (StrCateGory.equals(""))
			{
				
				StrCateGory ="010101";
			}
			
            String Cat1 = "";
            String Cat2 = "";
            String Cat3 = "";
            String Cat4 = "";
            
            Cat1 = StrCateGory.length() > 1 ? StrCateGory.substring(0, 2) : "01";
            Cat2 = StrCateGory.length() > 3 ? StrCateGory.substring(0, 4) : "";
            Cat3 = StrCateGory.length() > 5 ? StrCateGory.substring(0, 6) : "";
            Cat4 = StrCateGory.length() > 7 ? StrCateGory.substring(0, 8) : "";
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
            sb.append("<openMarket>");
            sb.append("<MessageHeader>");
            sb.append("<sendID>1</sendID>");
            sb.append("<senddate>" + ex1(date, "yyyyMMdd") + "</senddate>");
            sb.append(" </MessageHeader>");
            sb.append(" <product>");
            sb.append(" <customer_id>a0010102</customer_id>");
            sb.append(" <partner_product_id>" + bookInfo.get_ISBN13() + "</partner_product_id>");

            sb.append(" <product_name><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></product_name>");

            if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))
            {
            	sb.append(" <sale_status>001</sale_status>");
            }else
            {
            	sb.append(" <sale_status>004</sale_status>");
            }
                                
        
            sb.append(" <ccategory_l><![CDATA[" + Cat1 + "]]></ccategory_l>");
            sb.append(" <ccategory_m><![CDATA[" + Cat2 + "]]></ccategory_m>");
            sb.append(" <ccategory_s><![CDATA[" + Cat3 + "]]></ccategory_s>");
            sb.append(" <ccategory_d><![CDATA[" + Cat4 + "]]></ccategory_d>");



            sb.append(" <maker><![CDATA[" + bookInfo.get_PUBLISHER() + "]]></maker>");
            sb.append(" <maker_dt>" + bookInfo.get_OPENDATE().replace("-", "")  + "</maker_dt>");
            sb.append(" <origin><![CDATA[APIORIGIN_031]]></origin>");
            sb.append(" <image_url num='1'><![CDATA[" + bookInfo.get_IMG_PATH2() + "]]></image_url>");
            sb.append(" <image_url num='2'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='3'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='4'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='5'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='6'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='7'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='8'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='9'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='10'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='11'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='12'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='13'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='14'><![CDATA[]]></image_url>");

            double MARKETPRICE =  Double.parseDouble(bookInfo.get_PRICE());
            double SALEPRICE = 0;


            if ( Double.parseDouble(bookInfo.get_INRT()) > 80) return;

            if (Double.parseDouble(bookInfo.get_INRT()) <= 70)
            {
            	SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
 				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
 				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
 				SALEPRICE = incm_civil_amt.doubleValue();        
            }
            else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76)
            {                
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();

            }
            else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81)
            {
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
            }
            else
            {
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
            }
            
            
            bookInfo.set_SALEPRICE(Double.toString(SALEPRICE).replaceAll((String)"\\.0$", ""));

            sb.append(" <start_price>" + (int)SALEPRICE + "</start_price>");
            sb.append(" <market_price>" + (int)MARKETPRICE + "</market_price>");
            sb.append(" <sale_price>" + (int)SALEPRICE + "</sale_price>");
            sb.append(" <supply_price>" + (int)MARKETPRICE + "</supply_price>");
            sb.append(" <market_price_p>" + (int)MARKETPRICE + "</market_price_p>");
            sb.append(" <sale_price_p>" + (int)SALEPRICE + "</sale_price_p>");
            sb.append(" <supply_price_p>" + (int)MARKETPRICE + "</supply_price_p>");


            sb.append(" <delivery_charge_type><![CDATA[003]]></delivery_charge_type>");
            sb.append(" <delivery_charge>2500</delivery_charge>");
            sb.append(" <tax_yn>002</tax_yn>");
     
            sb.append(" <detail_desc><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></detail_desc>");
            sb.append(" <new_desc_top><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></new_desc_top>");

            sb.append(" <new_desc_center><![CDATA[]]></new_desc_center>");
            sb.append(" <new_desc_bottom><![CDATA[]]></new_desc_bottom>");
            sb.append(" <desc_type_tag><![CDATA[]]>.</desc_type_tag>");
            sb.append(" <quantity>999</quantity>");
            sb.append(" <salearea><![CDATA[001]]></salearea>");
            sb.append(" <partner_id_tmp><![CDATA[]]></partner_id_tmp>");
            sb.append(" <sex></sex>");
            sb.append(" <keyword><![CDATA[]]></keyword>");
            sb.append(" <model><![CDATA[]]></model>");
            sb.append(" <model_no><![CDATA[isbn-" + bookInfo.get_ISBN13() + "]]></model_no>");
         
            sb.append(" <option_kind>000</option_kind>");
            sb.append(" <brand><![CDATA[웅진북센 / WJBOOXEN]]></brand>");
            sb.append(" <auth_no></auth_no>");
            sb.append(" <expirydate></expirydate>");
            sb.append(" <trans_product_id><![CDATA[]]></trans_product_id>");
            sb.append("  </product>");
            sb.append(" </openMarket>");
            
            String sVal ="";
            
            File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\xmlInsert\\");            
            if (!desti.exists())
            {
            	desti.mkdirs();
            }
            
            stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\xmlInsert\\" + bookInfo.get_ISBN13()+".xml");
            
        			 sVal = getHttpHTML_POST("http://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/xmlInsert.php",HOST_IP() + "/xmlInsert/" + bookInfo.get_ISBN13() + ".xml");
                     //java.util.HashMap<String, String> map_url = new HashMap<String, String>(); 
                     //map_url.put("iteminfo_url", HOST_IP() + "/xmlInsert/" + bookInfo.get_ISBN13() + ".xml");
        			 
                     //sVal = getHttpPostData("http://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/xmlInsert.php",map_url); 
        			 
        			 Document doc = toXmlDocument(sVal);
        		      
        		      NodeList list = doc.getElementsByTagName("ResultMessage");
        		      if (list != null)
        		      {
        		        String result = "";
        		        String product_id = "";
        		        String message = "";
        		        for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
        		          if (node.getNodeName().equals("result")) {
        		            result = node.getTextContent();
        		          }
        		          if (node.getNodeName().equals("product_id")) {
        		            product_id = node.getTextContent();
        		          }
        		          if (node.getNodeName().equals("message")) {
        		            message = node.getTextContent();
        		          }
        		        }
        		        
        		        String RtnMsg = "";
        		        if (result.equals("true"))
        		        {

        		          HashMap<String, Object> map = new HashMap();
        		          map.put("ISBN13", bookInfo.get_ISBN13());
        		          map.put("BOOKSTS", bookInfo.get_BOOKSTS());
        		          map.put("PRICE", bookInfo.get_PRICE());
        		          map.put("SALEPRICE", bookInfo.get_SALEPRICE());
        		          
        		          goodsDAO.UptStatusItem(map);
        		          


        		        


        		          if (Double.parseDouble(bookInfo.get_INRT()) <= 65)
        		          {
        		            SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;
        		            BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
        		            incm_civil_amt = incm_civil_amt.setScale(-1, 1);
        		            SALEPRICE = incm_civil_amt.doubleValue();
        		          }
        		          else if ((Double.parseDouble(bookInfo.get_INRT()) > 65) && (Double.parseDouble(bookInfo.get_INRT()) <= 70))
        		          {
        		            SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95D;
        		            BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
        		            incm_civil_amt = incm_civil_amt.setScale(-1, 1);
        		            SALEPRICE = incm_civil_amt.doubleValue();

        		          }
        		          else if ((Double.parseDouble(bookInfo.get_INRT()) > 70) && (Double.parseDouble(bookInfo.get_INRT()) < 76))
        		          {
        		            SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
        		          }
        		          else
        		          {
        		            SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
        		          }
        		          System.out.println("수정 ISBN13: "+bookInfo.get_ISBN13());   	
        		          MallSendUpdate(bookInfo, bookInfo.get_FARM_PRODUCT_ID(), (int)SALEPRICE);
        		          
        		        /*
        		           if (bookInfo.get_FARM_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_FARM_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_FARM_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          
        		          if (bookInfo.get_BENEPIA_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_BENEPIA_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_BENEPIA_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          

        		          if (bookInfo.get_GMARKET_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_GMARKET_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_GMARKET_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          
        		          if (bookInfo.get_SMCARD_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_SMCARD_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_SMCARD_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          


        		          if (bookInfo.get_EMART_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_EMART_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_EMART_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          

        		          if (bookInfo.get_SSG_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_SSG_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_SSG_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          

        		          if (bookInfo.get_WMP_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_WMP_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_WMP_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          
        		          if (bookInfo.get_TMON_PRODUCT_ID() != null)
        		          {
        		            if (!bookInfo.get_TMON_PRODUCT_ID().equals(""))
        		            {
        		              MallSendUpdate(bookInfo, bookInfo.get_TMON_PRODUCT_ID(), (int)SALEPRICE);
        		            }
        		          }
        		          */
        		        }
        		      }
        		    }catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void OpenMarketContinue(Goods bookInfo)
	{
		try
		{
			
			Date date = Calendar.getInstance().getTime();

	         StringBuilder sb = new StringBuilder();
	         sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
	         sb.append("<openmarket>");
	         sb.append("<productInfo>");	        
	         sb.append("<customer_id>a0010102</customer_id>");
	         sb.append("<mall_id>G</mall_id>");
	         sb.append("<mall_product_id><![CDATA["+bookInfo.get_GMARKET_PRODUCT_ID()+"]]></mall_product_id>");
	         sb.append("<conti_prod>004</conti_prod>");	       
	         sb.append("</productInfo>");
	         sb.append("</openmarket>");	
	         
	         File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\OpenMarketContinue");            
	         if (!desti.exists()) desti.mkdirs();
	      
	          stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\OpenMarketContinue\\"  + bookInfo.get_ISBN13() +  ".xml");
	          
	          String sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/OpenMarketContinue.html",HOST_IP() + "/OpenMarketContinue/" +  bookInfo.get_ISBN13() +  ".xml");
				        
			  Document doc = toXmlDocument(sVal);
				
			  NodeList list = doc.getElementsByTagName("ResultMessage");
			  if (list !=null)
 			 {
 				 String result=""; 				 
 				 String message="";
 				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
 					 if(node.getNodeName().equals("result")) {
 						 result = node.getTextContent();
 					 }
 					
 			       }
 				if (result.equals("true"))
 				{
 					 String RtnMsg="";
 	 				 java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
 					 map.put("ISBN13", bookInfo.get_ISBN13());
 					
 						  GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
 						  goodsDAO.UptPeriod(map);
 					
 				}
 				
 			 }		  
			  
		}catch (Exception e) {
			// TODO: handle exception
		}
		 
	}

	  public void MallSendUpdate(Goods bookInfo, String product_id, int SALEPRICE)
	{
		try
		{
			
			Date date = Calendar.getInstance().getTime();
		      
		      StringBuilder sb = new StringBuilder();
		      sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
		      sb.append("<openmarket>");
		      sb.append("<ProductInfo>");
		      sb.append("<Product>");
		      sb.append("<customer_id> a0010102</customer_id>");
		     // sb.append("<mall_product_id>" + product_id + "</mall_product_id>");
		      sb.append("<partner_product_id>" + bookInfo.get_ISBN13() + "</partner_product_id>");

		      if ((bookInfo.get_BOOKSTS().equals("정상")) || (bookInfo.get_BOOKSTS().equals("현매")))
		      {
		        sb.append("<sale_status>001</sale_status>");
		      }
		      else
		      {
		        sb.append("<sale_status>004</sale_status>");
		      }
		      

		      sb.append("<sale_price> " + SALEPRICE + "</sale_price>");
		      sb.append("<quantity> 999</quantity>");
		      sb.append("<keyword> 도서</keyword>");
		      sb.append("  </Product>");
		      sb.append("  </ProductInfo>");
		      
		      sb.append(" </openmarket>");


	       
	         
		      File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\MallSendUpdate");
		      if (!desti.exists()) { desti.mkdirs();
		      }
		      stringToDom(sb.toString(), "D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\MallSendUpdate\\" + product_id + ".xml");
		      
		      String sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/MallSendUpdate.php", HOST_IP() + "/MallSendUpdate/" + product_id + ".xml");
		      
		      Document doc = toXmlDocument(sVal);
		      
		      NodeList list = doc.getElementsByTagName("ResultMessage");
		      if (list != null)
		      {
		        String result = "";
		        String message = "";
		        for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
		          if (node.getNodeName().equals("result")) {
		            result = node.getTextContent();
		          }
		        }
		      }
		    }catch (Exception e) {
			// TODO: handle exception
		}
		 
	}
	
	public void MallSendUpdate_TMON(Goods bookInfo,String product_id)
	{
		double SALEPRICE = 0;
		
		try
		{
			
			if ( Double.parseDouble(bookInfo.get_INRT()) > 70) return;
       	 
			if (Double.parseDouble(bookInfo.get_INRT()) <= 64)
            {
            	SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
    				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
    				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
    				SALEPRICE = incm_civil_amt.doubleValue();   
            }
            else if (Double.parseDouble(bookInfo.get_INRT()) >= 65 && Double.parseDouble(bookInfo.get_INRT()) < 68)
            {            
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
    			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
    			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
    			SALEPRICE = incm_civil_amt.doubleValue();

            }
            else if (Double.parseDouble(bookInfo.get_INRT()) >= 68 && Double.parseDouble(bookInfo.get_INRT()) < 71)
            {
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
            }
            else
            {
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
            }
      	  
			Date date = Calendar.getInstance().getTime();

	         StringBuilder sb = new StringBuilder();
	         sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
	         sb.append("<openmarket>");
	         sb.append("<ProductInfo>");
	         sb.append("<Product>");
	         sb.append("<customer_id> a0010102</customer_id>");
	         sb.append("<mall_product_id>"+product_id+"</mall_product_id>");
	         
	         
	         if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))
	         {
	             sb.append("<sale_status>001</sale_status>");
	         }
	         else
	         {
	             sb.append("<sale_status>004</sale_status>");
	         }


	         sb.append("<sale_price> "+ SALEPRICE + "</sale_price>");
	         sb.append("<quantity> 999</quantity>");
	         sb.append("<keyword> 도서</keyword>");
	         sb.append("  </Product>");
	         sb.append("  </ProductInfo>");

	         sb.append(" </openmarket>");	
	         
	         File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\MallSendUpdate");            
	         if (!desti.exists()) desti.mkdirs();
	      
	          stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\MallSendUpdate\\"  + product_id +  ".xml");
	          
	          String sVal = getHttpHTML_POST_TMON("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/MallSendUpdate.php",HOST_IP() + "/MallSendUpdate/" +  product_id +  ".xml");
				        
			  Document doc = toXmlDocument(sVal);
				
			  NodeList list = doc.getElementsByTagName("ResultMessage");
			  /*
			  if (list !=null)
 			 {
 				 String result=""; 				 
 				 String message="";
 				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
 					 if(node.getNodeName().equals("result")) {
 						 result = node.getTextContent();
 					 }
 					
 			       }
 				 
 				 String RtnMsg="";
 				 java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
				 map.put("ISBN13", bookInfo.get_ISBN13());
				 map.put("BOOKSTS", bookInfo.get_BOOKSTS());
				 map.put("PRICE", bookInfo.get_PRICE());
				 map.put("SALEPRICE", bookInfo.get_SALEPRICE());
				
					  GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					  goodsDAO.UptStatusItem(map);
 			 }		  
			  */
		}catch (Exception e) {
			// TODO: handle exception
		}
		 
	}
	
	public String GetGoodsBody_New(Goods bookInfo)
	{
		String Str_Contents = "";

        Str_Contents = Str_Contents + "<div  style=\"width: 900px; margin: 0 auto; float:none; text-align:left; \">";

        if (!"".equals(bookInfo.get_BOOK_INTRCN_CN()) && bookInfo.get_BOOK_INTRCN_CN() != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("<p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 책소개</p>");
            //sb.append("<span  style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span> ");
            sb.append("<div  style=\"width: 745px;text-align:left;\">");
            sb.append("<span style = \"display:block;text-align:left;\" >");
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
        if (!"".equals(bookInfo.get_CNTNT_CN()) && bookInfo.get_CNTNT_CN() != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" <p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 목차</p>");
            //sb.append("<span  style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span> ");
            sb.append("<div  style=\"width: 745px;text-align:left;\">");
            sb.append("<span style = \"display:block;text-align:left;\" >     ");
            sb.append(bookInfo.get_CNTNT_CN().replace("\r\n", "<br>") + "</div>");

            Str_Contents = Str_Contents + sb.toString();
        }
        if (!"".equals( bookInfo.get_PLSCMPN_BKRVW_CN())&& bookInfo.get_PLSCMPN_BKRVW_CN() != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" <p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 출판사서평</p>");
            //sb.append("<span  style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span>  ");
            sb.append("<div style=\"width: 745px;text-align:left;\">");
            sb.append("<span style = \"display:block;text-align:left;\" >     ");
            sb.append(bookInfo.get_PLSCMPN_BKRVW_CN().replace("\r\n", "<br>") + "</div>");

            Str_Contents = Str_Contents + sb.toString();
        }
        if (!"".equals(bookInfo.get_AUTHR_INTRCN_CN())&& bookInfo.get_AUTHR_INTRCN_CN() != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" <p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 저자소개</p>");
            //sb.append("<span class=\"topBtn\" style=\"position: absolute; right: 0; top: 9px;text-align:left;\"></span>");
            sb.append("<div style=\"width: 745px;text-align:left;\">");
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
	
	
	public String getHttpHTML_POST(String urlStr,String sparam) {		
		String sReturn ="";
		try
		{
			
			URL url = new URL(urlStr); // 호출할 url
	        Map<String,Object> params = new LinkedHashMap<>(); // 파라미터 세팅
	        params.put("iteminfo_url", sparam);
	      
	        if (urlStr.indexOf("MallSendUpdate") > -1)
	        {
	        	 params.put("type", "002");
	        }
	        if (urlStr.indexOf("orderDetail") > -1)
	        {
	        	 params.put("type", "002");
	        }
	        
	 
	        StringBuilder postData = new StringBuilder();
	        for(Map.Entry<String,Object> param : params.entrySet()) {
	            if(postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	 
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes); // POST 호출
	        /*conn.setConnectTimeout(30000);
	        conn.setReadTimeout(30000);*/
	 
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
	 
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while((inputLine = in.readLine()) != null) { // response 출력
	        	 response.append(inputLine);
	        }
	        System.out.println(response.toString());
	        in.close();
	        sReturn = response.toString();
	     
		}catch (Exception e) {
			// TODO: handle exception
		}
		
        return sReturn;
        
	}
	
	public String getHttpHTML_POST_TMON(String urlStr,String sparam) {		
		String sReturn ="";
		try
		{
			
			URL url = new URL(urlStr); // 호출할 url
	        Map<String,Object> params = new LinkedHashMap<>(); // 파라미터 세팅
	        params.put("iteminfo_url", sparam);
	      
	        if (urlStr.indexOf("MallSendUpdate") > -1)
	        {
	        	 params.put("type", "001");
	        }
	 
	        StringBuilder postData = new StringBuilder();
	        for(Map.Entry<String,Object> param : params.entrySet()) {
	            if(postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	 
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes); // POST 호출
	 
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
	 
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while((inputLine = in.readLine()) != null) { // response 출력
	        	 response.append(inputLine);
	        }
	        System.out.println(response.toString());
	        in.close();
	        sReturn = response.toString();
	     
		}catch (Exception e) {
			// TODO: handle exception
		}
		
        return sReturn;
        
	}

	public String getHttpPostData(String url,  Map<String,String> paramMap) {
	    String responseBody = "";
	    try {
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        Builder builder = RequestConfig.custom();
	        /*builder.setConnectTimeout(4000);
	        builder.setSocketTimeout(4000);*/
	        builder.setStaleConnectionCheckEnabled(false);
	        RequestConfig config = builder.build();
	        
	        try {
	            HttpPost httpPost = new HttpPost(url);
	            ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
	            for (Map.Entry<String, String> entry: paramMap.entrySet()) {
	                postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	            }
	            httpPost.setEntity(new UrlEncodedFormEntity(postParams, "euc-kr"));
	            httpPost.setConfig(config);
	            
	            System.out.println("url : " + url);
	            System.out.println("Executing request " + httpPost.getRequestLine());
	 
	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	                
	                @Override
	                public String handleResponse(
	                        final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        String responseStr = "";
	                        if( entity != null ) {
	                             responseStr = EntityUtils.toString(entity,"euc-kr");
	                        }
	                        return responseStr;
	                    } else {
	                        throw new ClientProtocolException("Unexpected response status: " + status);
	                    }
	                }
	 
	            };
	            responseBody = httpclient.execute(httpPost, responseHandler);
	            
	            System.out.println("----------------------------------------");
	            System.out.println(responseBody);
	            
	        } finally {
	            httpclient.close();
	        }
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    
	    return responseBody;
	}
	public  String ex1(Date date, String pattern) {
		String result = null;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
		result = format.format(date);
		return result;
		}
	public  Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException{
        
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(new InputSource(new StringReader(str)));
       
        return document;
   }
	
	public void stringToDom(String xmlSource,String FilePath) 
	        throws IOException {
	    java.io.FileWriter fw = new java.io.FileWriter(FilePath);
	    fw.write(xmlSource);
	    fw.close();
	}
	  public  String HOST_IP()
      {
		  Date date = Calendar.getInstance().getTime();
		
		  //return "http://211.115.107.33/XML/" + ex1(date, "yyyy-MM-dd") +"/" ;
		    return "http://shoplinker.booxen.com/XML/" + ex1(date, "yyyy-MM-dd") +"/" ;
      }

	public String GoodsItemInformationSendXml(String product_id, Goods bookInfo )
	{
		String RtnMsg = "";
		String result="";
			
		try
		{
			 StringBuilder sb = new StringBuilder();
             sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
             sb.append("<openMarket>");
             sb.append("<goodsinfo>");
             sb.append(" <customer_id>a0010102</customer_id>");
             sb.append("<product_id> " + product_id + "</product_id>");
             sb.append("<partner_product_id> " + bookInfo.get_ISBN13() + "</partner_product_id>");
             sb.append("<lclass_id>i26</lclass_id>");
             sb.append("<item>");
             sb.append("<item_seq>2601</item_seq>");
             sb.append("<item_info><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></item_info>");
             sb.append("</item>");
             sb.append("<item>");
             sb.append("<item_seq>2602</item_seq>");
             sb.append("<item_info><![CDATA[" + bookInfo.get_AUTHR() + "##" + bookInfo.get_PUBLISHER() + "]]></item_info>");
             sb.append("</item>");
             sb.append("<item>");
             sb.append("<item_seq>2603</item_seq>");

             if (bookInfo.get_BOOK_WT_VAL()==null)
             {
            	 bookInfo.set_BOOK_WT_VAL("상세참조");
             }

             
             if (bookInfo.get_BOOK_WT_VAL().trim().equals(""))
             {
                 sb.append("<item_info><![CDATA[상세참조]]></item_info>");
             }
             else
             {
                 sb.append("<item_info><![CDATA[" + bookInfo.get_BOOK_WT_VAL().replace("*", "##") + "]]></item_info>");
             }

             sb.append("</item>");
             sb.append("<item>");
             sb.append("<item_seq>2604</item_seq>");
             
             if (bookInfo.get_BOOK_PAGE_VAL()==null)
             {
            	 bookInfo.set_BOOK_PAGE_VAL("상세참조");
             }
             
             if (bookInfo.get_BOOK_PAGE_VAL().trim().equals(""))
             {
                 sb.append("<item_info><![CDATA[상세참조]]></item_info>");
             }
             else
             {
                 sb.append("<item_info><![CDATA["+ bookInfo.get_BOOK_PAGE_VAL() + "]]></item_info>");
             }

             sb.append("</item>");
             sb.append("<item>");
             sb.append("<item_seq>2605</item_seq>");
             sb.append("<item_info><![CDATA[상품상세참조]]></item_info>");
             sb.append("</item>");
             sb.append("<item>");
             sb.append("<item_seq>2606</item_seq>");
             sb.append("<item_info><![CDATA[" + bookInfo.get_OPENDATE() + "]]></item_info>");
             sb.append("</item>");
             sb.append("<item>");
             sb.append("<item_seq>2607</item_seq>");
             sb.append("<item_info><![CDATA[상품상세참조]]></item_info>");
             sb.append("</item>");


             sb.append("  </goodsinfo>");

             sb.append(" </openMarket>");
             
             Date date = Calendar.getInstance().getTime();
             File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\GoodsIteminfo");            
             if (!desti.exists()) desti.mkdirs();
             
              stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\GoodsIteminfo\\" + bookInfo.get_ISBN13()+".xml");
              
                
              RtnMsg = getHttpHTML_POST("http://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/goods_info_reg.php",HOST_IP() + "/GoodsIteminfo/" + bookInfo.get_ISBN13() + ".xml");
              //java.util.HashMap<String, String> map_url = new HashMap<String, String>(); 
              //map_url.put("iteminfo_url", "D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\GoodsIteminfo\\" + bookInfo.get_ISBN13()+".xml");
        
              //RtnMsg = getHttpPostData("http://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/goods_info_reg.php",map_url); 

 			 Document doc = toXmlDocument(RtnMsg);
 			
 			 NodeList list = doc.getElementsByTagName("ResultMessage");
 			
 				
 			 if (list !=null)
			 {
				
				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
					 if(node.getNodeName().equals("result")) {
						 result = node.getTextContent();
					 }
				
			       }
				
			 }
 			 
 			 
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	
	public void MallProductReg( Goods bookInfo , String product_id, String mall_id,String user_id, String group_id, String Site)
    {
        
        Date date = Calendar.getInstance().getTime();
		
        double MARKETPRICE =  Double.parseDouble(bookInfo.get_PRICE());
        double SALEPRICE = 0;

    
        if ( Double.parseDouble(bookInfo.get_INRT()) > 80) return;

        if ((mall_id == "APISHOP_0321") || (mall_id == "APISHOP_0063") || (mall_id == "APISHOP_0170") || (mall_id == "APISHOP_0287") || (mall_id == "APISHOP_0182"))  //티몬 롯데 이미트 신세계
        {
        	  if ( Double.parseDouble(bookInfo.get_INRT()) > 75) return;
        	 
        	  if (Double.parseDouble(bookInfo.get_INRT()) <= 65)
              {
              	SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
      				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
      				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
      				SALEPRICE = incm_civil_amt.doubleValue();   
              }
              else if (Double.parseDouble(bookInfo.get_INRT()) >= 66 && Double.parseDouble(bookInfo.get_INRT()) < 71)
              {            
                  SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
      			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
      			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
      			SALEPRICE = incm_civil_amt.doubleValue();

              }
              else if (Double.parseDouble(bookInfo.get_INRT()) >= 71)
              {
                  SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
              }
              else
              {
                  SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
              }
        }else {
        	 if (Double.parseDouble(bookInfo.get_INRT()) <= 70)
             {
                 SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;
                 BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
                 incm_civil_amt = incm_civil_amt.setScale(-1, 1);
                 SALEPRICE = incm_civil_amt.doubleValue();
               }
               else if ((Double.parseDouble(bookInfo.get_INRT()) > 70) && (Double.parseDouble(bookInfo.get_INRT()) < 76))
               {
                 SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;
                 BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
                 incm_civil_amt = incm_civil_amt.setScale(-1, 1);
                 SALEPRICE = incm_civil_amt.doubleValue();

               }
               else if ((Double.parseDouble(bookInfo.get_INRT()) > 75) && (Double.parseDouble(bookInfo.get_INRT()) < 81))
               {
                 SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
               }
               else
               {
                 SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
               }
        }
        
      

        double SUPPLY_PRICE = SALEPRICE * 0.88;
        
        try
        {

            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
            sb.append("<Shoplinker>");
            sb.append("<ProductMallReg>");
            sb.append("<Productinfo>");
            sb.append("<customer_id>a0010102</customer_id>");
            sb.append("<product_id> " + product_id + "</product_id>");
            sb.append(" <group_id>"+ group_id + "</group_id>");
            sb.append("<mall_id>"+ mall_id + "</mall_id>");
            sb.append("<user_id>"+ user_id + "</user_id>");
           
            if (Site.equals("BENEPIA"))
			 {
            	sb.append("<currently_sale_price>"+ SALEPRICE + "</currently_sale_price>");
            	sb.append("<currently_supply_price>"+ SUPPLY_PRICE + "</currently_supply_price>");
            	sb.append("<currently_market_price>"+ SALEPRICE + "</currently_market_price>");
            	sb.append("<currently_model>"+  bookInfo.get_PUBLISHER() + "</currently_model>");
            	sb.append("<currently_brand>"+ bookInfo.get_PUBLISHER() + "</currently_brand>");
            	sb.append("<currently_product_name>"+ bookInfo.get_BOOK_NM() + "</currently_product_name>");
            
            	  if (bookInfo.get_BOOK_WT_VAL().trim().equals(""))
                  {
                      sb.append("<weight>상세참조</weight>");
                  }
                  else
                  {
                      sb.append("<weight>" + bookInfo.get_BOOK_WT_VAL().replace("*", "##") + "</weight>");
                  }

            	  
            	sb.append("<category_gubun>001</category_gubun>");
            	 sb.append("<goodsinfo>");
            	   sb.append("<lclass_id>i26</lclass_id>");
                   sb.append("<item>");
                   sb.append("<item_seq>2601</item_seq>");
                   sb.append("<item_info><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></item_info>");
                   sb.append("</item>");
                   sb.append("<item>");
                   sb.append("<item_seq>2602</item_seq>");
                   sb.append("<item_info><![CDATA[" + bookInfo.get_AUTHR() + "##" + bookInfo.get_PUBLISHER() + "]]></item_info>");
                   sb.append("</item>");
                   sb.append("<item>");
                   sb.append("<item_seq>2603</item_seq>");

                   if (bookInfo.get_BOOK_WT_VAL().trim().equals(""))
                   {
                       sb.append("<item_info><![CDATA[상세참조]]></item_info>");
                   }
                   else
                   {
                       sb.append("<item_info><![CDATA[" + bookInfo.get_BOOK_WT_VAL().replace("*", "##") + "]]></item_info>");
                   }

                   sb.append("</item>");
                   sb.append("<item>");
                   sb.append("<item_seq>2604</item_seq>");
                   if (bookInfo.get_BOOK_PAGE_VAL().trim().equals(""))
                   {
                       sb.append("<item_info><![CDATA[상세참조]]></item_info>");
                   }
                   else
                   {
                       sb.append("<item_info><![CDATA["+ bookInfo.get_BOOK_PAGE_VAL() + "]]></item_info>");
                   }

                   sb.append("</item>");
                   sb.append("<item>");
                   sb.append("<item_seq>2605</item_seq>");
                   sb.append("<item_info><![CDATA[상품상세참조]]></item_info>");
                   sb.append("</item>");
                   sb.append("<item>");
                   sb.append("<item_seq>2606</item_seq>");
                   sb.append("<item_info><![CDATA[" + bookInfo.get_OPENDATE() + "]]></item_info>");
                   sb.append("</item>");
                   sb.append("<item>");
                   sb.append("<item_seq>2607</item_seq>");
                   sb.append("<item_info><![CDATA[상품상세참조]]></item_info>");
                   sb.append("</item>");
                 sb.append("</goodsinfo>");
            	
			 }//else if (Site.equals("FARM")) {
			//	 sb.append("<category_gubun>002</category_gubun>");
			// }
            else {
				 sb.append("<category_gubun>001</category_gubun>");	 
			 }
            
            if (Site.equals("SMCARD"))
            {
            	 SUPPLY_PRICE = Math.round( Integer.parseInt(bookInfo.get_PRICE()) - (Integer.parseInt(bookInfo.get_PRICE()) * 0.165) );
            	
            	 int SMCARD_SUPPLY = (int) Math.round((int)(SUPPLY_PRICE)/10.0)*10; 
            	 double _round= Math.round(SMCARD_SUPPLY/100d)*100;
            	 
            	sb.append("<currently_sale_price>"+ Double.parseDouble(bookInfo.get_PRICE()) + "</currently_sale_price>");
            	sb.append("<currently_supply_price>"+ (int)_round + "</currently_supply_price>");
            	sb.append("<currently_market_price>"+ Double.parseDouble(bookInfo.get_PRICE()) + "</currently_market_price>");
            	sb.append("<currently_model>도서</currently_model>");
            	sb.append("<currently_brand>도서</currently_brand>");
            }
            
            
            if (Site.equals("TMON"))
            {
            	sb.append("<currently_sale_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "") + "</currently_sale_price>");
            	//sb.append("<currently_supply_price>"+ bookInfo.get_PRICE() + "</currently_supply_price>");
            	//sb.append("<currently_market_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "") + "</currently_market_price>");
            }
            
            if (Site.equals("LOTTE"))
            {
            	sb.append("<currently_sale_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "")  + "</currently_sale_price>");
            	//sb.append("<currently_supply_price>"+ bookInfo.get_PRICE() + "</currently_supply_price>");
            	//sb.append("<currently_market_price>"+ SALEPRICE + "</currently_market_price>");
            }
            
            if (Site.equals("SSG"))
            {
            	sb.append("<currently_sale_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "")  + "</currently_sale_price>");
            	//sb.append("<currently_supply_price>"+ bookInfo.get_PRICE() + "</currently_supply_price>");
            	//sb.append("<currently_market_price>"+ SALEPRICE + "</currently_market_price>");
            }
            
            if (Site.equals("EMART"))
            {
            	sb.append("<currently_product_name>"+ bookInfo.get_BOOK_NM() +" / " +bookInfo.get_PUBLISHER()+ "</currently_product_name>");
            	sb.append("<currently_sale_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "")  + "</currently_sale_price>");
            	//sb.append("<currently_supply_price>"+ bookInfo.get_PRICE() + "</currently_supply_price>");
            	//sb.append("<currently_market_price>"+ SALEPRICE + "</currently_market_price>");
            }
            if (Site.equals("WMP"))
            {
            	sb.append("<currently_sale_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "") + "</currently_sale_price>");
            	//sb.append("<currently_supply_price>"+ bookInfo.get_PRICE() + "</currently_supply_price>");
            	//sb.append("<currently_market_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "") + "</currently_market_price>");
            }
            if (Site.equals("AK"))
            {
            	sb.append("<currently_sale_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "") + "</currently_sale_price>");
            	//sb.append("<currently_supply_price>"+ bookInfo.get_PRICE() + "</currently_supply_price>");
            	//sb.append("<currently_market_price>"+ Double.toString(SALEPRICE).replaceAll(" ", "") + "</currently_market_price>");
            }
            
            
            sb.append("  </Productinfo>");
            sb.append("  </ProductMallReg>");

            sb.append(" </Shoplinker>");

            File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\MallProductReg");            
            if (!desti.exists()) desti.mkdirs();
            
             stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\MallProductReg\\" + user_id + "_" + product_id +  ".xml");
             
             System.out.println("상품등록=====>" +  bookInfo.get_ISBN13());
		    	
			   String sVal = getHttpHTML_POST("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/MallProductReg.html",HOST_IP() + "/MallProductReg/" + user_id + "_" + product_id +  ".xml");
			   System.out.println("ResultMessage=====>" +  sVal);
			    
			   //java.util.HashMap<String, String> map_url = new HashMap<String, String>(); 
	            //map_url.put("iteminfo_url","D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\MallProductReg\\" + user_id + "_" + product_id +  ".xml");
	            //String sVal = getHttpPostData("https://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/MallProductReg.html",map_url); 

	              
			 Document doc = toXmlDocument(sVal);
			
			 NodeList list = doc.getElementsByTagName("ResultMessage");
			 if (list !=null)
			 {
				 String result="";
				 String Mall_product_id="";
				 String message ="";
				
				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
					 if(node.getNodeName().equals("result")) {
						 result = node.getTextContent();
					 }
					 if(node.getNodeName().equals("product_id")) {
						 Mall_product_id = node.getTextContent().replace("<^**^>", "/");
						 }
					 if(node.getNodeName().equals("message")) {
						 message = node.getTextContent();
						 }
					 
			       }
				
				 if (result.equals("true"))
		            {
		                Mall_product_id = Mall_product_id.split( "/")[0];
		                
		                GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
						 java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
						 map.put("BENEPIA_PRODUCT_ID", Mall_product_id);
						 map.put("FARM_PRODUCT_ID", Mall_product_id);
						 map.put("PRODUCT_ID", product_id);
						 
						 if (Site.equals("BENEPIA"))
						 {
							 goodsDAO.RegMallProduct_id_Benepia(map);
						 }else if (Site.equals("FARM")) {
							 
							 goodsDAO.RegMallProduct_id_Farm(map);
						 }else if (Site.equals("GMARKET")) {
							 
							 goodsDAO.RegMallProduct_id_GMARKET(map);
						 }else if (Site.equals("TMON")) {
							 
							 goodsDAO.RegMallProduct_id_TMON(map);
						 }else if (Site.equals("LOTTE")) {
							 
							 goodsDAO.RegMallProduct_id_LOTTE(map);
						 }else if (Site.equals("EMART")) {
							 
							 goodsDAO.RegMallProduct_id_EMART(map);
						 }else if (Site.equals("SSG")) {
							 
							 goodsDAO.RegMallProduct_id_SSG(map);
						 }else if (Site.equals("SMCARD")) {
							 
							 goodsDAO.RegMallProduct_id_SMCARD(map);
						 }else if (Site.equals("WMP")) {
							 
							 goodsDAO.RegMallProduct_id_WMP(map);
						 }else if (Site.equals("AK")) {
							 
							 goodsDAO.RegMallProduct_id_AK(map);
						 }
		            }
		            else
		            {	
		            	InsertError(Site,"RegGoods",bookInfo.get_ISBN13(),"",message);
		            }
		           
			 }
         
           


        }
        catch (Exception ex)
        {
            //WriteTextLog("GoodsIteminfo Send Xml", ex.Message.ToString(), DateTime.Now.ToString("yyyy-MM-dd"));
        }
       
    }
	public void InsertError(String Site,String ERROR_GUBUN,String ISBN13,String ITEMCD,String MSG)
	{
		java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
		 map.put("SITE", Site);
		 map.put("ERROR_GUBUN", ERROR_GUBUN);
		 map.put("ISBN13", ISBN13);
		 map.put("ITEMCD", ITEMCD);
		 map.put("MSG", MSG);
		 
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());			
		goodsDAO.InsertError(map);
	}
	
	public void RegGoodsAPI(Goods bookInfo) {
		try
		{
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());					
			Date date = Calendar.getInstance().getTime();
			
			java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>(); 
			String StrCateGory ="";
			if (bookInfo.get_BX_CATECD()== null)
			{
				StrCateGory ="010101";
			}else
			{
				
				Result_map = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());
				
				if (Result_map==null)
				{
					StrCateGory ="010101";
				}else {
					Iterator iter = Result_map.entrySet().iterator();
					
					while (iter.hasNext()) {

					    Entry entry = (Entry) iter.next();

					    StrCateGory = entry.getValue().toString();

					}
		
				}
				
				
			}
			
			if (StrCateGory.equals(""))
			{
				
				StrCateGory ="010101";
			}
			
            String Cat1 = "";
            String Cat2 = "";
            String Cat3 = "";
            String Cat4 = "";
            
            Cat1 = StrCateGory.length() > 1 ? StrCateGory.substring(0, 2) : "01";
            Cat2 = StrCateGory.length() > 3 ? StrCateGory.substring(0, 4) : "";
            Cat3 = StrCateGory.length() > 5 ? StrCateGory.substring(0, 6) : "";
            Cat4 = StrCateGory.length() > 7 ? StrCateGory.substring(0, 8) : "";
            
           
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
            sb.append("<openMarket>");
            sb.append("<MessageHeader>");
            sb.append("<sendID>1</sendID>");
            sb.append("<senddate>" + ex1(date, "yyyyMMdd") + "</senddate>");
            sb.append(" </MessageHeader>");
            sb.append(" <product>");
            sb.append(" <customer_id>a0010102</customer_id>");
            sb.append(" <partner_product_id>" + bookInfo.get_ISBN13() + "</partner_product_id>");

            sb.append(" <product_name><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></product_name>");


            sb.append(" <sale_status>001</sale_status>");                    
          
            sb.append(" <ccategory_l><![CDATA[" + Cat1 + "]]></ccategory_l>");
            sb.append(" <ccategory_m><![CDATA[" + Cat2 + "]]></ccategory_m>");
            sb.append(" <ccategory_s><![CDATA[" + Cat3 + "]]></ccategory_s>");
            sb.append(" <ccategory_d><![CDATA[" + Cat4 + "]]></ccategory_d>");



            sb.append(" <maker><![CDATA[" + bookInfo.get_PUBLISHER() + "]]></maker>");
            sb.append(" <maker_dt>" + bookInfo.get_OPENDATE().replace("-", "")  + "</maker_dt>");
            sb.append(" <origin><![CDATA[APIORIGIN_031]]></origin>");
            sb.append(" <image_url num='1'><![CDATA[" + bookInfo.get_IMG_PATH2() + "]]></image_url>");
            sb.append(" <image_url num='2'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='3'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='4'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='5'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='6'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='7'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='8'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='9'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='10'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='11'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='12'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='13'><![CDATA[]]></image_url>");
            sb.append(" <image_url num='14'><![CDATA[]]></image_url>");

            double MARKETPRICE =  Double.parseDouble(bookInfo.get_PRICE());
            double SALEPRICE = 0;


            if ( Double.parseDouble(bookInfo.get_INRT()) > 80) return;

            if (Double.parseDouble(bookInfo.get_INRT()) <= 70)
            {
            	SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
 				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
 				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
 				SALEPRICE = incm_civil_amt.doubleValue();
                       
            }
            else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76)
            {                
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
              

            }
            else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81)
            {
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
            }
            else
            {
                SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
            }


            sb.append(" <start_price>" + (int)SALEPRICE + "</start_price>");
            sb.append(" <market_price>" + (int)MARKETPRICE + "</market_price>");
            sb.append(" <sale_price>" + (int)SALEPRICE + "</sale_price>");
            sb.append(" <supply_price>" + (int)MARKETPRICE + "</supply_price>");
            sb.append(" <market_price_p>" + (int)MARKETPRICE + "</market_price_p>");
            sb.append(" <sale_price_p>" + (int)SALEPRICE + "</sale_price_p>");
            sb.append(" <supply_price_p>" + (int)MARKETPRICE + "</supply_price_p>");


            sb.append(" <delivery_charge_type><![CDATA[003]]></delivery_charge_type>");
            sb.append(" <delivery_charge>2500</delivery_charge>");
            sb.append(" <tax_yn>002</tax_yn>");
     
            sb.append(" <detail_desc><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></detail_desc>");
            sb.append(" <new_desc_top><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></new_desc_top>");

            sb.append(" <new_desc_center><![CDATA[]]></new_desc_center>");
            sb.append(" <new_desc_bottom><![CDATA[]]></new_desc_bottom>");
            sb.append(" <desc_type_tag><![CDATA[]]>.</desc_type_tag>");
            sb.append(" <quantity>999</quantity>");
            sb.append(" <salearea><![CDATA[001]]></salearea>");
            sb.append(" <partner_id_tmp><![CDATA[]]></partner_id_tmp>");
            sb.append(" <sex></sex>");
            sb.append(" <keyword><![CDATA[]]></keyword>");
            sb.append(" <model><![CDATA[]]></model>");
            sb.append(" <model_no><![CDATA[isbn-" + bookInfo.get_ISBN13() + "]]></model_no>");
         
            sb.append(" <option_kind>000</option_kind>");
            sb.append(" <brand><![CDATA[웅진북센 / WJBOOXEN]]></brand>");
            sb.append(" <auth_no></auth_no>");
            sb.append(" <expirydate></expirydate>");
            sb.append(" <trans_product_id><![CDATA[]]></trans_product_id>");
            sb.append("  </product>");
            sb.append(" </openMarket>");
            
            String sVal ="";
            
            File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\xmlInsert\\");            
            if (!desti.exists())
            {
            	desti.mkdirs();
            }
            
             stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\xmlInsert\\" + bookInfo.get_ISBN13()+".xml");
             
			 sVal = getHttpHTML_POST("http://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/xmlInsert.php",HOST_IP() + "/xmlInsert/" + bookInfo.get_ISBN13() + ".xml");
             //java.util.HashMap<String, String> map_url = new HashMap<String, String>(); 
             //map_url.put("iteminfo_url", HOST_IP() + "/xmlInsert/" + bookInfo.get_ISBN13() + ".xml");
			 
             //sVal = getHttpPostData("http://apiweb.shoplinker.co.kr/ShoplinkerApi/Product/xmlInsert.php",map_url); 
			 
			 Document doc = toXmlDocument(sVal);
			
			 NodeList list = doc.getElementsByTagName("ResultMessage");
			 if (list !=null)
			 {
				 String result="";
				 String product_id="";
				 String message="";
				 for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
					 if(node.getNodeName().equals("result")) {
						 result = node.getTextContent();
					 }
					 if(node.getNodeName().equals("product_id")) {
						 product_id = node.getTextContent();
						 }
					 if(node.getNodeName().equals("message")) {
						 message = node.getTextContent();
						 }
			       }
				 
				 String RtnMsg="";
				 String Site ="";
				 if (result.equals("true"))
				 {	
					 try
					 {
						 RtnMsg = GoodsItemInformationSendXml(product_id, bookInfo);
						 
						 if (RtnMsg.equals("true")) {
							 
							 java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
							 map.put("YYYYMMDD", ex1(date, "yyyyMMdd"));
							 map.put("ITEMCD", bookInfo.get_ITEMCD());
							 map.put("PRODUCT_ID", product_id);
							 map.put("ISBN13", bookInfo.get_ISBN13());
							 map.put("BOOK_NM", bookInfo.get_BOOK_NM());
							 map.put("BOOKSTS", bookInfo.get_BOOKSTS());
							 map.put("PRICE", bookInfo.get_PRICE());
							 map.put("SALEPRICE", bookInfo.get_SALEPRICE());
							 
							 
						 int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());
						 
						 if (Pcnt < 1)
						 {
							 goodsDAO.InsertRegItem(map);
							 
							  //1.스토어팜
							    if (bookInfo.get_FARM_PRODUCT_ID() == null || bookInfo.get_FARM_PRODUCT_ID().equals(""))
							    {
							      System.out.println("=====1.스토어팜=====" + bookInfo.get_BOOK_NM());
							    	MallProductReg(bookInfo, product_id, "APISHOP_0148", "dasoda11", "00000020","FARM");   	
							    }
							 
							 
							 //2.베네피아
							 if (bookInfo.get_BENEPIA_PRODUCT_ID() == null || bookInfo.get_BENEPIA_PRODUCT_ID().equals(""))
							    {
								 System.out.println("=====2.베네피아=====" + bookInfo.get_BOOK_NM());
								 MallProductReg(bookInfo, product_id, "APISHOP_0276", "booxen", "00000019","BENEPIA");   	
							    }
							 
							 
							 
							 //3.지마켓	
							 /*
				    	      if (bookInfo.get_GMARKET_PRODUCT_ID() == null || bookInfo.get_GMARKET_PRODUCT_ID().equals(""))
				    	      {
				    	    	  System.out.println("=====3.지마켓=====" + bookInfo.get_BOOK_NM());
				    	    	  MallProductReg(bookInfo, product_id, "APISHOP_0010", "booxen4", "00000026","GMARKET"); 
				    	      }
				    	      
				    	      if (bookInfo.get_TMON_PRODUCT_ID() ==null || bookInfo.get_TMON_PRODUCT_ID().equals(""))
				    	      {
				    	    	      System.out.println("=====4.티몬=====" + bookInfo.get_BOOK_NM());
								     MallProductReg(bookInfo, product_id, "APISHOP_0182", "booxen1", "00000025","TMON");
								 
				    	      }
				    	      
				    	      if ((bookInfo.get_LOTTE_PRODUCT_ID() == null) || (bookInfo.get_LOTTE_PRODUCT_ID().equals("")))
				                {
				    	    	  System.out.println("=====5.롯데=====" + bookInfo.get_BOOK_NM());
				    	    	  
				    	    	  if (bookInfo.get_ISBN13().substring(0,1).equals("9"))
				    	    	  {
				    	    		  MallProductReg(bookInfo, product_id, "APISHOP_0321", "LD477577", "00000037", "LOTTE");
				    	    	  }else {
				    	    		  MallProductReg(bookInfo, product_id, "APISHOP_0321", "LD477577", "00000040", "LOTTE");  
				    	    	  }
				                  
				                }
						      */
				    	      /*
				    	      if (bookInfo.get_EMART_PRODUCT_ID() ==null || bookInfo.get_EMART_PRODUCT_ID().equals(""))
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //이마트
				    	    		  System.out.println("=====6.이마트=====" + bookInfo.get_BOOK_NM());
									     MallProductReg(bookInfo, product_id, "APISHOP_0063", "0024774739", "00000035","EMART");
											 
									 }
								 
				    	      }	
				    	      
				    	      if (bookInfo.get_SSG_PRODUCT_ID() ==null || bookInfo.get_SSG_PRODUCT_ID().equals(""))
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //신세계
				    	    		  System.out.println("=====7.신세계=====" + bookInfo.get_BOOK_NM());
									     MallProductReg(bookInfo, product_id, "APISHOP_0170", "0024774739", "00000029","SSG");
											 
									 }
								 
				    	      }	
				    	      */
				    	      /*
				    	      if (bookInfo.get_SMCARD_PRODUCT_ID() ==null || bookInfo.get_SMCARD_PRODUCT_ID().equals(""))
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //삼성카드
				    	    		  System.out.println("=====8.삼성카드=====" + bookInfo.get_BOOK_NM());
									     MallProductReg(bookInfo, product_id, "APISHOP_0231", "booxen01", "00000008","SMCARD");
											 
									 }
								 
				    	      }	
				    	      */
				    	      if (bookInfo.get_WMP_PRODUCT_ID()==null || bookInfo.get_WMP_PRODUCT_ID().equals(""))
				    	      {
				    	    		 //6.WMP	
				    	    	  System.out.println("=====9.SMP=====" + bookInfo.get_BOOK_NM());
				    	    		//  MallProductReg(bookInfo, product_id, "APISHOP_0287", "booxen2020", "00000036","WMP");
									
								 
				    	      }
							 /*
				    	      if (bookInfo.get_AK_PRODUCT_ID()==null || bookInfo.get_AK_PRODUCT_ID().equals(""))
				    	      {
				    	    		 //7.마	
				    	    	  System.out.println("=====10.AK몰=====" + bookInfo.get_BOOK_NM());
				    	    		  MallProductReg(bookInfo, product_id, "APISHOP_0033", "AKM1418519641", "00000041","AK");
									
								 
				    	      }
							 */
				    	      
						 }else
						 {
							 System.out.println("=====1.스토어팜=====" + bookInfo.get_BOOK_NM());
							 //1.스토어팜
							 MallProductReg(bookInfo, product_id, "APISHOP_0148", "dasoda11", "00000020","FARM");
							 System.out.println("=====2.베네피아=====" + bookInfo.get_BOOK_NM());
							 //2.베네피아
					         MallProductReg(bookInfo, product_id, "APISHOP_0276", "booxen", "00000019","BENEPIA");
					       
					       //3.지마켓
				    	      
				    	      if (bookInfo.get_GMARKET_PRODUCT_ID() == null || bookInfo.get_GMARKET_PRODUCT_ID().equals(""))
				    	      {
				    	    	  System.out.println("=====3.지마켓=====" + bookInfo.get_BOOK_NM());
				    	    	  MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0010", "booxen4", "00000026","GMARKET"); 
				    	      }
				    	      /*
				    	      if (bookInfo.get_TMON_PRODUCT_ID() ==null || bookInfo.get_TMON_PRODUCT_ID().equals(""))
				    	      {
				    	    	  if ( Double.parseDouble(bookInfo.get_INRT()) <= 70)
									 {
										 //4.티몬
				    	    		  System.out.println("=====4.티몬=====" + bookInfo.get_BOOK_NM());
									     MallProductReg(bookInfo, bookInfo.get_PRODUCT_ID(), "APISHOP_0182", "booxen1", "00000025","TMON");
											 
									 }
								 
				    	      }
				    	      */
				    	     
						 }
							/*
							if (goodsDAO.InsertRegItem(map) > 0)
							{
								
								if (Integer.parseInt( bookInfo.get_INRT()) <=65)
								{
									Site ="Farm";
									 //1.스토어팜
	                                MallProductReg(bookInfo, product_id, "APISHOP_0148", "dasoda11", "00000020",Site);									
								}
								
								if (Integer.parseInt( bookInfo.get_INRT()) <=75)
								{
									Site ="Benepia";

		                               //2.베네피아 상품등록 전송
		                            MallProductReg(bookInfo, product_id, "APISHOP_0276", "booxen", "00000019",Site);
	
								}
								
							}
							*/
						 }
						 
					 }catch (Exception e) {
						 InsertError("SHAPLINKER","RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());						 
					}
					 
					 			 
				 }else {
					
				}	 
			 }
            
            
		}catch(Exception e)
		{
			
		}
		
		
	}
	
}



