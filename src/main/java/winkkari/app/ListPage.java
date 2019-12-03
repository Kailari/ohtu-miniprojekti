package winkkari.app;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.AllTipsDAO;
import winkkari.data.Tip;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListPage implements PageRoute {
    private final AllTipsDAO genericDAO;

    public ListPage(AllTipsDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        return new ModelAndView(Map.ofEntries(
                Map.entry("tips",
                          genericDAO.getAll()
                                    .stream()
                                    .filter(tip -> Optional.ofNullable(req.queryParams("search"))
                                                           .map(searchStr -> typeMatches(tip, searchStr))
                                                           .orElse(true))
                                    .collect(Collectors.toList()))),
                                "list");
    }

    private boolean typeMatches(Tip tip, String searchStr) {
        return Arrays.stream(Tip.Type.values())
                     .filter(type -> type.name().equalsIgnoreCase(searchStr))
                     .findFirst()
                     .map(type -> type.equals(tip.getType()))
                     .orElse(true);
    }
}
