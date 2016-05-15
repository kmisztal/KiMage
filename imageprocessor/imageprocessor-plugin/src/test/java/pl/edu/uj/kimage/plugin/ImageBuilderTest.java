package pl.edu.uj.kimage.plugin;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

public class ImageBuilderTest {

    private static final int ZERO_INDEX = 0;
    private static final int ONE_INDEX = 1;
    private static final int TWO_INDEX = 2;
    private static final int THREE_INDEX = 3;
    private static final int ZERO_PIXELS = 0;
    private static final int ONE_PIXEL = 1;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void cantBuildImageWith0Width() {
        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Width cannot be 0");

        // when
        new ImageBuilder(ZERO_PIXELS, ONE_PIXEL);
    }

    @Test
    public void cantBuildImageWith0Height() {
        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Height cannot be 0");

        // when
        new ImageBuilder(ONE_PIXEL, ZERO_PIXELS);
    }


    @Test
    public void throwsExceptionWhenTryToSetColorWithInvalidXCoordinate() {
        // given
        ImageBuilder builder = new ImageBuilder(ONE_PIXEL, ONE_PIXEL);

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Wrong pixel coordinates x=1, y=0. Image width: 1, image height: 1");

        // when
        builder.withColor(Color.BLACK, ONE_PIXEL, ZERO_PIXELS);
    }

    @Test
    public void throwsExceptionWhenTryToSetColorWithInvalidYCoordinate() {
        // given
        ImageBuilder builder = new ImageBuilder(ONE_PIXEL, ONE_PIXEL);

        // expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Wrong pixel coordinates x=0, y=1. Image width: 1, image height: 1");

        // when
        builder.withColor(Color.BLACK, ZERO_PIXELS, ONE_PIXEL);
    }

    @Test
    public void throwsExceptionWhenNotAllPixelsHaveColor() {
        // given
        int width = 4;
        int height = 2;
        ImageBuilder builder = new ImageBuilder(width, height);
        builder.withColor(Color.BLACK, ZERO_INDEX, ZERO_INDEX);
        builder.withColor(Color.BLACK, ONE_INDEX, ZERO_INDEX);
        builder.withColor(Color.BLACK, TWO_INDEX, ZERO_INDEX);
        builder.withColor(Color.BLACK, THREE_INDEX, ZERO_INDEX);
        builder.withColor(Color.BLACK, ZERO_INDEX, ONE_INDEX);
        builder.withColor(Color.BLACK, ONE_INDEX, ONE_INDEX);
        builder.withColor(Color.BLACK, THREE_INDEX, ONE_INDEX);

        // expect
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Null color at x=2, y=1");

        // when
        builder.build();
    }
}