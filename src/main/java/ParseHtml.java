import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ParseHtml {
    private String pathToHtml = "data/metro_moscow.html";
    public List<Line> lines;
    public List<Station> stations;

    public ParseHtml() {
        parseHtml();
    }

    private void parseHtml() {
        lines = new ArrayList<>();
        stations = new ArrayList<>();
        File htmlFile = new File(pathToHtml);

        try {

            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            Elements linesInfo = doc.select("span.js-metro-line");
            for (Element element : linesInfo) {
                lines.add(new Line(element.text(), element.attr("data-line")));
            }

            Elements stationsPerLine = doc.getElementsByClass("js-metro-stations");
            for (Element element : stationsPerLine) {
                Elements stationsInfo = element.getElementsByClass("single-station");
                for (Element station : stationsInfo) {
                    String name = station.getElementsByClass("name").text();
                    String line = element.attr("data-line");
                    Boolean hasConnection = station.select("span.t-icon-metroln").hasAttr("title");
                    stations.add(new Station(name, line, hasConnection));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Line> getLines() {
        return lines;
    }

    public List<Station> getStations() {
        return stations;
    }
}