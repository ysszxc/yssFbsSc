package com.qf.test_solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSolrApplicationTests {


	@Autowired
	private SolrClient solrClient;


	@Test
	public void updata(){
		try {
			solrClient.deleteByQuery("*:*");
			solrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void insert() {

		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id","1");
		solrInputDocument.addField("subject","惠普笔记本");
		solrInputDocument.addField("price","19999.99");
		solrInputDocument.addField("save","1000");
		try {
			solrClient.add(solrInputDocument);
			solrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void delete() {
//        try {
//            solrClient.deleteById("1");
//            solrClient.commit();
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

		try {
			solrClient.deleteByQuery("*:*");
			solrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void search() {
		String key = "*";

		SolrQuery solrQuery = new SolrQuery("*:"+key+"");
		try {
			QueryResponse response = solrClient.query(solrQuery);
			SolrDocumentList results = response.getResults();
			for (SolrDocument solrDocument : results) {
				String id =  solrDocument.getFirstValue("id")+"";
				String price =solrDocument.getFirstValue("price")+"";
				String save =  solrDocument.getFirstValue("save")+"";
				String subject =  solrDocument.getFirstValue("subject")+"";
				System.out.println(id+"--"+price+"--"+save+"--"+subject);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
