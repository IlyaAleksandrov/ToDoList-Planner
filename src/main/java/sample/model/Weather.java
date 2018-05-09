package sample.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weather {

    public static String getWeather() throws IOException {
        StringBuilder weatherList = new StringBuilder("");
        weatherList.append("\t\t\t  Weather at this time: \t");

        String url = "https://pogoda.ngs.ru/?";
        Document s = Jsoup.parse(new URL(url), 3000);

        Element e1 = s.select("span[class = value__main]").first();
        Pattern pattern = Pattern.compile("(\\++|\\-)?[0-9]{1,2}");
        Matcher matcher = pattern.matcher(e1.toString());
        if(matcher.find())
            weatherList.append(matcher.group()  + "\t");
        Element e2 = s.select("span[class = value-description]").first();
        String[] strings = e2.toString().split(">");
        weatherList.append(strings[1].split("<")[0]);

        weatherList.append("\n\t\t\t  Weather tommorow: \t");

        Elements e3 = s.select("div[class = elements__section__item elements__section__item-day]");
        String[] elements1 = e3.toString().split("<");
        weatherList.append(elements1[3].substring(elements1[3].length()-5, elements1[3].length() - 2) + "\t");

        Elements e4 = s.select("td[class = elements__section-weather]");
        String[] elements2 = e4.toString().split("<td");
        String[] elements3 = elements2[2].split("\"");
        weatherList.append(elements3[13] + "\t");

        return weatherList.toString();
    }

    }
