package dao;
import java.util.List;

import model.Goods;
 
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


public class GoodsDAO {

	  private SqlSessionFactory sqlSessionFactory = null;
	  
	    public GoodsDAO(SqlSessionFactory sqlSessionFactory){
	        this.sqlSessionFactory = sqlSessionFactory;
	    }
	 
	    public  List<Goods> GetGoodsList(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetGoodsList");
	        }catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
			} 
	        finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	  
	    
	    
	    
	 
	    public  List<Goods> GetUptBagicGoodsList(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetUptBagicGoodsList");
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	    public  List<Goods> GetStatusGoodsList(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetStatusGoodsList");
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	    
	    public  List<Goods> GetUptSaleGoodsList(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetUptSaleGoodsList");
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	    
	    public  List<Goods> GetUptPriceGoodsList(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetUptPriceGoodsList");
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	    
	    public  java.util.HashMap<String, Object> GetCategoryList(String  BOOXEN_CATE_CD){
	    	java.util.HashMap<String, Object> result;
	        
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	        	result = session.selectOne("Goods.GetCategoryList",BOOXEN_CATE_CD);
	        } finally {
	            session.close();
	        }
	 
	        return result;
	    }
	    
	 		public  java.util.HashMap<String, Object> GetISBNList(String  ISBN13){
	 			java.util.HashMap<String, Object> result;
	 	        
	 	        SqlSession session = sqlSessionFactory.openSession();
	 	 
	 	        try {
	 	        	result = session.selectOne("Goods.GetISBNList",ISBN13);
	 	        } finally {
	 	            session.close();
	 	        }
	 	 
	 	        return result;
	 		    }
	    
	    public int GetExistGoodsList(String  ISBN13){
	    	  SqlSession session = sqlSessionFactory.openSession();
	    	  int pCnt =0;
	        try {
	        	 pCnt = session.selectOne("Goods.GetExistGoodsList", ISBN13);
	        } finally {
	            session.close();
	        }
	 
	        return pCnt;
	    }
	    
	    public int GetExisKidsList(String  CATE_CD){
	    	  SqlSession session = sqlSessionFactory.openSession();
	    	  int pCnt =0;
	        try {
	        	 pCnt = session.selectOne("Goods.GetExisKidsList", CATE_CD);
	        } finally {
	            session.close();
	        }
	 
	        return pCnt;
	    }
	    
	 
	    public int InsertRegItem(java.util.HashMap map){
	        int id = -1;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            id = session.insert("Goods.InsertRegItem", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	 
	        return id;
	    }
	 
	    public void UptRegItem(java.util.HashMap map){
	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.UptRegItem", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void UptSalePriceItem(java.util.HashMap map){
	   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.UptSalePriceItem", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    
	    /**
	     * Delete an instance of Goods from the database.
	     * @param id value of the instance to be deleted.
	     */
	    public void delete(int id){
	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.delete("Goods.delete", id);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public  List<Goods> GetBoriboriGoodsList(java.util.HashMap map){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetBoriboriGoodsList",map);
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	    public int InsertError(java.util.HashMap map){
	        int id = -1;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            id = session.insert("Goods.InsertError", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	 
	        return id;
	    }
	 
}
