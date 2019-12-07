package winkkari.app;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.AllTipsDAO;
import winkkari.data.Tip;

import java.util.Arrays;
import java.util.Comparator;
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
        Comparator<Tip> comparator = Comparator.comparing(Tip::getTitle);
        if (Optional.ofNullable(req.queryParams("order")).isEmpty()) {
            comparator = Comparator.comparing(Tip::getTitle);
        } else if (req.queryParams("order").equalsIgnoreCase("ASC")) {
            comparator = Comparator.comparing(Tip::getTitle);
        } else if (req.queryParams("order").equalsIgnoreCase("DESC")) {
            comparator = Comparator.comparing(Tip::getTitle).reversed();
        }
        return new ModelAndView(Map.ofEntries(
                Map.entry("tips",
                        genericDAO.getAll()
                                .stream()
                                .filter(tip -> Optional.ofNullable(req.queryParams("search"))
                                        .map(searchStr -> typeMatches(tip, searchStr))
                                        .orElse(true))
                                .sorted(comparator)
                                .collect(Collectors.toList())),
                Map.entry("search", Optional.ofNullable(req.queryParams("search")).orElse("")),
                Map.entry("sortBy", Optional.ofNullable(req.queryParams("sortBy")).orElse("")),
                Map.entry("order", Optional.ofNullable(req.queryParams("order")).orElse(""))),
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
