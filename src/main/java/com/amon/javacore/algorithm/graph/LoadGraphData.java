package com.amon.javacore.algorithm.graph;

import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/12/30.
 */
public class LoadGraphData {


    /**
     * get graph size
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public int getGraphSize(String fileName) throws Exception {

        if (null != fileName) {
            File file = ResourceUtils.getFile("classpath:testdata/graph/" + fileName);
            if (file.isFile() && file.exists()) {
                String lineTxt = new BufferedReader(new InputStreamReader(new FileInputStream(file))).readLine();
                if (null != lineTxt) {
                    return Integer.valueOf(lineTxt);
                }
            }
        }

        return 0;

    }

    /**
     * load data to map
     *
     * @param fileName
     * @param baseGraph
     */
    public void loadData(String fileName, BaseGraph baseGraph) throws Exception {

        if (null != baseGraph && null != fileName) {

            File file = ResourceUtils.getFile("classpath:testdata/graph/" + fileName);

            List<String> dataList = new ArrayList<>();
            if (file.isFile() && file.exists()) {

                InputStreamReader read = new InputStreamReader(new FileInputStream(file));

                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    dataList.add(lineTxt);
                }
                bufferedReader.close();

            }

            for (int i = 0; i < dataList.size(); i++) {

                if (i == 0) {
                    continue;
                } else {

                    String line = dataList.get(i);
                    String[] arr = line.split(" ");
                    if (null != arr && arr.length == 3) {
                        baseGraph.addWeightEdge(Integer.valueOf(arr[0]),
                                Integer.valueOf(arr[1]),
                                baseGraph.directed(),
                                Double.valueOf(arr[2]));
                    }

                }

            }


        }

    }

}
