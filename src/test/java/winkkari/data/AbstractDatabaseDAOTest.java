package winkkari.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AbstractDatabaseDAOTest {
    private AbstractDatabaseDAO<BookTip> dao;

    @BeforeEach
    void beforeEach() throws Exception {
        var connection = mock(Connection.class);
        var createStatement = mock(PreparedStatement.class);
        doReturn(createStatement).when(connection).prepareStatement(any());

        dao = spy(new BookDatabaseDAO(() -> connection));
    }

    @Test
    void addDoesNotThrowEvenIfGettingAddQueryFails() throws SQLException {
        doThrow(new SQLException("Expected exception")).when(dao).getAddQuery(any(), any());
        assertDoesNotThrow(() -> dao.add(new BookTip("test", "test", "test")));
    }

    @Test
    void getReturnsEmptyIfGettingGetQueryFails() throws SQLException {
        doThrow(new SQLException("Expected exception")).when(dao).getGetQuery(any(), any());
        assertFalse(dao.get("0").isPresent());
    }

    @Test
    void getAllReturnsEmptyCollectionIfGettingGetAllQueryFails() throws SQLException {
        doThrow(new SQLException("Expected exception")).when(dao).getGetAllQuery(any());
        assertTrue(dao.getAll().isEmpty());
    }

    @Test
    void deleteDoesNotThrowEvenIfGettingDeleteQueryFails() throws SQLException {
        doThrow(new SQLException("Expected exception")).when(dao).getDeleteQuery(any(), any());
        assertDoesNotThrow(() -> dao.delete("0"));
    }

    @Test
    void checkDoesNotThrowEvenIfGettingCheckQueryFails() throws SQLException {
        doThrow(new SQLException("Expected exception")).when(dao).getCheckQuery(any(), any(), anyBoolean());
        assertDoesNotThrow(() -> dao.check("0", true));
    }

    @Test
    void updateDoesNotThrowEvenIfGettingUpdateQueryFails() throws SQLException {
        doThrow(new SQLException("Expected exception")).when(dao).getUpdateQuery(any(), any());
        assertDoesNotThrow(() -> dao.update(new BookTip("", "", "")));
    }
}
