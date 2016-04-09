package pl.edu.uj.kimage.legacy.plugins.color;

import pl.edu.uj.kimage.legacy.plugin.lut.LookupTablePlugin;

/**
 * <table border="0" class="img_block">
 * <caption></caption>
 * <tbody>
 * <tr>
 * <td>
 * <p>
 * <img src="../../image/doc-files/image.jpg" alt="None"></p>
 * <p>
 * Input</p>
 * </td>
 * <td>
 * <p>
 * <img src="doc-files/invert.png" alt="None"></p>
 * <p>
 * Output</p>
 * </td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Krzysztof
 */
public class Invert extends LookupTablePlugin {

    @Override
    protected void createLUT() {
        LUT = new short[256];
        for (short i = 0; i < 256; ++i) {
            LUT[i] = (short) (255 - i);
        }
    }

}
