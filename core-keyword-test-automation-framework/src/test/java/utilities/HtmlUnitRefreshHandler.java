package utilities;

import com.gargoylesoftware.htmlunit.Page;

import java.net.URL;

/**
 * A wrapper class to override the refresh behavior of HtmlUnit.
 * This is a quick and dirty hack required to make HtmlUnit work with the ATH Movil user site.
 *
 * @author  Jonathan Diaz
 * @version 1.0
 * @since   09/03/2019
 */
public class HtmlUnitRefreshHandler implements com.gargoylesoftware.htmlunit.RefreshHandler {
    public HtmlUnitRefreshHandler() { }
    public void handleRefresh(final Page page, final URL url, final int seconds) { }
}