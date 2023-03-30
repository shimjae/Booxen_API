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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import java.io.Writer;

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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class Booxen_BaroGodoMall {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			System.out.println("=====주문 start=====");

			try {
				work wk = new work();
				wk.Get_OrderInfo();

			} catch (Exception e) {

			}
			System.out.println("=====주문 end=====");

			// 상품등록

			try {
				System.out.println("=====상품 start=====");

				List<Goods> bookList = goodsDAO.GetGoodsList();
				for (Goods bookInfo : bookList) {
					try {
						int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

						if (Pcnt < 1 && (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))) {
							System.out.println("ISBN13: " + bookInfo.get_ISBN13());

							work wk = new work();
							wk.RegGoodsAPI(bookInfo);
						}
					} catch (Exception e) {
						work wk = new work();
						wk.InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
						continue;
					}

				}
			} catch (Exception e) {
			}

			// 상품업데이트
			try {

				List<Goods> bookUptList = goodsDAO.GetUptGoodsList();
				for (Goods bookInfo : bookUptList) {
					System.out.println("상품업데이트 ISBN13: " + bookInfo.get_ISBN13());

					int Pcnt = goodsDAO.GetExistGoodsList(bookInfo.get_ISBN13());

					if (Pcnt > 0) {

						java.util.HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("ITEMCD", bookInfo.get_ITEMCD());
						map.put("PRODUCTNO", bookInfo.get_PRODUCTNO());
						map.put("ISBN13", bookInfo.get_ISBN13());
						map.put("BOOK_NM", bookInfo.get_BOOK_NM());
						map.put("BOOKSTS", bookInfo.get_BOOKSTS());
						map.put("SALEPRICE", bookInfo.get_SALEPRICE());
						map.put("PRICE", bookInfo.get_PRICE());

						work wk = new work();
						wk.UptGoodsAPI(map, bookInfo);
					}
				}
			} catch (Exception e) {
			}
			System.out.println("=====상품 end=====");

		} catch (Exception e) {
		} finally {
			System.exit(0);
		}

	}

}

class work {
	public void Get_OrderInfo() {
		Date date = Calendar.getInstance().getTime();

		Date yesterday = new Date();
		yesterday.setTime(date.getTime() - ((long) 1000 * 60 * 60 * 360));

		try {

			String sReturn = "";
			try {
				URL url = new URL("https://openhub.godo.co.kr/godomall5/order/Order_Search.php"); // 호출할 url
				Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅

				params.put("partner_key", "JUQwJTg1JUJEJTAxJUY3JURGWCVFNg==");
				params.put("key",
						"Ul8lREElODElRUUlMTIlRDglMkYlMDhFJUQ3JUFDOCU4NiVFRSU4Q0clOTlTJTdGJTIzJUU1JTkwJUY2alVIJUNCUiU5NSVDOSVERiUyOSU4RiVBRCU5OSVFOCU1QyVFQyVGMiUyMSVCRXklOUQ=");
				params.put("dateType", "order");
				params.put("startDate", ex1(yesterday, "yyyy-MM-dd"));
				params.put("endDate", ex1(date, "yyyy-MM-dd"));
				params.put("orderStatus", "p1");
				params.put("scmNo", "31");

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

				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) { // response 출력
					response.append(inputLine);
				}
				System.out.println(response.toString());
				in.close();
				sReturn = response.toString();

				if (!sReturn.equals("")) {
					Document doc = toXmlDocument(sReturn);
					ArrayList<Order> Order_Array = new ArrayList<Order>();

					Order Order = new Order();

					NodeList list = doc.getElementsByTagName("order_data");
					if (list != null && list.getLength() > 0) {
						for (int i = 0; i < list.getLength(); i++) {
							Node node = list.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								Order.Set_ORD_ID(getTagValue("orderNo", eElement));
								Order.Set_DLV_PRICE(getTagValue("totalDeliveryCharge", eElement));
								Order.Set_ORD_DATE(
										getTagValue("orderDate", eElement).substring(0, 10).replace("-", ""));
								Order.Set_SELL_TOTAMT(getTagValue("settlePrice", eElement));

								NodeList nlList_orderInfoData = eElement.getElementsByTagName("orderInfoData");
								if (nlList_orderInfoData != null && nlList_orderInfoData.getLength() > 0) {
									for (int ix = 0; ix < nlList_orderInfoData.getLength(); ix++) {
										Node OrderInfoValue = nlList_orderInfoData.item(ix);

										if (OrderInfoValue.getNodeType() == Node.ELEMENT_NODE) {
											Element nElement = (Element) OrderInfoValue;
											Order.Set_DLV_NM(getTagValue("receiverName", nElement));
											Order.Set_DLV_ADDR1(getTagValue("receiverAddress", nElement));
											Order.Set_DLV_ADDR2(getTagValue("receiverAddressSub", nElement));
											Order.Set_DLV_HP(getTagValue("receiverCellPhone", nElement));
											Order.Set_DLV_MSG(getTagValue("orderMemo", nElement));
											Order.Set_DLV_POST(getTagValue("receiverZipcode", nElement));
											Order.Set_DLV_TEL(getTagValue("receiverPhone", nElement));

											if (getTagValue("receiverZipcode", nElement).equals("") || getTagValue("receiverZipcode", nElement).equals("-"))
											{
												Order.Set_DLV_POST(getTagValue("receiverZonecode", nElement));												
											}
											
											
											if (getTagValue("receiverPhone", nElement).equals("")) {

												Order.Set_DLV_TEL(getTagValue("receiverCellPhone", eElement));
											}

											if (getTagValue("receiverCellPhone", nElement).equals("")) {

												Order.Set_DLV_HP(getTagValue("receiverPhone", eElement));
											}
										}
									}
								}

								NodeList nlList_orderGoodsData = eElement.getElementsByTagName("orderGoodsData");
								if (nlList_orderGoodsData != null && nlList_orderGoodsData.getLength() > 0) {
									for (int io = 0; io < nlList_orderGoodsData.getLength(); io++) {
										Node OrderValue = nlList_orderGoodsData.item(io);

										if (OrderValue.getNodeType() == Node.ELEMENT_NODE) {
											Element Order_Element = (Element) OrderValue;
											Order.Set_ISBN13(getTagValue("goodsCd", Order_Element));
											Order.Set_SELL_QTY(getTagValue("goodsCnt", Order_Element));
											Order.Set_ITEM_NM(getTagValue("goodsNm", Order_Element));
											Order.Set_SELL_AMT(getTagValue("goodsPrice", Order_Element));
											Order.Set_ORD_NO(String.valueOf(io + 1));

											java.util.HashMap<String, Object> map = new HashMap<String, Object>();
											map.put("ORD_ID", Order.Get_ORD_ID());
											map.put("ISBN13", Order.Get_ISBN13());
											map.put("ORD_NO", Order.Get_ORD_NO());

											OrderDAO orderDAO = new OrderDAO(
													MyBatisConnectionFactory.getSqlSessionFactory());

											int MstCnt = orderDAO.GetExistBooxenOrderList(map);
											int DetailCnt = orderDAO.GetExistOrderList(map);

											if (MstCnt < 1) {
												double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
												double SELL_TOTAMT = SELL_AMT
														* Double.parseDouble(Order.Get_SELL_QTY());

												java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
												maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
												maping.put("SELLER_ID", "booxen");
												maping.put("MALL_ID", "50764");
												maping.put("ORD_ID", Order.Get_ORD_ID());
												maping.put("ORD_NO", Order.Get_ORD_NO());
												maping.put("SELL_QTY", Order.Get_SELL_QTY());
												maping.put("SELL_AMT", Order.Get_SELL_AMT());
												maping.put("SELL_TOTAMT", String.valueOf((int) SELL_TOTAMT));
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
														if (DetailCnt < 1) {
															orderDAO.InsertOrderList(maping);
															Set_OrderConfirm(Order.Get_ORD_ID(),
																	getTagValue("sno", Order_Element));
														}

													}
												} catch (Exception ex) {
													InsertError("RegOrder", Order.Get_ORD_ID(), "", ex.getMessage());
												}
											}
										}
									}
								}

							}

						}
					}

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {

		}

	}

	public void Set_OrderConfirm(String orderNo, String sno) {
		Date date = Calendar.getInstance().getTime();

		try {
			/*
			 * StringBuilder sb = new StringBuilder();
			 * 
			 * sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"); sb.append("<data>");
			 * sb.append("<status_data idx=\"1\">");
			 * sb.append("<orderNo>"+orderNo+"</orderNo>");
			 * sb.append("<sno>"+Isbn13+"</sno>");
			 * sb.append("<orderStatus>g1</orderStatus>"); sb.append("</status_data>");
			 * sb.append("</data>");
			 * 
			 * 
			 * 
			 * File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\
			 * GodoxmlOrderConfirm\\"); if (!desti.exists()) { desti.mkdirs(); }
			 * 
			 * stringToDom(sb.toString(),"D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-
			 * dd") + "\\GodoxmlOrderConfirm\\"+orderNo+".xml");
			 */
			String sVal = "";
			// sVal =
			// getHttpHTML_POST("https://openhub.godo.co.kr/godomall5/order/Order_Status.php",HOST_IP()
			// + "/GodoxmlOrderConfirm/" + orderNo + ".xml");
			sVal = getHttpHTML_POST_Confirm("https://openhub.godo.co.kr/godomall5/order/Order_Status.php", orderNo,
					sno);
			if (!"".equals(sVal)) {

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, " ");
		return str;
	}

	public String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return "";
		return nValue.getNodeValue();
	}

	public void RegGoodsAPI(Goods bookInfo) {
		double SALEPRICE = 0;
		String goodsDiscount = "10";
		String RtnMsg = "";
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		Date date = Calendar.getInstance().getTime();

		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 80)
				return;

			if (Double.parseDouble(bookInfo.get_INRT()) <= 70) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;
				BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);
				SALEPRICE = incm_civil_amt.doubleValue();
				goodsDiscount = "10";
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;
				BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);
				SALEPRICE = incm_civil_amt.doubleValue();

				goodsDiscount = "5";
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
				goodsDiscount = "0";
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
				goodsDiscount = "0";
			}

			String StrCateGory = "";

			java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>();
			if (bookInfo.get_BX_CATECD() == null) {
				StrCateGory = "013002004";
			} else {

				Result_map = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());

				if (Result_map == null) {
					StrCateGory = "013002004";
				} else {
					Iterator iter = Result_map.entrySet().iterator();

					while (iter.hasNext()) {

						Entry entry = (Entry) iter.next();

						StrCateGory = entry.getValue().toString();

					}

				}

			}

			if (StrCateGory.equals("")) {

				StrCateGory = "013002004";
			}

			StringBuilder sb = new StringBuilder();

			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			sb.append("<data>");
			sb.append("<goods_data idx=\"1\">");
			sb.append("<cateCd><![CDATA[" + StrCateGory + "]]></cateCd>");
			sb.append("<allCateCd><![CDATA[" + StrCateGory + "]]></allCateCd>");
			sb.append("<goodsDisplayFl>y</goodsDisplayFl>");
			sb.append("<goodsDisplayMobileFl>y</goodsDisplayMobileFl>");
			sb.append("<goodsSellFl>y</goodsSellFl>");
			sb.append("<goodsSellMobileFl>y</goodsSellMobileFl>");
			sb.append("<goodsCd><![CDATA[" + bookInfo.get_ISBN13() + "]]></goodsCd>");
			sb.append("<goodsNmFl>d</goodsNmFl>");
			sb.append("<goodsNm><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsNm>");
			sb.append("<goodsNmMain><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsNmMain>");
			sb.append("<goodsNmList><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsNmList>");
			sb.append("<goodsNmDetail><![CDATA[]]></goodsNmDetail>");
			sb.append("<goodsSearchWord><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsSearchWord>");
			sb.append("<goodsOpenDt><![CDATA[" + ex1(date, "yyyy-MM-dd") + "]]></goodsOpenDt>");
			sb.append("<goodsState>n</goodsState>");
			sb.append("<goodsColor><![CDATA[]]></goodsColor>");
			sb.append("<imageStorage><![CDATA[url]]></imageStorage>");
			sb.append("<payLimitFl>n</payLimitFl>");
			sb.append("<makerNm><![CDATA[" + bookInfo.get_PUBLISHER() + "]]></makerNm>");
			sb.append("<goodsModelNo><![CDATA[" + bookInfo.get_ISBN13() + "]]></goodsModelNo>");
			sb.append("<originNm><![CDATA[대한민국]]></originNm>");
			sb.append("<makeYmd><![CDATA[" + bookInfo.get_OPENDATE().substring(0, 10) + "]]></makeYmd>");
			sb.append("<goodsPermission>all</goodsPermission>");
			sb.append("<onlyAdultFl>n</onlyAdultFl>");

			sb.append("<goodsMustInfoData idx=\"1\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[도서명]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");
			
			sb.append("<goodsMustInfoData idx=\"2\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[저자/출판사]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_PUBLISHER() + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");
			
			sb.append("<goodsMustInfoData idx=\"3\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[크기/전자책용량]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_BOOK_WT_VAL() + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");
			
			sb.append("<goodsMustInfoData idx=\"4\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[쪽수]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_BOOK_PAGE_VAL() + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");
			
			sb.append("<goodsMustInfoData idx=\"5\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[제품 구성]]></infoTitle>");
			sb.append("<infoValue><![CDATA[상품상세참조]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");
			
			sb.append("<goodsMustInfoData idx=\"6\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[출간일]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_OPENDATE().substring(0, 10) + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");
			
			sb.append("<goodsMustInfoData idx=\"7\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[목차 또는 책소개]]></infoTitle>");
			sb.append("<infoValue><![CDATA[상품상세참조]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");
			

			sb.append("<taxFreeFl>f</taxFreeFl>");
			sb.append("<totalStock>9999</totalStock>");
			sb.append("<stockFl>y</stockFl>");
			sb.append("<soldOutFl>n</soldOutFl>");
			sb.append("<minOrderCnt>1</minOrderCnt>");
			sb.append("<maxOrderCnt>0</maxOrderCnt>");
			sb.append("<salesUnit>1</salesUnit>");
			sb.append("<mileageFl>c</mileageFl>");
			sb.append("<goodsDiscountFl>n</goodsDiscountFl>");
			sb.append("<goodsDiscount>" + goodsDiscount + "</goodsDiscount>");
			sb.append("<goodsDiscountUnit>percent</goodsDiscountUnit>");
			sb.append("<fixedPrice>" + bookInfo.get_PRICE() + "</fixedPrice>");
			sb.append("<costPrice>" + bookInfo.get_PRICE() + "</costPrice>");
			sb.append("<goodsPrice>" + SALEPRICE + "</goodsPrice>");
			sb.append("<optionFl>n</optionFl>");
			sb.append("<optionTextFl>n</optionTextFl>");
			sb.append("<addGoodsFl>n</addGoodsFl>");

			sb.append("<image_size_decrease>Y</image_size_decrease>");
			sb.append("<detailImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></detailImageData>");
			sb.append("<listImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></listImageData>");
			sb.append("<magnifyImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></magnifyImageData>");
			sb.append("<mainImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></mainImageData>");

			sb.append("<shortDescription><![CDATA[]]></shortDescription>");
			sb.append("<goodsDescription><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></goodsDescription>");
			sb.append(
					"<goodsDescriptionMobile><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></goodsDescriptionMobile>");

			sb.append("<deliverySno>1</deliverySno>");
			
			sb.append("<relationFl>n</relationFl>");

			sb.append("<imgDetailViewFl>y</imgDetailViewFl>");

			sb.append("<detailInfoDelivery>002001</detailInfoDelivery>");
			sb.append("<detailInfoAS>003001</detailInfoAS>");
			sb.append("<detailInfoExchange>005001</detailInfoExchange>");
			sb.append("<detailInfoRefund>004001</detailInfoRefund>");
			  
			sb.append("<externalVideoFl>n</externalVideoFl>");
			sb.append("<naverFl>n</naverFl>");
			sb.append("<naverAgeGroup>a</naverAgeGroup>");
			sb.append("<naverGender>c</naverGender>");
			sb.append("<naverTag><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></naverTag>");
			sb.append("<scmNo>1</scmNo>");
			sb.append("<restockFl>n</restockFl>");

			sb.append("</goods_data>");

			sb.append("</data>");

			String sVal = "";

			File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\GodoxmlInsert\\");
			if (!desti.exists()) {
				desti.mkdirs();
			}

			stringToDom(sb.toString(), "D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\GodoxmlInsert\\"
					+ bookInfo.get_ISBN13() + ".xml");

			// NoBom("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") +
			// "\\GodoxmlInsert\\temp.xml","D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd")
			// + "\\GodoxmlInsert\\"+bookInfo.get_ISBN13()+".xml");
			sVal = getHttpHTML_POST("https://openhub.godo.co.kr/godomall5/goods/Goods_Insert.php",
					HOST_IP() + "/GodoxmlInsert/" + bookInfo.get_ISBN13() + ".xml");

			if (!"".equals(sVal)) {

				Document doc = toXmlDocument(sVal);

				NodeList list = doc.getElementsByTagName("goods_data");
				if (list != null) {
					String PCode = "";
					String ResultCode = "";
					String ResultMsg = "";

					NodeList list_data = doc.getElementsByTagName("goodsno");

					if (list_data != null) {
						PCode = list_data.item(0).getFirstChild().getTextContent();
					}

					for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {

						if (node.getNodeName().equals("code")) {
							ResultCode = node.getTextContent();
						}
						if (node.getNodeName().equals("msg")) {
							ResultMsg = node.getTextContent();
						}

					}

					if (ResultCode.equals("000")) {
						bookInfo.set_PRODUCTNO(PCode);
						bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));

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

					}
				}

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}

	public void FileSaveNoBOM(String Origin_Path, String FilePath) throws IOException {
		String filePath = Origin_Path;
		File file = new File(filePath);

		if (file != null) {
			// BOM 이 존재한다면 BOM을 제거하여 새로운 파일을 작성한다.(UTF-8)
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filePath));
			int firstChar = dataInputStream.readByte();
			int secondChar = dataInputStream.readByte();
			int thirdChar = dataInputStream.readByte();

			if ((firstChar == -17) && (secondChar == -69) && (thirdChar == -65)) {
				DataOutputStream dataOutputStream = new DataOutputStream(

						new FileOutputStream(FilePath));
				int i;
				while ((i = dataInputStream.read()) != -1) {
					dataOutputStream.writeByte(i);
				}
				dataOutputStream.close();
				file = new File(FilePath);
			}
			dataInputStream.close();
		}
	}

	public String removeUTF8BOM(String str) {
		// FEFF because this is the Unicode char represented by the UTF-8 byte order
		// mark (EF BB BF).
		if (str.startsWith("uFEFF")) {
			str = str.substring(1);
		}
		return str;
	}

	public String getHttpHTML_POST(String urlStr, String sparam) {
		String sReturn = "";
		try {

			URL url = new URL(urlStr); // 호출할 url
			Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
			params.put("data_url", sparam);

			params.put("partner_key", "JUQwJTg1JUJEJTAxJUY3JURGWCVFNg==");
			params.put("key",
					"Ul8lREElODElRUUlMTIlRDglMkYlMDhFJUQ3JUFDOCU4NiVFRSU4Q0clOTlTJTdGJTIzJUU1JTkwJUY2alVIJUNCUiU5NSVDOSVERiUyOSU4RiVBRCU5OSVFOCU1QyVFQyVGMiUyMSVCRXklOUQ=");

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

	public String getHttpHTML_POST_Confirm(String urlStr, String orderNo, String sno) {
		String sReturn = "";
		try {

			URL url = new URL(urlStr); // 호출할 url
			Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
			params.put("orderNo", orderNo);
			params.put("sno", sno);
			params.put("orderStatus", "g1");

			params.put("partner_key", "JUQwJTg1JUJEJTAxJUY3JURGWCVFNg==");
			params.put("key",
					"Ul8lREElODElRUUlMTIlRDglMkYlMDhFJUQ3JUFDOCU4NiVFRSU4Q0clOTlTJTdGJTIzJUU1JTkwJUY2alVIJUNCUiU5NSVDOSVERiUyOSU4RiVBRCU5OSVFOCU1QyVFQyVGMiUyMSVCRXklOUQ=");

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

	public String HOST_IP() {
		Date date = Calendar.getInstance().getTime();

		return "http://211.115.107.33/XML/" + ex1(date, "yyyy-MM-dd") + "/";
		// return "http://shoplinker.booxen.com/XML/" + ex1(date, "yyyy-MM-dd") + "/";
	}

	public void stringToDom(String xmlSource, String FilePath) throws IOException {
		/*
		 * java.io.FileWriter fw = new java.io.FileWriter(FilePath);
		 * 
		 * fw.write(xmlSource); fw.close();
		 */

		File target = new File(FilePath);
		target.createNewFile();

		BufferedWriter output = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(target.getPath()), "UTF8"));
		output.write(xmlSource);
		output.close();

	}

	public String ex1(Date date, String pattern) {
		String result = null;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(pattern);
		result = format.format(date);
		return result;
	}

	public void UptGoodsAPI(java.util.HashMap map, Goods bookInfo)
			throws ParserConfigurationException, SAXException, IOException {
		double SALEPRICE = 0;
		String goodsDiscount = "10";
		String RtnMsg = "";
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		Date date = Calendar.getInstance().getTime();

		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 80)
				return;

			if (Double.parseDouble(bookInfo.get_INRT()) <= 70) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;
				BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);
				SALEPRICE = incm_civil_amt.doubleValue();
				goodsDiscount = "10";
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 70 && Double.parseDouble(bookInfo.get_INRT()) < 76) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;
				BigDecimal incm_civil_amt = new BigDecimal(SALEPRICE);
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);
				SALEPRICE = incm_civil_amt.doubleValue();
				goodsDiscount = "5";
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 75 && Double.parseDouble(bookInfo.get_INRT()) < 81) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
				goodsDiscount = "0";
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
				goodsDiscount = "0";
			}
			String StrCateGory = "";

			java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>();
			if (bookInfo.get_BX_CATECD() == null) {
				StrCateGory = "013002004";
			} else {

				Result_map = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());

				if (Result_map == null) {
					StrCateGory = "013002004";
				} else {
					Iterator iter = Result_map.entrySet().iterator();

					while (iter.hasNext()) {

						Entry entry = (Entry) iter.next();

						StrCateGory = entry.getValue().toString();

					}

				}

			}

			if (StrCateGory.equals("")) {

				StrCateGory = "013002004";
			}

			StringBuilder sb = new StringBuilder();

			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			sb.append("<data>");
			sb.append("<goods_data idx=\"1\">");
			sb.append("<goodsNo><![CDATA[" + bookInfo.get_PRODUCTNO() + "]]></goodsNo>");
			sb.append("<cateCd><![CDATA[" + StrCateGory + "]]></cateCd>");
			sb.append("<allCateCd><![CDATA[" + StrCateGory + "]]></allCateCd>");

			if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매")) {
				sb.append("<goodsDisplayFl>y</goodsDisplayFl>");
				sb.append("<goodsDisplayMobileFl>y</goodsDisplayMobileFl>");
				sb.append("<goodsSellFl>y</goodsSellFl>");
				sb.append("<goodsSellMobileFl>y</goodsSellMobileFl>");
			} else {
				sb.append("<goodsDisplayFl>n</goodsDisplayFl>");
				sb.append("<goodsDisplayMobileFl>n</goodsDisplayMobileFl>");
				sb.append("<goodsSellFl>n</goodsSellFl>");
				sb.append("<goodsSellMobileFl>n</goodsSellMobileFl>");
			}

			sb.append("<goodsCd><![CDATA[" + bookInfo.get_ISBN13() + "]]></goodsCd>");
			sb.append("<goodsNmFl>d</goodsNmFl>");
			sb.append("<goodsNm><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsNm>");
			sb.append("<goodsNmMain><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsNmMain>");
			sb.append("<goodsNmList><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsNmList>");
			sb.append("<goodsNmDetail><![CDATA[]]></goodsNmDetail>");
			sb.append("<goodsSearchWord><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></goodsSearchWord>");
			sb.append("<goodsOpenDt><![CDATA[" + ex1(date, "yyyy-MM-dd") + "]]></goodsOpenDt>");
			sb.append("<goodsState>n</goodsState>");
			sb.append("<goodsColor><![CDATA[]]></goodsColor>");
			sb.append("<imageStorage><![CDATA[url]]></imageStorage>");
			sb.append("<payLimitFl>n</payLimitFl>");
			sb.append("<makerNm><![CDATA[" + bookInfo.get_PUBLISHER() + "]]></makerNm>");
			sb.append("<goodsModelNo><![CDATA[" + bookInfo.get_ISBN13() + "]]></goodsModelNo>");
			sb.append("<originNm><![CDATA[대한민국]]></originNm>");
			sb.append("<makeYmd><![CDATA[" + bookInfo.get_OPENDATE().substring(0, 10) + "]]></makeYmd>");
			sb.append("<goodsPermission>all</goodsPermission>");
			sb.append("<onlyAdultFl>n</onlyAdultFl>");

			sb.append("<goodsMustInfoData idx=\"1\">");
			sb.append("<stepData idx=\"1\">");
			sb.append("<infoTitle><![CDATA[도서명]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("<stepData idx=\"2\">");
			sb.append("<infoTitle><![CDATA[저자/출판사]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_PUBLISHER() + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("<stepData idx=\"3\">");
			sb.append("<infoTitle><![CDATA[크기/전자책용량]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_BOOK_WT_VAL().replace("*", "##") + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("<stepData idx=\"4\">");
			sb.append("<infoTitle><![CDATA[쪽수]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_BOOK_PAGE_VAL() + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("<stepData idx=\"5\">");
			sb.append("<infoTitle><![CDATA[제품 구성]]></infoTitle>");
			sb.append("<infoValue><![CDATA[상품상세참조]]></infoValue>");
			sb.append("</stepData>");
			sb.append("<stepData idx=\"6\">");
			sb.append("<infoTitle><![CDATA[출간일]]></infoTitle>");
			sb.append("<infoValue><![CDATA[" + bookInfo.get_OPENDATE().substring(0, 10) + "]]></infoValue>");
			sb.append("</stepData>");
			sb.append("<stepData idx=\"7\">");
			sb.append("<infoTitle><![CDATA[목차 또는 책소개]]></infoTitle>");
			sb.append("<infoValue><![CDATA[상품상세참조]]></infoValue>");
			sb.append("</stepData>");
			sb.append("</goodsMustInfoData>");

			sb.append("<taxFreeFl>f</taxFreeFl>");
			sb.append("<totalStock>9999</totalStock>");
			sb.append("<stockFl>y</stockFl>");
			sb.append("<soldOutFl>n</soldOutFl>");
			sb.append("<minOrderCnt>1</minOrderCnt>");
			sb.append("<maxOrderCnt>0</maxOrderCnt>");
			sb.append("<salesUnit>1</salesUnit>");
			sb.append("<mileageFl>c</mileageFl>");
			sb.append("<goodsDiscountFl>n</goodsDiscountFl>");
			sb.append("<goodsDiscount>" + goodsDiscount + "</goodsDiscount>");
			sb.append("<goodsDiscountUnit>percent</goodsDiscountUnit>");
			sb.append("<fixedPrice>" + bookInfo.get_PRICE() + "</fixedPrice>");
			sb.append("<costPrice>" + bookInfo.get_PRICE() + "</costPrice>");
			sb.append("<goodsPrice>" + SALEPRICE + "</goodsPrice>");
			sb.append("<optionFl>n</optionFl>");
			sb.append("<optionTextFl>n</optionTextFl>");
			sb.append("<addGoodsFl>n</addGoodsFl>");

			sb.append("<image_size_decrease>Y</image_size_decrease>");
			sb.append("<detailImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></detailImageData>");
			sb.append("<listImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></listImageData>");
			sb.append("<magnifyImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></magnifyImageData>");
			sb.append("<mainImageData idx=\"1\"><![CDATA[" + bookInfo.get_IMG_PATH2().replace("\\", "/")
					+ "]]></mainImageData>");

			sb.append("<shortDescription><![CDATA[]]></shortDescription>");
			sb.append("<goodsDescription><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></goodsDescription>");
			sb.append(
					"<goodsDescriptionMobile><![CDATA[" + GetGoodsBody_New(bookInfo) + "]]></goodsDescriptionMobile>");

			sb.append("<deliverySno>1</deliverySno>");

			sb.append("<relationFl>n</relationFl>");

			sb.append("<imgDetailViewFl>y</imgDetailViewFl>");

			sb.append("<detailInfoDelivery>002001</detailInfoDelivery>");
			sb.append("<detailInfoAS>003001</detailInfoAS>");
			sb.append("<detailInfoExchange>005001</detailInfoExchange>");
			sb.append("<detailInfoRefund>004001</detailInfoRefund>");
			
			sb.append("<externalVideoFl>n</externalVideoFl>");
			sb.append("<naverFl>y</naverFl>");
			sb.append("<naverAgeGroup>a</naverAgeGroup>");
			sb.append("<naverGender>c</naverGender>");
			sb.append("<naverTag><![CDATA[" + bookInfo.get_BOOK_NM() + "]]></naverTag>");
			sb.append("<scmNo>1</scmNo>");
			sb.append("<restockFl>n</restockFl>");
			sb.append("</goods_data>");
			sb.append("</data>");

			String sVal = "";

			File desti = new File("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\GodoxmlUpt\\");
			if (!desti.exists()) {
				desti.mkdirs();
			}

			stringToDom(sb.toString(), "D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") + "\\GodoxmlUpt\\"
					+ bookInfo.get_ISBN13() + ".xml");

			// NoBom("D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd") +
			// "\\GodoxmlInsert\\temp.xml","D:\\ShapLinker\\XML\\" + ex1(date, "yyyy-MM-dd")
			// + "\\GodoxmlInsert\\"+bookInfo.get_ISBN13()+".xml");
			sVal = getHttpHTML_POST("https://openhub.godo.co.kr/godomall5/goods/Goods_Update.php",
					HOST_IP() + "/GodoxmlUpt/" + bookInfo.get_ISBN13() + ".xml");

			if (!"".equals(sVal)) {

				Document doc = toXmlDocument(sVal);

				NodeList list = doc.getElementsByTagName("goods_data");
				if (list != null) {
					String PCode = "";
					String ResultCode = "";
					String ResultMsg = "";
					/*
					 * NodeList list_data = doc.getElementsByTagName("goodsno");
					 * 
					 * if (list_data !=null) { PCode =
					 * list_data.item(0).getFirstChild().getTextContent(); }
					 * 
					 */
					for (Node node = list.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {

						if (node.getNodeName().equals("code")) {
							ResultCode = node.getTextContent();
						}
						if (node.getNodeName().equals("msg")) {
							ResultMsg = node.getTextContent();
						}

					}

					if (ResultCode.equals("000")) {
						goodsDAO.UptRegItem(map);

					}
				}

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}

	public String GetGoodsBody_New(Goods bookInfo) {
		String Str_Contents = "";

		Str_Contents = Str_Contents + "<div  style=\"width: 900px; margin: 0 auto; float:none; text-align:left; \">";

		if (!"".equals(bookInfo.get_BOOK_INTRCN_CN()) && bookInfo.get_BOOK_INTRCN_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					"<p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 책소개</p>");
			// sb.append("<span style=\"position: absolute; right: 0; top:
			// 9px;text-align:left;\"></span> ");
			sb.append("<div  style=\"width: 745px;text-align:left;\">");
			sb.append("<span style = \"display:block;text-align:left;\" >");
			sb.append(bookInfo.get_BOOK_INTRCN_CN().replace("\r\n", "<br>") + "</div>");

			Str_Contents = Str_Contents + sb.toString();
		}
		if (!"".equals(bookInfo.get_CNTNT_CN()) && bookInfo.get_CNTNT_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" <p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 목차</p>");
			// sb.append("<span style=\"position: absolute; right: 0; top:
			// 9px;text-align:left;\"></span> ");
			sb.append("<div  style=\"width: 745px;text-align:left;\">");
			sb.append("<span style = \"display:block;text-align:left;\" >     ");
			sb.append(bookInfo.get_CNTNT_CN().replace("\r\n", "<br>") + "</div>");

			Str_Contents = Str_Contents + sb.toString();
		}
		if (!"".equals(bookInfo.get_PLSCMPN_BKRVW_CN()) && bookInfo.get_PLSCMPN_BKRVW_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" <p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 출판사서평</p>");
			// sb.append("<span style=\"position: absolute; right: 0; top:
			// 9px;text-align:left;\"></span> ");
			sb.append("<div style=\"width: 745px;text-align:left;\">");
			sb.append("<span style = \"display:block;text-align:left;\" >     ");
			sb.append(bookInfo.get_PLSCMPN_BKRVW_CN().replace("\r\n", "<br>") + "</div>");

			Str_Contents = Str_Contents + sb.toString();
		}
		if (!"".equals(bookInfo.get_AUTHR_INTRCN_CN()) && bookInfo.get_AUTHR_INTRCN_CN() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" <p style=\"font-size:16px;line-height:18px;height:26px;padding-top:4px;color:#000;font-family:'Malgun Gothic','Apple SD Gothic Neo','Dotum','Sans-Serif';\" >■ 저자소개</p>");
			// sb.append("<span class=\"topBtn\" style=\"position: absolute; right: 0; top:
			// 9px;text-align:left;\"></span>");
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
}
