import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream("config.csv"));
        int ch;
        StringBuilder config = new StringBuilder();
        while ((ch = isr.read()) != -1) {
            config.append((char) ch);
        }
        isr.close();

        //数据处理
        String[] configArr = config.toString().split(",");

        int model = Integer.parseInt(configArr[0]);//模式

        switch (model) {
            case 1 -> fileByteCreate(configArr[1],configArr[2]);
            case 2 -> fileHeadAdd(configArr[2]);
            case 3-> fileHeadDel(configArr[2]);
        }


    }

    //文件缝纫机 接收前置文件后缀和压缩包文件后缀
    public static void fileByteCreate(String postfix, String zipPostfix) throws IOException {
        BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream("1." + postfix));
        BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream("1." + zipPostfix));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("finish." + postfix));

        byte[] bys = new byte[1024];
        int len;
        while ((len = bis1.read(bys)) != -1) {
            bos.write(bys, 0, len);
        }
        while ((len = bis2.read(bys)) != -1) {
            bos.write(bys, 0, len);
        }

        bis1.close();
        bis2.close();
        bos.close();
    }

    //文件头添加 接收压缩包文件后缀
    public static void fileHeadAdd(String zipPostfix) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("1." + zipPostfix));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("2." + zipPostfix));

        byte[] bys = new byte[1024];
        int len;

        //添加标志头
        byte[] zipHead = {0x50, 0x4b, 0x03, 0x04};
        bos.write(zipHead);
        while ((len = bis.read(bys)) != -1) {
            bos.write(bys, 0, len);
        }

        bis.close();
        bos.close();
    }

    //文件头删除 接收压缩包文件后缀
    public static void fileHeadDel(String zipPostfix) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("1." + zipPostfix));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("2." + zipPostfix));

        byte[] bys = new byte[1024];
        int len;

        //删除标志头
        for (int i = 0; i < 4; i++) bis.read();
        while ((len = bis.read(bys)) != -1) {
            bos.write(bys, 0, len);
        }

        bis.close();
        bos.close();
    }


}