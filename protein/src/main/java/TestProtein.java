import com.protein.models.Protein;
import com.protein.services.ProteinService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;


public class TestProtein {
    public static void main(String[] args) {
        ProteinService service = new ProteinService();
        //将Faster文件转成list对象
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\data\\uniprotkb.fasta";
        List<Protein> listProtein = service.getProteinSequences(filePath);
        System.out.println(filePath+ "文件中一共有" + listProtein.size() + "个蛋白质！");

        //减少数据量，只对部分蛋白质进行分析和处理
        int proteinNum = 30;
        listProtein = listProtein.subList(0, proteinNum);
        System.out.println("下面是对" + proteinNum + "个蛋白质进行胰蛋白酶酶切后的分析：");

        //第1问
        System.out.println("第1问："+ proteinNum + "个蛋白质共生成了多少个肽段？");
        //统计肽段，并将散点图数据持久化到json文件中，供js提取
        System.out.println("\t" + proteinNum + "个蛋白质胰蛋白酶酶切理论上会生成" + service.countPeptides(listProtein) + "个肽段！");


        //第2-5问
        boolean flag;  //第3问：有没有蛋白质不存在特征序列？true--有； false--没有
        HashMap<String, Double> featurePeptideMCR = new HashMap<>();  //key：特征肽段序列；value：特征肽段质荷比
        HashMap<String, String> singletonPeptide = new HashMap<>();  //key：单次特征肽段；value：蛋白1和蛋白2
        HashMap<Double, List<Pair<String, Protein>>> sameMCRPeptide = new HashMap<>();  //key：质荷比；value：(特征肽段, 蛋白质)

        //第2问
        System.out.println("第2问：请将这些蛋白质的特征肽段全部列出，并且计算每一个特征肽段的质荷比。");
        for(Protein protein : listProtein){
            String proteinName = protein.getName();
            for(Map.Entry<String, Integer> entryPeptideType : protein.getPeptideType().entrySet()){
                String peptide = entryPeptideType.getKey();
                int type = entryPeptideType.getValue();
                if(type == 0){  //特征肽段
                    System.out.println("\t蛋白质" + proteinName + "特征肽段：");
                    System.out.println("\t\t" + peptide);
                    featurePeptideMCR.put(peptide, service.calcMCR(peptide));
                }
                if(type == 1){  //单次特征肽段
                    if(singletonPeptide.containsKey(peptide)){
                        singletonPeptide.put(peptide, singletonPeptide.get(peptide) + "和" + proteinName);
                    }else{
                        singletonPeptide.put(peptide, proteinName);
                    }
                }
            }
        }

        //第3问
        System.out.println("第3问：有没有蛋白质不存在特征肽段？");
        int total = 0;
        for(Protein protein : listProtein){
            int count = 0;
            for(Map.Entry<String, Integer> entry : protein.getPeptideType().entrySet()){
                if(entry.getValue() == 0){
                    count++;
                }
            }
            if(count == 0){
                System.out.println("\t蛋白质" + protein.getName() +  "不存在特征肽段！");
            }
            total += count;
        }
        if(total == 0){
            System.out.println("\t当前分析的所有蛋白质都不存在特征序列！");
        }

        //第4问
        System.out.println("第4问：请打印出所有单次特征肽段对应的两个蛋白质");
        if(singletonPeptide.isEmpty()){
            System.out.println("\t不存在单次特征肽段！");
        }else {
            for(Map.Entry<String, String> entry : singletonPeptide.entrySet()){
                System.out.println("\t单次特征肽段\"" + entry.getKey() + "\"对应的两个蛋白质：" + entry.getValue());
            }
        }

        //第5问
        System.out.println("第5问：请计算质荷比相同的特征肽段，打印拥有相同质荷比特征肽段的肽段序列以及所对应的蛋白质");
        //将所有特征肽段的质荷比
        for(Protein protein : listProtein){
            for(Map.Entry<String, Integer> entry : protein.getPeptideType().entrySet()){
                if(entry.getValue() == 0){
                    String peptide = entry.getKey();
                    double mcr = featurePeptideMCR.get(peptide);
                    Pair<String, Protein> featurePeptidePair = new Pair<>(peptide, protein);
                    if(sameMCRPeptide.containsKey(mcr)){
                        sameMCRPeptide.get(mcr).add(featurePeptidePair);
                    } else{
                        List<Pair<String, Protein>> listFeaturePeptide = new ArrayList<>();
                        listFeaturePeptide.add(featurePeptidePair);
                        sameMCRPeptide.put(mcr, listFeaturePeptide);
                    }
                }
            }
        }
        //打印
        int count = 0;
        for(Map.Entry<Double, List<Pair<String, Protein>>> entry : sameMCRPeptide.entrySet()){
            if(entry.getValue().size() > 1){
                count ++;
                System.out.println("\t质荷比都为" + entry.getKey() + "的特征肽段：");
                List<Pair<String, Protein>> listSameMCRPeptide = entry.getValue();
                for(Pair<String, Protein> pairSameMCRPeptide : listSameMCRPeptide){
                    System.out.println("\t\t肽段：\"" + pairSameMCRPeptide.getKey() + "\"，对应蛋白质：" + pairSameMCRPeptide.getValue().getName());
                }
            }
        }
        if(count == 0){
            System.out.println("\t不存在相同质荷比的特征肽段！");
        }

    }
}
