package model;

public class DigitalProduct extends Product{
    private String downloadUrl;
    private double  fileSizeMB;

    @Override
    public String getSummary() {
        return "Digital: " + getName() + " [" + fileSizeMB + "MB] - " + downloadUrl;
    }

    public DigitalProduct() {};

    public DigitalProduct(Long id,
                          String name,
                          double price,
                          String downloadUrl,
                          double fileSizeMB) {
        super(id, name, price);
        this.downloadUrl = downloadUrl;
        this.fileSizeMB = fileSizeMB;
    }

    //Getters
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public double getFileSizeMB() {
        return fileSizeMB;
    }

    //Setters
    public void setDownloadUrl(String newDownloadUrl) {
        this.downloadUrl = newDownloadUrl;
    }
    public void setFileSizeMB(double newFileSizeMB) {
        if (newFileSizeMB < 0) {
            throw new IllegalArgumentException("File Size cannot be negative");
        }
        this.fileSizeMB = newFileSizeMB;
    }
}
