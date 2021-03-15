package com.mw.coinbase.object;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Order {
    public String id;
    public String type;
    public String side;
    @SerializedName("product_id")
    public String productId;
    public String size;
    public String funds;
    public String price;
}
