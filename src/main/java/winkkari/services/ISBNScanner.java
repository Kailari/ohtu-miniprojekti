package winkkari.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;


public class ISBNScanner implements ISBNSearchService{

    private Scanner scanner;

    public ISBNScanner() {
    }

    @Override
    public Optional<BookInfo> find(String isbn) {
        if(isbn.substring(0, 4).equals("978-")){
            isbn = "978" + isbn.substring(4);
        }
        String isbnUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn;
        try {
            URL url = new URL(isbnUrl);
            scanner = new Scanner(url.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> data = new ArrayList<String>();

        String nl = "";
        while (scanner.hasNext()) {
            nl = scanner.nextLine();

            if(nl.contains("title") && !nl.contains("subtitle")){
                data.add(nl);
            }
            if(nl.contains("authors")){
                nl = scanner.nextLine();
                while(!nl.contains("publish")){
                    if(!nl.contains("[") && !nl.contains("]")){
                        data.add(nl);
                    }
                    nl = scanner.nextLine();
                }
            }
        }
        data = formatter(data);

        Optional<BookInfo> oB = Optional.ofNullable(new BookInfo(isbn, data.get(1), data.get(0)));

        return oB;
    }

    public ArrayList<String> formatter(ArrayList<String> data){
        String title = "";
        String dataTitle = data.get(0);
        String author = "";
        ArrayList<String> formatedData = new ArrayList<>(); 
        boolean flag = false;
        
        for(int i = 1; i < dataTitle.length(); i++){

            String letter = dataTitle.substring(i-1, i);
            
            if(letter.equals("\"")){
                flag = !flag;
                continue;
            }

            if(flag){
                title += letter;
            }

        }

        for(int j = 1; j < data.size(); j++){
            String tempAuth = data.get(j);
            String thisAuth = "";
            for(int i = 1; i < tempAuth.length(); i++){
                String letter = tempAuth.substring(i-1, i);
                if(letter.equals("\"")){
                    flag = !flag;
                    continue;
                }
    
                if(flag){
                    thisAuth += letter;
                }
            }
            author += thisAuth +", ";
        }
        author = author.substring(0, author.length()-2);

        formatedData.add(title.substring(5));
        formatedData.add(author);

        return formatedData;
    }

}