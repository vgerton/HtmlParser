package org.htmlparser.fileprocessing;


import org.htmlparser.data.structure.Node;
import org.htmlparser.data.structure.Tree;
import org.htmlparser.tag.Tags;

public class OpeningTagState implements State {

    private Tree tree;
    private HtmlReader htmlReader;
    private StringBuilder tag;

    public OpeningTagState(HtmlReader htmlReader, char c, Tree tree) {
        this.htmlReader = htmlReader;
        this.tag = new StringBuilder().append(c);
        this.tree = tree;
    }

    @Override
    public void process(char c) {
        if (c == '>') {
            if (Tags.isElement(new String(tag))) {
                tree.addChild(new Node(new String(tag)));
                htmlReader.setState(new TextState(htmlReader, tree));
            } else {
                tag.insert(0, '<');
                tag.append('>');
                htmlReader.setState(new TextState(htmlReader, tree, tag));
            }
        } else if (c == ' ') {
            if (Tags.isElement(new String(tag))) {
                tree.addChild(new Node(new String(tag)));
                htmlReader.setState(new IgnoredState(htmlReader, tree));
            } else {
                tag.append(c);
            }
        } else {
            tag.append(c);
        }
    }

}
