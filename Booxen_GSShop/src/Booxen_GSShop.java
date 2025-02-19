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

import javax.net.ssl.HttpsURLConnection;
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
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

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


public class Booxen_GSShop {

	public static void main(String[] args) {
		try {
			GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
			// 주문등록
			try {
				
			
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
				
				
				System.exit(0);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		
			
		} catch (Exception e) {
		}

	}
		
}

class work {
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

	            StringEntity params =new StringEntity(sParam,"euc-kr");
	            // set header, demonstarte how to use hmac signature here
	            //requestPost.addHeader("Authorization", Key);
	          
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
	
	
	public void Get_OrderInfo() {
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        
        JSONObject OrderInfo = new JSONObject();        
        OrderInfo.clear();
        OrderInfo.put("sender", "BKS");
        OrderInfo.put("receiver", "GS SHOP");
        OrderInfo.put("documentId", "ORDINF");
        OrderInfo.put("processType", "S");
        OrderInfo.put("supCd", "1046582");
        cal.add(Calendar.DATE, -2);
        OrderInfo.put("sdDt", df.format(cal.getTime()));
        
        RequestOrder(OrderInfo.toString(),"http://realapi.gsshop.com/b2b/SupSendOrderInfo.gs");
        
        OrderInfo.clear();
        OrderInfo.put("sender", "BKS");
        OrderInfo.put("receiver", "GS SHOP");
        OrderInfo.put("documentId", "ORDINF");
        OrderInfo.put("processType", "S");
        OrderInfo.put("supCd", "1046582");
        cal.add(Calendar.DATE, 1);
        OrderInfo.put("sdDt", df.format(cal.getTime()));
        
        RequestOrder(OrderInfo.toString(),"http://realapi.gsshop.com/b2b/SupSendOrderInfo.gs");
        
        
        OrderInfo.clear();
        OrderInfo.put("sender", "BKS");
        OrderInfo.put("receiver", "GS SHOP");
        OrderInfo.put("documentId", "ORDINF");
        OrderInfo.put("processType", "S");
        OrderInfo.put("supCd", "1046582");
        cal.add(Calendar.DATE, 1);
        OrderInfo.put("sdDt", df.format(cal.getTime()));
        
        RequestOrder(OrderInfo.toString(),"http://realapi.gsshop.com/b2b/SupSendOrderInfo.gs");
        
        
	}
	
	
	public String RequestOrder(String sParam,String surl)
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
				 
				
				code = jsonObj.get("resultCd").toString();
				String data ="";
				if (code.equals("S"))
				{
				
					
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
	
	public String GetCategory(Goods bookInfo) {
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>();

		if (bookInfo.get_BX_CATECD() == null) {
			bookInfo.set_BX_CATECD("179909");
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

			StrCateGory = "B11010301";
		}

		return StrCateGory;
	}
	
	public String GetViewCategory(Goods bookInfo) {
		GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		java.util.HashMap<String, Object> Result_map = new HashMap<String, Object>();

		if (bookInfo.get_BX_CATECD() == null) {
			bookInfo.set_BX_CATECD("179909");
		}

		Result_map = goodsDAO.GetViewCategoryList(bookInfo.get_BX_CATECD());
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

			StrCateGory = "1515317";
		}

		return StrCateGory;
	}
	
	public String GetXml_Status(Goods bookInfo,double SALEPRICE) {
		String sRtn ="";
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		Date time = new Date();
				
		String time1 = format1.format(time);
		
		sRtn = "regGbn=U";					
		sRtn = sRtn + "&modGbn=S";
		sRtn = sRtn + "&regId=BKS";
		sRtn = sRtn + "&supPrdCd=" + bookInfo.get_ISBN13();
		sRtn = sRtn + "&supCd=1046582";
		if (bookInfo.get_BOOKSTS().equals("정상") || bookInfo.get_BOOKSTS().equals("현매"))
		{
			//sRtn = sRtn + "&saleEndDtm="+ time1;
			sRtn = sRtn + "&saleEndDtm=29991231235959";
		}else {
			//sRtn = sRtn + "&saleEndDtm=29991231235959";
			sRtn = sRtn + "&saleEndDtm="+ time1;	
		}
		
		sRtn = sRtn + "&attrSaleEndStModYn=Y";
		
		return sRtn;
	}
	public String GetXml_Price(Goods bookInfo,double SALEPRICE) {
		String sRtn ="";
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		Date time = new Date();
				
		String time1 = format1.format(time);
		int prdPrcSupGivRtamt = (int) Math.round(SALEPRICE);
		int prdDiscount = (int) Math.round(SALEPRICE * 0.15);
		
		sRtn = "regGbn=U";					
		sRtn = sRtn + "&modGbn=P";
		sRtn = sRtn + "&regId=BKS";
		sRtn = sRtn + "&supPrdCd=" + bookInfo.get_ISBN13();
		sRtn = sRtn + "&supCd=1046582";
		sRtn = sRtn + "&subSupCd=1046582";
		sRtn = sRtn + "&prdPrcValidStrDtm="+ time1;
		sRtn = sRtn + "&prdPrcValidEndDtm=29991231235959";
		sRtn = sRtn + "&prdPrcSalePrc="+ bookInfo.get_SALEPRICE();		
		sRtn = sRtn + "&prdPrcSupGivRtamtCd=01";
		sRtn = sRtn + "&prdPrcSupGivRtamt=" + (prdPrcSupGivRtamt - (int)(prdPrcSupGivRtamt * 0.15)) ;				
		
		return sRtn;
	}
	
    public static String substring(String parameterName, int maxLength) {
        int DB_FIELD_LENGTH = maxLength;
 
        Charset utf8Charset = Charset.forName("UTF-8");
        CharsetDecoder cd = utf8Charset.newDecoder();
 
        try {
            byte[] sba = parameterName.getBytes("UTF-8");
            // Ensure truncating by having byte buffer = DB_FIELD_LENGTH
            ByteBuffer bb = ByteBuffer.wrap(sba, 0, DB_FIELD_LENGTH); // len in [B]
            CharBuffer cb = CharBuffer.allocate(DB_FIELD_LENGTH); // len in [char] <= # [B]
            // Ignore an incomplete character
            cd.onMalformedInput(CodingErrorAction.IGNORE);
            cd.decode(bb, cb, true);
            cd.flush(cb);
            parameterName = new String(cb.array(), 0, cb.position());
        } catch (UnsupportedEncodingException e) {
            System.err.println("### 지원하지 않는 인코딩입니다." + e);
        }
 
        return parameterName;
    }
    public static int length(CharSequence sequence) {
        int count = 0;
        for (int i = 0, len = sequence.length(); i < len; i++) {
            char ch = sequence.charAt(i);
 
            if (ch <= 0x7F) {
                count++;
            } else if (ch <= 0x7FF) {
                count += 2;
            } else if (Character.isHighSurrogate(ch)) {
                count += 4;
                ++i;
            } else {
                count += 3;
            }
        }
        return count;
    }
	public String GetXml(Goods bookInfo,double SALEPRICE) {
		String sRtn ="";
		String GS_CATE_CD ="";
		try
		{
			String sName = bookInfo.get_BOOK_NM().length() > 15 ? bookInfo.get_BOOK_NM().substring(0,14) : bookInfo.get_BOOK_NM();
			String prdNmChgExposPrdNm=  bookInfo.get_BOOK_NM().length() > 30 ? bookInfo.get_BOOK_NM().substring(0,30) : bookInfo.get_BOOK_NM();
			sName= StringReplace(sName);
			
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
			Date time = new Date();
					
			String time1 = format1.format(time);
			
			sRtn = "regGbn=I";			
			//sRtn = sRtn + "&modGbn=";
			sRtn = sRtn + "&regId=BKS";
			sRtn = sRtn + "&prdCntntListCnt=1";  //			
			sRtn = sRtn + "&prdSpecListCnt=0";
			sRtn = sRtn + "&attrPrdListCnt=1";
			sRtn = sRtn + "&prdSectListCnt=1";
			
			
			sRtn = sRtn + "&prdGovPublsItmListCnt=7";
			sRtn = sRtn + "&prdDescdHtmlImgListCnt=0";
			
			sRtn = sRtn + "&supPrdCd=" + bookInfo.get_ISBN13();
			sRtn = sRtn + "&supCd=1046582";
			sRtn = sRtn + "&brandCd=248191";
			sRtn = sRtn + "&dlvPickMthodCd=3200";
			
			sRtn = sRtn + "&dlvsCoCd=HJ";
			sRtn = sRtn + "&saleStrDtm=" + time1;
			sRtn = sRtn + "&saleEndDtm=29991231235959";
			sRtn = sRtn + "&mnfcCoNm=" + URLEncoder.encode(String.valueOf(bookInfo.get_PUBLISHER()), "euc-kr") ;
			sRtn = sRtn + "&operMdId=82274";
			
			sRtn = sRtn + "&orgpNm=" + URLEncoder.encode("국내", "euc-kr") ;
			sRtn = sRtn + "&prdNm=" +  URLEncoder.encode(String.valueOf(StringReplace(sName)), "euc-kr") ; 
			sRtn = sRtn + "&ordPrdTypCd=02";
			
			if (bookInfo.get_ISBN13().substring(0,1).equals("8"))
			{
				sRtn = sRtn + "&taxTypCd=02";
				sRtn = sRtn + "&prdClsCd=1663279";
			}else
			{
				sRtn = sRtn + "&taxTypCd=01";
				sRtn = sRtn + "&prdClsCd=" + GetCategory(bookInfo);
				
			}			
			sRtn = sRtn + "&prdTypCd=P";
			
			if (Double.parseDouble(bookInfo.get_SALEPRICE()) >= 12000)
			{
				sRtn = sRtn + "&chrDlvYn=N";
				sRtn = sRtn + "&shipLimitAmt=12000";
			}else {
				sRtn = sRtn + "&chrDlvYn=Y";
				sRtn = sRtn + "&shipLimitAmt=12000";
				sRtn = sRtn + "&chrDlvcAmt=2500";
			}
			
			
			sRtn = sRtn + "&exchRtpChrYn=Y";
			sRtn = sRtn + "&rtpAmt=5000";  //반품비
			sRtn = sRtn + "&exchAmt=5000"; //교환비
			sRtn = sRtn + "&chrDlvAddYn=N";
			sRtn = sRtn + "&ilndDlvPsblYn=Y";
			sRtn = sRtn + "&jejuDlvPsblYn=Y";
			sRtn = sRtn + "&ilndChrDlvYn=Y";
			sRtn = sRtn + "&ilndChrDlvcAmt=3000";
			sRtn = sRtn + "&ilndExchRtpChrYn=Y";
			sRtn = sRtn + "&ilndRtpAmt=6000";
			sRtn = sRtn + "&ilndExchAmt=6000";
			sRtn = sRtn + "&jejuChrDlvYn=Y";
			sRtn = sRtn + "&jejuChrDlvcAmt=3000";
			sRtn = sRtn + "&jejuExchRtpChrYn=Y";
			sRtn = sRtn + "&jejuRtpAmt=6000";
			sRtn = sRtn + "&jejuExchAmt=6000";
			
			sRtn = sRtn + "&bundlDlvCd=A01";
			sRtn = sRtn + "&modelNo=" + bookInfo.get_ISBN13();
			sRtn = sRtn + "&openAftRtpNoadmtYn=N";
			
			sRtn = sRtn + "&prdRelspAddrCd=0001";
			sRtn = sRtn + "&prdRetpAddrCd=0001";
			
			sRtn = sRtn + "&subSupCd=1046582";
			sRtn = sRtn + "&ordMnfcYn=N";
		
			sRtn = sRtn + "&attrTypExposCd=L";
			sRtn = sRtn + "&adultCertYn=N";
			
			sRtn = sRtn + "&barcdNo=" + bookInfo.get_ISBN13();
			sRtn = sRtn + "&apntDlvDlvsCoCd=HJ";
			sRtn = sRtn + "&apntPickDlvsCoCd=HJ";
			
			sRtn = sRtn + "&frmlesPrdTypCd=N";
			sRtn = sRtn + "&rsrvSalePrdYn=N";
			sRtn = sRtn + "&attrTypNm1=";
			sRtn = sRtn + "&attrTypNm2=";
			sRtn = sRtn + "&attrTypNm3=";
			sRtn = sRtn + "&attrTypNm4=";
			
			sRtn = sRtn + "&prdBaseCmposCntnt=" +  URLEncoder.encode(StringReplace(sName), "euc-kr");
			sRtn = sRtn + "&orgprdPkgCnt=1";
			sRtn = sRtn + "&prdUnitValCd40=A01";
			sRtn = sRtn + "&prdUnitValCd20=B01";
			
			
			int prdPrcSupGivRtamt = (int) Math.round(SALEPRICE);
			int prdDiscount = (int) Math.round(SALEPRICE * 0.15);
		
			sRtn = sRtn + "&prdPrcValidStrDtm=" + time1;
			sRtn = sRtn + "&prdPrcValidEndDtm=29991231235959";			
			sRtn = sRtn + "&prdPrcSalePrc="+ bookInfo.get_SALEPRICE();
			sRtn = sRtn + "&prdPrcSupGivRtamtCd=01";			
			sRtn = sRtn + "&prdPrcSupGivRtamt=" + (int)Math.floor((prdPrcSupGivRtamt - (prdPrcSupGivRtamt * 0.15))) ;
			
			sRtn = sRtn + "&prdNmChgValidStrDtm=" + time1;
			sRtn = sRtn + "&prdNmChgValidEndDtm=29991231235959";
			
			sRtn = sRtn + "&prdNmChgExposPrdNm="+  URLEncoder.encode(String.valueOf(StringReplace(prdNmChgExposPrdNm)), "euc-kr") ;  
			sRtn = sRtn + "&prdNmChgExposPmoNm=";			
			//sRtn = sRtn + "&prdDescdHtmlImgUrl=" +  bookInfo.get_IMG_PATH2();
			sRtn = sRtn + "&prdCntntListCntntUrlNm=" + bookInfo.get_IMG_PATH2();
			
			//sRtn = sRtn + "&prdDescdHtmlDescdExplnCntnt=" + GetGoodsBody_New(bookInfo).replace("&", "");
			//sRtn = sRtn + "&prdDescdHtmlDescdExplnCntnt=" + URLEncoder.encode(GetGoodsBody_New(bookInfo).replace("&", ""), "euc-kr");;
			sRtn = sRtn + "&prdDescdHtmlDescdExplnCntnt=.";
			
			
			sRtn = sRtn + "&attrPrdListSupAttrPrdCd=" + bookInfo.get_ISBN13();		
			sRtn = sRtn + "&attrPrdListSaleStrDtm=" +time1;
			sRtn = sRtn + "&attrPrdListSaleEndDtm=29991231235959";
			sRtn = sRtn + "&attrPrdListModelNo=";
			sRtn = sRtn + "&attrPrdListAttrVal1=" + URLEncoder.encode("공통", "euc-kr") ;
			sRtn = sRtn + "&attrPrdListAttrVal2="+ URLEncoder.encode("공통", "euc-kr") ;
			sRtn = sRtn + "&attrPrdListAttrVal3="+ URLEncoder.encode("공통", "euc-kr") ;
			sRtn = sRtn + "&attrPrdListAttrVal4="+ URLEncoder.encode("공통", "euc-kr") ;
			sRtn = sRtn + "&attrPrdListOrgpNm=";
			sRtn = sRtn + "&attrPrdListMnfcCoNm=";
			sRtn = sRtn + "&attrPrdListSafeStockQty=10";
			sRtn = sRtn + "&attrPrdListTempoutYn=N";
			sRtn = sRtn + "&attrPrdListTempoutDtm=";
			sRtn = sRtn + "&attrPrdListOrdPsblQty=999";
			
			sRtn = sRtn + "&prdSectListSectid=" + GetViewCategory(bookInfo);
			sRtn = sRtn + "&prdSectListSectGbn=S";
			sRtn = sRtn + "&prdSectListSectStdYn=Y";
			
			sRtn = sRtn + "&safeCertGbnCd=0";
			sRtn = sRtn + "&safeCertOrgCd=0";
			sRtn = sRtn + "&safeCertModelNm=";
			sRtn = sRtn + "&safeCertNo=";
			sRtn = sRtn + "&safeCertDt=";			
			sRtn = sRtn + "&safeCertFileNm=";
			
			
			
			
		
			
			sRtn = sRtn + "&govPublsItmCd=3501";
			if (bookInfo.get_BOOK_NM().length() < 2)
			{
				sRtn = sRtn + "&govPublsItmCntnt="+ URLEncoder.encode("상품상세 설명 참고", "euc-kr") ;
				
			}else {
				sRtn = sRtn + "&govPublsItmCntnt="+ URLEncoder.encode(String.valueOf(StringReplace(bookInfo.get_BOOK_NM())), "euc-kr") ;
			}
			
			
			   
			sRtn = sRtn + "&govPublsItmCd=3502";
			if (bookInfo.get_AUTHR() == null || bookInfo.get_PUBLISHER() == null)
			{
				sRtn = sRtn + "&govPublsItmCntnt=" + URLEncoder.encode("상품상세 설명 참고", "euc-kr");
			}else {
				sRtn = sRtn + "&govPublsItmCntnt=" + URLEncoder.encode(bookInfo.get_AUTHR(), "euc-kr")  + "/" + URLEncoder.encode(bookInfo.get_PUBLISHER(), "euc-kr");
			}
			sRtn = sRtn + "&govPublsItmCd=3503";
			
			if (bookInfo.get_BOOK_WT_VAL() != null)
			{				
				if (bookInfo.get_BOOK_WT_VAL().equals("") || bookInfo.get_BOOK_WT_VAL().equals(" "))
				{
					sRtn = sRtn + "&govPublsItmCntnt=" + URLEncoder.encode("상품상세 설명 참고", "euc-kr");
				}else
				{
					sRtn = sRtn + "&govPublsItmCntnt=" + bookInfo.get_BOOK_WT_VAL();	
				}	
			}else
			{
				sRtn = sRtn + "&govPublsItmCntnt="  + URLEncoder.encode("상품상세 설명 참고", "euc-kr");
			}
			
		
			
			sRtn = sRtn + "&govPublsItmCd=3504";
			
			if (bookInfo.get_BOOK_PAGE_VAL() != null)
			{
				if (bookInfo.get_BOOK_PAGE_VAL().length() < 2)
				{
					sRtn = sRtn + "&govPublsItmCntnt="+URLEncoder.encode("상품상세 설명 참고", "euc-kr");
				}else {
					if (bookInfo.get_BOOK_PAGE_VAL().equals("") || bookInfo.get_BOOK_PAGE_VAL().equals(" "))
					{
						sRtn = sRtn + "&govPublsItmCntnt="+URLEncoder.encode("상품상세 설명 참고", "euc-kr");
					}else
					{
						sRtn = sRtn + "&govPublsItmCntnt=" +  URLEncoder.encode(bookInfo.get_BOOK_PAGE_VAL(), "euc-kr");
					}	
				}
				
			}else
			{
				sRtn = sRtn + "&govPublsItmCntnt="+URLEncoder.encode("상품상세 설명 참고", "euc-kr");
			}
			
			
			sRtn = sRtn + "&govPublsItmCd=3505";
			sRtn = sRtn + "&govPublsItmCntnt="+URLEncoder.encode("상품상세 설명 참고", "euc-kr");
			sRtn = sRtn + "&govPublsItmCd=3506";
			sRtn = sRtn + "&govPublsItmCntnt="+URLEncoder.encode("상품상세 설명 참고", "euc-kr");
			sRtn = sRtn + "&govPublsItmCd=3507";
			sRtn = sRtn + "&govPublsItmCntnt="+URLEncoder.encode("상품상세 설명 참고", "euc-kr");
			
			sRtn = sRtn + "&prdBookBookPrdGbnCd=B";
			sRtn = sRtn + "&prdBookIsbnNo=" + bookInfo.get_ISBN13();
			sRtn = sRtn + "&prdBookOrgTitleNm="+ URLEncoder.encode(StringReplace(bookInfo.get_BOOK_NM()), "euc-kr"); 
			sRtn = sRtn + "&prdBookAuthrNm=" + URLEncoder.encode(bookInfo.get_AUTHR(), "euc-kr"); 
			sRtn = sRtn + "&prdBookPublcCoNm="+ URLEncoder.encode(bookInfo.get_PUBLISHER(), "euc-kr");	
		
			
			if (bookInfo.get_BOOK_PAGE_VAL() != null)
			{
				sRtn = sRtn + "&prdBookPageCnt=" + StringReplaceDigital(bookInfo.get_BOOK_PAGE_VAL().replace("쪽", "").replace(" ", ""));
			}else {
				sRtn = sRtn + "&prdBookPageCnt=0";
			}
			
			sRtn = sRtn + "&prdBookIssueDt=" + bookInfo.get_OPENDATE().replace("-", "");
			sRtn = sRtn + "&prdBookDmstcFixprc="+ bookInfo.get_PRICE().replace(".0", "");
			sRtn = sRtn + "&prdBookCurrunCd=1";
			sRtn = sRtn + "&prdBookCentStockYn=Y";
			/*
			if (!bookInfo.get_PLSCMPN_BKRVW_CN().equals(""))
			{
				sRtn = sRtn + "&prdDescdHtmlItmListDescdItmNm=" + URLEncoder.encode("출판사 리뷰", "euc-kr");	
				sRtn = sRtn + "&prdDescdHtmlItmListDescdExplnCntnt="+ URLEncoder.encode(bookInfo.get_PLSCMPN_BKRVW_CN(), "euc-kr");		
			}else if (!bookInfo.get_AUTHR_INTRCN_CN().equals(""))
			{
				sRtn = sRtn + "&prdDescdHtmlItmListDescdItmNm=" + URLEncoder.encode("저자소개", "euc-kr");	
				sRtn = sRtn + "&prdDescdHtmlItmListDescdExplnCntnt="+ URLEncoder.encode(bookInfo.get_AUTHR_INTRCN_CN(), "euc-kr");		
			}else if (!bookInfo.get_BOOK_INTRCN_CN().equals(""))
			{
				sRtn = sRtn + "&prdDescdHtmlItmListDescdItmNm=" + URLEncoder.encode("책소개", "euc-kr");	
				sRtn = sRtn + "&prdDescdHtmlItmListDescdExplnCntnt="+ URLEncoder.encode(bookInfo.get_BOOK_INTRCN_CN(), "euc-kr");		
			}				
		*/
			
			sRtn = sRtn + "&prdDescdHtmlItmListDescdItmNm=" + URLEncoder.encode("기술서", "euc-kr");	
			sRtn = sRtn + "&prdDescdHtmlItmListDescdExplnCntnt="+ URLEncoder.encode(GetGoodsBody_New(bookInfo).replace("&", ""), "euc-kr");
			
			//sRtn = sRtn + "&prdDescdHtmlItmListDescdItmNm="+bookInfo.get_PLSCMPN_BKRVW_CN();
			//sRtn = sRtn + "&prdDescdHtmlItmListDescdExplnCntnt="+GetGoodsBody_New(bookInfo).replace("&", "");			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return sRtn;
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
	public void UptGoodsSaleAPI(Goods bookInfo) {
		double SALEPRICE = 0;

		try {
			if (Double.parseDouble(bookInfo.get_INRT()) > 80)
				return;

			if (Double.parseDouble(bookInfo.get_INRT()) <= 67) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 67 && Double.parseDouble(bookInfo.get_INRT()) < 73) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();

			} else if (Double.parseDouble(bookInfo.get_INRT()) > 72 && Double.parseDouble(bookInfo.get_INRT()) < 81) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			}
			
			bookInfo.set_SALEPRICE(String.valueOf((int) Math.round(SALEPRICE)));
			//bookInfo.set_SALEPRICE("100000");
			String sRtn = GetXml_Price(bookInfo, SALEPRICE);
			  System.out.println( sRtn);
			  
			  if (!"".equals(sRtn)) {
					String sVal = "";	
					 bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
					sVal = sendPost_Up(sRtn, "https://realapi.gsshop.com/alia/aliaCommonPrd.gs", bookInfo);				

				}
			  

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	public void UptStatusAPI(Goods bookInfo) {
		
		double SALEPRICE = 0;

		try {
			/*
			if (Double.parseDouble(bookInfo.get_INRT()) > 80)
				return;
*/
			if (Double.parseDouble(bookInfo.get_INRT()) <= 67) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 67 && Double.parseDouble(bookInfo.get_INRT()) < 73) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();

			} else if (Double.parseDouble(bookInfo.get_INRT()) > 72 && Double.parseDouble(bookInfo.get_INRT()) < 81) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			}
			bookInfo.set_SALEPRICE(String.valueOf((int) Math.round(SALEPRICE)));
			String sRtn = GetXml_Status(bookInfo, SALEPRICE);
			  System.out.println( sRtn);
              
			if (!"".equals(sRtn)) {
				String sVal = "";					
				sVal = sendPost_Up(sRtn, "https://realapi.gsshop.com/alia/aliaCommonPrd.gs", bookInfo);				

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

			if (Double.parseDouble(bookInfo.get_INRT()) <= 67) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.9;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();
			} else if (Double.parseDouble(bookInfo.get_INRT()) > 67 && Double.parseDouble(bookInfo.get_INRT()) < 73) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE()) * 0.95;				
				BigDecimal incm_civil_amt = new BigDecimal (SALEPRICE);   
				incm_civil_amt = incm_civil_amt.setScale(-1, BigDecimal.ROUND_DOWN);    				
				SALEPRICE = incm_civil_amt.doubleValue();

			} else if (Double.parseDouble(bookInfo.get_INRT()) > 72 && Double.parseDouble(bookInfo.get_INRT()) < 81) {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			} else {
				SALEPRICE = Double.parseDouble(bookInfo.get_PRICE());
			}

			bookInfo.set_SALEPRICE(String.valueOf((int) Math.round(SALEPRICE)));
			String sRtn = GetXml(bookInfo, SALEPRICE);
			 //System.out.println( sRtn);
              
			if (!"".equals(sRtn)) {
				String sVal = "";	
				//bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				sVal = sendPost(sRtn, "https://realapi.gsshop.com/alia/aliaCommonPrd.gs", bookInfo);				

			}

		} catch (Exception e) {
			InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
		}
	}
	public static String StringReplace(String str){       
	    String match = "[^\uAC00-\uD7A30-9a-zA-Z]";
	    str = str.replaceAll(match, " ");
	    return str;
	}
	
	public  String StringReplaceDigital(String str){       
	    String match = "[^0-9]";
	    str = str.replaceAll(match, " ");
	    return str;
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
	
	private String sendPost_Up(String sParam, String surl,Goods bookInfo) throws Exception 
	{ 
		URL url = new URL(surl); 
		HttpsURLConnection con = (HttpsURLConnection) 
				url.openConnection();
		con.setRequestMethod("POST"); // HTTP POST 메소드 설정
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=euc-kr");
		con.setRequestProperty("Content-Language","ko-KR");
		con.setDoOutput(true); // POST 파라미터 전달을 위한 설정 // Send post request 
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(sParam);
		wr.flush();
		wr.close(); 
		int responseCode = con.getResponseCode(); 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
		String inputLine; 
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) 
		{ 
			response.append(inputLine);
		} 
		in.close(); // print result 
		System.out.println("HTTP 응답 코드 : " + responseCode); 
		System.out.println("HTTP body : " + response.toString()); 
		
		if (responseCode == 200)
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject)jsonParser.parse( response.toString() );										
			String success = jsonObj.get("success").toString();		
			String msg= jsonObj.get("msg").toString();
		
			
			
			if (success.equals("true") &&  msg.indexOf("성공") > -1 )
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
				
			}else {
				InsertError("RegGoods",bookInfo.get_ISBN13(),"",response.toString());
			}
		}
		return "";
	}
	
	
	private String sendPost(String sParam, String surl,Goods bookInfo) throws Exception 
	{ 
		URL url = new URL(surl); 
		HttpsURLConnection con = (HttpsURLConnection) 
				url.openConnection();
		con.setRequestMethod("POST"); // HTTP POST 메소드 설정
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=euc-kr");
		con.setRequestProperty("Content-Language","ko-KR");
		con.setDoOutput(true); // POST 파라미터 전달을 위한 설정 // Send post request 
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(sParam);
		wr.flush();
		wr.close(); 
		int responseCode = con.getResponseCode(); 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); 
		String inputLine; 
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) 
		{ 
			response.append(inputLine);
		} 
		in.close(); // print result 
		System.out.println("HTTP 응답 코드 : " + responseCode); 
		System.out.println("HTTP body : " + response.toString()); 
		
		if (responseCode == 200)
		{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject)jsonParser.parse( response.toString() );										
			String success = jsonObj.get("success").toString();		
			String msg= jsonObj.get("msg").toString();
		
			
			
			if (success.equals("true") &&  msg.indexOf("성공") > -1 )
			{
				String prdCd = jsonObj.get("prdCd").toString();
				GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				
				java.util.HashMap<String, Object> map = new HashMap<String, Object>();						
				map.put("ITEMCD", bookInfo.get_ITEMCD());
				map.put("PRODUCTNO", prdCd);
				map.put("ISBN13", bookInfo.get_ISBN13());
				map.put("BOOK_NM", bookInfo.get_BOOK_NM());
				map.put("BOOKSTS", bookInfo.get_BOOKSTS());
				map.put("SALEPRICE", bookInfo.get_SALEPRICE());
				map.put("PRICE", bookInfo.get_PRICE());


				goodsDAO.InsertRegItem(map);
				
			}else {
				InsertError("RegGoods",bookInfo.get_ISBN13(),"",response.toString());
			}
		}
		return "";
	}
	

	public String getHttpHTML_POST_Reg(String sParam, String surl,Goods bookInfo) {
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
            requestPost.addHeader("Accept-Language", "ko");
            //requestPost.addHeader("X-Timezone", "GMT+09:00");
          
            //requestPost.addHeader("content-type", "application/json");
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
				String prdCd = jsonObj.get("prdCd").toString();
				String success = jsonObj.get("success").toString();		
				String msg= jsonObj.get("msg").toString();
			
				
				
				if (success.equals("true") &&  msg.indexOf("성공") > -1 )
				{
					
					GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
					
					java.util.HashMap<String, Object> map = new HashMap<String, Object>();						
					map.put("ITEMCD", bookInfo.get_ITEMCD());
					map.put("PRODUCTNO", prdCd);
					map.put("ISBN13", bookInfo.get_ISBN13());
					map.put("BOOK_NM", bookInfo.get_BOOK_NM());
					map.put("BOOKSTS", bookInfo.get_BOOKSTS());
					map.put("SALEPRICE", bookInfo.get_SALEPRICE());
					map.put("PRICE", bookInfo.get_PRICE());


					goodsDAO.InsertRegItem(map);
					
				}else {
					InsertError("RegGoods",bookInfo.get_ISBN13(),"",strResult);
				}
				
               
            } catch (Exception e) {
                e.printStackTrace();
                InsertError("RegGoods",bookInfo.get_ISBN13(),"","에러");
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
        
        return resultMessage;
    }
	
}

