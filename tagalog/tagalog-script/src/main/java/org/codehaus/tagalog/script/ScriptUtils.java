/*
 * $Id: ScriptUtils.java,v 1.1 2005-03-31 21:34:18 mhw Exp $
 */

package org.codehaus.tagalog.script;

/**
 * Miscellaneous static utility methods.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision: 1.1 $
 */
public final class ScriptUtils {

    /**
     * Private constructor to prevent instantiation
     */
    private ScriptUtils() {
    }

    private static String[][] xmlReplacements = {
        { "&", "&amp;" },
        { "<", "&lt;" },
        { ">", "&gt;" },
        { "'", "&#039;" },
        { "\"", "&#034;" },
    };

    /**
     * Replace characters that have significance in XML with their
     * escaped equivalents, as per the JSTL specification.
     *
     * @param s String to be processed.
     * @return Processed string.
     */
    public static String escapeXml(String s) {
        for (int i = 0; i < xmlReplacements.length; i++) {
            s = s.replaceAll(xmlReplacements[i][0], xmlReplacements[i][1]);
        }
        return s;
    }
}