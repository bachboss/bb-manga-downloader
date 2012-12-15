/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.bus;

import bbmangadownloader.bus.description.ADefaultBus;
import bbmangadownloader.bus.description.IBusOnePage;
import bbmangadownloader.bus.exception.HtmlParsingException;
import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Image;
import bbmangadownloader.entity.Manga;
import bbmangadownloader.entity.Server;
import bbmangadownloader.entity.data.MangaDateTime;
import bbmangadownloader.ult.DateTimeUtilities;
import bbmangadownloader.manager.HttpDownloadManager;
import bbmangadownloader.ult.MultitaskJob;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Bach
 */
public class BlogTruyen extends ADefaultBus implements IBusOnePage {

    private static final String URL_LIST_MANGA = "http://blogtruyen.com/DanhSach/TatCa/DanhSach/changePage";
    private static final String BASED_URL = "http://blogtruyen.com";
    private static final String POST_FORM_STR = "key=TatCa&orderBy=AB&X-Requested-With=XMLHttpRequest&page=";
    private static final String DEFAULT_TRANS = "BlogTruyen";
    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("dd/MM/yyyy");

    private Document getDocumentPostForm(String url, String postForm) throws IOException {
        return HttpDownloadManager.createConnection(url).post(postForm).getDocument();
    }

    private void getMangasFromDocument(Document doc, List<Manga> listManga, Server s) {
        Elements elsLink = doc.select("a[onmouseout=UnTip()][onmouseover][href]");
        for (Element aTag : elsLink) {
            Manga m = new Manga(s, aTag.text(), BASED_URL + aTag.attr("href"));
            listManga.add(m);
        }
    }

    @Override
    public List<Manga> getAllMangas(final Server s) throws IOException, HtmlParsingException {
        ArrayList<Callable<Object>> listTask = new ArrayList<Callable<Object>>();
        final ArrayList<Manga> returnValue = new ArrayList<Manga>();
        int numberOfPage = 1;
        {
            final Document doc = getDocumentPostForm(URL_LIST_MANGA, POST_FORM_STR + "1");
            String str = doc.select("div[class=paging] span[class=page]").last().child(0).attr("href");
            str = str.substring(str.indexOf('(') + 1, str.indexOf(')'));
            try {
                numberOfPage = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
            }
            listTask.add(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    getMangasFromDocument(doc, returnValue, s);
                    return null;
                }
            });
        }
        System.out.println("Number Of Page = " + numberOfPage);
        for (int i = 2; i <= numberOfPage; i++) {
            final String u = POST_FORM_STR + i;
            listTask.add(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    try {
                        Document doc = getDocumentPostForm(URL_LIST_MANGA, u);
                        getMangasFromDocument(doc, returnValue, s);
                    } catch (IOException ex) {
                        Logger.getLogger(BlogTruyen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return null;
                }
            });
        }
        System.out.println(listTask.size());
        MultitaskJob.doTask(listTask);
        return returnValue;
    }

    @Override
    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
        if (m.getUrl().contains("http://blogtruyen.com/Truyen/")) {
            ArrayList<Chapter> returnValue = new ArrayList<Chapter>();
            Document doc = getDocument(m.getUrl());
            Elements els = doc.getElementById("listChapter").getElementsByClass("row-chapter");
            for (Element divTag : els) {
                Element aTag = divTag.select("a").first();
                MangaDateTime time;
                try {
                    Element timeTag = divTag.select("div[class=content_cacchuong]").first();
                    time = new MangaDateTime(DateTimeUtilities.getDate(timeTag.text(), DATE_FORMAT_UPLOAD));
                } catch (ParseException ex) {
                    Logger.getLogger(BlogTruyen.class.getName()).log(Level.SEVERE, null, ex);
                    time = MangaDateTime.NOT_AVAIABLE;
                }
                Chapter c = new Chapter(-1F, aTag.text(), BASED_URL + aTag.attr("href"), m, DEFAULT_TRANS, time);
                returnValue.add(c);
            }
            return returnValue;
        }
        return null;
    }

    @Override
    public List<Image> getAllImages(Chapter c) throws IOException, HtmlParsingException {
        Document doc = getDocument(c.getUrl());
        ArrayList<Image> listImage = new ArrayList<Image>();
        Elements elsImages = doc.getElementById("noidungchuong").getElementsByTag("img");
        for (int i = 0; i < elsImages.size(); i++) {
            Element eImgTag = elsImages.get(i);
            Image img = new Image(i, eImgTag.attr("src"), c);
            listImage.add(img);
        }
        return listImage;
    }
    //<editor-fold>
//      private static Invocable JS_METHOD2;
//
////    private static String replaceText(String s) throws ScriptException, NoSuchMethodException {
////        Object o = JS_METHOD.invokeFunction("replaceText", s);
////        return o.toString();
////    }
//    private static String replaceeText(String s) throws ScriptException, NoSuchMethodException {
//        Object o = JS_METHOD2.invokeFunction("replaceeText", s);
//        return o.toString();
//    }
//
//    public static String getText(File f) throws FileNotFoundException, IOException {
//        StringBuilder sb;
//        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
//            String text = br.readLine();
//            sb = new StringBuilder(text);
//            while ((text = br.readLine()) != null) {
//                sb.append(text);
//            }
//        }
//        return sb.toString();
//    }
//
//    public static void main(String[] args) throws Exception {
////        {
////            String jsCode = getText(new File("D:\\js.js"));
////            ScriptEngineManager manager = new ScriptEngineManager();
////            ScriptEngine engine = manager.getEngineByName("JavaScript");
////            // evaluate script  
////            engine.eval(jsCode);
////            // javax.script.Invocable is an optional interface.  
////            // Check whether your script engine implements or not!  
////            // Note that the JavaScript engine implements Invocable interface.  
////            JS_METHOD = (Invocable) engine;
////        }
//
//        {
//            String jsCode = getText(new File("D:\\js2.js"));
//            ScriptEngineManager manager = new ScriptEngineManager();
//            ScriptEngine engine = manager.getEngineByName("JavaScript");
//            // evaluate script  
//            engine.eval(jsCode);
//            // javax.script.Invocable is an optional interface.  
//            // Check whether your script engine implements or not!  
//            // Note that the JavaScript engine implements Invocable interface.  
//            JS_METHOD2 = (Invocable) engine;
//        }
//        String input = getText(new File("D:\\input.txt"));
//        String text = replaceeText(input);
//
//        Elements els = Jsoup.parse(text).select("img");
//        for (Element e : els) {
//            String url = e.attr("src");
//            url = url.substring(url.indexOf("http"));
//            System.out.println(url);
//            System.out.println("--------------------------------------------------------------------------------");
//        }
//    }
//</editor-fold>
}
