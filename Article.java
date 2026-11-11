import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Year;

public abstract class Article implements Serializable {
    private int id;
    private String title;
    private int releaseYear;
    private String publisher;
    private double basePrice;

    public static final String ERR_INVALID_PARAMETER = "Error: Invalid parameter.";
    public static final String ERR_INVALID_YEAR = "Error: Invalid release year.";
    public static final String ERR_INVALID_AGE_RATING = "Error: Invalid age rating.";

    public Article(int id, String title, int releaseYear, String publisher, double basePrice) {
        if (id < 0)
            throw new IllegalArgumentException(ERR_INVALID_PARAMETER);
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException(ERR_INVALID_PARAMETER);
        if (releaseYear < 1 || releaseYear > Year.now().getValue())
            throw new IllegalArgumentException(ERR_INVALID_YEAR);
        if (publisher == null || publisher.trim().isEmpty())
            throw new IllegalArgumentException(ERR_INVALID_PARAMETER);
        if (!(basePrice >= 0)) // fängt NaN ab
            throw new IllegalArgumentException(ERR_INVALID_PARAMETER);
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.publisher = publisher;
        this.basePrice = basePrice;
    }

    public static DecimalFormat getDecimalFormat() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        return new DecimalFormat("0.00", dfs);
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public int getReleaseYear() { return releaseYear; }

    public String getPublisher() { return publisher; }

    public double getBasePrice() { return basePrice; }

    public int getAge() { return Year.now().getValue() - releaseYear; }

    public abstract double getDiscount();

    public double getPrice() { return basePrice * (1.0 - getDiscount()); }

    @Override
    public String toString() {
        DecimalFormat df = getDecimalFormat();
        return  "Type:       " + getClass().getSimpleName() + "\n" +
                "Id:         " + id + "\n" +
                "Title:      " + title + "\n" +
                "Year:       " + releaseYear + "\n" +
                "Publisher:  " + publisher + "\n" +
                "Base price: " + df.format(basePrice) + "\n" +
                "Price:      " + df.format(getPrice());
    }
}
