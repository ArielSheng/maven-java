package com.protein.services;

import com.protein.models.AminoAcid;
import com.protein.models.Digestion;
import com.protein.models.Protein;
import com.protein.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProteinService {
    String jsonFile = System.getProperty("user.dir") + "\\src\\main\\resources\\data\\AminoAcidData.json";
    HashMap<String, AminoAcid> hmAminoAcid = CommonUtil.jsonDeserialize(jsonFile);
    //将Faster文件转成list对象
    public List<Protein> getProteinSequences(String filePath){
        List<Protein> listProtein = new ArrayList<>();

        //将Fasta文件转化成HashMap对象(key:蛋白质名称；value:氨基酸序列)
        HashMap<String, String> proteinSequence = CommonUtil.readProteinSequence(filePath);
        //酶切
        Protein protein;
        for (Map.Entry<String, String> entry : proteinSequence.entrySet()) {
            protein = new Protein();
            protein.setName(entry.getKey());
            protein.setSequence(entry.getValue());
            String[] peptides = entry.getValue().split("KR");
            protein.setPeptides(peptides);
            //初始化peptideType, 默认肽段为特征肽段
            HashMap<String, Integer> peptideType = new HashMap<>();
            for(String peptide : peptides){
                peptideType.put(peptide, 0);
            }
            protein.setPeptideType(peptideType);
            listProtein.add(protein);
        }
        // 更新peptideType
        for(int i = 0; i < listProtein.size(); i++){  //第1层循环：遍历蛋白质列表
            protein = listProtein.get(i);
            for (Map.Entry<String, Integer> entry : protein.getPeptideType().entrySet()) { //第2层循环：遍历当前蛋白质的所有peptideType
                String peptide = entry.getKey();
                //更新peptideType
                int count = 0;
                for(int j = 0; j < listProtein.size(); j++){  //第3层循环：与其他蛋白质进行比较
                    if(j != i){
                        Protein tmpProtein = listProtein.get(j);
                        if(tmpProtein.getPeptideType().containsKey(peptide)){
                            count++;  //找到相同肽段则累计加1
                        }
                    }
                }
                entry.setValue(count);
            }
        }
        return listProtein;
    }

    //统计蛋白质序列的肽段数量，持久化echarts数据
    public int countPeptides(List<Protein> listProtein){
        int peptideNum = 0;
        //统计肽段总数，持久化listEchartsData
        List<Digestion> listDigestion = new ArrayList<>();
        for (Protein protein : listProtein){
            peptideNum += protein.getPeptides().length;
            //listDigestion
            Digestion digestion = new Digestion();
            digestion.setProteinName(protein.getName());
            digestion.setPeptideCount(protein.getPeptides().length);
            listDigestion.add(digestion);
        }
        //数据持久化，供js提取数据
        CommonUtil.DataPersistence(listDigestion);
        return peptideNum;
    }

    public double calcMCR(String peptide){
        double mcr;
        int charge = 0;
        double mass = 0;
        for(int i = 0; i < peptide.length(); i++){
            String oneLetterCode = String.valueOf(peptide.charAt(i));
            //计算charge：计算肽段中碱性氨基酸R、K的数量
            if ("RK".contains(oneLetterCode)) {
                charge++;
            }
            //计算mass：肽段中所有氨基酸平均质量总和+H2O分子质量
            if(! hmAminoAcid.containsKey(oneLetterCode)){
                System.out.println("氨基酸json文件信息不全，没有" + oneLetterCode + "符号的氨基酸！");
                System.exit(0);
            }
            mass += hmAminoAcid.get(oneLetterCode).getAverageMass();
        }
        mcr = (mass + 18.02) / charge;
        mcr = Math.round(mcr * 100) / 100.0;
        //mcr = Double.parseDouble(new DecimalFormat("#.##").format(mcr));
        return mcr;
    }

}
