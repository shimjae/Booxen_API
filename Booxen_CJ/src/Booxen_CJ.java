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
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

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
import java.io.UnsupportedEncodingException;

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
import java.io.ByteArrayInputStream;
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


public class Booxen_CJ {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			// 상품등록
			//시작
			try {
				
				System.out.println("=====송장입력 start=====");
				//송장입력
		    	try {
		    		 work wk = new work();
		    		wk.GetTransNoList("51714"); //
		    		
		    	}catch (Exception e) {
					// TODO: handle exception
				}
		    	
		    	System.out.println("=====송장입력 end=====");
		    	
		    	  
			  
				System.out.println("=====주문 start=====");
				   
			    try {
			    	 work wk = new work();
			    	
			    	 wk.Get_OrderInfo_New();
			    	
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
						if (Pcnt < 1 ) 
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
				
				System.out.println("=====상품수정  Start=====");
				
				try {

					List<Goods> bookUptSaleList = goodsDAO.GetUptBagicGoodsList();
					for (Goods bookInfo : bookUptSaleList) {
						System.out.println("상품수정 ISBN13: " + bookInfo.get_ISBN13());					
						int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

						if (Pcnt > 0) {
							work wk = new work();
							
							System.out.println("=====상품수정  start=====" + bookInfo.get_ISBN13());
							//bookInfo.set_SALEPRICE("10000");
							wk.UptGoods(bookInfo);
							
						}
					}
				} catch (Exception e) {
				}
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
							//bookInfo.set_BOOKSTS("품절");
							wk.UptStatusAPI(bookInfo);
							
							 int min = 1;

							int max = 2000000000;


						}
					}

					
				}catch (Exception e) {
					// TODO: handle exception
				}
				*/
				System.out.println("=====상품상태수정  end=====");
				
				
				
				//Thread.sleep(100000000);
				//System.exit(0);
				
			} catch (Exception e) {
				e.printStackTrace();
				//ㅎㅐㅂㅗㅏ ㄸㅗㄱㄱㅏㅌㅇㅣ
			}
		
			
		} catch (Exception e) {
		}

	}

}

class work {
	
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

	
	public void GetTransNoList( String sMALL_ID)
	{
		try
		{
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
				String ORD_TMP3 = (String)map.get("ORD_TMP3");
				String ORD_TMP4 = (String)map.get("ORD_TMP4");
				String ORD_TMP5 = (String)map.get("ORD_TMP5");
				
				 StringBuilder sb = new StringBuilder();
			     sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
			     sb.append("   <tns:ifRequest tns:ifId=\"IF_04_04\" xmlns:tns=\"http://www.example.org/ifpa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/ifpa ../IF_04_04.xsd\">");         
			     sb.append("            <tns:vendorId>533130</tns:vendorId>");
			     sb.append("            <tns:vendorCertKey>CJ02225331300</tns:vendorCertKey>");
			     sb.append("        <tns:takeout>");
			     sb.append("           <tns:ordNo>"+ORD_ID+"</tns:ordNo>");
			     sb.append("           <tns:ordGSeq>"+ORD_TMP3+"</tns:ordGSeq>");
			     sb.append("           <tns:ordDSeq>"+ORD_TMP4+"</tns:ordDSeq>");
			     sb.append("           <tns:ordWSeq>"+ORD_TMP5+"</tns:ordWSeq>");
			     sb.append("           <tns:delicompCd>22</tns:delicompCd>");
			     sb.append("           <tns:wbNo>"+TRNS_NO+"</tns:wbNo>");
			     sb.append("           <tns:vendorOrdId>"+ORD_ID+"</tns:vendorOrdId>");
			     sb.append("        </tns:takeout>");
			     sb.append("</tns:ifRequest>");
			        
			     String sVal = getHttpHTML_POST_Trans(sb.toString(), "http://api.cjmall.com/IFPAServerAction.action");
			     
			     if (sVal.equals("true")) {
			    	 String RtnMsg="";
 	 				 java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
 	 				 map1.put("ORD_ID", ORD_ID);
 	 				 map1.put("MALL_ID", MALL_ID);		 					
 					
 					orderDAO.UptTrans(map1);
			     }
			     
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	

	public void Get_OrderInfo() {
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
        sb.append("   <tns:ifRequest tns:ifId=\"IF_04_01\" xmlns:tns=\"http://www.example.org/ifpa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/ifpa ../IF_04_01.xsd\">");         
        sb.append("            <tns:vendorId>533130</tns:vendorId>");
        sb.append("            <tns:vendorCertKey>CJ02225331300</tns:vendorCertKey>");
        sb.append("        <tns:contents>");
        sb.append("           <tns:instructionCls>1</tns:instructionCls>"); //구분
        sb.append("           <tns:wbCrtDt>"+df.format(cal.getTime())+"</tns:wbCrtDt>");                 
        sb.append("        </tns:contents>");
        sb.append("  </tns:ifRequest>");
        
      
        String sVal = getHttpHTML_POST_Order(sb.toString(),"http://api.cjmall.com/IFPAServerAction.action");
     
			
        if (!sVal.equals("")) {
        	try
        	{
        		 Document doc = toXmlDocument(sVal);

     			NodeList list = doc.getElementsByTagName("ns1:instruction");
     			Order Order = new Order();
     		
     			if (list != null && list.getLength() > 0) {
     				for (int i = 0; i < list.getLength(); i++) {

     					Node node = list.item(i);
     					if (node.getNodeType() == Node.ELEMENT_NODE) {
     						Element eElement = (Element) node;     						
     						Order.Set_ORD_ID(getTagValue("ns1:ordNo", eElement));
     						Order.Set_ORD_DATE(getTagValue("ns1:ordNo", eElement).substring(0,8));
     						
     						NodeList nlList_orderInfoData = eElement.getElementsByTagName("ns1:instructionDetail");
							if (nlList_orderInfoData != null && nlList_orderInfoData.getLength() > 0) {
								for (int ix = 0; ix < nlList_orderInfoData.getLength(); ix++) {
									Node OrderInfoValue = nlList_orderInfoData.item(ix);

									if (OrderInfoValue.getNodeType() == Node.ELEMENT_NODE) {
										Element nElement = (Element) OrderInfoValue;
									
										String[] arr_ORD_ID = getTagValue("ns1:ordNo", eElement).split("-");
			     						
			     						Order.Set_ORD_ID(arr_ORD_ID[0]);
			     						Order.Set_ORD_DATE(getTagValue("ns1:ordNo", eElement).substring(0,8));
			     						
			     						String[] arr_addrMoll = getTagValue("ns1:addrMoll", eElement).split("\\|");
			     						
										String ordDtlClsCd = getTagValue("ns1:ordDtlClsCd", eElement).replace(" ", "");
										
										Order.Set_ISBN13(getTagValue("ns1:contItemCd", nElement));
										Order.Set_ORD_TMP3(getTagValue("ns1:ordGSeq", nElement));
										Order.Set_ORD_TMP4(getTagValue("ns1:ordDSeq", nElement));
										Order.Set_ORD_TMP5(getTagValue("ns1:ordWSeq", nElement));
										Order.Set_ORD_NO(String.valueOf(ix+1));
										Order.Set_SELL_QTY(getTagValue("ns1:outwQty", nElement).replace(".0", ""));
										Order.Set_DLV_PRICE(getTagValue("ns1:custDeliveryCost", nElement).replace(".0", ""));
										Order.Set_DLV_POST(getTagValue("ns1:zipno", nElement));
										Order.Set_DLV_ADDR1(arr_addrMoll[0]);
										Order.Set_DLV_ADDR2(arr_addrMoll[1]);											
										Order.Set_DLV_MSG("");
										Order.Set_DLV_NM(getTagValue("ns1:receverNm", nElement));	
										Order.Set_DLV_TEL(getTagValue("ns1:telno", nElement));
										Order.Set_DLV_HP(getTagValue("ns1:cellno", nElement));
										Order.Set_ITEM_NM(getTagValue("ns1:itemName", nElement));
										Order.Set_SELL_AMT(getTagValue("ns1:realSlAmt", nElement).replace(".00", "")); //고객결제가
										
										if (Order.Get_DLV_NM().equals("*"))
										{
											Order.Set_DLV_NM(getTagValue("ns1:custNm", eElement));
										}
										
										if (ordDtlClsCd.equals("주문") && Order.Get_ISBN13().substring(0, 1).equals("9"))
										{
											OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
											java.util.HashMap<String, Object> map = new HashMap<String, Object>();
											map.put("ORD_ID", Order.Get_ORD_ID());
											map.put("ISBN13", Order.Get_ISBN13());
											int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
											
											if (API_MstCnt < 1) {
												
												/*
												String strBooxen = getHttpHTML_Booxen("http://api-b.booxen.com/API/BookSaleInfoOnline", Order.Get_ISBN13());
												JSONParser jsonParser = new JSONParser();
												strBooxen = strBooxen.replace("callback(", "");
												strBooxen = strBooxen.substring(0, strBooxen.length() - 2); 				                
												JSONObject jsonObj_Out = (JSONObject)jsonParser.parse(strBooxen);	
												String RESULTCD = jsonObj_Out.get("RESULTCD").toString();
												
												String OUTRT2 = jsonObj_Out.get("OUTRT2").toString();
												String Price = jsonObj_Out.get("PRICE").toString();
												String ITEM_NM =jsonObj_Out.get("NAME").toString();
												*/
												
												double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
												
												
												java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
												maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
												maping.put("SELLER_ID", "BOOXEN_CJ");
												maping.put("MALL_ID", "53310");
												maping.put("ORD_ID", Order.Get_ORD_ID());
												maping.put("ORD_NO", Order.Get_ORD_NO());
												maping.put("SELL_QTY", Order.Get_SELL_QTY());
												maping.put("SELL_AMT", String.valueOf((int)SELL_AMT));
												maping.put("SELL_TOTAMT", String.valueOf((int)SELL_AMT));
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
												maping.put("ORD_TMP3", Order.Get_ORD_TMP3());
												maping.put("ORD_TMP4", Order.Get_ORD_TMP4());
												maping.put("ORD_TMP5", Order.Get_ORD_TMP5());
												
												
												
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
   		     	
        	}catch (Exception e) {
				// TODO: handle exception
			}
        	 
        }
       
        
	}
	
	
	public void Get_OrderInfo_New() throws ParserConfigurationException, SAXException, IOException {
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        cal.add(Calendar.DATE, -2); 
        
        String DtFrom = df.format(cal.getTime());
        
        cal.add(Calendar.DATE, 2); 
        String DtTo = df.format(cal.getTime());
       
		 StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<tns:ifRequest     tns:ifId=\"IF_07_13\"     xmlns:tns=\"http://www.example.org/ifpa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/ifpa\">");
		sb.append("            <tns:vendorId>533130</tns:vendorId>");
        sb.append("            <tns:vendorCertKey>CJ02225331300</tns:vendorCertKey>");
		sb.append("    <tns:contents>");
		sb.append("        <tns:sschkwordcls>0</tns:sschkwordcls>");
		sb.append("        <tns:sinstDtFrom>"+DtFrom+"</tns:sinstDtFrom>");
		sb.append("        <tns:sinstDtTo>"+DtTo+"</tns:sinstDtTo>");
		sb.append("        <tns:sbeShipYn>1</tns:sbeShipYn>");
		sb.append("        <tns:sdelivClsCd>1</tns:sdelivClsCd>");
		sb.append("        <tns:swbProgCd>1</tns:swbProgCd>");
		sb.append("        <tns:swbOrder>1</tns:swbOrder>");
		sb.append("    </tns:contents>");
		sb.append("</tns:ifRequest>");

        
      
        String sVal = getHttpHTML_POST_Order(sb.toString(),"http://api.cjmall.com/IFPAServerAction.action");
		/*
		File file = new File("D:\\1.xml");
		 InputSource is = new InputSource(new InputStreamReader(new FileInputStream(file), "UTF-8"));
	     
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    Document doc = db.parse(is);
		    String sVal = "";
    */
       
        if (!sVal.equals("")) {
        	try
        	{
        		Document doc = toXmlDocument(sVal);

     			NodeList list = doc.getElementsByTagName("ns1:order");
     			Order Order = new Order();
     		
     			if (list != null && list.getLength() > 0) {
     				for (int i = 0; i < list.getLength(); i++) {

     					Node node = list.item(i);
     					if (node.getNodeType() == Node.ELEMENT_NODE) {
     						Element eElement = (Element) node;  
     						String[] arr_ORD_ID = getTagValue("ns1:ordNo", eElement).split("-");
     						
     						Order.Set_ORD_ID(arr_ORD_ID[0]);
     						Order.Set_ORD_DATE(getTagValue("ns1:ordNo", eElement).substring(0,8));
     						
     						String[] arr_addrMoll = getTagValue("ns1:addrMoll", eElement).split("\\|");
     						
							String ordDtlClsCd = getTagValue("ns1:ordDtlClsCd", eElement).replace(" ", "");
							Order.Set_ISBN13(getTagValue("ns1:contItemCd", eElement));
							Order.Set_ORD_TMP3(arr_ORD_ID[1]);
							Order.Set_ORD_TMP4(arr_ORD_ID[2]);
							Order.Set_ORD_TMP5(arr_ORD_ID[3]);
							Order.Set_ORD_NO(String.valueOf(arr_ORD_ID[1].replace("00", "")));
							Order.Set_SELL_QTY(getTagValue("ns1:outwQty", eElement).replace(".0", ""));
							Order.Set_DLV_PRICE(getTagValue("ns1:custDeliveryCost", eElement).replace(".0", ""));
							Order.Set_DLV_POST(getTagValue("ns1:zipNo", eElement));
							Order.Set_DLV_ADDR1(arr_addrMoll[0]);
							if (arr_addrMoll.length > 1)
							{
								Order.Set_DLV_ADDR2(arr_addrMoll[1]);	
							}else {
								Order.Set_DLV_ADDR2("");
							}
																	
							Order.Set_DLV_MSG("");
							Order.Set_DLV_NM(getTagValue("ns1:receverNm", eElement).equals("") || getTagValue("ns1:receverNm", eElement)== null ?getTagValue("ns1:custNm", eElement):getTagValue("ns1:receverNm", eElement));	
							Order.Set_DLV_TEL(getTagValue("ns1:telNo", eElement));
							Order.Set_DLV_HP(getTagValue("ns1:cellNo", eElement));
							Order.Set_ITEM_NM(getTagValue("ns1:itemName", eElement));							
							Order.Set_SELL_AMT(getTagValue("ns1:realSlAmt", eElement).replace(".00", "")); //고객결제가
							
							if (Order.Get_DLV_NM().equals("*"))
							{
								Order.Set_DLV_NM(getTagValue("ns1:custNm", eElement));
							}
							if (StringReplace(Order.Get_DLV_NM()).trim().equals(""))
							{
								Order.Set_DLV_NM(getTagValue("ns1:custNm", eElement));
							}
							
							
							
							if (ordDtlClsCd.equals("주문") && Order.Get_ISBN13().substring(0, 1).equals("9"))
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
									maping.put("SELLER_ID", "BOOXEN_CJ");
									maping.put("MALL_ID", "51714");
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
									maping.put("ORD_TMP3", Order.Get_ORD_TMP3());
									maping.put("ORD_TMP4", Order.Get_ORD_TMP4());
									maping.put("ORD_TMP5", Order.Get_ORD_TMP5());
									
									
									
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
          
            requestPost.addHeader("content-type", "application/xml");
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
	
public String UptSalePriceAPI(Goods bookInfo) {
		
	double SALEPRICE = 0;
	String sVal = "";	
	try {
		if (Double.parseDouble(bookInfo.get_INRT()) > 75)
			return " ";

		if (Double.parseDouble(bookInfo.get_INRT()) <= 75 && Double.parseDouble(bookInfo.get_PRICE()) < 12000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();			
		} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if ((Double.parseDouble(bookInfo.get_INRT()) >=65 || Double.parseDouble(bookInfo.get_INRT()) < 76)&& Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) >= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) >=65 && Double.parseDouble(bookInfo.get_INRT()) < 70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) >=70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.95;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_PRICE()) >= 28000) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
		}
		
		bookInfo.set_SALEPRICE(String.valueOf((int)SALEPRICE));
		
		String sRtn = GetXml_SalePrice(bookInfo);
			
		if (!"".equals(sRtn)) {
								
			sVal = getHttpHTML_POST(sRtn, "http://api.cjmall.com/IFPAServerAction.action",bookInfo);				

		}
		
	}catch (Exception e) {
		// TODO: handle exception
	}

		return sVal;
	}
public void UptGoods(Goods bookInfo) {
	
	double SALEPRICE = 0;

	try {
		if (Double.parseDouble(bookInfo.get_INRT()) > 75)
			return;

		if (Double.parseDouble(bookInfo.get_INRT()) <= 75 && Double.parseDouble(bookInfo.get_PRICE()) < 12000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();			
		} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if ((Double.parseDouble(bookInfo.get_INRT()) >=65 || Double.parseDouble(bookInfo.get_INRT()) < 76)&& Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) >= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) >=65 && Double.parseDouble(bookInfo.get_INRT()) < 70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) >=70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
			
		} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.95;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else if (Double.parseDouble(bookInfo.get_INRT()) > 75  && Double.parseDouble(bookInfo.get_PRICE()) >= 28000) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.5;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		}else if (Double.parseDouble(bookInfo.get_INRT()) < 76 && Double.parseDouble(bookInfo.get_PRICE()) >= 28000) {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
			BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
			incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
			SALEPRICE = incm_civil_amt.doubleValue();
		} else {
			SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
		}
		
		bookInfo.set_SALEPRICE(String.valueOf((int)SALEPRICE));
		String sRtn = GetXml_Upt(bookInfo, SALEPRICE);
		//String sRtn = GetXml_SalePrice(bookInfo);
			
		if (!"".equals(sRtn)) {
			String sVal = "";						
			sVal = getHttpHTML_POST_Upt(sRtn, "http://api.cjmall.com/IFPAServerAction.action",bookInfo);				

		}
		
	}catch (Exception e) {
		// TODO: handle exception
	}

	
	}
	public String UptStatusAPI(Goods bookInfo) {
		
		double SALEPRICE = 0;
		String sVal = "";		
		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 75)
				return "";

			if (Double.parseDouble(bookInfo.get_INRT()) <= 75 && Double.parseDouble(bookInfo.get_PRICE()) < 12000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();			
			} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if ((Double.parseDouble(bookInfo.get_INRT()) >=65 || Double.parseDouble(bookInfo.get_INRT()) < 76)&& Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) >= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) >=65 && Double.parseDouble(bookInfo.get_INRT()) < 70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) >=70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.95;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_PRICE()) >= 28000) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			}

			//String sRtn = GetXml_Upt(bookInfo, SALEPRICE);
			String sRtn = GetXml_Status(bookInfo);
			
			sRtn = sRtn.replace("&", "");
			
			if (!"".equals(sRtn)) {
						
				bookInfo.set_SALEPRICE(String.valueOf((int)SALEPRICE));
				sVal = getHttpHTML_POST(sRtn, "http://api.cjmall.com/IFPAServerAction.action",bookInfo);				

			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	
		return sVal;
	}
	
	
	public void RegGoodsAPI(Goods bookInfo) {
		double SALEPRICE = 0;

		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 75)
				return;

			if (Double.parseDouble(bookInfo.get_INRT()) <= 75 && Double.parseDouble(bookInfo.get_PRICE()) < 12000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();			
			} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if ((Double.parseDouble(bookInfo.get_INRT()) >=65 || Double.parseDouble(bookInfo.get_INRT()) < 76)&& Double.parseDouble(bookInfo.get_PRICE()) >= 12000 && Double.parseDouble(bookInfo.get_PRICE()) < 13000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) >= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 13000 && Double.parseDouble(bookInfo.get_PRICE()) < 14000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) < 65 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) >=65 && Double.parseDouble(bookInfo.get_INRT()) < 70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) >=70 && Double.parseDouble(bookInfo.get_PRICE()) >= 14000 && Double.parseDouble(bookInfo.get_PRICE()) < 15000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 15000 && Double.parseDouble(bookInfo.get_PRICE()) < 20000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) <= 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 65  && Double.parseDouble(bookInfo.get_PRICE()) >= 20000 && Double.parseDouble(bookInfo.get_PRICE()) < 28000 ) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.95;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 75  && Double.parseDouble(bookInfo.get_PRICE()) >= 28000) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.5;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			}else if (Double.parseDouble(bookInfo.get_INRT()) < 76 && Double.parseDouble(bookInfo.get_PRICE()) >= 28000) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE())* 0.9;					
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			}

			String sRtn = GetXml(bookInfo, SALEPRICE);
			System.out.println(sRtn);
			
				if (!"".equals(sRtn)) {
				String sVal = "";
				
				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Reg(sRtn, "http://api.cjmall.com/IFPAServerAction.action", bookInfo);						
				
			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	
	public static String substringByBytes(String str, int beginBytes, int endBytes) {
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
	public String GetXml(Goods bookInfo, double SALEPRICE) {
		//최종 완성될 JSONObject 선언(전체)

		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		Date time = new Date();
				
		String time1 = format1.format(time);
		String sCate = GetCategory(bookInfo);
        StringBuilder sb = new StringBuilder();
        
        
        String charset = "euc-kr";
        
        
        bookInfo.set_BOOK_NM(StringReplace(bookInfo.get_BOOK_NM()) );
        
        String zLocalBolDesc = bookInfo.get_BOOK_NM();
        String itemDesc = bookInfo.get_BOOK_NM();
        String mallItemDesc	= bookInfo.get_BOOK_NM();
        String keyword= bookInfo.get_BOOK_NM();
        String zlocalCcDesc= bookInfo.get_BOOK_NM();
         try {
			int zLocalBolDesc_length = zLocalBolDesc.getBytes(charset).length;
			int itemDesc_length = itemDesc.getBytes(charset).length;
			int mallItemDesc_length = mallItemDesc.getBytes(charset).length;
			int keyword_length = keyword.getBytes(charset).length;
			int zlocalCcDesc_length = zlocalCcDesc.getBytes(charset).length;
			if (zLocalBolDesc_length >= 40)
			{
				zLocalBolDesc = substringByBytes(zLocalBolDesc,0,39);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (itemDesc_length >= 120)
			{
				itemDesc = substringByBytes(itemDesc,0,119);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (mallItemDesc_length >= 192)
			{
				mallItemDesc = substringByBytes(mallItemDesc,0,191);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (keyword_length >= 750)
			{
				keyword = substringByBytes(keyword,0,749);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (zlocalCcDesc_length >= 20)
			{
				zlocalCcDesc = substringByBytes(zlocalCcDesc,0,19);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      
        
        sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
        sb.append("   <tns:ifRequest tns:ifId=\"IF_03_01\" xmlns:tns=\"http://www.example.org/ifpa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/ifpa ../IF_03_01.xsd\">");         
        sb.append("            <tns:vendorId>533130</tns:vendorId>");
        sb.append("            <tns:vendorCertKey>CJ02225331300</tns:vendorCertKey>");
        sb.append("        <tns:good>");
        sb.append("           <tns:chnCls>30</tns:chnCls>"); //상품분류체계_가등록채널구분
        sb.append("           <tns:tGrpCd>"+sCate+"</tns:tGrpCd>"); //상품분류체계_상품분류 카테고리
        sb.append("           <tns:uniqBrandCd>T0009001</tns:uniqBrandCd>"); //상품분류체계_브랜드
        sb.append("           <tns:giftInd>Y</tns:giftInd>"); //상품분류체계_상품구분
        sb.append("           <tns:uniqMkrNatCd>KR</tns:uniqMkrNatCd>"); //상품분류체계_제조국
        sb.append("           <tns:uniqMkrCompCd>0000405185S1</tns:uniqMkrCompCd>"); //상품분류체계_제조사
        sb.append("           <tns:itemDesc>"+itemDesc+"</tns:itemDesc>"); //기본정보 - 상품명
        sb.append("           <tns:zLocalBolDesc>" + zLocalBolDesc +"</tns:zLocalBolDesc>" ); //기본정보 - 운송장명
        
        sb.append("           <tns:zlocalCcDesc> "+ zlocalCcDesc+"</tns:zlocalCcDesc>"); //기본정보 - 기본정보_SMS상품명

        if (bookInfo.get_ISBN13().substring(0,1).equals("9"))
        {
            sb.append("           <tns:vatCode>E</tns:vatCode>"); //면세
        }
        else
        {
            sb.append("           <tns:vatCode>S</tns:vatCode>"); //과세
        }
        sb.append("           <tns:zDeliveryType>20</tns:zDeliveryType> "); //기본정보 - 배송구분
        sb.append("           <tns:zShippingMethod>10</tns:zShippingMethod> "); //기본정보 - 배송유형

        sb.append("           <tns:courier>15</tns:courier> "); //기본정보 - 택배사        
        sb.append("           <tns:deliveryHomeCost>2500</tns:deliveryHomeCost> "); //기본정보 - 배송비
        sb.append("           <tns:zCostomMadeInd>P</tns:zCostomMadeInd> "); //기본정보 - 주문제작여부
        
        //sb.append("           <tns:zreturnNotReqInd>10</tns:zreturnNotReqInd> "); //기본정보 - 회수구분        
        //sb.append("           <tns:zJointPackingQty>99</tns:zJointPackingQty> "); //기본정보 - 합포장단위
        
        

        if (SALEPRICE >= 12000) {
			sb.append("<tns:lowpriceInd>N</tns:lowpriceInd>");
		} else {
			sb.append("<tns:lowpriceInd>Y</tns:lowpriceInd>");
		}
  
       // sb.append("<tns:lowpriceInd>Y</tns:lowpriceInd>");  //유료배송여부
        
        sb.append("<tns:delayShipRewardIind>N</tns:delayShipRewardIind>"); //기본정보 - 지연보상여부
        sb.append("<tns:zOrderMaxQty>999</tns:zOrderMaxQty>"); //고객당 1회 최대 주문가능 수량. 미입력시 99개 제한
        sb.append("<tns:zDayOrderMaxQty>999</tns:zDayOrderMaxQty>"); //고객당 일일 최대 주문가능 수량. 미입력시 99개 제한
        sb.append("<tns:reserveDayInd>Y</tns:reserveDayInd>"); //기본정보 - 예약배송방식
        sb.append("<tns:zContactSeqNo>10002</tns:zContactSeqNo>"); //기본정보 - 협력사담당자
         
        sb.append("<tns:zSupShipSeqNo>10005</tns:zSupShipSeqNo>");//기본정보_출하지
        sb.append("<tns:zReturnSeqNo>10005</tns:zReturnSeqNo>");//기본정보_회수지//>
        sb.append("<tns:zAsSupShipSeqNo>10005</tns:zAsSupShipSeqNo>");//기본정보_AS출하지//>
        sb.append("<tns:zAsReturnSeqNo>10005</tns:zAsReturnSeqNo>");//기본정보_AS회수지//>
        sb.append("<tns:certItemRequireYn>N</tns:certItemRequireYn>");//기본정보_인증항목필수여부//>
        sb.append("<tns:delivCostCd>00402406</tns:delivCostCd>");//기본정보_배송비코드//>
        sb.append("<tns:delivCostType>01</tns:delivCostType>");//<!//기본정보_배송비속성코드(01:일반배송,02:배송없음,03:바로사용,04:착불)//>
        
        sb.append("<tns:incomeDedYn>Y</tns:incomeDedYn>");
        sb.append("<tns:isbnNo>"+bookInfo.get_ISBN13()+"</tns:isbnNo>");// isbn

        
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        
        int requestCode =  rnd.nextInt();
        

        
		int prdDiscount = (int) Math.round(SALEPRICE * 0.86);
		
        sb.append("<tns:unit>");
        sb.append("    <tns:unitNm>상품명</tns:unitNm>");//단품정보_단품상세//>
        sb.append("    <tns:unitRetail>"+ (int)SALEPRICE+"</tns:unitRetail>");//단품정보_판매가//>
        sb.append("    <tns:unitCost>"+prdDiscount+"</tns:unitCost>");//단품정보_매입원가//>
        sb.append("    <tns:availableQty>999</tns:availableQty>");//단품정보_공급가능수량//>
        sb.append("    <tns:leadTime>04</tns:leadTime>");//단품정보_리드타임//>
        sb.append("    <tns:unitApplyRsn>30</tns:unitApplyRsn>");//단품정보_적용사유(10:적용안함,20:상품포장,30:상품생산,40:입고검사,50:출고검사,60:설치상품//>
        sb.append("    <tns:startSaleDt>"+time1+"</tns:startSaleDt>");//단품정보_판매시작일자(YYYY-MM-DD)//>
        sb.append("    <tns:endSaleDt>2999-12-31</tns:endSaleDt>");//단품정보_판매종료일자(YYYY-MM-DD)//>
        sb.append("    <tns:vpn>"+bookInfo.get_ISBN13()+"</tns:vpn>");//단품정보_협력사상품코드//>
        sb.append("</tns:unit>");
        
        sb.append("<tns:mallitem>");
        sb.append("<tns:mallItemDesc>"+mallItemDesc+"</tns:mallItemDesc>"); //CJmall상품정보_CJmall상품명-->
        sb.append("<tns:keyword>"+keyword+"</tns:keyword>");//CJmall상품정보_검색키워드-->
        
        sb.append("<tns:mallCtg>");
        sb.append("<tns:mainInd>Y</tns:mainInd>");//메인카테고리여부-->
        sb.append("<tns:ctgName>G01515</tns:ctgName>");//CJmall카테고리(세)-->
        sb.append("</tns:mallCtg>");
        sb.append("</tns:mallitem>");
        
        
        if (bookInfo.get_KC() != null || sCate.substring(0, 6).equals("050304")||sCate.substring(0, 6).equals("050305"))
        {
        	sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>91059</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA["+GetGoodsBody_New(bookInfo)+"]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
            
            
            sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>25202</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA[031-955-2852]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25222</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25225</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25330</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25359</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");

             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25383</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25394</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25395</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25424</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25450</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
           
        }else {
            sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>91059</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA["+GetGoodsBody_New(bookInfo)+"]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
            
            sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>25222</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
            
            
            sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>25202</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA[031-955-2852]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
             /*
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25222</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             */
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25225</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25330</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25359</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");

             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25383</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25394</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25395</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25424</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25450</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
        }
        	
    
       

        sb.append("<tns:image>");
        sb.append("<tns:imageMain>"+ bookInfo.get_IMG_PATH2()+"</tns:imageMain>");
        sb.append("</tns:image>");
        sb.append("</tns:good>");
        sb.append("</tns:ifRequest>");

        if (sb != null)
        {
        	return sb.toString();
        }else {
        	return sb.toString();	
        }
		
	}
	
	public String GetXml_Upt(Goods bookInfo, double SALEPRICE) {
		//최종 완성될 JSONObject 선언(전체)

		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		Date time = new Date();
				
		String time1 = format1.format(time);
		String sCate = GetCategory(bookInfo);
        StringBuilder sb = new StringBuilder();
        
        
        String charset = "euc-kr";
        
        
        bookInfo.set_BOOK_NM(StringReplace(bookInfo.get_BOOK_NM()) );
        
        String zLocalBolDesc = bookInfo.get_BOOK_NM();
        String itemDesc = bookInfo.get_BOOK_NM();
        String mallItemDesc	= bookInfo.get_BOOK_NM();
        String keyword= bookInfo.get_BOOK_NM();
        String zlocalCcDesc= bookInfo.get_BOOK_NM();
         try {
			int zLocalBolDesc_length = zLocalBolDesc.getBytes(charset).length;
			int itemDesc_length = itemDesc.getBytes(charset).length;
			int mallItemDesc_length = mallItemDesc.getBytes(charset).length;
			int keyword_length = keyword.getBytes(charset).length;
			int zlocalCcDesc_length = zlocalCcDesc.getBytes(charset).length;
			if (zLocalBolDesc_length >= 40)
			{
				zLocalBolDesc = substringByBytes(zLocalBolDesc,0,39);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (itemDesc_length >= 120)
			{
				itemDesc = substringByBytes(itemDesc,0,119);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (mallItemDesc_length >= 192)
			{
				mallItemDesc = substringByBytes(mallItemDesc,0,191);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (keyword_length >= 750)
			{
				keyword = substringByBytes(keyword,0,749);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			if (zlocalCcDesc_length >= 20)
			{
				zlocalCcDesc = substringByBytes(zlocalCcDesc,0,19);
				//byte[] bytes = Goods_Name.getBytes();
				//Goods_Name= new String(bytes, 0, 39 );
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      
        
        sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
        sb.append("   <tns:ifRequest tns:ifId=\"IF_03_02\" xmlns:tns=\"http://www.example.org/ifpa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/ifpa ../IF_03_02.xsd\">");         
        sb.append("            <tns:vendorId>533130</tns:vendorId>");
        sb.append("            <tns:vendorCertKey>CJ02225331300</tns:vendorCertKey>");
        sb.append("        <tns:good>");
        sb.append("           <tns:sItem>"+bookInfo.get_PRODUCTNO()+"</tns:sItem>");
        sb.append("           <tns:loc>30</tns:loc>");
        sb.append("           <tns:zLocalBolDesc>" + zLocalBolDesc +"</tns:zLocalBolDesc>" ); //기본정보 - 운송장명        
        sb.append("           <tns:zlocalCcDesc> "+ zlocalCcDesc+"</tns:zlocalCcDesc>"); //기본정보 - 기본정보_SMS상품명

        sb.append("<tns:zContactSeqNo>10002</tns:zContactSeqNo>"); //기본정보 - 협력사담당자         
        sb.append("<tns:zSupShipSeqNo>10005</tns:zSupShipSeqNo>");//기본정보_출하지
        sb.append("<tns:zReturnSeqNo>10005</tns:zReturnSeqNo>");//기본정보_회수지//>
        sb.append("<tns:zAsSupShipSeqNo>10005</tns:zAsSupShipSeqNo>");//기본정보_AS출하지//>
        sb.append("<tns:zAsReturnSeqNo>10005</tns:zAsReturnSeqNo>");//기본정보_AS회수지//>
        sb.append("<tns:certItemRequireYn>N</tns:certItemRequireYn>");//기본정보_인증항목필수여부//>
        sb.append("<tns:delivCostCd>00402406</tns:delivCostCd>");//기본정보_배송비코드//>
        sb.append("<tns:delivCostType>01</tns:delivCostType>");//<!//기본정보_배송비속성코드(01:일반배송,02:배송없음,03:바로사용,04:착불)//>
        
        sb.append("<tns:incomeDedYn>Y</tns:incomeDedYn>");
        sb.append("<tns:isbnNo>"+bookInfo.get_ISBN13()+"</tns:isbnNo>");// isbn

       
        
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        
        int requestCode =  rnd.nextInt();
        

        
		int prdDiscount = (int) Math.round(SALEPRICE * 0.86);
		
        sb.append("<tns:mallitem>");
        sb.append("<tns:mallItemDesc>"+mallItemDesc+"</tns:mallItemDesc>"); //CJmall상품정보_CJmall상품명-->
        sb.append("<tns:keyword>"+keyword+"</tns:keyword>");//CJmall상품정보_검색키워드-->
        
        sb.append("<tns:mallCtg>");
        sb.append("<tns:useYn>Y</tns:useYn>");
        sb.append("<tns:ctgName>G01515</tns:ctgName>");//CJmall카테고리(세)-->
        sb.append("</tns:mallCtg>");
        sb.append("</tns:mallitem>");
        
        
        if (bookInfo.get_KC() != null || sCate.substring(0, 6).equals("050304")||sCate.substring(0, 6).equals("050305"))
        {
        	sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>91059</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA["+GetGoodsBody_New(bookInfo)+"]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
            
            
            sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>25202</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA[031-955-2852]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25222</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25225</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25330</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25359</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");

             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25383</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25394</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25395</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25424</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25450</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
           
        }else {
            sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>91059</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA["+GetGoodsBody_New(bookInfo)+"]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
            
            
            sb.append("<tns:goodsReport>");
            sb.append("<tns:pedfId>25202</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
            sb.append("<tns:html><![CDATA[031-955-2852]]></tns:html>");//상품기술서_기술서상세내역-->
            sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25222</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25225</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25330</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25359</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");

             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25383</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25394</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25395</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25424</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
             sb.append("<tns:goodsReport>");
             sb.append("<tns:pedfId>25450</tns:pedfId>");//상품기술서_기술서상세항목(필수항목은 별첨 확인)-->
             sb.append("<tns:html><![CDATA[상품상세참조]]></tns:html>");//상품기술서_기술서상세내역-->
             sb.append("</tns:goodsReport>");
             
        }
        	

        sb.append("<tns:image>");
        sb.append("<tns:imageMain>"+ bookInfo.get_IMG_PATH2()+"</tns:imageMain>");
        sb.append("</tns:image>");
        sb.append("</tns:good>");
        sb.append("</tns:ifRequest>");
        System.out.println(sb.toString());
    	
        if (sb != null)
        {
        	return sb.toString();
        }else {
        	return sb.toString();	
        }
		
	}
	
	public String GetXml_SalePrice(Goods bookInfo)
	{	
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
		int  SALEPRICE =  Integer.valueOf(bookInfo.get_SALEPRICE());
		int prdDiscount = (int) Math.round(SALEPRICE * 0.86);
		
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");        
        sb.append("   <tns:ifRequest tns:ifId=\"IF_03_04\" xmlns:tns=\"http://www.example.org/ifpa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/ifpa ../IF_03_04.xsd\">");
        sb.append("            <tns:vendorId>533130</tns:vendorId>");
        sb.append("            <tns:vendorCertKey>CJ02225331300</tns:vendorCertKey>");
        sb.append("        <tns:itemPrices>");
        sb.append("           <tns:typeCD>01</tns:typeCD>"); //코드구분(01:판매코드,02:단품코드)
        sb.append("           <tns:itemCD_ZIP>"+bookInfo.get_PRODUCTNO()+"</tns:itemCD_ZIP>"); //판매 및 단품코드
        sb.append("           <tns:chnCls>30</tns:chnCls>"); //가등록채널구분(30:인터넷)
        sb.append("           <tns:effectiveDate>"+df.format(cal.getTime())+"</tns:effectiveDate>"); 
        sb.append("           <tns:newUnitRetail>"+SALEPRICE+"</tns:newUnitRetail>"); //희망판매가격
        sb.append("           <tns:newUnitCost>"+prdDiscount+"</tns:newUnitCost>"); //
        sb.append("        </tns:itemPrices>");
        sb.append("</tns:ifRequest>");
		return sb.toString();
	}
	

	public String GetXml_Status(Goods bookInfo)
	{		
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
        sb.append("   <tns:ifRequest tns:ifId=\"IF_03_03\" xmlns:tns=\"http://www.example.org/ifpa\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/ifpa ../IF_03_03.xsd\">");         
        sb.append("            <tns:vendorId>533130</tns:vendorId>");
        sb.append("            <tns:vendorCertKey>CJ02225331300</tns:vendorCertKey>");
        sb.append("        <tns:itemStates>");
        sb.append("           <tns:typeCd>01</tns:typeCd>"); //코드구분(01:판매코드,02:단품코드)
        sb.append("           <tns:itemCd_zip>"+bookInfo.get_PRODUCTNO()+"</tns:itemCd_zip>"); //판매 및 단품코드
        sb.append("           <tns:chnCls>30</tns:chnCls>"); //가등록채널구분(30:인터넷)
        if (bookInfo.get_BOOKSTS().equals("현매") || bookInfo.get_BOOKSTS().equals("정상")|| bookInfo.get_BOOKSTS().equals("발간예정"))
        {
            sb.append("           <tns:packInd>A</tns:packInd>"); //판매구분(A:진행,I:일시중단)-
        }else {
            sb.append("           <tns:packInd>I</tns:packInd>"); //판매구분(A:진행,I:일시중단)-	
        }
        sb.append("        </tns:itemStates>");
        sb.append("</tns:ifRequest>");
        
		return sb.toString();
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

			StrCateGory = "50040204";
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
	 
	 
	 public String getHttpHTML_POST_Reg(String json, String surl,Goods bookInfo) {
	        //params
	        String method = "POST";
	       
	        String errorMsg ="";
	        CloseableHttpClient client = null;
	        try {
	            //create client
	            client = HttpClients.createDefault();
	            //build uri
	            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
	           
	            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

	            StringEntity params =new StringEntity(json,"UTF-8");
	            
	            // set header, demonstarte how to use hmac signature here
	            //requestPost.addHeader("apiKey", Key);
	          
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
	                
	                
	                Document doc = toXmlDocument(strResult);

					NodeList list = doc.getElementsByTagName("ns1:ifResult");
					
					String successYn ="";
					if (list != null && list.getLength() > 0) {
						for (int i = 0; i < list.getLength(); i++) {

							Node node = list.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								successYn = getTagValue("ns1:successYn", eElement);
								System.out.println(successYn);

							}
						}
					}
					
					if (successYn.equals("true"))
					{
						String productNo = "";
						NodeList list1 = doc.getElementsByTagName("ns1:results");
						if (list1 != null && list1.getLength() > 0) {
							for (int i = 0; i < list1.getLength(); i++) {

								Node node = list1.item(i);
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									Element eElement = (Element) node;
									productNo =getTagValue("ns1:itemCd", eElement);
									System.out.println(productNo);

								}
							}
						}
						
						
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
						
						NodeList list1 = doc.getElementsByTagName("ns1:results");
						if (list1 != null && list1.getLength() > 0) {
							for (int i = 0; i < list1.getLength(); i++) {

								Node node = list1.item(i);
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									Element eElement = (Element) node;
									errorMsg =getTagValue("ns1:errorMsg", eElement);
									System.out.println(errorMsg);

								}
							}
						}
						InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(),errorMsg);
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
	 
	 
	 public String getHttpHTML_POST_Trans(String json, String surl) {

	        //params
	        String method = "POST";
	        String successYn ="";
	       
	        CloseableHttpClient client = null;
	        try {
	            //create client
	            client = HttpClients.createDefault();
	            //build uri
	            URIBuilder uriBuilder = new URIBuilder().setPath(surl);
	           
	            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

	            StringEntity params =new StringEntity(json,"UTF-8");
	            // set header, demonstarte how to use hmac signature here
	           
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
	                
	                
	                Document doc = toXmlDocument(strResult);

					NodeList list = doc.getElementsByTagName("ns1:ifResult");
					
					
					if (list != null && list.getLength() > 0) {
						for (int i = 0; i < list.getLength(); i++) {

							Node node = list.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								successYn = getTagValue("ns1:successYn", eElement);
								System.out.println(successYn);

							}
						}
					}
					
					
					
					System.out.println("=====출고처리  code=====" + successYn);
					
					
					
	               
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
	        
	        return successYn;
	    }
	 
	 public String getHttpHTML_POST_Upt(String json, String surl,Goods bookInfo) {

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
	                
	                
	                Document doc = toXmlDocument(strResult);

					NodeList list = doc.getElementsByTagName("ns1:ifResult");
					
					String successYn ="";
					if (list != null && list.getLength() > 0) {
						for (int i = 0; i < list.getLength(); i++) {

							Node node = list.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								successYn = getTagValue("ns1:successYn", eElement);
								System.out.println(successYn);

							}
						}
					}
					
					
					
					System.out.println("=====상품수정  code=====" + successYn);
					
					if (successYn.equals("true"))
					{
						String sVal = UptStatusAPI(bookInfo);
						
						 doc = toXmlDocument(strResult);

						 list = doc.getElementsByTagName("ns1:ifResult");
						
						
						if (list != null && list.getLength() > 0) {
							for (int i = 0; i < list.getLength(); i++) {

								Node node = list.item(i);
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									Element eElement = (Element) node;
									successYn = getTagValue("ns1:successYn", eElement);
									System.out.println(successYn);

								}
							}
						}
					}
					if (successYn.equals("true"))
					{
						String sVal = UptSalePriceAPI(bookInfo);
						
						 doc = toXmlDocument(strResult);

						 list = doc.getElementsByTagName("ns1:ifResult");
						
						
						if (list != null && list.getLength() > 0) {
							for (int i = 0; i < list.getLength(); i++) {

								Node node = list.item(i);
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									Element eElement = (Element) node;
									successYn = getTagValue("ns1:successYn", eElement);
									System.out.println(successYn);

								}
							}
						}
					}
					if (successYn.equals("true"))
					{
						
						
						java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();						
						map_1.put("ITEMCD", bookInfo.get_ITEMCD());
						map_1.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
						map_1.put("ISBN13", bookInfo.get_ISBN13());
						map_1.put("BOOK_NM", bookInfo.get_BOOK_NM());
						map_1.put("BOOKSTS", bookInfo.get_BOOKSTS());
						map_1.put("SALEPRICE", bookInfo.get_SALEPRICE());
						map_1.put("PRICE", bookInfo.get_PRICE());
						
						GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
   						
   						goodsDAO.UptRegItem(map_1);
						
					}else {
						
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
	 
	 public String getHttpHTML_POST(String json, String surl,Goods bookInfo) {

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
	                strResult = EntityUtils.toString(entity);
		               
	                System.out.println("result:" + strResult);
	                
	                
	                Document doc = toXmlDocument(strResult);

					NodeList list = doc.getElementsByTagName("ns1:ifResult");
					
					String successYn ="";
					if (list != null && list.getLength() > 0) {
						for (int i = 0; i < list.getLength(); i++) {

							Node node = list.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								successYn = getTagValue("ns1:successYn", eElement);
								System.out.println(successYn);

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
	        
	        return strResult;
	    }
	 
	 public String getHttpHTML_POST_Order(String sParam, String surl) {
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

}

