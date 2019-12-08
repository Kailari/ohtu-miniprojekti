package winkkari.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import winkkari.services.URLScanner;
import winkkari.services.URLInfo;

public class URLScannerTest {
    private URLScanner scanner;
    private Optional<URLInfo> info;

    @BeforeEach
    public void setUp() {
        scanner = new URLScanner();
    }
    
    @Test
    public void findMethodReturnsCorrectURLInfoWhenCalledWithValidURL() {
        info = scanner.find("https://www.nytimes.com/section/technology");
        assertEquals("https://www.nytimes.com/section/technology", info.get().getUrl());
        assertEquals("Technology - The New York Times", info.get().getTitle());
        assertTrue(info.get().getDescription().contains("Exploring the business,"));
    }

    @Test
    public void findMethodReturnsEmptyOptionalWhenCalledWithUnknownURL() {
        info = scanner.find("https://thisUrlDoesNotExist1234567");
        assertTrue(!info.isPresent());
    }

    @Test
    public void findMethodReturnsEmptyOptionalWhenCalledWithMalformattedURL() {
        info = scanner.find("asdasdasd");
        assertTrue(!info.isPresent());
    }
}