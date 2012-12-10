///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package bbmangadownloader.bus;
//
//import bbmangadownloader.bus.description.ADefaultBus;
//import bbmangadownloader.bus.description.IBus;
//import bbmangadownloader.bus.exception.HtmlParsingException;
//import bbmangadownloader.entity.Chapter;
//import bbmangadownloader.entity.Image;
//import bbmangadownloader.entity.Manga;
//import bbmangadownloader.entity.Server;
//import bbmangadownloader.entity.data.MangaDateTime;
//import bbmangadownloader.ult.NumberUtilities;
//import bbmangadownloader.ult.TextUtilities;
//import com.google.code.regexp.NamedPattern;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
///**
// *
// * @author Bach
// */
//public class Veryim extends ADefaultBus implements IBus { // ERROR !
//
//    private static final String DEFAULT_TRANS = "Veryim";
//
//    @Override
//    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
//        // TODO: Later!
//        return null;
//    }
//
//    @Override
//    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
//        List<Chapter> listChapter = new ArrayList<Chapter>();
//
//        Document doc = getDocument(m.getUrl());
//        Elements esChapters = doc.select("div[id=chapters] ul li");
//        for (Element eChapter : esChapters) {
//            Element aTag = eChapter.select("a").first();
//            Chapter c = new Chapter(-1, aTag.text(), aTag.attr("href"), m, DEFAULT_TRANS, MangaDateTime.NOT_AVAIABLE);
//            listChapter.add(c);
//        }
//        return listChapter;
//    }
//    private static final NamedPattern PATTERN_PAGE = NamedPattern.compile("this.totalPage=\"(\\d+)\",");
//
//    @Override
//    public List<Image> getAllImages(Chapter c) throws IOException, HtmlParsingException {
//        if (ARR_PATTERN == null) {
//            ARR_PATTERN = new Pattern[ARR_PATTERN_TEXT.length];
//            for (int i = 0; i < ARR_PATTERN_TEXT.length; i++) {
//                ARR_PATTERN[i] = Pattern.compile(ARR_PATTERN_TEXT[i]);
//            }
//        }
//        Document doc = getDocument(c.getUrl());
//        int numberOfPage = 1;
//        Elements esScript = doc.select("script[type=text/javascript]");
//        String imgServer = null;
//        String letter = null;
//        String comicDir = null;
//        String chapterDir = null;
//        int fileNameType = 0;
//        String ext = null;
//
//        for (Element eScript : esScript) {
//            String text = eScript.html();
//            if (text.contains("function $(a)")) {
//                numberOfPage = Integer.parseInt(TextUtilities.getText(text, PATTERN_PAGE, 1));
//                //
//                imgServer = TextUtilities.getText(text, ARR_PATTERN[0], 1);
//                letter = TextUtilities.getText(text, ARR_PATTERN[1], 1);
//                comicDir = TextUtilities.getText(text, ARR_PATTERN[2], 1);
//                chapterDir = TextUtilities.getText(text, ARR_PATTERN[3], 1);
//                fileNameType = NumberUtilities.getNumberInt(TextUtilities.getText(text, ARR_PATTERN[5], 1));
//                ext = TextUtilities.getText(text, ARR_PATTERN[6], 1);
//                break;
//            }
//        }
//
//        List<Image> returnValue = new ArrayList<Image>();
//        String baseUrl = c.getUrl();
//        {
//            int index = baseUrl.lastIndexOf(numberOfPage);
//            if (index != -1) {
//                baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf('?'));
//            }
//        }
//
//        for (int i = 1; i <= numberOfPage; i++) {
//            StringBuilder url = new StringBuilder();
//            url.append(imgServer).
//                    append("/").append(letter).
//                    append("/").append(comicDir).
//                    append("/").append(chapterDir).
//                    append("/").append(format(i, fileNameType)).
//                    append(ext);
//            returnValue.add(new Image(i, url.toString(), c));
//
//        }
//
//        return returnValue;
//    }
////    private class CallableImp implements Callable<Object> {
////
////        private String url;
////        private Document doc;
////        private Chapter chapter;
////        private List<Image> listImage;
////
////        public CallableImp(List<Image> listImage, String url, Chapter chapter) {
////            this.chapter = chapter;
////            this.listImage = listImage;
////            this.url = url;
////        }
////
////        public CallableImp(List<Image> listImage, Document doc, Chapter chapter) {
////            this.chapter = chapter;
////            this.listImage = listImage;
////            this.doc = doc;
////        }
////
////        @Override
////        public Object call() throws Exception {
////            if (doc == null) {
////                System.out.println("Doc Load: " + url);
////                doc = getDocument(url);
////            }
////
////            Elements esScript = doc.select("script[type=text/javascript]");
////            for (Element eScript : esScript) {
////                String text = eScript.html();
////                if (text.contains("function $(a)")) {
////                    doParse(chapter, listImage, text);
////                    return null;
////                }
////            }
////            return null;
////        }
////    }
//    private static final String[] ARR_PATTERN_TEXT = new String[]{
//        "this.imgServer=\"(.+?)\",", "this.letter=\"(.+?)\",", "this.comicDir=\"(.+?)\",", "this.chapterDir=\"(.+?)\",",
//        "this.page=(\\d+?),", "this.fileNameType=\"(.+?)\",", "this.ext=\"(.+?)\","
//    };
//    private static Pattern[] ARR_PATTERN;
//
////    public void doParse(Chapter chapter, List<Image> listImage, String text) {
////        try {
////            String imgServer = TextUtilities.getText(text, ARR_PATTERN[0], 1);
////            String letter = TextUtilities.getText(text, ARR_PATTERN[1], 1);
////            String comicDir = TextUtilities.getText(text, ARR_PATTERN[2], 1);
////            String chapterDir = TextUtilities.getText(text, ARR_PATTERN[3], 1);
////            int page = NumberUtilities.getNumberInt(TextUtilities.getText(text, ARR_PATTERN[4], 1));
////            int fileNameType = NumberUtilities.getNumberInt(TextUtilities.getText(text, ARR_PATTERN[5], 1));
////            String ext = TextUtilities.getText(text, ARR_PATTERN[6], 1);
////            StringBuilder url = new StringBuilder();
////            System.out.println("----------");
//////            System.out.println(imgServer);
//////            System.out.println(letter);
//////            System.out.println(comicDir);
//////            System.out.println(chapterDir);
////            System.out.println(page);
//////            System.out.println(fileNameType);
//////            System.out.println(ext);
////            System.out.println("----------");
////            url.append(imgServer).
////                    append("/").append(letter).
////                    append("/").append(comicDir).
////                    append("/").append(chapterDir).
////                    append("/").append(format(page, fileNameType)).
////                    append(ext);
////            Image image = new Image(page, url.toString(), chapter);
////            listImage.add(image);
////
////            System.out.println(page + "\t" + url);
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
////    }
//    private String format(int page, int fileNameType) {
//        String[] c = new String[]{"", "0", "00"};
//        String d = String.valueOf(page);
//        String e = c[3 - d.length()] + d;
//        return fileNameType == 1 ? e + "" + format(page - 1, 0) : e;
//    }
//}
