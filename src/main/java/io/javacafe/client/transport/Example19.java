package io.javacafe.client.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class Example19 {

    /**
     * 확장 Aggregation 사용하기
     * */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        String INDEX_NAME = "movie_search";
        String TYPE_NAME = "_doc";
        String FIELD_NAME = "repNationNm";
        String QUERY = "한국";

        String AGGREGATION_NAME = "EXTENDED_STATS_AGG_NM";
        String AGG_FIELD_NAME = "movieCd";
        ExtendedStatsAggregationBuilder aggregation = AggregationBuilders.extendedStats(AGGREGATION_NAME)
                .field(AGG_FIELD_NAME);




        //Aggregation으로 출력할 결과물을 addAggregation메소드에 담아 출력한다.
        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .addAggregation(aggregation)
                .setSize(0)
                .get();

        //결과 출력
        ExtendedStats agg = response.getAggregations().get(AGGREGATION_NAME);
        double min = agg.getMin();
        double max = agg.getMax();
        double avg = agg.getAvg();
        double sum = agg.getSum();
        long count = agg.getCount();
        double stdDeviation = agg.getStdDeviation();
        double sumOfSquares = agg.getSumOfSquares();
        double variance = agg.getVariance();
        double stdDeviationBound = agg.getStdDeviationBound(ExtendedStats.Bounds.LOWER);






    }
}
