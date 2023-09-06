package czs.coding.controller;

import czs.coding.config.ElasticSearchConfig;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 查询操作
 *
 * @author czs
 */
public class QueryController {

    @Resource
    private RestHighLevelClient client;

    /**
     * 查询所有索引数据
     */
    @GetMapping("all")
    public void get1() throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 构建查询请求体
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 查询所有数据
        builder.query(QueryBuilders.matchAllQuery());
        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * term 查询，查询条件为关键字
     */
    @GetMapping("term")
    public void get2() throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 构建查询的请求体
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("age", 30));
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 分页查询
     */
    @GetMapping("page")
    public void page() throws IOException {
        SearchRequest request = new SearchRequest().indices("user");
        // 构建查询的请求体
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        // 分页查询
        builder.from(0).size(2);
        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 数据排序
     */
    @GetMapping("sort")
    public void sort() throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        // 构造查询的请求体
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        // 排序
        builder.sort("age", SortOrder.ASC);

        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 过滤字段
     */
    @GetMapping("filter")
    public void filter() throws IOException {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        // 构建查询的请求体
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        // 查询字段过滤
        String[] excludes = {};
        String[] includes = {"name", "age"};
        builder.fetchSource(includes, excludes);

        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * bool查询
     */
    @GetMapping("bool")
    public void bool() throws IOException {
        // 构成搜索查询对象
        SearchRequest request = new SearchRequest().indices("user");
        // 构建查询的请求体
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 必须包含
        boolQuery.must(QueryBuilders.matchQuery("age", 30));
        // 一定不包含
        boolQuery.mustNot(QueryBuilders.matchQuery("name", "lisi"));
        // 可能包含
        boolQuery.should(QueryBuilders.matchQuery("sex", "男"));

        builder.query(boolQuery);
        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 范围查询
     */
    @GetMapping("range")
    public void range() throws IOException {
        // 构建搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        // 构建搜索查询的请求体
        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder ageRangeQuery = QueryBuilders.rangeQuery("age");
        // 大于等于
        ageRangeQuery.gte("30");
        // 小于等于
        ageRangeQuery.lte("40");

        builder.query(ageRangeQuery);
        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 模糊查询
     */
    @GetMapping("fuzzy")
    public void fuzzy() throws IOException {
        SearchRequest request = new SearchRequest().indices("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("name", "zhangsa");
        /*
            本次查询允许的最大编辑距离，默认不开启模糊查询，相当于fuzziness=0。
            可以是数字（0、1、2）代表固定的最大编辑距离
         */
        fuzzyQuery.fuzziness(Fuzziness.ONE);
        builder.query(fuzzyQuery);
        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 高亮查询
     */
    @GetMapping("highLight")
    public void highLight() throws IOException {
        // 构建搜索请求对象
        SearchRequest request = new SearchRequest("user");
        // 构建搜索请求体
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 构建查询方式
        TermQueryBuilder termQuery = QueryBuilders.termQuery("name", "zhangsan");
        // 设置查询方式
        builder.query(termQuery);
        // 构建高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 设置标签前缀
        highlightBuilder.preTags("<font color='red'>");
        // 设置标签后缀
        highlightBuilder.postTags("</font>");
        // 设置高亮字段
        highlightBuilder.field("name");
        // 设置高亮请求对象
        builder.highlighter(highlightBuilder);

        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        // 打印响应结果
        SearchHits hits = response.getHits();
        System.out.println("took::" + response.getTook());
        System.out.println("time_out::" + response.isTimedOut());
        System.out.println("total::" + hits.getTotalHits());
        System.out.println("max_score::" + hits.getMaxScore());
        System.out.println("hits::::>>");
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            //打印高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
        }
        System.out.println("<<::::");
    }

    /**
     * 聚合查询
     */
    @GetMapping("agg")
    public void agg() throws IOException {
        SearchRequest request = new SearchRequest("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 构建聚合查询
        builder.aggregation(AggregationBuilders.max("maxAge").field("age"));

        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);

        // 打印结果
        Aggregations aggregations = response.getAggregations();
        SearchHits hits = response.getHits();
        System.out.println(hits);

        System.out.println(aggregations);
    }

    /**
     * 分组统计
     */
    @GetMapping("group")
    public void group() throws IOException {
        SearchRequest request = new SearchRequest("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.aggregation(AggregationBuilders.terms("agGroup").field("age"));

        request.source(builder);
        SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);

        // 打印结果
        Aggregations aggregations = response.getAggregations();
        SearchHits hits = response.getHits();
        System.out.println(response);

        System.out.println(aggregations);
    }
}
