package com.amon.javacore.concurrentutil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用ForkJoin并行计算框架计算经典的MapReduce wordcount程序
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/7/16.
 */
public class ForkJoinDemo02 {

    public static void main(String[] args) {

        ForkJoinPool fjp = new ForkJoinPool(4);


        String[] fc = {"hello world", "hello me", "hello fork", "hello join", "fork join in world"};

        MapReduce mr = new MapReduce(fc, 0, fc.length);

        Map<String, Long> result = fjp.invoke(mr);

        result.forEach((k, v) -> {
            System.out.println("word:" + k + " , value:" + v);
        });


    }

    /**
     * ForkJoin Task
     */
    static class MapReduce extends RecursiveTask<Map<String, Long>> {

        private String[] wordlineArray;

        private int startLine;

        private int endLine;

        public MapReduce(String[] wordlineArray, int startLine, int endLine) {
            this.wordlineArray = wordlineArray;
            this.startLine = startLine;
            this.endLine = endLine;
        }

        @Override
        protected Map<String, Long> compute() {

            if (endLine - startLine == 1) {

                return calline(wordlineArray[startLine]);

            } else {

                int mid = (startLine + endLine) / 2;
                MapReduce m1 = new MapReduce(wordlineArray, startLine, mid);
                // 创建子任务
                m1.fork();
                MapReduce m2 = new MapReduce(wordlineArray, mid, endLine);

                return merge(m2.compute(), m1.join());

            }

        }

    }

    /**
     * map合并逻辑
     *
     * @param m1
     * @param m2
     * @return
     */
    private static Map<String, Long> merge(Map<String, Long> m1, Map<String, Long> m2) {

        Map<String, Long> result = new HashMap<>(16);

        result.putAll(m1);

        if (null != m2) {

            for (Map.Entry<String, Long> entry : m2.entrySet()) {

                String word = entry.getKey();
                Long value = entry.getValue();

                Long v = result.get(word);
                if (null != v) {
                    result.put(word, v + value);
                } else {
                    result.put(word, 1L);
                }

            }

        }


        return result;
    }

    /**
     * 统计单次数量
     *
     * @param line
     * @return
     */
    private static Map<String, Long> calline(String line) {

        Map<String, Long> result = new HashMap<>(16);

        if (null != line) {

            String[] words = line.split("\\s+");

            for (String word : words) {

                Long v = result.get(word);

                if (null != v) {
                    result.put(word, v + 1);
                } else {
                    result.put(word, 1L);
                }

            }

        }

        return result;

    }


}
