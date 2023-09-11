package shoppinator.core.interfaces;

import java.net.URL;
import javax.swing.text.html.parser.Element;

public interface Scrapper {

    Element scrap(String productName);
}
