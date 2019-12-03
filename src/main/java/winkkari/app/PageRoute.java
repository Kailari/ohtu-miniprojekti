package winkkari.app;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

public interface PageRoute {
    default ModelAndView get(Request req, Response res) {
        return new ModelAndView(Map.of(), "404");
    }

    default Object post(Request req, Response res) {
        return res;
    }
}
