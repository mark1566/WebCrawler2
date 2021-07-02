package WebCrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    private static final int MAX_PAGES = 5;

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private static Set<String> pagesVisited = new HashSet<String>();
    private static List<String> pagesToVisit = new LinkedList<String>();

    // Starting point for our crawl ... could be a parameter going forwards
    private static String startUrl = "https://www.cotswoldwildlifepark.co.uk/";
    private static String rootUrl = "cotswoldwildlifepark.co.uk";
     // private static String startUrl = "http://www.asplen.co.uk";


    /**
     * Searches html content for urls and then scans each page found
     * @param html
     */
    public void search_page( Document html ) {

        Elements linksOnPage = html.select("a[href]");

        System.out.println("Found (" + linksOnPage.size() + ") links");
        for(Element link : linksOnPage)
        {
            pagesToVisit.add(link.absUrl("href"));
        }

        //  Pattern linkPattern = Pattern.compile("<a\\s?href=['\"](.*?)[\"']>(.*?)</a>", Pattern.DOTALL);
        //  Matcher pageMatcher = linkPattern.matcher(html);
        //    System.out.println( html );
         // while(pageMatcher.find()){
          //      String next_url = pageMatcher.group(1); //
/*                if( !pagesVisited.contains(next_url)) {
                    pagesToVisit.add(next_url);
                    System.out.println( next_url );
                    try {
                        URL url = new URL(next_url);
                        String s = url.getProtocol() + "://" + url.getHost() + url.getPath();

                        if( s.contains(rootUrl)) {
                            System.out.println( "X" + new URL(s) );
                            this.scan_page(new URL(s));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
          //}
          System.out.println( pagesToVisit );
    }

    /**
     * Reads page content for a url and then gets it searched
     * @param url
     * @throws IOException
     */
    public void scan_page(URL url) throws IOException {
        pagesVisited.add( url.toString() );
        BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

        System.out.println("PAGE:" + url);
        StringBuffer sb;
        Scanner sc = new Scanner(url.openStream());

        sb = new StringBuffer();
        while (sc.hasNext()) {
            sb.append(sc.next());
        }
      //  search_page( sb.toString() );
        in.close();
    }


    /**
     * main entry
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Connection connection = Jsoup.connect(startUrl).userAgent(USER_AGENT);
        Document htmlDocument = connection.get();
        System.out.println( htmlDocument);

        //URL url = new URL(startUrl);
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.search_page(htmlDocument);

    }

}
