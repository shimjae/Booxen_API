package mybatis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConnectionFactory {
	 private static SqlSessionFactory sqlSessionFactory;
	 
	    static {
	        try {
	        	/*
	          
	            
	            String resource = "resources/config.xml";
		        Reader reader = Resources.getResourceAsReader(resource);
		        
	           */
	        	System.out.println("=====config start=====");   	
	        	String resource = "D:\\Batch\\Booxen_Shaplinker\\config.xml";
		        Reader reader = new java.io.FileReader(resource);   
	            if (sqlSessionFactory == null) {
	                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	                System.out.println("=====config End=====");
	            }
	        }
	        catch (FileNotFoundException fileNotFoundException) {
	            fileNotFoundException.printStackTrace();
	        }
	        catch (IOException iOException) {
	            iOException.printStackTrace();
	        }
	    }
	    public static SqlSessionFactory getSqlSessionFactory() {
	        return sqlSessionFactory;
	    }
}
