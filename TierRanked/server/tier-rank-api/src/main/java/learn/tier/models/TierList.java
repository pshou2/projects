package learn.tier.models;

import java.time.LocalDateTime;

public class TierList {

    private int tierListId;
    private String name;
    private String description;
    private LocalDateTime timestamp;
    private String s_Tier;
    private String a_Tier;
    private String b_Tier;
    private String c_Tier;
    private String d_Tier;
    private String e_Tier;
    private String f_Tier;
    private int upvotes;
    private int downvotes;
    private int appUserId;
    private int categoryId;

    public TierList() {
    }

    public TierList(int tierListId, String name, String description, LocalDateTime timestamp, String s_Tier, String a_Tier, String b_Tier, String c_Tier, String d_Tier, String e_Tier, String f_Tier, int upvotes, int downvotes, int appUserId, int categoryId) {
        this.tierListId = tierListId;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.s_Tier = s_Tier;
        this.a_Tier = a_Tier;
        this.b_Tier = b_Tier;
        this.c_Tier = c_Tier;
        this.d_Tier = d_Tier;
        this.e_Tier = e_Tier;
        this.f_Tier = f_Tier;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.appUserId = appUserId;
        this.categoryId = categoryId;
    }

    public int getTierListId() {
        return tierListId;
    }

    public void setTierListId(int tierListId) {
        this.tierListId = tierListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getS_Tier() {
        return s_Tier;
    }

    public void setS_Tier(String s_Tier) {
        this.s_Tier = s_Tier;
    }

    public String getA_Tier() {
        return a_Tier;
    }

    public void setA_Tier(String a_Tier) {
        this.a_Tier = a_Tier;
    }

    public String getB_Tier() {
        return b_Tier;
    }

    public void setB_Tier(String b_Tier) {
        this.b_Tier = b_Tier;
    }

    public String getC_Tier() {
        return c_Tier;
    }

    public void setC_Tier(String c_Tier) {
        this.c_Tier = c_Tier;
    }

    public String getD_Tier() {
        return d_Tier;
    }

    public void setD_Tier(String d_Tier) {
        this.d_Tier = d_Tier;
    }

    public String getE_Tier() {
        return e_Tier;
    }

    public void setE_Tier(String e_Tier) {
        this.e_Tier = e_Tier;
    }

    public String getF_Tier() {
        return f_Tier;
    }

    public void setF_Tier(String f_Tier) {
        this.f_Tier = f_Tier;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
