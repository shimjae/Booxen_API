package dao;
import java.util.List;

import model.Member;
 
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MemberDAO {

	 private SqlSessionFactory sqlSessionFactory = null;
	  
	    public MemberDAO(SqlSessionFactory sqlSessionFactory){
	        this.sqlSessionFactory = sqlSessionFactory;
	    }
	 
	    public  List<Member> GetMemberList(){
	        List<Member> list = null;
	        SqlSession session = sqlSessionFactory.openSession();
	 
	        try {
	            list = session.selectList("Goods.GetMemberList");
	        } finally {
	            session.close();
	        }
	 
	        return list;
	    }
}
