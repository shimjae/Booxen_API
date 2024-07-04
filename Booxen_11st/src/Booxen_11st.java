import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import java.util.Date;



public class Booxen_11st {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			/*
			//상품중지
		    try {

			    List<Goods> bookUptList = goodsDAO.GetGoodsList();
			    for(Goods bookInfo: bookUptList){
		            System.out.println("ISBN13: "+bookInfo.get_ISBN13());   
		            
		            java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 					
					 map.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
					 map.put("ISBN13", bookInfo.get_ISBN13());					 
					 map.put("BOOKSTS", bookInfo.get_BOOKSTS());
					 					
	            	
					  work wk = new work();
		              wk.UptGoodsAPI(map,bookInfo);	
		          }
		    }catch (Exception e) {
			}
		
			
			  
			
			//송장입력
	    	try {
	    		work wk = new work();	
	    		
	    		  System.out.println("=====송장입력 start=====");
	    		wk.GetTransNoList("22262"); //스토어팜
	    		System.out.println("=====송장입력 end=====");
	    	}catch (Exception e) {
				// TODO: handle exception
			}
	    	
	    	*/ 
			
			   System.out.println("=====주문 start=====");
			   
			    try {
			    	 work wk = new work();
			    	 wk.Get_OrderInfo();
			    	
			    }catch (Exception e) {
				
				}
			    System.out.println("=====주문 end=====");
			   
			 
			// 상품등록
			try {
				System.out.println("=====상품 start=====");

				List<Goods> bookList = goodsDAO.GetGoodsList();
				for (Goods bookInfo : bookList) {
					try
					{
						int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

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
			} catch (Exception e) {
			}
			//상품가격업데이트
			    try {

				    List<Goods> bookUptList = goodsDAO.GetUptPriceGoodsList();
				    for(Goods bookInfo: bookUptList){
			            System.out.println("ISBN13: "+bookInfo.get_ISBN13());   
			            
			            try
			            {
			            	 int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());
					            
					            if (Pcnt > 0)
					            {
									 work wk = new work();
						             wk.UptPriceGoodsAPI(bookInfo);			
						              
					            }
			            }catch (Exception e) {
							// TODO: handle exception
			            	continue;
						}
			           
			          }
			    }catch (Exception e) {
				}
			  
			  //상품상태업데이트
			    try {

				    List<Goods> bookUptList = goodsDAO.GetUptGoodsList();
				    for(Goods bookInfo: bookUptList){
			            System.out.println("ISBN13: "+bookInfo.get_ISBN13());   
			            
			            int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());
			            
			            if (Pcnt > 0)
			            {
			            
				           	 java.util.HashMap<String, Object> map = new HashMap<String, Object>(); 
							 map.put("ITEMCD", bookInfo.get_ITEMCD());
							 map.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
							 map.put("ISBN13", bookInfo.get_ISBN13());
							 map.put("BOOK_NM", bookInfo.get_BOOK_NM());
							 map.put("BOOKSTS", bookInfo.get_BOOKSTS());
							 map.put("SALEPRICE", bookInfo.get_SALEPRICE());
							 map.put("PRICE", bookInfo.get_PRICE());

							
							  work wk = new work();
				              wk.UptGoodsAPI(map,bookInfo);				              												            
				             
			            }
			          }
			    }catch (Exception e) {
				}
			    System.out.println("=====상품 end=====");
				
			 
			 
		
		} catch (Exception e) {
		}

	}

}

class work {
	
	public void stringToDom(String xmlSource,String FilePath) 
	        throws IOException {		
	   /*
		java.io.FileWriter fw = new java.io.FileWriter(FilePath);

	    fw.write(xmlSource);
	    fw.close(); 
	    */
	    
		File target = new File(FilePath);		
		target.createNewFile();
		
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target.getPath()),"UTF8"));
		output.write(xmlSource);
		output.close();
	   
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
			
			sRtn = sRtn.replace("&", "");
			// String sRtn = Upt_GetXml();
			String Key = "d839fdeb9202c10bfe211d84f6d4a376";
			if (!"".equals(sRtn)) {
				String sVal = "";
				// Set_GoodsRegister
				// Set_GoodsState

				sVal = getHttpHTML_POST(sRtn, Key, "http://api.11st.co.kr/rest/prodservices/product");

				Document doc = toXmlDocument(sVal);

				NodeList list = doc.getElementsByTagName("ClientMessage");
				if (list != null) {
					String PCode = "";
					String ResultCode = "";
					String ResultMsg = "";
					for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
						if (node.getNodeName().equals("productNo")) {
							PCode = node.getTextContent();
						}
						if (node.getNodeName().equals("resultCode")) {
							ResultCode = node.getTextContent();
						}
						if (node.getNodeName().equals("message")) {
							ResultMsg = node.getTextContent();
						}
					}

					if (ResultCode.equals("200")) {
						bookInfo.set_PRODUCTNO(PCode);
						bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
						GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());

						int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

						if (Pcnt < 1) {
							java.util.HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("ITEMCD", bookInfo.get_ITEMCD());
							map.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
							map.put("ISBN13", bookInfo.get_ISBN13());
							map.put("BOOK_NM", bookInfo.get_BOOK_NM());
							map.put("BOOKSTS", bookInfo.get_BOOKSTS());
							map.put("SALEPRICE", bookInfo.get_SALEPRICE());
							map.put("PRICE", bookInfo.get_PRICE());

							goodsDAO.InsertRegItem(map);
						}

					}else {
						InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), ResultMsg);
					}
				}

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	
	
	public String UptGoodsList(Goods bookInfo) {
		double SALEPRICE = 0;
		String ResultCode = "";
		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 80)
				return "500";

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
			String sRtn = GetXml(bookInfo, SALEPRICE);
			
			sRtn = sRtn.replace("&", "");
			// String sRtn = Upt_GetXml();
			String Key = "d839fdeb9202c10bfe211d84f6d4a376";
			if (!"".equals(sRtn)) {
				String sVal = "";
				// Set_GoodsRegister
				// Set_GoodsState

				sVal = getHttpHTML_Put(sRtn, Key, "http://api.11st.co.kr/rest/prodservices/product/"+ bookInfo.get_PRODUCTNO() );

				Document doc = toXmlDocument(sVal);

				NodeList list = doc.getElementsByTagName("ClientMessage");
				if (list != null) {
					String PCode = "";
					
					String ResultMsg = "";
					for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
						if (node.getNodeName().equals("productNo")) {
							PCode = node.getTextContent();
						}
						if (node.getNodeName().equals("resultCode")) {
							ResultCode = node.getTextContent();
						}
						if (node.getNodeName().equals("message")) {
							ResultMsg = node.getTextContent();
						}
					}

					if (ResultCode.equals("210")) {						

					}else {
						
					}
				}

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
		return ResultCode;
	}

	public String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return "";
		return nValue.getNodeValue();
	}
	
	
	public void GetTransNoList(String MALL_ID)
	{
		try
		{
			OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			java.util.HashMap<String, Object> map1 = new HashMap<String, Object>(); 
			map1.put("MALL_ID", MALL_ID);
			
			List list = orderDAO.GetTransNoList(map1);
			
			for(int i=0; i < list.size();i++)
			{
				HashMap map = (HashMap)list.get(i);
				
				String TRNS_NO = (String)map.get("TRNS_NO");
				String ORD_ID  = (String)map.get("ORD_ID");					
				String ISBN13 = (String)map.get("ISBN13");
				String ORD_NO = String.valueOf(map.get("ORD_NO"));
				String SELL_QTY  = String.valueOf(map.get("SELL_QTY"));
				String DLVNO  = String.valueOf(map.get("DLVNO"));
				
				
				
				if (!TRNS_NO.equals(""))
				{
					String sVal = "";
					String Key = "d839fdeb9202c10bfe211d84f6d4a376";
					Date date = Calendar.getInstance().getTime();
					
					String sUrl = "https://api.11st.co.kr/rest/ordservices/reqdelivery/"+ex1(date, "yyyyMMddHHmm")+"/01/00034 /TRNS_NO/DLVNO/N/" + ORD_ID +"/" +ORD_NO;
					
					// String sUrl
					// ="https://api.11st.co.kr/rest/ordservices/complete/201904170000/201904190000";
					sVal = getHttpHTML_GET(Key, sUrl);

					Document doc = toXmlDocument(sVal);

					
					NodeList list1 = doc.getElementsByTagName("ResultOrder");
					if (list1 != null) {
						String productNo = "";
						String ResultCode = "";
						String ResultMsg = "";
						for (Node node = list1.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {

							if (node.getNodeName().equals("resultCode")) {
								ResultCode = node.getTextContent();
							}
							if (node.getNodeName().equals("message")) {
								ResultMsg = node.getTextContent();
							}
						}

						if (ResultCode.equals("0")) {							
							orderDAO.UptTrans(map);
						}
					}
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			InsertError("RegOrder",MALL_ID,"",e.getMessage());
		}
	}
	
	

	public void Get_OrderInfo() {
		String Key = "d839fdeb9202c10bfe211d84f6d4a376";
		Date date = Calendar.getInstance().getTime();

		Date yesterday = new Date();
		yesterday.setTime(date.getTime() - ((long) 1000 * 60 * 60 * 60));

		try {

			String sVal = "";
			String sUrl = "https://api.11st.co.kr/rest/ordservices/complete/" + ex1(yesterday, "yyyyMMddHHmm") + "/"
					+ ex1(date, "yyyyMMddHHmm");
			// String sUrl
			// ="https://api.11st.co.kr/rest/ordservices/complete/201904170000/201904190000";
			sVal = getHttpHTML_GET(Key, sUrl);

			Document doc = toXmlDocument(sVal);

			NodeList list = doc.getElementsByTagName("ns2:order");
			Order Order = new Order();

			if (list != null && list.getLength() > 0) {
				for (int i = 0; i < list.getLength(); i++) {

					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) node;

						Order.Set_ORD_DATE(getTagValue("ordStlEndDt", eElement).substring(0, 10).replace("-", ""));
						Order.Set_SELLER_ID(getTagValue("memID", eElement));
						Order.Set_ORD_ID(getTagValue("ordNo", eElement));
						Order.Set_ORD_NO(getTagValue("ordPrdSeq", eElement));
						Order.Set_SELL_QTY(getTagValue("ordQty", eElement));
						Order.Set_SELL_AMT(getTagValue("selPrc", eElement));
						Order.Set_SELL_TOTAMT(getTagValue("selPrc", eElement));
						Order.Set_ISBN13(getTagValue("sellerPrdCd", eElement));
						Order.Set_ITEM_NM(getTagValue("prdNm", eElement));
						Order.Set_DLV_PRICE(getTagValue("dlvCst", eElement));
						Order.Set_DLV_POST(getTagValue("rcvrMailNo", eElement));
						Order.Set_DLV_ADDR1(getTagValue("rcvrBaseAddr", eElement));
						Order.Set_DLV_ADDR2(getTagValue("rcvrDtlsAddr", eElement));
						Order.Set_DLV_MSG(getTagValue("ordDlvReqCont", eElement));
						Order.Set_DLV_NM(getTagValue("rcvrNm", eElement));
						Order.Set_DLV_TEL(getTagValue("rcvrTlphn", eElement));
						Order.Set_DLV_HP(getTagValue("rcvrPrtblNo", eElement));
						Order.Set_ADDPRDYN(getTagValue("addPrdYn", eElement));
						Order.Set_ADDPRDNO(getTagValue("addPrdNo", eElement));
						Order.Set_DLVNO(getTagValue("dlvNo", eElement));

						
						System.out.println(getTagValue("ordNo", eElement));
						
						if (getTagValue("rcvrTlphn", eElement).equals("")) {

							Order.Set_DLV_TEL(getTagValue("rcvrPrtblNo", eElement));
						}
						
						if (getTagValue("rcvrPrtblNo", eElement).equals("")) {

							Order.Set_DLV_HP(getTagValue("ordPrtblTel", eElement));
						}

						java.util.HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("ORD_ID", Order.Get_ORD_ID());
						map.put("ISBN13", Order.Get_ISBN13());
						map.put("ORD_NO", Order.Get_ORD_NO());

						OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());

						int MstCnt = orderDAO.GetExistBooxenOrderList(map);
						int DetailCnt = orderDAO.GetExistOrderList(map);

						if (MstCnt < 1) {
							double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
	 						double SELL_TOTAMT = SELL_AMT * Double.parseDouble(Order.Get_SELL_QTY());
	 						 
							java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
							maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
							maping.put("SELLER_ID", "booxen");
							maping.put("MALL_ID", "22262");
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
							maping.put("DLVNO", Order.Get_DLVNO());
							
							try {
								if (orderDAO.InsertBooxenOrdList(maping) > 0) {
									if (DetailCnt < 1) {
										orderDAO.InsertOrderList(maping);
										Set_OrderConfirm(Order.Get_ORD_ID(), Order.Get_ORD_NO(), Order.Get_ADDPRDYN(),
												Order.Get_ADDPRDNO(), Order.Get_DLVNO());
									}

								}
							} catch (Exception ex) {
								InsertError("RegOrder", Order.Get_ORD_ID(), "", ex.getMessage());
							}
						}else {
							Set_OrderConfirm(Order.Get_ORD_ID(), Order.Get_ORD_NO(), Order.Get_ADDPRDYN(),
									Order.Get_ADDPRDNO(), Order.Get_DLVNO());
						}

					}
				}

			}

		} catch (Exception e) {

		}

	}

	public void Set_OrderConfirm(String ordNo, String ordPrdSeq, String addPrdYn, String addPrdNo, String dlvNo) {
		String Key = "d839fdeb9202c10bfe211d84f6d4a376";
		String sVal = "";
		String sUrl = "https://api.11st.co.kr/rest/ordservices/reqpackaging/" + ordNo + "/" + ordPrdSeq + "/" + addPrdYn
				+ "/" + addPrdNo + "/" + dlvNo;
		sVal = getHttpHTML_GET(Key, sUrl);
	}

	public static String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, " ");
		return str;
	}

	public String ex1(Date date, String pattern) {
		String result = null;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
		result = format.format(date);
		return result;
	}

	public void UptPriceGoodsAPI(Goods bookInfo)
			throws ParserConfigurationException, SAXException, IOException {
		
		try
		{
			double SALEPRICE = 0;
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
			bookInfo.set_SALEPRICE(Double.toString(SALEPRICE).replaceAll((String)"\\.0$", ""));
			String sRtn = GetXml(bookInfo, SALEPRICE);
			
				
			String Key = "d839fdeb9202c10bfe211d84f6d4a376";
			String sVal = "";

			sVal = getHttpHTML_Put(sRtn, Key, 
					"http://api.11st.co.kr/rest/prodservices/product/" + bookInfo.get_PRODUCTNO());
			
			Document doc = toXmlDocument(sVal);

			NodeList list = doc.getElementsByTagName("ClientMessage");
			if (list != null) {
				String productNo = "";
				String ResultCode = "";
				String ResultMsg = "";
				for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {

					if (node.getNodeName().equals("resultCode")) {
						ResultCode = node.getTextContent();
					}
					if (node.getNodeName().equals("message")) {
						ResultMsg = node.getTextContent();
					}
				}

				if (ResultCode.equals("200")) {
					GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					java.util.HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("PRICE", bookInfo.get_PRICE());
					map.put("SALEPRICE", bookInfo.get_SALEPRICE());
					map.put("ISBN13", bookInfo.get_ISBN13());
					goodsDAO.UptSalePriceItem(map);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		

	}

	
	public void UptGoodsAPI(java.util.HashMap map, Goods bookInfo)
			throws ParserConfigurationException, SAXException, IOException {
		// String sRtn = Upt_GetXml(bookInfo);
		String Key = "d839fdeb9202c10bfe211d84f6d4a376";
		String sVal = "";

		if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매")) {
			// sVal =
			// getHttpHTML_PUT(Key,"http://api.11st.co.kr/rest/prodstatservice/stat/stopdisplay/"
			// + bookInfo.get_PRODUCTNO());
			sVal = getHttpHTML_PUT(Key,
					"http://api.11st.co.kr/rest/prodstatservice/stat/restartdisplay/" + bookInfo.get_PRODUCTNO());
		} else {
			sVal = getHttpHTML_PUT(Key,
					"http://api.11st.co.kr/rest/prodstatservice/stat/stopdisplay/" + bookInfo.get_PRODUCTNO());
		}

		Document doc = toXmlDocument(sVal);

		NodeList list = doc.getElementsByTagName("ClientMessage");
		if (list != null) {
			String productNo = "";
			String ResultCode = "";
			String ResultMsg = "";
			for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {

				if (node.getNodeName().equals("resultCode")) {
					ResultCode = node.getTextContent();
				}
				if (node.getNodeName().equals("message")) {
					ResultMsg = node.getTextContent();
				}
			}

			
			if (ResultCode.equals("200")) {
				GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				goodsDAO.UptRegItem(map);
			}
			
			if (ResultMsg.indexOf("해당 상품의 정보를 찾을 수 없습니다") > -1 || ResultMsg.indexOf("존재하지") > -1)
			{
				GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				goodsDAO.DeleteProd(map);
			}
			
			if (ResultMsg.indexOf("판매중지 상품만 판매중지해제가 가능합니다.") > -1)
			{
				sVal = getHttpHTML_PUT(Key,
						"http://api.11st.co.kr/rest/prodstatservice/stat/stopdisplay/" + bookInfo.get_PRODUCTNO());
				sVal = getHttpHTML_PUT(Key,
						"http://api.11st.co.kr/rest/prodstatservice/stat/restartdisplay/" + bookInfo.get_PRODUCTNO());
				if ( sVal.indexOf("판매상태가 수정되었습니다") > -1)
				{
					GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					goodsDAO.UptRegItem(map);	
				}
				
			}
			
			if (ResultMsg.indexOf("해당 상품의 재고수량 등록 후 판매중지를 해제해주세요.") > -1 || sVal.indexOf("판매중/전시전인 상품만 판매중지가 가능합니다.") > -1)
			{
				String strCode = UptGoodsList(bookInfo);
				if (strCode.equals("210"))
				{
					if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매")) {
						// sVal =
						// getHttpHTML_PUT(Key,"http://api.11st.co.kr/rest/prodstatservice/stat/stopdisplay/"
						// + bookInfo.get_PRODUCTNO());
						sVal = getHttpHTML_PUT(Key,
								"http://api.11st.co.kr/rest/prodstatservice/stat/restartdisplay/" + bookInfo.get_PRODUCTNO());
					} else {
						sVal = getHttpHTML_PUT(Key,
								"http://api.11st.co.kr/rest/prodstatservice/stat/stopdisplay/" + bookInfo.get_PRODUCTNO());
					}
					
					doc = toXmlDocument(sVal);
					list = doc.getElementsByTagName("ClientMessage");
					if (list != null) {
						
						for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {

							if (node.getNodeName().equals("resultCode")) {
								ResultCode = node.getTextContent();
							}
							if (node.getNodeName().equals("message")) {
								ResultMsg = node.getTextContent();
							}
						}
					}
					
					if (ResultCode.equals("200")) {
						GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
						goodsDAO.UptRegItem(map);
					}
					
				}
				
			}
		}

	}

	public String getHttpHTML_GET(String Key, String surl) {
		String sReturn = "";
		try {
			
			URL obj = new URL(surl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			con.setRequestProperty("openapikey", Key);

			con.setConnectTimeout(10000); // 컨텍션타임아웃 10초
			con.setReadTimeout(5000); // 컨텐츠조회 타임아웃 5총

			int responseCode = con.getResponseCode();

			Charset charset = Charset.forName("euc-kr");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			sReturn = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(sReturn);
		return sReturn;

	}

	public String getHttpHTML_PUT(String Key, String surl) {
		String sReturn = "";
		try {

			URL url = new URL(surl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			con.setRequestProperty("openapikey", Key);
			// con.setRequestProperty("Content-Length",String.valueOf(xml.length()));

			con.setDoOutput(true);

			String responseStatus = con.getResponseMessage();
			System.out.println(responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "euc-kr"));

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
		return sReturn;

	}

	public String getHttpHTML_POST(String xml, String Key, String surl) {
		String sReturn = "";
		try {

			URL url = new URL(surl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			con.setRequestProperty("openapikey", Key);
			// con.setRequestProperty("Content-Length",String.valueOf(xml.length()));

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(new String(xml.getBytes("UTF-8"), "8859_1"));
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			System.out.println(responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "euc-kr"));

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
		return sReturn;

	}

	
	public String getHttpHTML_Put(String xml, String Key, String surl) {
		String sReturn = "";
		try {

			URL url = new URL(surl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
			con.setRequestProperty("openapikey", Key);
			// con.setRequestProperty("Content-Length",String.valueOf(xml.length()));

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(new String(xml.getBytes("UTF-8"), "8859_1"));
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			System.out.println(responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "euc-kr"));

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
		return sReturn;

	}
	
	public String GetXml(Goods bookInfo, double SALEPRICE) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<Product>");
		sb.append("<selMthdCd>01</selMthdCd>");
		sb.append("<dispCtgrNo>" + GetCategory(bookInfo) + "</dispCtgrNo>");
		sb.append("<prdTypCd>01</prdTypCd>");
		sb.append("<prdNm><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></prdNm>");
		sb.append("<advrtStmt></advrtStmt>");
		sb.append("<orgnTypCd>01</orgnTypCd>");
		sb.append("<sellerPrdCd>" + bookInfo.get_ISBN13() + "</sellerPrdCd>");
		if (bookInfo.get_ISBN13().substring(0,1).equals("8"))
		{
			sb.append("<suplDtyfrPrdClfCd>01</suplDtyfrPrdClfCd>");
		}else {
			sb.append("<suplDtyfrPrdClfCd>02</suplDtyfrPrdClfCd>");
		}
		
		
		sb.append("<yearEndTaxYn>Y</yearEndTaxYn>");
		sb.append("<suplDtyfrPrdClfCd>02</suplDtyfrPrdClfCd>");
		sb.append("<prdStatCd>01</prdStatCd>");
		sb.append("<minorSelCnYn>Y</minorSelCnYn>");
		//sb.append("<prdImage01><![CDATA[" + bookInfo.get_IMG_PATH2().replaceFirst("\\", "/") + "]]></prdImage01>");
		sb.append("<prdImage01><![CDATA[" + bookInfo.get_IMG_PATH2() + "]]></prdImage01>");
		sb.append("<htmlDetail><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></htmlDetail>");
		sb.append("<ProductCert>");
		sb.append("<certTypeCd>131</certTypeCd>");
		sb.append("<certKey></certKey>");
		sb.append("</ProductCert>");
		sb.append("<selTermUseYn>N</selTermUseYn>");
		sb.append("<maktPrc>" + bookInfo.get_PRICE() + "</maktPrc>");
		sb.append("<selPrc>" + (int) SALEPRICE + "</selPrc>");
		sb.append("<prdSelQty>9999</prdSelQty>");
		sb.append("<dlvCnAreaCd>01</dlvCnAreaCd>");// 배송가능지역
		sb.append("<dlvWyCd>01</dlvWyCd>");// 배송방법 택배
		sb.append("<dlvEtprsCd>00011</dlvEtprsCd>");// 택배사
		sb.append("<bndlDlvCnYn>Y</bndlDlvCnYn>"); // 묶음 배송
		sb.append("<dlvCstInstBasiCd>07</dlvCstInstBasiCd>"); // 판매자 조건부 무료
		sb.append("<dlvCstPayTypCd>03</dlvCstPayTypCd>"); // 결제방법
		sb.append("<jejuDlvCst>3000</jejuDlvCst>"); // 제주
		sb.append("<islandDlvCst>3000</islandDlvCst>"); // 도서산간

		//판매기간설정
		sb.append("<setFpSelTermYn>Y</setFpSelTermYn>");
		sb.append("<selPrdClfCd>3y:110</selPrdClfCd>"); 
		
		sb.append("<addrSeqOut>2</addrSeqOut>"); // 출고지
		sb.append("<addrSeqIn>3</addrSeqIn>"); // 반품배송지

		sb.append("<rtngdDlvCst>2500</rtngdDlvCst>"); // 반품배송비
		sb.append("<exchDlvCst>5000</exchDlvCst>"); // 교환배송비
		sb.append("<asDetail>상세설명참고</asDetail>"); // A/S
		sb.append("<rtngExchDetail>상세설명참고</rtngExchDetail>"); // 교환/반품안내
		sb.append("<mnfcDy>" + bookInfo.get_OPENDATE().substring(0, 10).replace("-", "/") + "</mnfcDy>"); // A/S

		sb.append("<ProductNotification>");
		sb.append("<type>891036</type>");
		sb.append("<item>");
		sb.append("<code>17461</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<code>11924</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<code>11901</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<code>23674328</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<code>23674466</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<code>23674410</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<code>11932</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("<item>");
		sb.append("<code>36743120</code>");
		sb.append("<name>상품상세설명 참조</name>");
		sb.append("</item>");
		sb.append("</ProductNotification>");

		sb.append("<company>" + bookInfo.get_PUBLISHER() + "</company>");
		sb.append("<brand>" + bookInfo.get_PUBLISHER() + "</brand>");
		sb.append("<modelNm>" + bookInfo.get_PUBLISHER() + "</modelNm>");
		sb.append("<prcCmpExpYn>Y</prcCmpExpYn>");
		sb.append("</Product>");

		return sb.toString();
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

	public Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new InputSource(new StringReader(str)));

		return document;
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

			StrCateGory = "863254";
		}

		return StrCateGory;
	}
}
