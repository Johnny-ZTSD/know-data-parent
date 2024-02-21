package com.knowdata.framework.core.util.markdown;

import com.knowdata.framework.core.util.io.FileUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @description Markdown Java 解析器
 * @reference-doc
 *  [1] commonmark-java - codingdict - https://www.codingdict.com/os/software/51515
 *  [2] [Java] 202006-3 Markdown渲染器 - CSDN - https://blog.csdn.net/weixin_41714373/article/details/109112509
 */
public class MarkdownUtils {
    private static final Logger logger = LoggerFactory.getLogger(MarkdownUtils.class);

    public static String HTML_DOCUMENT_DOCTYPE_ELEMENT = "<!DOCTYPE html>";

    public static String HTML_DOCUMENT_HTML_ELEMENT_START = "<html lang=\"en\">";
    public static String HTML_DOCUMENT_HTML_ELEMENT_END = "</html>";

    public static String HTML_DOCUMENT_HEAD_ELEMENT_START = "<head>";
    public static String HTML_DOCUMENT_HEAD_ELEMENT_END = "</head>";

    public static String HTML_DOCUMENT_META_CHARSET_ELEMENT = "<meta charset=\"UTF-8\">";
    public static String HTML_DOCUMENT_META_VIEWPORT_ELEMENT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";

    public static String HTML_DOCUMENT_TITLE_ELEMENT_END = "<title>Markdown File</title>";

    public static String HTML_DOCUMENT_BODY_ELEMENT_START = "<body>";
    public static String HTML_DOCUMENT_BODY_ELEMENT_END = "</body>";

    // HTML 文档基本结构
    public static String HTML_DOCUMENT_BASE_CONTENT = "\n<html lang=\"en\">";

    /**
     * 渲染为 HTML
     */
    public static String covertToHtml(String markdownContent, Map<String, String> properties){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return generateHtml(renderer.render(document), properties);
    }

    private static String generateHtml(String bodyContent, Map<String, String> properties){
        StringBuilder htmlBuilder = new StringBuilder();
        //<html>
        htmlBuilder.append(HTML_DOCUMENT_DOCTYPE_ELEMENT);
        htmlBuilder.append(HTML_DOCUMENT_HTML_ELEMENT_START);

        //<head>
        htmlBuilder.append(HTML_DOCUMENT_HEAD_ELEMENT_START);
        htmlBuilder.append(HTML_DOCUMENT_META_CHARSET_ELEMENT);
        htmlBuilder.append(HTML_DOCUMENT_META_VIEWPORT_ELEMENT);
        htmlBuilder.append(HTML_DOCUMENT_TITLE_ELEMENT_END);
        htmlBuilder.append(HTML_DOCUMENT_HEAD_ELEMENT_END);

        //body
        htmlBuilder.append(HTML_DOCUMENT_BODY_ELEMENT_START);
        htmlBuilder.append(bodyContent);
        htmlBuilder.append(HTML_DOCUMENT_BODY_ELEMENT_END);

        return formatHtml( htmlBuilder.toString() );
    }

    /**
     * 格式化 HTML 文档
     * @return
     */
    public static String formatHtml(String html){
        //TODO
        logger.debug("success to format html!");
        return html;
    }

    public static void main(String[] args) throws IOException {
        String fileName = "markdown-file.html";
        String markdownContent = "This is *Sparta*";
        // "<p>This is <em>Sparta</em></p>\n"
        String html = covertToHtml(markdownContent, null);
        logger.warn(html);
        FileUtils.writeToFile(html, "C:\\Users\\xxxxxx\\Desktop\\" + fileName);
    }
}
