public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public String toString() {
        return pdfName + " - " + page + " - " + count + ";";
    }

    public String toStringAsJSON() {
        return "\t{\n" + "\t\t\"pdfName\" : " + pdfName + ",\n" + "\t\t\"page\" : " + page + ",\n" + "\t\t\"count\" : " + count + "\n" + "\t}";
    }

    @Override
    public int compareTo(PageEntry o) {
        return o.count - this.count;
    }

}
