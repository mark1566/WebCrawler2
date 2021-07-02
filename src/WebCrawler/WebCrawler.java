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

public class WebCrawler {

    private static final int MAX_PAGES = 5;

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private static Set<String> pagesVisited = new HashSet<String>();
    private static List<String> pagesToVisit = new LinkedList<String>();

    // Starting point for our crawl ... could be a parameter going forwards
    private static String startUrl = "https://www.cotswoldwildlifepark.co.uk/";
    private static String rootUrl = "";

    /**
     * Searches html content for urls and then scans each page found
     * @param html
     */
    public void search_page( Document html ) {

        Elements linksOnPage = html.select("a[href]");
        int new_links_found = 0;

        for( Element link : linksOnPage ) {
            if( link.absUrl( "href" ).contains( rootUrl )
                    && !pagesToVisit.contains(link.absUrl("href"))
                    && !pagesVisited.contains(link.absUrl("href")) ) {
                pagesToVisit.add(link.absUrl("href"));
                new_links_found++;
            }
        }

        System.out.println( " Links found = " + new_links_found );
    }


    public static void crawl( String url ) {
        System.out.print( "Visiting: " + url );
        if( url.endsWith( ".pdf" ) || url.endsWith( ".jpg" ) ) {
            System.out.println( " skipped");
            return;
        }
        if( url.contains( "?") ) {
            url = url.substring( 0, url.indexOf( "?" ));
            System.out.println( " removed parameters " + url );
        }
        // Get a connection using Jsoup and open the start page.
        Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
        Document htmlDocument = null;
        try {
            htmlDocument = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Start from the initial page and crawl through all found URLs
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.search_page( htmlDocument );
    }

    /**
     * main entry
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // establish our root url (remove up to www)
        rootUrl = startUrl.substring( startUrl.indexOf( "www." ) + 4 );
        System.out.println( "root = " + rootUrl );
        // Open the start page and get crawling
        pagesVisited.add( startUrl );
        crawl( startUrl );

        // Now loop through all the links we've found and crawl through those
        while( pagesToVisit.size() > 0 ) {
            // Get the first page to visit
            String nextUrl = pagesToVisit.get( 0 );
            pagesVisited.add( nextUrl );
            pagesToVisit.remove( 0 );
            crawl( nextUrl );
        }

        System.out.println( "At " + startUrl + " I visited " + pagesVisited.size() + " pages." );
        System.out.println( "Completed" );
    }

}
