package de.fmk.hammerhead.common.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 14.11.2015.
 */
public final class XMLDataHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLDataHandler.class);

    private final Document document;


    public XMLDataHandler(Path xmlFile) throws Exception {
        final DocumentBuilder builder;

        if (xmlFile == null || !Files.isRegularFile(xmlFile) ||
            !xmlFile.getFileName().toString().toLowerCase().endsWith(".xml"))
            throw new IllegalArgumentException(xmlFile.getFileName().toString() + " is an invalid file.");

        builder  = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = builder.parse(Files.newInputStream(xmlFile));
    }


    public List<Entry> parse() {
        final List<Entry> entries;
        final NodeList    nodes;

        document.getDocumentElement().normalize();

        entries = new ArrayList<>();
        nodes   = document.getElementsByTagName(XML_CONSTANTS.EL_ENTRY);

        for (int i = 0; i < nodes.getLength(); i++) {
            final Node     entryNode;
            final NodeList columnNodes;
            final Entry    entry;

            entryNode = nodes.item(i);

            if (entryNode.getNodeType() != Node.ELEMENT_NODE || !entryNode.hasChildNodes())
                continue;

            entry       = new Entry();
            columnNodes = entryNode.getChildNodes();

            for (int j = 0; j < columnNodes.getLength(); j++) {
                final Node         colNode;
                final NamedNodeMap attributes;
                final Column       column;

                colNode = columnNodes.item(j);

                if (colNode.getNodeType() != Node.ELEMENT_NODE || !colNode.hasAttributes())
                    continue;

                attributes = colNode.getAttributes();
                column     = new Column(attributes.getNamedItem(XML_CONSTANTS.AT_KEY).getNodeValue());

                column.setType(attributes.getNamedItem(XML_CONSTANTS.AT_TYPE).getNodeValue());
                column.setValue(colNode.getTextContent());

                entry.addColumn(column);
            }

            entries.add(entry);
        }

        return entries;
    }
}
