
/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package mangadownloader.ult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mangadownloader.entity.Chapter;
import mangadownloader.entity.Manga;
import org.jsoup.nodes.Node;

/**
 *
 * @author Bach
 */
public class Heuristic {

    public static final String PATTERN_CHAPTER_REGEX = "(chap|chapter|hồi|chương|tập|tiết|ch|oneshot)";
    public static final String PATTERN_SPACE = "[\\s|.]+";
    public static final String PATTERN_NUMBER_REGEX = "(?<cN>(\\d+\\.?\\d+)|(\\d+))";
    private static final Pattern[] DEFAULT_CHAPTER_LIST = {
        Pattern.compile(PATTERN_CHAPTER_REGEX + PATTERN_SPACE + PATTERN_NUMBER_REGEX)
    };
    public static int DEFAULT_MIN_ACCECPT = 60;

    //private static Pattern PATTERN_NUMBER = Pattern.compile("\\d+");
    public static String[] doSplitHeuristic(String text) {
        return text.split("\\s|-|_");
    }

    public static int getNumberOfDigitInString(String text, List<Integer> listParsedNumber) {
        String s = "";
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                counter++;
                if (listParsedNumber != null) {
                    s = s + text.charAt(i);
                }
            } else {
                if (listParsedNumber != null) {
                    if (s.length() != 0) {
                        try {
                            listParsedNumber.add(Integer.parseInt(s));
                            s = "";
                        } catch (NumberFormatException ex) {
                        }
                    }
                }
            }
        }

        if (s.length() != 0) {
            try {
                listParsedNumber.add(Integer.parseInt(s));
                s = "";
            } catch (NumberFormatException ex) {
            }
        }
        return counter;
    }

    public static int getRatio(int a, int b, int total) {
        return (int) (Math.floor(((float) a) / b) * total);
    }

    /*
     * This method compare 2 word to see how similar they are
     */
    public static int isTwoWordAccecptable(String word1, String word2) {
        int grade = 0;
        if (word1.equals(word2)) {
            return 100;
        }
        if (word1.length() == word2.length()) {
            grade += 50;
        } else {
            if (word1.length() > word2.length()) {
                String tW = word1;
                word1 = word2;
                word2 = tW;
            }
            // word1.length < word2.length
            grade += getRatio(word1.length(), word2.length(), 50);
        }

        int c = 0;
        int j = 0;

        for (int i = 0; i < word1.length(); i++) {
            while (word1.charAt(i) != word2.charAt(j)) {
                j++;
                if (j >= word2.length()) {
                    break;
                }
            }

            if (j >= word2.length()) {
                break;
            } else {
                c++;
            }
        }
        grade += getRatio(c, word1.length(), 50);

        return grade;
    }

    public static boolean doCheck(Node n, Manga m, StringBuilder sb) {
        List<Node> listNodes = n.childNodes();
        if (listNodes.isEmpty()) {
            // node with text                 
            if ("#text".equals(n.nodeName())) {
                if (m.isWordsAccecptable(n.toString())) {
                    sb.append(n.toString());
                    return true;
                }
            }
        } else {
            for (Node no : listNodes) {
                if (doCheck(no, m, sb)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isContainChapter(String askingTextLowerCase) {
        String text = askingTextLowerCase.toLowerCase();
        for (Pattern p : DEFAULT_CHAPTER_LIST) {
            Matcher m = p.matcher(text);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    private static float getChapterNumberByRegex(Chapter chapter) {
        String str = chapter.getDisplayName().toLowerCase();
        for (Pattern p : DEFAULT_CHAPTER_LIST) {
            Matcher m = p.matcher(str);
            if (m.find()) {
                try {
                    return Float.parseFloat(m.group("cN"));
                } catch (Exception ex) {
                }
                break;
            }
        }
        return -1;
    }

    private static float getChapterNumberFromMangaNamme(Chapter chapter) {
        String str = chapter.getDisplayName().toLowerCase();
        Pattern p = Pattern.compile(chapter.getManga().getMangaName().toLowerCase() + PATTERN_SPACE + PATTERN_NUMBER_REGEX);

        Matcher m = p.matcher(str);

        if (m.find()) {
            try {
                return Float.parseFloat(m.group("cN"));
            } catch (Exception ex) {
            }
        }
        return -1;
    }

    public static float tryGetChapterNumber(Chapter chapter) {

        List<Integer> lstInteger = new ArrayList<>();
        if (getNumberOfDigitInString(chapter.getDisplayName(), lstInteger) == 0) {
            return 0;
        }

        if (lstInteger.size() == 1) {
            return lstInteger.get(0);
        }

        float cN = getChapterNumberByRegex(chapter);
        if (cN > 0) {
            return cN;
        }

        cN = getChapterNumberFromMangaNamme(chapter);
        if (cN > 0) {
            return cN;
        }

        for (int i = lstInteger.size() - 1; i >= 0; i--) {
            if (lstInteger.get(i) > 1000) {
                lstInteger.remove(i);
            }
        }

        // Improve later, check neighbor for information is a idea
        return lstInteger.get(0);

    }

    public static String repairXML(String text) {
        return text.replaceAll("</([A-Za-z]+)<([A-Za-z]+)", "</$1><$2");
    }
}
