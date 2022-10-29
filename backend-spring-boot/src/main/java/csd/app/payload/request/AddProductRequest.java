package csd.app.payload.request;

import javax.validation.constraints.*;

public class AddProductRequest {
    
    @NotBlank
    private String productName;

    @NotBlank
    private String category;

    @NotBlank
    @Size(min = 5, max = 50)
    private String description;

    @NotBlank
    @Size(max = 50)
    private String condition;

    @NotBlank
    private String dateTime;

    private String imageUrl;

    private Long userId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
