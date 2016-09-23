package kr.swkang.bandaimallparser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kr.swkang.bandaimallparser.utils.mvp.model.GunDamProductInfos;
import kr.swkang.bandaimallparser.utils.widgets.recyclerview.SwRecyclerViewAdapter;

/**
 * @author KangSung-Woo
 * @since 2016-09-23
 */
public class RvGundamProductAdapter
    extends SwRecyclerViewAdapter<GunDamProductInfos> {

  public RvGundamProductAdapter(@NonNull Context context, @NonNull List<GunDamProductInfos> list) {
    super(context, list);
  }

  @Override
  protected View createView(Context context, ViewGroup viewGroup, int viewType) {
    return LayoutInflater.from(context).inflate(R.layout.rvstub_gundamitems, viewGroup, false);
  }

  @Override
  protected void bindView(int viewType, GunDamProductInfos item, ViewHolder viewHolder) {
    if (item != null) {
      ImageView ivImg = (ImageView) viewHolder.getView(R.id.rvstub_gundam_iv);

      if (!TextUtils.isEmpty(item.getImagePath())) {
        Picasso.with(context)
               .load(item.getImagePath())
               .fit()
               .centerCrop()
               .into(ivImg);
      }

      TextView tvName = (TextView) viewHolder.getView(R.id.rvstub_gundam_tv_name);
      tvName.setText(item.getName());

      TextView tvPrice = (TextView) viewHolder.getView(R.id.rvstub_gundam_tv_price);
      tvPrice.setText(String.valueOf(item.getPriceOfKorea() + " 원"));

    }
  }

}
