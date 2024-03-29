package winkkari.app;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.AllTipsDAO;
import winkkari.data.SortableTipWrapper;
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
        Comparator<SortableTipWrapper> comparator = getComparator(req);
        return new ModelAndView(Map.ofEntries(
                Map.entry("tips",
                        genericDAO.getAll()
                                .stream()
                                .filter(tip -> Optional.ofNullable(req.queryParams("search"))
                                        .map(searchStr -> typeMatches(tip, searchStr))
                                        .orElse(true))
                                .map(SortableTipWrapper::new)
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

    private Comparator getComparator(Request req) {
        Comparator comparator = Comparator.comparing(SortableTipWrapper::getTitle);
        String sortBy = Optional.ofNullable(req.queryParams("sortBy")).orElse("");

        if (sortBy.equalsIgnoreCase("title")) {
            comparator = Comparator.comparing(SortableTipWrapper::getTitle);
        } else if (sortBy.equalsIgnoreCase("author")) {
            comparator = Comparator.comparing(SortableTipWrapper::getAuthor);
        } else if (sortBy.equalsIgnoreCase("URL")) {
            comparator = Comparator.comparing(SortableTipWrapper::getUrl);
        } else if (sortBy.equalsIgnoreCase("comment")) {
            comparator = Comparator.comparing(SortableTipWrapper::getComment);
        }

        String order = Optional.ofNullable(req.queryParams("order")).orElse("");
        if (order.equalsIgnoreCase("DESC")) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
