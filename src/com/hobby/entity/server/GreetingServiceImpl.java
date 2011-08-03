package com.hobby.entity.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.elmergarduno.jcalais.CalaisClient;
import net.elmergarduno.jcalais.CalaisConfig;
import net.elmergarduno.jcalais.CalaisObject;
import net.elmergarduno.jcalais.CalaisResponse;
import net.elmergarduno.jcalais.rest.CalaisRestClient;

import com.hobby.entity.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public List<String> getLinkedArticle(String article) throws IllegalArgumentException {
		List<String> terms = parseArticle(article);
		return terms;
	}
	
	public ArrayList<String> parseArticle(String article){
		ArrayList<String> terms = new ArrayList<String>();
		CalaisClient client = new CalaisRestClient("7twbqwaky2upatmw968ukbkh");
	     CalaisConfig config = new CalaisConfig();
	     config.set(CalaisConfig.UserParam.EXTERNAL_ID, "17cabs901");
	     config.set(CalaisConfig.ProcessingParam.CALCULATE_RELEVANCE_SCORE, "true");
	     
	     try {
	    	 CalaisResponse response = client.analyze(article,config);
	    	 for(CalaisObject entity : response.getEntities()){
	    		 if(entity.getField("_type").equals("Person") || entity.getField("_type").equals("Organization")
	    				 || entity.getField("_type").equals("Company") || entity.getField("_type").equals("Country")
	    				 || entity.getField("_type").equals("City") || entity.getField("_type").equals("categoryName"))
					terms.add(entity.getField("name"));	
	    		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return terms; 
	}
	
}
