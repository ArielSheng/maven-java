package com.protein.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protein.models.AminoAcid;
import java.io.*;
import java.util.HashMap;

public class CommonUtil {
    //数据的持久化
    public static void DataPersistence(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = System.getProperty("user.dir")  + "\\src\\main\\resources\\data\\digestion.json";
        try {
            String json = objectMapper.writeValueAsString(object);
            File file = new File(filePath);
            objectMapper.writeValue(file, json);
//            System.out.println("数据持久化成功！");
//            System.out.println(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //将json文件转化成HashMap对象(key:oneLetterCode；value:氨基酸)
    public static HashMap<String, AminoAcid> jsonDeserialize(String jsonFile) {
        HashMap<String, AminoAcid> hmAminoAcid = new HashMap<>();
        try {
            AminoAcid[] aminoAcids = new ObjectMapper().readValue(new File(jsonFile), AminoAcid[].class);
            for (AminoAcid aminoAcid : aminoAcids) {
                hmAminoAcid.put(aminoAcid.getOneLetterCode(), aminoAcid);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return hmAminoAcid;
    }

    //将Fasta文件中的蛋白质数据转化成HashMap对象(key:蛋白质名称；value:氨基酸序列)
    public static HashMap<String, String> readProteinSequence(String filePath){
        HashMap<String, String> hmProteinSequence = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = null;
            StringBuilder sequenceBuilder = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) { //数据读完
                    if (header != null) { //最后一个蛋白质序列已经读完，保存到hmProteinSequence，并清空sequenceBuilder
                        hmProteinSequence.put(header, sequenceBuilder.toString());
                        sequenceBuilder.setLength(0);
                    }
                    break;
                }
                if (line.startsWith(">")) {  //标题行
                    if (header != null) { //上一个蛋白质序列读完，保存到hmProteinSequence，并清空sequenceBuilder
                        hmProteinSequence.put(header, sequenceBuilder.toString());
                        sequenceBuilder.setLength(0);
                    }
                    //header
                    header = line.substring(1).trim().split("\\|")[1];  //只保留蛋白质名称
                }
                else { //蛋白质序列行
                    sequenceBuilder.append(line.trim().replaceAll("[\\r\\n]+$", ""));   //去掉尾部的回车换行符
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hmProteinSequence;
    }
}