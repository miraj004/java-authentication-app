package components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SliderUI extends BasicSliderUI {

    private static final int TRACK_HEIGHT = 10;
    private static final int TRACK_WIDTH = 10;
    private static final int TRACK_ARC = 1;
    private static final Dimension THUMB_SIZE = new Dimension(5, 10);
    private static final Dimension THUMB_SIZE_VERTICAL = new Dimension(10, 5);
    private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

    public SliderUI(final JSlider b) {
        super(b);
    }

    @Override
    protected void installDefaults(JSlider slider) {
        super.installDefaults(slider);
        slider.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    protected void calculateTrackRect() {
        super.calculateTrackRect();
        if (isHorizontal()) {
            trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
            trackRect.height = TRACK_HEIGHT;
        } else {
            trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
            trackRect.width = TRACK_WIDTH;
        }
        trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
    }

    @Override
    protected void calculateThumbLocation() {
        super.calculateThumbLocation();
        if (isHorizontal()) {
            thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
        } else {
            thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
        }
    }

    @Override
    protected Dimension getThumbSize() {
        if(isHorizontal()) {
            return THUMB_SIZE;
        }else{
            return THUMB_SIZE_VERTICAL;
        }
    }

    private boolean isHorizontal() {
        return slider.getOrientation() == JSlider.HORIZONTAL;
    }

    @Override
    public void paint(final Graphics g, final JComponent c) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    public void paintTrack(final Graphics g) {
        super.paintTrack(g);
        Graphics2D g2 = (Graphics2D) g;
        Shape clip = g2.getClip();

        boolean horizontal = isHorizontal();
        boolean inverted = slider.getInverted();

        // Paint shadow.
        g2.setColor(new Color(213, 217, 223));
        g2.fill(trackShape);

        // Paint track background.
        g2.setColor(new Color(213, 217, 223));
        g2.setClip(trackShape);
        trackShape.y += 1;
        g2.fill(trackShape);
        trackShape.y = trackRect.y;

        g2.setClip(clip);

        // Paint selected track.
        if (horizontal) {
            boolean ltr = slider.getComponentOrientation().isLeftToRight();
            if (ltr) inverted = !inverted;
            int thumbPos = thumbRect.x + thumbRect.width / 2;
            if (inverted) {
                g2.clipRect(0, 0, thumbPos, slider.getHeight());
            } else {
                g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
            }

        } else {
            int thumbPos = thumbRect.y + thumbRect.height / 2;
            if (inverted) {
                g2.clipRect(0, 0, slider.getHeight(), thumbPos);
            } else {
                g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
            }
        }
        g2.setColor(new Color(213, 217, 223));
        g2.fill(trackShape);
        g2.setClip(clip);
    }

    @Override
    public void paintThumb(final Graphics g) {
        g.setColor(new Color(183, 187, 193));
//        g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        g.fillRect(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }

    @Override
    public void paintFocus(final Graphics g) {}
}