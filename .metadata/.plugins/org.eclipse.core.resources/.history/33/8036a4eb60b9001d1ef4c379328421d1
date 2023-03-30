import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

import dao.GoodsDAO;
import dao.OrderDAO;
import model.Goods;
import model.Order;
import mybatis.MyBatisConnectionFactory;

public class Booxen_Lotteon {

	public static void main(String[] args) {
			try {
				GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				
				// 상품등록
				try {
					/*
					System.out.println("=====송장입력 start=====");
					//송장입력
			    	try {
			    		 work wk = new work();
			    		wk.GetTransNoList("51071"); //
			    		
			    	}catch (Exception e) {
						// TODO: handle exception
					}
			    	
			    	System.out.println("=====송장입력 end=====");
			    	  
			    	*/
				   
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
								String sitmNo = wk.Get_sitmNo(bookInfo);
								
								if (!sitmNo.equals("")) {
									wk.UptPriceAPI(bookInfo,sitmNo);	
								}
								
								
							}
						}
					} catch (Exception e) {
					}
					System.out.println("=====상품가격수정 End=====");
					
					//Thread.sleep(100000000);
					System.exit(0);
					
				} catch (Exception e) {
					e.printStackTrace();
					
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
				String Key="Bearer 5d5b2cb498f3d20001665f4e6c4dd70be6a147ceaabc99e7fe06e9db";	
				
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
					String procSeq = (String)map.get("SHOPLINKER_ORDER_ID");
					String odSeq =	String.valueOf((BigDecimal)map.get("ORD_NO"));
					String ISBN13=	(String)map.get("ISBN13");
					String spdNo ="";
					String sitmNo ="";
					String SELL_QTY=String.valueOf((BigDecimal)map.get("SELL_QTY"));
					if (!procSeq.equals(""))
					{
						try
						{
							 java.util.HashMap<String, Object> Map_Info = new HashMap<String, Object>(); 
							 Map_Info.put("ISBN13", ISBN13);
					
							GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
							List<Goods> bookInfoList = goodsDAO.GetGoodsInfo(Map_Info);
							
							
								 
							 for (Goods bookInfo : bookInfoList) {
								 spdNo = bookInfo.get_PRODUCTNO();
								  sitmNo = Get_sitmNo(bookInfo);
							}
							
								Calendar cal = Calendar.getInstance();
						        cal.setTime(new Date());
						        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
						        
						    JSONArray ArrayConfirm = new JSONArray();	  
							JSONObject ObjectBundle = new JSONObject();	
							JSONObject ObjectConfirm = new JSONObject();
							
							    							       
							ObjectBundle.put("odNo", ORD_ID);
							ObjectBundle.put("odSeq", odSeq);
							ObjectBundle.put("procSeq", procSeq);
							ObjectBundle.put("odPrgsStepCd", "13");
							ObjectBundle.put("dvTrcStatDttm", df.format(cal.getTime()));
							
							ObjectBundle.put("dvCoCd", "0006");
							ObjectBundle.put("invcNo", TRNS_NO);
							ObjectBundle.put("invcNbr", 1);
							
							ObjectBundle.put("spdNo", spdNo);
							ObjectBundle.put("sitmNo", sitmNo);
							ObjectBundle.put("slQty", SELL_QTY);
							ArrayConfirm.add(ObjectBundle);
							ObjectConfirm.put("deliveryProgressStateList", ArrayConfirm);
						 
							
							 String code = setOrderConfirm(ObjectConfirm.toJSONString(),Key,"https://openapi.lotteon.com/v1/openapi/delivery/v1/SellerDeliveryProgressStateInform");
							
							 if (code.equals("0000"))
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
		
		
		public void Get_OrderInfo() {
				//운영
			String Key="Bearer 5d5b2cb498f3d20001665f4e6c4dd70be6a147ceaabc99e7fe06e9db";
			Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	        
	        JSONObject OrderInfo = new JSONObject();
	        
	        
	        cal.add(Calendar.HOUR, -23);
	        //OrderInfo.put("fromDate", df.format(cal.getTime()));
	        OrderInfo.put("srchStrtDt","20230302000001");
	        
	        cal.add(Calendar.HOUR, 23);
	        //OrderInfo.put("toDate", df.format(cal.getTime()));
	        OrderInfo.put("srchEndDt","20230302235959");
	        OrderInfo.put("odPrgsStepCd", "11");
	        
	      
	        //운영
	        String sVal = getHttpHTML_POST_Order(OrderInfo.toJSONString(),Key,"https://openapi.lotteon.com/v1/openapi/delivery/v1/SellerDeliveryProgressStateSearch");
	    
	        if (!sVal.equals("")) {
	        	try
	        	{
	        		JSONParser jsonParser = new JSONParser();
	   		     	JSONObject jsonObj = (JSONObject)jsonParser.parse(sVal);							 				 				 				 			   		     	
	   		     	String data = jsonObj.get("data").toString();
	   		     	
	   		     	JSONObject jsonBundle = (JSONObject)jsonParser.parse(data);							 				 				
	   		     	JSONArray jsonArray_Bundle = (JSONArray)jsonBundle.get("deliveryProgressStateList");
		   		 	
	   		     	for(int i=0;i<jsonArray_Bundle.size();i++)
	   		     	{
	   		     		try
	   		     		{
		   		     		JSONObject jsonObjOrder = (JSONObject)jsonArray_Bundle.get(i);
			   		 		
			   		 		String orderDate = jsonObjOrder.get("odCmptDttm").toString().substring(0,8);
							String odrNm = jsonObjOrder.get("odrNm").toString();
							String odNo=jsonObjOrder.get("odNo").toString();
							String odSeq = jsonObjOrder.get("odSeq").toString();
							String procSeq= jsonObjOrder.get("procSeq").toString();
							String odQty = jsonObjOrder.get("odQty").toString();
							String slAmt= jsonObjOrder.get("slAmt").toString();
							String slPrc= jsonObjOrder.get("slPrc").toString();
							String dvpZipNo= jsonObjOrder.get("dvpZipNo").toString();
							String dvpStnmZipAddr= jsonObjOrder.get("dvpStnmZipAddr").toString();
							String dvpStnmDtlAddr= jsonObjOrder.get("dvpStnmDtlAddr") !="" ? jsonObjOrder.get("dvpStnmDtlAddr").toString() : "";
							String dvpCustNm= jsonObjOrder.get("dvpCustNm") != null ? jsonObjOrder.get("dvpCustNm").toString() :jsonObjOrder.get("odrNm").toString();
							String dvpMphnNo= jsonObjOrder.get("dvpMphnNo") != null? jsonObjOrder.get("dvpMphnNo").toString() : jsonObjOrder.get("dvpTelNo").toString();
							String dvpTelNo= jsonObjOrder.get("dvpTelNo") != null ? jsonObjOrder.get("dvpTelNo").toString() : jsonObjOrder.get("dvpMphnNo").toString();
							String dvMsg= jsonObjOrder.get("dvMsg") != null ? jsonObjOrder.get("dvMsg").toString() : "";
							String pdNm= jsonObjOrder.get("pdNm") != null ? jsonObjOrder.get("pdNm").toString() : "";
							String epdNo= jsonObjOrder.get("epdNo") !=null ? jsonObjOrder.get("epdNo").toString() : "";
							String dvCst= jsonObjOrder.get("dvCst") !=null ? jsonObjOrder.get("dvCst").toString(): "";
							
							String spdNo= jsonObjOrder.get("spdNo").toString();
							String sitmNo= jsonObjOrder.get("sitmNo").toString();
							
							
							
						
			   		 		 if (!epdNo.equals("") && (epdNo.substring(0, 1).equals("8") || epdNo.substring(0, 1).equals("9"))) {
			   		 			Order Order = new Order();
			   		 			
								Order.Set_ORD_DATE(orderDate);
								Order.Set_ORD_ID(odNo);
								Order.Set_ISBN13(epdNo);
								Order.Set_ORD_NO(odSeq);
								Order.Set_SELL_QTY(odQty);
								Order.Set_SELL_AMT(slPrc);
								Order.Set_DLV_PRICE(dvCst);
								Order.Set_DLV_POST(dvpZipNo);
								Order.Set_DLV_ADDR1(dvpStnmZipAddr);
								Order.Set_DLV_ADDR2(dvpStnmDtlAddr);
								Order.Set_DLV_MSG(dvMsg);
								Order.Set_DLV_NM(dvpCustNm);
								Order.Set_DLV_TEL(dvpTelNo);
								Order.Set_DLV_HP(dvpMphnNo);
								Order.Set_ITEM_NM(pdNm);
								
			   		 			
				   		 		java.util.HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("ORD_ID", Order.Get_ORD_ID());
								map.put("ISBN13", Order.Get_ISBN13());
								map.put("ORD_NO", Order.Get_ORD_NO());
			
								OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			
								int API_MstCnt = orderDAO.GetExistBooxenOrderList(map);
								
								if (API_MstCnt < 1) {
									double SELL_AMT = Double.parseDouble(Order.Get_SELL_AMT());
			 						double SELL_TOTAMT = SELL_AMT * Double.parseDouble(Order.Get_SELL_QTY());
			 						 
									java.util.HashMap<String, Object> maping = new HashMap<String, Object>();
									maping.put("ORD_DATE", Order.Get_ORD_DATE().replace("-", ""));
									maping.put("SELLER_ID", "booxen");
									maping.put("MALL_ID", "51071");
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
									maping.put("SHOPLINKER_ORDER_ID", procSeq);
									try {
									
										if (orderDAO.InsertBooxenOrdList(maping) > 0) {									
											System.out.println("=====주문확정=====" + odNo);
											
											SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
											Date time = new Date();
													
											String time1 = format1.format(time);
											
											JSONObject ObjectConfirm = new JSONObject();
											JSONObject ObjectConfirm_Info = new JSONObject();
											
											JSONArray ArrayConfirm = new JSONArray();	
											
											ObjectConfirm_Info.put("odNo", odNo);
											ObjectConfirm_Info.put("odSeq",odSeq );
											ObjectConfirm_Info.put("procSeq", procSeq);
											ObjectConfirm_Info.put("odPrgsStepCd", "12");
											ObjectConfirm_Info.put("dvTrcStatDttm",time1 );
											ObjectConfirm_Info.put("spdNo", spdNo);
											ObjectConfirm_Info.put("sitmNo", sitmNo);
											ObjectConfirm_Info.put("slQty",odQty.replace(".0", "") );
											
											ArrayConfirm.add(ObjectConfirm_Info);
											
											ObjectConfirm.put("deliveryProgressStateList", ArrayConfirm);
											
											String sCode = getHttpHTML_POST_Order(ObjectConfirm.toJSONString(),Key,"https://openapi.lotteon.com/v1/openapi/delivery/v1/SellerDeliveryProgressStateInform");
									
										}
									
								} catch (Exception ex) {
									
									System.out.println(ex.getMessage());
									
									InsertError("RegOrder", Order.Get_ORD_ID(), "", ex.getMessage());
								}
								
								
							
		   		 		 }
		   		 			
		   		 		}
	   		     		}catch (Exception e) {
							// TODO: handle exception
	   		     			continue;
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
	            requestPost.addHeader("Authorization", Key);
	            requestPost.addHeader("Accept-Language", "ko");
	            requestPost.addHeader("X-Timezone", "GMT+09:00");
	          
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
					 
					
					code = jsonObj.get("returnCode").toString();
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

				
				bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
				String sRtn = GetXml_Status(bookInfo);
				
				sRtn = sRtn.replace("&", "");
				String Key="Bearer 5d5b2cb498f3d20001665f4e6c4dd70be6a147ceaabc99e7fe06e9db";	
				
				if (!"".equals(sRtn)) {
					String sVal = "";							
					sVal = getHttpHTML_POST_Upt(sRtn, Key, "https://openapi.lotteon.com/v1/openapi/product/v1/product/status/change",bookInfo);				

				}
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		
		}
		
		
	public void UptPriceAPI(Goods bookInfo,String sitmNo) {
			
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
				
			
				
				bookInfo.set_SALEPRICE(String.valueOf( Math.round(SALEPRICE)));			
				String sRtn = GetXml_Price(bookInfo,sitmNo);
				
				sRtn = sRtn.replace("&", "");
				String Key="Bearer 5d5b2cb498f3d20001665f4e6c4dd70be6a147ceaabc99e7fe06e9db";	
				
				if (!"".equals(sRtn)) {
					String sVal = "";			
					
					sVal = getHttpHTML_POST_Upt(sRtn, Key, "https://openapi.lotteon.com/v1/openapi/product/v1/item/price/change",bookInfo);				

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
				
				sRtn = sRtn.replace("&", "");
				
				//운영
				String Key="Bearer 5d5b2cb498f3d20001665f4e6c4dd70be6a147ceaabc99e7fe06e9db";			
				if (!"".equals(sRtn)) {
					String sVal = "";	
					bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
					sVal = getHttpHTML_POST_Reg(sRtn, Key, "https://openapi.lotteon.com/v1/openapi/product/v1/product/registration/request", bookInfo);				

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

				String sRtn = GetXml_Upt(bookInfo, SALEPRICE);
			     System.out.println("sRtn:" + sRtn);
	             
		           
				sRtn = sRtn.replace("&", "");
				
				//운영
				String Key="Bearer 5d5b2cb498f3d20001665f4e6c4dd70be6a147ceaabc99e7fe06e9db";			
				if (!"".equals(sRtn)) {
					String sVal = "";	
					bookInfo.set_SALEPRICE(String.valueOf(SALEPRICE));
					sVal = Send_POST_Upt(sRtn, Key, "https://openapi.lotteon.com/v1/openapi/product/v1/product/modification/request", bookInfo);				

				}

			} catch (Exception e) {
				InsertError("RegGoods", bookInfo.get_ISBN13(), bookInfo.get_ITEMCD(), e.getMessage());
			}
		}
		
		public String GetXml_Upt(Goods bookInfo, double SALEPRICE) throws SQLException {
			//최종 완성될 JSONObject 선언(전체)
	        JSONObject jsonObject = new JSONObject();              
	        JSONObject items = new JSONObject(); 
	        JSONArray arr_spdLst = new JSONArray();               
	        
	        String Standard_Cat ="";
	        String Disp_Cat ="";
	        
	        items.put("epdNo", bookInfo.get_ISBN13());
	        items.put("trGrpCd", "SR");
	        
	        if (bookInfo.get_BX_CATECD() == null) {
	        	Standard_Cat ="BN70011970";
	        	Disp_Cat ="EC10400937";
			}else {
				GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				
				java.util.HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("BOOXEN_CATE_CD", bookInfo.get_BX_CATECD());

				HashMap<String, Object> list = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());
				
				for (int i = 0; i < list.size(); i++) {
					HashMap map = (HashMap) list.get(i);

					Standard_Cat = (String) map.get("STANDARD_CATE_CD");
					Disp_Cat = (String) map.get("DISP_CATE_CD");				
				}
			}

	        String sitmNo = Get_sitmNo(bookInfo);
	        
	        items.put("spdNo", bookInfo.get_PRODUCTNO());    
	        items.put("trNo", "LD477577");        
	        items.put("scatNo", Standard_Cat);
	        
	        
	        
	        JSONArray arr_dcatLst = new JSONArray();  // 전시카테고리
	        JSONObject dcatLst_items = new JSONObject();
	        dcatLst_items.put("mallCd", "LTON");
	        dcatLst_items.put("lfDcatNo",Disp_Cat);
	        arr_dcatLst.add(dcatLst_items);
	        items.put("dcatLst",arr_dcatLst);
	        
	        
	        items.put("slTypCd","GNRL");
	        items.put("pdTypCd","GNRL_GNRL");
	        items.put("spdNm",bookInfo.get_BOOK_NM());
	        items.put("mfcrNm",bookInfo.get_PUBLISHER());
	        items.put("oplcCd","KR");
	        
	        
	        if (bookInfo.get_ISBN13().substring(0,1).equals("8"))
	        {
	        	items.put("tdfDvsCd","01");   //과세
	        }
	        else
	        {
	        	items.put("tdfDvsCd","02");   //면세
	        }
	        
	        
	       
	        
	        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
			Date time = new Date();
					
			String time1 = format1.format(time);
	        
			items.put("slStrtDttm",time1); 
			items.put("slEndDttm","20991230000000"); 
			
			
			
			 JSONObject NoticeInfo = new JSONObject();
			 NoticeInfo.put("pdItmsCd","26"); 
			 
			 JSONObject NoticeInfo1 = new JSONObject();
			 NoticeInfo1.put("pdArtlCd","0830"); 
			 NoticeInfo1.put("pdArtlCnts",bookInfo.get_BOOK_NM());
			 
			 JSONObject NoticeInfo2 = new JSONObject();
			 NoticeInfo2.put("pdArtlCd","0840"); 
			 NoticeInfo2.put("pdArtlCnts",bookInfo.get_AUTHR() + "/" + bookInfo.get_PUBLISHER());
			 
			 JSONObject NoticeInfo3 = new JSONObject();
			 NoticeInfo3.put("pdArtlCd","0870"); 
			 NoticeInfo3.put("pdArtlCnts",bookInfo.get_BOOK_WT_VAL() );
			 
			 JSONObject NoticeInfo4 = new JSONObject();
			 NoticeInfo4.put("pdArtlCd","0880"); 
			 NoticeInfo4.put("pdArtlCnts",bookInfo.get_BOOK_PAGE_VAL() );
			 
			 JSONObject NoticeInfo5 = new JSONObject();
			 NoticeInfo5.put("pdArtlCd","0860"); 
			 NoticeInfo5.put("pdArtlCnts","상품상세 설명 참고" );
			 
			 JSONObject NoticeInfo6 = new JSONObject();
			 NoticeInfo6.put("pdArtlCd","0850"); 
			 NoticeInfo6.put("pdArtlCnts","상품상세 설명 참고" );
			 
			 JSONObject NoticeInfo7 = new JSONObject();
			 NoticeInfo7.put("pdArtlCd","0890"); 
			 NoticeInfo7.put("pdArtlCnts","상품상세 설명 참고" );
			 
			 JSONArray NoticeValueArray = new JSONArray();               
			 NoticeValueArray.add(NoticeInfo1);
	         NoticeValueArray.add(NoticeInfo2);
	         NoticeValueArray.add(NoticeInfo3);
	         NoticeValueArray.add(NoticeInfo4);
	         NoticeValueArray.add(NoticeInfo5);
	         NoticeValueArray.add(NoticeInfo6);
	         NoticeValueArray.add(NoticeInfo7);
	         
	         NoticeInfo.put("pdItmsArtlLst", NoticeValueArray);
	         items.put("pdItmsInfo", NoticeInfo);
	         
	         JSONObject ColpurPsbQtyInfo = new JSONObject();    //구매가능수량정보
	         ColpurPsbQtyInfo.put("itmByMinPurYn", "Y");
	         ColpurPsbQtyInfo.put("itmByMinPurQty", 1);
	         ColpurPsbQtyInfo.put("itmByMaxPurPsbQtyYn", "Y");
	         ColpurPsbQtyInfo.put("maxPurQty", "1000");
	         items.put("purPsbQtyInfo", ColpurPsbQtyInfo);
	         
	         
	         items.put("ageLmtCd", "0");   //연령제한코드   
	         items.put("prstPsbYn", "N");   //선물가능여부    
	         items.put("prcCmprEpsrYn", "Y");   //가격비교노출여부     
	         items.put("bookCultCstDdctYn", "Y");   //도서문화비 공제여부 
	         items.put("isbnCd", bookInfo.get_ISBN13());   //ISBN
	         items.put("pdStatCd", "NEW");   //상품상태코드 

	         JSONArray epnLstArray = new JSONArray(); //상품설명
	         JSONObject ColepnLst = new JSONObject();
	         ColepnLst.put("pdEpnTypCd", "DSCRP");
	         ColepnLst.put("cnts", GetGoodsBody_New(bookInfo));

	         epnLstArray.add(ColepnLst);
	         items.put("epnLst",epnLstArray);

	         items.put("dvProcTypCd", "LO_ENTP");   //배송처리유형코드    
	         items.put("dvPdTypCd", "GNRL");   //배송상품유형코드     
	         items.put("sndBgtNday", "3");   //발송예정일수    
	         
	         
	         items.put("dvRgsprGrpCd", "GN000");   //배송권역그룹코드    
	         items.put("dvMnsCd", "DPCL");   //배송수단코드     
	         items.put("owhpNo", "PLO336024");   //출고지번호    
	         items.put("hdcCd", "0006");   //택배  
	         items.put("dvCstPolNo", "368987");   //배송비정책번호  
	         items.put("adtnDvCstPolNo", "368990");   //추가배송비정책번호 				  
	         items.put("rtrpNo", "PLO336024");   //회수지번호    
	         items.put("stkMgtYn", "Y");   //재고관리여부     				  
	         items.put("sitmYn", "N");   //판매자단품여부     
	         
	         
	         JSONObject ColitmLst = new JSONObject();
	         ColitmLst.put("sitmNo", sitmNo);
	         ColitmLst.put("sortSeq",1);
	         ColitmLst.put("dpYn", "Y");
	         
	         
	         JSONArray arr_itmImgLst = new JSONArray();
	         JSONObject ColitmImgLst = new JSONObject();
	         ColitmImgLst.put("epsrTypCd", "IMG");
	         ColitmImgLst.put("epsrTypDtlCd", "IMG_SQRE");
	         ColitmImgLst.put("origImgFileNm", bookInfo.get_IMG_PATH2());
	         ColitmImgLst.put("rprtImgYn", "Y");
	         
	         arr_itmImgLst.add(ColitmImgLst);
	         ColitmLst.put("itmImgLst", arr_itmImgLst);
	         ColitmLst.put("slPrc", SALEPRICE);
	         ColitmLst.put("stkQty", 999);

	         
	         JSONArray itmLstArray = new JSONArray();
	         itmLstArray.add(ColitmLst);
	         items.put("itmLst", itmLstArray);
	         
	         
	         arr_spdLst.add(items);
	         jsonObject.put("spdLst", arr_spdLst);
	         
			return jsonObject.toJSONString();
		}

		
		public String GetXml(Goods bookInfo, double SALEPRICE) throws SQLException {
			//최종 완성될 JSONObject 선언(전체)
	        JSONObject jsonObject = new JSONObject();              
	        JSONObject items = new JSONObject(); 
	        JSONArray arr_spdLst = new JSONArray();               
	        
	        String Standard_Cat ="";
	        String Disp_Cat ="";
	        
	        items.put("epdNo", bookInfo.get_ISBN13());
	        items.put("trGrpCd", "SR");
	        
	        if (bookInfo.get_BX_CATECD() == null) {
	        	Standard_Cat ="BN70011970";
	        	Disp_Cat ="EC10400937";
			}else {
				GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
				
				java.util.HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("BOOXEN_CATE_CD", bookInfo.get_BX_CATECD());

				HashMap<String, Object> list = goodsDAO.GetCategoryList(bookInfo.get_BX_CATECD());
				
				for (int i = 0; i < list.size(); i++) {
					HashMap map = (HashMap) list.get(i);

					Standard_Cat = (String) map.get("STANDARD_CATE_CD");
					Disp_Cat = (String) map.get("DISP_CATE_CD");				
				}
			}

	        
	        items.put("trNo", "LD477577");        
	        items.put("scatNo", Standard_Cat);
	        
	        
	        
	        JSONArray arr_dcatLst = new JSONArray();  // 전시카테고리
	        JSONObject dcatLst_items = new JSONObject();
	        dcatLst_items.put("mallCd", "LTON");
	        dcatLst_items.put("lfDcatNo",Disp_Cat);
	        arr_dcatLst.add(dcatLst_items);
	        items.put("dcatLst",arr_dcatLst);
	        
	        
	        items.put("slTypCd","GNRL");
	        items.put("pdTypCd","GNRL_GNRL");
	        items.put("spdNm",bookInfo.get_BOOK_NM());
	        items.put("mfcrNm",bookInfo.get_PUBLISHER());
	        items.put("oplcCd","KR");
	        
	        
	        if (bookInfo.get_ISBN13().substring(0,1).equals("8"))
	        {
	        	items.put("tdfDvsCd","01");   //과세
	        }
	        else
	        {
	        	items.put("tdfDvsCd","02");   //면세
	        }
	        
	        
	       
	        
	        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
			Date time = new Date();
					
			String time1 = format1.format(time);
	        
			items.put("slStrtDttm",time1); 
			items.put("slEndDttm","20991230000000"); 
			
			
			
			 JSONObject NoticeInfo = new JSONObject();
			 NoticeInfo.put("pdItmsCd","26"); 
			 
			 JSONObject NoticeInfo1 = new JSONObject();
			 NoticeInfo1.put("pdArtlCd","0830"); 
			 NoticeInfo1.put("pdArtlCnts",bookInfo.get_BOOK_NM());
			 
			 JSONObject NoticeInfo2 = new JSONObject();
			 NoticeInfo2.put("pdArtlCd","0840"); 
			 NoticeInfo2.put("pdArtlCnts",bookInfo.get_AUTHR() + "/" + bookInfo.get_PUBLISHER());
			 
			 JSONObject NoticeInfo3 = new JSONObject();
			 NoticeInfo3.put("pdArtlCd","0870"); 
			 NoticeInfo3.put("pdArtlCnts",bookInfo.get_BOOK_WT_VAL() );
			 
			 JSONObject NoticeInfo4 = new JSONObject();
			 NoticeInfo4.put("pdArtlCd","0880"); 
			 NoticeInfo4.put("pdArtlCnts",bookInfo.get_BOOK_PAGE_VAL() );
			 
			 JSONObject NoticeInfo5 = new JSONObject();
			 NoticeInfo5.put("pdArtlCd","0860"); 
			 NoticeInfo5.put("pdArtlCnts","상품상세 설명 참고" );
			 
			 JSONObject NoticeInfo6 = new JSONObject();
			 NoticeInfo6.put("pdArtlCd","0850"); 
			 NoticeInfo6.put("pdArtlCnts","상품상세 설명 참고" );
			 
			 JSONObject NoticeInfo7 = new JSONObject();
			 NoticeInfo7.put("pdArtlCd","0890"); 
			 NoticeInfo7.put("pdArtlCnts","상품상세 설명 참고" );
			 
			 JSONArray NoticeValueArray = new JSONArray();               
			 NoticeValueArray.add(NoticeInfo1);
	         NoticeValueArray.add(NoticeInfo2);
	         NoticeValueArray.add(NoticeInfo3);
	         NoticeValueArray.add(NoticeInfo4);
	         NoticeValueArray.add(NoticeInfo5);
	         NoticeValueArray.add(NoticeInfo6);
	         NoticeValueArray.add(NoticeInfo7);
	         
	         NoticeInfo.put("pdItmsArtlLst", NoticeValueArray);
	         items.put("pdItmsInfo", NoticeInfo);
	         
	         JSONObject ColpurPsbQtyInfo = new JSONObject();    //구매가능수량정보
	         ColpurPsbQtyInfo.put("itmByMinPurYn", "Y");
	         ColpurPsbQtyInfo.put("itmByMinPurQty", 1);
	         ColpurPsbQtyInfo.put("itmByMaxPurPsbQtyYn", "Y");
	         ColpurPsbQtyInfo.put("maxPurQty", "1000");
	         items.put("purPsbQtyInfo", ColpurPsbQtyInfo);
	         
	         
	         items.put("ageLmtCd", "0");   //연령제한코드   
	         items.put("prstPsbYn", "N");   //선물가능여부    
	         items.put("prcCmprEpsrYn", "Y");   //가격비교노출여부     
	         items.put("bookCultCstDdctYn", "Y");   //도서문화비 공제여부 
	         items.put("isbnCd", bookInfo.get_ISBN13());   //ISBN
	         items.put("pdStatCd", "NEW");   //상품상태코드 

	         JSONArray epnLstArray = new JSONArray(); //상품설명
	         JSONObject ColepnLst = new JSONObject();
	         ColepnLst.put("pdEpnTypCd", "DSCRP");
	         ColepnLst.put("cnts", GetGoodsBody_New(bookInfo));

	         epnLstArray.add(ColepnLst);
	         items.put("epnLst",epnLstArray);

	         items.put("dvProcTypCd", "LO_ENTP");   //배송처리유형코드    
	         items.put("dvPdTypCd", "GNRL");   //배송상품유형코드     
	         items.put("sndBgtNday", "3");   //발송예정일수    
	         
	         
	         items.put("dvRgsprGrpCd", "GN000");   //배송권역그룹코드    
	         items.put("dvMnsCd", "DPCL");   //배송수단코드     
	         items.put("owhpNo", "PLO336024");   //출고지번호    
	         items.put("hdcCd", "0006");   //택배  
	         items.put("dvCstPolNo", "368987");   //배송비정책번호  
	         items.put("adtnDvCstPolNo", "368990");   //추가배송비정책번호 				  
	         items.put("rtrpNo", "PLO336024");   //회수지번호    
	         items.put("stkMgtYn", "Y");   //재고관리여부     				  
	         items.put("sitmYn", "N");   //판매자단품여부     
	         
	         
	         JSONObject ColitmLst = new JSONObject();
	         ColitmLst.put("eitmNo", bookInfo.get_ISBN13());
	         ColitmLst.put("sortSeq",1);
	         ColitmLst.put("dpYn", "Y");
	         
	         
	         JSONArray arr_itmImgLst = new JSONArray();
	         JSONObject ColitmImgLst = new JSONObject();
	         ColitmImgLst.put("epsrTypCd", "IMG");
	         ColitmImgLst.put("epsrTypDtlCd", "IMG_SQRE");
	         ColitmImgLst.put("origImgFileNm", bookInfo.get_IMG_PATH2());
	         ColitmImgLst.put("rprtImgYn", "Y");
	         
	         arr_itmImgLst.add(ColitmImgLst);
	         ColitmLst.put("itmImgLst", arr_itmImgLst);
	         ColitmLst.put("slPrc", SALEPRICE);
	         ColitmLst.put("stkQty", 999);

	         
	         JSONArray itmLstArray = new JSONArray();
	         itmLstArray.add(ColitmLst);
	         items.put("itmLst", itmLstArray);
	         
	         
	         arr_spdLst.add(items);
	         jsonObject.put("spdLst", arr_spdLst);
	         
			return jsonObject.toJSONString();
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
			JSONObject jsonStatusObject = new JSONObject();        
	        JSONObject jsonObject = new JSONObject();        
	        JSONArray basicArray = new JSONArray();               
	      
	        jsonObject.put("trGrpCd", "SR");
	        jsonObject.put("trNo", "LD477577");
	        jsonObject.put("spdNo", bookInfo.get_PRODUCTNO());
	      
	         if (bookInfo.get_BOOKSTS().equals("현매") || bookInfo.get_BOOKSTS().equals("정상"))
	        {
	             jsonObject.put("slStatCd", "SALE");
	             
	        }else {
	            jsonObject.put("slStatCd", "SOUT");
	            
	        }
	        
	         basicArray.add(jsonObject);
	         jsonStatusObject.put("spdLst", basicArray);
	         
			return jsonStatusObject.toJSONString();
		}
		
		public String Get_sitmNo(Goods bookInfo)
		{
			String sitmNo ="";
			try {
				JSONObject jsonObject = new JSONObject();        

		    	String Key="Bearer 5d5b2cb498f3d20001665f4e6c4dd70be6a147ceaabc99e7fe06e9db";
		    	
		        jsonObject.put("trGrpCd", "SR");
		        jsonObject.put("trNo", "LD477577");
		        jsonObject.put("spdNo", bookInfo.get_PRODUCTNO());
		       
		        String sVal = getHttpHTML_POST_Order(jsonObject.toJSONString(),Key,"https://openapi.lotteon.com/v1/openapi/product/v1/product/detail");
		        
		        if (!sVal.equals("")) {
		        	
		        	JSONParser jsonParser = new JSONParser();
					JSONObject jsonObj = (JSONObject)jsonParser.parse( sVal );							
					String returnCode = jsonObj.get("returnCode").toString();
					String message = jsonObj.get("message").toString();					
				
					JSONObject sdata = (JSONObject)jsonObj.get("data");
					
					JSONArray jsonArray_items = (JSONArray)sdata.get("itmLst");
					
					
					
					
					for(int i=0;i<jsonArray_items.size();i++){

		 					JSONObject jsonObjOrder = (JSONObject)jsonArray_items.get(i);
		 					sitmNo = jsonObjOrder.get("sitmNo").toString();
		 				
		 				}
					
		        	/*
		        	  Document doc = toXmlDocument(sVal);
		              Element root = doc.getDocumentElement();
		              NodeList childeren = root.getChildNodes();
		              for(int i = 0; i < childeren.getLength(); i++){
		      			Node node = childeren.item(i);
		      			if(node.getNodeType() == Node.ELEMENT_NODE){
		      				Element ele = (Element)node;
		      				String nodeName = ele.getNodeName();
		      				 if(nodeName.equals("data"))
		      				{
		      					NodeList childeren2 = ele.getChildNodes();
		      					for(int a = 0; a < childeren2.getLength(); a++){
		      						Node node2 = childeren2.item(a);
		      						if(node2.getNodeType() == Node.ELEMENT_NODE){
		      							Element ele2 = (Element)node2;
		      							String nodeName2 = ele2.getNodeName();
		      							if(nodeName2.equals("itmLst")){
		      								NodeList childeren3 = ele2.getChildNodes();
		      		      					for(int b = 0; b < childeren3.getLength(); b++){
		      		      						Node node3 = childeren3.item(b);
		      		      						if(node3.getNodeType() == Node.ELEMENT_NODE){
		      		      							Element ele3 = (Element)node3;
		      		      							String nodeName3 = ele3.getNodeName();
		      		      							if(nodeName3.equals("itmLst")){
			      		      							NodeList childeren4 = ele3.getChildNodes();
			    	      		      					for(int c = 0; c < childeren4.getLength(); c++){
			    	      		      						Node node4 = childeren4.item(c);
			    	      		      						if(node4.getNodeType() == Node.ELEMENT_NODE){
			    	      		      							Element ele4 = (Element)node4;
			    	      		      							String nodeName4 = ele4.getNodeName();
			    	      		      							if(nodeName4.equals("sitmNo")){
			    	      		      								sitmNo = ele4.getTextContent();
			    	      		      								break;
			    	      		      							}
			    	      		      							
			    	      		      						}
			    	      		      					}
		      		      							}
		      		      							
		      		      						}
		      		      					}
		      							}
		      							
		      						}
		      					}
		     
		      				}
		      			}
		              }
		              */
		        }
			}catch (Exception e) {
				// TODO: handle exception
			}
	        
	        
	       return sitmNo;
		}
		
		public String GetXml_Price(Goods bookInfo,String sitmNo)
		{
			
			 SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
				Date time = new Date();
						
				String time1 = format1.format(time);
		        
			
				
			//최종 완성될 JSONObject 선언(전체)
			JSONObject jsonStatusObject = new JSONObject();        
	        JSONObject jsonObject = new JSONObject();        
	        JSONArray basicArray = new JSONArray();               
	      
	        jsonObject.put("trGrpCd", "SR");
	        jsonObject.put("trNo", "LD477577");
	        jsonObject.put("spdNo", bookInfo.get_PRODUCTNO());
	        jsonObject.put("sitmNo", sitmNo);
	        jsonObject.put("slPrc", bookInfo.get_SALEPRICE());
	        jsonObject.put("hstStrtDttm", time1);
	        jsonObject.put("hstEndDttm", "20991230000000");
	       
	        
	        
	         basicArray.add(jsonObject);
	         jsonStatusObject.put("itmPrcLst", basicArray);
	         
			return jsonStatusObject.toJSONString();
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
		 
		 
		 public String getHttpHTML_POST_Reg(String sParam, String Key, String surl,Goods bookInfo) {
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
		            requestPost.addHeader("Authorization", Key);
		            requestPost.addHeader("Accept-Language", "ko");
		            requestPost.addHeader("X-Timezone", "GMT+09:00");
		          
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
		                
		                String spdNo = "";
							
						JSONParser jsonParser = new JSONParser();
						JSONObject jsonObj = (JSONObject)jsonParser.parse( strResult );							
						String returnCode = jsonObj.get("returnCode").toString();
						String message = jsonObj.get("message").toString();					
					
						JSONArray jsonArray_items = (JSONArray)jsonObj.get("data");	
						
						
						for(int i=0;i<jsonArray_items.size();i++){

	   	 					JSONObject jsonObjOrder = (JSONObject)jsonArray_items.get(i);
	   	 					 spdNo = jsonObjOrder.get("spdNo").toString();
	   	 					resultMessage= jsonObjOrder.get("resultMessage").toString();
	   	 				}
						
		                /*
		                Document doc = toXmlDocument(strResult);
		                Element root = doc.getDocumentElement();
		                NodeList childeren = root.getChildNodes();
		                for(int i = 0; i < childeren.getLength(); i++){
		        			Node node = childeren.item(i);
		        			if(node.getNodeType() == Node.ELEMENT_NODE){
		        				Element ele = (Element)node;
		        				String nodeName = ele.getNodeName();
		        				if(nodeName.equals("returnCode")){
		        					resultCode = ele.getTextContent();
		      
		        				}else if(nodeName.equals("data"))
		        				{
		        					NodeList childeren2 = ele.getChildNodes();
		        					for(int a = 0; a < childeren2.getLength(); a++){
		        						Node node2 = childeren2.item(a);
		        						if(node2.getNodeType() == Node.ELEMENT_NODE){
		        							Element ele2 = (Element)node2;
		        							String nodeName2 = ele2.getNodeName();
		        							if(nodeName2.equals("spdNo")){
		        								spdNo = ele2.getTextContent();
		        							}
		        							if(nodeName2.equals("resultMessage")){
		        								resultMessage = ele2.getTextContent();
		        							}
		        						}
		        					}
		       
		        				}
		        			}
		                }
				*/
						
						if (returnCode.equals("0000") && !spdNo.equals("")&& !spdNo.equals(null) && resultMessage.indexOf("정상") > -1 )
						{
							
							GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
							
							java.util.HashMap<String, Object> map = new HashMap<String, Object>();						
							map.put("ITEMCD", bookInfo.get_ITEMCD());
							map.put("PRODUCTNO", spdNo);
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
		 public Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException {

				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document document = docBuilder.parse(new InputSource(new StringReader(str)));

				return document;
			}
		 public String getHttpHTML_POST_Upt(String sParam, String Key, String surl,Goods bookInfo) {

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
		            requestPost.addHeader("Authorization", Key);
		            requestPost.addHeader("Accept-Language", "ko");
		            requestPost.addHeader("X-Timezone", "GMT+09:00");
		          
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
		                
		                String spdNo = "";
						
						JSONParser jsonParser = new JSONParser();
						JSONObject jsonObj = (JSONObject)jsonParser.parse( strResult );							
						String returnCode = jsonObj.get("returnCode").toString();
						String message = jsonObj.get("message").toString();					
					
						JSONArray jsonArray_items = (JSONArray)jsonObj.get("data");	
						
						
						for(int i=0;i<jsonArray_items.size();i++){

	   	 					JSONObject jsonObjOrder = (JSONObject)jsonArray_items.get(i);
	   	 					 spdNo = jsonObjOrder.get("spdNo").toString();
	   	 					resultMessage= jsonObjOrder.get("resultMessage").toString();
	   	 				}
						
						if (returnCode.equals("0000") && !spdNo.equals("")&& !spdNo.equals(null) && resultMessage.indexOf("정상") > -1 )
							
						{
							
							java.util.HashMap<String, Object> map_1 = new HashMap<String, Object>();						
							map_1.put("ITEMCD", bookInfo.get_ITEMCD());
							map_1.put("PRODUCTNO", spdNo);
							map_1.put("ISBN13", bookInfo.get_ISBN13());
							map_1.put("BOOK_NM", bookInfo.get_BOOK_NM());
							map_1.put("BOOKSTS", bookInfo.get_BOOKSTS());
							map_1.put("SALEPRICE", bookInfo.get_SALEPRICE());
							map_1.put("PRICE", bookInfo.get_PRICE());
							
							GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	   						
	   						goodsDAO.UptRegItem(map_1);
							
						}else {
							if (returnCode.equals("9999") )
							{
								GoodsDAO goodsDAO = new GoodsDAO(MyBatisConnectionFactory.getSqlSessionFactory());
								java.util.HashMap<String, Object> map_2 = new HashMap<String, Object>();						
								map_2.put("ISBN13", bookInfo.get_ISBN13());
		   						//goodsDAO.DelItem(map_2);
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
		        
		        return "";
		    }
		 
		 
		 public String Send_POST_Upt(String sParam, String Key, String surl,Goods bookInfo) {

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
		            requestPost.addHeader("Authorization", Key);
		            requestPost.addHeader("Accept-Language", "ko");
		            requestPost.addHeader("X-Timezone", "GMT+09:00");
		          
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
		                
		                String spdNo = "";
						String resultCode = "";
						
		                
		               
						
		               
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
		            requestPost.addHeader("Accept-Language", "ko");
		            requestPost.addHeader("X-Timezone", "GMT+09:00");
		          
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

	}

