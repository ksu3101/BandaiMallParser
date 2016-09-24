package kr.swkang.bandaimallparser.utils.mvp.model;

import android.support.annotation.NonNull;

/**
 * @author KangSung-Woo
 * @since 2016-09-24
 */
public enum GundamBrandCode {
  ALL("20101000"),
  PG("20101010"),
  MG("20101020"),
  RG("20101030"),
  HGUC("20101040"),
  HG("20101050"),
  RE("20101060"),
  MEGASIZE("20101070"),
  NON_BRAND_SCALE("20101080"),
  SD_BB("20101090"),
  ETC("20101100");

  private String value;

  GundamBrandCode(@NonNull String v) {
    this.value = v;
  }

  public String getValue() {
    return value;
  }

}
