package ui_bag;
 
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 *@author WL
 */
public class MP3Test {
    public static void main(String[] args) throws IOException {
        String url = "http://music.163.com/song/media/outer/url?id=108138";  //MP3地址
        downloadFile(url, UUID.randomUUID().toString()+".mp3");
    }
 
    /**
     * 文件下载保存
     * @param downloadURL  下载链接
     * @param savePath     保存路径
     * @throws MalformedURLException
     * @throws MalformedURLException
     */
    public static void downloadFile(String downloadURL, String savePath) throws MalformedURLException, MalformedURLException {
        // 下载网络文件
        int byteread = 0;
        FileOutputStream fs =null;
        try {
            URL url = new URL(downloadURL.trim());
            URLConnection conn = url.openConnection();
            System.out.println(conn+"   ------");
            System.out.println(conn.getInputStream()+"   33333333");
            InputStream inStream = conn.getInputStream();
            String addr = "E:\\mv\\"+savePath;
            File file = new File(addr);
            if(!file.exists()){
                if(!file.getParentFile().exists()) {
                    //若文件夹不存在，创建文件夹
                    file.getParentFile().mkdirs();  //注意不是 mkdir ， mkdirs是创建多级目录
                }
            }
            fs = new FileOutputStream(file);
 
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            System.out.println("下载成功！！");
        } catch(NullPointerException e){
            System.out.println("下载地址无效！！！");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null != fs){
                try {
                    fs.close();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }
}
