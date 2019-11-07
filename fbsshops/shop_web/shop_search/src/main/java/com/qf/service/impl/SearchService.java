package com.qf.service.impl;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yss
 * @Date 2019/10/17 0017
 */

@Service
public class SearchService implements ISearchService {


    @Autowired
    private SolrClient solrClient;


    @Override
    public boolean insert(Goods goods) {
//        System.out.println("Search内/search/insert中goods的值为："+goods);

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", goods.getId() + "");
        document.addField("subject", goods.getSubject());
        document.addField("info", goods.getInfo());
        document.addField("price", goods.getPrice().doubleValue());
        document.addField("save", goods.getSave());
        document.addField("images", goods.getFengmian());
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Goods> queryByKey(String keyword) {

//        System.out.println("SearchService中的queryByKey中的参数keyword为："+keyword);

        SolrQuery solrQuery = new SolrQuery();
        if (keyword == null){
            solrQuery.setQuery("*:*");
        }else {
            solrQuery.setQuery("subject:"+keyword+" || info:"+keyword);
        }

        //分页设置
        solrQuery.setStart(0);//开始的条数
        solrQuery.setRows(100);//每页显示条数

        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.addHighlightField("subject");

        try {
            QueryResponse response = solrClient.query(solrQuery);

            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            SolrDocumentList documents = response.getResults();

            //分页总条数
            documents.getNumFound();

            List<Goods> goodsList = new ArrayList<>();
            for (SolrDocument document : documents) {
                Goods goods = new Goods();

                goods.setId(Integer.parseInt(document.getFieldValue("id")+""));
                goods.setSubject(document.getFieldValue("subject")+"");
                goods.setPrice(BigDecimal.valueOf((double) document.getFieldValue("price")));
                goods.setSave((int)document.getFieldValue("save"));
                goods.setFengmian((String)document.getFieldValue("images"));

                if(highlighting.containsKey(goods.getId()+"")){
                    Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");
                    if (stringListMap.containsKey("subject")){
                        String subject = stringListMap.get("subject").get(0);
                        goods.setSubject(subject);
//                        System.out.println("SearchService中queryByKey方法内显示高亮的subject为："+goods.getSubject());
                    }
                }
                goodsList.add(goods);
            }
//            System.out.println("SearchService中的queryByKey中的参数goodsList为："+goodsList);

            return goodsList;

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
