public class PageEntry implements Comparable<PageEntry> { //имплементирую метод compareTo;
    private final String pdfName;
    private final int page;
    private final int count; //добавляю конструктор параметров. Параметры ниже.

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return 0;
    }

    //реализуем JSONчик;
}
