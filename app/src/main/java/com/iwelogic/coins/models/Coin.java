package com.iwelogic.coins.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;

public class Coin implements Parcelable {

	@SerializedName("price_change_percentage_24h")
	private double priceChangePercentage24h;

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("image")
	private String image;

	@SerializedName("circulating_supply")
	private double circulatingSupply;

	@SerializedName("total_volume")
	private double totalVolume;

	@SerializedName("price_change_24h")
	private double priceChange24h;

	@SerializedName("last_updated")
	private String lastUpdated;

	@SerializedName("total_supply")
	private double totalSupply;

	@SerializedName("market_cap_rank")
	private int marketCapRank;

	@SerializedName("roi")
	private Object roi;

	@SerializedName("market_cap_change_24h")
	private double marketCapChange24h;

	@SerializedName("market_cap")
	private long marketCap;

	@SerializedName("ath")
	private double ath;

	@SerializedName("name")
	private String name;

	@SerializedName("high_24h")
	private double high24h;

	@SerializedName("low_24h")
	private double low24h;

	@SerializedName("market_cap_change_percentage_24h")
	private double marketCapChangePercentage24h;

	@SerializedName("id")
	private String id;

	@SerializedName("current_price")
	private double currentPrice;

	@SerializedName("ath_change_percentage")
	private double athChangePercentage;

	@SerializedName("ath_date")
	private String athDate;


	protected Coin(Parcel in) {
		priceChangePercentage24h = in.readDouble();
		symbol = in.readString();
		image = in.readString();
		circulatingSupply = in.readDouble();
		totalVolume = in.readDouble();
		priceChange24h = in.readDouble();
		lastUpdated = in.readString();
		totalSupply = in.readDouble();
		marketCapRank = in.readInt();
		marketCapChange24h = in.readDouble();
		marketCap = in.readLong();
		ath = in.readDouble();
		name = in.readString();
		high24h = in.readDouble();
		low24h = in.readDouble();
		marketCapChangePercentage24h = in.readDouble();
		id = in.readString();
		currentPrice = in.readDouble();
		athChangePercentage = in.readDouble();
		athDate = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(priceChangePercentage24h);
		dest.writeString(symbol);
		dest.writeString(image);
		dest.writeDouble(circulatingSupply);
		dest.writeDouble(totalVolume);
		dest.writeDouble(priceChange24h);
		dest.writeString(lastUpdated);
		dest.writeDouble(totalSupply);
		dest.writeInt(marketCapRank);
		dest.writeDouble(marketCapChange24h);
		dest.writeLong(marketCap);
		dest.writeDouble(ath);
		dest.writeString(name);
		dest.writeDouble(high24h);
		dest.writeDouble(low24h);
		dest.writeDouble(marketCapChangePercentage24h);
		dest.writeString(id);
		dest.writeDouble(currentPrice);
		dest.writeDouble(athChangePercentage);
		dest.writeString(athDate);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Coin> CREATOR = new Creator<Coin>() {
		@Override
		public Coin createFromParcel(Parcel in) {
			return new Coin(in);
		}

		@Override
		public Coin[] newArray(int size) {
			return new Coin[size];
		}
	};

	public double getPriceChangePercentage24h(){
		return priceChangePercentage24h;
	}

	public String getSymbol(){
		return symbol;
	}

	public String getImage(){
		return image;
	}

	public double getCirculatingSupply(){
		return circulatingSupply;
	}

	public double getTotalVolume(){
		return totalVolume;
	}

	public double getPriceChange24h(){
		return priceChange24h;
	}

	public String getLastUpdated(){
		return lastUpdated;
	}

	public double getTotalSupply(){
		return totalSupply;
	}

	public int getMarketCapRank(){
		return marketCapRank;
	}

	public Object getRoi(){
		return roi;
	}

	public double getMarketCapChange24h(){
		return marketCapChange24h;
	}

	public long getMarketCap(){
		return marketCap;
	}

	public double getAth(){
		return ath;
	}

	public String getName(){
		return name;
	}

	public double getHigh24h(){
		return high24h;
	}

	public double getLow24h(){
		return low24h;
	}

	public double getMarketCapChangePercentage24h(){
		return marketCapChangePercentage24h;
	}

	public String getId(){
		return id;
	}

	public double getCurrentPrice(){
		return currentPrice;
	}

	public double getAthChangePercentage(){
		return athChangePercentage;
	}

	public String getAthDate(){
		return athDate;
	}

	@BindingAdapter({ "image" })
	public static void loadImage(ImageView imageView, String imageURL) {
		Glide.with(imageView.getContext())
				.setDefaultRequestOptions(new RequestOptions()
						.circleCrop())
				.load(imageURL)
				.into(imageView);
	}
}