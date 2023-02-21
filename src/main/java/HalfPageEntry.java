public class HalfPageEntry {
    private String pdfName;
    private int page;
    HalfPageEntry(String pdfName, int page) {
        this.pdfName = pdfName;
        this.page = page;}

    public int getPage() {
        return page;
    }

    public String getPdfName() {
        return pdfName;
    }

}
