package com.lee.crawler;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2017/4/7
 * @Description:
 */

/*
获取豆瓣TOP250电影的url如下:
https://movie.douban.com/top250?start=0&filter=
 */
public class Main {
    public static void main(String[] args) {

//        爬取https://movie.douban.com/top250 页面所有的电影的电影详情页地址
        for (int i = 0; i < 250; i += 25) {
            getMovieDetailsUrl(i);
        }

//      使用HtmlCleaner获取所有的电影名称,页面默认每次返回25条电影信息
//        for (int i = 0; i < 250; i += 25) {
//            getMoviename(i);
//        }
    }

    /**
     * 使用正则表达式爬取豆瓣top250所有电影的电影详情页地址
     */
    private static void getMovieDetailsUrl(int start) {
        HttpUtils.getResult("https://movie.douban.com/top250?start=" + start, new HttpUtils.ResultCallback() {
            @Override
            public void onResult(String result) {
                // 电影详情页对应地址的正则表达式

                List<String> hrefList = getHref(result);
                for (String href : hrefList) {
                    System.out.println(href);
                }

            }
        });
    }

    /**
     * @param start 从第几部电影开始
     */
    private static void getMoviename(int start) {
        HttpUtils.getResult("https://movie.douban.com/top250?start=" + start, new HttpUtils.ResultCallback() {
            @Override
            public void onResult(String result) {
                try {
                    getMovieName(result);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 爬去指定字符串中的指定的内容
     *
     * @param targetStr 指定的字符串
     */
    public static List<String> getHref(String targetStr) {
        String regexStr = "(https://movie.douban.com/subject/\\d*/)\">";
        List<String> stringList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(targetStr);
        while (matcher.find()) {
            stringList.add(matcher.group(1));
        }

        return stringList;
    }


    // 获取电影的名称
    public static void getMovieName(String target) throws ParserConfigurationException, XPathExpressionException {
        String exp = "//div[@class='hd']/a/span[1]";
        HtmlCleaner hc = new HtmlCleaner();
        // 获取根节点
        TagNode tn = hc.clean(target);
        // 将HTML组织成XML,并生成DOM对象
        Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
        // 获取XPath对象(XPath 是一门在 XML 文档中查找信息的语言。XPath 用于在 XML 文档中通过元素和属性进行导航)
        XPath xPath = XPathFactory.newInstance().newXPath();
        // 执行查找,生成NodeList对象
        Object result = xPath.evaluate(exp, dom, XPathConstants.NODESET);
        if (result instanceof NodeList) {
            NodeList nodeList = (NodeList) result;
//            System.out.println(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                /**
                 * Node.getTextContent() 此方法返回元素体(元素体可包含子元素或者文本)。
                 * Node.getFirstChild()  此节点的第一个子节点。
                 * Node.getAttributes() 包含此节点的属性的 NamedNodeMap（如果它是 Element）；否则为 null
                 * 如果想获取相应对象的相关属性，可以调用  getAttributes().getNamedItem("属性名") 方法
                 */
                System.out.println(node.getTextContent());
            }

        }
    }
}
