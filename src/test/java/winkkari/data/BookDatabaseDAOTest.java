package winkkari.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookDatabaseDAOTest {
    private BookDatabaseDAO dao;
    private Connection connection;
    private BookDatabaseDAO.ConnectionProvider connectionProvider;

    private class ConnectionProvider implements AbstractDatabaseDAO.ConnectionProvider {
        @Override
        public Connection get() throws SQLException {
            return connection = spy(DriverManager.getConnection("jdbc:h2:test.db"));
        }
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.delete(Paths.get("./test.db.h2.db"));
            Files.delete(Paths.get("./test.db.trace.db"));
            Files.delete(Paths.get("./test.db.lock.db"));
        } catch (IOException ignored) {
        }
    }

    @BeforeEach
    void beforeEach() {
        try {
            Files.delete(Paths.get("./test.db.h2.db"));
            Files.delete(Paths.get("./test.db.trace.db"));
            Files.delete(Paths.get("./test.db.lock.db"));
        } catch (IOException ignored) {
        }

        connectionProvider = spy(new ConnectionProvider());
        dao = new BookDatabaseDAO(connectionProvider);
        reset(connectionProvider);
    }

    @Test
    void addingTipWithValidInfoDoesOneQuery() throws SQLException {
        dao.add(new BookTip("test", "test", "isbn"));

        verify(connectionProvider, times(1)).get();
        verify(connection, times(1)).prepareStatement(anyString());
    }

    @Test
    void afterAddingTipGetAllContainsTheTip() {
        dao.add(new BookTip("testTitle", "testAuthor", "testIsbn"));

        var result = dao.getAll().stream().findFirst().get();
        assertEquals("testTitle", result.getTitle());
        assertEquals("testAuthor", result.getAuthor());
        assertEquals("testIsbn", result.getIsbn());
    }

    @Test
    void afterAddGetReturnsTheCorrectTip() {
        dao.add(new BookTip("testTitle", "testAuthor", "testIsbn"));
        var added = dao.getAll().stream().findFirst().get();

        var result = dao.get(added.getId()).get();
        assertEquals("testTitle", result.getTitle());
        assertEquals("testAuthor", result.getAuthor());
        assertEquals("testIsbn", result.getIsbn());
    }

    @Test
    void gettingWithInvalidIDReturnsEmpty() {
        assertFalse(dao.get("1234").isPresent());
    }

    @Test
    void tipsAreUncheckedByDefault() {
        dao.add(new BookTip("testTitle", "testAuthor", "testIsbn"));
        var added = dao.getAll().stream().findFirst().get();
        assertFalse(added.getCheck());
    }

    @Test
    void checkingATipTogglesItsStatus() {
        dao.add(new BookTip("testTitle", "testAuthor", "testIsbn"));
        var added = dao.getAll().stream().findFirst().get();
        dao.check(added.getId(), added.getCheck());
        var afterChecking = dao.getAll().stream().findFirst().get();
        assertTrue(afterChecking.getCheck());
    }

    @Test
    void checkingATipAgainTogglesItsStatus() {
        dao.add(new BookTip("testTitle", "testAuthor", "testIsbn"));
        var added = dao.getAll().stream().findFirst().get();
        dao.check(added.getId(), added.getCheck());
        var checked = dao.getAll().stream().findFirst().get();
        dao.check(checked.getId(), checked.getCheck());

        var afterChecking = dao.getAll().stream().findFirst().get();
        assertFalse(afterChecking.getCheck());
    }

    @Test
    void gettingTipReturnsEmptyAfterDeletingTheTip() {
        dao.add(new BookTip("testTitle", "testAuthor", "testIsbn"));
        var added = dao.getAll().stream().findFirst().get();

        dao.delete(added.getId());
        assertFalse(dao.get(added.getId()).isPresent());
    }

    @Test
    void updatingTipChangesItsFields() {
        dao.add(new BookTip("original", "original", "original"));
        var added = dao.getAll().stream().findFirst().get();
        dao.update(new BookTip(added.getId(), "updated", "updated", "updated", true));

        var updated = dao.getAll().stream().findFirst().get();
        assertEquals("updated", updated.getTitle());
        assertEquals("updated", updated.getAuthor());
        assertEquals("updated", updated.getIsbn());
        assertTrue(updated.getCheck());
    }
}
