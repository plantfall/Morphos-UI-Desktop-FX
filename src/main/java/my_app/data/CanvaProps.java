package my_app.data;

import java.io.Serializable;

//canvaProps
public class CanvaProps implements Serializable {

    public int padding_top;
    public int padding_right;
    public int padding_bottom;
    public int padding_left;
    public double width;
    public double height;
    public String bg_type;
    public String bgContent;
    public String identification;
    public int x;
    public int y;

    public CanvaProps() {
    }

    public CanvaProps(
            int padding_top,
            int padding_right,
            int padding_bottom,
            int padding_left,
            double width,
            double height,
            String bg_type,
            String bgContent,
            String identification,
            int x, int y) {

        this.padding_top = padding_top;
        this.padding_right = padding_right;
        this.padding_bottom = padding_bottom;
        this.padding_left = padding_left;
        this.width = width;
        this.height = height;
        this.bg_type = bg_type;
        this.bgContent = bgContent;
        this.identification = identification;
        this.x = x;
        this.y = y;
    }

}