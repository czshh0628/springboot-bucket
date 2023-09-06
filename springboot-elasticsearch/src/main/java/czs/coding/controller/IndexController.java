package czs.coding.controller;


import czs.coding.config.ElasticSearchConfig;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 索引操作
 *
 * @author czs
 */
@RequestMapping("index")
@RestController
public class IndexController {
    @Resource
    private RestHighLevelClient client;

    @GetMapping("create")
    public void createIndex() throws IOException {
        CreateIndexRequest indexRequest = new CreateIndexRequest("user");
        client.indices().create(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
    }

    @GetMapping("query")
    public void queryIndex() throws IOException {
        GetIndexRequest indexRequest = new GetIndexRequest("user");
        GetIndexResponse response = client.indices().get(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(response);
    }

    @GetMapping("delete")
    public void deleteIndex() throws IOException {
        DeleteIndexRequest indexRequest = new DeleteIndexRequest("user");
        client.indices().delete(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
    }
}
