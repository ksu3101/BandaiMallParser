package kr.swkang.bandaimallparser.utils.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author KangSung-Woo
 * @since 2016-09-23
 */

public class GunDamProductInfos
    implements Parcelable {
  private String name;
  private String imagePath;
  private String priceOfKorea;

  public GunDamProductInfos() {
    this.name = "";
    this.imagePath = "";
    this.priceOfKorea = "";
  }

  public GunDamProductInfos(String name, String imagePath, String priceOfKorea) {
    this.name = name;
    this.imagePath = imagePath;
    this.priceOfKorea = priceOfKorea;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getPriceOfKorea() {
    return priceOfKorea;
  }

  public void setPriceOfKorea(String priceOfKorea) {
    this.priceOfKorea = priceOfKorea;
  }

  @Override
  public String toString() {
    return "\"" + name + "\",\"" + imagePath + "\",\"" + priceOfKorea + "\"";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.imagePath);
    dest.writeString(this.priceOfKorea);
  }

  protected GunDamProductInfos(Parcel in) {
    this.name = in.readString();
    this.imagePath = in.readString();
    this.priceOfKorea = in.readString();
  }

  public static final Parcelable.Creator<GunDamProductInfos> CREATOR = new Parcelable.Creator<GunDamProductInfos>() {
    @Override
    public GunDamProductInfos createFromParcel(Parcel source) {
      return new GunDamProductInfos(source);
    }

    @Override
    public GunDamProductInfos[] newArray(int size) {
      return new GunDamProductInfos[size];
    }
  };
}
