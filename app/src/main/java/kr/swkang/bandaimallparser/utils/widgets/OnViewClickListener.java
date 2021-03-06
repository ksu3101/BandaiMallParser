package kr.swkang.bandaimallparser.utils.widgets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import kr.swkang.bandaimallparser.utils.widgets.recyclerview.SwRecyclerViewAdapter;

/**
 * @author KangSung-Woo
 * @since 2016/07/11
 */
public interface OnViewClickListener {
  void onClicked(@NonNull SwRecyclerViewAdapter.ViewHolder viewHolder, @Nullable View touchedView, int position);
}
