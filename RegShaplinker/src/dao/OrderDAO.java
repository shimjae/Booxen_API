package dao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import model.Order;;


public class OrderDAO {
	 private SqlSessionFactory sqlSessionFactory = null;
	  
	   public OrderDAO(SqlSessionFactory sqlSessionFactory){		   
	        this.sqlSessionFactory = sqlSessionFactory;
	    }
	   
	
	 
	   public int GetExistBooxenOrderList(java.util.HashMap map){
	    	  SqlSession session = sqlSessionFactory.openSession();
	    	  int pCnt =0;
	        try {
	        	 pCnt = session.selectOne("Goods.GetExistBooxenOrderList", map);
	        } finally {
	            session.close();
	        }
	 
	        return pCnt;
	    }
	   
	   
	   public int InsertBooxenOrdList(java.util.HashMap map){
	        int id = -1;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            id = session.insert("Goods.InsertBooxenOrdList", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	 
	        return id;
	    }
	   

	   public int GetOrderSeq(java.util.HashMap map){
	    	  SqlSession session = sqlSessionFactory.openSession();
	    	  int pCnt =0;
	        try {
	        	 pCnt = session.selectOne("Goods.GetOrderSeq", map);
	        } finally {
	            session.close();
	        }
	 
	        return pCnt;
	    }
	   
	   public int GetOrderSeq_SSG(java.util.HashMap map){
	    	  SqlSession session = sqlSessionFactory.openSession();
	    	  int pCnt =0;
	        try {
	        	 pCnt = session.selectOne("Goods.GetOrderSeq_SSG", map);
	        } finally {
	            session.close();
	        }
	 
	        return pCnt;
	    }
	   
	   public  List  GetTransNoList(HashMap map)throws SQLException{
	    	List list = null;
	        
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	        	list = session.selectList("Goods.GetTransNoList",map);
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
	   
	   public void UptTrans(HashMap map){
		   	 
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            session.update("Goods.UptTrans", map);
	        } finally {
	            session.commit();
	            session.close();
	        }
	    }
}
