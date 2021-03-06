package org.iidp.ostmap.batch_processing.pathcalc;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.io.Serializable;
import java.util.TreeSet;


public class PathCoordGroupReduce implements GroupReduceFunction<Tuple2<String, String>, Tuple2<String, String>>, Serializable {
    private String user = "";
    private String coords = "";
    private TreeSet<String> coordSet = new TreeSet<>();
    @Override
    public void reduce(Iterable<Tuple2<String, String>> values, Collector<Tuple2<String, /*TODO POJO*/String>> out) throws Exception {
        coords = "";
        for (Tuple2<String,String> entry: values) {
            if(coordSet.size() == 0){
                user = entry.f0;
                coords = entry.f1.toString();
            }else{
                coords += "|" + entry.f1.toString();
            }
            coordSet.add(entry.f1.toString());
        }
        if(coordSet.size() > 1){
            out.collect(new Tuple2<>(user,coords));
            coordSet.clear();
        }

    }

}
