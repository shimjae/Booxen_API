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
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
import java.io.StringWriter;
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


public class Booxen_Daiso {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			Date current = new Date();
			long current_time = current.getTime()/1000;
			
			// 상품등록
			//시작
			try {
				/*
				System.out.println("=====송장입력 start=====");
				//송장입력
		    	try {
		    		 work wk = new work();
		    		wk.GetTransNoList("50944"); //
		    		
		    	}catch (Exception e) {
					// TODO: handle exception
				}
		    	
		    	System.out.println("=====송장입력 end=====");
		    	  */
		    	  
			   
			
				System.out.println("=====주문 start=====");
				   
			    try {
			    	 work wk = new work();
			    	 //wk.Get_OrderInfo();
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
						if (Pcnt < 1 && (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))) 
						{
							
							
							
							   if (bookInfo.get_ISBN13().substring(0,1).equals("9"))
						        {
								   System.out.println("ISBN13: " + bookInfo.get_ISBN13());

								   work wk = new work();
								   wk.RegGoodsAPI(bookInfo);    	 
						       }
							
							
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
							//bookInfo.set_BOOKSTS("품절");
							wk.UptStatusAPI(bookInfo);
							
							 int min = 1;

							int max = 2000000000;


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
							//bookInfo.set_SALEPRICE("10000");
							wk.UptSalePriceAPI(bookInfo);
							
						}
					}
				} catch (Exception e) {
				}
				System.out.println("=====상품가격수정 End=====");
				
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
		 sb.append("<root>");
         sb.append("<header>");
         sb.append("<accesskey><![CDATA[1ea729e35704003a15bfc7978306040e]]></accesskey>");            
         sb.append("</header>");
         sb.append("<item>");
         sb.append("<sdate><![CDATA[20230101]]></sdate>");
         sb.append("<edate><![CDATA[20231001]]></edate>");
         sb.append("<status><![CDATA[IC]]></status>");
         sb.append("</item>");
         sb.append("</root>");

        
      
        String sVal = getHttpHTML("http://www.daisomall.co.kr/openapi/mainApi/orderService/orderList.php",sb.toString());
		
        
	}
	
	public String getHttpHTML(String urlStr,String sxml) {
		String sReturn = "";
		try {
			HttpURLConnection con;
			sxml = "?data=" + URLEncoder.encode(sxml, "utf-8");
				URL myurl = new URL(urlStr + sxml);
	            con = (HttpURLConnection) myurl.openConnection();

	            con.setRequestMethod("GET");

	            StringBuilder content;

	            try (BufferedReader in = new BufferedReader(
	                    new InputStreamReader(con.getInputStream()))) {

	                String line;
	                content = new StringBuilder();

	                while ((line = in.readLine()) != null) {

	                    content.append(line);
	                    content.append(System.lineSeparator());
	                }
	            }

	            System.out.println(content.toString());

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
	
public void UptSalePriceAPI(Goods bookInfo) {
		
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
		bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
		String sRtn = GetXml_SalePrice(bookInfo);
		
		sRtn = sRtn.replace("&", "");
			
		if (!"".equals(sRtn)) {
			String sVal = "";						
			sVal = getHttpHTML_POST_Upt(sRtn, "http://api.cjmall.com/IFPAServerAction.action",bookInfo);				

		}
		
	}catch (Exception e) {
		// TODO: handle exception
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
				
			if (!"".equals(sRtn)) {
				String sVal = "";			
				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Upt(sRtn, "http://api.cjmall.com/IFPAServerAction.action",bookInfo);				

			}
			
		}catch (Exception e) {
			// TODO: handle exception
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

			String sRtn = GetXml(bookInfo, SALEPRICE);
			System.out.println(sRtn);
			
				if (!"".equals(sRtn)) {
				String sVal = "";
				
				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = getHttpHTML_POST_Reg(sRtn, "http://www.daisomall.co.kr/openapi/mainApi/prodService/productInsert.php", bookInfo);						
				
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
	public String GetXml(Goods bookInfo, double SALEPRICE) throws ParserConfigurationException {
		//최종 완성될 JSONObject 선언(전체)

		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		Date time = new Date();
				
		String time1 = format1.format(time);
		String sCate = GetCategory(bookInfo);
        StringBuilder sb = new StringBuilder();
        
        
        String charset = "euc-kr";
        bookInfo.set_BOOK_NM(StringReplace(bookInfo.get_BOOK_NM()) );
        

		int prdDiscount = (int) Math.round(SALEPRICE * 0.87);
		
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// 루트 엘리먼트
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("root");
		doc.appendChild(rootElement);
		
		Element header = doc.createElement("header");
			
		Element accesskey = doc.createElement("accesskey");
		accesskey.appendChild(doc.createCDATASection("1ea729e35704003a15bfc7978306040e"));
		header.appendChild(accesskey);
		rootElement.appendChild(header);
		
		Element item = doc.createElement("item");
		
		Element pid = doc.createElement("pid");
		pid.appendChild(doc.createCDATASection("0"));
		
		Element co_pid = doc.createElement("co_pid");
		co_pid.appendChild(doc.createCDATASection(bookInfo.get_ISBN13()));
		
		Element pcode = doc.createElement("pcode");
		pcode.appendChild(doc.createCDATASection(bookInfo.get_ISBN13()));
		

		Element cid = doc.createElement("cid");
		cid.appendChild(doc.createCDATASection(sCate));
		
		Element state = doc.createElement("state");
		state.appendChild(doc.createCDATASection("6"));
		
		Element pname = doc.createElement("pname");
		pname.appendChild(doc.createCDATASection(bookInfo.get_BOOK_NM()));
		
		Element coprice = doc.createElement("coprice");
		coprice.appendChild(doc.createCDATASection(String.valueOf(Integer.valueOf(prdDiscount)) ));
		
		Element listprice = doc.createElement("listprice");
		listprice.appendChild(doc.createCDATASection(String.valueOf((int)SALEPRICE) ));
		
		Element sellprice = doc.createElement("sellprice");
		sellprice.appendChild(doc.createCDATASection(String.valueOf((int)SALEPRICE) ));
		
		Element basicinfo = doc.createElement("basicinfo");
		basicinfo.appendChild(doc.createCDATASection(GetGoodsBody_New(bookInfo)));
		
		Element m_basicinfo = doc.createElement("m_basicinfo");
		m_basicinfo.appendChild(doc.createCDATASection(GetGoodsBody_New(bookInfo)));
		
		Element bimg = doc.createElement("bimg");
		bimg.appendChild(doc.createCDATASection(bookInfo.get_IMG_PATH2()));
		
		Element origin = doc.createElement("origin");
		origin.appendChild(doc.createCDATASection("대한민국"));
		Element company = doc.createElement("company");
		company.appendChild(doc.createCDATASection(bookInfo.get_PUBLISHER()));
		Element delivery_type = doc.createElement("delivery_type");
		delivery_type.appendChild(doc.createCDATASection("2"));
		Element delivery_policy = doc.createElement("delivery_policy");
		delivery_policy.appendChild(doc.createCDATASection("2"));
		Element delivery_code = doc.createElement("delivery_code");
		delivery_code.appendChild(doc.createCDATASection("24561"));

		Element stock = doc.createElement("stock");
		stock.appendChild(doc.createCDATASection("9999"));
		
		 Element surtax = doc.createElement("surtax");
		 if (bookInfo.get_ISBN13().substring(0,1).equals("9"))
	     {			
			 surtax.appendChild(doc.createCDATASection("Y"));		   
	     }
	     else
	     {
	       	 surtax.appendChild(doc.createCDATASection("N"));				
	     }
		 Element is_adult = doc.createElement("is_adult");
		 is_adult.appendChild(doc.createCDATASection("0"));
		 Element import_flag = doc.createElement("import_flag");
		 import_flag.appendChild(doc.createCDATASection("N"));
		 Element parallel_import = doc.createElement("parallel_import");
		 parallel_import.appendChild(doc.createCDATASection("N"));
		 Element order_made = doc.createElement("order_made");
		 order_made.appendChild(doc.createCDATASection("N"));
		 Element installation_costs = doc.createElement("installation_costs");
		 installation_costs.appendChild(doc.createCDATASection("N"));	
		 Element mandatory_type = doc.createElement("mandatory_type");
		 mandatory_type.appendChild(doc.createCDATASection("26|1"));	
		 Element make_date = doc.createElement("make_date");
		 make_date.appendChild(doc.createCDATASection(bookInfo.get_OPENDATE().replace("-", "")));
		
		 Element mandatory_infos = doc.createElement("mandatory_infos");
		 Element mandatory_info01 = doc.createElement("mandatory_info");
		 Element code01 = doc.createElement("code");
		 code01.appendChild(doc.createCDATASection("01"));
		 Element desc01 = doc.createElement("desc");
		 desc01.appendChild(doc.createCDATASection(bookInfo.get_BOOK_NM()));
		 
		 mandatory_info01.appendChild(code01);
		 mandatory_info01.appendChild(desc01);
		 mandatory_infos.appendChild(mandatory_info01);
		 
		 Element mandatory_info02 = doc.createElement("mandatory_info");
		 Element code02 = doc.createElement("code");
		 code02.appendChild(doc.createCDATASection("02"));
		 Element desc02 = doc.createElement("desc");
		 desc02.appendChild(doc.createCDATASection(bookInfo.get_AUTHR() + "," + bookInfo.get_PUBLISHER()));
		 
		 mandatory_info02.appendChild(code02);
		 mandatory_info02.appendChild(desc02);
		 mandatory_infos.appendChild(mandatory_info02);
		 
		 Element mandatory_info03 = doc.createElement("mandatory_info");
		 Element code03 = doc.createElement("code");
		 code03.appendChild(doc.createCDATASection("03"));
		 Element desc03 = doc.createElement("desc");
		 desc03.appendChild(doc.createCDATASection("상세페이지 참조"));
		 
		 mandatory_info03.appendChild(code03);
		 mandatory_info03.appendChild(desc03);
		 mandatory_infos.appendChild(mandatory_info03);
		 
		 Element mandatory_info04 = doc.createElement("mandatory_info");
		 Element code04 = doc.createElement("code");
		 code04.appendChild(doc.createCDATASection("04"));
		 Element desc04 = doc.createElement("desc");
		 desc04.appendChild(doc.createCDATASection("상세페이지 참조"));
		 
		 mandatory_info04.appendChild(code04);
		 mandatory_info04.appendChild(desc04);
		 mandatory_infos.appendChild(mandatory_info04);
		 
		 Element mandatory_info05 = doc.createElement("mandatory_info");
		 Element code05 = doc.createElement("code");
		 code05.appendChild(doc.createCDATASection("05"));
		 
		 Element desc05 = doc.createElement("desc");
		 desc05.appendChild(doc.createCDATASection("상세페이지 참조"));
		 
		 mandatory_info05.appendChild(code05);
		 mandatory_info05.appendChild(desc05);
		 mandatory_infos.appendChild(mandatory_info05);
		 
		 Element mandatory_info06 = doc.createElement("mandatory_info");
		 Element code06 = doc.createElement("code");
		 code06.appendChild(doc.createCDATASection("06"));
		 Element desc06 = doc.createElement("desc");
		 desc06.appendChild(doc.createCDATASection(bookInfo.get_OPENDATE().replace("-", "")));
		 
		 mandatory_info06.appendChild(code06);
		 mandatory_info06.appendChild(desc06);
		 mandatory_infos.appendChild(mandatory_info06);
		 
		 Element mandatory_info07 = doc.createElement("mandatory_info");
		 Element code07 = doc.createElement("code");
		 code07.appendChild(doc.createCDATASection("07"));
		 Element desc07 = doc.createElement("desc");
		 desc07.appendChild(doc.createCDATASection("상세페이지 참조"));
		 
		 mandatory_info07.appendChild(code07);
		 mandatory_info07.appendChild(desc07);
		 mandatory_infos.appendChild(mandatory_info07);
		 
		 Element mandatory_info08 = doc.createElement("mandatory_info");
		 Element code08 = doc.createElement("code");
		 code08.appendChild(doc.createCDATASection("08"));
		 Element desc08 = doc.createElement("desc");
		 desc08.appendChild(doc.createCDATASection(bookInfo.get_ISBN13()));
		 
		 mandatory_info08.appendChild(code08);
		 mandatory_info08.appendChild(desc08);
		 mandatory_infos.appendChild(mandatory_info08);
		 
		 
		item.appendChild(pid);
		item.appendChild(co_pid);
		item.appendChild(pcode);
		item.appendChild(cid);
		item.appendChild(state);
		item.appendChild(pname);
		item.appendChild(coprice);
		item.appendChild(listprice);
		item.appendChild(sellprice);
		item.appendChild(basicinfo);
		item.appendChild(m_basicinfo);
		item.appendChild(bimg);
		
		item.appendChild(origin);
		item.appendChild(company);
		item.appendChild(delivery_type);
		item.appendChild(delivery_policy);
		item.appendChild(delivery_code);
		item.appendChild(stock);
		item.appendChild(surtax);
		item.appendChild(is_adult);
		item.appendChild(import_flag);
		item.appendChild(parallel_import);
		item.appendChild(order_made);
		item.appendChild(installation_costs);
		item.appendChild(mandatory_type);
		item.appendChild(make_date);
		item.appendChild(mandatory_infos);
		
		rootElement.appendChild(item);
		
        return DocumentToString(doc);	
	}
	public static String DocumentToString( Document doc )
	  {
	   try
	   {
		   doc.setXmlStandalone(true);
		   
	    StringWriter clsOutput = new StringWriter( );
	    Transformer clsTrans = TransformerFactory.newInstance( ).newTransformer( );
	 
	    clsTrans.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );
	    clsTrans.setOutputProperty( OutputKeys.METHOD, "xml" );
	   // clsTrans.setOutputProperty( OutputKeys.INDENT, "yes" );
	    clsTrans.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
	    //clsTrans.setOutputProperty( OutputKeys.STANDALONE, "yes" );	    
	    clsTrans.transform( new DOMSource( doc ), new StreamResult( clsOutput ) );	    

	    return clsOutput.toString( );
	   }
	   catch( Exception ex )
	   {
	    return "";
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
        sb.append("           <tns:typeCd>01</tns:typeCd>"); //코드구분(01:단품코드,02:판매코드)
        sb.append("           <tns:itemCd_zip>"+bookInfo.get_PRODUCTNO()+"</tns:itemCd_zip>"); //판매 및 단품코드
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
        sb.append("           <tns:typeCd>01</tns:typeCd>"); //코드구분(01:단품코드,02:판매코드)
        sb.append("           <tns:itemCd_zip>"+bookInfo.get_PRODUCTNO()+"</tns:itemCd_zip>"); //판매 및 단품코드
        sb.append("           <tns:chnCls>30</tns:chnCls>"); //가등록채널구분(30:인터넷)
        if (bookInfo.get_BOOKSTS().equals("현매") || bookInfo.get_BOOKSTS().equals("정상"))
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
	 
	 
	 public String getHttpHTML_POST_Reg(String json, String urlStr,Goods bookInfo) {
		 String sReturn = "";
			try {

				URL url = new URL(urlStr); // 호출할 url
				Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
				params.put("data", json);

				StringBuilder postData = new StringBuilder();
				for (Map.Entry<String, Object> param : params.entrySet()) {
					if (postData.length() != 0)
						postData.append('&');
					postData.append(param.getKey());
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

				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) { // response 출력
					response.append(inputLine);
				}
				System.out.println(response.toString());
				in.close();
				sReturn = response.toString();

			} catch (Exception e) {
				// TODO: handle exception
			}

			return sReturn;
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

