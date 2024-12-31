package dao;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import java.util.HashMap;
import model.Goods;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class GoodsDAO {
	  private SqlSessionFactory sqlSessionFactory = null;
	  
	    public GoodsDAO(SqlSessionFactory sqlSessionFactory){
	        this.sqlSessionFactory = sqlSessionFactory;
	    }
	    

	    
	    public  List  GetOrderList(java.util.HashMap map)throws SQLException{
	    	List list = null;
	        
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	        	list = session.selectList("Goods.GetOrderList",map);
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	   
	 
	    public  List<Goods> GetGoodsList(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetGoodsList");
	        }catch (Exception e) {
	        	 System.out.println(e.getMessage());
			} finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	    
	    
	    
	    public  List<Goods> GetUptGoodsList(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetUptGoodsList");
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	    
	    public  List<Goods> GetShplinkerGoodsList(String  ISBN13){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetShplinkerGoodsList",ISBN13);
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
	    
	    public  List<Goods> GetGmarketPeriod(){
	        List<Goods> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetGmarketPeriod");
	        } finally {
	            session.close();
	        }
	 
	        return list;
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
	    
	    
	  
	    public int InsertRegItem(HashMap map){
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
	    
	    public void UptPeriod(HashMap map){
	   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.UptPeriod", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void UptStatusItem(HashMap map){
	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.UptStatusItem", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void RegMallProduct_id_Benepia(HashMap map){
	   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_Benepia", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void RegMallProduct_id_Farm(HashMap map){
	   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_Farm", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    
	    
	    public void RegMallProduct_id_GMARKET(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_GMARKET", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    
	    public void RegMallProduct_id_TMON(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_TMON", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void RegMallProduct_id_LOTTE(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_LOTTE", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void RegMallProduct_id_EMART(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_EMART", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    public void RegMallProduct_id_SSG(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_SSG", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    public void RegMallProduct_id_SMCARD(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_SMCARD", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void RegMallProduct_id_EZWELL(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_EZWELL", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    public void RegMallProduct_id_SMCARD_BOCJI(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_SMCARD_BOCJI", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    public void RegMallProduct_id_WMP(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_WMP", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    public void RegMallProduct_id_AK(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.RegMallProduct_id_AK", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
	    
	    
	    public int InsertError(HashMap map){
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
