package com.vance.demo.tool;

import java.util.Arrays;

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarkdownToHtml {
    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS,
                Arrays.asList(
                        TablesExtension.create(),
                        StrikethroughExtension.create()));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String source = "Hello **world**, _this_ is a *test*.";
        Node document = parser.parse(source);
        String output = renderer.render(document);
        log.info(output);
    }
}
