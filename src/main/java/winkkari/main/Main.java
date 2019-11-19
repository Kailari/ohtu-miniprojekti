package winkkari.main;

import winkkari.app.Winkkari;
import winkkari.data.Tip;
import winkkari.data.TipDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Winkkari winkkari = new Winkkari(new TipDAO() {
            private final List<Tip> tips = new ArrayList<>(List.of(
                    new Tip("1", "Some Amazing Book 1", "Some Author 1"),
                    new Tip("1", "Some Amazing Book 2", "Some Author 2"),
                    new Tip("1", "Some Amazing Book 3", "Some Author 3")
            ));

            @Override
            public void add(Tip tip) {
                tips.add(tip);
            }

            @Override
            public Optional<Tip> get(String id) {
                return tips.stream()
                           .filter(tip -> tip.getId().equals(id))
                           .findFirst();
            }

            @Override
            public Collection<Tip> getAll() {
                return tips;
            }
        });
        winkkari.run();
    }
}
