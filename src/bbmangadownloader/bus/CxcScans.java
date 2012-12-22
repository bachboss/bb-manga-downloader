///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package bbmangadownloader.bus;
//
//import bbmangadownloader.bus.description.ADefaultBus;
//import bbmangadownloader.bus.description.IBusOnePage;
//import bbmangadownloader.bus.exception.HtmlParsingException;
//import bbmangadownloader.entity.Chapter;
//import bbmangadownloader.entity.Image;
//import bbmangadownloader.entity.Manga;
//import bbmangadownloader.entity.Server;
//import bbmangadownloader.entity.data.MangaDateTime;
//import bbmangadownloader.ult.DateTimeUtilities;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.nodes.Node;
//import org.jsoup.select.Elements;
//
///**
// *
// * @author Bach
// */
//public class CxcScans extends ADefaultBus implements IBusOnePage {
//
//    private static final String DEFAULT_TRANS = "Cxc Scans";
//    private static final DateFormat DATE_FORMAT_UPLOAD = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
//
//    @Override
//    public List<Manga> getAllMangas(Server s) throws IOException, HtmlParsingException {
//        // TODO: Later;
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public List<Chapter> getAllChapters(Manga m) throws IOException, HtmlParsingException {
//        List<Chapter> lstChapter = new ArrayList<Chapter>();
//
//        Document doc = getDocument(m.getUrl());
//        Elements eList = doc.select("body div#wrapper article#content div.panel div.list div.element");
//        for (Element e : eList) {
//            Element eTitle = e.select("div.title a").first();
//            String strDate;
//            {
//                List<Node> lstNode = e.select("div.meta_r").first().childNodes();
//                strDate = lstNode.get(lstNode.size() - 1).toString();
//                strDate = strDate.substring(strDate.indexOf(',') + 2);
//                strDate = strDate.replaceAll("(?<=\\d+)(?:st|nd|rd|th) ", " ");
//            }
//
//            MangaDateTime date;
//            try {
//                date = new MangaDateTime(DateTimeUtilities.getDate(strDate, DATE_FORMAT_UPLOAD));
//            } catch (Exception ex) {
//                date = new MangaDateTime(strDate);
//            }
//
//            Chapter c = new Chapter(-1,
//                    eTitle.text(),
//                    eTitle.attr("href"), m,
//                    DEFAULT_TRANS, date);
//            lstChapter.add(c);
//
//        }
//        return lstChapter;
//    }
//
//    @Override
//    public List<Image> getAllImages(Chapter c) throws IOException, HtmlParsingException {
//        Document doc = getDocument(c.getUrl());
//        Elements els = doc.select("script[type=text/javascript]");
//        return null;
//    }
//}
