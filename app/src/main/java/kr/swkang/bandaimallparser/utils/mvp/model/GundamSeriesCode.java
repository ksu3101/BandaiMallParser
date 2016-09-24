package kr.swkang.bandaimallparser.utils.mvp.model;

import android.support.annotation.NonNull;

/**
 * @author KangSung-Woo
 * @since 2016-09-24
 */
public enum GundamSeriesCode {
  ALL("00"),
  DEF("01"),
  THE_ORIGIN("02"),
  PLATOON_08MS("03"),
  WAR_IN_POKET("04"),
  STARDUST_MEMORY("05"),
  Z_GUNDAM("06"),
  ZZ_GUNDAM("07"),
  CHARS_COUNTER_ATTACK("08");

  private String value;

  GundamSeriesCode(@NonNull String v) {
    this.value = v;
  }

  public String getValue() {
    return value;
  }
}
