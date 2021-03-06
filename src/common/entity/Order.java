package common.entity;

import java.util.Date;
import java.util.List;

public class Order implements java.io.Serializable{
    private static final long serialVersionUID = -2508573542830625357L;
    private String orderID;
    private String commodityID;
    private String buyerID;
    private String sellerID;
    private double price;
    private String name;
    private int nums;
    private int isAuction;
    private Date buyDate;
    private String picPath;
    private List<Comment> commentList;
    private double auctionPrice;

    public double getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(double auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public Order() {
    }

    public Order(String orderID, String commodityID, String buyerID, String sellerID, double price, String name, int nums, int isAuction, Date buyDate, String picPath, List<Comment> commentList) {
        this.orderID = orderID;
        this.commodityID = commodityID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.price = price;
        this.name = name;
        this.nums = nums;
        this.isAuction = isAuction;
        this.buyDate = buyDate;
        this.picPath = picPath;
        this.commentList = commentList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Order(String orderID, String commodityID, String buyerID, String sellerID, double price, String name, int nums, int isAuction, Date buyDate, String picPath) {
        this.orderID = orderID;
        this.commodityID = commodityID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.price = price;
        this.name = name;
        this.nums = nums;
        this.isAuction = isAuction;
        this.buyDate = buyDate;
        this.picPath = picPath;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getIsAuction() {
        return isAuction;
    }

    public void setIsAuction(int isAuction) {
        this.isAuction = isAuction;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Order(String orderID, String commodityID, String buyerID, String sellerID, double price, String name, int nums, int isAuction, Date buyDate) {
        this.orderID = orderID;
        this.commodityID = commodityID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.price = price;
        this.name = name;
        this.nums = nums;
        this.isAuction = isAuction;
        this.buyDate = buyDate;
    }
}
