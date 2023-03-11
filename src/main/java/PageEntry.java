public class PageEntry implements Comparable<PageEntry> { //имплементирую метод compareTo;
    private final String pdfName;
    private final int page;
    private final int count; //добавляю конструктор параметров. Параметры ниже.

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override //реализация интерфейса Comparable;
    public int compareTo(PageEntry o) {
        return this.count - o.count;
    }

    @Override
    //Здесь пойдет реализация самого PageEntry через toString, спрягая все элементы для выдачи в текстовой форме
    public String toString() {
        return "PageEntry { " + " Имя файла = " + pdfName + " страница = " + page + " количество = " + count + "}";
    }
}
