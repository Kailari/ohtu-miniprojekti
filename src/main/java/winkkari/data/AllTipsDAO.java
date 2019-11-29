package winkkari.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AllTipsDAO implements TipDAO<Tip> {
    private final TipDAO<BookTip> bookDAO;
    private final TipDAO<LinkTip> linkDAO;

    public AllTipsDAO(TipDAO<BookTip> bookDAO, TipDAO<LinkTip> linkDAO) {
        this.bookDAO = bookDAO;
        this.linkDAO = linkDAO;
    }

    @Override
    public void add(Tip tip) {
        switch (tip.getType()) {
            case BOOK:
                bookDAO.add((BookTip) tip);
                break;
            case LINK:
                linkDAO.add((LinkTip) tip);
                break;
            case VIDEO:
                throw new UnsupportedOperationException("Not implemented!");
        }
    }

    @Override
    public Collection<Tip> getAll() {
        var all = new ArrayList<Tip>();
        all.addAll(bookDAO.getAll());
        all.addAll(linkDAO.getAll());
        //all.addAll(videoDAO.getAll());
        return all;
    }

    @Override
    @Deprecated
    public Optional<Tip> get(String id) {
        throw new UnsupportedOperationException("Cannot get by ID from generic DAO. Use specific DAO or type-sensitive overload!");
    }

    public Optional<Tip> get(Tip.Type type, String id) {
        switch (type) {
            case BOOK:
                return bookDAO.get(id).map(Tip.class::cast);
            case LINK:
                return linkDAO.get(id).map(Tip.class::cast);
            default:
            case VIDEO:
                throw new UnsupportedOperationException("Not implemented!");
        }
    }

    @Override
    @Deprecated
    public void delete(String id) {
        throw new UnsupportedOperationException("Cannot remove by ID from generic DAO. Use specific DAO or type-sensitive overload!");
    }

    public void delete(Tip.Type type, String id) {
        switch (type) {
            case BOOK:
                bookDAO.delete(id);
                break;
            case LINK:
                linkDAO.delete(id);
                break;
            default:
            case VIDEO:
                throw new UnsupportedOperationException("Not implemented!");
        }
    }

    @Override
    @Deprecated
    public void check(String id, String check) {
        throw new UnsupportedOperationException("Cannot remove by ID from generic DAO. Use specific DAO or type-sensitive overload!");
    }
    
    public void check(Tip.Type type, String id, String check) {
        switch (type) {
            case BOOK:
                bookDAO.check(id, check);
                break;
            case LINK:
                linkDAO.check(id, check);
                break;
            default:
            case VIDEO:
                throw new UnsupportedOperationException("Not implemented!");
        }
    }
}
