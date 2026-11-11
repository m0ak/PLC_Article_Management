public class Book extends Article {
    private int pages;

    public Book(int id, String title, int releaseYear, String publisher, double basePrice, int pages) {
        super(id, title, releaseYear, publisher, basePrice);
        if (pages <= 0)
            throw new IllegalArgumentException(ERR_INVALID_PARAMETER);
        this.pages = pages;
    }

    public int getPages() { return pages; }

    @Override
    public double getDiscount() {
        double discount = Math.min(getAge() * 0.05, 0.3);
        if (pages > 1000)
            discount += 0.03;
        return discount;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Pages:      " + pages + "\n";
    }
}
