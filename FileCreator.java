import java.io.*;
import java.util.Scanner;
import java.io.File;

public class FileCreator {
    //主方法
    public static void main (String[] args){
        //方法选择
        System.out.println("1、单图片+单压缩包");
        System.out.println("2、单图片+文件夹内所有文件");
        Scanner scanner = new Scanner(System.in);
        methodSwitch(scanner.nextInt());
    }

    //输入方法
    public static void methodSwitch(int inputSwitch){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请先拖入图片后回车");
        String inputPicLink = scanner.nextLine();
        switch (inputSwitch){
            case 1:
                System.out.println("请拖入压缩包后回车");
                String inputZipLink = scanner.nextLine();
                singleFileCreator(inputPicLink,inputZipLink);
                break;
            case 2:
                System.out.println("再拖入压缩包所在文件夹后回车");
                String inputPacLink = scanner.nextLine();
                groupFileCreator(inputPicLink,inputPacLink);
                break;
            default:
                System.out.println("你丫输点正常数值行吗?");
        }
    }

    public static void singleFileCreator(String FilePicLink,String FileZipLink){
        try {
            //创建文件输入流输出流缓冲流
            BufferedInputStream bufPic = new BufferedInputStream(new FileInputStream(FilePicLink));
            BufferedInputStream bufZip = new BufferedInputStream(new FileInputStream(FileZipLink));
            BufferedOutputStream bufOut = new BufferedOutputStream(new FileOutputStream(FileZipLink + ".png"));
            byte[] bys = new byte[2048];
            int len;
            while ((len = bufPic.read(bys)) != -1){
                bufOut.write(bys,0,len);
            }
            while ((len = bufZip.read(bys)) != -1){
                bufOut.write(bys,0,len);
            }
            //关闭流
            bufPic.close();
            bufZip.close();
            bufOut.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("无权限或路径错误!");
        }
    }
    public static void groupFileCreator(String FilePicLink,String FilePacLink){
        try {
            File f = new File(FilePacLink);
            File[] files = f.listFiles();
            int i = files.length;
            System.out.println("共" + i + "个文件");


            for (File file : files){
                String fileLink = file.getAbsolutePath();
                BufferedInputStream bufPic = new BufferedInputStream(new FileInputStream(FilePicLink));
                BufferedInputStream bufZip = new BufferedInputStream(new FileInputStream(fileLink));
                BufferedOutputStream bufOut = new BufferedOutputStream(new FileOutputStream(fileLink + ".png"));

                byte[] bys = new byte[2048];
                int len;
                while ((len = bufPic.read(bys)) != -1){
                    bufOut.write(bys,0,len);
                }
                while ((len = bufZip.read(bys)) != -1){
                    bufOut.write(bys,0,len);
                }
                //关闭流
                bufPic.close();
                bufZip.close();
                bufOut.close();
                //进度提示
                i--;
                System.out.println("剩余" + i + "个文件");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("无权限或路径错误!且内部不得包含文件夹!");
        }
    }
}

