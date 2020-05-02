import controllers.HueHttpController;
import dtos.HueLight;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static Logger logger = Logger.getLogger("Main");
    private static final HueHttpController controller = new HueHttpController("192.168.50.63", "nDUnfbZceW0m6wrMM3TK8rAbr2Sqgfu5N8LtdiMQ", logger);

    public static void main(String[] args) throws IOException {
        List<HueLight> lights = controller.getLights();
        for (HueLight h : lights) {
            System.out.println(h);
            controller.toggleLight(h);
        }
    }
}
