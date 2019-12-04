package winkkari.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import winkkari.mock.TestBookTipDAO;
import winkkari.mock.TestLinkTipDAO;
import winkkari.mock.TestVideoTipDAO;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
class AllTipsDAOTest {
    private AllTipsDAO dao;
    private TestBookTipDAO bookDao;
    private TestLinkTipDAO linkDao;
    private TestVideoTipDAO videoDao;
    private BookTip bookTip;
    private LinkTip linkTip;
    private VideoTip videoTip;

    @BeforeEach
    void beforeEach() {
        bookDao = new TestBookTipDAO();
        bookDao.add(new BookTip("test", "test", "test"));
        bookTip = bookDao.getAll().stream().findFirst().get();

        linkDao = new TestLinkTipDAO();
        linkDao.add(new LinkTip("test", "test", "test"));
        linkTip = linkDao.getAll().stream().findFirst().get();

        videoDao = new TestVideoTipDAO();
        videoDao.add(new VideoTip("test", "test", "test"));
        videoTip = videoDao.getAll().stream().findFirst().get();

        dao = new AllTipsDAO(bookDao, linkDao, videoDao);
    }

    @Test
    void getWithoutTypeThrows() {
        assertThrows(UnsupportedOperationException.class, () -> dao.get("0"));
    }

    @Test
    void deleteWithoutTypeThrows() {
        assertThrows(UnsupportedOperationException.class, () -> dao.delete("0"));
    }

    @Test
    void updateWithoutTypeThrows() {
        assertThrows(UnsupportedOperationException.class, () -> dao.update(new BookTip("", "", "")));
    }

    @Test
    void checkWithoutTypeThrows() {
        assertThrows(UnsupportedOperationException.class, () -> dao.check("0", true));
    }

    @Test
    void gettingBooksWorks() {
        assertTrue(dao.get(Tip.Type.BOOK, bookTip.getId()).isPresent());
    }

    @Test
    void gettingLinksWorks() {
        assertTrue(dao.get(Tip.Type.LINK, linkTip.getId()).isPresent());
    }

    @Test
    void gettingVideosWorks() {
        assertTrue(dao.get(Tip.Type.VIDEO, videoTip.getId()).isPresent());
    }

    @Test
    void addingBooksWorks() {
        dao.add(new BookTip("book", "book", "book"));
        assertEquals(2, bookDao.getAll().size());
    }

    @Test
    void addingLinksWorks() {
        dao.add(new LinkTip("link", "link", "link"));
        assertEquals(2, linkDao.getAll().size());
    }

    @Test
    void addingVideosWorks() {
        dao.add(new VideoTip("video", "video", "video"));
        assertEquals(2, videoDao.getAll().size());
    }

    @Test
    void deletingBooksWorks() {
        dao.delete(Tip.Type.BOOK, bookTip.getId());
        assertTrue(bookDao.getAll().isEmpty());
    }

    @Test
    void deletingLinksWorks() {
        dao.delete(Tip.Type.LINK, linkTip.getId());
        assertTrue(linkDao.getAll().isEmpty());
    }

    @Test
    void deletingVideosWorks() {
        dao.delete(Tip.Type.VIDEO, videoTip.getId());
        assertTrue(videoDao.getAll().isEmpty());
    }

    @Test
    void updatingBooksWorks() {
        dao.update(Tip.Type.BOOK, new BookTip(bookTip.getId(), "updated", "updated", "updated", true));

        var updated = bookDao.get(bookTip.getId()).get();
        assertEquals("updated", updated.getTitle());
        assertEquals("updated", updated.getAuthor());
        assertEquals("updated", updated.getIsbn());
        assertTrue(updated.getCheck());
    }

    @Test
    void updatingLinksWorks() {
        dao.update(Tip.Type.LINK, new LinkTip(linkTip.getId(), "updated", "updated", "updated", true));

        var updated = linkDao.get(linkTip.getId()).get();
        assertEquals("updated", updated.getTitle());
        assertEquals("updated", updated.getUrl());
        assertEquals("updated", updated.getComment());
        assertTrue(updated.getCheck());
    }

    @Test
    void updatingVideosWorks() {
        dao.update(Tip.Type.VIDEO, new VideoTip(videoTip.getId(), "updated", "updated", "updated", true));

        var updated = videoDao.get(videoTip.getId()).get();
        assertEquals("updated", updated.getTitle());
        assertEquals("updated", updated.getUrl());
        assertEquals("updated", updated.getComment());
        assertTrue(updated.getCheck());
    }

    @Test
    void checkingBooksWorks() {
        dao.check(Tip.Type.BOOK, bookTip.getId(), bookTip.getCheck());

        var checked = bookDao.get(bookTip.getId()).get();
        assertTrue(checked.getCheck());
    }

    @Test
    void checkingLinksWorks() {
        dao.check(Tip.Type.LINK, linkTip.getId(), linkTip.getCheck());

        var checked = linkDao.get(linkTip.getId()).get();
        assertTrue(checked.getCheck());
    }

    @Test
    void checkingVideosWorks() {
        dao.check(Tip.Type.VIDEO, videoTip.getId(), videoTip.getCheck());

        var checked = videoDao.get(videoTip.getId()).get();
        assertTrue(checked.getCheck());
    }

    @Test
    void getAllReturnsAll() {
        assertTrue(dao.getAll().stream().anyMatch(tip -> tip.getId().equals(bookTip.getId())));
        assertTrue(dao.getAll().stream().anyMatch(tip -> tip.getId().equals(linkTip.getId())));
        assertTrue(dao.getAll().stream().anyMatch(tip -> tip.getId().equals(videoTip.getId())));
    }
}
