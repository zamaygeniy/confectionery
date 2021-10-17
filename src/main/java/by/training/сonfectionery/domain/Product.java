package by.training.сonfectionery.domain;

import java.io.InputStream;

public class Product extends Entity {
    private String name;
    private double price;
    private String description;
    private int weight;
    private String image;
    private int productTypeId;

    public Product(){

    }

    public double getPrice(){
        return price;
    }

    public String getDescription(){
        return description;
    }

    public int getWeight(){
        return weight;
    }

    public String getImage(){
        return image;
    }

    public int getProductTypeId(){
        return productTypeId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setProductTypeId(int productTypeId){
        this.productTypeId = productTypeId;
    }

    public static class ProductBuilder{
        Product product;

        public ProductBuilder(){
            product = new Product();
        }

        public Product.ProductBuilder setId(int id) {
            product.setId(id);
            return this;
        }

        public Product.ProductBuilder setName(String name){
            product.setName(name);
            return this;
        }

        public Product.ProductBuilder setPrice(double price){
            product.setPrice(price);
            return this;
        }

        public Product.ProductBuilder setDescription(String description){
            product.setDescription(description);
            return this;
        }

        public Product.ProductBuilder setWeight(int weight){
            product.setWeight(weight);
            return this;
        }

        public  Product.ProductBuilder setImage(String image){
            product.setImage(image);
            return this;
        }

        public Product.ProductBuilder setProductTypeId(int productTypeId){
            product.setProductTypeId(productTypeId);
            return this;
        }

        public Product createProduct() {
            return product;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Product{id=");
        sb.append(getId());
        sb.append(", name=");
        sb.append(name);
        sb.append(", price=");
        sb.append(price);
        sb.append(", description=");
        sb.append(description);
        sb.append(", weight=");
        sb.append(weight);
        sb.append("}");
        return sb.toString();
    }


}
