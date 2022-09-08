package acq.nrk.image.transform.utils;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class HeifToPng {

    private String fileName;
    private String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HeifToPng(String url){
        this.fileName = "" + new Date().getTime();
        this.url = url;
    }

    public byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];//转换为二进制
        int len=0;
        while((len =inStream.read(buffer))!=-1){
            outStream.write(buffer,0,len);
        }
        return outStream.toByteArray();
    }

    public boolean getHeifByUrl(){
        boolean flag = true;
        try {
            URL requestUrl = new URL(this.url);

            HttpURLConnection conn= (HttpURLConnection) requestUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(1000);//超时提示1秒=1000毫秒
            InputStream inStream=conn.getInputStream();//获取输出流
            byte[] data=readInputStream(inStream);

            File file=new File(String.format("src/main/java/acq/nrk/image/transform/images/%s.heif", this.fileName));
            FileOutputStream outStream=new FileOutputStream(file);
            outStream.write(data);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean HeifToPng(){
        boolean flag = true;
        try {
            ConvertCmd cmd = new ConvertCmd();
            //windows下才需要设置imageMagick安装地址
            cmd.setSearchPath("D:\\ImageMagick");
            //创建图片操作对象
            IMOperation op = new IMOperation();

            //指定原图片全路径, 文件需要在同一文件夹下
            op.addImage(String.format("%s\\src\\main\\java\\acq\\nrk\\image\\transform\\images\\%s.heif", System.getProperty("user.dir"), this.fileName));

            op.compress("JPEG");

            op.format("JPG");
            //指定目标图片全路径, 文件需要在同一文件夹下
            op.addImage(String.format("%s\\src\\main\\java\\acq\\nrk\\image\\transform\\images\\%s.jpg", System.getProperty("user.dir"), this.fileName));
            cmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public String getImageBase64(){
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(String.format("%s\\src\\main\\java\\acq\\nrk\\image\\transform\\images\\%s.jpg", System.getProperty("user.dir"), this.fileName));
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public boolean deleteImage(){
        File heifFile = new File(String.format("%s\\src\\main\\java\\acq\\nrk\\image\\transform\\images\\%s.heif", System.getProperty("user.dir"), this.fileName));
        File pngFile = new File(String.format("%s\\src\\main\\java\\acq\\nrk\\image\\transform\\images\\%s.jpg", System.getProperty("user.dir"), this.fileName));
        boolean heifValue = heifFile.delete();
        boolean pngValue = pngFile.delete();
        return heifValue && pngValue;
    }


    public static void main(String[] args) {
        HeifToPng heifToPng = new HeifToPng("http://hw2.a.kwimgs.com/upic/2022/08/29/14/BMjAyMjA4MjkxNDE1NTFfMTcyOTY2NTY3NF84MzAxODM3ODc1M18yXzM=_480p_Ba619d608f2c01251e59bd986d6f48db4.heif?tag=1-1661822604-sp-0-wvmdndlizy-ef894603b51c3a15&clientCacheKey=3xwrtesr3ke3y2u_480p.heif&di=3cb997cd&bp=10321");
        heifToPng.getHeifByUrl();
        heifToPng.HeifToPng();
        heifToPng.getImageBase64();
    }
}
