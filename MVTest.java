package ui_bag;
 
import org.apache.http.HttpHeaders;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
 
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.UUID;
 
public class MVTest {
    static final String[] user_Agent = {
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36",
            "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10",
            "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
            "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
            "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
            "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; LBBROWSER)",
    };
 
    static final String neteaseReferer = "http://music.163.com/";
    static final String neteeaseHost = "music.163.com";
 
    public static void main(String[] args) throws IOException {
        String url = neteaseReferer+"mv?id=5917771";  //mv 视频地址
 
        //请求header 设置
        HashMap<String, String> config = new HashMap<String,String>();
        config.put(HttpHeaders.USER_AGENT, user_Agent[0]);
        config.put(HttpHeaders.REFERER, neteaseReferer);
        config.put(HttpHeaders.HOST, neteeaseHost);
        Document res = Jsoup.connect(url).headers(config).get();
 
        //解析框架源代码中的 mv 播放url
        String downloadURL = null;
        try {
            Element element = res.select("meta[property=og:video]").first();
            downloadURL = URLDecoder.decode( element.attr("content"), "UTF-8" );
        } catch (NullPointerException e) {
            System.out.println("找不到下载文件！！");
        }
        System.err.println(downloadURL+"+++++");
        // System.err.println(UUID.randomUUID().toString()+"   1111");
        downloadFile(downloadURL, UUID.randomUUID().toString()+".mp4");
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
            System.out.println(conn+"    777777777");
            System.out.println(conn.getInputStream()+"   0000000000");
            InputStream inStream = conn.getInputStream();
            String addr = "E:\\mv\\"+savePath;
            File file = new File(addr);
            // System.out.println(file+"   22222");
            // System.out.println(file.getParentFile()+"   333333");
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
