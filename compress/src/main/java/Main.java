import com.compress.utils.ZipUtils;

public class Main {
    public static void main(String[] args) {
        ZipUtils zipUtils = new ZipUtils();
        // 获取工程的resources目录路径
        String projectRoot = System.getProperty("user.dir");
        String filePath = projectRoot + "\\src\\main\\resources\\data\\uniprotkb";

        //**Zlib压缩**//
        System.out.println("Zlib压缩，压缩文件后缀.zlib");
        zipUtils.zlibCompress(filePath + ".fasta");
        zipUtils.zlibCompress(filePath + ".tsv");
        zipUtils.zlibCompress(filePath + ".gff");

        //**Zstd压缩**//
        System.out.println("Zstd压缩，压缩文件后缀.zst");
        zipUtils.zstCompress(filePath + ".fasta");
        zipUtils.zstCompress(filePath + ".tsv");
        zipUtils.zstCompress(filePath + ".gff");

        //**Snappy压缩**//
        System.out.println("Snappy压缩，压缩文件后缀.snap");
        zipUtils.snapCompress(filePath + ".fasta");
        zipUtils.snapCompress(filePath + ".tsv");
        zipUtils.snapCompress(filePath + ".gff");

        //**LZMA压缩**//
        System.out.println("LZMA压缩，压缩文件后缀.xz");
        zipUtils.xzCompress(filePath + ".fasta");
        zipUtils.xzCompress(filePath + ".tsv");
        zipUtils.xzCompress(filePath + ".gff");
    }
}